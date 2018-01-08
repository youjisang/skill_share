package com.immymemine.kevin.skillshare.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.GroupChatAdapter;
import com.immymemine.kevin.skillshare.model.group.Chat;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.GroupService;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.DialogUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GroupActivity extends AppCompatActivity implements GroupChatAdapter.OnLoadMoreListener {

    Toolbar toolbar;
    GroupChatAdapter mAdapter;
    RecyclerView mRecyclerView;
    Button buttonJoinGroup;
    LinearLayout layout_discussion;

    Group mGroup;
    StateUtil state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        mGroup = getIntent().getParcelableExtra("group");
        state = StateUtil.getInstance();

        initiateView();
        initiateRecyclerView();
    }

    private void initiateView() {
        // toolbar
        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());
        ((TextView)findViewById(R.id.toolbar_title)).setText(mGroup.getGroupName());
        ((TextView)findViewById(R.id.text_view_member_count)).setText(mGroup.getMemberCount() + " Members");
        toolbar = findViewById(R.id.toolbar);
        Glide.with(this).asDrawable().load(mGroup.getGroupThumbnail()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                toolbar.setBackground(resource);
            }
        });
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.chatting_toolbar_menu);

        // chat
        mRecyclerView = findViewById(R.id.recycler_view_chatting);

        buttonJoinGroup = findViewById(R.id.button_join);
        layout_discussion = findViewById(R.id.layout_frame_discussion);

        if(state.getState()) {
            if(state.getUserInstance().getGroups() != null) {
                if(state.getUserInstance().getGroups().contains(mGroup)) {
                    buttonJoinGroup.setVisibility(View.GONE);
                    layout_discussion.setVisibility(View.VISIBLE);
                } else {
                    buttonJoinGroup.setOnClickListener(v -> {
                        buttonJoinGroup.setVisibility(View.GONE);
                        layout_discussion.setVisibility(View.VISIBLE);

                        RetrofitHelper.createApi(UserService.class)
                                .joinGroup(mGroup, state.getUserInstance().get_id())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        (Response response) -> {
                                            if(ConstantUtil.SUCCESS.equals(response.getResult()))
                                                state.getUserInstance().getGroups().add(mGroup);
                                            else {
                                                // TODO 실패 메시지
                                            }

                                        }, (Throwable error) -> {
                                            Log.e("join group error :  ", error.getMessage());
                                        }
                                );
                    });
                }
            } else {
                buttonJoinGroup.setOnClickListener(v -> {
                    buttonJoinGroup.setVisibility(View.GONE);
                    layout_discussion.setVisibility(View.VISIBLE);

                    RetrofitHelper.createApi(UserService.class)
                            .joinGroup(mGroup, state.getUserInstance().get_id())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (Response response) -> {
                                        if(ConstantUtil.SUCCESS.equals(response.getResult())) {
                                            state.getUserInstance().setGroups(new ArrayList<>());
                                            state.getUserInstance().getGroups().add(mGroup);
                                        }

                                        else {
                                            // TODO 실패 메시지
                                        }

                                    }, (Throwable error) -> {
                                        Log.e("join group error :  ", error.getMessage());
                                    }
                            );
                });
            }
        } else {
            buttonJoinGroup.setOnClickListener(v -> {
                DialogUtil.showSignDialog(this);
            });
        }
    }

    private void initiateRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GroupChatAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (llManager.findLastCompletelyVisibleItemPosition() == (mAdapter.getItemCount() - 4)) {
                    onLoadMore();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RetrofitHelper.createApi(GroupService.class)
                .getChatList(mGroup.get_id(), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (List<Chat> chatList) -> {
                            mAdapter.addAll(chatList);
                        }, (Throwable error) -> {

                        }
                );
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onLoadMore() {
        mAdapter.setMore(true);
        mAdapter.showLoading();

        RetrofitHelper.createApi(GroupService.class)
                .getChatList(mGroup.get_id(),mAdapter.getItemCount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (List<Chat> chatList) -> {
                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(chatList);
                        }, (Throwable error) -> {

                        }
                );
    }
}
