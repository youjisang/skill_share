package com.immymemine.kevin.skillshare.fragment.main_f;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.main_adapter.HomeRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.HomeService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
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

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstace(List<String> followSkills) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ConstantUtil.FOLLOW_SKILLS, (ArrayList<String>) followSkills);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        if(!StateUtil.getInstance().getState()) {
            FrameLayout welcomeViewContainer = view.findViewById(R.id.welcome_view_container);
            welcomeViewContainer.addView(ViewFactory.getInstance(context).getWelcomeView());
        }

        if( getArguments() != null ) {
            List<String> followSkills = getArguments().getStringArrayList(ConstantUtil.FOLLOW_SKILLS);
            RetrofitHelper.createApi(HomeService.class)
                    .getHomeClasses(followSkills)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }

        // RecyclerView setting
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_home);
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
