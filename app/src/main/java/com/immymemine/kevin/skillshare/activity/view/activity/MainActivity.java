package com.immymemine.kevin.skillshare.activity.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.utility.Const;
import com.immymemine.kevin.skillshare.activity.view.ViewFactory;

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
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // view 생성을 담당할 view factory
        viewFactory = new ViewFactory(this);

        // if(/*로그인 되어 있지 않으면*/) {
        viewList.add(viewFactory.getViewInstance(Const.WELCOME_VIEW, ""));
        // }

        // 기본 view 추가
        viewList.add(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Feature On SkillShare"));
        viewList.add(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Trending now"));
        viewList.add(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Best This Month"));

        initView();
        // 최초 view 그리기
        drawingView();

        // cache view
        cacheView(R.id.navigation_home);
    }

    private void initView() {

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_left_button = findViewById(R.id.toolbar_button_l);
        toolbar_right_button = findViewById(R.id.toolbar_button_r);

        // refresh view setting
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 데이터 변화 감지

                // 다른 부분이 있으면 view 를 추가하거나 삭제

                // 완료 되면 호출 ∇
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void cacheView(int id) {
        if(VIEW_CACHE.get(id) == null) {
            List<View> cacheViewList = new ArrayList<>();
            cacheViewList.addAll(viewList);
            VIEW_CACHE.put(id, cacheViewList); // VIEW_CACHE 저장
        }
    }

    private void setViewList(int id) {
        // view list 비우기
        viewList.clear();

        // view list 채우기
        if(VIEW_CACHE.get(id) != null)
            viewList.addAll(VIEW_CACHE.get(id));
        else {
            if (id == R.id.navigation_groups) {
                viewList.add(viewFactory.getViewInstance(Const.GROUP_VIEW, "Feature Groups"));
                viewList.add(viewFactory.getViewInstance(Const.GROUP_VIEW, "Recently Active Groups"));
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
        if(id == R.id.navigation_home) {
            toolbar_title.setText("Home");
        } else if(id == R.id.navigation_groups) {
            toolbar_title.setText("Groups");
            toolbar_left_button.setVisibility(View.GONE);
        } else if(id == R.id.navigation_discover) {
            toolbar_title.setText("Discover");
            toolbar_left_button.setVisibility(View.GONE);
        } else if(id == R.id.navigation_your_classes) {
            toolbar_title.setText("Your Classes");
            toolbar_left_button.setVisibility(View.GONE);
            toolbar_right_button.setVisibility(View.GONE);
        } else if(id == R.id.navigation_my) {
            // toolbar 를 바꿔야 함..................
        }
    }

    private void setNavigationView() {
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if(itemStack == R.id.navigation_home) {
                            return true;
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
                            return true;
                        }

                    case R.id.navigation_groups:
                        if(itemStack == R.id.navigation_groups) {
                            return true;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_groups;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            return true;
                        }
                    case R.id.navigation_discover:
                        if(itemStack == R.id.navigation_discover) {
                            return true;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_discover;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            return true;
                        }
                    case R.id.navigation_your_classes:
                        if(itemStack == R.id.navigation_your_classes) {
                            return true;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_your_classes;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            return true;
                        }
                    case R.id.navigation_my:
                        if(itemStack == R.id.navigation_my) {
                            return true;
                        } else {
                            cacheView(itemStack);
                            itemStack = R.id.navigation_my;
                            changeToolbar(itemStack);
                            setViewList(itemStack);
                            drawingView();
                            return true;
                        }
                }
                return false;
            }
        });
    }

    @Override
    public void interact() {
        container.removeViewAt(0);
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