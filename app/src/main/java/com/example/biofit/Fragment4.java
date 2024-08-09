package com.example.biofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {

    // Arrays de nombres de las imágenes y textos para el ListView
    private final String[] nombresImagenes = {
            "imagen1", "imagen2", "imagen3", "imagen4", "imagen5",
            "imagen6", "imagen7", "imagen8"
    };

    private final String[] titulos = {
            "Inteligencia Dash",
            "Examenes Transplante",
            "Inmunosupresión",
            "Análisis Sanguineo",
            "Elimina Líquidos",
            "Suplementos Alimenticios",
            "Instrucciones Generales",
            "Descubre BioFit"  // Moved to the end
    };

    private final String[] descripciones = {
            "Inteligencia Artificial centralizada capaz de ayudar a los pacientes a comprender mejor su enfermedad y qué tipos de alimentos deben consumir según sus parámetros sanguíneos.",
            "Conoce la lista de exámenes que tienes que completar para llegar a la meta del trasplante renal, recuerda que es importante cuidarse en el camino para llegar a una buena operación.",
            "Si ya te transplantaste y tienes dificultad para entender tus medicamentos, puedes conocerlos aquí, y por qué cada uno de ellos es tan importante para mantener nuestro sistema controlado y que nuestro riñón permanezca funcionando.",
            "Aquí podrás tener excelentes recomendaciones nutricionales a partir de tus exámenes de sangre, procura ingresar correctamente tus principales parámetros como potasio, fósforo y hemoglobina.",
            "Aquí vas a encontrar las mejores formas para eliminar líquidos del cuerpo, aplica solo si estás en diálisis. Si eres transplantado, debes hidratarte lo más posible.",
            "Descubre cómo fortalecerte post-diálisis con estos suplementos alimenticios, recuerda consultar antes a tu médico, pero en su mayoría puedes resultar beneficioso por su aporte en vitaminas.",
            "Aquí podrás encontrar instrucciones para poder desempeñar de manera exitosa la experiencia que ofrece BioFit. Es muy importante que las leas antes de usar la aplicación; solo así tendrás éxito y comprenderás sus reales beneficios.",
            "Descubre qué es la experiencia BioFit y quién la ha creado, puedes ofrecer recomendaciones y mejorar nuestra aplicación para ofrecer un mejor servicio."  // Moved to the end
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        // Crear un adaptador personalizado
        MyListAdapter adapter = new MyListAdapter(getActivity(), nombresImagenes, titulos, descripciones);

        // Obtener el ListView desde el layout del fragmento
        ListView listView = view.findViewById(R.id.listaMedi);

        // Establecer el adaptador en el ListView
        listView.setAdapter(adapter);

        return view;
    }

    private static class MyListAdapter extends ArrayAdapter<String> {
        private final String[] nombresImagenes;
        private final String[] titulos;
        private final String[] descripciones;

        public MyListAdapter(@NonNull Context context, String[] nombresImagenes, String[] titulos, String[] descripciones) {
            super(context, R.layout.list_item, nombresImagenes);
            this.nombresImagenes = nombresImagenes;
            this.titulos = titulos;
            this.descripciones = descripciones;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.logo);
            TextView tituloView = convertView.findViewById(R.id.titulo);
            TextView descripcionView = convertView.findViewById(R.id.descripcion);

            // Obtener la imagen desde los recursos
            int id = getContext().getResources().getIdentifier(nombresImagenes[position], "drawable", getContext().getPackageName());
            imageView.setImageResource(id);

            // Establecer el título y la descripción
            tituloView.setText(titulos[position]);
            descripcionView.setText(descripciones[position]);



            // Agregar un OnClickListener a la vista
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción en clic (opcional)
                    if ("imagen1".equals(nombresImagenes[position])) {
                        Intent intent = new Intent(getContext(), Nutrifit_IA.class);
                        getContext().startActivity(intent);
                    }
                }
            });

            return convertView;
        }
    }
}
