package com.immymemine.kevin.skillshare.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.DiscoverRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GroupRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.SkillsRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.dummy.Group;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.model.user.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Main Activity 에서 사용하는 View Factory
 * Created by quf93 on 2017-11-18.
 */

public class ViewFactory {
    Context context;
    LayoutInflater inflater;
    RecyclerView recyclerView;
    InteractionInterface interactionInterface;

    // TODO nThreads 를 정하는 합리적인 로직이 있어야한다.
    public ExecutorService executor = Executors.newCachedThreadPool();

    // Singleton
    private static ViewFactory v;

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
        if (v == null) {
            v = new ViewFactory(context);
        }

        return v;
    }

    public void destroyViewFactory() {
        v = null;
    }

    public LinearLayout getViewContainer() {
        LinearLayout view_container = (LinearLayout) inflater.inflate(R.layout.container, null);
        return view_container;
    }

    public View getWelcomeView() {
        // 하나의 Thread 를 Thread pool 에서 가져와서 Callable 객체를 던져서 Thread 를 실행시킨다.
        // Future< ? > >>> thread 가 끝나면 객체를 반환받는다.
        Future<View> f = executor.submit(() -> {
            View view = inflater.inflate(R.layout.welcome_view, null);
            view.findViewById(R.id.close_button).setOnClickListener(v -> interactionInterface.close());
            view.findViewById(R.id.button_sign_up).setOnClickListener(v -> interactionInterface.signUp());
            return view;
        });

        try {
            // thread process 가 끝나면 return 값 반환
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
                recyclerView.setAdapter(new GeneralRecyclerViewAdapter(context, classes));
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

    public View getGeneralView(String title, List<Class> classes) {
        Future<View> f = executor.submit(new GeneralViewFactory(title, classes));
        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getGeneralView(String title) {
        return getGeneralView(title, null);
    }

    class GroupViewFactory implements Callable<View> {
        String title;
        List<Group> groupList;


        public GroupViewFactory(String title, List<Group> groupList) {
            this.title = title;
            this.groupList = groupList;


        }

        @Override
        public View call() throws Exception {
            View view = inflater.inflate(R.layout.group_view, null);

            // recycler view setting
            recyclerView = view.findViewById(R.id.group_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            recyclerView.setAdapter(new GroupRecyclerViewAdapter(groupList, context, groupList.size()));

            // title setting
            ((TextView) view.findViewById(R.id.text_view_title2)).setText(title);

            return view;
        }
    }

    public View getGroupView(String title, List<Group> groupList) {
        Future<View> f = executor.submit(new GroupViewFactory(title, groupList));

        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    class DiscoverViewFactory implements Callable<View> {

        @Override
        public View call() throws Exception {
            View view = inflater.inflate(R.layout.discover_view, null);
            recyclerView = view.findViewById(R.id.recycler_view_discover);
            recyclerView.setAdapter(new DiscoverRecyclerViewAdapter());
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            return view;
        }
    }

    public View getDiscoverView() {
        Future<View> f = executor.submit(new DiscoverViewFactory());

        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getYourClassesView() {
        Future<View> f = executor.submit(
                () -> {
                    View view = inflater.inflate(R.layout.your_classes_view, null);

                    // main thread 영역 ------------------------------------------------------------
                    // video thumbnail setting

                    // Review ) 속도가 너무 느리다... MainActivity 에서 view 를 받아서 처리해주는게 훨씬 빠름
//                    if(context instanceof MainActivity) {
//                        ((MainActivity) context).runOnUiThread(
//                                () -> {
//                                    ImageView video_thumbnail = view.findViewById(R.id._your_classes_video_thumbnail);
//                                    Glide.with(context).load(/*thumbnail*/R.drawable.common_google_signin_btn_icon_light_normal).into(video_thumbnail);
//                                }
//                        );
//                    }
                    return view;
                }
        );

        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getMeView(User user) {
        Future<View> f = executor.submit(
                () -> {
                    // main thread 에서 굳이 해주지 않아도 된다
                    View view = inflater.inflate(R.layout.me_view, null);

                    // 이름, 닉네임 세팅
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
        );

        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getMeSkillView(List<String> skills) {
        Future<View> f = executor.submit(
                () -> {
                    View view = inflater.inflate(R.layout.me_skill_view, null);
                    RecyclerView recyclerViewSkills = view.findViewById(R.id.recycler_view_skills);
                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
                    layoutManager.setFlexDirection(FlexDirection.ROW);

                    recyclerViewSkills.setLayoutManager(layoutManager);
                    recyclerViewSkills.setAdapter(new SkillsRecyclerViewAdapter(context, skills));

                    Button personalize = view.findViewById(R.id.personalize);
                    personalize.setOnClickListener(v -> interactionInterface.select());
                    if (skills == null)
                        view.findViewById(R.id.divider).setVisibility(View.GONE);
                    else
                        view.findViewById(R.id.divider).setVisibility(View.VISIBLE);

                    return view;
                }
        );

        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getMeSkillView() {
        return getMeSkillView(null);
    }

    public View getNotSignedInMeView() {
        Future<View> f = executor.submit(
                () -> {
                    View view = inflater.inflate(R.layout.me_view_not_signed_in, null);
                    view.findViewById(R.id.button_sign_up).setOnClickListener(v -> {
                        interactionInterface.signUp();
                    });
                    view.findViewById(R.id.button_sign_in).setOnClickListener(v -> {
                        interactionInterface.signIn();
                    });
                    return view;
                }
        );

        try {
            return f.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface InteractionInterface {
        // welcome view 닫기
        void close();

        // sign up / in page 이동
        void signUp();

        void signIn();

        // select activity 로 이동
        void select();

        // see all activity 이동
        void seeAll(String title);

        // sign out
        void signOut();
    }

    // 안쓰는 ====================================================================
    class ToolbarFactory implements Runnable {
        Toolbar toolbar_with_back_button;

        @Override
        public void run() {
            toolbar_with_back_button = (Toolbar) inflater.inflate(R.layout.toolbar_with_back_button, null);
            toolbar_with_back_button.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.toolbar_button_back) {
                    interactionInterface.close();
                }
                return false;
            });
        }

        public Toolbar getToolbar() {
            return toolbar_with_back_button;
        }
    }

    public Toolbar getToolbarWithBackButton() {
        ToolbarFactory toolbarFactory = new ToolbarFactory();
        Thread t = new Thread(toolbarFactory);
        try {
            t.join();
            return toolbarFactory.toolbar_with_back_button;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
