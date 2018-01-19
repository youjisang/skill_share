package com.immymemine.kevin.skillshare.fragment.main_f;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.main_adapter.GroupRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.HomeService;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.communication_util.Bus;
import com.immymemine.kevin.skillshare.utility.communication_util.Subscribe;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {

    Context context;
    GroupRecyclerViewAdapter mAdapter, fAdapter, rAdapter;
    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("JUWONLEE", "Group Fragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        context = getActivity();

        RecyclerView myGroupsRecyclerView = view.findViewById(R.id.my_groups_recycler_view);
        myGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new GroupRecyclerViewAdapter(context);
        myGroupsRecyclerView.setAdapter(mAdapter);

        RecyclerView featuredGroupsRecyclerView = view.findViewById(R.id.featured_groups_recycler_view);
        featuredGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        fAdapter = new GroupRecyclerViewAdapter(context);
        featuredGroupsRecyclerView.setAdapter(fAdapter);

        RecyclerView recentlyActiveGroupsRecyclerView = view.findViewById(R.id.recently_active_groups_recycler_view);
        recentlyActiveGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rAdapter = new GroupRecyclerViewAdapter(context);
        recentlyActiveGroupsRecyclerView.setAdapter(rAdapter);

        RetrofitHelper.createApi(HomeService.class)
                .getGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        Bus.getInstance().register(this);

        return view;
    }

    private void handleResponse(Map<String, List<Group>> groups) {
        Log.d("JUWONLEE", "Group Fragment handle response");
        StateUtil state = StateUtil.getInstance();

        if(state.getState()) {
            if(state.getUserInstance().getGroups() != null) {
                mAdapter.update(state.getUserInstance().getGroups());
            }
        }

        List<Group> featuredGroups = groups.get("Featured Groups");
        fAdapter.update(featuredGroups);

        List<Group> recentlyActiveGroups = groups.get("Recently Active Groups");
        rAdapter.update(recentlyActiveGroups);
    }

    private void handleError(Throwable error) {

    }

    @Subscribe
    public void updateMyGroups(List<Group> groups) {
        mAdapter.update(groups);
    }
}
