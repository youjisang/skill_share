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
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

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

    public void updateData(List<Discussion> discussions) {
//        DiscussionDiffCallback callback = new DiscussionDiffCallback(this.discussions, discussions);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

//        this.discussions.clear();
//        this.discussions.addAll(discussions);
        this.discussions = discussions;
        notifyDataSetChanged();
//        result.dispatchUpdatesTo(this);
    }

    /* TODO 지상
        댓글이나 글을 작성할 수 있으므로, 최신화하는 처리 로직.
        calculateDiff?
     */

    @Override
    public int getItemViewType(int position) {
        if(discussions == null)
            return ConstantUtil.NO_ITEM;
        }
        else
            return super.getItemViewType(position);
    }
     /* TODO 지상
       seeAll부분 처리와는 다름.
       seeall은 클릭 이벤트가 일어났을시 인텐트로 콘스탄트유틸값을 전달해 처리했으나 이 부분은 size로 처리
       애초에 이슈가 다름. getItemViewType은 리싸이클러뷰만의 somethig?!
     */

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

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
          
            if(discussions != null) {
                // profile
                v.findViewById(R.id.frame_layout_profile).setOnClickListener(view -> {
                    // profile activity 이동
                });
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
