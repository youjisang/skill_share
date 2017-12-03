package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;

import android.widget.EditText;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.DiscussionsAdapter;
import com.immymemine.kevin.skillshare.model.online_class.Discussion;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;
import com.immymemine.kevin.skillshare.sampleModel.DiscussionModel;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionsFragment extends Fragment {


    private RecyclerView discussionsRecyclerView;
    private View view;
//    private DiscussionsAdapter adapter;
    private List<DiscussionModel> discussionData = new ArrayList<>();
    private FrameLayout layout_frame_discussion;

    TextView textViewDiscussion;
    RecyclerView recyclerViewDiscussion;
    DiscussionsAdapter adapter;


    public DiscussionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions, container, false);

        recyclerViewDiscussion = view.findViewById(R.id.recycler_view_discussion);
        recyclerViewDiscussion.setLayoutManager(new LinearLayoutManager(view.getContext()));


        initView();
//        initRecycler();
        onScrollReActing();

        textViewDiscussion = view.findViewById(R.id.text_view_discussion);

        RetrofitHelper.createApi(ClassService.class)
                .getDiscussions(savedInstanceState.getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // TODO progress bar


        return view;
    }


    private void initView() {
        discussionsRecyclerView = view.findViewById(R.id.recycler_view_discussion);
//        layout_frame_discussion = view.findViewById(R.id.layout_frame_discussion);

    public void addDiscussion(View view) {
        EditText editText = view.findViewById(R.id.editText);
        String input = editText.getText().toString();
        if( !ValidationUtil.isEmpty(input) ) {
            editText.setText("");
            // discussion 세팅
            Discussion discussion = new Discussion();

            // add discussion 통신
            RetrofitHelper.createApi(ClassService.class)
                    .addDiscussion(discussion)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }
    }

    private void handleResponse(List<Discussion> discussions) {
        // list reverse...
        if(adapter == null) {
            adapter = new DiscussionsAdapter(discussions);
            recyclerViewDiscussion.setAdapter(adapter);
        } else {
            adapter.updateData(discussions);
        }

        textViewDiscussion.setText(discussions.size() + " Discussions");
        // TODO hide progress bar

    }

    private void handleError(Throwable error) {
        // wifi connection retry page
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
