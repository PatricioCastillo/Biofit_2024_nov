package com.example.biofit.ia_recognition;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MusicHandler {

    private Context context;
    private Handler mainHandler;
    private TextView statusTextView;

    public MusicHandler(Context context, TextView statusTextView) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.statusTextView = statusTextView;
    }

    public void handleVoiceCommand(ArrayList<String> matches) {

        WarCry2002(matches);
        SelloTiempos(matches);
        AleaJactaEst(matches);
        DondeEstaLaLuz(matches);
        QuintaEscencia(matches);
        Rayden(matches);
    }

    private void activatePython(String urlString) {
        mainHandler.post(() -> statusTextView.setText("Conectando..."));

        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    mainHandler.post(() -> statusTextView.setText("Servicio activado!"));
                } else {
                    mainHandler.post(() -> statusTextView.setText("Error al activar el servicio"));
                }
            } catch (IOException e) {
                mainHandler.post(() -> statusTextView.setText("Error de conexión"));
            }
        }).start();
    }


    public void WarCry2002(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos Python
            if (recognizedText.equalsIgnoreCase("activar servicio Python")) {
                activatePython("http://192.168.100.26:5002/activate-python");
            } else if (recognizedText.equalsIgnoreCase("reproducir intro")) {
                activatePython("http://192.168.100.26:5002/play?track=intro");
            } else if (recognizedText.equalsIgnoreCase("reproducir luz del norte")) {
                activatePython("http://192.168.100.26:5002/play?track=luz_del_norte");
            } else if (recognizedText.equalsIgnoreCase("reproducir quiero")) {
                activatePython("http://192.168.100.26:5002/play?track=quiero");
            } else if (recognizedText.equalsIgnoreCase("reproducir nadie")) {
                activatePython("http://192.168.100.26:5002/play?track=nadie");
            } else if (recognizedText.equalsIgnoreCase("reproducir pueblo maldito")) {
                activatePython("http://192.168.100.26:5002/play?track=pueblo_maldito");
            } else if (recognizedText.equalsIgnoreCase("reproducir cada vez")) {
                activatePython("http://192.168.100.26:5002/play?track=cada_vez");
            } else if (recognizedText.equalsIgnoreCase("reproducir señor")) {
                activatePython("http://192.168.100.26:5002/play?track=senor");
            } else if (recognizedText.equalsIgnoreCase("reproducir al salir el sol")) {
                activatePython("http://192.168.100.26:5002/play?track=al_salir_el_sol");
            } else if (recognizedText.equalsIgnoreCase("reproducir trono del metal")) {
                activatePython("http://192.168.100.26:5002/play?track=trono_del_metal");
            } else if (recognizedText.equalsIgnoreCase("reproducir hoy gano yo")) {
                activatePython("http://192.168.100.26:5002/play?track=hoy_gano_yo");
            } else if (recognizedText.equalsIgnoreCase("reproducir nana")) {
                activatePython("http://192.168.100.26:5002/play?track=nana");
            }
        }
    }

    public void SelloTiempos(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para "El Sello de los Tiempos"
            if (recognizedText.equalsIgnoreCase("reproducir el sello de los tiempos")) {
                activatePython("http://192.168.100.26:5002/play?track=sello_de_los_tiempos");
            } else if (recognizedText.equalsIgnoreCase("reproducir hijo de la ira")) {
                activatePython("http://192.168.100.26:5002/play?track=hijo_de_la_ira");
            } else if (recognizedText.equalsIgnoreCase("reproducir capitan lawrence")) {
                activatePython("http://192.168.100.26:5002/play?track=capitan_lawrence");
            } else if (recognizedText.equalsIgnoreCase("reproducir tú mismo")) {
                activatePython("http://192.168.100.26:5002/play?track=tu_mismo");
            } else if (recognizedText.equalsIgnoreCase("reproducir un mar de estrellas")) {
                activatePython("http://192.168.100.26:5002/play?track=un_mar_de_estrellas");
            } else if (recognizedText.equalsIgnoreCase("reproducir un lugar")) {
                activatePython("http://192.168.100.26:5002/play?track=un_lugar");
            } else if (recognizedText.equalsIgnoreCase("reproducir dispuesto a combatir")) {
                activatePython("http://192.168.100.26:5002/play?track=dispuesto_a_combatir");
            } else if (recognizedText.equalsIgnoreCase("reproducir vampiro")) {
                activatePython("http://192.168.100.26:5002/play?track=vampiro");
            } else if (recognizedText.equalsIgnoreCase("reproducir hacia delante")) {
                activatePython("http://192.168.100.26:5002/play?track=hacia_delante");
            } else if (recognizedText.equalsIgnoreCase("reproducir renacer")) {
                activatePython("http://192.168.100.26:5002/play?track=renacer");
            }
        }
    }

    public void AleaJactaEst(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para el álbum "Alea Jacta Est"
            if (recognizedText.equalsIgnoreCase("reproducir el guardian de troya")) {
                activatePython("http://192.168.100.26:5002/play?track=el_guardian_de_troya");
            } else if (recognizedText.equalsIgnoreCase("reproducir iberia")) {
                activatePython("http://192.168.100.26:5002/play?track=iberia");
            } else if (recognizedText.equalsIgnoreCase("reproducir despertar")) {
                activatePython("http://192.168.100.26:5002/play?track=despertar");
            } else if (recognizedText.equalsIgnoreCase("reproducir lamento")) {
                activatePython("http://192.168.100.26:5002/play?track=lamento");
            } else if (recognizedText.equalsIgnoreCase("reproducir sin tu voz")) {
                activatePython("http://192.168.100.26:5002/play?track=sin_tu_voz");
            } else if (recognizedText.equalsIgnoreCase("reproducir aire")) {
                activatePython("http://192.168.100.26:5002/play?track=aire");
            } else if (recognizedText.equalsIgnoreCase("reproducir junto a mi")) {
                activatePython("http://192.168.100.26:5002/play?track=junto_a_mi");
            } else if (recognizedText.equalsIgnoreCase("reproducir espiritu de amor")) {
                activatePython("http://192.168.100.26:5002/play?track=espiritu_de_amor");
            } else if (recognizedText.equalsIgnoreCase("reproducir fe")) {
                activatePython("http://192.168.100.26:5002/play?track=fe");
            } else if (recognizedText.equalsIgnoreCase("reproducir reflejos de sangre")) {
                activatePython("http://192.168.100.26:5002/play?track=reflejos_de_sangre");
            }
        }
    }

    public void DondeEstaLaLuz(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para el álbum "Donde Está la Luz"
            if (recognizedText.equalsIgnoreCase("reproducir nuevo mundo")) {
                activatePython("http://192.168.100.26:5002/play?track=nuevo_mundo");
            } else if (recognizedText.equalsIgnoreCase("reproducir el anticristo")) {
                activatePython("http://192.168.100.26:5002/play?track=el_anticristo");
            } else if (recognizedText.equalsIgnoreCase("reproducir el regreso")) {
                activatePython("http://192.168.100.26:5002/play?track=el_regreso");
            } else if (recognizedText.equalsIgnoreCase("reproducir perdido")) {
                activatePython("http://192.168.100.26:5002/play?track=perdido");
            } else if (recognizedText.equalsIgnoreCase("reproducir hacia el infierno")) {
                activatePython("http://192.168.100.26:5002/play?track=hacia_el_infierno");
            } else if (recognizedText.equalsIgnoreCase("reproducir el amor de una madre")) {
                activatePython("http://192.168.100.26:5002/play?track=el_amor_de_una_madre");
            } else if (recognizedText.equalsIgnoreCase("reproducir contra el viento")) {
                activatePython("http://192.168.100.26:5002/play?track=contra_el_viento");
            } else if (recognizedText.equalsIgnoreCase("reproducir en un lugar sin dios")) {
                activatePython("http://192.168.100.26:5002/play?track=en_un_lugar_sin_dios");
            } else if (recognizedText.equalsIgnoreCase("reproducir tu ausencia")) {
                activatePython("http://192.168.100.26:5002/play?track=tu_ausencia");
            } else if (recognizedText.equalsIgnoreCase("reproducir el ultimo")) {
                activatePython("http://192.168.100.26:5002/play?track=el_ultimo");
            }
        }
    }

    public void QuintaEscencia(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para el álbum "La Quinta Esencia"
            if (recognizedText.equalsIgnoreCase("reproducir que vengan ya")) {
                activatePython("http://192.168.100.26:5002/play?track=que_vengan_ya");
            } else if (recognizedText.equalsIgnoreCase("reproducir ulises")) {
                activatePython("http://192.168.100.26:5002/play?track=ulises");
            } else if (recognizedText.equalsIgnoreCase("reproducir tu recuerdo me bastara")) {
                activatePython("http://192.168.100.26:5002/play?track=tu_recuerdo_me_bastara");
            } else if (recognizedText.equalsIgnoreCase("reproducir la vieja guardia")) {
                activatePython("http://192.168.100.26:5002/play?track=la_vieja_guardia");
            } else if (recognizedText.equalsIgnoreCase("reproducir un poco de fe")) {
                activatePython("http://192.168.100.26:5002/play?track=un_poco_de_fe");
            } else if (recognizedText.equalsIgnoreCase("reproducir el mas triste adios")) {
                activatePython("http://192.168.100.26:5002/play?track=el_mas_triste_adios");
            } else if (recognizedText.equalsIgnoreCase("reproducir buscando una luz")) {
                activatePython("http://192.168.100.26:5002/play?track=buscando_una_luz");
            } else if (recognizedText.equalsIgnoreCase("reproducir ha pasado su tiempo")) {
                activatePython("http://192.168.100.26:5002/play?track=ha_pasado_su_tiempo");
            } else if (recognizedText.equalsIgnoreCase("reproducir redencion")) {
                activatePython("http://192.168.100.26:5002/play?track=redencion");
            } else if (recognizedText.equalsIgnoreCase("reproducir mirando al mar")) {
                activatePython("http://192.168.100.26:5002/play?track=mirando_al_mar");
            } else if (recognizedText.equalsIgnoreCase("reproducir más allá")) {
                activatePython("http://192.168.100.26:5002/play?track=mas_allá");
            }
        }
    }

    public void Rayden(ArrayList<String> matches) {
        if (matches != null && !matches.isEmpty()) {
            String recognizedText = matches.get(0);

            // Comandos para el álbum de Rayden
            if (recognizedText.equalsIgnoreCase("reproducir el mejor de tus errores")) {
                activatePython("http://192.168.100.26:5002/play?track=el_mejor_de_tus_errores");
            } else if (recognizedText.equalsIgnoreCase("reproducir haz de luz")) {
                activatePython("http://192.168.100.26:5002/play?track=haz_de_luz");
            } else if (recognizedText.equalsIgnoreCase("reproducir la mujer cactus y el hombre globo")) {
                activatePython("http://192.168.100.26:5002/play?track=la_mujer_cactus_y_el_hombre_globo");
            } else if (recognizedText.equalsIgnoreCase("reproducir mentiras de jarabe")) {
                activatePython("http://192.168.100.26:5002/play?track=mentiras_de_jarabe");
            }
        }
    }


}
