package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.view.ViewFactory;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ViewFactory.InteractionInterface {

    // recycler view 를 가지고 있는 View 를 담는 container
    LinearLayout container;
    LinearLayout.LayoutParams layoutParams;

    // view list <<< ( ? )
    List<View> viewList = new ArrayList<>();

    // view cache
    private static final Map<Integer, List<View>> VIEW_CACHE = new HashMap<>();

    // view factory
    ViewFactory viewFactory;

    // item stack for navigation view control
    int itemStack = R.id.navigation_home;

    // toolbar initiate
    Toolbar toolbar;
    TextView toolbar_title;
    ImageButton toolbar_left_button, toolbar_right_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // navigation view setting
        setNavigationView();

        // view 를 추가 삭제 할 container
        container = findViewById(R.id.container);

        // view 생성을 담당할 view factory
        viewFactory = new ViewFactory(this);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // if(/*로그인 되어 있지 않으면*/) {
        viewList.add(viewFactory.getWelcomeView());
        // }

        // 기본 view 추가
        viewList.add(viewFactory.getGeneralView(getString(R.string.feature_on_skillShare)));
        viewList.add(viewFactory.getGeneralView(getString(R.string.trending_now)));
        viewList.add(viewFactory.getGeneralView(getString(R.string.best_this_month)));

        initView();
        // 최초 view 그리기
        drawingView();

        // cache view
        cacheView(R.id.navigation_home);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_left_button = findViewById(R.id.toolbar_button_l);
        toolbar_right_button = findViewById(R.id.toolbar_button_r);

        // refresh view setting
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener( () -> {
            // 데이터 변화 감지

            // 다른 부분이 있으면 view 를 추가하거나 삭제


            // 완료 되면 호출 ∇
            refreshLayout.setRefreshing(false);
        });
    }

    private void cacheView(int id) {
        new Thread() {
            public void run() {
                if(VIEW_CACHE.get(id) == null) {
                    List<View> cacheViewList = new ArrayList<>();
                    cacheViewList.addAll(viewList);
                    VIEW_CACHE.put(id, cacheViewList); // VIEW_CACHE 저장
                }
            }
        }.start();
    }

    private void setViewList(int id) {

        new Thread(
                () -> {

                }
        ).start();
        // view list 비우기
        viewList.clear();

        // view list 채우기
        if(VIEW_CACHE.get(id) != null)
            viewList.addAll(VIEW_CACHE.get(id));
        else {
            if (id == R.id.navigation_groups) {
                viewList.add(viewFactory.getGroupView(getString(R.string.my_groups)));
                viewList.add(viewFactory.getGroupView(getString(R.string.featured_groups)));
                viewList.add(viewFactory.getGroupView(getString(R.string.recently_active_groups)));
            } else if(id == R.id.navigation_discover) {
                viewList.add(viewFactory.getGeneralView(getString(R.string.trending_classes)));
                viewList.add(viewFactory.getGeneralView(getString(R.string.popular_classes)));
            } else if(id == R.id.navigation_your_classes) {
                viewList.add(viewFactory.getYourClassesView());
            } else if(id == R.id.navigation_me) {
                viewList.add(viewFactory.getMeView());
                viewList.add(viewFactory.getMeSkillView());
            }
        }
    }

    private void drawingView() {
        container.removeAllViews();

        for(int i=0; i<viewList.size(); i++) {
            container.addView(viewList.get(i), i, layoutParams);
        }
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

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index) {
                    case 0:
                        if(itemStack == R.id.navigation_home) {
                            break;
                        } else {
                            // 이전 페이지의 view 들을 캐싱한다.
                            cacheView(itemStack);
                            itemStack = R.id.navigation_home;
                            // toolbar 바꾸기
                            changeToolbar(itemStack);
                            // viewList 를 다시 세팅
                            setViewList(itemStack);
                            // container 에 담기 ( 그리기 )
                            drawingView();
                            break;
                        }
                    case 1:
                        if(itemStack == R.id.navigation_groups) {
                            break;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_groups;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            break;
                        }
                    case 2:
                        if(itemStack == R.id.navigation_discover) {
                            break;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_discover;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            break;
                        }
                    case 3:
                        if(itemStack == R.id.navigation_your_classes) {
                            break;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_your_classes;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            break;
                        }
                    case 4:
                        if(itemStack == R.id.navigation_me) {
                            break;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_me;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            break;
                        }
                }
            }
        });
    }

    @Override
    public void close() {
        container.removeViewAt(0);
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
        intent.putExtra("Type", title);
        startActivity(intent);
    }


    // ------------------------------------------------------------------------------------------
    // view 를 추가하는 메소드
    private void addViewAt(View view, int position) {
        container.addView(view, position, layoutParams);
    }

    // view 를 제거하는 메소드
    private void removeViewAt(int position) {
        container.removeViewAt(position);
    }
}