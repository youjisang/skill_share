package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.DiscussionsAdapter;
import com.immymemine.kevin.skillshare.sampleModel.DiscussionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionsFragment extends Fragment {

    private RecyclerView discussionsRecyclerView;
    private View view;
    private DiscussionsAdapter adapter;
    private List<DiscussionModel> discussionData = new ArrayList<>();

    public DiscussionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discussions, container, false);

        initView();

        initRecycler();

        return view;
    }

    private void initView() {
        discussionsRecyclerView = view.findViewById(R.id.recycler_view_discussion);
    }

    private void initRecycler() {
        adapter = new DiscussionsAdapter(discussionData, getContext());
        discussionsRecyclerView.setAdapter(adapter);
        discussionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
