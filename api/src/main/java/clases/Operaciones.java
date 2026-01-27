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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/operaciones")
public class Operaciones {

    // Hassear contraseña
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Rutas
    // Ruta para el rester: http://localhost:8080/api/wikz/operaciones
    private static final String ruta = "jdbc:mariadb://localhost:3306/wikz";
    private static final String user = "root";
    private static final String pass = "";

    // Añadir un usuario a la base de datos
    @POST
    @Path("/addUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addUsuario(Usuario u) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "INSERT INTO usuarios (nombre, email, pass, bio, foto_perfil) VALUES(?, ?, ?, ?, ?)";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, u.getNombre());
                ps.setString(2, u.getEmail());

                // hashear contraseña
                String hash = hashPassword(u.getPass());
                ps.setString(3, hash);

                ps.setString(4, u.getBiografia());
                ps.setBytes(5, u.getFotoPerfil());

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

    // Añadir una publicacion
    @POST
    @Path("/addPublicacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addPublicacion(Publicacion p) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "INSERT INTO publicaciones (id_usuario, titulo, imagen, descripcion) VALUES(?, ?, ?, ?)";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, p.getIdUsuario());
                ps.setString(2, p.getTitulo());

                ps.setBytes(3, p.getImagen());
                ps.setString(4, p.getDescripcion());

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas == 1) {
                    return Response.ok("Se ha añadido la publicacion").build();
                } else {
                    return Response.status(Response.Status.NOT_IMPLEMENTED).entity("No se ha añadido la publicacion")
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

    // Obtener todos los usuarios
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

                    Usuario u = new Usuario();

                    u.setId(rs.getInt("id"));
                    u.setNommbre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setPass(rs.getString("pass"));
                    u.setBiografia(rs.getString("bio"));
                    u.setFotoPerfil(rs.getBytes("foto_perfil"));
                    u.setFechaCreacion(rs.getDate("creacion"));

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

    // Obtener un usuario por id
    // ruta: http://localhost:8080/api/wikz/operaciones/getUsuarioId?id=x
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

                u.setId(id);
                u.setNommbre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPass(rs.getString("pass"));
                u.setBiografia(rs.getString("bio"));
                u.setFotoPerfil(rs.getBytes("foto_perfil"));
                u.setFechaCreacion(rs.getDate("creacion"));

                return Response.ok(u).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    @GET
    @Path("/gethola")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response gethola() {
        return Response.ok("hola").build();
    }

    @GET
    @Path("/getUsuarioNombre")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getUsuarioNombre(@QueryParam("nombre") String nombre) {

        Usuario u = new Usuario();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM usuarios WHERE nombre LIKE ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, nombre);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Usuario no encontrado")
                            .build();
                }

                u.setId(rs.getInt("id"));
                u.setNommbre(nombre);
                u.setEmail(rs.getString("email"));
                u.setPass(rs.getString("pass"));
                u.setBiografia(rs.getString("bio"));
                u.setFotoPerfil(rs.getBytes("foto_perfil"));
                u.setFechaCreacion(rs.getDate("creacion"));

                return Response.ok(u).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Obtener todas las publicaciones
    // ruta: http://localhost:8080/api/wikz/operaciones/getPublicaciones
    @GET
    @Path("/getPublicaciones")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPublicaciones() {

        ArrayList<Publicacion> publis = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM publicaciones";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Publicacion p = new Publicacion(rs.getInt("id"), rs.getInt("id_usuario"), rs.getString("titulo"),
                            rs.getBytes("imagen"), rs.getString("descripcion"));

                    p.setFechaCreacion(rs.getDate("creacion"));

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

    // Obtener un usuario por id
    // ruta: http://localhost:8080/api/wikz/operaciones/getUsuarioId?id=x
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

                p.setId(rs.getInt("id"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getBytes("imagen"));
                p.setFechaCreacion(rs.getDate("creacion"));

                return Response.ok(p).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en la base de datos")
                        .build();

            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Driver").build();
        }
    }

    // Obtener todas las publicaciones
    // ruta: http://localhost:8080/api/wikz/operaciones/getPublicaciones
    @GET
    @Path("/getPublicaciones")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPublicacionesUsuario(@QueryParam("idUsuario") int idUsuario) {

        ArrayList<Publicacion> publis = new ArrayList<>();

        //seguir aqui !!!!!
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(ruta, user, pass)) {

                String consulta = "SELECT * FROM publicaciones where id_usuario = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, idUsuario);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Publicacion p = new Publicacion(rs.getInt("id"), rs.getInt("id_usuario"), rs.getString("titulo"),
                            rs.getBytes("imagen"), rs.getString("descripcion"));

                    p.setFechaCreacion(rs.getDate("creacion"));

                    publis.add(p);
                }

                if (publis.size() == 0) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No se han encontrado publicaciones")
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
}
