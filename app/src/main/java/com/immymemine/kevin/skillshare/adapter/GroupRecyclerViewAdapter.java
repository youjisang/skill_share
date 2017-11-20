package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

/**
 * Created by quf93 on 2017-11-18.
 */

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.GroupViewHolder> {

    Context context;
    public GroupRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_group, parent, false);
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
        LinearLayout recyclerViewItemGroup;
        ImageView imageView;
        TextView textViewCount, textViewGroup;

        public GroupViewHolder(View view) {
            super(view);
            recyclerViewItemGroup = view.findViewById(R.id.recycler_view_item_group);
            imageView = view.findViewById(R.id.imageView);
            textViewCount = view.findViewById(R.id.text_view_count);
            textViewGroup = view.findViewById(R.id.text_view_group);
            recyclerViewItemGroup.setOnClickListener(v -> {
                // 그룹 가입
            });
        }
    }
}
