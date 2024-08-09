package com.example.biofit;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import Clases.RutinaAbdomen;
import adapters.SliderSportAdapter;

public class Fragment3 extends Fragment {
    private Spinner spinner, spinnerInten;
    private ViewPager viewPager;
    private SliderSportAdapter adapter;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 1000; // Tiempo de espera antes de que comience el desplazamiento automático
    private final long PERIOD_MS = 3000; // Intervalo entre desplazamientos automáticos

    // Constantes para identificar las preferencias en SharedPreferences
    private static final String PREF_EJERCICIO = "ejercicio";
    private static final String PREF_SECCION = "seccion";


    // Required empty public constructor
    public Fragment3() {}

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_3, container, false);

        // Configurar el ViewPager y asignar el adaptador
        viewPager = rootView.findViewById(R.id.view_pager);
        adapter = new SliderSportAdapter(requireContext());
        viewPager.setAdapter(adapter);


        spinner = rootView.findViewById(R.id.spnSeccion);
        spinnerInten = rootView.findViewById(R.id.spnEjercicio);

        // Datos del Spinner
        String[] items = {"Abdomen", "Brazos", "Piernas", "Espalda", "Cardiovascular"};
        int[] icons = {R.drawable.abdomen, R.drawable.brazos, R.drawable.piernas, R.drawable.espalda, R.drawable.cardio}; // Reemplaza con tus íconos

        // Adaptador para el Spinner
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_text, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
                }
                ImageView icon = convertView.findViewById(R.id.spinner_icon);
                TextView text = convertView.findViewById(R.id.spinner_text);

                icon.setImageResource(icons[position]);
                text.setText(getItem(position));

                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return getView(position, convertView, parent);
            }
        };


        spinner.setAdapter(adapters);


        // Datos del Spinner
        String[] itemDos = {"Baja Intensidad", "Media Intensidad", "Alta Intensidad"};
        int[] iconos = {R.drawable.baja, R.drawable.media, R.drawable.alta}; // Reemplaza con tus íconos

        // Adaptador para el Spinner
        ArrayAdapter<String> adapterInten = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_text, itemDos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
                }
                ImageView icon = convertView.findViewById(R.id.spinner_icon);
                TextView text = convertView.findViewById(R.id.spinner_text);

                icon.setImageResource(iconos[position]);
                text.setText(getItem(position));

                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return getView(position, convertView, parent);
            }
        };


        spinnerInten.setAdapter(adapterInten);


        // Recuperar las selecciones de los Spinners desde SharedPreferences
        String ejercicioSeleccionado = obtenerEjercicioSeleccionado();
        String seccionSeleccionada = obtenerSeccionSeleccionada();

        // Establecer las selecciones recuperadas en los Spinners
        if (!ejercicioSeleccionado.isEmpty()) {
            int posicionEjercicio = adapterInten.getPosition(ejercicioSeleccionado);
            spinnerInten.setSelection(posicionEjercicio);
        }

        if (!seccionSeleccionada.isEmpty()) {
            int posicionSeccion = adapters.getPosition(seccionSeleccionada);
            spinner.setSelection(posicionSeccion);
        }

        // Configurar el desplazamiento automático del ViewPager
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage >= adapter.getImageSports().length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // Initialize Timer
        timer.schedule(new TimerTask() { // Iniciar TimerTask
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS); // Ejecutar el desplazamiento automático después del tiempo de espera y repetir después de cada intervalo

        // Define your ListView
        ListView listView = rootView.findViewById(R.id.listaMedi);
        String[] daysOfWeek = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, daysOfWeek);
        listView.setAdapter(listViewAdapter);


        RutinaAbdomen actividadesPorDia = new RutinaAbdomen();

        HashMap<String, Class<?>> actividadesPorDiaBajaIntensidad = actividadesPorDia.getActividadesPorDiaBajaIntensidad();
        HashMap<String, Class<?>> actividadesPorDiaMediaIntensidad = actividadesPorDia.getActividadesPorDiaMediaIntensidad();
        HashMap<String, Class<?>> actividadesPorDiaAltaIntensidad = actividadesPorDia.getActividadesPorDiaAltaIntensidad();


// VÁLIDACIONES !!!
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = (String) parent.getItemAtPosition(position);
                String ejercicioSeleccionado = obtenerEjercicioSeleccionado();
                String seccionSeleccionada = obtenerSeccionSeleccionada();

                // Verificar si la actividad está mapeada para el día seleccionado y si se cumplen las condiciones para la intensidad de baja intensidad
                if (actividadesPorDia.getActividadesPorDiaBajaIntensidad().containsKey(selectedDay) &&
                        ejercicioSeleccionado.equals("Baja Intensidad") &&
                        seccionSeleccionada.equals("Abdomen")) {
                    // Obtener la actividad correspondiente al día seleccionado y de baja intensidad, y iniciarla
                    startActivity(new Intent(requireContext(), actividadesPorDia.getActividadesPorDiaBajaIntensidad().get(selectedDay)));
                }
                // Verificar si la actividad está mapeada para el día seleccionado y si se cumplen las condiciones para la intensidad de media intensidad
                else if (actividadesPorDia.getActividadesPorDiaMediaIntensidad().containsKey(selectedDay) &&
                        ejercicioSeleccionado.equals("Media Intensidad") &&
                        seccionSeleccionada.equals("Abdomen")) {
                    // Obtener la actividad correspondiente al día seleccionado y de media intensidad, y iniciarla
                    startActivity(new Intent(requireContext(), actividadesPorDia.getActividadesPorDiaMediaIntensidad().get(selectedDay)));
                }
                // Verificar si la actividad está mapeada para el día seleccionado y si se cumplen las condiciones para la intensidad de alta intensidad
                else if (actividadesPorDia.getActividadesPorDiaAltaIntensidad().containsKey(selectedDay) &&
                        ejercicioSeleccionado.equals("Alta Intensidad") &&
                        seccionSeleccionada.equals("Abdomen")) {
                    // Obtener la actividad correspondiente al día seleccionado y de alta intensidad, y iniciarla
                    startActivity(new Intent(requireContext(), actividadesPorDia.getActividadesPorDiaAltaIntensidad().get(selectedDay)));
                } else {
                    // Si no se cumplen las condiciones, mostrar un mensaje de error
                    Toast.makeText(requireContext(), "No se cumplen las condiciones para el día seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });









        // Guardar la selección del Spinner de ejercicio en SharedPreferences
        spinnerInten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ejercicioSeleccionado = parent.getItemAtPosition(position).toString();
                guardarEjercicioSeleccionado(ejercicioSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Guardar la selección del Spinner de sección en SharedPreferences
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seccionSeleccionada = parent.getItemAtPosition(position).toString();
                guardarSeccionSeleccionada(seccionSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel(); // Detener el Timer cuando se destruye la vista
        }
    }










    // SHARED PREFERENCES ======================================================================================

    // Método para guardar la selección del Spinner de ejercicio en SharedPreferences
    private void guardarEjercicioSeleccionado(String ejercicio) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_EJERCICIO, ejercicio);
        editor.apply();
    }

    // Método para obtener la selección del Spinner de ejercicio desde SharedPreferences
    private String obtenerEjercicioSeleccionado() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return prefs.getString(PREF_EJERCICIO, "");
    }

    // Método para guardar la selección del Spinner de sección en SharedPreferences
    private void guardarSeccionSeleccionada(String seccion) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_SECCION, seccion);
        editor.apply();
    }

    // Método para obtener la selección del Spinner de sección desde SharedPreferences
    private String obtenerSeccionSeleccionada() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return prefs.getString(PREF_SECCION, "");
    }
}
