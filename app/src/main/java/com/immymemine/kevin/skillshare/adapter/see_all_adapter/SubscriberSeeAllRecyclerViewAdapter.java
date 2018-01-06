package com.immymemine.kevin.skillshare.adapter.see_all_adapter;

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
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;

import java.util.List;

/**
 * Created by quf93 on 2017-12-09.
 */

public class SubscriberSeeAllRecyclerViewAdapter extends RecyclerView.Adapter<SubscriberSeeAllRecyclerViewAdapter.Holder> {

    Context context;
    List<Subscriber> subscribers;
    public SubscriberSeeAllRecyclerViewAdapter(Context context, List<Subscriber> subscribers) {
        this.context = context;
        this.subscribers = subscribers;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_see_all_subscriber, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Subscriber subscriber = subscribers.get(position);

        holder.userId = subscriber.get_id();
        holder.textViewName.setText(subscriber.getName());
        Glide.with(context).load(subscriber.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageViewProfile);
    }

    @Override
    public int getItemCount() {
        return subscribers.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        String userId;

        ImageView imageViewProfile;
        TextView textViewName;

        public Holder(View view) {
            super(view);
            imageViewProfile = view.findViewById(R.id.image_view_thumbnail);
            textViewName = view.findViewById(R.id.text_view_name);
        }
    }
}
