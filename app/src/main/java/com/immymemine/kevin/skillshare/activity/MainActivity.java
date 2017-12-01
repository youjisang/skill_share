package com.immymemine.kevin.skillshare.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.gcm.RegistrationIntentService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // view
        initiateView(); // initiate view
        setBottomNavigation(); // navigation view setting

        viewFactory = ViewFactory.getInstance(this); // view 생성을 담당할 view factory
        executor = viewFactory.executor; // Thread pool

        home_view_container = viewFactory.getViewContainer(); // view container

        // 로그인 상태 확인
        Intent intent = getIntent();

        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ConstantUtil.SIGN_IN_SUCCESS:
                    userId = intent.getStringExtra("USER_ID");
                    break;
                case ConstantUtil.SIGN_IN_BY_GOOGLE:
                    account = GoogleSignIn.getLastSignedInAccount(this);
//                    String email = intent.getStringExtra("email");
//                    String password = intent.getStringExtra("password");
//                    String pictureUrl = intent.getStringExtra("pictureUrl");
                    break;
                case ConstantUtil.SIGN_UP_SUCCESS:
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.SELECT_SKILLS_REQUEST_CODE);

                    break;
                case ConstantUtil.SIGN_UP_BY_GOOGLE:
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.SELECT_SKILLS_REQUEST_CODE);
                    account = GoogleSignIn.getLastSignedInAccount(this);

                    break;
            }
        } else {
            home_view_container.addView(viewFactory.getWelcomeView());
        }

        // 기본 view 추가
        Future<LinearLayout> f = viewFactory.executor.submit(
                () -> {
                    home_view_container.addView(viewFactory.getGeneralView(getString(R.string.feature_on_skillShare)));
                    home_view_container.addView(viewFactory.getGeneralView(getString(R.string.trending_now)));
                    home_view_container.addView(viewFactory.getGeneralView(getString(R.string.best_this_month)));
                    return home_view_container;
                }
        );

        setContainer();

        // 최초 view 그리기
        try {
            drawingView(f.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // BroadCast Receiver 등록
        registerReceiver();

        if (checkPermission())
            startRegisterService();
        else
            requestPermission();
    }

    @Override
    protected void onStart() {
        // 사용자 로그인 되어 있으면
//        if (t != null) {
//            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
//                    GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestEmail()
//                    .build();
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                    .build();
//            // google api client connection
//            mGoogleApiClient.connect();
//        }

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
            // 데이터 변화 감지

            // 다른 부분이 있으면 view 를 추가하거나 삭제


            // 완료 되면 호출 ∇
            refreshLayout.setRefreshing(false);
        });
        refreshLayout.setColorSchemeResources(R.color.ProgressBarColor);
    }

    private void setContainer() {
        // for study
        /*
        executor.submit(
              new Callable<Boolean>() {
                  @Override
                  public Boolean call() throws Exception {
                      return null;
                  }
              }
        );
        */

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

    Future<LinearLayout> g, d;

    private void setViews() {
        g = executor.submit(
                () -> {
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.my_groups)));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.featured_groups)));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.recently_active_groups)));
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

        // main 에서 처리해줘야 한다.
        View view = viewFactory.getYourClassesView();
        ImageView video_thumbnail = view.findViewById(R.id._your_classes_video_thumbnail);
        Glide.with(this)
                .load(/*thumbnail*/R.drawable.common_google_signin_btn_icon_light_normal)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.skill_design)) // if( image == null ) setting default 이미지
                .into(video_thumbnail);
        your_classes_view_container.addView(view);


        View meView;
//        if (t != null) {
//            meView = viewFactory.getMeView(t.getName());
//            Glide.with(this)
//                    .load(t.getPhoto())
//                    .apply(RequestOptions.placeholderOf(R.drawable.skill_gaming)) // 이미지가 없을 때
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(((ImageView) meView.findViewById(R.id.me_image)));
//        } else {
            meView = viewFactory.getMeView("My Name");
            Glide.with(this)
                    .load(R.drawable.skill_design)
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) meView.findViewById(R.id.me_image)));
//        }
        me_view_container.addView(meView);
        me_view_container.addView(viewFactory.getMeSkillView());
    }

    private void drawingView(LinearLayout view_container) {

        // remove previous view
        scrollView.removeAllViewsInLayout();
        scrollView.requestLayout();

        // add selected view
        scrollView.addView(view_container);
    }

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
            toolbar.setVisibility(View.GONE);
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

                        try {
                            drawingView(your_classes_view_container);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                case 4:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_me);

                        try {
                            drawingView(me_view_container);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        return  deviceMan + " " +deviceName;
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
        startActivity(new Intent(MainActivity.this, SelectSkillsActivity.class));
    }

    @Override
    public void seeAll(String title) {
        Intent intent = new Intent(MainActivity.this, SeeAllActivity.class);
        // title >> int
        // ConstantUtil
        intent.putExtra("Type", title);
        startActivity(intent);
    }

    @Override
    public void signOut() {
//        if(t != null) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
                // TODO me page 변경 >>> sign in / sign up
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finish();
//                t = null;
            });
//        } else
//            return;
    }

    @Override
    public void signUp() {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
}