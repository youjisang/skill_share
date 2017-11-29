package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    ScrollView scrollView;
    // project
    TextView textViewStudentProjectNum, textViewProjectSeeAll;
    ImageView imageViewProject1, imageViewProject2, imageViewProject3;
    // review
    TextView textViewReviews, textViewReviewSeeAll, textViewReview, textViewReviewerName;
    ImageView imageViewThumb, imageViewReviewProfile;
    // student
    TextView textViewStudentNum, textViewStudentSeeAll;
    ImageView imageViewStudent1, imageViewStudent2, imageViewStudent3, imageViewStudent4, imageViewStudent5,
            imageViewStudent6, imageViewStudent7, imageViewStudent8, imageViewStudent9;
    // related class
    ImageView imageViewRelatedClass1, imageViewRelatedClass2, imageViewRelatedClass3;
    TextView textViewRelatedTitle1, textViewRelatedTutor1, textViewRelatedTitle2, textViewRelatedTutor2, textViewRelatedTitle3, textViewRelatedTutor3;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initiateView(view);
        return view;
    }

    private void initiateView(View view) {
        // scroll view
        scrollView = view.findViewById(R.id.scroll_view);
        // project
        textViewStudentProjectNum = view.findViewById(R.id.text_view_studentProjectNum);
        textViewProjectSeeAll = view.findViewById(R.id.text_view_project_see_all);
        imageViewProject1 = view.findViewById(R.id.image_view_project1);
        imageViewProject2 = view.findViewById(R.id.image_view_project2);
        imageViewProject3 = view.findViewById(R.id.image_view_project3);
        // review
        textViewReview = view.findViewById(R.id.text_view_review);
        textViewReviewSeeAll = view.findViewById(R.id.text_view_review_see_all);
        imageViewThumb = view.findViewById(R.id.image_view_thumb);
        imageViewReviewProfile = view.findViewById(R.id.image_view_review_profile);
        textViewReviews = view.findViewById(R.id.text_view_reviews);
        textViewReviewerName = view.findViewById(R.id.text_view_reviewer_name);
        // student
        textViewStudentNum = view.findViewById(R.id.text_view_student_num);
        textViewStudentSeeAll = view.findViewById(R.id.text_view_student_see_all);
        imageViewStudent1 = view.findViewById(R.id.image_view_student1);
        imageViewStudent2 = view.findViewById(R.id.image_view_student2);
        imageViewStudent3 = view.findViewById(R.id.image_view_student3);
        imageViewStudent4 = view.findViewById(R.id.image_view_student4);
        imageViewStudent5 = view.findViewById(R.id.image_view_student5);
        imageViewStudent6 = view.findViewById(R.id.image_view_student6);
        imageViewStudent7 = view.findViewById(R.id.image_view_student7);
        imageViewStudent8 = view.findViewById(R.id.image_view_student8);
        imageViewStudent9 = view.findViewById(R.id.image_view_student9);
        // related class
        imageViewRelatedClass1 = view.findViewById(R.id.image_view_related_class1);
        textViewRelatedTitle1 = view.findViewById(R.id.text_view_related_title1);
        textViewRelatedTutor1 = view.findViewById(R.id.text_view_related_tutor1);
        imageViewRelatedClass2 = view.findViewById(R.id.image_view_related_class2);
        textViewRelatedTitle2 = view.findViewById(R.id.text_view_related_title2);
        textViewRelatedTutor2 = view.findViewById(R.id.text_view_related_tutor2);
        imageViewRelatedClass3 = view.findViewById(R.id.image_view_related_class3);
        textViewRelatedTitle3 = view.findViewById(R.id.text_view_related_title3);
        textViewRelatedTutor3 = view.findViewById(R.id.text_view_related_tutor3);
    }
}
