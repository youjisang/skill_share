package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.model_class.About;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

/**
 * Created by super on 2017-12-05.
 */
public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<About> about;
    int customViewType;


    public AboutAdapter(Context context, int viewType) {
        Log.i("Test", context + "   " + viewType);
        this.context = context;
        this.customViewType = viewType;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        int resId;

        switch (customViewType) {
            case ConstantUtil.NO_ITEM:
                Log.i("Test", "Item");
                resId = R.layout.recycler_view_item_no_discussions;
                break;
            case ConstantUtil.PROJECT_ITEM:
                Log.i("Test", "project");

                resId = R.layout.recycler_view_item_see_all_projects;
                break;
            case ConstantUtil.REVIEW_ITEM:
                Log.i("Test", "review");
                resId = R.layout.recycler_view_item_see_all_reviews;
                break;
            case ConstantUtil.STUDENT_ITEM:
                Log.i("Test", "student");
                resId = R.layout.recycler_view_item_see_all_students;
                break;
            // 메인화면 see all 추후 위치 수정 필요
            case ConstantUtil.CLASS_ITEM:
                Log.i("Test", "class");
                resId = R.layout.recycler_view_item_see_all_classes;
                break;
            default:
                Log.i("Test", "default");
                return null;
        }
        view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 데이터 표시
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Holder extends RecyclerView.ViewHolder {
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
        ImageView imageViewThumbUp ;
        TextView textViewThumbUp;

        ImageView imageViewAttendanceStudents;
        TextView textViewAttendanceStudents;

        public Holder(View v) {
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
            else if (customViewType == ConstantUtil.STUDENT_ITEM) {
                // profile
                imageViewProfile = v.findViewById(R.id.image_view_profile);
                textViewNickname = v.findViewById(R.id.text_view_nickName);
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
            // 메인화면 see all 추후 위치 수정 필요
            else if (customViewType == ConstantUtil.CLASS_ITEM) {
                // title
                textViewTitle5 = v.findViewById(R.id.text_view_title5);
                textViewTutor3 = v.findViewById(R.id.text_view_tutor3);
                // profile
                imageViewProfile = v.findViewById(R.id.image_view_profile);
                // imformation
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