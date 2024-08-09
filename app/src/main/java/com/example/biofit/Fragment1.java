package com.example.biofit;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ekn.gruzer.gaugelibrary.FullGauge;

public class Fragment1 extends Fragment {
    private ImageView perfil, heart;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);


        FullGauge peso_gauge = view.findViewById(R.id.peso);
        FullGauge calorias_gauge = view.findViewById(R.id.calorias);
        FullGauge adicional_gauge = view.findViewById(R.id.adicional);

        float valor = 70.0f;
        float valorFull = 100f;
        peso_gauge.setValue(valor);
        calorias_gauge.setValue(valor);
        adicional_gauge.setValue(valor);


        peso_gauge.setGaugeBackGroundColor(Color.GREEN);
        calorias_gauge.setGaugeBackGroundColor(Color.BLUE);
        adicional_gauge.setGaugeBackGroundColor(Color.RED);

        peso_gauge.setDisplayValuePoint(true);


        peso_gauge.setValueColor(Color.GREEN);
        calorias_gauge.setValueColor(Color.BLUE);
        adicional_gauge.setValueColor(Color.RED);

        // Cargar y mostrar el GIF animado en el ImageView
        ImageView gifImageView = view.findViewById(R.id.gifImageView);
        Glide.with(this).asGif().load(R.drawable.line_dos).into(gifImageView);

        //ImageView heartGif = view.findViewById(R.id.heartG);
      //  Glide.with(this).asGif().load(R.drawable.heart).into(heartGif);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referenciar el ImageView dentro del método onViewCreated()
        perfil = view.findViewById(R.id.perfil_image);
        heart = view.findViewById(R.id.heartG);

        // Cargar y aplicar la animación de aparición
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        perfil.startAnimation(fadeInAnimation);
        heart.startAnimation(fadeInAnimation);
    }
}