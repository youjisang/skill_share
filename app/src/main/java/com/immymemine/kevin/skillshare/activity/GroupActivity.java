package com.immymemine.kevin.skillshare.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.main_adapter.GroupCommentAdapter;
import com.immymemine.kevin.skillshare.model.group.Comment;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.GroupService;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.DialogUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.communication_util.Bus;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GroupActivity extends AppCompatActivity implements GroupCommentAdapter.OnLoadMoreListener, GroupCommentAdapter.InteractionInterface {

    Toolbar toolbar;
    GroupCommentAdapter mAdapter;
    RecyclerView mRecyclerView;
    Button buttonJoinGroup;
    LinearLayout layout_discussion;
    EditText editTextComment;
    Button buttonSend;

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
        toolbar.setTitle("");
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
                        if(state.getUserInstance().getNickname() == null || state.getUserInstance().getNickname().equals("")) {
                            AlertDialog dialog = DialogUtil.showSettingNicknameDialog(this);
                            dialog.setOnDismissListener(d -> {
                                if(state.getUserInstance().getNickname() != null) {
                                    buttonJoinGroup.setVisibility(View.GONE);
                                    layout_discussion.setVisibility(View.VISIBLE);

                                    RetrofitHelper.createApi(UserService.class)
                                            .joinGroup(mGroup, state.getUserInstance().get_id())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(Schedulers.io())
                                            .subscribe(
                                                    (Response response) -> {
                                                        if(ConstantUtil.SUCCESS.equals(response.getResult())) {
                                                            state.getUserInstance().getGroups().add(mGroup);
                                                            Bus.getInstance().post(state.getUserInstance().getGroups());
                                                        } else {
                                                            // TODO 실패 메시지
                                                            Log.d("JUWONLEE","failed");
                                                        }

                                                    }, (Throwable error) -> {
                                                        Log.e("join group error :  ", error.getMessage());
                                                    }
                                            );
                                } else {
                                    buttonJoinGroup.setVisibility(View.VISIBLE);
                                    layout_discussion.setVisibility(View.GONE);
                                }
                            });
                        }
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
                                            Bus.getInstance().post(state.getUserInstance().getGroups());
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

        editTextComment = findViewById(R.id.edit_text_comment);
        buttonSend = findViewById(R.id.button_send);

        RxTextView.textChanges(editTextComment).subscribe(
                (CharSequence c) -> {
                    if(c.length()>0) {
                        buttonSend.setEnabled(true);
                        buttonSend.setTextColor(getResources().getColor(R.color.IcActive));
                    } else {
                        buttonSend.setEnabled(false);
                        buttonSend.setTextColor(getResources().getColor(R.color.IcInactive));
                    }
                }
        );

        buttonSend.setOnClickListener(
                v -> {
                    User user = StateUtil.getInstance().getUserInstance();
                    Comment comment = new Comment(
                            user.get_id(),
                            user.getName(),
                            "@" + user.getNickname(),
                            user.getImageUrl(),
                            editTextComment.getText().toString(),
                            System.currentTimeMillis() + ""
                    );

                    editTextComment.setText("");
                    // 키보드 닫기
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( editTextComment.getWindowToken(), 0);

                    RetrofitHelper.createApi(GroupService.class)
                            .sendComment(mGroup.getGroupName(), comment)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (Response response) -> {
                                        if(ConstantUtil.SUCCESS.equals(response.getResult())) {
                                            mAdapter.addItem(comment);
                                            mRecyclerView.scrollToPosition(0);
                                        } else {
                                            // 에러 메시지
                                        }
                                    }, (Throwable error) -> {

                                    }
                            );
                }
        );
    }

    private void initiateRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GroupCommentAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (llManager.findLastCompletelyVisibleItemPosition() == (mAdapter.getItemCount() - 4)) {
                    mAdapter.setMore(true);
                    mAdapter.showLoading();
                }
            }
        });
    }

    String groupId;
    @Override
    protected void onStart() {
        super.onStart();

        if(mGroup.getGroupId() == null || mGroup.getGroupId().equals("")) {
            groupId = mGroup.get_id();
        } else {
            groupId = mGroup.getGroupId();
        }

        RetrofitHelper.createApi(GroupService.class)
                .getComments(groupId, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (List<Comment> comments) -> {
                            mAdapter.addAll(comments);
                        }, (Throwable error) -> {

                        }
                );
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onLoadMore() {
        RetrofitHelper.createApi(GroupService.class)
                .getComments(groupId, mAdapter.getItemCount()-1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (List<Comment> comments) -> {
                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(comments);
                            if(comments.size() < 20) {
                                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                    }
                                });
                            }
                        }, (Throwable error) -> {

                        }
                );
    }

    @Override
    public void reply(String nickname) {
        if(editTextComment.getText().length() > 0) {
            editTextComment.setText(nickname+" ");
        } else {
            editTextComment.append(" " + nickname+" ");
        }
    }
}
