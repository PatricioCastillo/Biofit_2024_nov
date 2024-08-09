package com.example.biofit.ia_recognition;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biofit.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ClockHandler implements RecognitionListener {

    private Context context;
    private MediaPlayer mediaPlayer;
    private TextView tvRecognizedText;

    public ClockHandler(Context context, TextView tvRecognizedText) {
        this.context = context;
        this.tvRecognizedText = tvRecognizedText;
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
            tvRecognizedText.setText(recognizedText); // Muestra el texto reconocido en el TextView

            if (obtenerHora(recognizedText)) {
                // Solo reproducir audio a las 19:00
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                if (hour == 19 && minute == 0) {
                    playAudio(R.raw.medicacion);
                }
            }
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

    private boolean obtenerHora(String text) {
        if (text.equalsIgnoreCase("qué hora es") || text.equalsIgnoreCase("dash qué hora es") ||
                text.equalsIgnoreCase("dime la hora") || text.equalsIgnoreCase("qué hora tienes") ||
                text.equalsIgnoreCase("dash dime la hora") || text.equalsIgnoreCase("hora")) {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Actualiza el TextView con la hora actual
            String currentTime = String.format("%02d:%02d", hour, minute);
            tvRecognizedText.setText(currentTime);

            return true; // Devuelve verdadero si se pregunta la hora
        }
        return false;
    }

    public void playAudio(int resId) {
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
