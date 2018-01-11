package com.immymemine.kevin.skillshare.fragment.main_f;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    ImageView meImage;
    TextView meName, meNickname, meFollowers, meFollowing;
    ImageButton meButton;
    LinearLayout container;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        Context context = getActivity();

        initiateView(view);

        StateUtil state = StateUtil.getInstance();
        User user = state.getUserInstance();

        if(user.getImageUrl() == null || user.getImageUrl().equals("")) {
            Glide.with(context).load(R.drawable.image_profile)
                    .apply(RequestOptions.circleCropTransform())
                    .into(meImage);
        } else {
            Glide.with(context).load(user.getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(meImage);
        }

        meName.setText(user.getName());
        if(user.getNickname() != null)
            meNickname.setText("@"+user.getNickname());

        meFollowers.setText(user.getFollowers().size() + " Followers");
        meFollowing.setText("Following " + user.getFollowing().size());

        if(user.getFollowingSkills() != null) {
            List<String> skills = user.getFollowingSkills();
            container.addView(ViewFactory.getInstance(context).getMeSkillView(skills));
        }

        return view;
    }

    private void initiateView(View view) {
        meImage = view.findViewById(R.id.me_image);
        meName = view.findViewById(R.id.me_name);
        meNickname = view.findViewById(R.id.me_nickname);
        meFollowers = view.findViewById(R.id.me_followers);
        meFollowing = view.findViewById(R.id.me_following);

        meButton = view.findViewById(R.id.me_button); // sign out button
        container = view.findViewById(R.id.container);
    }
}