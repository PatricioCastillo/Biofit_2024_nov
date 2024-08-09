package com.example.biofit.ia_recognition;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ServerHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;
    private boolean isRecognizerBusy = false;  // Flag para controlar el estado del reconocedor
    private static final long COMMAND_DELAY_MS = 2000; // Retraso entre comandos
    private long lastCommandTime = 0;

    public ServerHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.statusTextView = statusTextView;
    }

    public void handleVoiceCommand(ArrayList<String> matches) {
        long currentTime = System.currentTimeMillis();
        if (isRecognizerBusy || currentTime - lastCommandTime < COMMAND_DELAY_MS) {
            // Si el reconocedor está ocupado o el tiempo desde el último comando es menor al retraso, retorna sin hacer nada
            return;
        }

        if (matches != null && !matches.isEmpty()) {
            isRecognizerBusy = true;
            lastCommandTime = currentTime; // Actualizar el tiempo del último comando
            String recognizedText = matches.get(0);

            // Comandos Unity
            if (recognizedText.equalsIgnoreCase("activar servicio Unity")) {
                sendRequest("http://192.168.100.23:5001/activate-unity", "POST");
            } else if (recognizedText.equalsIgnoreCase("activar desaparición")) {
                sendRequest("http://192.168.100.23:5001/hide-object", "POST");
            } else if (recognizedText.equalsIgnoreCase("activar aparición")) {
                sendRequest("http://192.168.100.23:5001/show-object", "POST");

                // Comandos Python
            } else if (recognizedText.equalsIgnoreCase("Reproducir Pelicula")) {
                sendRequest("http://192.168.100.23:5003/activate-python", "POST");

                // Comandos Web
            } else if (recognizedText.equalsIgnoreCase("nutrición")) {
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=activate-web", "GET");
            } else if (recognizedText.equalsIgnoreCase("menú") ||
                    recognizedText.equalsIgnoreCase("abrir menú")) {
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=deactivate-web", "GET");
            } else if (recognizedText.equalsIgnoreCase("pacientes")) {
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=pacientes-web", "GET");
            } else if (recognizedText.equalsIgnoreCase("análisis")) {
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=analisis-web", "GET");
            } else if (recognizedText.equalsIgnoreCase("fitness")) {
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=fitness-web", "GET");
            } else if (recognizedText.equalsIgnoreCase("descargar")) {
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=descargar-web", "GET");
            }else if (recognizedText.equalsIgnoreCase("lista de pacientes")||
                    recognizedText.equalsIgnoreCase("listado de pacientes")){
                sendRequest("https://dashblog.cl/biofit/polling/server.php?command=lista-pacientes", "GET");
            }



            // Marcar el reconocedor como no ocupado después del retraso
            new Handler(Looper.getMainLooper()).postDelayed(() -> isRecognizerBusy = false, COMMAND_DELAY_MS);
        }
    }

    private void sendRequest(String urlString, String method) {
        mainHandler.post(() -> statusTextView.setText("Conectando..."));

        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    mainHandler.post(() -> statusTextView.setText("Comando enviado con éxito"));
                } else {
                    mainHandler.post(() -> statusTextView.setText("Error al enviar el comando"));
                }
            } catch (IOException e) {
                mainHandler.post(() -> statusTextView.setText("Error de conexión"));
            }
        }).start();
    }
}
