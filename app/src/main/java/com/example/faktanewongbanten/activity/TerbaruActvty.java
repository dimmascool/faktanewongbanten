package com.example.faktanewongbanten.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.adapter.AdapterHome;
import com.example.faktanewongbanten.model.ModelBerita;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TerbaruActvty extends AppCompatActivity {
    Context context;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<ModelBerita> mItems;
    RequestQueue requestQueue;
    RecyclerView.LayoutManager mManager;


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigasi = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(context, HomeScreen.class));
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    return true;
                case R.id.terbaru:
                    Toast.makeText(context, "Kamu Sedang Berada Di Terbaru", Toast.LENGTH_SHORT).show();
                    return false;
                case R.id.trending:
                    startActivity(new Intent(context, TrendingActvty.class));
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    return true;
                case R.id.kategori:
                    startActivity(new Intent(context, KategoriActvty.class));
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    return true;
                case R.id.menu:
                    startActivity(new Intent(context, AkunSaya.class));
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terbaru_actvty);
        context=TerbaruActvty.this;

        requestQueue = Volley.newRequestQueue(this);

        mRecyclerView = findViewById(R.id.rvTerbaru);
        BottomNavigationView botnav = findViewById(R.id.botnav);
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi);
        mItems = new ArrayList<>();

        mManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mManager);

        mAdapter = new AdapterHome(context, mItems);

        mRecyclerView.setAdapter(mAdapter);
        loadjson();
    }
    private void loadjson() {
        final String link_history = "https://dimas.bantani.net.id/github/get_berita?get_by=newest";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,link_history,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mItems.clear();
                        try {
                            JSONArray jsonArray = response.getJSONArray("data_berita");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ModelBerita mb = new ModelBerita();
                                mb.setAuthor(jsonObject.getString("author"));
                                mb.setIsi(jsonObject.getString("isi"));
                                mb.setJudul(jsonObject.getString("judul"));
                                mb.setUrl_thumbnail(jsonObject.getString("url_thumbnail"));
                                mb.setId_berita(jsonObject.getString("id_berita"));
                                mb.setKategori(jsonObject.getString("kategori"));
                                mb.setTanggal_dibuat(jsonObject.getString("tanggal_dibuat"));
                                mb.setTanggal_diupdate(jsonObject.getString("tanggal_diupdate"));
                                mb.setDilihat(jsonObject.getString("dilihat"));
                                mItems.add(mb);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
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