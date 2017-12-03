package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.DiscussionsAdapter;
import com.immymemine.kevin.skillshare.model.online_class.Discussion;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

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

    public DiscussionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions, container, false);

        recyclerViewDiscussion = view.findViewById(R.id.recycler_view_discussion);
        recyclerViewDiscussion.setLayoutManager(new LinearLayoutManager(view.getContext()));

        textViewDiscussion = view.findViewById(R.id.text_view_discussion);

        RetrofitHelper.createApi(ClassService.class)
                .getDiscussions(savedInstanceState.getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // TODO progress bar

        return view;
    }

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
}
