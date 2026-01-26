package com.example.wikz;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Api {

    public void añadirUsuario(String nombre, String email, String pass, String bio ,byte[] foto_perfil){

        new Thread(() -> {

            try {
                URL url = new URL("http://192.130.0.138:8000/API_REST/operaciones/android");

                //dar permisos en el manifest y colocar la linea a true

                //abrir conexion
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                //indicar el metodo
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true); //Indica que se va a escribir el body y no se recoge de otro sitio

                //si es un get, se cambia el Content-Type por Accept
                //con.setRequestProperty("Accept", "application/json");

                //Crear Json
                JSONObject json = new JSONObject();
                json.put("nombre",nombre);
                json.put("email",email);
                json.put("pass", pass);
                json.put("bio",bio );
                json.put("foto_perfil",foto_perfil );



                System.out.println(json);

                //Enviar body
                try(OutputStream os = con.getOutputStream()){

                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));

                }catch (Exception e){
                    System.out.println("Error");
                }

                //codigo de la respuesta por si da error y sacarlo en un Log
                int code = con.getResponseCode();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
