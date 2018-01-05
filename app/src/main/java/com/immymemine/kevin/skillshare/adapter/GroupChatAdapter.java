package com.immymemine.kevin.skillshare.adapter;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.GroupActivity;
import com.immymemine.kevin.skillshare.model.group.Chat;
import com.immymemine.kevin.skillshare.utility.TimeUtil;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JisangYou on 2017-12-13.
 */


public class GroupChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<Chat> chatList;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isMoreLoading = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public GroupChatAdapter(GroupActivity onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        chatList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new GroupChattingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_chatting, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }
    }

    public void showLoading() {
        if (isMoreLoading && chatList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(() -> {
                // progress bar holder 호출
                chatList.add(null);
                notifyItemInserted(chatList.size() - 1);
                // data load
                onLoadMoreListener.onLoadMore();
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (chatList != null && chatList.size() > 0) {
            chatList.remove(chatList.size() - 1);
            notifyItemRemoved(chatList.size());
        }
    }

    public void addAll(List<Chat> lst) {
        chatList.clear();
        chatList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Chat> lst) {
        int sizeInit = chatList.size();
        chatList.addAll(lst);
        notifyItemRangeChanged(sizeInit, chatList.size());
    }

    public void clear() {
        chatList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupChattingHolder) {
            Chat chat = chatList.get(position);
            GroupChattingHolder chatHolder = (GroupChattingHolder) holder;
            chatHolder.userId = chat.getUserId();
            chatHolder.textViewUserName.setText(chat.getUserName());
            chatHolder.textViewUserHashtag.setText(chat.getUserHashTag());
            chatHolder.expandableTextView.setText(chat.getComment());
            chatHolder.textViewTime.setText(TimeUtil.calculateTime(chat.getTime()));
        }
    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class GroupChattingHolder extends RecyclerView.ViewHolder {

        String userId;
        ImageView imageViewProfile;
        TextView textViewUserName, textViewUserHashtag, textViewTime;
        ExpandableTextView expandableTextView;
        TextView reply;

        public GroupChattingHolder(View v) {
            super(v);
            imageViewProfile = v.findViewById(R.id.image_view_profile);
            textViewUserName = v.findViewById(R.id.text_view_user_name);
            textViewUserHashtag = v.findViewById(R.id.text_view_user_hashtag);
            expandableTextView = v.findViewById(R.id.expandable_text_view);
            textViewTime = v.findViewById(R.id.text_view_time);
            reply = v.findViewById(R.id.text_view_reply);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;

        public ProgressViewHolder(View v) {
            super(v);
            pBar = v.findViewById(R.id.pBar);
        }
    }

}


