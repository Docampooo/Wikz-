package clases;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

@Path("/operaciones")
public class Operaciones {

    // Hassear contraseña con jBcrypt
    public static String hashPassword(String password) {
        // Genera el hash compatible con PHP
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Rutas
    // Ruta para el rester: http://localhost:8080/api/wikz/operaciones
    private static final String ruta = "jdbc:mariadb://localhost:3306/wikz";
    private static final String user = "root";
    private static final String pass = "";

    // Helpers
    private Usuario mapUsuario(ResultSet rs) throws SQLException {

        // Se omite la contraseña para devolver
        Usuario u = new Usuario();

        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setBiografia(rs.getString("bio"));
        byte[] foto = rs.getBytes("foto_perfil");
        if (foto != null)
            u.setFotoPerfilBase64(Base64.getEncoder().encodeToString(foto));

        u.setFechaCreacion(rs.getTimestamp("creacion"));
        return u;
    }

    private Publicacion mapPublicacion(ResultSet rs) throws SQLException {

        Publicacion p = new Publicacion();

        p.setId(rs.getInt("id"));
        p.setIdUsuario(rs.getInt("id_usuario"));
        p.setTitulo(rs.getString("titulo"));
        p.setDescripcion(rs.getString("descripcion"));

        byte[] img = rs.getBytes("imagen");
        if (img != null)
            p.setImagenBase64(Base64.getEncoder().encodeToString(img));

        p.setFechaCreacion(rs.getTimestamp("creacion"));
        return p;
    }

    private Coleccion mapColeccion(ResultSet rs) throws SQLException {
        Coleccion c = new Coleccion();

        c.setId(rs.getInt("id"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setTitulo(rs.getString("titulo"));

        byte[] img = rs.getBytes("imagen_url");
        if (img != null) {
            c.setImagenBase64(
                    Base64.getEncoder().encodeToString(img));
        }

        return c;
    }

    public ArrayList<Publicacion> getPublicacionesDeColeccion(int idColeccion) {

        ArrayList<Publicacion> publis = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String sql = "SELECT p.* FROM publicaciones p JOIN guardados g ON p.id = g.id_publicacion WHERE g.id_coleccion = ?";

                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setInt(1, idColeccion);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    publis.add(mapPublicacion(rs));
                }

            } catch (Exception e) {

            }

        } catch (Exception e) {
            return null;

        }
        return publis;
    }

    // Añadir un usuario a la base de datos --> bien
    // http://localhost:8080/api/wikz/operaciones/addUsuario
    @POST
    @Path("/addUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addUsuario(UsuarioRegistro u) {

        if (u == null || u.getNombre() == null || u.getEmail() == null || u.getPass() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Datos de usuario incompletos")
                    .build();
        }

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String checkSql = "SELECT 1 FROM usuarios WHERE email = ? OR nombre = ?";
                PreparedStatement checkPs = conexion.prepareStatement(checkSql);
                checkPs.setString(1, u.getEmail());
                checkPs.setString(2, u.getNombre());

                ResultSet rs = checkPs.executeQuery();

                // si hay algun resultado, el usuario ya existe
                if (rs.next()) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Ya existe un usuario con ese nombre o correo")
                            .build();
                }

                String consulta = "INSERT INTO usuarios (nombre, email, pass, bio, foto_perfil) VALUES(?, ?, ?, ?, ?)";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, u.getNombre());
                ps.setString(2, u.getEmail());

                // hashear contraseña
                String hash = hashPassword(u.getPass());
                ps.setString(3, hash);

                ps.setString(4, u.getBiografia());

                byte[] foto = null;
                if (u.getFotoPerfilBase64() != null) {
                    foto = Base64.getDecoder().decode(u.getFotoPerfilBase64());
                }

                ps.setBytes(5, foto);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas == 1) {
                    return Response.ok("Se ha añadido el usuario").build();
                } else {
                    return Response.status(Response.Status.NOT_IMPLEMENTED).entity("No se ha añadido el Usuario")
                            .build();
                }

            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en los drivers").build();
        }
    }

    // Añadir una publicacion --> bien
    // http://localhost:8080/api/wikz/operaciones/addPublicacion
    @POST
    @Path("/addPublicacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPublicacion(Publicacion p) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {
                String consulta = "INSERT INTO publicaciones (id_usuario, titulo, imagen, descripcion) VALUES(?, ?, ?, ?)";
                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, p.getIdUsuario());
                ps.setString(2, p.getTitulo());

                byte[] foto = null;
                if (p.getImagenBase64() != null) {
                    // Importante: El JS debe enviar solo el string Base64 sin el prefijo
                    // "data:image..."
                    foto = Base64.getDecoder().decode(p.getImagenBase64());
                }

                ps.setBytes(3, foto);
                ps.setString(4, p.getDescripcion());

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas == 1) {
                    return Response.ok("{\"mensaje\":\"Se ha añadido la publicacion\"}").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"error\":\"No se insertó la fila\"}").build();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @OPTIONS
    @Path("/addPublicacion")
    public Response optionsAddPublicacion() {
        return Response.ok().build();
    }

    // Añadir colecciones --> bien
    // http://localhost:8080/api/wikz/operaciones/addColeccion
    @POST
    @Path("/addColeccion")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addColeccion(Coleccion c) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "INSERT INTO Colecciones (id_usuario, titulo, imagen_url) VALUES(?, ?, ?)";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, c.getIdUsuario());
                ps.setString(2, c.getTitulo());

                byte[] foto = null;
                if (c.getImagenBase64() != null) {
                    foto = Base64.getDecoder().decode(c.getImagenBase64());
                }

                ps.setBytes(3, foto);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas == 1) {
                    return Response.ok("Se ha añadido la coleccion").build();
                } else {
                    return Response.status(Response.Status.NOT_IMPLEMENTED).entity("No se ha añadido la coleccion")
                            .build();
                }

            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en los drivers").build();
        }
    }

    // añade una publicacion a una coleccion --> bien
    // http://localhost:8080/api/wikz/operaciones/addPublicacionColeccion
    @POST
    @Path("/addPublicacionColeccion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPublicacionColeccion(Guardado g) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "INSERT INTO guardados(id_coleccion, id_publicacion) VALUES (?, ?)";

                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, g.getIdColeccion());
                ps.setInt(2, g.getIdPublicacion());

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas == 1) {
                    return Response.ok("Se ha añadido la relacion").build();
                } else {
                    return Response.status(Response.Status.NOT_IMPLEMENTED).entity("No se ha añadido la relacion")
                            .build();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage())
                        .build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al guardar publicación")
                    .build();

        }
    }

    // Obtener un usuario por id --> bien
    // ruta: http://localhost:8080/api/wikz/operaciones/getUsuarioId
    @GET
    @Path("/getUsuarioId")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getUsuarioId(@QueryParam("id") int id) {

        Usuario u = new Usuario();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM usuarios where id = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Usuario no encontrado")
                            .build();
                }

                u = mapUsuario(rs);

                return Response.ok(u).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Usuario por su nombre --> bien
    // http://localhost:8080/api/wikz/operaciones/getUsuarioNombre?nombre=MorganBono
    @GET
    @Path("/getUsuarioNombre")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getUsuarioNombre(@QueryParam("nombre") String nombre) {

        Usuario u = new Usuario();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM usuarios WHERE nombre = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, nombre);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Usuario no encontrado")
                            .build();
                }

                u = mapUsuario(rs);

                return Response.ok(u).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Usuario por nombre y contraseña --> bien
    // path:http://localhost:8080/api/wikz/operaciones/getUsuarioNombrePass?nombreUs=iago&passUs=34
    @GET
    @Path("/getUsuarioNombrePass")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioNombrePass(@QueryParam("nombreUs") String nombreUs, @QueryParam("passUs") String passUs) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM usuarios WHERE nombre = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setString(1, nombreUs);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Extraemos el hash PRIMERO
                        String hashBD = rs.getString("pass");

                        // Verificamos si es BCrypt
                        if (hashBD != null && hashBD.startsWith("$2")) {
                            String hashParaValidar = hashBD.startsWith("$2y$")
                                    ? hashBD.replaceFirst("\\$2y\\$", "\\$2a\\$")
                                    : hashBD;

                            if (BCrypt.checkpw(passUs, hashParaValidar)) {
                                // SI LA CONTRASEÑA ES CORRECTA, MAPEAMOS
                                // Invocamos a mapUsuario solo si la validación es exitosa
                                Usuario u = mapUsuario(rs);
                                return Response.ok(u).build();
                            }
                        }
                    }

                    // Si no hay usuario o la contraseña no coincide
                    return Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"error\":\"Usuario o contraseña incorrectos\"}")
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Obtener todos los usuarios --> bien
    // ruta: http://localhost:8080/api/wikz/operaciones/getUsuarios
    @GET
    @Path("/getUsuarios")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM usuarios";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Usuario u = mapUsuario(rs);

                    usuarios.add(u);
                }

                if (usuarios.size() == 0) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No se han encontrado usuarios").build();

                } else {

                    return Response.ok(usuarios).build();
                }

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Obtener todas las publicaciones --> bien
    // ruta: http://localhost:8080/api/wikz/operaciones/getPublicaciones
    @GET
    @Path("/getPublicaciones")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPublicaciones() {

        ArrayList<Publicacion> publis = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM publicaciones ORDER BY RAND()";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Publicacion p = mapPublicacion(rs);

                    publis.add(p);
                }

                if (publis.size() == 0) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("No se han encontrado publicaciones")
                            .build();

                } else {
                    return Response.ok(publis).build();

                }

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Obtener una publicacoin por id --> bien
    // ruta: http://localhost:8080/api/wikz/operaciones/getPublicacionId?id=x
    @GET
    @Path("/getPublicacionId")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPublicacionId(@QueryParam("id") int id) {

        Publicacion p = new Publicacion();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM publicaciones where id = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("publicacion no encontrada")
                            .build();
                }

                p = mapPublicacion(rs);

                return Response.ok(p).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Devuelve las publicaciones de un usuario --> bien
    // ruta: http://localhost:8080/api/wikz/operaciones/getPublicacionesUsuario
    @GET
    @Path("/getPublicacionesUsuario")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPublicacionesUsuario(@QueryParam("idUsuario") int idUsuario) {

        ArrayList<Publicacion> publis = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM publicaciones WHERE id_usuario = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, idUsuario);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Publicacion p = mapPublicacion(rs);

                    publis.add(p);
                }

                if (publis.size() == 0) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("No se han encontrado publicaciones del usuario" + idUsuario)
                            .build();

                } else {
                    return Response.ok(publis).build();

                }

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // obtener las colecciones de un usuario --> bien
    // http://localhost:8080/api/wikz/operaciones/getColeccionesUsuario?idUsuario=1
    @GET
    @Path("/getColeccionesUsuario")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getColeccionesUsuario(@QueryParam("idUsuario") int idUsuario) {

        ArrayList<Coleccion> colec = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM colecciones WHERE id_usuario = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, idUsuario);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Coleccion c = mapColeccion(rs);

                    colec.add(c);
                }

                if (colec.size() == 0) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("No se han encontrado colecciones del usuario")
                            .build();

                } else {
                    return Response.ok(colec).build();

                }

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // obtiene las publicaciones contenidas en las publicaciones --> bien
    // http://localhost:8080/api/wikz/operaciones/getPublicacionesColeccion?idColeccion=1
    @GET
    @Path("/getPublicacionesColeccion")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPublicacionesColeccion(@QueryParam("idColeccion") int idColeccion) {

        ArrayList<Publicacion> p = getPublicacionesDeColeccion(idColeccion);

        if (p.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se han encontrado publicaciones en esta coleccion").build();

        } else if (p.equals(null)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("La coleccion no existe").build();

        }
        return Response.ok(p).build();
    }

    @POST
    @Path("/updateUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsuario(Usuario u) {

        if (u == null || u.getId() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Usuario inválido")
                    .build();
        }

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String sql;
                PreparedStatement ps;

                if (u.getFotoPerfilBase64() != null) {

                    sql = "UPDATE usuarios SET nombre = ?, bio = ?, foto_perfil = ? WHERE id = ?";

                    ps = conexion.prepareStatement(sql);
                    ps.setString(1, u.getNombre());
                    ps.setString(2, u.getBiografia());
                    ps.setBytes(3, Base64.getDecoder().decode(u.getFotoPerfilBase64()));
                    ps.setInt(4, u.getId());

                } else {

                    sql = "UPDATE usuarios SET nombre = ?, bio = ? WHERE id = ?";

                    ps = conexion.prepareStatement(sql);
                    ps.setString(1, u.getNombre());
                    ps.setString(2, u.getBiografia());
                    ps.setInt(3, u.getId());
                }

                int filas = ps.executeUpdate();

                if (filas == 1) {
                    return Response.ok("Usuario actualizado correctamente").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Usuario no encontrado")
                            .build();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error en los drivers")
                    .build();
        }
    }

    @GET
    @Path("/fotoPerfil")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFotoPerfil(@QueryParam("id") int idUsuario) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(ruta, user, pass)) {

                String sql = "SELECT foto_perfil FROM usuarios WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idUsuario);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    byte[] foto = rs.getBytes("foto_perfil");

                    if (foto == null) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }

                    return Response.ok(foto)
                            .type("image/jpeg")
                            .build();
                }

                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getImagenPublicacion")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImagenPublicacion(@QueryParam("id") int idPublicacion) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(ruta, user, pass)) {

                String sql = "SELECT imagen FROM publicaciones WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idPublicacion);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    byte[] foto = rs.getBytes("imagen");

                    if (foto == null) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }

                    return Response.ok(foto)
                            .type("image/jpeg")
                            .build();
                }

                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}