package com.example.biofit.ia_recognition;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;
import com.example.biofit.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainHandler implements RecognitionListener {

    private Context context;
    private MediaPlayer mediaPlayer;
    private Random random = new Random();
    private SpeechRecognizer speechRecognizer;
    private JSONObject responsesJson;

    public MainHandler(Context context) {
        this.context = context;
        initializeSpeechRecognizer();
        loadResponsesJson();
    }

    private void initializeSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(this);
    }

    private void loadResponsesJson() {
        try {
            InputStream inputStream = context.getAssets().open("mainResponses.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            responsesJson = new JSONObject(json);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainHandler", "Error al cargar el archivo JSON", e);
            Toast.makeText(context, "Error al cargar el archivo JSON", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // No action needed
    }

    @Override
    public void onBeginningOfSpeech() {
        // No action needed
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        // No action needed
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // No action needed
    }

    @Override
    public void onEndOfSpeech() {
        // No action needed
    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorMessage(error);
        Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);
            handleCommand(recognizedText);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        // No action needed
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        // No action needed
    }

    private void handleCommand(String recognizedText) {
        if (isInList(recognizedText, "greetings")) {
            playRandomAudio(R.raw.buenosdias, R.raw.hola, R.raw.saludos);
        } else if (isInList(recognizedText, "responses")) {
            playRandomAudio(R.raw.mealegro, R.raw.bien, R.raw.superbien);
        } else if (isInList(recognizedText, "questions", "whatCanDo")) {
            playRandomAudio(R.raw.puedohacer, R.raw.quehaceryo, R.raw.quehacer);
        } else if (isInList(recognizedText, "questions", "whoAreYou")) {
            playRandomAudio(R.raw.soydash, R.raw.yosoydash, R.raw.dash);
        } else if (isInList(recognizedText, "questions", "dontKnow")) {
            playRandomAudio(R.raw.nose, R.raw.nolose, R.raw.nos);
        }else if (isInList(recognizedText, "questions", "youreDoing")) {
            playRandomAudio(R.raw.nada, R.raw.broma, R.raw.estoyhaciendo);
        } else {
            // Reproduce un audio de respuesta por defecto si lo deseas
            // playRandomAudio(R.raw.losiento, R.raw.losientodos, R.raw.losientotres);
        }
    }

    private void playRandomAudio(int... audioResIds) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        int randomIndex = random.nextInt(audioResIds.length);
        mediaPlayer = MediaPlayer.create(context, audioResIds[randomIndex]);
        mediaPlayer.setOnCompletionListener(mp -> {
            // No es necesario reiniciar el reconocimiento de voz aquí
        });
        mediaPlayer.start();
    }

    private String getErrorMessage(int error) {
        switch (error) {
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT: return "Network timeout";
            case SpeechRecognizer.ERROR_NETWORK: return "Network error";
            case SpeechRecognizer.ERROR_AUDIO: return "Audio error";
            case SpeechRecognizer.ERROR_SERVER: return "Server error";
            case SpeechRecognizer.ERROR_CLIENT: return "Client error";
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: return "Speech timeout";
            case SpeechRecognizer.ERROR_NO_MATCH: return "No match";
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY: return "Recognizer busy";
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: return "Insufficient permissions";
            default: return "Unknown error";
        }
    }

    private boolean isInList(String text, String arrayName) {
        return isInList(text, arrayName, null);
    }

    private boolean isInList(String text, String arrayName, String subArrayName) {
        try {
            JSONArray array;
            if (subArrayName != null) {
                array = responsesJson.getJSONObject(arrayName).getJSONArray(subArrayName);
            } else {
                array = responsesJson.getJSONArray(arrayName);
            }
            for (int i = 0; i < array.length(); i++) {
                if (text.equalsIgnoreCase(array.getString(i))) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
