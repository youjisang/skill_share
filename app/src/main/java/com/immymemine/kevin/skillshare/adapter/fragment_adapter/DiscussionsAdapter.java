package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.sampleModel.DiscussionModel;


import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionsAdapter extends RecyclerView.Adapter<DiscussionsAdapter.Holder> {
    List<DiscussionModel> discussionsData;
    Context context;

    public DiscussionsAdapter(List<DiscussionModel> discussionsData, Context context) {
        this.discussionsData = discussionsData;
        this.context = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussions_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        // 일단 생략
    }

    @Override
    public int getItemCount() {

        return discussionsData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView profileImageView, replyImageView, heartImageView;
        TextView userNameTextView, contentTextView, dateTextView, likeNumTextView;

        public Holder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            heartImageView = itemView.findViewById(R.id.heartImageView);
            replyImageView = itemView.findViewById(R.id.replyImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            likeNumTextView = itemView.findViewById(R.id.likeNumTextView);

        }
    }
}
