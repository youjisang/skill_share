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

public class ReviewSeeAllRecyclerViewAdapter extends RecyclerView.Adapter<ReviewSeeAllRecyclerViewAdapter.Holder>  {

    List<ReviewSeeAllModel> seeAllReviewData;
    Context context;

    public ReviewSeeAllRecyclerViewAdapter(List<ReviewSeeAllModel> seeAllReviewData, Context context) {
        this.seeAllReviewData = seeAllReviewData;
        this.context = context;
    }


    @Override
    public ReviewSeeAllRecyclerViewAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_reviews, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ReviewSeeAllRecyclerViewAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView text_view_reviewContentsName,text_view_reviewAuthor;
        ImageView image_view_reviewProfile;
        public Holder(View itemView) {
            super(itemView);


            text_view_reviewContentsName = itemView.findViewById(R.id.text_view_reviewContentsName);
            text_view_reviewAuthor = itemView.findViewById(R.id.text_view_reviewAuthor);
            image_view_reviewProfile = itemView.findViewById(R.id.image_view_reviewProfile);


        }
    }
}
