package com.example.biofit.ia_recognition;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FileHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;

    public FileHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.statusTextView = statusTextView;
    }

    public void handleVoiceCommand(ArrayList<String> matches) {
        androidUnidadUno(matches);
    }

    private void activatePython(String urlString) {
        mainHandler.post(() -> statusTextView.setText("Conectando..."));

        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    mainHandler.post(() -> statusTextView.setText("Servicio activado!"));
                } else {
                    mainHandler.post(() -> statusTextView.setText("Error al activar el servicio"));
                }
            } catch (IOException e) {
                mainHandler.post(() -> statusTextView.setText("Error de conexión"));
            }
        }).start();
    }


    public void androidUnidadUno(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para file móvil
            if (recognizedText.equalsIgnoreCase("desarrollo móvil unidad 1") ||
                    recognizedText.equalsIgnoreCase("dash abre desarrollo móvil 1") ||
                    recognizedText.equalsIgnoreCase("dash abre desarrollo móvil unidad 1")) {
                activatePython("http://192.168.100.26:5003/movil-uno");

            } else if (recognizedText.equalsIgnoreCase("hablemos del ide") ||
                    recognizedText.equalsIgnoreCase("android studio ide") ||
                    recognizedText.equalsIgnoreCase("hablemos del ID") ||
                    recognizedText.equalsIgnoreCase("hablemos de líder") ||
                    recognizedText.equalsIgnoreCase("android studio ID")) {
                activatePython("http://192.168.100.26:5003/movil-ide");

            } else if (recognizedText.equalsIgnoreCase("desarrollo móvil unidad 2") ||
                    recognizedText.equalsIgnoreCase("dash abre desarrollo móvil 2") ||
                    recognizedText.equalsIgnoreCase("dash abre desarrollo móvil unidad 2")) {
                activatePython("http://192.168.100.26:5003/movil-dos");

            } else if (recognizedText.equalsIgnoreCase("containers en android") ||
                    recognizedText.equalsIgnoreCase("container en android") ||
                    recognizedText.equalsIgnoreCase("hablemos de containers") ||
                    recognizedText.equalsIgnoreCase("hablemos de container") ||
                    recognizedText.equalsIgnoreCase("contenedores en android")) {
                activatePython("http://192.168.100.26:5003/movil-containers");

            } else if (recognizedText.equalsIgnoreCase("elementos de la interfaz") ||
                    recognizedText.equalsIgnoreCase("hablemos de elementos de la interfaz") ||
                    recognizedText.equalsIgnoreCase("elementos interfaz")) {
                activatePython("http://192.168.100.26:5003/movil-elements");

            } else if (recognizedText.equalsIgnoreCase("introducción a android") ||
                    recognizedText.equalsIgnoreCase("hablemos de introducción a android") ||
                    recognizedText.equalsIgnoreCase("intro android")) {
                activatePython("http://192.168.100.26:5003/movil-intro");

            } else if (recognizedText.equalsIgnoreCase("layouts en android") ||
                    recognizedText.equalsIgnoreCase("layout en android") ||
                    recognizedText.equalsIgnoreCase("vamos a ver layouts en android") ||
                    recognizedText.equalsIgnoreCase("vamos a ver layout en android") ||
                    recognizedText.equalsIgnoreCase("hablemos de layouts en android") ||
                    recognizedText.equalsIgnoreCase("layouts android")) {
                activatePython("http://192.168.100.26:5003/movil-layout");

            } else if (recognizedText.equalsIgnoreCase("widgets en android") ||
                    recognizedText.equalsIgnoreCase("hablemos de widgets en android") ||
                    recognizedText.equalsIgnoreCase("widgets android")) {
                activatePython("http://192.168.100.26:5003/movil-widgets");

            }
        }
    }

}
