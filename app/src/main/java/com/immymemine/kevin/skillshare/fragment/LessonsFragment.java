package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.immymemine.kevin.skillshare.adapter.fragment_adapter.LessonsAdapter;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.sampleModel.LessonsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonsFragment extends Fragment {

    private RecyclerView lessonsRecyclerView;
    private View view;
    private LessonsAdapter adapter;
    private List<LessonsModel> lessonsData = new ArrayList<>();

    public LessonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lessons, container, false);
        lessonsRecyclerView = view.findViewById(R.id.lessonsRecyclerView);
        dummydata();
        adapter = new LessonsAdapter(lessonsData, getContext());
        lessonsRecyclerView.setAdapter(adapter);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    public void dummydata() {
        lessonsData.add(new LessonsModel("","","",1));
        lessonsData.add(new LessonsModel("","","",1));
        lessonsData.add(new LessonsModel("","","",1));

    }
}
