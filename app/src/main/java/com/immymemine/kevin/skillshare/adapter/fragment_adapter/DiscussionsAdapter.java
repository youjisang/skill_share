package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.model_class.Discussion;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.diff_util.DiscussionDiffCallback;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionsAdapter extends RecyclerView.Adapter<DiscussionsAdapter.Holder> {
    Context context;
    List<Discussion> discussions;

    int size;
    public DiscussionsAdapter(Context context, List<Discussion> discussions) {
        this.context = context;
        this.discussions = discussions;
    }

    public void updateData(List<Discussion> discussions) {
        DiscussionDiffCallback callback = new DiscussionDiffCallback(this.discussions, discussions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        this.discussions.clear();
        this.discussions.addAll(discussions);
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        size = discussions.size();
        if(size == 0) {
            return ConstantUtil.NO_ITEM;
        }
        else
            return super.getItemViewType(position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == ConstantUtil.NO_ITEM)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_no_discussions, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_discussions, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(size != 0) {
            Discussion discussion = discussions.get(position);
            // identifier
            holder.id = discussion.get_id();
            // profile
            Glide.with(context).load(discussion.getPictureUrl()).into(holder.imageViewProfile);
            holder.textViewProfile.setText(discussion.getName());
            // content
            holder.expandableTextView.setText(context.getText(R.string.test), TextView.BufferType.NORMAL);
            // time
            holder.textViewTime.setText(discussion.getTime());
            // like
            holder.textViewLikeCount.setText(discussion.getLike() + "");
        }
    }

    @Override
    public int getItemCount() {
        if(size == 0)
            return 1;
        else
            return size;
    }

    public class Holder extends RecyclerView.ViewHolder {
        String id;
        // profile
        ImageView imageViewProfile;
        TextView textViewProfile;
        // content
        ExpandableTextView expandableTextView;
        // info / reply
        ImageButton imageButtonReply;
        TextView textViewTime;
        // like
        ImageButton iamgeButtonLike;
        TextView textViewLikeCount;

        public Holder(View v) {
            super(v);
            if(size != 0) {
                // profile
                imageViewProfile = v.findViewById(R.id.image_view_profile);
                textViewProfile = v.findViewById(R.id.text_view_profile);
                // content
                expandableTextView = v.findViewById(R.id.expandable_text_view);
                expandableTextView.setTrimLength(5); // 5줄 이상 작성시 expandable 기능
                // info / reply
                imageButtonReply = v.findViewById(R.id.image_button_reply);
                textViewTime = v.findViewById(R.id.text_view_time);
                // like
                iamgeButtonLike = v.findViewById(R.id.iamge_button_like);
                textViewLikeCount = v.findViewById(R.id.text_view_like_count);
            }
        }
    }
}
