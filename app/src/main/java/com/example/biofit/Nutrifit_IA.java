package com.example.biofit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.biofit.ia_recognition.AccountHandler;
import com.example.biofit.ia_recognition.ClockHandler;
import com.example.biofit.ia_recognition.FileHandler;
import com.example.biofit.ia_recognition.MainHandler;
import com.example.biofit.ia_recognition.MalwareHandler;
import com.example.biofit.ia_recognition.MusicHandler;
import com.example.biofit.ia_recognition.OfficeHandler;
import com.example.biofit.ia_recognition.RenalHandler;
import com.example.biofit.ia_recognition.ServerHandler;
import com.example.biofit.ia_recognition.WebHandler;
import com.example.biofit.ia_recognition.YugiOhHandler;

import java.util.ArrayList;
import java.util.Calendar;

public class Nutrifit_IA extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private ClockHandler clockHandler;
    private MainHandler mainHandler;
    private RenalHandler renalHandler;
    private ServerHandler serverHandler;
    private MusicHandler musicHandler;
    private YugiOhHandler yugiOhHandler;
    private WebHandler webHandler;
    private MalwareHandler malwareHandler;
    private OfficeHandler officeHandler;
    private FileHandler fileHandler;
    private AccountHandler accountHandler;
    private TextView tvRecognizedText;


    private Handler handler;
    private Runnable timeCheckRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrifit_ia);

        tvRecognizedText = findViewById(R.id.tvRecognizedText);
        clockHandler = new ClockHandler(this, tvRecognizedText);
        mainHandler = new MainHandler(this);
        renalHandler = new RenalHandler(this);
        serverHandler = new ServerHandler(this, tvRecognizedText);
        musicHandler = new MusicHandler(this, tvRecognizedText);
        yugiOhHandler = new YugiOhHandler(this, tvRecognizedText);
        webHandler = new WebHandler(this, tvRecognizedText);
        malwareHandler = new MalwareHandler(this, tvRecognizedText);
        officeHandler = new OfficeHandler(this, tvRecognizedText);
        fileHandler = new FileHandler(this, tvRecognizedText);
        accountHandler = new AccountHandler(this, tvRecognizedText);

        // Verificar y solicitar permisos
        checkPermissions();

        // Configurar el Handler y el Runnable para verificar la hora
        handler = new Handler();
        timeCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkTime();
                handler.postDelayed(this, 60000); // Verificar cada minuto
            }
        };
        handler.post(timeCheckRunnable);

        // Iniciar el reconocimiento de voz continuo
        startListening();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListening();
            } else {
                Toast.makeText(this, "Permiso para usar el micrófono denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startListening() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        setupSpeechRecognizer();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

        speechRecognizer.startListening(intent);
    }

    private void setupSpeechRecognizer() {
        speechRecognizer.setRecognitionListener(new android.speech.RecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);

                    // Mostrar el texto reconocido para depuración
                    Log.d("SpeechRecognition", "Texto reconocido: " + recognizedText);
                    tvRecognizedText.setText(recognizedText);

                    // Implementando Inteligencia de Voz
                    clockHandler.onResults(results);
                    mainHandler.onResults(results);
                    renalHandler.onResults(results);
                    serverHandler.handleVoiceCommand(matches);
                    musicHandler.handleVoiceCommand(matches);
                    yugiOhHandler.handleVoiceCommand(matches);
                    webHandler.handleVoiceCommand(matches);
                    malwareHandler.handleVoiceCommand(matches);
                    officeHandler.handleVoiceCommand(matches);
                    fileHandler.handleVoiceCommand(matches);
                    accountHandler.handleCommand(recognizedText); // Llama a handleCommand con el texto reconocido
                } else {
                    Log.d("SpeechRecognition", "No se reconoció ningún texto.");
                }
                // Reiniciar la escucha después de procesar resultados
                startListening();
            }

            @Override
            public void onError(int error) {
                Log.e("SpeechRecognition", "Error de reconocimiento: " + error);
                // Reiniciar la escucha si ocurre un error
                startListening();
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
            public void onPartialResults(Bundle partialResults) {
                // No usado
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // No usado
            }
        });
    }

    private void checkTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 19 && minute == 0) {
            clockHandler.playAudio(R.raw.medicacion);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        handler.removeCallbacks(timeCheckRunnable);
        clockHandler.releaseMediaPlayer();
    }
}
