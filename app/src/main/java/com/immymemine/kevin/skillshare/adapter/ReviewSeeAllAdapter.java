package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.sampleModel.ReviewSeeAllModel;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-24.
 */

public class ReviewSeeAllAdapter extends RecyclerView.Adapter<ReviewSeeAllAdapter.Holder>  {

    List<ReviewSeeAllModel> seeAllReviewData;
    Context context;

    public ReviewSeeAllAdapter(List<ReviewSeeAllModel> seeAllReviewData, Context context) {
        this.seeAllReviewData = seeAllReviewData;
        this.context = context;
    }


    @Override
    public ReviewSeeAllAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_reviews, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ReviewSeeAllAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView reviewContentsNameTextView,reviewAuthorTextView;
        ImageView reviewProfileImageView;
        public Holder(View itemView) {
            super(itemView);


            reviewContentsNameTextView = itemView.findViewById(R.id.reviewContentsNameTextView);
            reviewAuthorTextView = itemView.findViewById(R.id.reviewAuthorTextView);
            reviewProfileImageView = itemView.findViewById(R.id.reviewProfileImageView);


        }
    }
}
