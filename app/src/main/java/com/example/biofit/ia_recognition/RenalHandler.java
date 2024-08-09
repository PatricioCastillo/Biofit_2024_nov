package com.example.biofit.ia_recognition;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.example.biofit.R;

import java.util.ArrayList;

public class RenalHandler implements RecognitionListener {

    private Context context;
    private MediaPlayer mediaPlayer;

    public RenalHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // Not used
    }

    @Override
    public void onBeginningOfSpeech() {
        // Not used
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        // Not used
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // Not used
    }

    @Override
    public void onEndOfSpeech() {
        // Not used
    }

    @Override
    public void onError(int error) {
        String errorMessage;
        switch (error) {
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "Network error";
                break;
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "Audio error";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "Server error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "Client error";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorMessage = "Speech timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                errorMessage = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "Recognizer busy";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "Insufficient permissions";
                break;
            default:
                errorMessage = "Unknown error";
                break;
        }
        Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null) {
            String recognizedText = matches.get(0);
            handleCommand(recognizedText);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        // Not used
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        // Not used
    }


    // Dietas Bajas en Potasio, Fosoforo y Sodio
    public void handleCommand(String recognizedText) {
        // Check and handle various commands
        if (bajoPotasio(recognizedText)) {
            playAudio(R.raw.dietapotasio);
        } else {
            Toast.makeText(context, "Comando no reconocido", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean bajoPotasio(String text) {
        return text.equalsIgnoreCase("qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("dash qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("dash dime qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("cuéntame qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("dieta baja en potasio") ||
                text.equalsIgnoreCase("por favor dash dime qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("por favor dime qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("dash define dieta baja en potasio") ||
                text.equalsIgnoreCase("define dieta baja en potasio") ||
                text.equalsIgnoreCase("dime qué es una dieta baja en potasio") ||
                text.equalsIgnoreCase("dash qué se entiende por dieta baja en potasio") ||
                text.equalsIgnoreCase("qué alimentos debo evitar en una dieta baja en potasio") ||
                text.equalsIgnoreCase("cuáles son los alimentos recomendados para una dieta baja en potasio") ||
                text.equalsIgnoreCase("cómo seguir una dieta baja en potasio") ||
                text.equalsIgnoreCase("ejemplos de comidas bajas en potasio") ||
                text.equalsIgnoreCase("recomendaciones para una dieta baja en potasio") ||
                text.equalsIgnoreCase("dieta baja en potasio para mayores de edad") ||
                text.equalsIgnoreCase("alimentos bajos en potasio para la dieta") ||
                text.equalsIgnoreCase("dame una dieta baja en potasio") ||
                text.equalsIgnoreCase("dash dame una dieta baja en potasio") ||
                text.equalsIgnoreCase("hola dash dime qué dieta me sirve para bajar el potasio") ||
                text.equalsIgnoreCase("dash planifica una dieta baja en potasio") ||
                text.equalsIgnoreCase("dash recomienda una dieta baja en potasio") ||
                text.equalsIgnoreCase("cómo hacer una dieta baja en potasio");
    }




    private void openApp(String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Aplicación no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio(int resId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId);
        } else {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(context, resId);
        }
        mediaPlayer.start();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
