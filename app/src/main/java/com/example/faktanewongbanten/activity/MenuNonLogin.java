package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.faktanewongbanten.R;

public class MenuNonLogin extends AppCompatActivity {
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_non_login);
        login = findViewById(R.id.tvUsername);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuNonLogin.this, Login.class);
                startActivity(intent);
            }
        });
    }
}