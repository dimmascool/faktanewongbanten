package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.faktanewongbanten.R;

@SuppressWarnings("ALL")
@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    Context context;
    TextView pakta,wong,banten;
    public SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        SharedPreferences sh = getSharedPreferences("author", Context.MODE_PRIVATE);
        if(!sh.contains("login?")){
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean("login?",false);
            editor.apply();
        }
        pakta = findViewById(R.id.pakta);
        wong = findViewById(R.id.wong);
        banten = findViewById(R.id.banten);

        pakta.startAnimation(topAnimation);
        wong.startAnimation(middleAnimation);
        banten.startAnimation(bottomAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, 4500);
    }
}