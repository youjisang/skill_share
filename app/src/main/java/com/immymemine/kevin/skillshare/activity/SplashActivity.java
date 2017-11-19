package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;

public class SplashActivity extends AppCompatActivity {

    // 0. background thread 에서 data 가져오기

    // 1. 사용자 이전에 로그인 이력 있으면 건너뛰기

    // 2. 없으면 splash page 보여주기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // glide gif
        Glide.with(this).load(R.raw.splash).into((ImageView) findViewById(R.id.splash_image));
        CardView get_started = findViewById(R.id.get_started);
        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // Sign up page
            }
        });

        TextView text_view_sign_in = findViewById(R.id.text_view_sign_in);
        text_view_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign in page
            }
        });
    }
}
