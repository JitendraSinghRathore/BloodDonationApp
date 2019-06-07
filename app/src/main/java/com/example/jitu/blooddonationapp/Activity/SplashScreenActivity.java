package com.example.jitu.blooddonationapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jitu.blooddonationapp.R;

public class SplashScreenActivity extends AppCompatActivity implements Runnable {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       getSupportActionBar().hide();

        ImageView imageView = (ImageView) findViewById(R.id.image);
        android.os.Handler hd = new android.os.Handler();
        hd.postDelayed(this, 3000);
    }

    @Override
    public void run() {
        Intent intent=new Intent(SplashScreenActivity.this, FrontPageActivity.class);
        startActivity(intent);
    }
}


