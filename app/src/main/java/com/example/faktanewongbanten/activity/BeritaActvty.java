package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.adapter.ViewPager2Adapter;
import com.example.faktanewongbanten.model.ModelBerita;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        loadjson();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });
    }
    private void loadjson(){
        String idberita = getIntent().getStringExtra("id");
        final String link_history = "https://dimas.bantani.net.id/github/get_berita?id_berita="+idberita;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,link_history,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("data_berita");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                judul.setText(jsonObject.getString("judul"));
                                tanggal.setText(jsonObject.getString("tanggal_dibuat"));
                                penulis.setText(jsonObject.getString("author"));
                                isi.setText(jsonObject.getString("isi"));

                                Picasso.get().load(jsonObject.getString("url_thumbnail"))
                                        .fit()
                                        .centerCrop()
                                        .error(R.drawable.ic_launcher_background)
                                        .into(gambar);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}