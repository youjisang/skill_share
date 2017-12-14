package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.m_class.Discussion;
import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionsAdapter extends RecyclerView.Adapter<DiscussionsAdapter.Holder> {

    Context context;
    List<Discussion> discussions;

    public DiscussionsAdapter(Context context) {
        this.context = context;
    }

    public void initiateData(List<Discussion> discussions) {
        this.discussions = discussions;
        notifyDataSetChanged();
    }

    // TODO DiffUtil 개선
    public void updateData(List<Discussion> discussions) {
//        DiscussionDiffCallback callback = new DiscussionDiffCallback(this.discussions, discussions);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

//        this.discussions.clear();
//        this.discussions.addAll(discussions);
        this.discussions = discussions;
        notifyDataSetChanged();
//        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        if(discussions == null)
            return ConstantUtil.NO_ITEM;

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
        if(discussions != null) {
            Discussion discussion = discussions.get(position);
            // identifier
            if(discussion.get_id() != null)
                holder.id = discussion.get_id();
            // profile
            Glide.with(context).load(discussion.getPictureUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageViewProfile);
            holder.textViewProfile.setText(discussion.getName());
            // content
            holder.expandableTextView.setText(discussion.getContent(), TextView.BufferType.NORMAL);
            // time
            holder.textViewTime.setText(discussion.getTime());
            // like
            holder.textViewLikeCount.setText(discussion.getLike() + "");
            // reply
            if( discussion.getReplies() != null )
                holder.setReply(discussion.getReplies());
        }
    }

    @Override
    public int getItemCount() {
        if(discussions==null)
            return 1;
        else
            return discussions.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        // id
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
        ToggleImageButton imageButtonLike;
        TextView textViewLikeCount;

        public Holder(View v) {
            super(v);
            if(discussions != null) {
                // profile
                imageViewProfile = v.findViewById(R.id.image_view_profile);
                imageViewProfile.setOnClickListener(view -> {
                    // profile activity 이동
                });
                textViewProfile = v.findViewById(R.id.text_view_profile);
                textViewProfile.setOnClickListener(view -> {
                    // profile activity 이동
                });
                // content
                expandableTextView = v.findViewById(R.id.expandable_text_view);
                expandableTextView.setTrimLength(8);
                // info / reply
                imageButtonReply = v.findViewById(R.id.image_button_reply);
                textViewTime = v.findViewById(R.id.text_view_time);
                // like
                imageButtonLike = v.findViewById(R.id.image_button_like);
                imageButtonLike.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked)
                        textViewLikeCount.setText( (Integer.parseInt(textViewLikeCount.getText().toString()) + 1) + "");
                    else
                        textViewLikeCount.setText( (Integer.parseInt(textViewLikeCount.getText().toString()) - 1) + "");
                });
                textViewLikeCount = v.findViewById(R.id.text_view_like_count);
                // reply
                textViewReplies = v.findViewById(R.id.text_view_replies);
                imageViewReplyProfile = v.findViewById(R.id.image_view_reply_profile);
                textViewReplyProfile = v.findViewById(R.id.text_view_reply_profile);
                expandableTextViewReply = v.findViewById(R.id.expandable_text_view_reply);
                textViewTimeReply = v.findViewById(R.id.text_view_time_reply);
            }
        }

        TextView textViewReplies, textViewReplyProfile, textViewTimeReply;
        ImageView imageViewReplyProfile;
        ExpandableTextView expandableTextViewReply;

        public void setReply(List<Reply> replies) {
            if(replies == null || replies.size() == 0) {
                // nothing to do
            } else {
                textViewReplyProfile.setVisibility(View.VISIBLE);
                textViewTimeReply.setVisibility(View.VISIBLE);
                imageViewReplyProfile.setVisibility(View.VISIBLE);
                expandableTextViewReply.setVisibility(View.VISIBLE);

                int size = replies.size();
                if( size > 1 ) {
                    textViewReplies.setText("See all " + replies.size() + " replies");
                    textViewReplies.setVisibility(View.VISIBLE);
                    textViewReplies.setOnClickListener(view -> {
                        // replies activity 로 이동
                    });
                }

                Reply reply = replies.get(size-1);
                textViewReplyProfile.setText(reply.getName());
                textViewTimeReply.setText(reply.getTime());
                Glide.with(context).load(reply.getPictureUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewReplyProfile);
                expandableTextViewReply.setTrimLength(4);
                expandableTextViewReply.setText(reply.getContent(), TextView.BufferType.NORMAL);
            }
        }
    }
}