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
import com.immymemine.kevin.skillshare.model.m_class.Review;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

/**
 * Created by quf93 on 2017-12-16.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

    Context context;
    Review review;
    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public void updateData(Review review) {
        this.review = review;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if ( review == null ) {
            return ConstantUtil.NO_ITEM;
        }
        return super.getItemViewType(position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if ( viewType == ConstantUtil.NO_ITEM ) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_review, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_review, parent, false);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if( review != null ) {
            holder.textViewReview.setText(review.getContent());
            Glide.with(context).load(review.getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageViewReviewProfile);
            holder.textViewReviewerName.setText(review.getReviewerName());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageViewThumb, imageViewReviewProfile;
        TextView textViewReview, textViewReviewerName;

        public Holder(View v) {
            super(v);
                imageViewThumb = v.findViewById(R.id.image_view_thumb);
                imageViewReviewProfile = v.findViewById(R.id.image_view_review_profile);
                textViewReview = v.findViewById(R.id.text_view_review);
                textViewReviewerName = v.findViewById(R.id.text_view_reviewer_name);
        }
    }
}
