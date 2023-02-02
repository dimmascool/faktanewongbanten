package com.example.faktanewongbanten.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faktanewongbanten.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings("ALL")
public class AkunSaya extends AppCompatActivity {
    TextView nama,akunsaya,konten,logout,bio,btnBio,btnCP;
    Button addberita;
    Context context;
    @SuppressWarnings("unused")
    public SharedPreferences sh;
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigasi =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
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
        setContentView(R.layout.activity_akun_saya);
        context=AkunSaya.this;
        BottomNavigationView botnav = findViewById(R.id.botnav);
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi);
        SharedPreferences sh = getSharedPreferences("author", Context.MODE_PRIVATE);
        nama = findViewById(R.id.tvUsername);
        akunsaya = findViewById(R.id.myprofil);
        konten = findViewById(R.id.tvKonten);
        logout = findViewById(R.id.tvLogout);
        bio = findViewById(R.id.tvBio);
        btnBio = findViewById(R.id.ubahbiodata);
        btnCP = findViewById(R.id.ubahpassword);
        addberita = findViewById(R.id.btn_tambahberita);
        nama.setText(sh.getString("nickname", ""));
        bio.setText(sh.getString("bio",""));

        btnBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EditAkun.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        btnCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ChangePassword.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        konten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, KontenSayaActvty.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        akunsaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EditAkun.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        addberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TambahBeritaActvty.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, HomeScreen.class));
                SharedPreferences.Editor editor = sh.edit();
                editor.clear();
                editor.putBoolean("login?",false);
                editor.apply();
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
    }
}