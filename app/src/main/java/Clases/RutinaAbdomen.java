package Clases;

import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_lunes;
import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_martes;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_domingo;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_jueves;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_lunes;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_martes;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_miercoles;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_sabado;
import com.example.biofit.rutinaAbdomen.abdomen_altaInt_viernes;
import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_domingo;
import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_jueves;
import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_miercoles;
import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_sabado;
import com.example.biofit.rutinaAbdomen.abdomen_bajaInt_viernes;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_domingo;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_jueves;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_lunes;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_martes;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_miercoles;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_sabado;
import com.example.biofit.rutinaAbdomen.abdomen_mediaInt_viernes;
import java.util.HashMap;

public class RutinaAbdomen {
    // Define un HashMap para mapear cada día de la semana con su actividad correspondiente
    private HashMap<String, Class<?>> actividadesPorDiaBajaIntensidad;
    private HashMap<String, Class<?>> actividadesPorDiaMediaIntensidad;
    private HashMap<String, Class<?>> actividadesPorDiaAltaIntensidad;

    // Constructor para inicializar los HashMaps
    public RutinaAbdomen() {
        actividadesPorDiaBajaIntensidad = new HashMap<>();
        actividadesPorDiaMediaIntensidad = new HashMap<>();
        actividadesPorDiaAltaIntensidad = new HashMap<>();

        // Inicializar los HashMaps con las actividades correspondientes para cada día de la semana y cada intensidad
        inicializarActividadesPorDiaBajaIntensidad();
        inicializarActividadesPorDiaMediaIntensidad();
        inicializarActividadesPorDiaAltaIntensidad();
    }

    // Métodos para inicializar los HashMaps con las actividades correspondientes para cada día de la semana y cada intensidad
    private void inicializarActividadesPorDiaBajaIntensidad() {
        actividadesPorDiaBajaIntensidad.put("Lunes", abdomen_bajaInt_lunes.class);
        actividadesPorDiaBajaIntensidad.put("Martes", abdomen_bajaInt_martes.class);
        actividadesPorDiaBajaIntensidad.put("Miercoles", abdomen_bajaInt_miercoles.class);
        actividadesPorDiaBajaIntensidad.put("Jueves", abdomen_bajaInt_jueves.class);
        actividadesPorDiaBajaIntensidad.put("Viernes", abdomen_bajaInt_viernes.class);
        actividadesPorDiaBajaIntensidad.put("Sábado", abdomen_bajaInt_sabado.class);
        actividadesPorDiaBajaIntensidad.put("Domingo", abdomen_bajaInt_domingo.class);
    }

    private void inicializarActividadesPorDiaMediaIntensidad() {
        actividadesPorDiaMediaIntensidad.put("Lunes", abdomen_mediaInt_lunes.class);
        actividadesPorDiaMediaIntensidad.put("Martes", abdomen_mediaInt_martes.class);
        actividadesPorDiaMediaIntensidad.put("Miercoles", abdomen_mediaInt_miercoles.class);
        actividadesPorDiaMediaIntensidad.put("Jueves", abdomen_mediaInt_jueves.class);
        actividadesPorDiaMediaIntensidad.put("Viernes", abdomen_mediaInt_viernes.class);
        actividadesPorDiaMediaIntensidad.put("Sábado", abdomen_mediaInt_sabado.class);
        actividadesPorDiaMediaIntensidad.put("Domingo", abdomen_mediaInt_domingo.class);
    }

    private void inicializarActividadesPorDiaAltaIntensidad() {
        actividadesPorDiaAltaIntensidad.put("Lunes", abdomen_altaInt_lunes.class);
        actividadesPorDiaAltaIntensidad.put("Martes", abdomen_altaInt_martes.class);
        actividadesPorDiaAltaIntensidad.put("Miercoles", abdomen_altaInt_miercoles.class);
        actividadesPorDiaAltaIntensidad.put("Jueves", abdomen_altaInt_jueves.class);
        actividadesPorDiaAltaIntensidad.put("Viernes", abdomen_altaInt_viernes.class);
        actividadesPorDiaAltaIntensidad.put("Sábado", abdomen_altaInt_sabado.class);
        actividadesPorDiaAltaIntensidad.put("Domingo", abdomen_altaInt_domingo.class);    }

    // Métodos para acceder a los HashMaps desde otras clases
    public HashMap<String, Class<?>> getActividadesPorDiaBajaIntensidad() {
        return actividadesPorDiaBajaIntensidad;
    }

    public HashMap<String, Class<?>> getActividadesPorDiaMediaIntensidad() {
        return actividadesPorDiaMediaIntensidad;
    }

    public HashMap<String, Class<?>> getActividadesPorDiaAltaIntensidad() {
        return actividadesPorDiaAltaIntensidad;
    }
}
