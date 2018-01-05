package com.immymemine.kevin.skillshare.adapter.main_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.GroupActivity;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

/**
 * Created by quf93 on 2017-12-29.
 */

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.Holder> {

    Context context;
    List<Group> groups;
    public GroupRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void update(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(groups == null || groups.size() == 0)
            return ConstantUtil.NO_ITEM;
        else
            return super.getItemViewType(position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == ConstantUtil.NO_ITEM)
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_group, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_group, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(groups != null && groups.size() != 0) {
            Group group = groups.get(position);

            holder.textViewGroupName.setText(group.getGroupName());
            holder.textViewMemberCount.setText(group.getMemberCount());
            Glide.with(context).load(group.getGroupThumbnail())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.imageViewGroup);

            holder.position = position;
        }
    }

    @Override
    public int getItemCount() {
        if(groups == null || groups.size() == 0)
            return 1;
        return groups.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageViewGroup;
        TextView textViewMemberCount, textViewGroupName;

        int position;

        public Holder(View view) {
            super(view);
            if(groups != null && groups.size() != 0) {
                imageViewGroup = view.findViewById(R.id.image_view_group);
                textViewMemberCount = view.findViewById(R.id.text_view_member_count);
                textViewGroupName = view.findViewById(R.id.text_view_group_name);

                view.setOnClickListener(
                        v -> {
                            Intent intent = new Intent(context, GroupActivity.class);
                            Group group = groups.get(position);
                            intent.putExtra("group", group);
                            context.startActivity(intent);
                        }
                );
            }
        }
    }
}
