package com.immymemine.kevin.skillshare.adapter.main_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.ClassActivity;
import com.immymemine.kevin.skillshare.model.discover.SearchClass;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

/**
 * Created by JisangYou on 2017-12-11.
 */

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<SearchClass> searchClasses;

    public SearchRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SearchClass> searchClasses) {
        this.searchClasses = searchClasses;
        notifyDataSetChanged();
    }



    @Override
    public int getItemViewType(int position) {
        if(searchClasses == null || searchClasses.size() == 0)
            return ConstantUtil.FAIL_SEARCH;
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ConstantUtil.FAIL_SEARCH) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_search, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_see_all_classes, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (searchClasses != null && searchClasses.size() != 0) {
            SearchClass searchClass = searchClasses.get(position);
            holder.classId = searchClass.get_id();
            // image
            Glide.with(context).load(searchClass.getImageUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.imageViewThumbnail);
            // text
            holder.textViewTitle.setText(searchClass.getTitle());
            holder.textViewTutorName.setText(searchClass.getTutorName());
            holder.textViewDuration.setText(searchClass.getTotalDuration());
            holder.textViewSubscriberCount.setText(searchClass.getSubscriberCount());
            holder.textViewReviewPercent.setText(searchClass.getReviewPercent() + "%");
        }

    }

    @Override
    public int getItemCount() {
        if (searchClasses == null || searchClasses.size() == 0)
            return 1;

        return searchClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        String classId;

        TextView textViewTitle, textViewTutorName, textViewDuration,
                textViewReviewPercent, textViewSubscriberCount;
        ImageView imageViewThumbnail;

        public ViewHolder(View v) {
            super(v);

            if (searchClasses != null) {
                textViewTitle = v.findViewById(R.id.text_view_title);
                textViewTutorName = v.findViewById(R.id.text_view_tutor_name);
                imageViewThumbnail = v.findViewById(R.id.image_view_profile);

                textViewDuration = v.findViewById(R.id.text_view_duration);
                textViewReviewPercent = v.findViewById(R.id.text_view_review_percent);
                textViewSubscriberCount = v.findViewById(R.id.text_view_subscriber_count);

                v.setOnClickListener( view -> {
                    Intent intent = new Intent(context, ClassActivity.class);
                    intent.putExtra(ConstantUtil.ID_FLAG, classId);
                    context.startActivity(intent);
                });
            }
        }
    }
}
