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
import com.immymemine.kevin.skillshare.entity.GroupItem;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JisangYou on 2017-12-13.
 */


public class GroupChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<GroupItem> groupItemList;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isMoreLoading = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public GroupChattingAdapter(GroupActivity onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        groupItemList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return groupItemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
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
        if (isMoreLoading && groupItemList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    groupItemList.add(null);
                    notifyItemInserted(groupItemList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (groupItemList != null && groupItemList.size() > 0) {
            groupItemList.remove(groupItemList.size() - 1);
            notifyItemRemoved(groupItemList.size());
        }
    }

    public void addAll(ArrayList<GroupItem> lst) {
        groupItemList.clear();
        groupItemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<GroupItem> lst) {
        int sizeInit = groupItemList.size();
        groupItemList.addAll(lst);
        notifyItemRangeChanged(sizeInit, groupItemList.size());
    }

    public void clear() {
        groupItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupChattingHolder) {
            GroupItem singleGroupItem = (GroupItem) groupItemList.get(position);
//            ((GroupChattingHolder) holder).imageViewProfile.set(singleGroupItem.getItem());
//            ((GroupChattingHolder) holder).textViewProfile.setText(singleGroupItem.getItem());
//            ((GroupChattingHolder) holder).textViewProfileHashtag.setText(singleGroupItem.getItem());
//            ((GroupChattingHolder) holder).expandableTextView.setText(singleGroupItem.getItem());
//            ((GroupChattingHolder) holder).textViewTime.setText(singleGroupItem.getItem());
//            ((GroupChattingHolder) holder).textViewLike.setText(singleGroupItem.getItem());
        }
    }


    @Override
    public int getItemCount() {
        return groupItemList.size();
    }

    static class GroupChattingHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProfile;
        TextView textViewProfile;
        TextView textViewProfileHashtag;
        ExpandableTextView expandableTextView;
        TextView textViewTime;
        TextView textViewLike;

        public GroupChattingHolder(View v) {
            super(v);
            imageViewProfile = (ImageView) v.findViewById(R.id.image_view_profile);
//            textViewProfile = (TextView) v.findViewById(R.id.text_view_profile);
//            textViewProfileHashtag = (TextView) v.findViewById(R.id.text_view_nickName);
            expandableTextView = (ExpandableTextView) v.findViewById(R.id.expandable_text_view);
            textViewTime = (TextView) v.findViewById(R.id.text_view_time);
            textViewLike = (TextView) v.findViewById(R.id.text_view_reply);

        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;

        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }

}


