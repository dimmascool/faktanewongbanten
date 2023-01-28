package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.faktanewongbanten.R;

public class SplashScreen extends AppCompatActivity {
    TextView pakta,wong,banten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

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