package com.immymemine.kevin.skillshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.ProjectSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.sampleModel.ProjectSeeAllModel;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {

    ArrayList<ProjectSeeAllModel> projectSeeAlldata;
    RecyclerView seeAllProjectRecyclerView;
    ProjectSeeAllRecyclerViewAdapter projectSeeAllRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);


        initView();

        initRecycler();


    }

    private void initView(){
        seeAllProjectRecyclerView = findViewById(R.id.seeAllProjectRecyclerView);
    }

    private void initRecycler(){
        seeAllProjectRecyclerView.setHasFixedSize(true);
        projectSeeAllRecyclerViewAdapter = new ProjectSeeAllRecyclerViewAdapter(projectSeeAlldata, this);
        seeAllProjectRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        seeAllProjectRecyclerView.setAdapter(projectSeeAllRecyclerViewAdapter);
    }


}
