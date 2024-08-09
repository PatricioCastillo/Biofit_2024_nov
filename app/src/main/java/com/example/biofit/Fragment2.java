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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_domingo;
import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_jueves;
import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_lunes;
import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_martes;
import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_miercoles;
import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_sabado;
import com.example.biofit.dietasAltCalorias.Listado_dieta_altaCalorias_viernes;
import com.example.biofit.dietasCalorias.Listado_dieta_domingo;
import com.example.biofit.dietasCalorias.Listado_dieta_jueves;
import com.example.biofit.dietasCalorias.Listado_dieta_lunes;
import com.example.biofit.dietasCalorias.Listado_dieta_martes;
import com.example.biofit.dietasCalorias.Listado_dieta_miercoles;
import com.example.biofit.dietasCalorias.Listado_dieta_sabado;
import com.example.biofit.dietasCalorias.Listado_dieta_viernes;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_domingo;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_jueves;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_lunes;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_martes;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_miercoles;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_sabado;
import com.example.biofit.dietasFosforo.Listado_dieta_fosforo_viernes;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_domingo;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_jueves;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_lunes;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_martes;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_miercoles;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_sabado;
import com.example.biofit.dietasHemoglobina.Listado_dieta_hemoglobina_viernes;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_domingo;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_jueves;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_lunes;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_martes;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_miercoles;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_sabado;
import com.example.biofit.dietasMasaMuscular.Listado_dieta_altaMasaMusc_viernes;

import java.util.Timer;
import java.util.TimerTask;

import adapters.SliderAdapter;
import dietasPotasio.Listado_dieta_potasio_domingo;
import dietasPotasio.Listado_dieta_potasio_jueves;
import dietasPotasio.Listado_dieta_potasio_lunes;
import dietasPotasio.Listado_dieta_potasio_martes;
import dietasPotasio.Listado_dieta_potasio_miercoles;
import dietasPotasio.Listado_dieta_potasio_sabado;
import dietasPotasio.Listado_dieta_potasio_viernes;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment2 extends Fragment {

    //Variables para el Slider:
    private ViewPager viewPager;
    private SliderAdapter adapter;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 1000; // Tiempo de espera antes de que comience el desplazamiento automático
    private final long PERIOD_MS = 3000; // Intervalo entre desplazamientos automáticos
    private Spinner spinner;

    // Constante para identificar la preferencia en SharedPreferences
    private static final String PREF_DIETA = "dieta";

    // Required empty public constructor
    public Fragment2() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_2, container, false);


        spinner = rootView.findViewById(R.id.spnMeta);

        // Datos del Spinner
        String[] items = {"Bajo en Calorias", "Bajo en Potasio", "Bajo en Fosforo", "Alto Masa Muscular", "Alto en Hemoglobina","Alto en Calorias", "Bajo en Sodio", "Desintoxicación"};
        int[] icons = {R.drawable.caldown, R.drawable.pota, R.drawable.fosfo, R.drawable.musc, R.drawable.hemo, R.drawable.calup, R.drawable.sodio, R.drawable.clorofila}; // Reemplaza con tus íconos

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




        ImageButton imageButton = rootView.findViewById(R.id.libro);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBibliotecasLimentosChileActivity();
            }
        });

        ImageButton imageButtons = rootView.findViewById(R.id.icon);
        imageButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProhibicionActivity();
            }
        });


        ImageButton imageButtonss = rootView.findViewById(R.id.farma);
        imageButtonss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMedicamentoActivity();
            }
        });



        // Configurar el ViewPager y asignar el adaptador
        viewPager = rootView.findViewById(R.id.view_pager1);
        adapter = new SliderAdapter(requireContext());
        viewPager.setAdapter(adapter);

        // Configurar el desplazamiento automático del ViewPager
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage >= adapter.getImageIds().length) {
                    currentPage = 0; // Reiniciar a la primera imagen
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

        // Define your Spinner
        //       Spinner spinner = rootView.findViewById(R.id.spnMeta);
        //         ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
                //               R.array.goals, android.R.layout.simple_spinner_item);
        //        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //      spinner.setAdapter(spinnerAdapter);

        // Recuperar la selección del Spinner desde SharedPreferences
        String dietaSeleccionada = obtenerDietaSeleccionada();
        if (!dietaSeleccionada.isEmpty()) {
            int posicionDieta = adapters.getPosition(dietaSeleccionada);
            spinner.setSelection(posicionDieta);
        }

        // Set click listener for Spinner items
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDieta = parent.getItemAtPosition(position).toString();
                // Guardar la selección del Spinner en SharedPreferences
                guardarDietaSeleccionada(selectedDieta);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Define your ListView
        ListView listView = rootView.findViewById(R.id.listaMedi);
        ArrayAdapter<CharSequence> listViewAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.days, android.R.layout.simple_list_item_1);
        listView.setAdapter(listViewAdapter);




        // Set click listener for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = (String) parent.getItemAtPosition(position);
                String selectedOption = spinner.getSelectedItem().toString(); // Obtener la opción seleccionada en el Spinner

                // Validar el día y la opción seleccionada
                if (selectedDay.equals("Lunes")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_lunes.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_lunes.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_lunes.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_lunes.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_lunes.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_lunes.class);
                    }
                } else if (selectedDay.equals("Martes")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_martes.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_martes.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_martes.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_martes.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_martes.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_martes.class);
                    }
                } else if (selectedDay.equals("Miercoles")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_miercoles.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_miercoles.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_miercoles.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_miercoles.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_miercoles.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_miercoles.class);
                    }
                } else if (selectedDay.equals("Jueves")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_jueves.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_jueves.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_jueves.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_jueves.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_jueves.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_jueves.class);
                    }
                } else if (selectedDay.equals("Viernes")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_viernes.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_viernes.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_viernes.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_viernes.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_viernes.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_viernes.class);
                    }
                } else if (selectedDay.equals("Sabado")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_sabado.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_sabado.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_sabado.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_sabado.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_sabado.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_sabado.class);
                    }
                } else if (selectedDay.equals("Domingo")) {
                    if (selectedOption.equals("Bajo en Calorias")) {
                        startActivity(Listado_dieta_domingo.class);
                    } else if (selectedOption.equals("Bajo en Potasio")) {
                        startActivity(Listado_dieta_potasio_domingo.class);
                    } else if (selectedOption.equals("Bajo en Fosforo")) {
                        startActivity(Listado_dieta_fosforo_domingo.class);
                    } else if (selectedOption.equals("Alto Masa Muscular")) {
                        startActivity(Listado_dieta_altaMasaMusc_domingo.class);
                    } else if (selectedOption.equals("Alto en Calorias")) {
                        startActivity(Listado_dieta_altaCalorias_domingo.class);
                    } else if (selectedOption.equals("Alto en Hemoglobina")) {
                        startActivity(Listado_dieta_hemoglobina_domingo.class);
                    }
                }
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

    // Method to start activity based on class
    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(requireContext(), cls);
        startActivity(intent);
    }

    // Método para guardar la selección del Spinner en SharedPreferences
    private void guardarDietaSeleccionada(String dieta) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_DIETA, dieta);
        editor.apply();
    }

    // Método para obtener la selección del Spinner desde SharedPreferences
    private String obtenerDietaSeleccionada() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return prefs.getString(PREF_DIETA, "");
    }

    private void startBibliotecasLimentosChileActivity() {
        Intent intent = new Intent(requireContext(), biblioteca_alimentos_chile.class);
        startActivity(intent);
    }

    private void startProhibicionActivity() {
        Intent intent = new Intent(requireContext(), no_fosforo.class);
        startActivity(intent);
    }

    private void startMedicamentoActivity() {
        Intent intent = new Intent(requireContext(), medicamento_fosforo.class);
        startActivity(intent);
    }
}
