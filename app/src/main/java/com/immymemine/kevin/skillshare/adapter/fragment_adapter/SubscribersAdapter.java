package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.m_class.Subscribers;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

/**
 * Created by quf93 on 2017-12-05.
 */

public class SubscribersAdapter extends RecyclerView.Adapter<SubscribersAdapter.SubscriberHolder> {

    Context context;
    String[] pictureUrls, ids;

    int count;

    public SubscribersAdapter(Context context) {
        this.context = context;
        count = 0;
    }

    public void update(Subscribers subscriber, int count) {
        this.pictureUrls = subscriber.getPictureUrls();
        this.ids = subscriber.getIds();

        this.count = count;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
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
        if(count != 0) {
            Glide.with(context).load(Uri.parse(pictureUrls[position]))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageViewSubscriber);

            holder.id = ids[position];
        }
    }

    @Override
    public int getItemCount() {
        if(count == 0)
            return 1;
        return 10;
    }

    class SubscriberHolder extends RecyclerView.ViewHolder {
        String id;
        ImageView imageViewSubscriber;

        public SubscriberHolder(View view) {
            super(view);
            if(count != 0) {
                imageViewSubscriber = view.findViewById(R.id.image_view_subscriber);
                imageViewSubscriber.setOnClickListener( v -> {
                    // profile 이동
                });
            }
        }
    }
}
