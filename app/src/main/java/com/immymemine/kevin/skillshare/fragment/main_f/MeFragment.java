package com.immymemine.kevin.skillshare.fragment.main_f;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.SelectSkillsActivity;
import com.immymemine.kevin.skillshare.adapter.main_adapter.SkillsRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.network.user.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    ImageView meImage;
    TextView meName, meNickname, meFollowers, meFollowing;
    ImageButton meButton;
    LinearLayout container;

    Button mePersonaize;
    View selectSkillView;
    View meFragment;
    RecyclerView recyclerViewSkills;
    SkillsRecyclerViewAdapter adapter;
    List<String> skills;
    FlexboxLayoutManager layoutManager;

    String imagePath;

    StateUtil state;
    User user;

    Context context;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        meFragment = inflater.inflate(R.layout.fragment_me, container, false);
        context = getActivity();
        selectSkillView = LayoutInflater.from(context).inflate(R.layout.me_skill_view, container, false);

        MeFragmentInitiateView(meFragment);
        MeSkillViewinitiateView2(selectSkillView);

        state = StateUtil.getInstance();
        user = state.getUserInstance();

        if (user.getImageUrl() != null) {

            RetrofitHelper.createApi(UserService.class).downloadImage(user.get_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((User user) -> {
                        Log.e("success2", "success2");

                        Glide.with(context).load(new File("C:/Users/JisangYou/workspace/Team_Project/skillShareProject/skill_share_node.js/" + user.getImageUrl()).getPath()).apply(RequestOptions.circleCropTransform()).into(meImage);

                        Log.e("total url  ", "C:/Users/JisangYou/workspace/Team_Project/skillShareProject/skill_share_node.js/" + user.getImageUrl());
                    }, (Throwable error) -> {
                        Log.e("handleError2", "throwable message2" + error.getMessage());
                    });


        }
//        else {
//            if (user.getImageUrl() == null || user.getImageUrl().equals("")) {
//                Glide.with(context).load(R.drawable.image_profile)
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(meImage);
//            }
//        }


        meName.setText(user.getName());
        if (user.getNickname() != null)
            meNickname.setText("@" + user.getNickname());

        meFollowers.setText(user.getFollowers().size() + " Followers");
        meFollowing.setText("Following " + user.getFollowing().size());
//        Glide.with(context).load(user.getImageUrl()).apply(RequestOptions.circleCropTransform()).into(meImage);

        recyclerViewSetting();

        return meFragment;
    }

    private void MeFragmentInitiateView(View view) {

        meImage = view.findViewById(R.id.me_image);
        meImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission();
                } else {
                    getImageFile();
                }
            }
        });
        meName = view.findViewById(R.id.me_name);
        meNickname = view.findViewById(R.id.me_nickname);
        meFollowers = view.findViewById(R.id.me_followers);
        meFollowing = view.findViewById(R.id.me_following);
        meButton = view.findViewById(R.id.me_button); // sign out button
        container = view.findViewById(R.id.container);


    }

    public void MeSkillViewinitiateView2(View selectView) {

        mePersonaize = selectView.findViewById(R.id.personalize);
        recyclerViewSkills = selectView.findViewById(R.id.recycler_view_skills);
        container.addView(selectView);
        mePersonaize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectSkillsActivity.class);
                startActivityForResult(intent, ConstantUtil.SELECT_SKILLS_REQUEST_CODE);
            }
        });


    }

    public void uploadImage(String Path) {
        File file = new File(Path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Log.e("requestFile", "   requestFile ==   " + requestFile + "     file ==    " + file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadImageFile", file.getName(), requestFile);
        Log.e("body", "  body ==   " + requestFile + "    file.getName() ==   " + file.getName());


        RetrofitHelper.createApi(UserService.class).uploadImage(user.get_id(), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((User user) -> {
                    Log.e("success", "success");
                    imagePath = "";
                }, (Throwable error) -> {
                    Log.e("handleError", "throwable message" + error.getMessage());
                });

    }

    public void getImageFile() {
        final Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image to Upload");

        startActivityForResult(chooserIntent, 982);

    }


    Uri imageUri = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 982 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

//            Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(meImage);
//            user.setImageUrl(imageUri.toString()); // 유저 정보에 이미지 저장
            //Todo 지상 파일 만들기

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                Log.e("columnIndex", "columnIndex = " + columnIndex);
                Log.e("filePathColumn", "filePathColumn = " + filePathColumn[0]);

                imagePath = cursor.getString(columnIndex);

                Glide.with(context).load(new File(imagePath)).apply(RequestOptions.circleCropTransform()).into(meImage);

                Log.e("imagePath", "imagePath = " + imagePath);

                cursor.close();
            }

            uploadImage(imagePath);


        } else if (requestCode == ConstantUtil.SELECT_SKILLS_REQUEST_CODE && resultCode == RESULT_OK) {

            skills = data.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);

            user.setFollowingSkills(skills);

            recyclerViewSetting();

            RetrofitHelper.createApi(UserService.class).followSkills(user.get_id(), user.getFollowingSkills())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (User user) -> {
                                Log.e("handleResponse", "USER" + user.getFollowingSkills());
                            }, (Throwable error) -> {
                                Log.e("handleError", "throwable message" + error.getMessage());
                            }
                    );
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        // 1. 권한이 있는지 여부 확인
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            getImageFile();
            // 진행 허용 처리

            // 2. 권한이 없으면 권한을 요청
        } else {
            // 2.1 요청할 권한을 정의
            String permissions[] = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            // 2.2 권한 요청
            requestPermissions(permissions, ConstantUtil.REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 3. 권한 승인 여부 체크
        switch (requestCode) {
            case ConstantUtil.REQ_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 진행 허용 처리
                    getImageFile();
                }
                break;
        }
    }


    public void recyclerViewSetting() {

        layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        recyclerViewSkills.setLayoutManager(layoutManager);
        recyclerViewSkills.setAdapter(new SkillsRecyclerViewAdapter(context, user.getFollowingSkills()));
    }

}