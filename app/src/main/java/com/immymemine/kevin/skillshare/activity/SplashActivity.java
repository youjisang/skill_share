package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;

public class SplashActivity extends AppCompatActivity {

    // 0. background thread 에서 data 준비하기

    // 1. 사용자 이전에 로그인 이력 있으면 건너뛰기

    // 2. 없으면 splash page 보여주기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // glide gif
        Glide.with(this).load(R.raw.splash).into((ImageView) findViewById(R.id.splash_image));
        // button get_started
        CardView get_started = findViewById(R.id.get_started);
        get_started.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            // Sign up page
        });

        // textview sign_in
        TextView text_view_sign_in = findViewById(R.id.text_view_sign_in);
        text_view_sign_in.setOnClickListener(v -> {
            // Sign in page
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
        });
    }
}
