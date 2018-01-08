package com.immymemine.kevin.skillshare.adapter.main_adapter;

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
import com.immymemine.kevin.skillshare.model.discover.FeaturedClass;
import com.immymemine.kevin.skillshare.utility.TimeUtil;

import java.util.List;

/**
 * Created by quf93 on 2017-11-27.
 */

public class DiscoverRecyclerViewAdapter extends RecyclerView.Adapter<DiscoverRecyclerViewAdapter.MainHolder> {

    Context context;
    List<FeaturedClass> featuredClasses;

    public DiscoverRecyclerViewAdapter(Context context, List<FeaturedClass> featuredClasses) {
        this.context = context;
        this.featuredClasses = featuredClasses;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_discover, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        FeaturedClass featuredClass = featuredClasses.get(position);

        holder.classId = featuredClass.get_id();

        holder.textViewTime.setText(TimeUtil.calculateVideoTime(featuredClass.getDuration()));
        holder.textViewTitle.setText(featuredClass.getTitle());
        holder.textViewTutorName.setText(featuredClass.getTutorName());

        Glide.with(context).load(featuredClass.getImageUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageViewFeaturedClass);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MainHolder extends RecyclerView.ViewHolder {

        String classId;

        ImageView imageViewFeaturedClass;
        TextView textViewTime, textViewTitle, textViewTutorName;

        public MainHolder(View view) {
            super(view);
            imageViewFeaturedClass = view.findViewById(R.id.image_view_group);
            textViewTime = view.findViewById(R.id.text_view_time);
            textViewTitle = view.findViewById(R.id.text_view_title);
            textViewTutorName = view.findViewById(R.id.text_view_user_name);

            view.setOnClickListener(v -> {
                // Class Activity
            });
        }
    }
}
