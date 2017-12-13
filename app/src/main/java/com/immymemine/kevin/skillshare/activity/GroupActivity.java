package com.immymemine.kevin.skillshare.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.GroupChattingAdapter;
import com.immymemine.kevin.skillshare.entity.GroupItem;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements GroupChattingAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private GroupChattingAdapter mAdapter;
    private ArrayList<GroupItem> groupItemList;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.chatting_toolbar_menu);

        groupItemList = new ArrayList<>();
        RecyclerView mRecyclerView =  findViewById(R.id.recycler_view_chatting);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GroupChattingAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (llManager.findLastCompletelyVisibleItemPosition() == (mAdapter.getItemCount() - 4)) {
                    mAdapter.showLoading();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("GroupActivity_","onStart");
        loadData();
    }

    @Override
    public void onRefresh() {
        Log.d("GroupActivity_","onRefresh");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                loadData();
            }
        },2000);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onLoadMore() {
        new AsyncTask<Void,Void,List<GroupItem>>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mAdapter.showLoading();
            }

            // swype add item에 대한 코드
            @Override
            protected List<GroupItem> doInBackground(Void... voids) {
                int start = mAdapter.getItemCount() - 1;
                int end = start + 10;
                List<GroupItem> list = new ArrayList<>();
                if (end < 200) {
                    for (int i = start + 1; i <= end; i++) {
                        list.add(new GroupItem("GroupItem " + i));
                    }
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<GroupItem> groupItems) {
                super.onPostExecute(groupItems);
                mAdapter.dismissLoading();
                mAdapter.addItemMore(groupItems);
                mAdapter.setMore(true);
            }
        }.execute();
    }

    private void loadData() {
        groupItemList.clear();
        for (int i = 1; i <= 20; i++) {
            groupItemList.add(new GroupItem("GroupItem " + i));
        }
        mAdapter.addAll(groupItemList);
    }
}


