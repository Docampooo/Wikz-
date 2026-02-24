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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

public class Api {

    // --- CONFIGURACIÓN DE RED CENTRALIZADA ---
    // Emulador: "10.0.2.2"
    // Casa: "192.168.1.64"
    // Clase: "192.130.0.157"
    // movil: 10.1.13.56
    private static final String IP_SERVIDOR = "10.1.13.56";
    private static final String BASE_URL = "http://" + IP_SERVIDOR + ":8080/api/wikz/operaciones/";
    // -----------------------------------------

    public interface ApiCallbackBitmap {
        void onResult(Bitmap bitmap);
    }

    public interface UpdateCallBackUsuario {
        void onResult(boolean success);
    }

    public interface LoginCallBackUsuario {
        void onLoginResult(boolean success, Usuario u);
    }

    public interface LoginCallBackUsuarios {
        void onLoginResult(boolean success, ArrayList<Usuario> usuarios);
    }

    public interface LoginCallBackPublicaciones {
        void onLoginResult(boolean succes, ArrayList<Publicacion> publicacions);
    }

    public interface PublicacionCallBack {
        void onResult(boolean success);
    }

    public interface LoginCallBackColecciones {
        void onLoginResult(boolean success, ArrayList<Coleccion> colecciones);
    }

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
        int idUsuario = 0;
        if (obj.has("id_usuario")) {
            idUsuario = obj.getInt("id_usuario");
        } else if (obj.has("idUsuario")) {
            idUsuario = obj.getInt("idUsuario");
        }
        p.setIdUsuario(idUsuario);
        p.setTitulo(obj.getString("titulo"));
        p.setDescripcion(obj.getString("descripcion"));
        if (obj.has("fechaCreacion")) {
            p.setFechaCreacion(new Date(obj.getLong("fechaCreacion")));
        }
        return p;
    }

    private Coleccion mapColeccion(JSONObject obj) throws JSONException {
        Coleccion c = new Coleccion();
        c.setId(obj.getInt("id"));
        c.setIdUsuario(obj.getInt("idUsuario"));
        c.setTitulo(obj.getString("titulo"));
        if (!obj.isNull("imagenBase64")) {
            Bitmap imagen = base64ToBitmap(obj.getString("imagenBase64"));
            c.setImagen(imagen);
        }
        ArrayList<Publicacion> publicaciones = new ArrayList<>();
        if (!obj.isNull("elementos")) {
            JSONArray arr = obj.getJSONArray("elementos");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject pubObj = arr.getJSONObject(i);
                Publicacion p = mapPublicacion(pubObj);
                publicaciones.add(p);
            }
        }
        c.setElementos(publicaciones);
        return c;
    }

    public void addUsuario(Activity activity, String nombre, String email, String pass, String bio, UpdateCallBackUsuario call) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL(BASE_URL + "addUsuario");
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
                }
                int code = con.getResponseCode();
                activity.runOnUiThread(() -> call.onResult(code == 200 || code == 201));
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onResult(false));
            }
        }).start();
    }

    public void addPublicacion(Activity activity, int id_usuario, String titulo, Bitmap imagen, String descripcion, PublicacionCallBack callback) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL(BASE_URL + "addPublicacion");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setDoOutput(true);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                String imagenBase64 = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);

                JSONObject json = new JSONObject();
                json.put("idUsuario", id_usuario);
                json.put("titulo", titulo);
                json.put("imagenBase64", imagenBase64);
                json.put("descripcion", descripcion);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }
                int code = con.getResponseCode();
                activity.runOnUiThread(() -> callback.onResult(code == 200 || code == 201));
            } catch (Exception e) {
                activity.runOnUiThread(() -> callback.onResult(false));
            }
        }).start();
    }

    public void getFotoPerfil(Activity activity, int idUsuario, ApiCallbackBitmap call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(BASE_URL + "fotoPerfil?id=" + idUsuario);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream stream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();
                    activity.runOnUiThread(() -> call.onResult(bitmap));
                } else {
                    activity.runOnUiThread(() -> call.onResult(null));
                }
            } catch (Exception e) {
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
                URL url = new URL(BASE_URL + "getImagenPublicacion?id=" + idPublicacion);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream stream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();
                    activity.runOnUiThread(() -> call.onResult(bitmap));
                } else {
                    activity.runOnUiThread(() -> call.onResult(null));
                }
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onResult(null));
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void getUsuarioId(Activity activity, int idUs, LoginCallBackUsuario call) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "getUsuarioId?id=" + idUs);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) response.append(line.trim());
                    JSONObject obj = new JSONObject(response.toString());
                    Usuario u = mapUsuario(obj);
                    activity.runOnUiThread(() -> call.onLoginResult(true, u));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, null));
                }
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onLoginResult(false, null));
            }
        }).start();
    }

    public void getUsuarioNombrePass(Activity activity, String nombre, String pass, LoginCallBackUsuario call) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "getUsuarioNombrePass?nombreUs=" + nombre + "&passUs=" + pass);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) response.append(line.trim());
                    JSONObject obj = new JSONObject(response.toString());
                    Usuario u = mapUsuario(obj);
                    activity.runOnUiThread(() -> call.onLoginResult(true, u));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, null));
                }
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onLoginResult(false, null));
            }
        }).start();
    }

    public void getPublicaciones(Activity activity, LoginCallBackPublicaciones call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(BASE_URL + "getPublicaciones");
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
                    for (int i = 0; i < array.length(); i++) publis.add(mapPublicacion(array.getJSONObject(i)));
                    activity.runOnUiThread(() -> call.onLoginResult(true, publis));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, null));
                }
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onLoginResult(false, null));
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void getPublicacionesUsuario(Activity activity, int idUsuario, LoginCallBackPublicaciones call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(BASE_URL + "getPublicacionesUsuario?idUsuario=" + idUsuario);
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
                    for (int i = 0; i < array.length(); i++) publis.add(mapPublicacion(array.getJSONObject(i)));
                    activity.runOnUiThread(() -> call.onLoginResult(true, publis));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, null));
                }
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onLoginResult(false, null));
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void getColeccionesUsuario(Activity activity, int idUsuario, LoginCallBackColecciones call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(BASE_URL + "getColeccionesUsuario?idUsuario=" + idUsuario);
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
                    for (int i = 0; i < array.length(); i++) colec.add(mapColeccion(array.getJSONObject(i)));
                    activity.runOnUiThread(() -> call.onLoginResult(true, colec));
                } else {
                    activity.runOnUiThread(() -> call.onLoginResult(false, new ArrayList<>()));
                }
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onLoginResult(false, new ArrayList<>()));
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void eliminarPublicacion(Activity activity, int idPublicacion, PublicacionCallBack call) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(BASE_URL + "eliminarPublicacion?idPublicacion=" + idPublicacion);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Accept", "application/json");
                int code = conn.getResponseCode();
                activity.runOnUiThread(() -> call.onResult(code == 200));
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onResult(false));
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    public void updateUsuario(Activity activity, Usuario u, Bitmap fotoPerfil, UpdateCallBackUsuario call) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL(BASE_URL + "updateUsuario");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("id", u.getId());
                json.put("nombre", u.getNombre());
                json.put("biografia", u.getBiografia());

                if (fotoPerfil != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fotoPerfil.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    String base64Image = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
                    json.put("fotoPerfilBase64", base64Image);
                } else {
                    json.put("fotoPerfilBase64", JSONObject.NULL);
                }

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }
                int code = con.getResponseCode();
                activity.runOnUiThread(() -> call.onResult(code == 200 || code == 201));
            } catch (Exception e) {
                activity.runOnUiThread(() -> call.onResult(false));
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }
}