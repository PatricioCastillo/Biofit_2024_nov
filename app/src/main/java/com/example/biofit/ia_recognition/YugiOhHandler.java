package com.example.biofit.ia_recognition;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class YugiOhHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;

    public YugiOhHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.statusTextView = statusTextView;
    }

    public void handleVoiceCommand(ArrayList<String> matches) {

        YugiOhTemp1(matches);
    }

    private void activatePython(String urlString) {
        mainHandler.post(() -> statusTextView.setText("Conectando..."));

        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");  // Asegúrate de usar GET
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

    public void YugiOhTemp1(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para reproducir capítulos de Yu-Gi-Oh
            if (recognizedText.equalsIgnoreCase("yugi capítulo 1 temporada 1")
                    || recognizedText.equalsIgnoreCase("yugioh capítulo 1 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x01%20El%20corazón%20de%20los%20naipes");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 2 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x02%20La%20suerte%20esta%20echada");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 3 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x03%20Viaje%20al%20reino%20de%20los%20duelos");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 4 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x04%20En%20el%20nido%20de%20las%20avispas%20(1)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 4 temporada 1 parte 2")) {
                activatePython("http://192.168.100.26:5005/play?track=1x04%20En%20el%20nido%20de%20las%20avispas");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 5 temporada 1 parte 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x05%20La%20ultima%20polilla%20(1)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 5 temporada 1 parte 2")) {
                activatePython("http://192.168.100.26:5005/play?track=1x05%20La%20ultima%20polilla");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 6 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x06%20El%20primer%20duelo");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 7 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x07%20Ataque%20desde%20las%20profundidades");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 8 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x08%20Todo%20es%20relativo");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 9 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x09%20Duelo%20con%20un%20fantasma");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 10 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x10%20Olvida%20al%20fantasma");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 11 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x11%20El%20maniático%20del%20duelo");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 12 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x12%20La%20lucha%20con%20ojos%20rojos");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 13 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x13%20El%20espíritu%20Maligno%20de%20la%20Sortija");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 14 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x14%20La%20luz%20al%20final%20del%20túnel");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 15 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x15%20Lo%20intimido%20y%20lo%20venció");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 16 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x16%20Las%20cicatrices%20de%20la%20derrota");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 17 temporada 1 parte 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x17%20La%20arena%20de%20las%20almas%20perdidas%20(Parte%201)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 17 temporada 1 parte 2")) {
                activatePython("http://192.168.100.26:5005/play?track=1x17%20La%20arena%20de%20las%20almas%20perdidas%20(Parte%202)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 18 temporada 1 parte 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x18%20La%20arena%20de%20las%20almas%20perdidas%20(Parte%201)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 18 temporada 1 parte 2")) {
                activatePython("http://192.168.100.26:5005/play?track=1x18%20La%20arena%20de%20las%20almas%20perdidas%20(Parte%202)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 19 temporada 1 parte 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x19%20Duelo%20de%20Doble%20Problema%20(Parte%201)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 19 temporada 1 parte 2")) {
                activatePython("http://192.168.100.26:5005/play?track=1x19%20Duelo%20de%20Doble%20Problema%20(Parte%202)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 19 temporada 1 parte 3")) {
                activatePython("http://192.168.100.26:5005/play?track=1x19%20Duelo%20de%20Doble%20Problema%20(Parte%203)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 20 temporada 1 parte 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x20%20Duelo%20de%20Doble%20Problema%20(Parte%202)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 21 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x21%20Prueba%20Final%20(Parte%201)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 22 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x22%20Prueba%20Final%20(Parte%202)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 23 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x23%20Prueba%20Final%20(Parte%203)");
            } else if (recognizedText.equalsIgnoreCase("yugi capítulo 24 temporada 1")) {
                activatePython("http://192.168.100.26:5005/play?track=1x24%20Prueba%20Final%20(Parte%203)");
            }
        }
    }

}
