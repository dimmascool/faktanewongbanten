package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.model.ModelAuthor;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Context context;
     public EditText etUsername, etPassword, etNickname, etBio, etAlamat,etNohp;
     Button btnRegister;
     TextView loginpage;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btn_register);
        loginpage = findViewById(R.id.loginpage);
        context = Register.this;
        requestQueue = Volley.newRequestQueue(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanAkun();
            }
        });
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Register.this, Login.class);
                startActivity(registerIntent);
            }
        });
    }
    private void simpanAkun() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etNickname = findViewById(R.id.et_nickname);
        etBio = findViewById(R.id.et_bio);
        etAlamat = findViewById(R.id.et_alamat);
        etNohp = findViewById(R.id.et_nohp);
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String nickname = etNickname.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String nohp = etNohp.getText().toString().trim();
        String linkSimpan = "https://dimas.bantani.net.id/github/create_author";
        stringRequest = new StringRequest(Request.Method.POST, linkSimpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(context, "Register Berhasil", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context, Login.class));
                } else {
                    //Displaying an error message on toast
                    Toast.makeText(context, "Register Gagal", Toast.LENGTH_LONG).show();
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
                params.put("username", username);
                params.put("password", password);
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
