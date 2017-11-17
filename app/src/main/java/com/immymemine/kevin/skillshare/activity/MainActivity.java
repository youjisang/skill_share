package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.fragment.GroupFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // recycler view 를 가지고 있는 View 를 담는 container
    LinearLayout container;
    LinearLayout.LayoutParams layoutParams;

    // view list <<< ( ? )
    List<View> viewList = new ArrayList<>();

    FragmentManager fragmentManager;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroupFragment fragment = new GroupFragment();

        setNavigationView();

        // view 를 추가 삭제 할 container
        container = findViewById(R.id.container);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // view 생성을 담당할 view factory
        ViewFactory viewFactory = new ViewFactory(this);

        // if(/*로그인 되어 있지 않으면*/) {
        viewList.add(viewFactory.getViewInstance(Const.WELCOME_VIEW, ""));
        // }

        // 기본 view 추가
        viewList.add(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Feature On SkillShare"));
        viewList.add(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Trending now"));
        viewList.add(viewFactory.getViewInstance(Const.GENERAL_VIEW, "Best This Month"));

        initView();

        //fragment
        fragmentManager = getSupportFragmentManager();
    }

    private void initView() {
        for(int i=0; i<viewList.size(); i++) {
            container.addView(viewList.get(i), i, layoutParams);
        }

        // refresh view setting
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 데이터 가져오기

                // 이전과 같다면 그대로 뿌려주고

                // 다른 부분이 있으면 view 를 추가하거나 삭제

                // 완료 되면 호출
                refreshLayout.setRefreshing(false);
            }
        });
    }

    // view 를 추가하는 메소드
    private void addViewAt(View view, int position) {
        container.addView(view, position, layoutParams);
    }

    // view 를 제거하는 메소드
    private void removeViewAt(int position) {
        container.removeViewAt(position);
    }

    private void setNavigationView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_groups:
                        container.removeAllViews();
                        fragmentManager
                                .beginTransaction()
                                .add(container.getId(), new GroupFragment(), "group frag")
                                .commit();
                        return true;
                    case R.id.navigation_discover:
                        return true;
                    case R.id.navigation_your_classes:
                        return true;
                    case R.id.navigation_my:
                        return true;
                }
                return false;
            }
        });
    }
}