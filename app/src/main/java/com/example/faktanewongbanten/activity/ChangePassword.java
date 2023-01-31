package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class ChangePassword extends AppCompatActivity {
    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ImageView back;
    public SharedPreferences sh;
    public Button btnUpdate,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = ChangePassword.this;
        requestQueue = Volley.newRequestQueue(this);

        btnUpdate = findViewById(R.id.btnUpdatePassword);
        btnCancel = findViewById(R.id.btnCancelUpdate);
        back = findViewById(R.id.back);

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
        EditText etOldpass = findViewById(R.id.et_password_lama);
        EditText etPassbaru = findViewById(R.id.et_password_baru);
        EditText etConfirmPass = findViewById(R.id.et_confirm_password);
        String oldpass = etOldpass.getText().toString().trim();
        String newpass = etPassbaru.getText().toString().trim();
        String confirmpass = etConfirmPass.getText().toString().trim();
        String linkSimpan = "https://dimas.bantani.net.id/github/edit_author";
        SharedPreferences sh = getSharedPreferences("author", Context.MODE_PRIVATE);
        if (!newpass.equals(confirmpass)){
            Toast.makeText(context, "Password Tidak sama", Toast.LENGTH_LONG).show();
        }else {
            stringRequest = new StringRequest(Request.Method.POST, linkSimpan, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("success")) {
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("password",confirmpass);
                        editor.apply();
                        Toast.makeText(context, "Ubah password Berhasil", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        //Displaying an error message on toast
                        Toast.makeText(context, "Ubah password Gagal", Toast.LENGTH_LONG).show();
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
                    params.put("old_password", oldpass);
                    params.put("new_password", confirmpass);
                    //...
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}