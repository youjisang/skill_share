package com.immymemine.kevin.skillshare.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.gcm.RegistrationIntentService;
import com.immymemine.kevin.skillshare.model.dummy.dummyDataForGroup;
import com.immymemine.kevin.skillshare.model.home.Class;

import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.HomeService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ViewFactory.InteractionInterface {

    // view factory
    ViewFactory viewFactory;
    ExecutorService executor;

    // toolbar initiate
    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton toolbar_left_button, toolbar_right_button;

    // view container
    LinearLayout home_view_container, group_view_container, discover_view_container, your_classes_view_container, me_view_container;

    // attach view container to scroll view
    ScrollView scrollView;

    // bottom navigation view
    AHBottomNavigation bottomNavigation;

    // google sign in / out
    GoogleApiClient mGoogleApiClient;
    GoogleSignInAccount account;

    // user
    String userId;
    boolean isSignIn;

    // user followed skills
    List<String> followSkills = new ArrayList<>();

    //TODO 지상 for select skills -----------------------
    ArrayList<String> selectList = new ArrayList<>();
    View meView;
    //---------------------------------------------------

    //TODO 지상 for group--------------------------------
    List<dummyDataForGroup> groupList1, groupList2, mygroupList;


    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // view
        initiateView(); // initiate view
        setBottomNavigation(); // navigation view setting

        // TODO 지상 추가
        searchButtonListener();
        //

        viewFactory = ViewFactory.getInstance(this); // view 생성을 담당할 view factory
        executor = viewFactory.executor; // Thread pool

        home_view_container = viewFactory.getViewContainer(); // view container

        // 로그인 상태 확인
        Intent intent = getIntent();

        if (intent.getAction() != null) {
            isSignIn = true;
            switch (intent.getAction()) {
                case ConstantUtil.SIGN_IN_SUCCESS:
                    userId = intent.getStringExtra("USER_ID");
                    break;
                case ConstantUtil.SIGN_IN_BY_GOOGLE:
                    account = GoogleSignIn.getLastSignedInAccount(this);

                    break;
                case ConstantUtil.SIGN_UP_SUCCESS:
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.SELECT_SKILLS_REQUEST_CODE);

                    break;
                case ConstantUtil.SIGN_UP_BY_GOOGLE:
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.SELECT_SKILLS_REQUEST_CODE);
                    account = GoogleSignIn.getLastSignedInAccount(this);

                    break;
                case ConstantUtil.SIGN_OUT:
                    isSignIn = false;
                    home_view_container.addView(viewFactory.getWelcomeView());
                    bottomNavigation.setCurrentItem(4);
                    break;
            }
        } else {
            isSignIn = false;
            home_view_container.addView(viewFactory.getWelcomeView());

            // user follow skills 를 배열로 담아서 Query 로 보낸다
            followSkills.add(ConstantUtil.FEATURED_ON_SKILLSHARE);
            followSkills.add(ConstantUtil.TRENDING_NOW);
            followSkills.add(ConstantUtil.BEST_THIS_MONTH);
        }

        // TODO Progress Bar

        // follow skills 에 해당되는 카테고리들을 받아온다.
        RetrofitHelper.createApi(HomeService.class)
                .getHomeClasses(followSkills)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);


        //====================================================================

        Map<String, List<Class>> data = new LinkedHashMap<>();
        List<Class> classData = new ArrayList<>();
        Class c = new Class("id", "Create a Desktop Calendar/Wallpaper using a Pattern", "http://cfile10.uf.tistory.com/image/275C833D577FD5282C26B5",
                "Sorin Constantin", "24");
        classData.add(c);
        classData.add(c);
        classData.add(c);
        classData.add(c);
        classData.add(c);
        // [ fix ] LinkedHashMap <<< 순서가 보장된 Map
        // TODO 순서를 보장해주고 DATA 를 가져와야 한다. <<< Node
        data.put("Feature on Skillshare", classData);
        data.put("Best this month", classData);
        data.put("Test", classData);
        handleResponse(data);
        //test ====================================================================

        groupDummyDataSetting();

        setContainer();// 컨테이너 만드는 method

        // BroadCast Receiver 등록
        registerReceiver();

        if (checkPermission())
            startRegisterService();
        else
            requestPermission();


    }

    private void handleResponse(Map<String, List<Class>> classes) {
        // 기본 view 추가
        Future<LinearLayout> f = viewFactory.executor.submit(
                () -> {
                    int i = home_view_container.getChildCount();
                    for (String key : classes.keySet()) {
                        Log.d("JUWON LEE", key);
                        home_view_container.addView(viewFactory.getGeneralView(key, classes.get(key)), i);
                        i++;
                    }

                    return home_view_container;
                }
        );

        // 최초 view 그리기
        try {
            drawingView(f.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleError(Throwable error) {

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private void initiateView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_left_button = findViewById(R.id.toolbar_button_l);
        toolbar_right_button = findViewById(R.id.toolbar_button_r);

        scrollView = findViewById(R.id.scroll_view);

        // refresh view setting
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(() -> {
            // 데이터 변화 감지 ( ? )

            // 다른 부분이 있으면 view 를 추가하거나 삭제


            // 완료 되면 호출 ∇
            refreshLayout.setRefreshing(false);
        });
        refreshLayout.setColorSchemeResources(R.color.ProgressBarColor);
    }

    //TODO 지상 toolbar에 버튼 달았음-------------------------------------------

    private void searchButtonListener() {
        toolbar_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);

                startActivity(intent);
            }

        });
    }
    //-------------------------------------------------------------------------


    private void setContainer() {

        Future<Boolean> f = executor.submit(
                () -> {
                    // view container 만들기
                    group_view_container = viewFactory.getViewContainer();
                    discover_view_container = viewFactory.getViewContainer();
                    your_classes_view_container = viewFactory.getViewContainer();
                    me_view_container = viewFactory.getViewContainer();
                    return true;
                }
        );

        try {
            if (f.get())
                setViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void groupDummyDataSetting() {
        //TODO 지상 group dummydata 만들기

        groupList1 = new ArrayList<dummyDataForGroup>();
        groupList1.add(new dummyDataForGroup("6.4k", "Ux/Ui", "https://cdn-images-1.medium.com/max/2000/1*7pjzaWKedACc3-olWUghLg.png"));
        groupList1.add(new dummyDataForGroup("4.6k", "Design a Beautiful App", "https://learn.canva.com/wp-content/uploads/2015/10/40-People-Through-History-Who-Changed-Design-For-Good-04.png"));
        groupList1.add(new dummyDataForGroup("5.6k", "Moblie/App", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIeQXRYXiQyOD3f_Kbw3lvlvo92XMcMImEJrqcwKq1JliJQkfj"));


        groupList2 = new ArrayList<>();
        groupList2.add(new dummyDataForGroup("2.1k", "codings", "http://cfile23.uf.tistory.com/image/9907AF3359C0C1153C71D2"));
        groupList2.add(new dummyDataForGroup("3.8k", "music", "https://i.ytimg.com/vi/eqEcRwmV2vU/maxresdefault.jpg"));
        groupList2.add(new dummyDataForGroup("7.1k", "marketing", "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH82423479_w300_h300.jpg"));


        mygroupList = new ArrayList<dummyDataForGroup>();
        //----------------------------------

    }


    /* TODO 지상
       네비게이션 목록에 있는 컨테이너를 만드는 로직이고, 만약 컨테이너가 다 만들어졌으면 setView메서드 호출 ▽
     */

    Future<LinearLayout> g, d;

    private void setViews() {
        g = executor.submit(
                () -> {
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.my_groups), mygroupList));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.featured_groups), groupList1));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.recently_active_groups), groupList2));
                    return group_view_container;
                }
        );

        d = executor.submit(
                () -> {
                    discover_view_container.addView(viewFactory.getDiscoverView());
                    discover_view_container.addView(viewFactory.getGeneralView(getString(R.string.trending_classes)));
                    discover_view_container.addView(viewFactory.getGeneralView(getString(R.string.popular_classes)));
                    return discover_view_container;
                }
        );

        //TODO 지상 여기는 Saved video를 처리해주는 부분임.
        // main 에서 처리해줘야 한다.
        View view = viewFactory.getYourClassesView();
        ImageView video_thumbnail = view.findViewById(R.id._your_classes_video_thumbnail);
        Glide.with(this)
                .load(/*thumbnail*/R.drawable.common_google_signin_btn_icon_light_normal)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.skill_design)) // if( image == null ) setting default 이미지
                .into(video_thumbnail);
        your_classes_view_container.addView(view);

        //TODO 지상 여기는 여기는 내 프로필을 처리해주는 부분임. git pull 시 수정 필요

        if (isSignIn) {
            meView = viewFactory.getMeView();
            Glide.with(this)
                    .load(R.drawable.skill_design)
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) meView.findViewById(R.id.me_image)));
            me_view_container.addView(meView);
            me_view_container.addView(viewFactory.getMeSkillView(selectList));
        }





    }

    View notSignedInMeView;

    private void drawingView(LinearLayout view_container) {
        // remove previous view
        scrollView.removeAllViewsInLayout();
        scrollView.requestLayout();

        // add selected view
        scrollView.addView(view_container);
    }

    private void drawingView(View view) {
        // remove previous view
        scrollView.removeAllViewsInLayout();
        scrollView.requestLayout();

        // add selected view
        scrollView.addView(view);
    }

    /*TODO 지상
     콘테이너와 뷰들이 이 함수에 들어와 생성.
     */

    private void changeToolbar(int id) {
        // to do on main thread
        if (id == R.id.navigation_home) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Home");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.VISIBLE);

        } else if (id == R.id.navigation_groups) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Groups");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.VISIBLE);


        } else if (id == R.id.navigation_discover) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Discover");
            toolbar_left_button.setVisibility(View.VISIBLE);
            toolbar_right_button.setVisibility(View.VISIBLE);

        } else if (id == R.id.navigation_your_classes) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Your Classes");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.GONE);
        } else if (id == R.id.navigation_me) {
//            if (isSignIn)
//                toolbar.setVisibility(View.GONE);
//            else
//                {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Me");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.VISIBLE);
//            }
        }
    }

    // 네비게이션바
    private void setBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Add items <<< created items (R.string.TITLE, R.drawable.ICON_IMG, R.color.NAVIGATION_BAR_BACKGROUND_COLOR)
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_home, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.groups, R.drawable.ic_group, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.discover, R.drawable.ic_discover, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.your_classes, R.drawable.ic_list, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.me, R.drawable.ic_profile, R.color.BottomNaviBackground));

        // Set Navigation Icon & Background Colors
        bottomNavigation.setColoredModeColors(getResources().getColor(R.color.IcActive), getResources().getColor(R.color.IcInactive));
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));

        // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.IcActive));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.IcInactive));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Display color under navigation bar (API 21+)
        bottomNavigation.setTranslucentNavigationEnabled(true);

        // Manage titles (SHOW_WHEN_ACTIVE, ALWAYS_SHOW, ALWAYS_HIDE)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.BottomNaviNotiBackground));

        bottomNavigation.setOnTabSelectedListener((int position, boolean wasSelected) -> {
            switch (position) {
                case 0:
                    if (wasSelected) {
                        return false;
                    } else {
                        // toolbar 바꾸기
                        changeToolbar(R.id.navigation_home);
                        // container 에 담기 ( 그리기 )
                        drawingView(home_view_container);
                        return true;
                    }
                case 1:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_groups);

                        try {
                            drawingView(g.get());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                case 2:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_discover);
                        try {
                            drawingView(d.get());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                case 3:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_your_classes);
                        drawingView(your_classes_view_container);
                        return true;
                    }
                case 4:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_me);
//                        if (isSignIn)
                        drawingView(me_view_container);
//                        else
//                            drawingView(notSignedInMeView);
                        return true;
                    }
            }
            return false;
        });
    }

    // Broad Cast Receiver
    private void registerReceiver() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantUtil.REGISTRATION);
        intentFilter.addAction(ConstantUtil.RECEIVED);
        localBroadcastManager.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConstantUtil.RECEIVED)) {
                    // notification 받으면 bottom navigation view 에 noti 달기
                    bottomNavigation.setNotification(" ", 1);
                } else if (intent.getAction().equals(ConstantUtil.REGISTRATION)) {
                    // registration 기록 남겨놔야할까?
                }
            }
        }, intentFilter);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        return (result == PackageManager.PERMISSION_GRANTED) ? true : false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, ConstantUtil.PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantUtil.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startRegisterService();
                else
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void startRegisterService() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("DEVICE_ID", getDeviceId());
        intent.putExtra("DEVICE_NAME", getDeviceName());
        startService(intent);
    }

    @SuppressLint("MissingPermission")
    private String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    private String getDeviceName() {
        String deviceName = Build.MODEL;
        String deviceMan = Build.MANUFACTURER;
        return deviceMan + " " + deviceName;
    }

    // Interaction Listener
    @Override
    public void close() {
        home_view_container.removeViewAt(0);
    }

    @Override
    public void select() {
        // 선택된 카테고리들을 받아와서 그려줘야 함
        // startActivityForResult();

        Intent intent1 = new Intent(MainActivity.this, SelectSkillsActivity.class);
        startActivityForResult(intent1, ConstantUtil.SELECT_SKILLS_REQUEST_CODE2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantUtil.SELECT_SKILLS_REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();


                selectList = (ArrayList<String>) data.getSerializableExtra("toggleArray");
                Log.e("fromSelectActivity", "========list================" + selectList);


                meView = viewFactory.getMeView();

                Glide.with(this)
                        .load(R.drawable.skill_design)
                        .apply(RequestOptions.circleCropTransform())
                        .into(((ImageView) meView.findViewById(R.id.me_image)));
                me_view_container.removeAllViews();

                me_view_container.addView(meView);
                me_view_container.addView(viewFactory.getMeSkillView(selectList));

            }
        } else if (requestCode == ConstantUtil.ALREADY_JOIN_GROUP) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();

                int groupItemPosition = data.getIntExtra("position", 0);
                String groupTitle_s = data.getStringExtra("groupName");
                String groupNum_s = data.getStringExtra("groupJoinNum");
                String imageUri_s = data.getStringExtra("groupImageUri");
                mygroupList.add(new dummyDataForGroup(groupNum_s, groupTitle_s, imageUri_s));
                int i;
                for(i=0; i<groupList1.size(); i++) {
                    if (groupTitle_s.equals(groupList1.get(i).getGroupName())){
                        groupList1.remove(i);
                    }
                }

                for(i=0; i<groupList2.size(); i++) {
                    if (groupTitle_s.equals(groupList2.get(i).getGroupName())){
                        groupList2.remove(i);
                    }

                }


                Log.e("MainActivity", "check============" + mygroupList.get(0).getGroupName());
                Log.e("MainActivity", "check position============" + groupItemPosition);
                group_view_container.removeAllViews();
                group_view_container.addView(viewFactory.getGroupView(getString(R.string.my_groups), mygroupList));
                group_view_container.addView(viewFactory.getGroupView(getString(R.string.featured_groups), groupList1));
                group_view_container.addView(viewFactory.getGroupView(getString(R.string.recently_active_groups), groupList2));


            }
        }
    }

    @Override
    public void seeAll(String title) {
        Intent intent = new Intent(MainActivity.this, SeeAllActivity.class);
        // title >> int
        // ConstantUtil
        intent.putExtra("TYPE", title);
        startActivity(intent);

    }

    @Override
    public void signOut() {


        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setAction(ConstantUtil.SIGN_OUT);
        startActivity(intent);
        finish();

        /* TODO 지상
            Intent를 메인에서 메인으로 주는데 이는 로그아웃시 이슈?
            이 부분 이해가 필요함.
         */
    }

    @Override
    public void signUp() {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
}