package com.example.biofit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import database.API.Alimento;

public class Listado_alimentos extends AppCompatActivity {
    private boolean alimentosSolicitados = false; // Variable para verificar si se han solicitado alimentos
    private ListView listado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alimentos);
        listado = (ListView) findViewById(R.id.lista);

        // Llamar al método para iniciar la solicitud de alimentos
        obtenerAlimentos();
    }

    // Método para iniciar la solicitud de alimentos
    private void obtenerAlimentos() {
        // Verificar si los alimentos ya han sido solicitados antes de iniciar la solicitud
        if (!alimentosSolicitados) {
            try {
                Alimento.getInstance(this).getAlimentos(new Alimento.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        // Marcar alimentosSolicitados como verdadero después de recibir la respuesta exitosa
                        alimentosSolicitados = true;

                        // Crear un ArrayList para almacenar los alimentos
                        ArrayList<Alimento> listaAlimentos = parseJSONResponse(response);

                        // Crear un ArrayList para almacenar los nombres y las calorías de los alimentos
                        ArrayList<String> datosAlimentos = new ArrayList<>();
                        for (int i = 0; i < listaAlimentos.size(); i++) {
                            Alimento alimento = listaAlimentos.get(i);
                            String infoAlimento = "Nombre del alimento: " + alimento.getNombre() + ", Calorías: " + alimento.getCalorias();
                            datosAlimentos.add(infoAlimento);
                        }

                        // Crear un ArrayAdapter para mostrar los datos en el ListView
                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(Listado_alimentos.this, android.R.layout.simple_list_item_1, datosAlimentos);

                        // Establecer el adaptador en el ListView
                        listado.setAdapter(adaptador);

                    }

                    @Override
                    public void onError(VolleyError error) {
                        // Manejar el error aquí (puedes mostrar un mensaje de error al usuario)
                        error.printStackTrace();
                    }
                });
            } catch (JSONException e) {
                // Manejar la excepción JSONException aquí
                e.printStackTrace();
            }
        }
    }


    private ArrayList<Alimento> parseJSONResponse(JSONArray response) {
        ArrayList<Alimento> listaAlimentos = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonAlimento = response.getJSONObject(i);
                String nombre = jsonAlimento.getString("Nombre");
                int calorias = jsonAlimento.getInt("Calorias");
                listaAlimentos.add(new Alimento(nombre, calorias));
            }
        } catch (JSONException e) {
            // Manejar la excepción adecuadamente (puedes mostrar un mensaje de error al usuario)
            e.printStackTrace();
        }

        return listaAlimentos;
    }
}
