package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
    private FrameLayout layout_frame_discussion;

    public DiscussionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discussions, container, false);

        initView();
        initRecycler();
        onScrollReActing();

        return view;
    }

    private void initView() {
        discussionsRecyclerView = view.findViewById(R.id.recycler_view_discussion);
        layout_frame_discussion = view.findViewById(R.id.layout_frame_discussion);
    }

    private void initRecycler() {
        adapter = new DiscussionsAdapter(discussionData, getContext());
        discussionsRecyclerView.setAdapter(adapter);
        discussionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // 리싸이클러 뷰가 움직일 때, 프레임 레이아웃이 사라지게 하는 메서드
    // coodinatorLayout으로 하는 방법이 머티리얼 디자인 가이드에서 더 추천??하는 것 같음.
    // 해당 프래그먼트의 밑으로 내리는 이벤트가 생길떄 클래스액티비티와 통신을 해서, 탭레이아웃을 사라지게 해야하는 것인지 고민이 필요함.
    private void onScrollReActing() {
        discussionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && layout_frame_discussion.isShown()) {
                    layout_frame_discussion.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    layout_frame_discussion.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


}
