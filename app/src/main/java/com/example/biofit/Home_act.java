package com.example.biofit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Home_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Referencia al ViewPager y al TabLayout
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Crear el adaptador para el ViewPager
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new Fragment1(); // Reemplaza Fragment1 con el primer fragment que quieras mostrar
                    case 1:
                        return new Fragment2(); // Reemplaza Fragment2 con el segundo fragment que quieras mostrar
                    case 2:
                        return new Fragment3(); // Reemplaza Fragment3 con el tercer fragment que quieras mostrar
                    case 3:
                        return new Fragment4();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 4; // Reemplaza 3 con el número total de fragments que quieras mostrar
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Perfil";
                    case 1:
                        return "Nutrición";
                    case 2:
                        return "Fitness";
                    case 3:
                        return "Biofit";
                    default:
                        return null;
                }
            }
        };

        // Asignar el adaptador al ViewPager
        viewPager.setAdapter(adapter);

        // Enlazar el ViewPager con el TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }


}
