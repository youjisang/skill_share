package com.immymemine.kevin.skillshare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.ProjectActivity;
import com.immymemine.kevin.skillshare.activity.ReviewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private View view;
    private TextView projectStudentNum, seeAllTextView1, percentageNum, seeAllTextView2, reviewTextView, reviewAuthorTextView, classStudentNum, seeAllTextView3, relatedContentNameTextView1, AuthorTextView1, relatedContentNameTextView2, AuthorTextView2, relatedContentNameTextView3, AuthorTextView3;
    private ImageView projectImageView1, projectImageView2, projectImageView3, positiveImageView1, positiveImageView2, studentImage1, studentImage2, studentImage3, studentImage4, studentImage5, studentImage6, studentImage7, studentImage8, studentImage9, studentImage10, relatedImageView1, relatedImageView2, relatedImageView3;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_about, container, false);

//        initView();
//        clickListener();

        return view;


    }

//    private void initView() {
//        projectStudentNum = view.findViewById(R.id.student_count_project);
//        seeAllTextView1 = view.findViewById(R.id.seeAllTextView1);
//        percentageNum = view.findViewById(R.id.percentageNum);
//        seeAllTextView2 = view.findViewById(R.id.seeAllTextView2);
//        reviewTextView = view.findViewById(R.id.reviewTextView);
//        reviewAuthorTextView = view.findViewById(R.id.reviewAuthorTextView);
//        classStudentNum = view.findViewById(R.id.classStudentNum);
//        seeAllTextView3 = view.findViewById(R.id.seeAllTextView3);
//        relatedContentNameTextView1 = view.findViewById(R.id.relatedContentNameTextView1);
//        AuthorTextView1 = view.findViewById(R.id.AuthorTextView1);
//        relatedContentNameTextView2 = view.findViewById(R.id.relatedContentNameTextView2);
//        AuthorTextView2 = view.findViewById(R.id.AuthorTextView2);
//        relatedContentNameTextView3 = view.findViewById(R.id.relatedContentNameTextView3);
//        AuthorTextView3 = view.findViewById(R.id.AuthorTextView3);
//        projectImageView1 = view.findViewById(R.id.projectImageView1);
//        projectImageView2 = view.findViewById(R.id.projectImageView2);
//        projectImageView3 = view.findViewById(R.id.projectImageView3);
//        positiveImageView1 = view.findViewById(R.id.positiveImageView1);
//        positiveImageView2 = view.findViewById(R.id.positiveImageView2);
//        studentImage1 = view.findViewById(R.id.studentImage1);
//        studentImage2 = view.findViewById(R.id.studentImage2);
//        studentImage3 = view.findViewById(R.id.studentImage3);
//        studentImage4 = view.findViewById(R.id.studentImage4);
//        studentImage5 = view.findViewById(R.id.studentImage5);
//        studentImage6 = view.findViewById(R.id.studentImage6);
//        studentImage7 = view.findViewById(R.id.studentImage7);
//        studentImage8 = view.findViewById(R.id.studentImage8);
//        studentImage9 = view.findViewById(R.id.studentImage9);
//        studentImage10 = view.findViewById(R.id.studentImage10);
//        relatedImageView1 = view.findViewById(R.id.relatedImageView1);
//        relatedImageView2 = view.findViewById(R.id.relatedImageView2);
//        relatedImageView3 = view.findViewById(R.id.relatedImageView3);
//    }

    private void clickListener() {
        seeAllTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ProjectActivity.class);
                startActivity(intent1);
            }
        });
        seeAllTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), ReviewActivity.class);
                startActivity(intent2);

            }
        });
        seeAllTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
