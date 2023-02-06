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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class Login extends AppCompatActivity {
    Context context;
     Button btnLogin;
     TextView registerpage;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ImageView back;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = Login.this;
        btnLogin = findViewById(R.id.btn_login);
        registerpage = findViewById(R.id.registerpage);
        back = findViewById(R.id.back);
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = context.getSharedPreferences("author", Context.MODE_PRIVATE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUsername = findViewById(R.id.et_username);
                EditText etPassword = findViewById(R.id.et_password);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();
                } else {
                    login(username, password);
                }
            }
        });

        registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });
    }

    private void login(String Username, String Password) {
        String URL = "https://dimas.bantani.net.id/github/login_author";
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("message");
                    JSONObject data = jsonObject.getJSONObject("data_author");
//                    Log.e("asd", data.getString("author_id"));
                    if (status.equals("login success")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id",data.getString("id"));
                        editor.putString("username",data.getString("username"));
                        editor.putString("password",data.getString("password"));
                        editor.putString("nickname",data.getString("nickname"));
                        editor.putString("status",data.getString("status"));
                        editor.putString("alamat",data.getString("alamat"));
                        editor.putString("no_telpon",data.getString("no_telpon"));
                        editor.putString("bio",data.getString("bio"));
                        editor.putBoolean("login?",true);
                        editor.apply();
                        startActivity(new Intent(context, HomeScreen.class));
                        Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    } else {
                        Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                Log.e("Volley", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", Username);
                params.put("password", Password);
                //...
                return params;
            }

// menambahkan request ke antrian
        };
        requestQueue.add(stringRequest);
    }
}
