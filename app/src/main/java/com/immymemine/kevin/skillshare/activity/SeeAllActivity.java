package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.DiscussionSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.ProjectSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.ReviewSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.SubscriberSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;
import com.immymemine.kevin.skillshare.model.see_all.Project;
import com.immymemine.kevin.skillshare.model.see_all.Review;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.SeeAllService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;
import com.immymemine.kevin.skillshare.utility.communication_util.Bus;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SeeAllActivity extends AppCompatActivity {

    // intent
    Intent intent;
    String type;
    int position;

    // api
    SeeAllService service;

    // recycler view
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    // discussion
    EditText editTextReply;
    TextView textViewSendReply;
    List<Reply> replies;

    // Review
    TextView textViewReviewPercent, textViewTestimonials, textViewPositiveCount, textViewNegativeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 인텐트 받아옴
        intent = getIntent();
        type = intent.getStringExtra(ConstantUtil.SEE_ALL_FLAG);
        position = intent.getIntExtra("position", -1);

        if(type.equals(ConstantUtil.REVIEW_ITEM))
            setContentView(R.layout.activity_see_all_review);
        else if(type.equals(ConstantUtil.DISCUSSION_ITEM))
            setContentView(R.layout.activity_see_all_discussion);
        else
            setContentView(R.layout.activity_see_all);

        String id = intent.getStringExtra("ID");
        String title = intent.getStringExtra(ConstantUtil.TOOLBAR_TITLE_FLAG);

        service = RetrofitHelper.createApi(SeeAllService.class);

        initiateView(title);
        networking(id);
    }

    private void networking(String id) {
        //retrofit...
        switch (type) {
            case ConstantUtil.CLASS_ITEM:

                break;
            case ConstantUtil.DISCUSSION_ITEM:
                replies = intent.getParcelableArrayListExtra(ConstantUtil.DISCUSSION_ITEM);
                adapter = new DiscussionSeeAllRecyclerViewAdapter(this, replies);
                recyclerView.setAdapter(adapter);
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

                break;
        }
    }



    @Override
    public void onBackPressed() {
        if(type.equals(ConstantUtil.DISCUSSION_ITEM)) {
            Map<Integer, List<Reply>> data = new HashMap<>();
            replies.remove(0);
            data.put(position, replies);
            Bus.getInstance().post(data);
        }
        super.onBackPressed();
    }
    TextView textViewToolbarTitle;
    private void initiateView(String title) {
        // toolbar title
        textViewToolbarTitle = findViewById(R.id.toolbar_title);
        textViewToolbarTitle.setText(title);

        // back
        findViewById(R.id.toolbar_button_back).setOnClickListener(v -> finish());

        // recycler view
        recyclerView = findViewById(R.id.recycler_view_seeAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switch (type) {
            case ConstantUtil.CLASS_ITEM:

                break;
            case ConstantUtil.DISCUSSION_ITEM:
                editTextReply = findViewById(R.id.edit_text_reply);
                textViewSendReply = findViewById(R.id.text_view_send_reply);

                int activeColor = getResources().getColor(R.color.IcActive);

                RxTextView.textChanges(editTextReply).subscribe(
                        charSequence -> {
                            if(!ValidationUtil.isEmpty(charSequence.toString())) {
                                textViewSendReply.setEnabled(true);
                                textViewSendReply.setTextColor(activeColor);
                            }
                        }
                );

                textViewSendReply.setOnClickListener(
                        view -> {

                            User user = MainActivity.user;
                            Reply reply = new Reply(
                                    user.getName(),
                                    user.getImageUrl(),
                                    editTextReply.getText().toString(),
                                    System.currentTimeMillis() + ""
                            );

                            service.addReply(reply)
                                    .observeOn(Schedulers.io())
                                    .subscribeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            (List<Reply> replies) -> {
                                                ((DiscussionSeeAllRecyclerViewAdapter)adapter).update(replies);
                                            },
                                            (Throwable error) -> {
                                                Log.e("JUWONLEE", error.getMessage() + "");
                                            }
                                    );

                            // Reply 입력칸 비우기
                            editTextReply.setText("");

                            replies.add(1, reply);
                            textViewToolbarTitle.setText(replies.size() + " Replies");

                            ((DiscussionSeeAllRecyclerViewAdapter)adapter).update(replies);
                        }
                );

                break;
            case ConstantUtil.PROJECT_ITEM:

                break;
            case ConstantUtil.REVIEW_ITEM:
                // review
                textViewTestimonials = findViewById(R.id.text_view_testimonials);
                textViewReviewPercent = findViewById(R.id.text_view_review_percent);
                textViewPositiveCount = findViewById(R.id.text_view_positive_count);
                textViewNegativeCount = findViewById(R.id.text_view_negative_count);
                break;
            case ConstantUtil.SUBSCRIBER_ITEM:
                List<Subscriber> subscribers = intent.getParcelableArrayListExtra(ConstantUtil.SUBSCRIBERS_FLAG);
                recyclerView.setAdapter(new SubscriberSeeAllRecyclerViewAdapter(this, subscribers));
                break;
        }
    }
}