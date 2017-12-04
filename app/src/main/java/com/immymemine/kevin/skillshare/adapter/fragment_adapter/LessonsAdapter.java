package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.model_class.Video;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.Holder> {

    Context context;
    List<Video> videos;
    public LessonsAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_lessons, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textViewOrder, textViewVideoTitle, textViewDuration;
        ImageView imageViewVideo, imageViewDownload;

        public Holder(View itemView) {
            super(itemView);
            textViewOrder = itemView.findViewById(R.id.text_view_order);
            imageViewVideo = itemView.findViewById(R.id.image_view_video);
            textViewVideoTitle = itemView.findViewById(R.id.text_view_video_title);
            textViewDuration = itemView.findViewById(R.id.text_view_duration);
            imageViewDownload = itemView.findViewById(R.id.image_view_download);
        }
    }
}
