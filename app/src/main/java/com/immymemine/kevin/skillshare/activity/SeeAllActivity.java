package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.DiscussionSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.ReviewSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.see_all_adapter.SubscriberSeeAllRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.model.m_class.Review;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.SeeAllService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;
import com.immymemine.kevin.skillshare.utility.communication_util.Bus;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Collections;
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
    String id;

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

        id = intent.getStringExtra(ConstantUtil.ID_FLAG);
        String title = intent.getStringExtra(ConstantUtil.TOOLBAR_TITLE_FLAG);

        service = RetrofitHelper.createApi(SeeAllService.class);

        initiateView(title);

//        networking(id);
    }

    private void networking(String id) {
        //retrofit...
        switch (type) {
            case ConstantUtil.CLASS_ITEM:
                break;
            case ConstantUtil.DISCUSSION_ITEM:
                break;
            case ConstantUtil.PROJECT_ITEM:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(type.equals(ConstantUtil.DISCUSSION_ITEM)) {
            Map<Integer, List<Reply>> data = new HashMap<>();
            replies.remove(0);
            Collections.reverse(replies);
            data.put(position, replies);
            Bus.getInstance().post(data);
        }
        super.onBackPressed();
    }

    TextView textViewToolbarTitle;
    private void initiateView(@Nullable String title) {
        // toolbar title
        textViewToolbarTitle = findViewById(R.id.toolbar_title);

        if(title != null)
            textViewToolbarTitle.setText(title);

        // back button
        findViewById(R.id.toolbar_button_back).setOnClickListener(
                v -> {
                    if(type.equals(ConstantUtil.DISCUSSION_ITEM)) {
                        Map<Integer, List<Reply>> data = new HashMap<>();
                        replies.remove(0);
                        data.put(position, replies);
                        Bus.getInstance().post(data);
                    }
                    finish();
                }
        );

        // recycler view
        recyclerView = findViewById(R.id.recycler_view_seeAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switch (type) {
            case ConstantUtil.CLASS_ITEM:

                break;
            case ConstantUtil.DISCUSSION_ITEM:
                replies = intent.getParcelableArrayListExtra(ConstantUtil.DISCUSSION_ITEM);
                adapter = new DiscussionSeeAllRecyclerViewAdapter(this, replies);
                recyclerView.setAdapter(adapter);

                editTextReply = findViewById(R.id.edit_text_reply);
                textViewSendReply = findViewById(R.id.text_view_send_reply);

                // TODO stateUtil 사용
                User user = StateUtil.getInstance().getUserInstance();
                if(user == null || user.get_id() == null) {

                    findViewById(R.id.linear_layout_discussion).setVisibility(View.GONE);
                    findViewById(R.id.linear_layout_sign_message).setVisibility(View.VISIBLE);

                    findViewById(R.id.sign_in).setOnClickListener(
                            view -> startActivity(new Intent(SeeAllActivity.this, SignInActivity.class))
                    );

                    findViewById(R.id.sign_up).setOnClickListener(
                            view -> startActivity(new Intent(SeeAllActivity.this, SignUpActivity.class))
                    );

                } else {

                    findViewById(R.id.linear_layout_discussion).setVisibility(View.VISIBLE);
                    findViewById(R.id.linear_layout_sign_message).setVisibility(View.GONE);

                    RxTextView.textChanges(editTextReply).subscribe(
                            charSequence -> {
                                if(!ValidationUtil.isEmpty(charSequence.toString())) {
                                    textViewSendReply.setEnabled(true);
                                    textViewSendReply.setTextColor(getResources().getColor(R.color.IcActive));
                                } else {
                                    textViewSendReply.setEnabled(false);
                                    textViewSendReply.setTextColor(getResources().getColor(R.color.IcDisabled));
                                }
                            }
                    );

                    textViewSendReply.setOnClickListener(
                            view -> {
                                Reply reply = new Reply(
                                        user.getName(),
                                        user.getImageUrl(),
                                        editTextReply.getText().toString(),
                                        System.currentTimeMillis() + ""
                                );

                                service.addReply(reply, id)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                (List<Reply> replies) -> {
                                                    Reply discussion = this.replies.get(0);

                                                    Collections.reverse(replies);
                                                    this.replies.clear();
                                                    this.replies.add(0, discussion);
                                                    this.replies.addAll(replies);

                                                    ((DiscussionSeeAllRecyclerViewAdapter)adapter).update(this.replies);

                                                    textViewToolbarTitle.setText(this.replies.size()-1 + " Replies");
                                                },
                                                (Throwable error) -> {
                                                    Log.e("JUWONLEE", "add reply error : "+ error.getMessage());
                                                }
                                        );

                                // Reply 입력칸 비우기
                                editTextReply.setText("");
                            }
                    );
                }

                break;
            case ConstantUtil.PROJECT_ITEM:
                textViewToolbarTitle.setText("Subscriber Projects");
                intent.getParcelableArrayListExtra(ConstantUtil.ID_FLAG);
//                recyclerView.setAdapter(new ProjectSeeAllRecyclerViewAdapter(this, projects));
                break;
            case ConstantUtil.REVIEW_ITEM:

                textViewToolbarTitle.setText("Class Reviews");

                // review
                textViewTestimonials = findViewById(R.id.text_view_testimonials);
                textViewReviewPercent = findViewById(R.id.text_view_review_percent);
                textViewPositiveCount = findViewById(R.id.text_view_positive_count);
                textViewNegativeCount = findViewById(R.id.text_view_negative_count);

                List<Review> reviews = intent.getParcelableArrayListExtra(ConstantUtil.REVIEWS_FLAG);

                int size = reviews.size();
                int likeCount = 0;
                for(Review review : reviews) {
                    if("like".equals(review.getLikeOrNot()))
                        likeCount++;
                }

                textViewReviewPercent.setText( (likeCount * 100 / size) + "% Positive Reviews");
                textViewPositiveCount.setText(likeCount + "");
                textViewNegativeCount.setText(size-likeCount + "");
                if(size > 1)
                    textViewTestimonials.setText(size + " Testimonials");
                else
                    textViewTestimonials.setText(size + " Testimonial");

                recyclerView.setAdapter(new ReviewSeeAllRecyclerViewAdapter(this, reviews));
                break;
            case ConstantUtil.SUBSCRIBER_ITEM:
                textViewToolbarTitle.setText("Subscribers");

                List<Subscriber> subscribers = intent.getParcelableArrayListExtra(ConstantUtil.SUBSCRIBERS_FLAG);
                recyclerView.setAdapter(new SubscriberSeeAllRecyclerViewAdapter(this, subscribers));
                break;
        }
    }
}