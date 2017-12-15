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

    List<Group> groupList;
    Context context;
    int size;

    Intent intent;

    Group dummy;

    public GroupRecyclerViewAdapter(List<Group> groupList, Context context, int size) {
        this.groupList = groupList;
        this.context = context;
        this.size = size;
    }


    @Override
    public int getItemViewType(int position) {

        if (size == 0)
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


        if (size != 0) {

            dummy = groupList.get(position);

            holder.textViewCount.setText(dummy.getGroupJoinNum());

            holder.textViewGroup.setText(dummy.getGroupName());


            Glide.with(context).load(dummy.getGroupImage()).into(holder.imageView);
//            로그인 후 그룹카테고리에 들어가면  Exception이 발생! 그 이유는 Activity가 끝난 상태에서 Glide with 함수를 호출해서 발생한 문제


        }
        // Group group = data.get(position);

        // holder.textViewGroup.setText(/* Group title ex) Graphic Designers */);
        // holder.textViewCount.setText(/* ex) 3.1K */);
        // Glide.with(context).load(/* Uri || Url */).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        if (size == 0)
            return 1;
        else
            // return data.size();
            return groupList.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewCount, textViewGroup;

        public GroupViewHolder(View view) {
            super(view);
            if (size != 0) {

                imageView = view.findViewById(R.id.image_view_group);
                textViewCount = view.findViewById(R.id.text_view_count);
                textViewGroup = view.findViewById(R.id.text_view_group);
                view.setOnClickListener(v -> {
                    // TODO if (그룹원이 아니면) 그룹 가입 else 밑에 edittext 창이 나와야 함
                    intent = new Intent(context, GroupActivity.class);

                    intent.putExtra("position", getLayoutPosition());
                    intent.putExtra("groupName", groupList.get(getLayoutPosition()).getGroupName());
                    intent.putExtra("groupJoinNum", groupList.get(getLayoutPosition()).getGroupJoinNum());
                    intent.putExtra("groupImageUri", groupList.get(getLayoutPosition()).getGroupImage());
                    v.getContext().startActivity(intent);
                    Log.e("=GroupAdapter=", "=========startActivity======" + intent);



                    ((Activity) context).startActivityForResult(intent, ConstantUtil.ALREADY_JOIN_GROUP);
                    Log.e("=GroupAdapter=", "=========startActivityForResult======" + intent);

                });
            }
        }


    }
}



