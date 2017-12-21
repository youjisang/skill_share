package com.immymemine.kevin.skillshare.activity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.media.Image;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.GroupChattingAdapter;
import com.immymemine.kevin.skillshare.entity.GroupItem;
import com.immymemine.kevin.skillshare.model.user.Group;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import org.w3c.dom.Text;


import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.GroupChattingAdapter;
import com.immymemine.kevin.skillshare.entity.GroupItem;
import com.immymemine.kevin.skillshare.utility.DialogUtil;


import java.util.ArrayList;
import java.util.List;


public class GroupActivity extends AppCompatActivity implements GroupChattingAdapter.OnLoadMoreListener
        , SwipeRefreshLayout.OnRefreshListener {


    private Toolbar toolbar;
    private GroupChattingAdapter mAdapter;
    private ArrayList<GroupItem> groupItemList;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView mRecyclerView;
    private Button groupJoin;
    private ImageButton back;
    private TextView groupTitle, groupNum;
    private String groupTitle_s, groupNum_s, imageUri_s;
    private int position;
    private LinearLayout layout_discussion;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        initiateView();

        toolbarSetting();

        checkJoinOrNot();

        backButton();

        fromGroupRecylcerViewAdapter();

        settingViewFromData();

        initRecycler();


        groupItemList = new ArrayList<>();


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


    //TODO 지상 ----------------------------------------------------
    private void toolbarSetting() {

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.chatting_toolbar_menu);
    }

    private void initiateView() {
        mRecyclerView = findViewById(R.id.recycler_view_chatting);
        groupJoin = findViewById(R.id.button_join);
        toolbar = findViewById(R.id.toolbar);
        groupTitle = findViewById(R.id.toolbar_title);
        groupNum = findViewById(R.id.textview_lookup_number);
        layout_discussion = findViewById(R.id.layout_frame_discussion);
        back = findViewById(R.id.toolbar_button_back);

    }

    private void initRecycler() {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GroupChattingAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fromGroupRecylcerViewAdapter() {

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        groupTitle_s = intent.getStringExtra("groupName");
        groupNum_s = intent.getStringExtra("groupJoinNum");
        imageUri_s = intent.getStringExtra("groupImageUri");
        Log.e("groupActivity", "========groupname========" + groupTitle_s + "=========groupjoinnum==========" + groupNum_s + "========groupImageUri===========" + imageUri_s);

    }

    private void settingViewFromData() {
        groupNum.setText(groupNum_s);
        groupTitle.setText(groupTitle_s);

    }

    private void checkJoinOrNot() {


        groupJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO 지상 로그인이 되어 있을 때, ▽

                if (MainActivity.isSignIn == true) {
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("groupName", groupTitle_s);
                    intent.putExtra("groupJoinNum", groupNum_s);
                    intent.putExtra("groupImageUri", imageUri_s);
                    setResult(RESULT_OK, intent);
                    groupJoin.setVisibility(View.GONE);
                    layout_discussion.setVisibility(View.VISIBLE);
                }

                //TODO 지상 로그인 및 회원가입이 안되어 있을 때, ▽
                if (MainActivity.isSignIn == false) {
                    DialogUtil.showGroupDialog(v.getContext());
                }

            }
        });
    }

    private void backButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("GroupActivity_", "onStart");
        loadData();
    }

    @Override
    public void onRefresh() {
        Log.d("GroupActivity_", "onRefresh");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                loadData();

            }
        }, 2000);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onLoadMore() {
        new AsyncTask<Void, Void, List<GroupItem>>() {
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
