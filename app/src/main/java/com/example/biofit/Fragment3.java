package com.example.biofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.biofit.estadisticas_fragments.creatinina_fragment;
import com.example.biofit.estadisticas_fragments.filtracion_fragment;
import com.example.biofit.estadisticas_fragments.fosforo_fragment;
import com.example.biofit.estadisticas_fragments.hemoglobina_fragment;
import com.example.biofit.estadisticas_fragments.nitrogeno_fragment;
import com.example.biofit.estadisticas_fragments.potasio_fragment;

public class Fragment3 extends Fragment {
    private Spinner spinnerRenal, spinnerIndicadores;

    // Constantes para identificar las preferencias en SharedPreferences
    private static final String PREF_RENAL = "renal";
    private static final String PREF_INDICADOR = "indicador";

    // Opciones y iconos para cada caso en spinnerRenal y spinnerIndicadores
    private final String[] opcionesRenal = {"Paciente Transplantado", "Paciente Diálisis"};
    private final int[] iconsRenal = {R.drawable.transplantado, R.drawable.dialisis};

    private final String[] opcionesTransplantado = {"Nitrogeno Ureico (BUN)", "Filtración Glomerular (TFG)", "Creatinina Sérica"};
    private final int[] iconsTransplantado = {R.drawable.tfg, R.drawable.filtro, R.drawable.creatina};

    private final String[] opcionesDialisis = {"Niveles de Potasio", "Hemoglobina", "Niveles de Fosforo"};
    private final int[] iconsDialisis = {R.drawable.pota, R.drawable.hemo, R.drawable.fosfo};

    public Fragment3() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_3, container, false);

        // Inicialización de Spinners
        spinnerRenal = rootView.findViewById(R.id.spnRenal);
        spinnerIndicadores = rootView.findViewById(R.id.spnIndicadores);

        // Adaptador personalizado para spinnerRenal
        SpinnerAdapter adapterRenal = new SpinnerAdapter(getContext(), opcionesRenal, iconsRenal);
        spinnerRenal.setAdapter(adapterRenal);

        // Configuración inicial para el segundo Spinner
        actualizarOpcionesSpinnerIndicadores("Paciente Transplantado");

        // Listener para el primer Spinner (spinnerRenal)
        spinnerRenal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccionRenal = opcionesRenal[position];
                guardarSeleccionRenal(seleccionRenal);
                actualizarOpcionesSpinnerIndicadores(seleccionRenal);

                // Reemplazar el fragmento correspondiente
                Fragment selectedFragment = null;

                switch (seleccionRenal) {
                    case "Paciente Transplantado":
                        selectedFragment = new nitrogeno_fragment(); // Se puede actualizar si hay más opciones
                        break;
                    case "Paciente Diálisis":
                        selectedFragment = new potasio_fragment(); // El fragmento por defecto al inicio
                        break;
                }

                if (selectedFragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener para el segundo Spinner (spinnerIndicadores)
        spinnerIndicadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = parent.getItemAtPosition(position).toString();
                guardarSeleccionIndicador(seleccion);

                // Reemplazar el fragmento correspondiente
                Fragment selectedFragment = null;

                switch (seleccion) {
                    case "Nitrogeno Ureico (BUN)":
                        selectedFragment = new nitrogeno_fragment();
                        break;
                    case "Filtración Glomerular (TFG)":
                        selectedFragment = new filtracion_fragment();
                        break;
                    case "Creatinina Sérica":
                        selectedFragment = new creatinina_fragment();
                        break;
                    case "Niveles de Potasio":
                        selectedFragment = new potasio_fragment();
                        break;
                    case "Hemoglobina":
                        selectedFragment = new hemoglobina_fragment();
                        break;
                    case "Niveles de Fosforo":
                        selectedFragment = new fosforo_fragment();
                        break;
                }

                if (selectedFragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return rootView;
    }

    private void actualizarOpcionesSpinnerIndicadores(String seleccionRenal) {
        String[] opciones;
        int[] icons;

        if ("Paciente Transplantado".equals(seleccionRenal)) {
            opciones = opcionesTransplantado;
            icons = iconsTransplantado;
        } else {
            opciones = opcionesDialisis;
            icons = iconsDialisis;
        }

        SpinnerAdapter adapterIndicadores = new SpinnerAdapter(getContext(), opciones, icons);
        spinnerIndicadores.setAdapter(adapterIndicadores);

        // Restaurar la selección del segundo Spinner
        String seleccionIndicador = obtenerSeleccionIndicador();
        if (seleccionIndicador != null && !seleccionIndicador.isEmpty()) {
            for (int i = 0; i < opciones.length; i++) {
                if (opciones[i].equals(seleccionIndicador)) {
                    spinnerIndicadores.setSelection(i);
                    break;
                }
            }
        }
    }

    private void guardarSeleccionRenal(String seleccion) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_RENAL, seleccion);
        editor.apply();
    }

    private String obtenerSeleccionRenal() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return prefs.getString(PREF_RENAL, "");
    }

    private void guardarSeleccionIndicador(String seleccion) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_INDICADOR, seleccion);
        editor.apply();
    }

    private String obtenerSeleccionIndicador() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return prefs.getString(PREF_INDICADOR, "");
    }

    private static class SpinnerAdapter extends ArrayAdapter<String> {
        private final int[] icons;
        private final String[] items;
        private final Context context;

        public SpinnerAdapter(Context context, String[] items, int[] icons) {
            super(context, android.R.layout.simple_spinner_item, items);
            this.context = context;
            this.items = items;
            this.icons = icons;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return createCustomView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return createCustomView(position, convertView, parent);
        }

        private View createCustomView(int position, View convertView, ViewGroup parent) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setPadding(18, 15, 18, 15);

            ImageView icon = new ImageView(context);
            icon.setImageResource(icons[position]);
            icon.setLayoutParams(new LinearLayout.LayoutParams(60, 60)); // Tamaño del ícono
            icon.setPadding(4, 4, 10, 0); // Espacio entre ícono y texto

            TextView text = new TextView(context);
            text.setText(items[position]);
            layout.addView(icon);
            layout.addView(text);

            return layout;
        }
    }
}
