package com.example.biofit.ia_recognition;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WebHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;

    public WebHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.statusTextView = statusTextView;
    }

    public void handleVoiceCommand(ArrayList<String> matches) {

        Web(matches);
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


    public void Web(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para el web
            if (recognizedText.equalsIgnoreCase("abre biofit")) {
                activatePython("http://192.168.100.26:5006/abre-biofit");
            } else if (recognizedText.equalsIgnoreCase("abre github")) {
                activatePython("http://192.168.100.26:5006/abre-github");
            } else if (recognizedText.equalsIgnoreCase("abre google")) {
                activatePython("http://192.168.100.26:5006/abre-google");
            }
        }
    }

}
