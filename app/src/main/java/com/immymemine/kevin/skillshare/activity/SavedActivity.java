package com.immymemine.kevin.skillshare.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.SavedRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.dummy.Saved;

import java.util.List;

public class SavedActivity extends AppCompatActivity {


    RecyclerView savedRecyclerView;
    List<Saved> savedList;
    ImageButton toolbar_button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        initiateView();
        initRecylcerView();


    }

    private void initiateView() {
        savedRecyclerView = findViewById(R.id.recycler_view_saved);
        toolbar_button_back = findViewById(R.id.toolbar_button_back);
        toolbar_button_back.setOnClickListener(v -> finish());

    }

    private void initRecylcerView() {
        savedRecyclerView.setHasFixedSize(true);
        SavedRecyclerViewAdapter adapter = new SavedRecyclerViewAdapter(savedList, this);
        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        savedRecyclerView.setAdapter(adapter);

    }

}
