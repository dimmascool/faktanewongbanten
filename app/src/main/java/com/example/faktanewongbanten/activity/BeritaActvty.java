package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.squareup.picasso.Picasso;

public class BeritaActvty extends AppCompatActivity {
    RequestQueue requestQueue;
    Context context;
    TextView judul,tanggal,penulis,isi;
    ImageView gambar,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_actvty);
        context=BeritaActvty.this;
        requestQueue = Volley.newRequestQueue(this);

        judul = findViewById(R.id.tvJudul);
        tanggal = findViewById(R.id.tvTanggal);
        penulis = findViewById(R.id.tvPenulis);
        isi = findViewById(R.id.tvIsiBerita);
        gambar = findViewById(R.id.ivGambar);
        back = findViewById(R.id.ivBack);

        judul.setText(getIntent().getStringExtra("judul"));
        tanggal.setText(getIntent().getStringExtra("tanggal"));
        penulis.setText(getIntent().getStringExtra("author"));
        isi.setText(getIntent().getStringExtra("isi"));

        Picasso.get().load(getIntent().getStringExtra("gambar"))
                .fit()
                .error(R.drawable.ic_launcher_background)
               .into(gambar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}