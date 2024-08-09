package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import database.API.Alimento;

public class AlimentosAdapter extends ArrayAdapter<Alimento> {
    private ArrayList<Alimento> alimentos;

    public AlimentosAdapter(Context context, ArrayList<Alimento> alimentos) {
        super(context, 0, alimentos);
        this.alimentos = alimentos;
    }

    @Override
    public int getCount() {
        return alimentos.size();
    }

    @Override
    public Alimento getItem(int position) {
        return alimentos.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        Alimento alimento = getItem(position);
        textView.setText(alimento.getNombre()); // Asumiendo que hay un método getNombre() en la clase Alimento
        return convertView;
    }
}
