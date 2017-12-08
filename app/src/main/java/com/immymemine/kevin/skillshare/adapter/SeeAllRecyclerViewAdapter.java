package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

/**
 * Created by quf93 on 2017-11-20.
 */

public class SeeAllRecyclerViewAdapter extends RecyclerView.Adapter<SeeAllRecyclerViewAdapter.SeeAllHolder> {

    Context context;
    int customViewType;
    public SeeAllRecyclerViewAdapter(Context context, int viewType) {
        this.context = context;
        this.customViewType = viewType;
    }

    @Override
    public SeeAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        int resId;

        switch (customViewType) {
            case ConstantUtil.NO_ITEM:
                resId = R.layout.recycler_view_item_no_discussions;
                break;
            case ConstantUtil.PROJECT_ITEM:
                resId = R.layout.recycler_view_item_see_all_projects;
                break;
            case ConstantUtil.REVIEW_ITEM:
                resId = R.layout.recycler_view_item_see_all_reviews;
                break;
            case ConstantUtil.SUBSCRIBER_ITEM:
                resId = R.layout.recycler_view_item_see_all_subscriber;
                break;
            // 메인화면 see all 추후 위치 수정 필요
            case ConstantUtil.CLASS_ITEM:
                resId = R.layout.recycler_view_item_see_all_classes;
                break;
            default:
                return null;
        }

        view = LayoutInflater.from(context).inflate(resId, parent, false);

        return new SeeAllHolder(view);
    }

    @Override
    public void onBindViewHolder(SeeAllHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SeeAllHolder extends RecyclerView.ViewHolder {
        String id;

        // profile
        ImageView imageViewProfile;
        ImageView imageViewProject;
        TextView textViewNickname;

        // like
        ImageButton imageButtonLike;
        ImageView imageViewLike;
        TextView textViewLikeCount;

        // title
        TextView textViewTitle;
        TextView textViewTitle2;

        // review
        TextView textViewReview;
        TextView textViewReviewAuthor;
        TextView textViewReviewJob;
        ImageView imageViewReviewProfile;

        // Main Class See All
        TextView textViewTitle5;
        TextView textViewTutor3;

        ImageView imageViewDuration;
        TextView textViewDuration;
        ImageView imageViewThumbUp;
        TextView textViewThumbUp;

        ImageView imageViewAttendanceStudents;
        TextView textViewAttendanceStudents;

        public SeeAllHolder(View v) {
            super(v);

            if (customViewType == ConstantUtil.PROJECT_ITEM) {
                // profile
                imageViewProject = v.findViewById(R.id.image_view_project1);
                // like
                imageButtonLike = v.findViewById(R.id.image_button_like);
                textViewLikeCount = v.findViewById(R.id.text_view_like_count);
                // title
                textViewTitle = v.findViewById(R.id.text_view_title);
                textViewTitle2 = v.findViewById(R.id.text_view_title2);
            }
            else if (customViewType == ConstantUtil.REVIEW_ITEM) {
                // review
                textViewReview = v.findViewById(R.id.text_view_review);
                imageViewReviewProfile = v.findViewById(R.id.image_view_review_profile);
                textViewReviewAuthor = v.findViewById(R.id.text_view_review_author);
                textViewReviewJob = v.findViewById(R.id.text_view_review_job);
                // like
                imageViewLike = v.findViewById(R.id.image_view_like);
            }
            else if (customViewType == ConstantUtil.SUBSCRIBER_ITEM) {
                // profile
                imageViewProfile = v.findViewById(R.id.image_view_profile);
                textViewNickname = v.findViewById(R.id.text_view_nickName);
            }
            // 메인화면 see all 추후 위치 수정 필요
            else if (customViewType == ConstantUtil.CLASS_ITEM) {
                // title
                textViewTitle5 = v.findViewById(R.id.text_view_title5);
                textViewTutor3 = v.findViewById(R.id.text_view_tutor3);
                // profile
                imageViewProfile = v.findViewById(R.id.image_view_profile);
                // information
                imageViewDuration = v.findViewById(R.id.image_view_duration);
                textViewDuration = v.findViewById(R.id.text_view_duration);
                imageViewThumbUp = v.findViewById(R.id.image_view_thumUp);
                textViewThumbUp = v.findViewById(R.id.text_view_thumbUp);
                // students
                imageViewAttendanceStudents = v.findViewById(R.id.image_view_attendanceStudents);
                textViewAttendanceStudents = v.findViewById(R.id.text_view_attendanceStudents);
            }
        }
    }
}