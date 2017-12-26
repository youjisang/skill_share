package com.immymemine.kevin.skillshare.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.MainActivity;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.DiscussionsAdapter;
import com.immymemine.kevin.skillshare.model.m_class.Discussion;
import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;
import com.immymemine.kevin.skillshare.utility.communication_util.Bus;
import com.immymemine.kevin.skillshare.utility.communication_util.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionsFragment extends Fragment {
    // view
    TextView textViewDiscussion;
    RecyclerView recyclerViewDiscussion;
    DiscussionsAdapter adapter;

    EditText editText;
    LinearLayout linearLayoutDiscussion;
    Context context;

//    List<Discussion> discussions;

    String classId;
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
        recyclerViewDiscussion.setNestedScrollingEnabled(false);

        linearLayoutDiscussion = view.findViewById(R.id.linear_layout_discussion);

        textViewDiscussion = view.findViewById(R.id.text_view_discussion);

        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.button_send).setOnClickListener( v ->
                sendDiscussion()
        );

        classId = getArguments().getString(ConstantUtil.ID_FLAG);

        RetrofitHelper.createApi(ClassService.class)
                .getDiscussions(classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // TODO progress bar
        Bus.getInstance().register(this);

        return view;
    }


    public void sendDiscussion() {
        String input = editText.getText().toString();
        if( !ValidationUtil.isEmpty(input) ) {
            editText.setText("");
            User user = MainActivity.user;
            Discussion discussion = new Discussion(
                    input,
                    System.currentTimeMillis()+"",
                    "0",
                    user.get_id(),
                    user.getName(),
                    user.getImageUrl(),
                    user.getRegistrationId(),
                    new ArrayList<>()
            );

            // add discussion 통신
            RetrofitHelper.createApi(ClassService.class)
                    .sendDiscussion(discussion, classId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }
    }

    List<Discussion> discussions;

    private void handleResponse(List<Discussion> discussions) {

        Collections.reverse(discussions);
        this.discussions = discussions;
        // TODO list reverse or get data by sort
        if(discussions == null || discussions.size() == 0) {
            textViewDiscussion.setVisibility(View.GONE);
        } else {
            if(textViewDiscussion.getVisibility() == View.VISIBLE) {// update 시에는 새롭게 갱신해준다. >>> recycler view 위치 초기화
                adapter.updateData(discussions);
            } else {
                adapter.initiateData(discussions);
                textViewDiscussion.setVisibility(View.VISIBLE);
            }

            textViewDiscussion.setText(discussions.size() + " Discussions");
        }
        // TODO hide progress bar
    }

    @Subscribe
    public void updateReplies(Map<Integer, List<Reply>> data) {
        Log.d("JUWONLEE", "successful subscribe");
        for(int position : data.keySet()) {
            if (position != -1) {
                Discussion discussion = discussions.get(position);
                discussion.setReplies(data.get(position));
            }
            adapter.updateData(discussions);
        }
    }

    private void handleError(Throwable error) {
        // wifi connection retry page
        Log.e("discussion error :  ", error.getMessage());
    }

}