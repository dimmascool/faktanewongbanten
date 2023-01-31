package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;

import java.util.HashMap;
import java.util.Map;

public class EditAkun extends AppCompatActivity {
    Context context;
    public EditText etNickname, etBio, etAlamat,etNohp;
    public Button btnUpdate,btnCancel;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ImageView back;
    public SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        context = EditAkun.this;
        requestQueue = Volley.newRequestQueue(this);
        SharedPreferences sh = getSharedPreferences("author", Context.MODE_PRIVATE);
        etNickname = findViewById(R.id.et_nickname);
        etBio = findViewById(R.id.et_bio);
        etAlamat = findViewById(R.id.et_alamat);
        etNohp = findViewById(R.id.et_nohp);
        btnUpdate = findViewById(R.id.btnUpdateAccount);
        btnCancel = findViewById(R.id.btnCancelUpdate);
        back = findViewById(R.id.back);

        etNickname.setText(sh.getString("username",""));
        etBio.setText(sh.getString("bio",""));
        etAlamat.setText(sh.getString("alamat",""));
        etNohp.setText(sh.getString("no_telpon",""));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanAkun();
            }
        });
    }
    private void simpanAkun() {
        String nickname = etNickname.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String nohp = etNohp.getText().toString().trim();
        String linkSimpan = "https://dimas.bantani.net.id/github/edit_author";
        SharedPreferences sh = getSharedPreferences("author", Context.MODE_PRIVATE);
        stringRequest = new StringRequest(Request.Method.POST, linkSimpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("bio",bio);
                    editor.putString("nickname",nickname);
                    editor.putString("alamat",alamat);
                    editor.putString("no_telpon",nohp);
                    editor.apply();
                    Toast.makeText(context, "Ubah data Berhasil", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //Displaying an error message on toast
                    Toast.makeText(context, "Ubah data Gagal", Toast.LENGTH_LONG).show();
                    Log.e("aaa", response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", sh.getString("username",""));
                params.put("nickname", nickname);
                params.put("alamat",alamat);
                params.put("no_telpon",nohp );
                params.put("bio",bio );

                //...
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}