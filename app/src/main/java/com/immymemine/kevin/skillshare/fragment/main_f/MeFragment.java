package com.immymemine.kevin.skillshare.fragment.main_f;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.immymemine.kevin.skillshare.activity.SelectSkillsActivity;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.network.user.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    ImageView meImage;
    TextView meName, meNickname, meFollowers, meFollowing;
    ImageButton meButton;
    LinearLayout container;

    StateUtil state;
    User user;
    Uri imageUri = null;
    Context context;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        context = getActivity();

        initiateView(view);

        state = StateUtil.getInstance();
        user = state.getUserInstance();

        if (user.getImageUrl() != null) {

            RetrofitHelper.createApi(UserService.class).imageUrl(user.get_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);


        } else {
        meImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 982);

            }
        });
        }

        meName.setText(user.getName());
        meNickname.setText("@" + user.getNickname());
        meFollowers.setText(user.getFollowers().size() + " Followers");
        meFollowing.setText("Following " + user.getFollowing().size());

        if (user.getFollowingSkills() != null) {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 982 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            meImage.setImageURI(imageUri);
            user.setImageUrl(imageUri.toString());

//            Glide.with(this).load(imageUri)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(meImage);

            // retrofit
            RetrofitHelper.createApi(UserService.class).putImageUrl(user.get_id(), user.getImageUrl())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError);
        }

    }

    private void handleResponse(User user) {
        Log.e("handleResponse", "USER" + user.getImageUrl());

    }

    private void handleError(Throwable throwable) {
        Log.e("handleError", "throwable message" + throwable.getMessage());
    }


}