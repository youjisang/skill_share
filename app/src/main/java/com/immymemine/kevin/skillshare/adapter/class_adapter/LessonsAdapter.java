package com.immymemine.kevin.skillshare.adapter.class_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.m_class.Video;
import com.immymemine.kevin.skillshare.utility.TimeUtil;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.Holder> {

    Context context;
    List<Video> videos;

    InteractionInterface interactionInterface;

    public LessonsAdapter(Context context) {
        this.context = context;
        if(context instanceof InteractionInterface)
            this.interactionInterface = (InteractionInterface) context;
    }

    public void update(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_lessons, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(videos != null) {
            Video video = videos.get(position);

            holder.textViewOrder.setText(video.getOrder()+".");
            Glide.with(context)
                    .load(video.getThumbnailUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.imageViewVideo);
            holder.textViewVideoTitle.setText(video.getTitle());
            holder.textViewDuration.setText(TimeUtil.calculateVideoTimeByColon(video.getDuration()));
            holder.id = video.get_id();
            holder.url = video.getUrl();
        }
    }

    @Override
    public int getItemCount() {
        if(videos == null)
            return 0;
        return videos.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        String id, url;

        TextView textViewOrder, textViewVideoTitle, textViewDuration;
        ImageView imageViewVideo;

        public Holder(View itemView) {
            super(itemView);
            textViewOrder = itemView.findViewById(R.id.text_view_order);
            imageViewVideo = itemView.findViewById(R.id.image_view_video);
            textViewVideoTitle = itemView.findViewById(R.id.text_view_video_title);
            textViewDuration = itemView.findViewById(R.id.text_view_duration);
            // 클릭시 다운로드
            itemView.findViewById(R.id.image_view_download).setOnClickListener(view -> {
             /* TODO 지상
                해당아이템 로컬로 다운로드
             */
            });

            // 클릭시 focus 이동
            itemView.setOnClickListener(view -> {
                interactionInterface.play(url);
            });
        }
    }

    public interface InteractionInterface {
        void play(String url);
    }
}
