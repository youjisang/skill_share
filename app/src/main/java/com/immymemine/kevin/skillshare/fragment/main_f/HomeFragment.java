package com.immymemine.kevin.skillshare.fragment.main_f;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.MainActivity;
import com.immymemine.kevin.skillshare.activity.SearchActivity;
import com.immymemine.kevin.skillshare.adapter.main_adapter.HomeRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.HomeService;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {

    Context context;
    HomeRecyclerViewAdapter adapter;

    FrameLayout welcomeViewContainer;
    View welcomeView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        view.findViewById(R.id.toolbar_button_search)
                .setOnClickListener(v -> context.startActivity(new Intent(context, SearchActivity.class)));

        if (!StateUtil.getInstance().getState() &&
                (getArguments() != null) ? getArguments().getBoolean("show") : true) {
            welcomeViewContainer = view.findViewById(R.id.welcome_view_container);
            welcomeView = ViewFactory.getInstance(context).getWelcomeView();
            welcomeView.findViewById(R.id.close_button).setOnClickListener(
                    v -> {
                        welcomeViewContainer.removeView(welcomeView);
                    }
            );

            welcomeViewContainer.addView(welcomeView);
        }

        // Todo 지상 : 로그인하고 들어오면 welcomeView 사라지게....
        // 이게 안사라지니까 yourClass fragment, mefragment에서 버튼 클릭할때 welcomeView가 뜸.
        if (StateUtil.getInstance().getUserInstance() != null) {
            welcomeViewContainer.removeView(welcomeView);
        }
        // TODO 여기까지......................................

        List<String> followSkills = (StateUtil.getInstance().getUserInstance().getFollowingSkills() != null) ?
                StateUtil.getInstance().getUserInstance().getFollowingSkills() : new ArrayList<>();

        RetrofitHelper.createApi(HomeService.class)
                .getHomeClasses(followSkills)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // RecyclerView setting
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HomeRecyclerViewAdapter(context);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void handleResponse(List<Map<String, List<Class>>> classes) {
        adapter.update(classes);
    }

    private void handleError(Throwable error) {

    }
}
