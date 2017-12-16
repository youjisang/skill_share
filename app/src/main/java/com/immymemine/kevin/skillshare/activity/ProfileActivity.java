package com.immymemine.kevin.skillshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.immymemine.kevin.skillshare.R;

import com.immymemine.kevin.skillshare.adapter.ProfileRecyclerViewAdapter;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView TutorRecyclerView;
    ProfileRecyclerViewAdapter tutorRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initRecycler();
    }

    private void initView(){
//        TutorRecyclerView = findViewById(R.id.recycler_view_teaching);
    }
    private void initRecycler(){
        TutorRecyclerView.setHasFixedSize(true);
        tutorRecyclerViewAdapter = new ProfileRecyclerViewAdapter();
        TutorRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TutorRecyclerView.setAdapter(tutorRecyclerViewAdapter);
    }
}
