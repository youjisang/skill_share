package com.immymemine.kevin.skillshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.immymemine.kevin.skillshare.R;

import com.immymemine.kevin.skillshare.adapter.TutorRecyclerViewAdapter;

public class TutorActivity extends AppCompatActivity {

    RecyclerView TutorRecyclerView;
    TutorRecyclerViewAdapter tutorRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        initView();
        initRecycler();
    }

    private void initView(){
        TutorRecyclerView = findViewById(R.id.recycler_view_teaching);
    }
    private void initRecycler(){
        TutorRecyclerView.setHasFixedSize(true);
        tutorRecyclerViewAdapter = new TutorRecyclerViewAdapter();
        TutorRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TutorRecyclerView.setAdapter(tutorRecyclerViewAdapter);
    }
}
