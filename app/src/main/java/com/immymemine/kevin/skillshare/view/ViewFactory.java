package com.immymemine.kevin.skillshare.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.SignInActivity;
import com.immymemine.kevin.skillshare.activity.SignUpActivity;
import com.immymemine.kevin.skillshare.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.SkillsRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.discover.DiscoverClass;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.TimeUtil;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Main Activity 에서 사용하는 View Factory
 * Created by quf93 on 2017-11-18.
 */

public class ViewFactory {

    Context context;
    LayoutInflater inflater;
    RecyclerView recyclerView;
    InteractionInterface interactionInterface;

    // Singleton
    private static ViewFactory instance;

    private ViewFactory(Context context) {
        // context
        this.context = context;
        // inflater
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // interface for interaction
        if (context instanceof InteractionInterface)
            interactionInterface = (InteractionInterface) context;
    }

    public static ViewFactory getInstance(Context context) {
        if (instance == null) {
            instance = new ViewFactory(context);
        }

        return instance;
    }

    public void destroyViewFactory() {
        instance = null;
    }

    public LinearLayout getViewContainer() {
        LinearLayout view_container = (LinearLayout) inflater.inflate(R.layout.container, null);
        return view_container;
    }

    public View getWelcomeView() {
        View view = inflater.inflate(R.layout.welcome_view, null);

        view.findViewById(R.id.close_button).setOnClickListener(v -> interactionInterface.close());
        view.findViewById(R.id.button_sign_up).setOnClickListener(v -> context.startActivity(new Intent(context, SignUpActivity.class)));

        return view;
    }

    class GeneralViewFactory implements Callable<View> {

        String title;
        List<Class> classes;

        public GeneralViewFactory(String title, List<Class> classes) {
            this.title = title;
            this.classes = classes;
        }

        @Override
        public View call() throws Exception {
            View view = inflater.inflate(R.layout.general_view, null);

            // recycler view setting
            recyclerView = view.findViewById(R.id.general_recycler_view);

            if (classes == null)
                recyclerView.setAdapter(new GeneralRecyclerViewAdapter(context));
            else
//                recyclerView.setAdapter(new GeneralRecyclerViewAdapter(context, classes));

            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // title setting
            ((TextView) view.findViewById(R.id.text_view_title)).setText(title);
            // button onClickListener setting
            view.findViewById(R.id.button_see_all).setOnClickListener(v -> {
                // see all page 이동
                interactionInterface.seeAll(title);
            });

            return view;
        }
    }

    public View getGeneralView(String title, List<Class> classes) throws Exception {
        return new GeneralViewFactory(title, classes).call();
    }

    class GroupViewFactory implements Callable<View> {
        String title;
        List<Group> groups;


        public GroupViewFactory(String title, List<Group> groups) {
            this.title = title;
            this.groups = groups;
        }

        @Override
        public View call() throws Exception {
            View view = inflater.inflate(R.layout.group_view, null);

            // recycler view setting
            recyclerView = view.findViewById(R.id.group_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

//            recyclerView.setAdapter(new GroupRecyclerViewAdapter(groups, context));

            // title setting
            ((TextView) view.findViewById(R.id.text_view_title)).setText(title);

            return view;
        }
    }

    public View getGroupView(String title, List<Group> groupList) throws Exception {
        return new GroupViewFactory(title, groupList).call();
    }

    class DiscoverViewFactory implements Callable<View> {

        DiscoverClass discoverClass;

        public DiscoverViewFactory(DiscoverClass discoverClass) {
            this.discoverClass = discoverClass;
        }

        @Override
        public View call() throws Exception {
            View view = inflater.inflate(R.layout.discover_view, null);

            view.findViewById(R.id.relative_layout_class).setOnClickListener(v -> {
                // class activity 이동
                String classId = discoverClass.get_id();
            });

            view.findViewById(R.id.relative_layout_tutor).setOnClickListener(v -> {
                // profile activity 이동
                String tutorId = discoverClass.getTutorId();
            });

            ((TextView)view.findViewById(R.id.text_view_featured_class_title)).setText(discoverClass.getTitle());
            ((TextView)view.findViewById(R.id.text_view_featured_class_duration)).setText(TimeUtil.calculateVideoTime(discoverClass.getDuration()));
            ((TextView)view.findViewById(R.id.text_view_featured_class_review_percent)).setText(discoverClass.getReviewPercent()+"%");
            ((TextView)view.findViewById(R.id.text_view_featured_class_subscriber_count)).setText(discoverClass.getSubscriberCount());

            ((TextView)view.findViewById(R.id.text_view_tutor_name)).setText(discoverClass.getTutorName());

            recyclerView = view.findViewById(R.id.recycler_view_discover);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//            recyclerView.setAdapter(new DiscoverRecyclerViewAdapter(context, discoverClass.getFeaturedClasses()));

            return view;
        }
    }

    public View getDiscoverView(DiscoverClass discoverClass) throws Exception {
        return new DiscoverViewFactory(discoverClass).call();
    }

    public View getDiscoverView() throws Exception {
        return getDiscoverView(null);
    }

    public View getYourClassesView() {
        View view = inflater.inflate(R.layout.your_classes_view, null);

        view.findViewById(R.id.text_view_download).setOnClickListener(
                v -> {

                }
        );

        view.findViewById(R.id.image_view_thumbnail).setOnClickListener(
                v -> {

                }
        );

        return view;
    }

    public View getMeView() {
        // main thread 에서 굳이 해주지 않아도 된다
        View view = inflater.inflate(R.layout.me_view, null);

        // 이름, 닉네임 세팅
        User user = StateUtil.getInstance().getUserInstance();

        ((TextView) view.findViewById(R.id.me_name)).setText(user.getName());
        ((TextView) view.findViewById(R.id.me_nickname)).setText(user.getNickname());

        // followers, following <<< onClick setting...
        if (user.getFollowers() != null)
            ((TextView) view.findViewById(R.id.me_followers)).setText(user.getFollowers().size() + " Followers");
        else
            ((TextView) view.findViewById(R.id.me_followers)).setText("0 Followers");

        if (user.getFollowing() != null)
            ((TextView) view.findViewById(R.id.me_following)).setText("Following " + user.getFollowing().size());
        else
            ((TextView) view.findViewById(R.id.me_following)).setText("Following 0");

        view.findViewById(R.id.me_button).setOnClickListener(v -> interactionInterface.signOut());

        return view;
    }

    public View getMeSkillView(List<String> skills) {
        View view = inflater.inflate(R.layout.me_skill_view, null);

        RecyclerView recyclerViewSkills = view.findViewById(R.id.recycler_view_skills);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        recyclerViewSkills.setLayoutManager(layoutManager);
        recyclerViewSkills.setAdapter(new SkillsRecyclerViewAdapter(context, skills));

        view.findViewById(R.id.personalize).setOnClickListener(v -> interactionInterface.select());

        if (skills == null || skills.size() == 0)
            view.findViewById(R.id.divider).setVisibility(View.GONE);
        else
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);

        return view;
    }

    public View getMeSkillView() {
        return getMeSkillView(null);
    }

    public View getNotSignedInMeView() {
        View view = inflater.inflate(R.layout.me_view_not_signed_in, null);

        view.findViewById(R.id.button_sign_up).setOnClickListener(v -> {
            context.startActivity(new Intent(context, SignUpActivity.class));
        });

        view.findViewById(R.id.button_sign_in).setOnClickListener(v -> {
            context.startActivity(new Intent(context, SignInActivity.class));
        });

        return view;
    }

    public interface InteractionInterface {
        // welcome view 닫기
        void close();

        // select activity 로 이동
        void select();

        // see all activity 이동
        void seeAll(String title);

        // sign out
        void signOut();
    }
}
