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
import com.immymemine.kevin.skillshare.model.m_class.Review;

import java.util.List;

/**
 * Created by quf93 on 2017-11-20.
 */

public class ReviewSeeAllRecyclerViewAdapter extends RecyclerView.Adapter<ReviewSeeAllRecyclerViewAdapter.Holder> {

    Context context;

    List<Review> reviews;

    public ReviewSeeAllRecyclerViewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_see_all_reviews, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Review review = reviews.get(position);

//        if("FALSE".equals(review.getBool())) {
//            // 이미지 바꿔주기
//        }

        holder.textViewReview.setText(review.getContent());
        holder.textViewReviewerName.setText(review.getReviewerName());
        Glide.with(context).load(review.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageViewReviewProfile);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        // review <<< id 값이 필요하지 않다
        TextView textViewReview, textViewReviewerName;
        ImageView imageViewReviewProfile, imageViewThumb;

        public Holder(View view) {
            super(view);
            // review
            textViewReview = view.findViewById(R.id.text_view_review);
            imageViewReviewProfile = view.findViewById(R.id.image_view_review_profile);
            textViewReviewerName = view.findViewById(R.id.text_view_reviewer_name);
            // like
            imageViewThumb = view.findViewById(R.id.image_view_thumb);
        }
    }
}