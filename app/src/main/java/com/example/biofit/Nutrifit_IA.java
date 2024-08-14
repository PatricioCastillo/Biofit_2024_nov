package com.example.biofit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
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
import java.util.Random;

public class Nutrifit_IA extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private boolean isListening = false; // Bandera para verificar el estado del reconocimiento de voz
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
    private MediaPlayer mediaPlayer;
    private Random random = new Random();

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

        LottieAnimationView animatedButton = findViewById(R.id.animatedButton);

        // Configurar el comportamiento de clic
        animatedButton.setOnClickListener(v -> {
            if (isListening) {
                stopListening(); // Detener la escucha si ya está en curso
            } else {
                startListening(); // Iniciar la escucha si no está en curso
            }
        });
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
                if (!isListening) {
                    startListening();
                } else {
                    tvRecognizedText.setText("El reconocimiento de voz ya está en curso.");
                }
            } else {
                Toast.makeText(this, "Permiso para usar el micrófono denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startListening() {
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            setupSpeechRecognizer();
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

        speechRecognizer.startListening(intent);
        isListening = true;
        tvRecognizedText.setText("Reconocimiento de voz iniciado.");
    }

    private void stopListening() {
        if (speechRecognizer != null && isListening) {
            speechRecognizer.stopListening();
            isListening = false;
            tvRecognizedText.setText("Reconocimiento de voz detenido.");
        }
    }

    private void setupSpeechRecognizer() {
        speechRecognizer.setRecognitionListener(new android.speech.RecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
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
                    accountHandler.handleCommand(recognizedText);
                } else {
                    playRandomAudio(R.raw.losiento, R.raw.losientodos, R.raw.losientotres);
                }
                isListening = false; // Marcar como no escuchando después de procesar los resultados
            }

            @Override
            public void onError(int error) {
                // Manejo de errores
                tvRecognizedText.setText("Error en el reconocimiento de voz.");
                isListening = false; // Marcar como no escuchando en caso de error
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                // Manejar si es necesario
            }

            @Override
            public void onBeginningOfSpeech() {
                // Manejar si es necesario
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // Manejar si es necesario
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // Manejar si es necesario
            }

            @Override
            public void onEndOfSpeech() {
                // Se maneja en onResults y onError
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Manejar si es necesario
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // Manejar si es necesario
            }
        });
    }

    private void playRandomAudio(int... audioResIds) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        int randomIndex = random.nextInt(audioResIds.length);
        mediaPlayer = MediaPlayer.create(this, audioResIds[randomIndex]);
        mediaPlayer.setOnCompletionListener(mp -> {
            startListening(); // Reiniciar reconocimiento de voz después de reproducir audio
        });
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
}
