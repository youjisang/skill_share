package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.AccountTemp;
import com.immymemine.kevin.skillshare.view.ViewFactory;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
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
    LinearLayout.LayoutParams layoutParams;
    // attach view container to scroll view
    ScrollView scrollView;

    // temp account 객체
    AccountTemp t = null;

    // google sign in / out
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // navigation view setting
        setBottomNavigation();

        // view 생성을 담당할 view factory
        viewFactory = ViewFactory.getInstance(this);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Thread pool
        executor = viewFactory.executor;

        // view container
        home_view_container = viewFactory.getViewContainer();

        // sign in 상태 확인
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null) {
            t = new AccountTemp(
                    account.getDisplayName(), // name
                    account.getEmail(), // email
                    account.getId(), // id
                    account.getPhotoUrl() // photo uri
            );
        } else {
            home_view_container.addView(viewFactory.getWelcomeView());

        }

        initView();

        // 기본 view 추가
        Future<Boolean> f = viewFactory.executor.submit(
                () -> {
                    home_view_container.addView(viewFactory.getGeneralView(getString(R.string.feature_on_skillShare)));
                    home_view_container.addView(viewFactory.getGeneralView(getString(R.string.trending_now)));
                    home_view_container.addView(viewFactory.getGeneralView(getString(R.string.best_this_month)));
                    return true;
                }
        );

        setContainer();

        // 최초 view 그리기
        try {
            if(f.get())
                drawingView(home_view_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        // 사용자 로그인 되어 있으면
        if(t != null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            // google api client connection
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_left_button = findViewById(R.id.toolbar_button_l);
        toolbar_right_button = findViewById(R.id.toolbar_button_r);

        scrollView = findViewById(R.id.scroll_view);

        // refresh view setting
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener( () -> {
            // 데이터 변화 감지

            // 다른 부분이 있으면 view 를 추가하거나 삭제


            // 완료 되면 호출 ∇
            refreshLayout.setRefreshing(false);
        });
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
            if(f.get())
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
                .into(video_thumbnail);
        your_classes_view_container.addView(view);


        View meView;
        if (t != null) {
            meView = viewFactory.getMeView(t.getName());
            Glide.with(this)
                    .load(t.getPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) meView.findViewById(R.id.me_image)));
        } else {
            meView = viewFactory.getMeView("My Name");
            Glide.with(this)
                    .load(R.drawable.design)
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) meView.findViewById(R.id.me_image)));
        }
        me_view_container.addView(meView);
        me_view_container.addView(viewFactory.getMeSkillView());
    }

    private void drawingView(LinearLayout view_container) {
        Log.e("here", view_container.toString());
        // remove previous view
        scrollView.removeAllViewsInLayout();
        scrollView.requestLayout();

        // add selected view
        scrollView.addView(view_container);
    }

    private void changeToolbar(int id) {
        // to do on main thread
        if(id == R.id.navigation_home) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Home");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.VISIBLE);
        } else if(id == R.id.navigation_groups) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Groups");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.VISIBLE);
        } else if(id == R.id.navigation_discover) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Discover");
            toolbar_left_button.setVisibility(View.VISIBLE);
            toolbar_right_button.setVisibility(View.VISIBLE);
        } else if(id == R.id.navigation_your_classes) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar_title.setText("Your Classes");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.GONE);
        } else if(id == R.id.navigation_me) {
            toolbar.setVisibility(View.GONE);
        }
    }

    // 네비게이션바
    private void setBottomNavigation() {
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        // Create items (R.string.TITLE, R.drawable.ICON_IMG, R.color.NAVIGATION_BAR_BACKGROUND_COLOR)
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_home, R.color.BottomNaviBackground);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.groups, R.drawable.ic_group, R.color.BottomNaviBackground);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.discover, R.drawable.ic_discover, R.color.BottomNaviBackground);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.your_classes, R.drawable.ic_list, R.color.BottomNaviBackground);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.me, R.drawable.ic_profile, R.color.BottomNaviBackground);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        // Set Navigation Icon & Background Colors
        bottomNavigation.setColoredModeColors(getResources().getColor(R.color.IcActive), getResources().getColor(R.color.IcInactive));
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));

        // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.IcActive));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.IcInactive));
        bottomNavigation.setItemDisableColor(getResources().getColor(R.color.IcDisabled));

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

        // Add or remove notification for each item
        bottomNavigation.setNotification("3", 1);
        // OR
        /*AHNotification notification = new AHNotification.Builder()
                .setText("4")
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.BottomNaviNotiBackground))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        bottomNavigation.setNotification(notification, 1);*/

        // Set listeners
        /*bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });*/

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
        // Const
        intent.putExtra("Type", title);
        startActivity(intent);
    }

    @Override
    public void signOut() {
        if(t != null) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
                // TODO 데이터 변경만 notify...
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finish();
                t = null;
            });
        } else
            return;
    }

    // 안쓰는 ------------------------------------------------------------------------------------------
    // view 를 추가하는 메소드
//    private void addViewAt(View view, int position) {
//        container.addView(view, position, layoutParams);
//    }

    // view 를 제거하는 메소드
//    private void removeViewAt(int position) {
//        container.removeViewAt(position);
//    }
}