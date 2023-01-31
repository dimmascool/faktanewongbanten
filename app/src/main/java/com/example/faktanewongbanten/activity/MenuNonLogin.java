package com.example.faktanewongbanten.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faktanewongbanten.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuNonLogin extends AppCompatActivity {
    TextView login;
    Context context;
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigasi =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home:
                            startActivity(new Intent(context, HomeScreen.class));
                            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                            return true;
                        case R.id.terbaru:
                            startActivity(new Intent(context, TerbaruActvty.class));
                            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                            return true;
                        case R.id.trending:
                            startActivity(new Intent(context, TrendingActvty.class));
                            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                            return true;
                        case R.id.kategori:
                            startActivity(new Intent(context, KategoriActvty.class));
                            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                            return true;
                        case R.id.menu:
                            Toast.makeText(context, "Kamu Sedang Berada Di Menu", Toast.LENGTH_SHORT).show();
                            return false;
                    }
                    return false;
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_non_login);
        login = findViewById(R.id.tvUsername);
        context=MenuNonLogin.this;
        BottomNavigationView botnav = findViewById(R.id.botnav);
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi);
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuNonLogin.this, Login.class);
                startActivity(intent);
            }
        });
    }
}