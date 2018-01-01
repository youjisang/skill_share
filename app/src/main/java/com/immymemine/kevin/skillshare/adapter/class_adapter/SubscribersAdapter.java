package com.immymemine.kevin.skillshare.adapter.class_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

/**
 * Created by quf93 on 2017-12-05.
 */

public class SubscribersAdapter extends RecyclerView.Adapter<SubscribersAdapter.SubscriberHolder> {

    Context context;
    List<Subscriber> subscribers;
    public SubscribersAdapter(Context context) {
        this.context = context;
    }

    public void update(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(subscribers == null || subscribers.size()==0)
            return ConstantUtil.NO_ITEM;

        return super.getItemViewType(position);
    }

    @Override
    public SubscriberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == ConstantUtil.NO_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_subscriber, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_subscribers, parent, false);
        }

        return new SubscriberHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscriberHolder holder, int position) {
        if(subscribers != null) {
            Subscriber subscriber = subscribers.get(subscribers.size()-1-position);

            Glide.with(context).load(subscriber.getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageViewSubscriber);

            holder.userId = subscriber.get_id();
        }
    }

    @Override
    public int getItemCount() {
        if(subscribers == null || subscribers.size() == 0)
            return 1;
        return subscribers.size();
    }

    class SubscriberHolder extends RecyclerView.ViewHolder {
        String userId;
        ImageView imageViewSubscriber;

        public SubscriberHolder(View view) {
            super(view);
            if(subscribers !=null && subscribers.size() != 0) {
                imageViewSubscriber = view.findViewById(R.id.image_view_subscriber);
                imageViewSubscriber.setOnClickListener( v -> {
                    // profile 이동
                });
            }
        }
    }
}
