package com.immymemine.kevin.skillshare.fragment.main_f;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.main_adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.discover.DiscoverClass;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.HomeService;
import com.immymemine.kevin.skillshare.utility.TimeUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    Context context;
    View view;

    GeneralRecyclerViewAdapter dAdapter, tAdapter, pAdapter;
    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, container, false);

        context = getActivity();

        RecyclerView recyclerViewDiscover = view.findViewById(R.id.recycler_view_discover);
        RecyclerView recyclerViewTrendingClasses = view.findViewById(R.id.recycler_view_t);
        RecyclerView recyclerViewPopularClasses = view.findViewById(R.id.recycler_view_p);

        recyclerViewDiscover.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrendingClasses.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopularClasses.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        dAdapter = new GeneralRecyclerViewAdapter(context);
        recyclerViewDiscover.setAdapter(dAdapter);
        tAdapter = new GeneralRecyclerViewAdapter(context);
        recyclerViewTrendingClasses.setAdapter(tAdapter);
        pAdapter = new GeneralRecyclerViewAdapter(context);
        recyclerViewPopularClasses.setAdapter(pAdapter);

        RetrofitHelper.createApi(HomeService.class)
                .getDiscoverClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

        return view;
    }

    private void handleResponse(DiscoverClass discoverClass) {
        ((TextView)view.findViewById(R.id.text_view_featured_class_title)).setText(discoverClass.getTitle());
        ((TextView)view.findViewById(R.id.text_view_featured_class_duration)).setText(TimeUtil.calculateVideoTime(discoverClass.getDuration()));
        ((TextView)view.findViewById(R.id.text_view_featured_class_review_percent)).setText(discoverClass.getReviewPercent()+"%");
        ((TextView)view.findViewById(R.id.text_view_featured_class_subscriber_count)).setText(discoverClass.getSubscriberCount());

        ((TextView)view.findViewById(R.id.text_view_user_name)).setText(discoverClass.getTutorName());

        view.findViewById(R.id.relative_layout_class).setOnClickListener(v -> {
            // class activity 이동
            String classId = discoverClass.get_id();
            Toast.makeText(context, "Class ID : " + classId, Toast.LENGTH_LONG).show();
        });

        view.findViewById(R.id.relative_layout_tutor).setOnClickListener(v -> {
            // profile activity 이동
            String tutorId = discoverClass.getTutorId();
            Toast.makeText(context, "Tutor ID : " + tutorId, Toast.LENGTH_LONG).show();
        });

        Glide.with(this).load(discoverClass.getImageUrl())
                .apply(RequestOptions.centerCropTransform())
                .into((ImageView) view.findViewById(R.id.image_view_featured_class));

        Glide.with(this).load(discoverClass.getTutorImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into((ImageView) view.findViewById(R.id.image_view_group));

        dAdapter.update(discoverClass.getFeaturedClasses());

        Map<String, List<Class>> classes = discoverClass.getClasses();

        List<Class> trendingClasses = classes.get("t");
        tAdapter.update(trendingClasses);

        List<Class> popularClasses = classes.get("p");
        pAdapter.update(popularClasses);
    }

    private void handleError(Throwable error) {

    }
}
