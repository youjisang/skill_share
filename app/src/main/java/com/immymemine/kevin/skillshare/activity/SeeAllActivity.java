package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.SeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

public class SeeAllActivity<T> extends AppCompatActivity {
    int type;
    SeeAllRecyclerViewAdapter seeAllRecyclerViewAdapter;
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
        type = intent.getIntExtra(ConstantUtil.SEE_ALL_FLAG,1);

        // 2. 인텐트에서 받아온 구분 값을 어댑터 설정

    }

    List<T> data;
    private void networking() {
        //retrofit...

    }

    private void setRecyclerView(List<T> data) {
        seeAllRecyclerViewAdapter = new SeeAllRecyclerViewAdapter(this, type);
        recyclerView.setAdapter(seeAllRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initview() {
        int stringId = 0;
        switch (type) {
            case ConstantUtil.PROJECT_ITEM:
                stringId = R.string.project;
                break;
            case ConstantUtil.CLASS_ITEM:
                break;
        }

        if(stringId != 0)
            ((TextView)findViewById(R.id.toolbar_title)).setText(getString(stringId));

        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.recycler_view_seeAll);
    }
}
