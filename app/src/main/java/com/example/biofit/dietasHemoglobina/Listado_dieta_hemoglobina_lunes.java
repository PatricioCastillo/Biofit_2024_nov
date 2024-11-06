package com.example.biofit.dietasHemoglobina;

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

public class Listado_dieta_hemoglobina_lunes extends AppCompatActivity {
    private ListView listView;
    private CustomListAdapter adapter;
    private ArrayList<DietaItem> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_dieta_hemoglobina_lunes);

        listView = findViewById(R.id.listaMedi);
        datos = new ArrayList<>();
        adapter = new CustomListAdapter(this, datos);
        listView.setAdapter(adapter);

        // Hacer la solicitud HTTP
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.dashblog.cl/showAlimentos.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON
                        try {
                            // Verificar si hay al menos un objeto en la respuesta
                            if (response.length() > 0) {
                                JSONObject jsonObject = response.getJSONObject(21);
                                String desayuno = jsonObject.getString("desayuno");
                                String almuerzo = jsonObject.getString("almuerzo");
                                String snacks = jsonObject.getString("snacks");
                                String cena = jsonObject.getString("cena");

                                // URLs de las imágenes
                                String imgDesUrl = jsonObject.getString("img_des");
                                String imgAlmUrl = jsonObject.getString("img_alm");
                                String imgSnackUrl = jsonObject.getString("img_snack");
                                String imgCenUrl = jsonObject.getString("img_cen");

                                // Agrega los objetos DietaItem con las URLs de las imágenes
                                datos.add(new DietaItem(desayuno, imgDesUrl));
                                datos.add(new DietaItem(almuerzo, imgAlmUrl));
                                datos.add(new DietaItem(snacks, imgSnackUrl));
                                datos.add(new DietaItem(cena, imgCenUrl));

                                adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                            } else {
                                // No se encontraron datos en la respuesta
                                // Aquí puedes mostrar un mensaje o realizar otra acción apropiada
                            }
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

            // Configurar el TextView
            TextView textView = listItem.findViewById(R.id.item_text);
            textView.setText(currentItem.getNombre());

            // Configurar el TextView "desc" según el elemento de la lista
            TextView desc = listItem.findViewById(R.id.desc);
            switch (position) {
                case 0:
                    desc.setText("Desayuno");
                    break;
                case 1:
                    desc.setText("Almuerzo");
                    break;
                case 2:
                    desc.setText("Snacks");
                    break;
                case 3:
                    desc.setText("Cena");
                    break;
                default:
                    desc.setText("Otro"); // Puedes cambiar esto según tus necesidades si tienes más elementos
                    break;
            }

            // Configurar el ImageView usando Glide para cargar la imagen desde la URL
            ImageView imageView = listItem.findViewById(R.id.item_image);
            Glide.with(mContext).load(currentItem.getImagen()).into(imageView);

            return listItem;
        }
    }

    // Clase para representar los elementos de la dieta con su nombre y su imagen asociada
    private class DietaItem {
        private String nombre;
        private String imagen;

        public DietaItem(String nombre, String imagen) {
            this.nombre = nombre;
            this.imagen = imagen;
        }

        public String getNombre() {
            return nombre;
        }

        public String getImagen() {
            return imagen;
        }
    }
}
