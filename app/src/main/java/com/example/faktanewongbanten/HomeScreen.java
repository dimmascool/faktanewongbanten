package com.example.faktanewongbanten;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    Context context;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<ModelBerita> mItems;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    RecyclerView.LayoutManager mManager;
    ViewPager2 imageviewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        context=HomeScreen.this;

        getSupportActionBar().hide();

        requestQueue = Volley.newRequestQueue(this);
        mRecyclerView = findViewById(R.id.recyclerview);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterHome(context, mItems);
        mRecyclerView.setAdapter(mAdapter);
        loadjson();
    }

    private void loadjson() {

        final String link_history = "https://dimas.bantani.net.id/github/get_all_berita";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,link_history,null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mItems.clear();
                try {
                    ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(context, mItems);
                    imageviewpager = findViewById(R.id.imageViewPager);
                    imageviewpager.setAdapter(viewPager2Adapter);
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