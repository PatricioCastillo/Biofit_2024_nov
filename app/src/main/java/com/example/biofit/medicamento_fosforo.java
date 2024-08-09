package com.example.biofit;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class medicamento_fosforo extends AppCompatActivity {
    private ListView listView;
    private CustomListAdapter adapter;
    private ArrayList<DietaItem> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_fosforo);

        listView = findViewById(R.id.listaMedi);
        datos = new ArrayList<>();
        adapter = new CustomListAdapter(this, datos);
        listView.setAdapter(adapter);

        // Hacer la solicitud HTTP
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.dashblog.cl/showMedicamentos.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String nombre = jsonObject.getString("nombre");
                                String dosis = jsonObject.getString("dosis");
                                String desc = jsonObject.getString("descripcion");
                                String imageUrl = jsonObject.getString("url"); // URL de la imagen

                                // Agrega el item al ArrayList
                                datos.add(new DietaItem(nombre, dosis, desc,imageUrl));
                            }

                            adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        error.printStackTrace();
                    }
                });

        queue.add(request); // Agregar la solicitud a la cola de solicitudes
    }

    // Adaptador personalizado para mostrar texto e imágenes en el ListView
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
            View listItem = convertView;
            if (listItem == null) {
                listItem = LayoutInflater.from(mContext).inflate(R.layout.list_dieta, parent, false);
            }

            DietaItem currentItem = mDatos.get(position);

            // Configurar el TextView para el nombre
            TextView textView = listItem.findViewById(R.id.item_text);
            textView.setText(currentItem.getNombre());

            // Configurar el TextView para el contenido
            TextView desc = listItem.findViewById(R.id.desc);
            desc.setText(currentItem.getDosis());

            // Configurar el TextView para el contenido
            TextView descri = listItem.findViewById(R.id.descripcion);
            descri.setText(currentItem.getDescripcion());

            // Configurar el ImageView
            ImageView imageView = listItem.findViewById(R.id.item_image);
            Glide.with(mContext)
                    .load(currentItem.getImagenUrl()) // Cargar la imagen desde la URL
                    .into(imageView); // Mostrar la imagen en el ImageView

            return listItem;
        }
    }

    // Clase para representar los elementos de la dieta con su nombre, contenido y URL de imagen
    private class DietaItem {
        private String nombre;
        private String dosis;
        private String descripcion;
        private String imagenUrl;

        public DietaItem(String nombre, String dosis, String descripcion, String imagenUrl) {
            this.nombre = nombre;
            this.dosis = dosis;
            this.descripcion = descripcion;
            this.imagenUrl = imagenUrl;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDosis() {
            return dosis;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getImagenUrl() {
            return imagenUrl;
        }


    }
}
