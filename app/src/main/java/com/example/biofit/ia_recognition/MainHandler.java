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
import java.util.Random;

public class MainHandler implements RecognitionListener {

    private Context context;
    private MediaPlayer mediaPlayer;
    private Random random = new Random();

    public MainHandler(Context context) {
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

    public void handleCommand(String recognizedText) {
        // Check and handle various commands
        if (isGreetingCommand(recognizedText)) {
            playRandomAudio(R.raw.buenosdias, R.raw.hola, R.raw.saludos); // Example audio files
        } else if (estoyBien(recognizedText)) {
            playRandomAudio(R.raw.mealegro, R.raw.bien, R.raw.superbien); // Example audio files
        } else if (puedoHacer(recognizedText)) {
            playRandomAudio(R.raw.puedohacer, R.raw.quehaceryo, R.raw.quehacer); // Example audio files
        }else if (quienEres(recognizedText)) {
            playRandomAudio(R.raw.soydash, R.raw.yosoydash, R.raw.dash); // Example audio files
        }else if (noSe(recognizedText)) {
            playRandomAudio(R.raw.nose, R.raw.nolose, R.raw.nos); // Example audio files

        } else {
            Toast.makeText(context, "Comando no reconocido", Toast.LENGTH_SHORT).show();
        }
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

    private boolean isGreetingCommand(String text) {
        return text.equalsIgnoreCase("Hola") ||
                text.equalsIgnoreCase("Hola hola") ||
                text.equalsIgnoreCase("buenos días") ||
                text.equalsIgnoreCase("hola dash") ||
                text.equalsIgnoreCase("hola dash cómo estás") ||
                text.equalsIgnoreCase("buenos días dash") ||
                text.equalsIgnoreCase("qué tal") ||
                text.equalsIgnoreCase("hola cómo estás") ||
                text.equalsIgnoreCase("cómo estás") ||
                text.equalsIgnoreCase("cómo estás dash") ||
                text.equalsIgnoreCase("hey dash") ||
                text.equalsIgnoreCase("hey dash qué cuentas") ||
                text.equalsIgnoreCase("saludos") ||
                text.equalsIgnoreCase("buenas") ||
                text.equalsIgnoreCase("buenas tardes") ||
                text.equalsIgnoreCase("buenas noches") ||
                text.equalsIgnoreCase("hola a todos") ||
                text.equalsIgnoreCase("qué pasa") ||
                text.equalsIgnoreCase("qué hay") ||
                text.equalsIgnoreCase("qué tal dash") ||
                text.equalsIgnoreCase("qué tal hoy") ||
                text.equalsIgnoreCase("hey") ||
                text.equalsIgnoreCase("hey qué tal") ||
                text.equalsIgnoreCase("hola qué tal") ||
                text.equalsIgnoreCase("hola qué hay") ||
                text.equalsIgnoreCase("buenos días qué tal") ||
                text.equalsIgnoreCase("buenos días qué hay") ||
                text.equalsIgnoreCase("hola qué cuentas") ||
                text.equalsIgnoreCase("cómo va todo") ||
                text.equalsIgnoreCase("todo bien") ||
                text.equalsIgnoreCase("cómo va");
    }

    private boolean estoyBien(String text) {
        return text.equalsIgnoreCase("bien gracias") ||
                text.equalsIgnoreCase("bien") ||
                text.equalsIgnoreCase("que bueno") ||
                text.equalsIgnoreCase("todo bien") ||
                text.equalsIgnoreCase("súper bien") ||
                text.equalsIgnoreCase("estoy bien") ||
                text.equalsIgnoreCase("estoy muy bien") ||
                text.equalsIgnoreCase("estoy bien gracias") ||
                text.equalsIgnoreCase("estoy muy bien gracias") ||
                text.equalsIgnoreCase("muy bien") ||
                text.equalsIgnoreCase("muy bien gracias") ||
                text.equalsIgnoreCase("gracias estoy bien") ||
                text.equalsIgnoreCase("gracias estoy muy bien") ||
                text.equalsIgnoreCase("bien estoy") ||
                text.equalsIgnoreCase("muy bien estoy") ||
                text.equalsIgnoreCase("gracias bien") ||
                text.equalsIgnoreCase("gracias muy bien") ||
                text.equalsIgnoreCase("muy bien gracias estoy");
    }

    private boolean puedoHacer(String text) {
        return text.equalsIgnoreCase("qué puedes hacer") ||
                text.equalsIgnoreCase("qué puedes hacer por mí") ||
                text.equalsIgnoreCase("qué puedes hacer para mí") ||
                text.equalsIgnoreCase("qué puedes hacer para ayudar") ||
                text.equalsIgnoreCase("qué haces") ||
                text.equalsIgnoreCase("qué sabes hacer") ||
                text.equalsIgnoreCase("qué más sabes hacer") ||
                text.equalsIgnoreCase("qué puedes hacer hoy") ||
                text.equalsIgnoreCase("qué hay para hacer") ||
                text.equalsIgnoreCase("qué puedes hacer aquí") ||
                text.equalsIgnoreCase("cuáles son tus funciones") ||
                text.equalsIgnoreCase("qué tareas puedes realizar") ||
                text.equalsIgnoreCase("qué habilidades tienes") ||
                text.equalsIgnoreCase("qué puedes hacer en esta aplicación") ||
                text.equalsIgnoreCase("qué puedo hacer contigo") ||
                text.equalsIgnoreCase("cómo puedes ayudar") ||
                text.equalsIgnoreCase("qué tareas puedes llevar a cabo") ||
                text.equalsIgnoreCase("cuál es tu propósito") ||
                text.equalsIgnoreCase("qué capacidades tienes") ||
                text.equalsIgnoreCase("cómo puedes asistirme") ||
                text.equalsIgnoreCase("qué servicios ofreces") ||
                text.equalsIgnoreCase("a ver nosé dime qué puedes hacer") ||
                text.equalsIgnoreCase("a ver dime que puedes hacer") ||
                text.equalsIgnoreCase("no sé a ver dime que puedes hacer") ||
                text.equalsIgnoreCase("no sé dime que puedes hacer") ||
                text.equalsIgnoreCase("qué opciones tengo contigo");
    }

    private boolean quienEres(String text) {
        return text.equalsIgnoreCase("quién eres") ||
                text.equalsIgnoreCase("qué eres tú") ||
                text.equalsIgnoreCase("quién eres tú") ||
                text.equalsIgnoreCase("qué eres") ||
                text.equalsIgnoreCase("cómo te llamas") ||
                text.equalsIgnoreCase("cuál es tu nombre") ||
                text.equalsIgnoreCase("qué haces aquí") ||
                text.equalsIgnoreCase("cuál es tu propósito") ||
                text.equalsIgnoreCase("qué haces") ||
                text.equalsIgnoreCase("por qué estás aquí") ||
                text.equalsIgnoreCase("qué puedes hacer") ||
                text.equalsIgnoreCase("para qué sirves") ||
                text.equalsIgnoreCase("cuál es tu función") ||
                text.equalsIgnoreCase("qué eres capaz de hacer") ||
                text.equalsIgnoreCase("qué puedes hacer por mí") ||
                text.equalsIgnoreCase("qué puedes hacer para ayudar") ||
                text.equalsIgnoreCase("qué tareas puedes realizar") ||
                text.equalsIgnoreCase("qué habilidades tienes") ||
                text.equalsIgnoreCase("cómo puedes asistirme") ||
                text.equalsIgnoreCase("qué servicios ofreces") ||
                text.equalsIgnoreCase("qué opciones tengo contigo") ||
                text.equalsIgnoreCase("qué puedes hacer en esta aplicación") ||
                text.equalsIgnoreCase("cómo me puedes ayudar") ||
                text.equalsIgnoreCase("qué puedes hacer aquí") ||
                text.equalsIgnoreCase("qué tareas puedes llevar a cabo") ||
                text.equalsIgnoreCase("cuál es tu propósito aquí") ||
                text.equalsIgnoreCase("qué capacidades tienes") ||
                text.equalsIgnoreCase("qué funciones tienes");
    }

    private boolean noSe(String text) {
        return text.equalsIgnoreCase("no sé") ||
                text.equalsIgnoreCase("no estoy seguro de que hacer") ||
                text.equalsIgnoreCase("no tengo idea") ||
                text.equalsIgnoreCase("no lo sé") ||
                text.equalsIgnoreCase("no sé qué hacer") ||
                text.equalsIgnoreCase("no sé qué hacer hoy") ||
                text.equalsIgnoreCase("no sé qué") ||
                text.equalsIgnoreCase("no sé no sé");
    }




    private void openApp(String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Aplicación no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
