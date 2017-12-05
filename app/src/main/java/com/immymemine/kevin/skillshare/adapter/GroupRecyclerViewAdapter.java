package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

/**
 * Created by quf93 on 2017-11-18.
 */

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.GroupViewHolder> {

    Context context;
    public GroupRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    int size;
    @Override
    public int getItemViewType(int position) {
        // size = data.size();
        size = 0;
        if(size == 0)
            return ConstantUtil.NO_ITEM;
        else
            return super.getItemViewType(position);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == ConstantUtil.NO_ITEM)
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_group, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_group, parent, false);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        // Group group = data.get(position);

        // holder.textViewGroup.setText(/* Group title ex) Graphic Designers */);
        // holder.textViewCount.setText(/* ex) 3.1K */);
        // Glide.with(context).load(/* Uri || Url */).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // return data.size();
        return 5;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewCount, textViewGroup;

        public GroupViewHolder(View view) {
            super(view);
            if(size != 0) {
                imageView = view.findViewById(R.id.image_view_tutor);
                textViewCount = view.findViewById(R.id.text_view_count);
                textViewGroup = view.findViewById(R.id.text_view_group);
                view.setOnClickListener(v -> {
                    // TODO if (그룹원이 아니면) 그룹 가입 else 밑에 edittext 창이 나와야 함
                });
            }
        }
    }
}
