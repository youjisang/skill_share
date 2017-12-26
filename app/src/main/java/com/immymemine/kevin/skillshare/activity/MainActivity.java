package com.immymemine.kevin.skillshare.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.immymemine.kevin.skillshare.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.SkillsRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.gcm.RegistrationIntentService;
import com.immymemine.kevin.skillshare.model.discover.DiscoverClass;
import com.immymemine.kevin.skillshare.model.dummy.Group;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.model.user.Following;
import com.immymemine.kevin.skillshare.model.user.SubscribeClass;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.HomeService;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.view.ViewFactory;

import java.util.ArrayList;
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
    View discoverView, yourClassesView, meView, meSkillView, notSignedInMeView;
    SkillsRecyclerViewAdapter meSkillRecyclerViewAdapter;
    GeneralRecyclerViewAdapter generalRecyclerViewAdapter;

    // attach view container to scroll view
    ScrollView scrollView;

    AHBottomNavigation bottomNavigation; // bottom navigation view
    ProgressBar progressBar; // progress bar

    // google sign in / out
    GoogleApiClient mGoogleApiClient;
    GoogleSignInAccount account;

    // user
    StateUtil stateUtil;
    public static boolean isSignIn;
    public static User user;

    // user followed skills
    List<String> followSkills = new ArrayList<>();

    //group
    List<Group> mygroupList, groupList1, groupList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // view
        initiateView(); // initiate view
        setBottomNavigation(); // navigation view setting

        viewFactory = ViewFactory.getInstance(this); // view 생성을 담당할 view factory
        executor = viewFactory.executor; // Thread pool

        stateUtil = StateUtil.getInstance(); // user state check util

        home_view_container = viewFactory.getViewContainer(); // view container

        // 로그인 상태 확인
        Intent intent = getIntent();

        if (intent.getAction() != null) {
            stateUtil.setState(true);
            switch (intent.getAction()) {
                case ConstantUtil.SIGN_IN_SUCCESS:
                    String userId = intent.getStringExtra(ConstantUtil.USER_ID_FLAG);

                    RetrofitHelper.createApi(UserService.class)
                            .getUser(userId)
                            .observeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (User user) -> {
                                        stateUtil.setUserInstance(user);

                                        RetrofitHelper.createApi(HomeService.class)
                                                .getHomeClasses(user.getFollowingSkills())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(this::setHomeViewContainer, this::handleError);

                                        setContainer();

                                    }, (Throwable error) -> {
                                        // TODO handle networking error

                                        // for test ====================
                                        // 통신이 성공했다고 가정하고...
                                        user = new User();

                                        user.set_id(userId);
                                        user.setName("JUWON LEE");
                                        user.setNickname("@jwl");

                                        List<Following> followings = new ArrayList<>();
                                        followings.add(new Following());
                                        user.setFollowing(followings);

                                        List<String> userSkills = new ArrayList<>();
                                        userSkills.add("Design");
                                        userSkills.add("Photography");
                                        userSkills.add("Crafts");
                                        userSkills.add("Music");
                                        user.setFollowingSkills(userSkills);

                                        followSkills.add(ConstantUtil.FEATURED_ON_SKILLSHARE);
                                        followSkills.addAll(userSkills);
                                        followSkills.add(ConstantUtil.TRENDING_NOW);
                                        followSkills.add(ConstantUtil.BEST_THIS_MONTH);

                                        user.setImageUrl("https://0.soompi.io/wp-content/uploads/2017/07/17012237/IU3.jpg");

                                        List<SubscribeClass> subscribeClasses = new ArrayList<>();
                                        subscribeClasses.add(new SubscribeClass(
                                                "https://i.pinimg.com/736x/fe/3f/df/fe3fdf460ee15c59b45fda4b0aff6feb--botanical-wallpaper-feature-wallpaper.jpg",
                                                "99%",
                                                "Botanical Line Drawing",
                                                "4740000",
                                                "classId",
                                                "9400",
                                                "Peggy Dean"
                                        ));
                                        user.setSubscribeClass(subscribeClasses);

                                        // for test ====================
                                        RetrofitHelper.createApi(HomeService.class)
                                                .getHomeClasses(followSkills)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(this::setHomeViewContainer, this::handleError);

                                        groupDummyDataSetting();

                                        setContainer();
                                    }
                            );
                    break;
                case ConstantUtil.SIGN_IN_BY_GOOGLE:
                    account = GoogleSignIn.getLastSignedInAccount(this);
                    break;
                case ConstantUtil.SIGN_UP_SUCCESS:
                    userId = intent.getStringExtra(ConstantUtil.USER_ID_FLAG);
                    user = new User();
                    user.set_id(userId);
                    user.setName(intent.getStringExtra(ConstantUtil.USER_NAME_FLAG));
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.INIT_SKILLS_REQUEST_CODE);
                    break;
                case ConstantUtil.SIGN_UP_BY_GOOGLE:
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.INIT_SKILLS_REQUEST_CODE);
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

            RetrofitHelper.createApi(HomeService.class)
                    .getHomeClasses(followSkills)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setHomeViewContainer, this::handleError);

            setContainer();
        }

        // TODO Progress Bar
        // follow skills 에 해당되는 카테고리들을 받아온다.

        // BroadCast Receiver 등록 for gcm
        registerReceiver();
        startRegisterService();
    }

    private void setHomeViewContainer(List<Map<String, List<Class>>> classes) {
        progressBar.setVisibility(View.VISIBLE);

        // 기본 view 추가
        int i = home_view_container.getChildCount();

        for (Map<String, List<Class>> item : classes) {
            for (String key : item.keySet()) {
                home_view_container.addView(viewFactory.getGeneralView(key, item.get(key)), i);
                i++;
            }
        }

        drawingView(home_view_container);

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void handleError(Throwable error) {
        Log.e("JUWONLEE", error.getMessage());
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

        toolbar_right_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        progressBar = findViewById(R.id.progress_bar);
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

        groupList1 = new ArrayList<Group>();
        groupList1.add(new Group("6.4k", "Ux/Ui", "https://cdn-images-1.medium.com/max/2000/1*7pjzaWKedACc3-olWUghLg.png"));
        groupList1.add(new Group("4.6k", "Design a Beautiful App", "https://learn.canva.com/wp-content/uploads/2015/10/40-People-Through-History-Who-Changed-Design-For-Good-04.png"));
        groupList1.add(new Group("5.6k", "Moblie/App", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIeQXRYXiQyOD3f_Kbw3lvlvo92XMcMImEJrqcwKq1JliJQkfj"));


        groupList2 = new ArrayList<>();
        groupList2.add(new Group("2.1k", "codings", "http://cfile23.uf.tistory.com/image/9907AF3359C0C1153C71D2"));
        groupList2.add(new Group("3.8k", "music", "https://i.ytimg.com/vi/eqEcRwmV2vU/maxresdefault.jpg"));
        groupList2.add(new Group("7.1k", "marketing", "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH82423479_w300_h300.jpg"));


        mygroupList = new ArrayList<>();
        //----------------------------------

    }

    Future<LinearLayout> g;
    Future<View> y, m;

    private void setViews() {

        g = executor.submit(
                () -> {
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.my_groups), mygroupList));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.featured_groups), groupList1));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.recently_active_groups), groupList2));
                    return group_view_container;
                }
        );

        y = executor.submit(
                () -> {
                    yourClassesView = viewFactory.getYourClassesView();
                    return yourClassesView;
                }
        );

        m = executor.submit(
                () -> {
                    if (isSignIn) {
                        meView = viewFactory.getMeView(user);
                        return meView;
                    } else {
                        notSignedInMeView = viewFactory.getNotSignedInMeView();
                        return notSignedInMeView;
                    }
                }
        );
    }

    private void setDiscoverViewContainer() {
        progressBar.setVisibility(View.VISIBLE);

        RetrofitHelper.createApi(HomeService.class)
                .getDiscoverClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (DiscoverClass discoverClass) -> {
                            View discoverView = viewFactory.getDiscoverView(discoverClass);

                            Glide.with(this).load(discoverClass.getTutorImageUrl())
                                    .apply(RequestOptions.circleCropTransform())
                                    .into((ImageView) discoverView.findViewById(R.id.image_view_tutor));

                            Glide.with(this).load(discoverClass.getImageUrl())
                                    .apply(RequestOptions.centerCropTransform())
                                    .into((ImageView) discoverView.findViewById(R.id.image_view_featured_class));

                            discover_view_container.addView(discoverView);

                            for (Map<String, List<Class>> item : discoverClass.getClasses()) {
                                for (String key : item.keySet()) {
                                    discover_view_container.addView(viewFactory.getGeneralView(key, item.get(key)));
                                }
                            }

                            drawingView(discover_view_container);

                            progressBar.setVisibility(View.INVISIBLE);

                        }, this::handleError
                );
    }

    private void setYourClassesViewContainer() {
        progressBar.setVisibility(View.VISIBLE);

        yourClassesView = viewFactory.getYourClassesView();

        if (isSignIn && user.getSubscribeClass() != null) {

            Glide.with(MainActivity.this)
                    .load(user.getSubscribeClass().get(0).getClassThumbnail())
                    .apply(RequestOptions.centerCropTransform())
                    .into((ImageView) yourClassesView.findViewById(R.id.image_view_thumbnail));
        }

        your_classes_view_container.addView(yourClassesView);

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setMeViewContainer() {
        try {
            if (m.get() != null) {
                Glide.with(this)
                        .load(user.getImageUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.placeholderOf(R.drawable.image_profile))
                        .into(((ImageView) meView.findViewById(R.id.me_image)));

                me_view_container.addView(meView);

                if (followSkills.size() > 3)
                    meSkillView = viewFactory.getMeSkillView(user.getFollowingSkills());
                else
                    meSkillView = viewFactory.getMeSkillView();

                meSkillRecyclerViewAdapter = (SkillsRecyclerViewAdapter) ((RecyclerView) meSkillView.findViewById(R.id.recycler_view_skills)).getAdapter();
                me_view_container.addView(meSkillView);
            } else {
                // TODO network error ?
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            if (isSignIn)
                toolbar.setVisibility(View.GONE);
            else {
                toolbar.setVisibility(View.VISIBLE);
                toolbar_title.setText("Me");
                toolbar_left_button.setVisibility(View.GONE);
                toolbar_right_button.setVisibility(View.VISIBLE);
            }
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

                        if (discover_view_container.getChildCount() < 3)
                            setDiscoverViewContainer();
                        else
                            drawingView(discover_view_container);

                        return true;
                    }
                case 3:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_your_classes);

                        if (your_classes_view_container.getChildCount() == 0)
                            setYourClassesViewContainer();
                        else
                            drawingView(your_classes_view_container);

                        return true;
                    }
                case 4:
                    if (wasSelected) {
                        return false;
                    } else {
                        changeToolbar(R.id.navigation_me);

                        if (isSignIn) {
                            setMeViewContainer();
                            drawingView(me_view_container);
                        } else {
                            drawingView(notSignedInMeView);
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

    private void startRegisterService() {
        if (isSignIn && user != null) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("USER_ID", user.get_id());
            startService(intent);
        }
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
        Intent intent = new Intent(MainActivity.this, SelectSkillsActivity.class);
        if (user.getFollowingSkills() != null)
            intent.putStringArrayListExtra(ConstantUtil.SKILLS_FLAG, (ArrayList<String>) user.getFollowingSkills());
        startActivityForResult(intent, ConstantUtil.SELECT_SKILLS_REQUEST_CODE);
    }

    @Override
    public void seeAll(String type) {
        Intent intent = new Intent(MainActivity.this, SeeAllActivity.class);
        // title >> int
        // ConstantUtil
        intent.putExtra(ConstantUtil.TYPE_FLAG, type);
        startActivity(intent);
    }

    @Override
    public void signOut() {
//        if(isSignIn) {
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
//                // TODO me page 변경 >>> 1. sign in / sign up 2. token 삭제
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                intent.setAction("SIGN_OUT");
//                startActivity(intent);
//                finish();
//            });
//        }

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setAction(ConstantUtil.SIGN_OUT);
        startActivity(intent);
        finish();
    }

    @Override
    public void signUp() {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    @Override
    public void signIn() {
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ConstantUtil.SELECT_SKILLS_REQUEST_CODE) {
                List<String> skills = data.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);
                if (skills != null && skills.size() > 0)
                    meSkillView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
                meSkillRecyclerViewAdapter.update(skills);
                user.setFollowingSkills(skills);
            } else if (requestCode == ConstantUtil.INIT_SKILLS_REQUEST_CODE) {
                List<String> skills = data.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);
                user.setFollowingSkills(skills);

                // TODO ConstantUtil 에서 쓰는 것들 삭제...
                followSkills.add(ConstantUtil.FEATURED_ON_SKILLSHARE);
                followSkills.addAll(skills);
                followSkills.add(ConstantUtil.TRENDING_NOW);
                followSkills.add(ConstantUtil.BEST_THIS_MONTH);

                RetrofitHelper.createApi(HomeService.class)
                        .getHomeClasses(followSkills)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setHomeViewContainer, this::handleError);

                groupDummyDataSetting();
                setContainer();
            }

            //TODO 지상 그룹 부분 -------------------------------------------------------------------------------
            else if (requestCode == ConstantUtil.ALREADY_JOIN_GROUP) {
                Log.e("MainActivity1", "check1");
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();

                    int groupItemPosition = data.getIntExtra("position", 0);
                    String groupTitle_s = data.getStringExtra("groupName");
                    String groupNum_s = data.getStringExtra("groupJoinNum");
                    String imageUri_s = data.getStringExtra("groupImageUri");
                    mygroupList.add(new Group(groupNum_s, groupTitle_s, imageUri_s));
                    int i;
                    for (i = 0; i < groupList1.size(); i++) {
                        if (groupTitle_s.equals(groupList1.get(i).getGroupName())) {
                            groupList1.remove(i);
                        }
                    }

                    for (i = 0; i < groupList2.size(); i++) {
                        if (groupTitle_s.equals(groupList2.get(i).getGroupName())) {
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
            //--------------------------------------------------------------------------------------------------------------
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewFactory.destroyViewFactory();
    }

}


