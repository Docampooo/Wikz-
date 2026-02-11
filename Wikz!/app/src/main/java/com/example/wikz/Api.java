package com.example.wikz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class Api {

    public interface  ApiCallbackBitmap{
        void onResult(Bitmap bitmap);
    }

    public interface UpdateCallBackUsuario {
        void onResult(boolean success);
    }

    //Añadir los callbacks y las llamadas para los gets
    public interface LoginCallBackUsuario{
        void onLoginResult(boolean success, Usuario u);
    }

    public interface LoginCallBackUsuarios{
        void onLoginResult(boolean success, ArrayList<Usuario> usuarios);
    }

    public interface  LoginCallBackPublicaciones{
        void  onLoginResult(boolean succes, ArrayList<Publicacion>publicacions);
    }

    public interface PublicacionCallBack{
        void onResult(boolean success);
    }

    public interface LoginCallBackColecciones{
        void onLoginResult(boolean success, ArrayList<Coleccion> colecciones);
    }

    //funcion para convertir de BitMap a Byte[]
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    public static Bitmap base64ToBitmap(String base64) {
        if (base64 == null) return null;
        byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private Usuario mapUsuario(JSONObject obj) throws JSONException {

        Usuario u = new Usuario();

        u.setId(obj.getInt("id"));
        u.setNombre(obj.getString("nombre"));
        u.setEmail(obj.getString("email"));
        u.setBiografia(obj.getString("biografia"));

        long millis = obj.getLong("fechaCreacion");
        u.setFechaCreacion(new Date(millis));

        return u;
    }

    private Publicacion mapPublicacion(JSONObject obj) throws JSONException {

        Publicacion p = new Publicacion();

        p.setId(obj.getInt("id"));
        int idUsuario = obj.has("id_usuario")
                ? obj.getInt("id_usuario")
                : obj.has("usuario_id")
                ? obj.getInt("usuario_id")
                : obj.getInt("id");

        p.setIdUsuario(idUsuario);
        p.setTitulo(obj.getString("titulo"));
        p.setDescripcion(obj.getString("descripcion"));

        long millis = obj.getLong("fechaCreacion");
        p.setFechaCreacion(new Date(millis));

        return p;
    }

    private Coleccion mapColeccion(JSONObject obj) throws JSONException {

        Coleccion c = new Coleccion();

        c.setId(obj.getInt("id"));
        c.setIdUsuario(obj.getInt("idUsuario"));
        c.setTitulo(obj.getString("titulo"));

        // Imagen de la colección en Bitmap
        if (!obj.isNull("imagenBase64")) {
            Bitmap imagen = base64ToBitmap(obj.getString("imagenBase64"));
            c.setImagen(imagen);
        }

        // Publicaciones de la colección
        ArrayList<Publicacion> publicaciones = new ArrayList<>();
        if (!obj.isNull("elementos")) {
            JSONArray arr = obj.getJSONArray("elementos");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject pubObj = arr.getJSONObject(i);

                // Usamos mapPublicacion para cada publicación
                Publicacion p = new Publicacion();
                p = mapPublicacion(pubObj);
                Log.d("API", "Publicacion mapeada" + p);

                publicaciones.add(p);
            }
        }
        c.setElementos(publicaciones);

        return c;
    }

    public void addUsuario(Activity activity, String nombre, String email, String pass, String bio, UpdateCallBackUsuario call){

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/addUsuario");
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("nombre", nombre);
                json.put("email", email);
                json.put("pass", pass);
                json.put("biografia", bio);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));

                }catch (Exception e){
                    System.out.println("Error");
                }

                System.out.println(json);
                int code = con.getResponseCode();

                // REGRESAMOS AL HILO PRINCIPAL ANTES DE LLAMAR AL CALLBACK
                activity.runOnUiThread(() -> {
                    if (code == 200 || code == 201) {
                        call.onResult(true);
                    } else {
                        call.onResult(false);
                    }
                });

            }catch (Exception e) {
                activity.runOnUiThread(() ->
                        call.onResult(false));
            }
        }).start();
    }

    public void addPublicacion(Activity activity, int id_usuario, String titulo, Bitmap imagen, String descripcion, PublicacionCallBack callback) {

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL(
                        "http://10.0.2.2:8080/api/wikz/operaciones/addPublicacion"
                );
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );
                con.setDoOutput(true);
                con.setConnectTimeout(15000);
                con.setReadTimeout(15000);

                //Bitmap → Base64
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageBytes = baos.toByteArray();
                String imagenBase64 = Base64.encodeToString(
                        imageBytes,
                        Base64.NO_WRAP
                );

                JSONObject json = new JSONObject();
                json.put("idUsuario", id_usuario);
                json.put("titulo", titulo);
                json.put("imagenBase64", imagenBase64);
                json.put("descripcion", descripcion);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                int code = con.getResponseCode();

                activity.runOnUiThread(() -> {
                    if (code == 200 || code == 201) {
                        callback.onResult(true);
                    } else {
                        callback.onResult(false);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> callback.onResult(false));
            }

        }).start();
    }

    public void getFotoPerfil(Activity activity, int idUsuario, ApiCallbackBitmap call) {

        new Thread(() -> {
            HttpURLConnection conn = null;

            try {
                URL url = new URL(
                        "http://10.0.2.2:8080/api/wikz/operaciones/fotoPerfil?id=" + idUsuario
                );

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                conn.setDoInput(true);

                int responseCode = conn.getResponseCode();
                Log.i("API", "fotoPerfil code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream stream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();

                    activity.runOnUiThread(() -> call.onResult(bitmap));

                } else {
                    // 404, 204, etc → no hay foto
                    activity.runOnUiThread(() -> call.onResult(null));
                }

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> call.onResult(null));

            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void getFotoPublicacion(Activity activity, int idPublicacion, ApiCallbackBitmap call) {

        new Thread(() -> {
            HttpURLConnection conn = null;

            try {
                URL url = new URL(
                        "http://10.0.2.2:8080/api/wikz/operaciones/getImagenPublicacion?id=" + idPublicacion
                );

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                conn.setDoInput(true);

                int responseCode = conn.getResponseCode();
                Log.i("API", "Imagen publicacion code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream stream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();

                    activity.runOnUiThread(() -> call.onResult(bitmap));

                } else {
                    // 404, 204, etc → no hay foto
                    activity.runOnUiThread(() -> call.onResult(null));
                }

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> call.onResult(null));

            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void getUsuarioId(Activity activity, int idUs, LoginCallBackUsuario call) {

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getUsuarioId?id="+idUs);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                System.out.println("Código HTTP: " + code);

                InputStream stream = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                if (code == 200) {
                    JSONObject obj = new JSONObject(response.toString());

                    Usuario u = mapUsuario(obj);

                    activity.runOnUiThread(() -> call.onLoginResult(true, u));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, null));
                }

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> call.onLoginResult(false, null));
            }

        }).start();
    }

    public void getUsuarioNombrePass(Activity activity, String nombre, String pass, LoginCallBackUsuario call) {

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getUsuarioNombrePass?nombreUs=" + nombre + "&passUs="+ pass);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                System.out.println("Código HTTP: " + code);

                InputStream stream = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                if (code == 200) {
                    JSONObject obj = new JSONObject(response.toString());

                    Usuario u = mapUsuario(obj);

                    activity.runOnUiThread(() -> call.onLoginResult(true, u));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, null));
                }

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> call.onLoginResult(false, null));
            }

        }).start();
    }

    public void getPublicaciones(Activity activity, LoginCallBackPublicaciones call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getPublicaciones");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream stream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line.trim());
                    }

                    JSONArray array = new JSONArray(response.toString());
                    ArrayList<Publicacion> publis = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        publis.add(mapPublicacion(array.getJSONObject(i)));
                    }

                    // Éxito: enviamos la lista y salimos del método
                    activity.runOnUiThread(() -> call.onLoginResult(true, publis));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
            }
            // Si llegamos aquí es que algo falló (code != 200 o Exception)
            activity.runOnUiThread(() -> call.onLoginResult(false, null));
        }).start();
    }

    public void getPublicacionesUsuario(Activity activity, int idUsuario, LoginCallBackPublicaciones call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getPublicacionesUsuario?idUsuario=" + idUsuario);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) response.append(line.trim());

                    JSONArray array = new JSONArray(response.toString());
                    ArrayList<Publicacion> publis = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        publis.add(mapPublicacion(array.getJSONObject(i)));
                    }

                    activity.runOnUiThread(() -> call.onLoginResult(true, publis));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
            }
            activity.runOnUiThread(() -> call.onLoginResult(false, null));
        }).start();
    }

    public void getColeccionesUsuario(Activity activity, int idUsuario, LoginCallBackColecciones call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                // He añadido el parámetro idUsuario a la URL que faltaba en tu código
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getColeccionesUsuario?idUsuario=" + idUsuario);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) response.append(line.trim());

                    JSONArray array = new JSONArray(response.toString());
                    ArrayList<Coleccion> colec = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        colec.add(mapColeccion(array.getJSONObject(i)));
                    }

                    activity.runOnUiThread(() -> call.onLoginResult(true, colec));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
            }
            activity.runOnUiThread(() -> call.onLoginResult(false, new ArrayList<>()));
        }).start();
    }

    public void updateUsuario(Activity activity,Usuario u,Bitmap fotoPerfil,UpdateCallBackUsuario call) {

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL(
                        "http://10.0.2.2:8080/api/wikz/operaciones/updateUsuario"
                );

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(15000);
                con.setReadTimeout(15000);

                // =========================
                // JSON DEL USUARIO
                // =========================
                JSONObject json = new JSONObject();
                json.put("id", u.getId());
                json.put("nombre", u.getNombre());
                json.put("biografia", u.getBiografia());

                // =========================
                // FOTO PERFIL (OPCIONAL)
                // =========================
                if (fotoPerfil != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    // ⚠️ comprimimos para evitar payloads gigantes
                    fotoPerfil.compress(Bitmap.CompressFormat.JPEG, 70, baos);

                    byte[] imageBytes = baos.toByteArray();
                    String base64Image = Base64.encodeToString(
                            imageBytes,
                            Base64.NO_WRAP
                    );

                    json.put("fotoPerfilBase64", base64Image);
                } else {
                    json.put("fotoPerfilBase64", JSONObject.NULL);
                }

                // =========================
                // ENVIAR JSON
                // =========================
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = json.toString()
                            .getBytes(StandardCharsets.UTF_8);
                    os.write(input);
                    os.flush();
                }

                int code = con.getResponseCode();
                Log.i("API", "updateUsuario code: " + code);

                activity.runOnUiThread(() -> {
                    call.onResult(code == 200 || code == 201);
                });

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> call.onResult(false));
            } finally {
                if (con != null) con.disconnect();
            }

        }).start();
    }
}
