package com.immymemine.kevin.skillshare.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.RelatedClassAdapter;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.SubscribersAdapter;
import com.immymemine.kevin.skillshare.model.m_class.About;
import com.immymemine.kevin.skillshare.model.m_class.Project;
import com.immymemine.kevin.skillshare.model.m_class.RelatedClass;
import com.immymemine.kevin.skillshare.model.m_class.Review;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    ScrollView scrollView;
    // project
    TextView textViewSubscriberCount, textViewProjectSeeAll;
    ImageView imageViewProject1, imageViewProject2, imageViewProject3;
    // review
    TextView textViewReviewPercent, textViewReviewSeeAll, textViewReview, textViewReviewerName;
    ImageView imageViewThumb, imageViewReviewProfile;
    // student
    TextView textViewSubscriberNum, textViewSubscriberSeeAll;
    RecyclerView recyclerViewSubscribers;
    // related class
    RecyclerView recyclerViewRelatedClass;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initiateView(view);

        RetrofitHelper.createApi(ClassService.class)
                .getAbout(getArguments().getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        return view;
    }

    private void handleResponse(About about) {
        // context
        Context context = getActivity();
        // project
        Project[] projects = about.getProjects();
        textViewSubscriberCount.setText(about.getProjectSubscriberCount());
        Glide.with(context).load(projects[0].getPictureUrl()).into(imageViewProject1);
        Glide.with(context).load(projects[1].getPictureUrl()).into(imageViewProject2);
        Glide.with(context).load(projects[2].getPictureUrl()).into(imageViewProject3);
        // review
        Review review = about.getReview();
        textViewReviewPercent.setText(review.getReviewPercent()+" Positive Reviews");
        textViewReview.setText(review.getContent());
        textViewReviewerName.setText(review.getReviewerName());
        Glide.with(context).load(review.getPictureUrl()).into(imageViewReviewProfile);
        // student
        Subscriber subscriber = about.getSubscriber();
        int count = Integer.parseInt(subscriber.getSubscriberNumber());
        if(count != 0) {
            textViewSubscriberNum.setVisibility(View.VISIBLE);
            textViewSubscriberNum.setText(subscriber.getSubscriberNumber() + " Subscribers");
        } else
            textViewSubscriberNum.setVisibility(View.GONE);

        recyclerViewSubscribers.setAdapter(new SubscribersAdapter(context, subscriber.getPictureUrl()));
        recyclerViewSubscribers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        // related class
        RelatedClass[] relatedClasses = about.getRelatedClasses();
        recyclerViewRelatedClass.setAdapter(new RelatedClassAdapter(context, relatedClasses));
        recyclerViewRelatedClass.setLayoutManager(new LinearLayoutManager(context));
    }

    private void handleError(Throwable error) {

    }

    private void initiateView(View view) {
        // scroll view
        scrollView = view.findViewById(R.id.scroll_view);
        // project
        textViewSubscriberCount = view.findViewById(R.id.text_view_subscriber_count);
        textViewProjectSeeAll = view.findViewById(R.id.text_view_project_see_all);
        imageViewProject1 = view.findViewById(R.id.image_view_project1);
        imageViewProject2 = view.findViewById(R.id.image_view_project2);
        imageViewProject3 = view.findViewById(R.id.image_view_project3);
        // review
        textViewReview = view.findViewById(R.id.text_view_review);
        textViewReviewSeeAll = view.findViewById(R.id.text_view_review_see_all);
        imageViewThumb = view.findViewById(R.id.image_view_thumb);
        imageViewReviewProfile = view.findViewById(R.id.image_view_review_profile);
        textViewReviewPercent = view.findViewById(R.id.text_view_review_percent);
        textViewReviewerName = view.findViewById(R.id.text_view_reviewer_name);
        // student
        textViewSubscriberNum = view.findViewById(R.id.text_view_subscriber_num);
        textViewSubscriberSeeAll = view.findViewById(R.id.text_view_subscriber_see_all);
        recyclerViewSubscribers = view.findViewById(R.id.recycler_view_subscribers);
        // related class
        recyclerViewRelatedClass = view.findViewById(R.id.recycler_view_related_class);
    }
}
