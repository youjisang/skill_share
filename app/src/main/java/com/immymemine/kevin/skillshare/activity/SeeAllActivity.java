package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

public class SeeAllActivity extends AppCompatActivity {
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);
        type = getIntent().getStringExtra("Type");
        ((TextView)findViewById(R.id.toolbar_title)).setText(type);
        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());

        // type 에 따라 data 를 가져와서
        // 미리 그려 놓은 recycler view 에 넣어주자

        // 카테고리 별 see all 은 discover 와 같은 형태 view 사용 <<< ex) Because You Follow Business ...
    }

}
