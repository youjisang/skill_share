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
import com.immymemine.kevin.skillshare.model.m_class.Discussion;
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

    EditText editText;
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

        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.button_send).setOnClickListener( v ->
                addDiscussion()
        );

        RetrofitHelper.createApi(ClassService.class)
                .getDiscussions(getArguments().getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // TODO progress bar

        return view;
    }


    public void addDiscussion() {
        String input = editText.getText().toString();
        if( !ValidationUtil.isEmpty(input) ) {
            editText.setText("");
            // TODO discussion 세팅
            Discussion discussion = new Discussion();

            // add discussion 통신
            RetrofitHelper.createApi(ClassService.class)
                    .addDiscussion(discussion)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }
    }
    /* TODO 지상
        이 부분이 로그인 이슈?
        로그인이 되어있으면, 작성을 할 수 있다는 의미?
     */

    private void handleResponse(List<Discussion> discussions) {
        int count = discussions.size();
        // discussion 없으면
        if(count == 0) {
            if(adapter == null) {
                adapter = new DiscussionsAdapter(getActivity(), discussions);
                recyclerViewDiscussion.setAdapter(adapter);
            }
            textViewDiscussion.setVisibility(View.GONE);
        } else {
            if(adapter == null) {
                adapter = new DiscussionsAdapter(getActivity(), discussions);
                recyclerViewDiscussion.setAdapter(adapter);
            } else {
                adapter.updateData(discussions);
            }
            textViewDiscussion.setVisibility(View.VISIBLE);
            textViewDiscussion.setText(discussions.size() + " Discussions");
        }// TODO list reverse or get data by sort

        // TODO hide progress bar
    }
    /* TODO 지상
        discussion.size가 0일때와 아닐때를 구분해서 처리한 부분.
        -> adapter로 이동
     */

    private void handleError(Throwable error) {
        // wifi connection retry page
    }
}