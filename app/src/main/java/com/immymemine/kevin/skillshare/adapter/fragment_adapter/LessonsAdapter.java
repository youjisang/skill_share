package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.Holder> {

    Context context;
    public LessonsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_lessons, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView text_view_orderNum, text_view_contentName, text_view_duration;
        ImageView image_view_video;

        public Holder(View itemView) {
            super(itemView);
            text_view_orderNum = itemView.findViewById(R.id.text_view_orderNum);
            text_view_contentName = itemView.findViewById(R.id.text_view_contentName);
            text_view_duration = itemView.findViewById(R.id.text_view_duration);
            image_view_video = itemView.findViewById(R.id.image_view_video);

        }
    }
}
