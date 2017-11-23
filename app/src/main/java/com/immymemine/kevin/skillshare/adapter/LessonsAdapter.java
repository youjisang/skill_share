package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.sampleModel.LessonsModel;


import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.Holder> {
    List<LessonsModel> lessonsData;
    Context context;

    public LessonsAdapter(List<LessonsModel> lessonsData, Context context) {
        this.lessonsData = lessonsData;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lessons_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lessonsData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView orderNumTextView, contentNameTextView, durationTextView;
        ImageView videoImageView;

        public Holder(View itemView) {
            super(itemView);
            orderNumTextView = itemView.findViewById(R.id.orderNumTextView);
            contentNameTextView = itemView.findViewById(R.id.contentNameTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            videoImageView = itemView.findViewById(R.id.videoImageView);

        }
    }
}
