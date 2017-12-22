package com.immymemine.kevin.skillshare.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.SeeAllActivity;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.ProjectAdapter;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.RelatedClassAdapter;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.ReviewAdapter;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.SubscribersAdapter;
import com.immymemine.kevin.skillshare.model.m_class.About;
import com.immymemine.kevin.skillshare.model.m_class.Project;
import com.immymemine.kevin.skillshare.model.m_class.RelatedClass;
import com.immymemine.kevin.skillshare.model.m_class.Review;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    ScrollView scrollView;
    // project
    TextView textViewProjectCount, textViewProjectSeeAll;
    RecyclerView recyclerViewProject;
    // review
    TextView textViewReviewPercent, textViewReviewSeeAll;
    RecyclerView recyclerViewReview;
    // student
    TextView textViewSubscriberNum, textViewSubscriberSeeAll;
    RecyclerView recyclerViewSubscribers;
    // related class
    RecyclerView recyclerViewRelatedClass;
    // adapter
    ProjectAdapter projectAdapter;
    SubscribersAdapter subscribersAdapter;
    RelatedClassAdapter relatedClassAdapter;
    ReviewAdapter reviewAdapter;
    // about ( test data )
    About about;

    Context context;
    // class id
    String classId;
    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initiateView(view);
        setSeeAllOnClickListener();
        classId = getArguments().getString(ConstantUtil.ID_FLAG);
        // empty constructor >>> recycler view 초기화
        context = getActivity();

        projectAdapter = new ProjectAdapter(context);
        recyclerViewProject.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProject.setAdapter(projectAdapter);

        reviewAdapter = new ReviewAdapter(context);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewReview.setAdapter(reviewAdapter);

        subscribersAdapter = new SubscribersAdapter(context);
        recyclerViewSubscribers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSubscribers.setAdapter(subscribersAdapter);

        relatedClassAdapter = new RelatedClassAdapter(context);
        recyclerViewRelatedClass.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewRelatedClass.setAdapter(relatedClassAdapter);

        RetrofitHelper.createApi(ClassService.class)
                .getAbout(classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);
//        // test data ==========================================
//        about = new About();
//        // project
//        List<Project> projects = new ArrayList<>();
//        projects.add(new Project("id","http://files.idg.co.kr/ciokr/image/imce/u132754/project_management-100536264-primary_idge.jpg"));
//        projects.add(new Project("id","https://www.sktinsight.com/wp-content/uploads/2017/08/4%EC%B0%A8%EC%82%B0%EC%97%85%ED%98%81%EB%AA%85_%EC%9D%BC%EC%9E%90%EB%A6%AC_%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EA%B8%B0%EC%97%85_3.jpg"));
//        projects.add(new Project("id","http://www.samsungengineering.co.kr/upload/MP/thumb_201507100237261334.jpg"));
//        projects.add(new Project("id","https://www.sktinsight.com/wp-content/uploads/2017/08/4%EC%B0%A8%EC%82%B0%EC%97%85%ED%98%81%EB%AA%85_%EC%9D%BC%EC%9E%90%EB%A6%AC_%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EA%B8%B0%EC%97%85_3.jpg"));
//
//        about.setProjects(projects);
//
//        // review
//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review("like", "Great way to get organized in Design Systems, and give a lot of consistency through Web and Mobile apps", "id1", "Conekta Design", "http://mblogthumb2.phinf.naver.net/MjAxNjEyMDhfNCAg/MDAxNDgxMTMwNjE1Nzk3.LgjHSitMLnOucYcE7uINtAVVxS1DK3zTFAajRXx9guIg.Y2bK-n1zsPTgBvLDspWS4TmMkRG7dXPDl9QcQxHyfQMg.JPEG.hy_chu/%EB%8F%84%EA%B9%A8%EB%B9%84_%EA%B9%80%EA%B3%A0%EC%9D%80_1.jpg?type=w800"));
//        reviews.add(new Review("like", "Great way to get organized in Design Systems, and give a lot of consistency through Web and Mobile apps", "id2",  "Conekta Design", "http://mblogthumb2.phinf.naver.net/MjAxNjEyMDhfNCAg/MDAxNDgxMTMwNjE1Nzk3.LgjHSitMLnOucYcE7uINtAVVxS1DK3zTFAajRXx9guIg.Y2bK-n1zsPTgBvLDspWS4TmMkRG7dXPDl9QcQxHyfQMg.JPEG.hy_chu/%EB%8F%84%EA%B9%A8%EB%B9%84_%EA%B9%80%EA%B3%A0%EC%9D%80_1.jpg?type=w800"));
//        reviews.add(new Review("dislike", "Great way to get organized in Design Systems, and give a lot of consistency through Web and Mobile apps", "id3",  "Conekta Design", "http://mblogthumb2.phinf.naver.net/MjAxNjEyMDhfNCAg/MDAxNDgxMTMwNjE1Nzk3.LgjHSitMLnOucYcE7uINtAVVxS1DK3zTFAajRXx9guIg.Y2bK-n1zsPTgBvLDspWS4TmMkRG7dXPDl9QcQxHyfQMg.JPEG.hy_chu/%EB%8F%84%EA%B9%A8%EB%B9%84_%EA%B9%80%EA%B3%A0%EC%9D%80_1.jpg?type=w800"));
//        reviews.add(new Review("like", "Great way to get organized in Design Systems, and give a lot of consistency through Web and Mobile apps", "id4",  "Conekta Design", "http://mblogthumb2.phinf.naver.net/MjAxNjEyMDhfNCAg/MDAxNDgxMTMwNjE1Nzk3.LgjHSitMLnOucYcE7uINtAVVxS1DK3zTFAajRXx9guIg.Y2bK-n1zsPTgBvLDspWS4TmMkRG7dXPDl9QcQxHyfQMg.JPEG.hy_chu/%EB%8F%84%EA%B9%A8%EB%B9%84_%EA%B9%80%EA%B3%A0%EC%9D%80_1.jpg?type=w800"));
//        reviews.add(new Review("like", "Great way to get organized in Design Systems, and give a lot of consistency through Web and Mobile apps", "id5",  "Conekta Design", "http://mblogthumb2.phinf.naver.net/MjAxNjEyMDhfNCAg/MDAxNDgxMTMwNjE1Nzk3.LgjHSitMLnOucYcE7uINtAVVxS1DK3zTFAajRXx9guIg.Y2bK-n1zsPTgBvLDspWS4TmMkRG7dXPDl9QcQxHyfQMg.JPEG.hy_chu/%EB%8F%84%EA%B9%A8%EB%B9%84_%EA%B9%80%EA%B3%A0%EC%9D%80_1.jpg?type=w800"));
//        about.setReviews(reviews);
//
//        // subscriber
//        String[] pictureUrls = {
//                "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH92438362_w300_h300.jpg",
//                "https://i.ytimg.com/vi/eqEcRwmV2vU/maxresdefault.jpg",
//                "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH82423479_w300_h300.jpg",
//                "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH92438362_w300_h300.jpg",
//                "http://cfile2.uf.tistory.com/image/1141C73D4DB609D82E1B99",
//                "http://img2.sbs.co.kr/img/sbs_cms/WE/2015/12/04/WE31504965_w300.jpg",
//                "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH92438362_w300_h300.jpg",
//                "http://blogimg.ohmynews.com/attach/26495/1372921881.jpg",
//                "http://img2.sbs.co.kr/img/sbs_cms/CH/2016/06/06/CH92438362_w300_h300.jpg",
//                "http://cfile23.uf.tistory.com/image/9907AF3359C0C1153C71D2"
//        };
//        List<Subscriber> subscribers = new ArrayList<>();
//        subscribers.add(new Subscriber("test id 0", "James", pictureUrls[0]));
//        subscribers.add(new Subscriber("test id 1", "ChicChoc", pictureUrls[1]));
//        subscribers.add(new Subscriber("test id 2", "Butter Waffle", pictureUrls[2]));
//        subscribers.add(new Subscriber("test id 3", "Wing Study", pictureUrls[3]));
//        subscribers.add(new Subscriber("test id 4", "Computer", pictureUrls[4]));
//        subscribers.add(new Subscriber("test id 5", "Apple", pictureUrls[5]));
//        subscribers.add(new Subscriber("test id 6", "Samsung", pictureUrls[6]));
//        subscribers.add(new Subscriber("test id 7", "Key Board", pictureUrls[7]));
//        subscribers.add(new Subscriber("test id 8", "Speaker", pictureUrls[8]));
//        subscribers.add(new Subscriber("test id 9", "Water bottle", pictureUrls[9]));
//        about.setSubscribers(subscribers);
//        // related class
//        List<RelatedClass> relatedClasses = new ArrayList<>();
//        relatedClasses.add(new RelatedClass("id_1","https://cdn-images-1.medium.com/max/2000/1*7pjzaWKedACc3-olWUghLg.png","iOS Design I: Getting Started with UX", "Kara Hodecker"));
//        relatedClasses.add(new RelatedClass("id_2","https://learn.canva.com/wp-content/uploads/2015/10/40-People-Through-History-Who-Changed-Design-For-Good-04.png","Getting Started with Sketch: Design a Beautiful App", "Christian Krammer"));
//        relatedClasses.add(new RelatedClass("id_3","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIeQXRYXiQyOD3f_Kbw3lvlvo92XMcMImEJrqcwKq1JliJQkfj","Mobile App Prototyping", "Noah Levin"));
//        about.setRelatedClasses(relatedClasses);
//
//        handleResponse(about);
//        // test ==========================================

        return view;

    }

    private void handleResponse(About about) {
        // project
        List<Project> projects = about.getProjects();
        int projectsSize = projects.size();
        if(projectsSize == 0) {
            textViewProjectCount.setText("Subscriber Project");
            textViewProjectSeeAll.setVisibility(View.GONE);
        } else
            textViewProjectCount.setText(projectsSize + " Subscriber Projects");

        projectAdapter.update(projects); // <<< data 전달 & notifyDataSetChanged

        // review
        List<Review> reviews = about.getReviews();
        int size = reviews.size();
        if(size != 0) {
            int likeCount = 0;
            for(Review review : reviews) {
                if( "like".equals(review.getLikeOrNot()) )
                    likeCount++;
            }
            textViewReviewPercent.setText((likeCount * 100 / size)+"% Positive Reviews");
            reviewAdapter.updateData(reviews.get(size-1));
        } else {
            textViewReviewPercent.setText("No Reviews");
            textViewReviewSeeAll.setVisibility(View.GONE);
        }

        // subscriber
        List<Subscriber> subscribers = about.getSubscribers();
        int count = subscribers.size();
        if(count != 0) {
            textViewSubscriberNum.setVisibility(View.VISIBLE);
            textViewSubscriberNum.setText(count + " Subscribers");
        } else
            textViewSubscriberNum.setVisibility(View.GONE);
        subscribersAdapter.update(subscribers);

        // related class
        List<RelatedClass> relatedClasses = about.getRelatedClasses();
        relatedClassAdapter.update(relatedClasses);
    }

    private void handleError(Throwable error) {

    }

    private void initiateView(View view) {
        // scroll view
        scrollView = view.findViewById(R.id.scroll_view);
        // project
        textViewProjectCount = view.findViewById(R.id.text_view_project_count);
        textViewProjectSeeAll = view.findViewById(R.id.text_view_project_see_all);
        recyclerViewProject = view.findViewById(R.id.recycler_view_project);
        // review
        textViewReviewPercent = view.findViewById(R.id.text_view_review_percent);
        textViewReviewSeeAll = view.findViewById(R.id.text_view_review_see_all);
        recyclerViewReview = view.findViewById(R.id.recycler_view_review);
        // student
        textViewSubscriberNum = view.findViewById(R.id.text_view_subscriber_num);
        textViewSubscriberSeeAll = view.findViewById(R.id.text_view_subscriber_see_all);
        recyclerViewSubscribers = view.findViewById(R.id.recycler_view_subscribers);
        // related class
        recyclerViewRelatedClass = view.findViewById(R.id.recycler_view_related_class);
    }

    private void setSeeAllOnClickListener() {
        textViewProjectSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SeeAllActivity.class);
            intent.putExtra(ConstantUtil.SEE_ALL_FLAG, ConstantUtil.PROJECT_ITEM);
            intent.putExtra(ConstantUtil.ID_FLAG, classId);
            context.startActivity(intent);
        });

        textViewSubscriberSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SeeAllActivity.class);
            intent.putExtra(ConstantUtil.SEE_ALL_FLAG, ConstantUtil.SUBSCRIBER_ITEM);
            intent.putExtra(ConstantUtil.ID_FLAG, classId);
            intent.putExtra(ConstantUtil.TOOLBAR_TITLE_FLAG, textViewSubscriberNum.getText().toString());
            intent.putParcelableArrayListExtra(ConstantUtil.SUBSCRIBERS_FLAG, (ArrayList<? extends Parcelable>) about.getSubscribers());
            context.startActivity(intent);
        });

        textViewReviewSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SeeAllActivity.class);
            intent.putExtra(ConstantUtil.SEE_ALL_FLAG, ConstantUtil.REVIEW_ITEM);
            intent.putExtra(ConstantUtil.ID_FLAG, classId);
            intent.putParcelableArrayListExtra(ConstantUtil.REVIEWS_FLAG, (ArrayList<? extends Parcelable>) about.getReviews());
            context.startActivity(intent);
        });
    }
}