package com.immymemine.kevin.skillshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.LessonsAdapter;
import com.immymemine.kevin.skillshare.model.m_class.Lessons;
import com.immymemine.kevin.skillshare.model.m_class.Tutor;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.ClassService;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonsFragment extends Fragment {

    TextView textViewTitle, textViewTime, textViewReview, textViewSubscriberCount;
    Button buttonFollow;
    TextView textViewTutor, textViewFollowersCount;
    ImageView imageViewTutor;
    RecyclerView recyclerViewLessons;

    LessonsAdapter adapter;

    public LessonsFragment() {
        // Required empty public constructor
    }
    /* TODO 지상
    프래그먼트에서는 빈 생성자가 필수라고 함. 없으면 튕길 수 있음.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        recyclerViewLessons = view.findViewById(R.id.recycler_view_lessons);
        recyclerViewLessons.setLayoutManager(new LinearLayoutManager(view.getContext()));

        initiateView(view);

        RetrofitHelper.createApi(ClassService.class)
                .getLessons(getArguments().getString("_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        // TODO progress bar

        return view;
    }
    /* TODO 지상
       getLenssons레트로핏 인터페이스에서 액티비티로 전달 받은 아이디를 참조해 데이터를 불러온다.
       성공적으로 불러왔으면 handelResponse로 전달함.
       실패했으면 handleError로 전달함.
     */

    private void handleResponse(Lessons lessons) {

        // class 정보
        textViewTitle.setText(lessons.getTitle());
        textViewTime.setText(lessons.getTime());
        textViewReview.setText(lessons.getReview());
        textViewSubscriberCount.setText(lessons.getSubscriberCount());

        // tutor 정보
        Tutor tutor = lessons.getTutor();
        textViewTutor.setText(tutor.getName());
        textViewFollowersCount.setText(tutor.getFollowers());
        Glide.with(getActivity()).load(tutor.getPictureUrl()).into(imageViewTutor);

        // video data 전달
        adapter = new LessonsAdapter(getActivity(), Arrays.asList(lessons.getVideos()));
        recyclerViewLessons.setAdapter(adapter);

        // TODO hide progress bar
    }
    /* TODO 지상

        Tutor tutor = lessons.getTutor()
        -> Tutor 객체를 lessons의 tutor로 정의함
         ☆ 개인적으로 그동안 헷갈렸던 부분
     */

    private void handleError(Throwable error) {

    }

    private void initiateView(View v) {
        // class 정보
        textViewTitle = v.findViewById(R.id.text_view_title);
        textViewTime = v.findViewById(R.id.text_view_time);
        textViewReview = v.findViewById(R.id.text_view_review);
        textViewSubscriberCount = v.findViewById(R.id.text_view_subscriber_count);
        // tutor 정보
        textViewTutor = v.findViewById(R.id.text_view_profile);
        textViewFollowersCount = v.findViewById(R.id.text_view_followers_count);
        imageViewTutor = v.findViewById(R.id.image_view_tutor);
        // follow 버튼
        buttonFollow = v.findViewById(R.id.button_follow);
        // lessons recycler view
        recyclerViewLessons = v.findViewById(R.id.recycler_view_lessons);
    }

}
