package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.LessonsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonsFragment extends Fragment {


    ImageView tutor_profile_image;
    private RecyclerView lessonsRecyclerView;
    private View view;
    private LessonsAdapter adapter;

    public LessonsFragment() {
        // Required empty public constructor
        Log.d("this lessons fragment ", "new");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lessons, container, false);
        // 초기 위치
        view.findViewById(R.id.scroll_view_lesson).scrollTo(0,0);
        initView();
        initRecycler();
        return view;
    }

    private void initView() {
        lessonsRecyclerView = view.findViewById(R.id.lessonsRecyclerView);
        tutor_profile_image = view.findViewById(R.id.tutor_profile_image);
        Glide.with(view).load(R.drawable.skill_design).apply(RequestOptions.circleCropTransform()).into(tutor_profile_image);
    }

    private void initRecycler() {
        adapter = new LessonsAdapter(getContext());
        lessonsRecyclerView.setAdapter(adapter);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
