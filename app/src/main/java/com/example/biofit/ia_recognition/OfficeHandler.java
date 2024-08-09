package com.example.biofit.ia_recognition;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OfficeHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;

    public OfficeHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.statusTextView = statusTextView;
    }

    public void handleVoiceCommand(ArrayList<String> matches) {

        officePowerPoint(matches);
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


    public void officePowerPoint(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para el web
            if (recognizedText.equalsIgnoreCase("presentar")) {
                activatePython("http://192.168.100.26:5004/press-efecinco");
            }else if (recognizedText.equalsIgnoreCase("siguiente")){
                activatePython("http://192.168.100.26:5004/next");
            }else if (recognizedText.equalsIgnoreCase("atrás")){
                activatePython("http://192.168.100.26:5004/prev");
            }
            else if (recognizedText.equalsIgnoreCase("cerrar")){
                activatePython("http://192.168.100.26:5004/altf");
            }
        }
    }

}
