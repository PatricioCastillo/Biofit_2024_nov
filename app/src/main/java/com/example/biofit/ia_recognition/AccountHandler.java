package com.example.biofit.ia_recognition;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.example.biofit.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class AccountHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;
    private Random random = new Random();
    private MediaPlayer mediaPlayer;

    public boolean accesoUser = false;

    private String usuario = "";
    private String contrasena = "";
    private String categoria = "";

    private int step = 0; // Paso actual en el proceso de recolección de datos

    public AccountHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.statusTextView = statusTextView;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void handleCommand(String recognizedText) {
        if (recognizedText != null && !recognizedText.isEmpty()) {
            // Verificar si el comando es para registrar cuenta
            if (registrar(recognizedText)) {
                accesoUser = true;
                mainHandler.post(() -> statusTextView.setText("Diga el nombre de usuario"));
                return;
            }

            if (accesoUser) {
                switch (step) {
                    case 0:
                        // Solicitar nombre de usuario
                        usuario = recognizedText;
                        mainHandler.post(() -> statusTextView.setText("Diga la contraseña"));
                        step++;
                        break;
                    case 1:
                        // Solicitar contraseña
                        contrasena = recognizedText;
                        mainHandler.post(() -> statusTextView.setText("Diga la categoría"));
                        step++;
                        break;
                    case 2:
                        // Solicitar categoría
                        categoria = recognizedText;
                        sendRequest("http://192.168.100.26:5008/addUser");
                        step++;
                        break;
                    case 3:
                        // Mensaje final y reiniciar el proceso
                        mainHandler.post(() -> statusTextView.setText("Datos ya enviados. Reinicie el proceso si es necesario."));
                        reset();
                        break;
                    default:
                        mainHandler.post(() -> statusTextView.setText("Datos ya enviados o no válidos"));
                        break;
                }
            } else {
                mainHandler.post(() -> statusTextView.setText("Acceso no autorizado. Diga 'registrar cuenta' para comenzar."));
            }
        } else {
            mainHandler.post(() -> statusTextView.setText("Texto no reconocido. Asegúrate de decir correctamente el nombre, la contraseña y la categoría."));
        }
    }

    private void sendRequest(String urlString) {
        mainHandler.post(() -> statusTextView.setText("Conectando..."));

        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                // Construir el JSON con los datos recopilados
                String jsonInputString = String.format(
                        "{\"usuario\": \"%s\", \"contrasena\": \"%s\", \"categoria\": \"%s\"}",
                        usuario, contrasena, categoria);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    mainHandler.post(() -> statusTextView.setText("Datos enviados correctamente!"));
                } else {
                    mainHandler.post(() -> statusTextView.setText("Error al enviar los datos: " + responseCode));
                }
            } catch (IOException e) {
                mainHandler.post(() -> statusTextView.setText("Error de conexión: " + e.getMessage()));
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    // Helper method to play a random audio from a list of resources
    private void playRandomAudio(int... audioResIds) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        int randomIndex = random.nextInt(audioResIds.length);
        mediaPlayer = MediaPlayer.create(context, audioResIds[randomIndex]);
        mediaPlayer.start();
    }

    private void reset() {
        // Reiniciar el estado para permitir nuevos datos
        usuario = "";
        contrasena = "";
        categoria = "";
        step = 0;
        accesoUser = false;
    }

    private boolean registrar(String text) {
        return text.equalsIgnoreCase("registrar cuenta") ||
                text.equalsIgnoreCase("guardar cuenta");
    }
}
