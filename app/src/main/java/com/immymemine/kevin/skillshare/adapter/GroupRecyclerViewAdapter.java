package com.immymemine.kevin.skillshare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.GroupActivity;
import com.immymemine.kevin.skillshare.model.dummy.Group;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

/**
 * Created by quf93 on 2017-11-18.
 */

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.GroupViewHolder> {

    List<Group> groups;
    Context context;
    Intent intent;

    boolean dataValidationCheck;
    public GroupRecyclerViewAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        dataValidationCheck = groups != null && groups.size() != 0;
        if (!dataValidationCheck)
            return ConstantUtil.NO_ITEM;
        else
            return super.getItemViewType(position);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == ConstantUtil.NO_ITEM)
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_group, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_group, parent, false);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {

        if (dataValidationCheck) {
            Group group = groups.get(position);
            holder.textViewGroup.setText(group.getGroupName());
            holder.textViewCount.setText(group.getGroupJoinNum());
            Glide.with(context).load(group.getImageUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (!dataValidationCheck)
            return 1;
        else
            return groups.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewCount, textViewGroup;

        public GroupViewHolder(View view) {
            super(view);

            if (dataValidationCheck) {

                imageView = view.findViewById(R.id.image_view_tutor);
                textViewCount = view.findViewById(R.id.text_view_count);
                textViewGroup = view.findViewById(R.id.text_view_group);
                view.setOnClickListener(v -> {

                    intent = new Intent(context, GroupActivity.class);

                    intent.putExtra("position", getLayoutPosition());
                    intent.putExtra("groupName", groups.get(getLayoutPosition()).getGroupName());
                    intent.putExtra("groupJoinNum", groups.get(getLayoutPosition()).getGroupJoinNum());
                    intent.putExtra("groupImageUri", groups.get(getLayoutPosition()).getImageUrl());

                    ((Activity) context).startActivityForResult(intent, ConstantUtil.ALREADY_JOIN_GROUP);


                });
            }
        }


    }
}




