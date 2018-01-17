package com.immymemine.kevin.skillshare.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.immymemine.kevin.skillshare.R;



import java.util.ArrayList;
import java.util.List;

//public class SavedActivity extends AppCompatActivity {
//
//
//
//
//    RecyclerView savedRecyclerView;
//    List<Saved> savedList;
//    ImageButton toolbar_button_back;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_saved);
//
//        initiateView();
//
//        settingDummy();
//
//        initRecyclerView();
//
//
//    }
//
//    private void settingDummy() {
//        savedList = new ArrayList<Saved>();
//        savedList.add(new Saved("music", "Jay", "https://learn.canva.com/wp-content/uploads/2015/10/40-People-Through-History-Who-Changed-Design-For-Good-04.png", "3:54", "99%", "6.3k"));
//        savedList.add(new Saved("codings", "allen", "http://cfile23.uf.tistory.com/image/9907AF3359C0C1153C71D2", "4:02", "99%", "2.3k"));
//        savedList.add(new Saved("design", "kevin", "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH82423479_w300_h300.jpg", "5:32", "99%", "4.7k"));
//
//    }
//
//    private void initiateView() {
//        savedRecyclerView = findViewById(R.id.recycler_view_saved);
//        toolbar_button_back = findViewById(R.id.toolbar_button_back);
//        toolbar_button_back.setOnClickListener(v -> finish());
//
//    }
//
//    private void initRecyclerView() {
//        savedRecyclerView.setHasFixedSize(true);
//        SavedRecyclerViewAdapter adapter = new SavedRecyclerViewAdapter(this, savedList, savedList.size());
//        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        savedRecyclerView.setAdapter(adapter);
//
//    }
//
//}
