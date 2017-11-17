package com.immymemine.kevin.skillshare.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.adapter.HomeRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    // 당겼을 때 새로고침
    SwipeRefreshLayout refreshLayout;

    // recycler view 를 가지고 있는 View 를 담는 container
    LinearLayout container;

    // view 객체 만들기 위해 사용되는 inflater
    LayoutInflater inflater;
    RecyclerView recyclerView;

    // for test
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationView();

        // view 를 추가 삭제 할 container
        container = findViewById(R.id.container);

        // for test
        position = 0;

        // inflater initiate
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // view 추가
        addView(position);
        position++;

        initView();
    }


    private void initView() {
        refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 데이터 가져오기

                // 이전과 같다면 그대로 뿌려주고

                // 다른 부분이 있으면 view 를 추가하거나 삭제
                addView(position);
                position++;

                // 완료 되면 호출
                refreshLayout.setRefreshing(false);
            }
        });
    }

    // view 를 추가하는 함수
    private void addView(int position) {
        View newView = inflater.inflate(R.layout.home_view, null);

        recyclerView = newView.findViewById(R.id.home_recycler_view);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(position);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Log.e("this position", position + "");
        container.addView(newView, position, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    // view 를 제거하는 함수
    private void removeView(int position) {
        container.removeViewAt(position);
    }

    private void setNavigationView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        removeView(0);
                        position--;
                        return true;
                    case R.id.navigation_groups:
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