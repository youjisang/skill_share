package com.immymemine.kevin.skillshare.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.Const;
import com.immymemine.kevin.skillshare.activity.ViewFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, viewGroup, false);
        LinearLayout container = view.findViewById(R.id.group_container);
        ViewFactory viewFactory = new ViewFactory(viewGroup.getContext());
        container.addView(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Feature Groups"));
        container.addView(viewFactory.getViewInstance(Const.GROUP_VIEW, "Recently Active Groups"));
        return view;
    }

}
