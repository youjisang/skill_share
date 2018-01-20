package com.immymemine.kevin.skillshare.adapter.main_adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.ProfileActivity;
import com.immymemine.kevin.skillshare.model.group.Comment;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.TimeUtil;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JisangYou on 2017-12-13.
 */


public class GroupCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int VIEW_ITEM = 1;
    final int VIEW_PROG = 0;

    ArrayList<Comment> comments;

    Context context;
    OnLoadMoreListener onLoadMoreListener;
    InteractionInterface interactionInterface;

    boolean isMoreLoading = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public GroupCommentAdapter(Context context) {
        this.context = context;

        if(context instanceof OnLoadMoreListener) {
            this.onLoadMoreListener = (OnLoadMoreListener) context;
        }

        if(context instanceof InteractionInterface) {
            this.interactionInterface = (InteractionInterface) context;
        }

        comments = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return comments.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new GroupChattingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_comment, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }
    }

    public void showLoading() {
        if (isMoreLoading && comments != null && onLoadMoreListener != null) {
            isMoreLoading = false;

            new Handler().post(() -> {
                // progress bar holder 호출
                comments.add(null);
                notifyItemInserted(comments.size() - 1);
                // data load
                onLoadMoreListener.onLoadMore();
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (comments != null && comments.size() > 0) {
            comments.remove(comments.size() - 1);
            notifyItemRemoved(comments.size());
        }
    }

    public void addAll(List<Comment> lst) {
        comments.clear();
        comments.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Comment> lst) {
        int sizeInit = comments.size();
        comments.addAll(lst);
        notifyItemRangeChanged(sizeInit, comments.size());
    }

    public void addItem(Comment comment) {
        comments.add(0, comment);
        notifyItemInserted(0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupChattingHolder) {
            Comment comment = comments.get(position);

            GroupChattingHolder chatHolder = (GroupChattingHolder) holder;

            chatHolder.userId = comment.getUserId();
            chatHolder.textViewName.setText(comment.getUserName());
            chatHolder.textViewNickname.setText(comment.getUserNickname());
            if(comment.getComment().contains("@")) {
                int index = comment.getComment().indexOf("@");

            }
            chatHolder.expandableTextView.setText(comment.getComment());
            chatHolder.textViewTime.setText(TimeUtil.calculateTime(comment.getTime()));
        }
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class GroupChattingHolder extends RecyclerView.ViewHolder {

        String userId;
        ImageView imageViewProfile;
        TextView textViewName, textViewNickname, textViewTime;
        ExpandableTextView expandableTextView;
        TextView reply;

        public GroupChattingHolder(View v) {
            super(v);
            imageViewProfile = v.findViewById(R.id.image_view_profile);
            imageViewProfile.setOnClickListener(
                    view -> {
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra(ConstantUtil.USER_ID_FLAG, userId);
                        context.startActivity(intent);
                    }
            );
            textViewName = v.findViewById(R.id.text_view_user_name);
            textViewNickname = v.findViewById(R.id.text_view_user_nickname);
            expandableTextView = v.findViewById(R.id.expandable_text_view);
            expandableTextView.setTrimLength(8);
            textViewTime = v.findViewById(R.id.text_view_time);
            reply = v.findViewById(R.id.text_view_reply);
            reply.setOnClickListener(
                    view -> {
                        interactionInterface.reply(textViewNickname.getText().toString());
                    }
            );
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;

        public ProgressViewHolder(View v) {
            super(v);
            pBar = v.findViewById(R.id.pBar);
        }
    }

    public interface InteractionInterface {
        void reply(String nickname);
    }
}


