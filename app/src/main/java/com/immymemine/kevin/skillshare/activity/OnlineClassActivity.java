package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

public class OnlineClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        String title = getIntent().getStringExtra("title");

        // 1. id 를 이용한 통신

        // 2. model object 에 담아주고

        // 3. view 에서 model object 를 사용

        ((TextView)findViewById(R.id.tv_test)).setText(title);
    }
}
