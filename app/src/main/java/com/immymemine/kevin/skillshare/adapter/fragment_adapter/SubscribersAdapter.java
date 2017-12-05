package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

/**
 * Created by quf93 on 2017-12-05.
 */

public class SubscribersAdapter extends RecyclerView.Adapter<SubscribersAdapter.SubscriberHolder> {

    Context context;
    String[] pictureUrl;

    int count;
    public SubscribersAdapter(Context context, String[] pictureUrl) {
        this.context = context;
        this.pictureUrl = pictureUrl;
    }

    @Override
    public int getItemViewType(int position) {
        count = pictureUrl.length;
        if(count == 0)
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
        if(count != 0)
            Glide.with(context).load(pictureUrl[position]).into(holder.imageViewSubscriber);
    }

    @Override
    public int getItemCount() {
        if(count == 0)
            return 1;
        return 10;
    }

    class SubscriberHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSubscriber;

        public SubscriberHolder(View view) {
            super(view);
            if(count != 0)
                imageViewSubscriber = view.findViewById(R.id.image_view_subscriber);
        }
    }
}
