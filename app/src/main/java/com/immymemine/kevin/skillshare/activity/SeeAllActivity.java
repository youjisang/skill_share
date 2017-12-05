package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.AboutAdapter;

import static java.security.AccessController.getContext;

public class SeeAllActivity extends AppCompatActivity {
    int type;
    AboutAdapter aboutAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        initview();



        // type 에 따라 data 를 가져와서
        // 미리 그려 놓은 recycler view 에 넣어주자

        // 카테고리 별 see all 은 discover 와 같은 형태 view 사용 <<< ex) Because You Follow Business ...

        // 1. 인텐트 받아옴

        Intent intent = getIntent();
        type=intent.getIntExtra("seeall",1);
        Log.e("getintent","intent value"+type);

        // 2. 인텐트에서 받아온 구분 값을 어댑텅 설정
        setRecyclerView();
    }

    private void setRecyclerView() {
        aboutAdapter = new AboutAdapter(this,type);
        recyclerView.setAdapter(aboutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initview(){
        ((TextView)findViewById(R.id.toolbar_title)).setText(type+"");
        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.recycler_view_seeAll);
    }
}
