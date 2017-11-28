package com.immymemine.kevin.skillshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.immymemine.kevin.skillshare.R;

import com.immymemine.kevin.skillshare.adapter.ReviewSeeAllRecyclerViewAdapter;

import com.immymemine.kevin.skillshare.sampleModel.ReviewSeeAllModel;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    ArrayList<ReviewSeeAllModel> reviewSeeAlldata;
    RecyclerView seeAllReviewRecyclerView;
    ReviewSeeAllRecyclerViewAdapter reviewSeeAllRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initView();

        initRecycler();


    }

    private void initView() {
        seeAllReviewRecyclerView = findViewById(R.id.seeAllReviewRecyclerView);
    }

    private void initRecycler() {
        seeAllReviewRecyclerView.setHasFixedSize(true);
        reviewSeeAllRecyclerViewAdapter = new ReviewSeeAllRecyclerViewAdapter(reviewSeeAlldata, this);
        seeAllReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        seeAllReviewRecyclerView.setAdapter(reviewSeeAllRecyclerViewAdapter);
    }
}