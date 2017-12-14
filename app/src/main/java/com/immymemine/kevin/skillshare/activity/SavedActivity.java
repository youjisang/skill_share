package com.immymemine.kevin.skillshare.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.immymemine.kevin.skillshare.R;

public class SavedActivity extends AppCompatActivity {


    RecyclerView savedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);





    }

    private void initiateView(){
        savedRecyclerView = findViewById(R.id.recycler_view_saved);

    }

    private void initRecylcerView(){
        savedRecyclerView.setHasFixedSize(true);

    }

}
