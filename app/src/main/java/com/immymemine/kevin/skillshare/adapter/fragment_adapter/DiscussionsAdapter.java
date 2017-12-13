package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.SeeAllActivity;
import com.immymemine.kevin.skillshare.model.m_class.Discussion;
import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.GCMService;
import com.immymemine.kevin.skillshare.network.gcm.SendMessageBody;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.TimeUtil;
import com.immymemine.kevin.skillshare.utility.diff_util.DiscussionDiffCallback;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        DiscussionDiffCallback callback = new DiscussionDiffCallback(this.discussions, discussions);
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
        View view;

        if (viewType == ConstantUtil.NO_ITEM)
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
            holder.id = discussion.get_id();
            holder.userId = discussion.getUserId();

            // profile
            holder.pictureUrl = discussion.getPictureUrl();
            Glide.with(context).load(holder.pictureUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageViewProfile);
            holder.textViewProfile.setText(discussion.getName());
            // content
            holder.expandableTextView.setText(discussion.getContent(), TextView.BufferType.NORMAL);
            // time
            holder.textViewTime.setText( TimeUtil.calculateTime(discussion.getTime()) );
            // like
            holder.textViewLikeCount.setText(discussion.getLikeCount());
            // reply
            if( discussion.getReplies() != null )
                holder.setReply(discussion.getReplies());
        }
    }

    /* TODO 지상
       expandableTextView 쓰는 방법.
     */
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
        String userId;

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

        String pictureUrl;

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
                imageButtonReply.setOnClickListener(view -> {

                });
                textViewTime = v.findViewById(R.id.text_view_time);
                // like
                imageButtonLike = v.findViewById(R.id.image_button_like);
                imageButtonLike.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked) {
                        textViewLikeCount.setText((Integer.parseInt(textViewLikeCount.getText().toString()) + 1) + "");
                        // if(login) >>> notification message else dialog 띄우기
                        RetrofitHelper.createApi(GCMService.class)
                                .sendMessage(new SendMessageBody("JUWON LEE", userId))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        (Response response) -> {
                                            Log.d("JUWON LEE", "result : " + response.getResult() + ", message : " + response.getMessage());
                                        }, (Throwable error) -> {
                                            Log.d("JUWON LEE", "error : " + error.getMessage());
                                        }
                                );
                    } else {
                        textViewLikeCount.setText((Integer.parseInt(textViewLikeCount.getText().toString()) - 1) + "");
                    }
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
            if(replies != null && replies.size() != 0) {
                textViewReplyProfile.setVisibility(View.VISIBLE);
                textViewTimeReply.setVisibility(View.VISIBLE);
                imageViewReplyProfile.setVisibility(View.VISIBLE);
                expandableTextViewReply.setVisibility(View.VISIBLE);

                int size = replies.size();
                if( size > 1 ) {
                    textViewReplies.setText("See all " + replies.size() + " replies");
                    textViewReplies.setVisibility(View.VISIBLE);
                    textViewReplies.setOnClickListener(view -> {
                        Intent intent = new Intent(context, SeeAllActivity.class);
                        intent.putExtra(ConstantUtil.SEE_ALL_FLAG, ConstantUtil.DISCUSSION_ITEM);
                        intent.putExtra(ConstantUtil.ID_FLAG, id);
                        intent.putExtra(ConstantUtil.TOOLBAR_TITLE_FLAG, replies.size() + " Replies");
                        replies.add(0, new Reply(
                                textViewProfile.getText().toString(),
                                pictureUrl,
                                expandableTextView.getText().toString(),
                                textViewTime.getText().toString()
                        ));
                        intent.putParcelableArrayListExtra("TEST", (ArrayList) replies);
                        context.startActivity(intent);
                    });
                }

                Reply reply = replies.get(size-1);
                textViewReplyProfile.setText(reply.getName());
                textViewTimeReply.setText( TimeUtil.calculateTime(reply.getTime()) );
                Glide.with(context).load(reply.getPictureUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewReplyProfile);
                expandableTextViewReply.setTrimLength(4);
                expandableTextViewReply.setText(reply.getContent(), TextView.BufferType.NORMAL);
            }
        }
    }
}
