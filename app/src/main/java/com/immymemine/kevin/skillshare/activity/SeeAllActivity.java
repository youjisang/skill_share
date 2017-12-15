package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;

import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.DiscussionSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.ProjectSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.ReviewSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.SubscriberSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.model.see_all.Project;
import com.immymemine.kevin.skillshare.model.see_all.Review;
import com.immymemine.kevin.skillshare.model.see_all.Subscriber;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.SeeAllService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SeeAllActivity extends AppCompatActivity {

    // intent
    Intent intent;

    // recycler view
    RecyclerView recyclerView;

    // Review
    TextView textViewReviewPercent, textViewTestimonials, textViewPositiveCount, textViewNegativeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 인텐트 받아옴
        intent = getIntent();
        String type = intent.getStringExtra(ConstantUtil.SEE_ALL_FLAG);

        if(type.equals(ConstantUtil.REVIEW_ITEM))
            setContentView(R.layout.activity_see_all_review);
        else if(type.equals(ConstantUtil.DISCUSSION_ITEM))
            setContentView(R.layout.activity_see_all_discussion);
        else
            setContentView(R.layout.activity_see_all);

        String id = intent.getStringExtra("ID");
        String title = intent.getStringExtra(ConstantUtil.TOOLBAR_TITLE_FLAG);


//        // 1. 인텐트 받아옴
//        intent = getIntent();
//        String type = intent.getStringExtra(ConstantUtil.SEE_ALL_FLAG);
//        String id = intent.getStringExtra("ID");
//        String title = intent.getStringExtra(ConstantUtil.TOOLBAR_TITLE_FLAG);
//
//        initiateView(title);
//
//        // TODO 2. 인텐트에서 받아온 구분 값을 어댑터 설정
//        networking(type, id);
    }

    private void networking(String type, String id) {
        SeeAllService service = RetrofitHelper.createApi(SeeAllService.class);
        //retrofit...
        switch (type) {
            case ConstantUtil.CLASS_ITEM:

                break;
            case ConstantUtil.DISCUSSION_ITEM:
                List<Reply> replyList = new ArrayList<>();
                replyList = (ArrayList) intent.getParcelableArrayListExtra("TEST");
                recyclerView.setAdapter(new DiscussionSeeAllRecyclerViewAdapter(this, replyList));
                break;
            case ConstantUtil.PROJECT_ITEM:
                service.getSeeAllProject(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (List<Project> projects) -> {
                                    recyclerView.setAdapter(new ProjectSeeAllRecyclerViewAdapter(this, projects));
                                },
                                (Throwable error) -> {

                                }
                        );

                // test project
                List<Project> projects = new ArrayList<>();
                projects.add(new Project(
                        "project_id_1",
                        "http://image.chosun.com/sitedata/image/201508/06/2015080603367_0.jpg",
                        "3",
                        "Buildings",
                        "Scott Donald"
                ));
                projects.add(new Project(
                        "project_id_1",
                        "http://news20.busan.com/content/image/2017/09/11/20170911000004_0.jpg",
                        "34",
                        "Golden",
                        "Yiria Ramirez"
                ));
                projects.add(new Project(
                        "project_id_2",
                        "https://0.soompi.io/wp-content/uploads/2017/09/13145643/IU12.jpg",
                        "7",
                        "Sweet Inspired Doodles",
                        "Beth N"
                ));
                recyclerView.setAdapter(new ProjectSeeAllRecyclerViewAdapter(this, projects));
                break;
            case ConstantUtil.REVIEW_ITEM:
                // setting data
                textViewReviewPercent.setText(intent.getStringExtra("REVIEW_PERCENT"));
                // TODO 좋아요 / 싫어요 개수 받아와서 뿌려주기

                service.getSeeAllReview(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (List<Review> reviews) -> {
                                    recyclerView.setAdapter(new ReviewSeeAllRecyclerViewAdapter(this, reviews));
                                },
                                (Throwable error) -> {

                                }
                        );

                // test review
                List<Review> reviews = new ArrayList<>();
                reviews.add(new Review(
                        "true",
                        "Tasty and nice recipe! Makes me want to try!",
                        "Elena Lepretre",
                        "http://news20.busan.com/content/image/2017/09/11/20170911000004_0.jpg"
                ));
                reviews.add(new Review(
                        "true",
                        "Yum! Nice instruction. Definitely will try these. Thanks!",
                        "Darlene Del Castillo",
                        "https://pbs.twimg.com/profile_images/908706663519051776/84pGO2Zl.jpg"
                ));

                recyclerView.setAdapter(new ReviewSeeAllRecyclerViewAdapter(this, reviews));
                break;
            case ConstantUtil.SUBSCRIBER_ITEM:
                service.getSeeAllSubscriber(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (List<Subscriber> subscribers) -> {
                                    recyclerView.setAdapter(new SubscriberSeeAllRecyclerViewAdapter(this, subscribers));
                                },
                                (Throwable error) -> {
                                    // if( error ) >>> networking 이 안된다는 ... 메시지
                                }
                        );

                // test subscriber
                List<Subscriber> subscribers = new ArrayList<>();
                subscribers.add(new Subscriber(
                        "subscriber_id1",
                        "James",
                        "https://pbs.twimg.com/profile_images/908706663519051776/84pGO2Zl.jpg"
                ));
                subscribers.add(new Subscriber(
                        "subscriber_id2",
                        "Ruthie Frank",
                        "http://news20.busan.com/content/image/2017/09/11/20170911000004_0.jpg"
                ));
                subscribers.add(new Subscriber(
                        "subscriber_id3",
                        "Kathy Archambault",
                        "http://image.chosun.com/sitedata/image/201508/06/2015080603367_0.jpg"
                ));
                recyclerView.setAdapter(new SubscriberSeeAllRecyclerViewAdapter(this, subscribers));
                break;
        }
    }



    private void initiateView(String title) {
        // toolbar title
        ((TextView)findViewById(R.id.toolbar_title)).setText(title);

        // back
        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());

        // recycler view
        recyclerView = findViewById(R.id.recycler_view_seeAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // review
        textViewTestimonials = findViewById(R.id.text_view_testimonials);
        textViewReviewPercent = findViewById(R.id.text_view_review_percent);
        textViewPositiveCount = findViewById(R.id.text_view_positive_count);
        textViewNegativeCount = findViewById(R.id.text_view_negative_count);
    }
}