package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.domain.AccountTemp;
import com.immymemine.kevin.skillshare.view.ViewFactory;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.immymemine.kevin.skillshare.activity.SignInActivity.mGoogleSignInClient;

public class MainActivity extends AppCompatActivity implements ViewFactory.InteractionInterface {

    // view list <<< ( ? )
    List<View> viewList = new ArrayList<>();

    // view factory
    ViewFactory viewFactory;
    ExecutorService executor;

    // item stack for navigation view control
    int itemStack = R.id.navigation_home;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO container 자체를 저장...
        // 메모리 관리를 위해서는 Fragment or 지우고 new

        // navigation view setting
        setNavigationView();

        // view 생성을 담당할 view factory
        viewFactory = new ViewFactory(this);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Thread pool
        executor = viewFactory.executor;

        // view container
        home_view_container = viewFactory.getViewContainer();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null) {
            t = new AccountTemp(
                    account.getDisplayName(), // name
                    account.getEmail(), // email
                    account.getId(), // id
                    account.getPhotoUrl() // photo uri
            );
        } else
            viewList.add(0, viewFactory.getWelcomeView());

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

        // cache view
        // cacheView(R.id.navigation_home);
    }

    @Override
    protected void onStart() {

//        else {
//            viewList.add(0, viewFactory.getWelcomeView());
//        }

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

    Future<Boolean> g, d, y, m;
    private void setViews() {
        Log.d("start",System.currentTimeMillis()+"");
        g = executor.submit(
                () -> {
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.my_groups)));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.featured_groups)));
                    group_view_container.addView(viewFactory.getGroupView(getString(R.string.recently_active_groups)));
                    return true;
                }
        );

        d = executor.submit(
                () -> {
                    discover_view_container.addView(viewFactory.getGeneralView(getString(R.string.trending_classes)));
                    discover_view_container.addView(viewFactory.getGeneralView(getString(R.string.popular_classes)));
                    return true;
                }
        );

        View view = viewFactory.getYourClassesView();
        ImageView video_thumbnail = view.findViewById(R.id._your_classes_video_thumbnail);
        Glide.with(this)
                .load(/*thumbnail*/R.drawable.common_google_signin_btn_icon_light_normal)
                .apply(RequestOptions.centerCropTransform())
                .into(video_thumbnail);
        your_classes_view_container.addView(view);


        View meView = null;
        if (t != null) {
            meView = viewFactory.getMeView(t.getName());
            Log.d("here", "run1");
            Glide.with(this)
                    .load(t.getPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) meView.findViewById(R.id.me_image)));
            Log.d("here", "run2");
        } else {
            meView = viewFactory.getMeView("My Name");
            Log.d("here", "run3");
            Glide.with(this)
                    .load(R.drawable.design)
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) meView.findViewById(R.id.me_image)));
            Log.d("here", "run4");
        }
        me_view_container.addView(meView);
        me_view_container.addView(viewFactory.getMeSkillView());
        Log.d("done",System.currentTimeMillis()+"");

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

    // TODO navigation view 사이즈 ... 색 변경 ... noti 가능한 navigation view 로 바꿔야 함
    private void setNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.addTab(new BottomNavigationItem
                ("Home", ContextCompat.getColor(this, R.color.withoutColoredBackground), R.drawable.ic_home_black_24dp));

        bottomNavigationView.addTab(new BottomNavigationItem
                ("Group", ContextCompat.getColor(this, R.color.withoutColoredBackground), R.drawable.ic_dashboard_black_24dp));

        bottomNavigationView.addTab(new BottomNavigationItem(
                "Discover", ContextCompat.getColor(this, R.color.withoutColoredBackground), R.drawable.ic_notifications_black_24dp));

        bottomNavigationView.addTab(new BottomNavigationItem
                ("Your Classes", ContextCompat.getColor(this, R.color.withoutColoredBackground), R.drawable.ic_dashboard_black_24dp));

        bottomNavigationView.addTab(new BottomNavigationItem
                ("Me", ContextCompat.getColor(this, R.color.withoutColoredBackground), R.drawable.ic_home_black_24dp));

        bottomNavigationView.isColoredBackground(false);
        bottomNavigationView.setItemActiveColorWithoutColoredBackground(R.color.itemActiveColorWithoutColoredBackground);

        bottomNavigationView.disableShadow();
        bottomNavigationView.setTextInactiveSize(14);
        bottomNavigationView.setTextActiveSize(16);
        bottomNavigationView.selectTab(0);

        bottomNavigationView.setOnBottomNavigationItemClickListener(index -> {
            switch (index) {
                case 0:
                    if(itemStack == R.id.navigation_home) {
                        break;
                    } else {
                        itemStack = R.id.navigation_home;
                        // toolbar 바꾸기
                        changeToolbar(itemStack);
                        // container 에 담기 ( 그리기 )
                        drawingView(home_view_container);
                        break;
                    }
                case 1:
                    if(itemStack == R.id.navigation_groups) {
                        break;
                    } else {
                        itemStack = R.id.navigation_groups;
                        changeToolbar(itemStack);

                        try {
                            if(g.get())
                                drawingView(group_view_container);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                case 2:
                    if(itemStack == R.id.navigation_discover) {
                        break;
                    } else {
                        itemStack = R.id.navigation_discover;
                        changeToolbar(itemStack);
                        try {
                            if(d.get())
                                drawingView(discover_view_container);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                case 3:
                    if(itemStack == R.id.navigation_your_classes) {
                        break;
                    } else {
                        itemStack = R.id.navigation_your_classes;
                        changeToolbar(itemStack);
                        drawingView(your_classes_view_container);
                        break;
                    }
                case 4:
                    if(itemStack == R.id.navigation_me) {
                        break;
                    } else {
                        itemStack = R.id.navigation_me;
                        changeToolbar(itemStack);
                        drawingView(me_view_container);
                        break;
                    }
            }
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
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                    finish();
                });
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