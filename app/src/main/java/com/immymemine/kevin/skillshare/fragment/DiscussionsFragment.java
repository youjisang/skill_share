package com.immymemine.kevin.skillshare.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.DiscussionsAdapter;
import com.immymemine.kevin.skillshare.model.m_class.Discussion;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionsFragment extends Fragment {

    TextView textViewDiscussion;
    RecyclerView recyclerViewDiscussion;
    DiscussionsAdapter adapter;
    LinearLayout layoutlineardiscussion;
    EditText editText;

    Context context;

    List<Discussion> discussions;
    public DiscussionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions, container, false);
        context = getActivity();

        recyclerViewDiscussion = view.findViewById(R.id.recycler_view_discussion);
        adapter = new DiscussionsAdapter(getActivity());
        recyclerViewDiscussion.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewDiscussion.setAdapter(adapter);

        textViewDiscussion = view.findViewById(R.id.text_view_discussion);
        layoutlineardiscussion = view.findViewById(R.id.layout_linear_discussion);


        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.button_send).setOnClickListener( v ->
                addDiscussion()
        );

        onScrollReActing();

        RetrofitHelper.createApi(ClassService.class)
                .getDiscussions(getArguments().getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // TODO progress bar

        // test =================================================================
        discussions = new ArrayList<>();
        discussions.add(new Discussion(null, "Jonathan", "http://pds.joins.com/news/component/htmlphoto_mmdata/201706/05/65f78b68-89f2-4add-8ff8-8c2b1b25be53.jpg",
                "앱을 만들 때 가장 중요하다고 생각하시는 부분이 어느 부분인가요? 테스트 해보고 싶은 부분이 있군요. 다섯줄을 넘겨야만 합니다. " +
                        "어디까지 써야 다섯줄을 넘길 수 있을까요? 동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세. " +
                        "무궁화 삼천리 화려가앙산 대한사람 대한으로 길이 보전하세. 남산 위에 저 소나무 철갑을 두른듯 바람서리 불변함은 우리 기상일세","2 days ago",2,null));
        discussions.add(new Discussion(null, "Coach", "https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory&fname=http%3A%2F%2Fcfile21.uf.tistory.com%2Fimage%2F216FB64E5639CB96315B1B",
                "도대체 앱 디자인은 어떻게 하는거죠?","1 days ago",0,null));
        discussions.add(new Discussion(null, "Kevin Lee", "http://image.chosun.com/sitedata/image/201508/06/2015080603367_0.jpg",
                "안녕하세요. 반갑습니다.","16 hours ago",1,null));
        discussions.add(new Discussion(null, "Jane", "https://pbs.twimg.com/profile_images/908706663519051776/84pGO2Zl.jpg",
                "Hello guys. Pleased to meet you.","14 minutes ago",7,null));

        handleResponse(discussions);
        // test =================================================================

        return view;
    }

    public void addDiscussion() {
        String input = editText.getText().toString();
        if( !ValidationUtil.isEmpty(input) ) {
            editText.setText("");

            Discussion discussion = new Discussion(null,
                    "Olivia",
                    "http://news20.busan.com/content/image/2017/09/11/20170911000004_0.jpg",
                    input,
                    "10 minutes ago",
                    2,
                    null
            );

            discussions.add(0, discussion);
            // test
            handleResponse(discussions);

            // add discussion 통신
            RetrofitHelper.createApi(ClassService.class)
                    .addDiscussion(discussion)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }
    }

    private void onScrollReActing() {
        recyclerViewDiscussion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && layoutlineardiscussion.isShown()) {
                    layoutlineardiscussion.setVisibility(View.GONE);
                } else if (dy < 0) {
                    layoutlineardiscussion.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void handleResponse(List<Discussion> discussions) {

        // TODO list reverse or get data by sort
        if(discussions == null || discussions.size() == 0) {
            textViewDiscussion.setVisibility(View.GONE);
        } else {
            if(textViewDiscussion.getVisibility() == View.VISIBLE) {
                adapter.updateData(discussions);
                textViewDiscussion.setText(discussions.size() + " Discussions");
            } else {
                Collections.reverse(discussions);
                adapter.initiateData(discussions);
                textViewDiscussion.setVisibility(View.VISIBLE);
                textViewDiscussion.setText(discussions.size() + " Discussions");
            }
        }

        // TODO hide progress bar
    }

    private void handleError(Throwable error) {
        // wifi connection retry page
    }
}