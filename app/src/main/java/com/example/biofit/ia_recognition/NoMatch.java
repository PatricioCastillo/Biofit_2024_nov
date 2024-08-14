package com.example.biofit.ia_recognition;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import com.example.biofit.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NoMatch {

    private Context context;
    private MediaPlayer mediaPlayer;
    private Random random = new Random();
    private Set<String> recognizedTerms = new HashSet<>();

    // Constructor con contexto
    public NoMatch(Context context) {
        this.context = context;
        loadTermsFromJSON();
    }

    private void loadTermsFromJSON() {
        try {
            // Cargar términos desde mainResponses.json
            InputStream isMain = context.getAssets().open("mainResponses.json");
            byte[] bufferMain = new byte[isMain.available()];
            isMain.read(bufferMain);
            isMain.close();
            String jsonMain = new String(bufferMain, "UTF-8");

            JSONObject jsonObjectMain = new JSONObject(jsonMain);
            JSONArray greetings = jsonObjectMain.getJSONArray("greetings");
            JSONArray responses = jsonObjectMain.getJSONArray("responses");
            JSONObject questions = jsonObjectMain.getJSONObject("questions");
            JSONArray whatCanDo = questions.getJSONArray("whatCanDo");
            JSONArray whoAreYou = questions.getJSONArray("whoAreYou");
            JSONArray dontKnow = questions.getJSONArray("dontKnow");
            JSONArray youreDoing = questions.getJSONArray("youreDoing");

            addJSONArrayToSet(greetings);
            addJSONArrayToSet(responses);
            addJSONArrayToSet(whatCanDo);
            addJSONArrayToSet(whoAreYou);
            addJSONArrayToSet(dontKnow);
            addJSONArrayToSet(youreDoing);

            // Cargar términos desde feelingsResponses.json
            InputStream isFeelings = context.getAssets().open("feelingsResponses.json");
            byte[] bufferFeelings = new byte[isFeelings.available()];
            isFeelings.read(bufferFeelings);
            isFeelings.close();
            String jsonFeelings = new String(bufferFeelings, "UTF-8");

            JSONObject jsonObjectFeelings = new JSONObject(jsonFeelings);
            JSONArray scared = jsonObjectFeelings.getJSONArray("scared");
            JSONArray hungry = jsonObjectFeelings.getJSONArray("hungry");
            JSONArray angry = jsonObjectFeelings.getJSONArray("angry");
            JSONArray happy = jsonObjectFeelings.getJSONArray("happy");
            JSONArray sad = jsonObjectFeelings.getJSONArray("sad");
            JSONArray anxiety = jsonObjectFeelings.getJSONArray("anxiety");

            addJSONArrayToSet(scared);
            addJSONArrayToSet(hungry);
            addJSONArrayToSet(angry);
            addJSONArrayToSet(happy);
            addJSONArrayToSet(sad);
            addJSONArrayToSet(anxiety);

        } catch (Exception e) {
            Log.e("NoMatch", "Error al cargar términos desde JSON", e);
        }
    }

    private void addJSONArrayToSet(JSONArray jsonArray) throws Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            recognizedTerms.add(jsonArray.getString(i).toLowerCase().trim());
        }
    }

    public void validateRecognition(String recognizedText) {
        if (!recognizedTerms.contains(recognizedText.toLowerCase().trim())) {
            playRandomAudios(R.raw.losiento, R.raw.losientodos, R.raw.losientotres);
        }
    }

    private void playRandomAudios(int... audioResIds) {
        if (context == null) {
            Log.e("NoMatch", "Contexto no está disponible");
            return;
        }

        // Releasing the previous MediaPlayer instance if it exists
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Creating and starting the new MediaPlayer instance
        int randomIndex = random.nextInt(audioResIds.length);
        mediaPlayer = MediaPlayer.create(context, audioResIds[randomIndex]);
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();  // Release the MediaPlayer when playback completes
            mediaPlayer = null;  // Set to null to avoid memory leaks
        });
        mediaPlayer.start();
    }
}
