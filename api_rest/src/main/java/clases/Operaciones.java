package clases;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/operaciones")
public class Operaciones {

    //Hassear contraseña
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
    private static final String ruta = "http://192.130.0.138:8000/API_REST/operaciones";
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
                ps.setString(2, u.getCorreo());

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

                String consulta = "INSERT INTO publicaciones (id_usuario, titulo, foto_perfil, bio) VALUES(?, ?, ?, ?)";

                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, p.getIdUsuario());
                ps.setString(2, p.getNombre());

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

}
