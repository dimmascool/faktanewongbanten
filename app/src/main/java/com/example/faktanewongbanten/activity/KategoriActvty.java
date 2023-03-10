package com.example.faktanewongbanten.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.adapter.AdapterKategori;
import com.example.faktanewongbanten.model.ModelKategori;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class KategoriActvty extends AppCompatActivity {

    Context context;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RequestQueue requestQueue;
    RecyclerView.LayoutManager mManager;
    ArrayList<ModelKategori> mItems;

    SharedPreferences sh;
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
                    Toast.makeText(context, "Kamu Sedang Berada Di Kategori", Toast.LENGTH_SHORT).show();
                    return false;
                case R.id.menu:
                    sh = getSharedPreferences("author", Context.MODE_PRIVATE);
                    if (sh.getBoolean("login?",true)){
                        startActivity(new Intent(context, AkunSaya.class));
                    }else{
                        startActivity(new Intent(context, MenuNonLogin.class));
                    }
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_actvty);
        context=KategoriActvty.this;
        requestQueue = Volley.newRequestQueue(this);
        BottomNavigationView botnav = findViewById(R.id.botnav);
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi);
        requestQueue = Volley.newRequestQueue(this);
        mRecyclerView = findViewById(R.id.rvKategori);
        mItems = new ArrayList<>();
        mManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mManager);

        mAdapter = new AdapterKategori(context, mItems);
        mRecyclerView.setAdapter(mAdapter);
        jsonParse();
    }

    private void jsonParse() {
        String url = "https://dimas.bantani.net.id/github/get_all_kategori";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONArray response) {
                mItems.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ModelKategori mk = new ModelKategori();
                        mk.setKategori(jsonObject.getString("kategori"));
                        mk.setId_kategori(jsonObject.getString("id_kategori"));
                        mk.setUrl_gambar_kategori(jsonObject.getString("url_gambar_kategori"));
                        mk.setDeskripsi(jsonObject.getString("deskripsi"));
                        mItems.add(mk);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, Throwable::printStackTrace);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}