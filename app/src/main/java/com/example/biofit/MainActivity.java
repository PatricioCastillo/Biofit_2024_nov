package com.example.biofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.biofit.dietasCalorias.Listado_dieta_lunes;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Referenciar el ImageView
        imageView = findViewById(R.id.logo);

        // Cargar y aplicar la animación de aparición
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
      //  Animation translateImage = AnimationUtils.loadAnimation(this, R.anim.translate);
        imageView.startAnimation(fadeInAnimation);
       // imageView.startAnimation(translateImage);

        // Establecer la visibilidad del ImageView a VISIBLE después de la animación
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Iniciar(View view) {
        Intent i = new Intent(this, Home_act.class);
        startActivity(i);
    }

    public void VerListados(View view) {
        Intent listado = new Intent(this, Listado_dieta_lunes.class);
        startActivity(listado);
    }

}
