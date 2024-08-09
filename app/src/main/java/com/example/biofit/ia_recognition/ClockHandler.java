package com.example.biofit.ia_recognition;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biofit.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ClockHandler implements RecognitionListener {

    private Context context;
    private MediaPlayer mediaPlayer;
    private TextView tvRecognizedText;
    private TextToSpeech textToSpeech;

    public ClockHandler(Context context, TextView tvRecognizedText) {
        this.context = context;
        this.tvRecognizedText = tvRecognizedText;

        // Inicializa TextToSpeech
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int langResult = textToSpeech.setLanguage(new Locale("es", "ES"));
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(context, "El idioma español no es soportado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Error al inicializar TextToSpeech", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // No usado
    }

    @Override
    public void onBeginningOfSpeech() {
        // No usado
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        // No usado
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // No usado
    }

    @Override
    public void onEndOfSpeech() {
        // No usado
    }

    @Override
    public void onError(int error) {
        String errorMessage;
        switch (error) {
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "Tiempo de red agotado";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "Error de red";
                break;
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "Error de audio";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "Error del servidor";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "Error del cliente";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorMessage = "Tiempo de espera del discurso";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                errorMessage = "No hay coincidencia";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "Reconocedor ocupado";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "Permisos insuficientes";
                break;
            default:
                errorMessage = "Error desconocido";
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
        // No usado
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        // No usado
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

            // Reproduce la hora actual por voz
            textToSpeech.speak("La hora actual es " + currentTime, TextToSpeech.QUEUE_FLUSH, null, null);

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
