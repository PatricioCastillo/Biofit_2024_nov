package database.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class Alimento {
    private static Alimento instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    private String nombre;
    private int calorias;

    public Alimento(String nombre, int calorias) {
        this.nombre = nombre;
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCalorias() {
        return calorias;
    }

    public Alimento(Context context) {
        ctx = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized Alimento getInstance(Context context) {
        if (instance == null) {
            instance = new Alimento(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }

    public void getAlimentos(final VolleyCallback callback) throws JSONException {
        // Verificar si ya se ha realizado la solicitud antes de iniciarla
        if (requestQueue != null && requestQueue.getCache() != null && requestQueue.getCache().get("http://192.168.56.1/biofit_dashboard/API/alimentos/showAlimentos.php") != null) {
            // Si ya existe en la caché, llamar directamente al método onSuccess
            JSONArray cachedResponse = new JSONArray(requestQueue.getCache().get("http://192.168.56.1/biofit_dashboard/API/alimentos/showAlimentos.php").data);
            callback.onSuccess(cachedResponse);
        } else {
            String url = "http://192.168.56.1/biofit_dashboard/API/alimentos/showAlimentos.php";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            callback.onSuccess(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.onError(error);
                        }
                    });

            addToRequestQueue(jsonArrayRequest);
        }
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    // Interfaz para manejar respuestas exitosas y errores
    public interface VolleyCallback {
        void onSuccess(JSONArray response);
        void onError(VolleyError error);
    }
}
