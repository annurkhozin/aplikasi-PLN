package com.kalinesia.tubesmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int splashTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashScreen();
    }

    private void splashScreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // menuju halaman home
                Intent getStarted = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(getStarted);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

        }, splashTime);
    }
}
