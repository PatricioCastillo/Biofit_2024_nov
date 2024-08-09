package com.example.biofit.rutinaAbdomen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.biofit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class abdomen_bajaInt_lunes extends AppCompatActivity {
    private ListView listView;
    private CustomListAdapter adapter;
    private ArrayList<DietaItem> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_dieta_fosforo_lunes);

        listView = findViewById(R.id.listaMedi);
        datos = new ArrayList<>();
        adapter = new CustomListAdapter(this, datos);
        listView.setAdapter(adapter);

        // Hacer la solicitud HTTP
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.dashblog.cl/showRutinas.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON
                        try {
                            datos.clear(); // Limpiar datos anteriores
                            // Solo mostramos el primer objeto del JSON array
                            if (response.length() > 0) {
                                JSONObject jsonObject = response.getJSONObject(0); // Primer objeto
                                // Agregar los 5 ejercicios a la lista
                                for (int i = 1; i <= 5; i++) {
                                    String ejercicio = jsonObject.getString("ejercicio" + (i == 1 ? "Uno" : (i == 2 ? "Dos" : (i == 3 ? "Tres" : (i == 4 ? "Cuatro" : "Cinco")))));
                                    String repeticiones = jsonObject.getString("repeticiones" + (i == 1 ? "Uno" : (i == 2 ? "Dos" : (i == 3 ? "Tres" : (i == 4 ? "Cuatro" : "Cinco")))));
                                    String gifUrl = jsonObject.getString("img" + i); // URL del GIF

                                    // Agregar el ejercicio actual a la lista
                                    datos.add(new DietaItem(ejercicio, "Repeticiones: " + repeticiones, gifUrl));
                                }
                                adapter.notifyDataSetChanged(); // Notificar al adaptador
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request); // Agregar la solicitud a la cola
    }

    private class CustomListAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<DietaItem> mDatos;

        public CustomListAdapter(Context context, ArrayList<DietaItem> datos) {
            mContext = context;
            mDatos = datos;
        }

        @Override
        public int getCount() {
            return mDatos.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_fitness, parent, false);
            }

            DietaItem currentItem = mDatos.get(position);

            ImageView gifView = convertView.findViewById(R.id.gifView);
            TextView ejercicioText = convertView.findViewById(R.id.ejercicio);
            TextView repeticionesText = convertView.findViewById(R.id.repeticiones);

            // Usar Glide para cargar el GIF
            Glide.with(mContext)
                    .asGif()
                    .load(currentItem.getGifUrl())
                    .into(gifView);

            ejercicioText.setText(currentItem.getEjercicio());
            repeticionesText.setText(currentItem.getRepeticiones());

            return convertView;
        }
    }

    private static class DietaItem {
        private String ejercicio;
        private String repeticiones;
        private String gifUrl;

        public DietaItem(String ejercicio, String repeticiones, String gifUrl) {
            this.ejercicio = ejercicio;
            this.repeticiones = repeticiones;
            this.gifUrl = gifUrl;
        }

        public String getEjercicio() {
            return ejercicio;
        }

        public String getRepeticiones() {
            return repeticiones;
        }

        public String getGifUrl() {
            return gifUrl;
        }
    }
}
