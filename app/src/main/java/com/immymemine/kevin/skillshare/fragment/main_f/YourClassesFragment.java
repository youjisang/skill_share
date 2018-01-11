package com.immymemine.kevin.skillshare.fragment.main_f;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.user.SubscribedClass;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.utility.StateUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YourClassesFragment extends Fragment {

    Context context;

    TextView textViewSubscribedCount;
    ImageView imageViewThumbnail;

    public YourClassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_classes, container, false);

        context = getActivity();

        textViewSubscribedCount = view.findViewById(R.id.text_view_subscribed_count);
        imageViewThumbnail = view.findViewById(R.id.image_view_thumbnail);

        StateUtil state = StateUtil.getInstance();
        if(state.getState()) {
            User user = state.getUserInstance();
            if(user.getSubscribedClasses() != null) {
                List<SubscribedClass> subscribedClasses = user.getSubscribedClasses();
                int size = subscribedClasses.size();
                textViewSubscribedCount.setText(size + " Classes");
                Glide.with(context).load(subscribedClasses.get(size-1).getImageUrl())
                        .apply(RequestOptions.centerCropTransform())
                        .into(imageViewThumbnail);
            }
        }

        return view;
    }

    public void clickDownload(View view) {
        // download page //
    }

    public void clickSubscribedClasses(View view) {
        // see all activity //
    }
}
