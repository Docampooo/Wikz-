package com.example.wikz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Api {

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
        u.setPass(obj.getString("pass"));
        u.setBiografia(obj.getString("bio"));

        if (!obj.isNull("fotoPerfilBase64")) {
            Bitmap foto = base64ToBitmap(
                    obj.getString("fotoPerfilBase64")
            );
            u.setFotoPerfil(foto);
        }

        long millis = obj.getLong("fechaCreacion");
        u.setFechaCreacion(new Date(millis));

        return u;
    }

    private Publicacion mapPublicacion(JSONObject obj) throws JSONException {

        Publicacion p = new Publicacion();

        p.setId(obj.getInt("id"));
        p.setIdUsuario(obj.getInt("id_usuario"));
        p.setTitulo(obj.getString("titulo"));
        p.setDescripcion(obj.getString("descripcion"));

        if (!obj.isNull("imagenBase64")) {
            Bitmap imagen = base64ToBitmap(
                    obj.getString("imagenBase64")
            );
            p.setImagen(imagen);
        }

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
                Publicacion p = mapPublicacion(pubObj);

                publicaciones.add(p);
            }
        }
        c.setElementos(publicaciones);

        return c;
    }

    public void addUsuario(Activity activity, String nombre, String email, String pass, String bio , byte[] foto_perfil){

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/addUsuario");
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                String base64Image = Base64.encodeToString(foto_perfil, Base64.NO_WRAP);

                JSONObject json = new JSONObject();
                json.put("nombre", nombre);
                json.put("email", email);
                json.put("pass", pass);
                json.put("bio", bio);
                json.put("fotoPerfil", base64Image);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));

                }catch (Exception e){
                    System.out.println("Error");
                }

                System.out.println(json);
                int code = con.getResponseCode();

                if (code == 200 || code == 201) {
                    activity.runOnUiThread(() ->
                            activity.startActivity(new Intent(activity, MenuPrincipal.class)));

                }else if(code == 409){

                    activity.runOnUiThread(() ->
                            Toast.makeText(activity, "Datos de usuario ya existentes", Toast.LENGTH_SHORT).show());
                }

            }catch (Exception e) {
                activity.runOnUiThread(() ->
                        Toast.makeText(activity, "Error de conexión", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    public void addPublicacion(Activity activity, int id_usuario ,String titulo, byte[]imagen, String descripcion){

        new Thread(() -> {

            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/addPublicacion");
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("id_usuario", id_usuario);
                json.put("titulo", titulo);
                json.put("imagen", JSONObject.NULL);
                json.put("descripcion", descripcion);
                json.put("foto_perfil", JSONObject.NULL); // por ahora

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));

                }catch (Exception e){
                    System.out.println("Error");
                }

                System.out.println(json);
                int code = con.getResponseCode();

                if (code == 200 || code == 201) {
                    activity.runOnUiThread(() ->
                            activity.startActivity(new Intent(activity, MenuPrincipal.class)));

                }

            }catch (Exception e) {
                activity.runOnUiThread(() ->
                        Toast.makeText(activity, "Error de conexión", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    public Usuario getUsuarioNombrePass(Activity activity, String nombre, String pass){

            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getUsuarioNombrePass");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                System.out.println("Código HTTP: " + code);

                InputStream stream = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null)
                {
                    response.append(line.trim());
                }

                if (code == 200) {
                    JSONObject obj = new JSONObject(response.toString());

                    Usuario u = mapUsuario(obj);

                    return  u;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return  null;
    }

    public boolean getUsuarioEmail(Activity activity, String email){

        HttpURLConnection con = null;

        try {
            URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getUsuarioEmail");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                response.append(line.trim());
            }

            if (code == 200) {

                return  true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Publicacion> getPublicaciones(Activity activity){

        HttpURLConnection con = null;
        ArrayList<Publicacion> publis = new ArrayList<>();

        try {
            URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getPublicaciones");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                response.append(line.trim());
            }

            if (code == 200) {
                JSONArray array = new JSONArray(response.toString());

                for(int i = 0; i < array.length(); i++){

                    JSONObject obj = array.getJSONObject(i);
                    Publicacion p = mapPublicacion(obj);

                    publis.add(p);
                }
                return  publis;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  publis;
    }

    public ArrayList<Publicacion> getPublicacionesUsuario(Activity activity, int id_usuario){

        HttpURLConnection con = null;
        ArrayList<Publicacion> publis = new ArrayList<>();

        try {
            URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getPublicacionesUsuario");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                response.append(line.trim());
            }

            if (code == 200) {
                JSONArray array = new JSONArray(response.toString());

                for(int i = 0; i < array.length(); i++){

                    JSONObject obj = array.getJSONObject(i);
                    Publicacion p = mapPublicacion(obj);

                    publis.add(p);
                }
                return  publis;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public ArrayList<Coleccion> getColeccionesUsuario(int id_usuario){

        HttpURLConnection con = null;
        ArrayList<Coleccion> colec = new ArrayList<>();

        try {
            URL url = new URL("http://10.0.2.2:8080/api/wikz/operaciones/getColeccionesUsuario");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                response.append(line.trim());
            }

            if (code == 200) {
                JSONArray array = new JSONArray(response.toString());

                for(int i = 0; i < array.length(); i++){

                    JSONObject obj = array.getJSONObject(i);
                    Coleccion c = mapColeccion(obj);

                    colec.add(c);
                }
                return  colec;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
