package com.example.yajmana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.yajmana.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        /*
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },3000);
        */
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause","Inside Splash onPause()");
        //startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("onRestart","Inside Splash onRestart()");
    }

}