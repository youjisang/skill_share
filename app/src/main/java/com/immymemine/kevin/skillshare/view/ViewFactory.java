package com.immymemine.kevin.skillshare.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;

import com.google.android.flexbox.FlexboxLayoutManager;

import com.immymemine.kevin.skillshare.R;

import com.immymemine.kevin.skillshare.activity.GroupActivity;
import com.immymemine.kevin.skillshare.activity.MainActivity;
import com.immymemine.kevin.skillshare.activity.SavedActivity;
import com.immymemine.kevin.skillshare.adapter.DiscoverRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GetMeViewRecylerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GroupRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.model.dummy.dummyDataForGroup;
import com.immymemine.kevin.skillshare.model.home.Class;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;


import java.util.ArrayList;

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
    RecyclerView recyclerView, selectRecyclerView;
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

    //TODO 이 부분까지는 시스템 자원과 싱글톤 세팅. 인터페이스로 각 뷰와 연결되는 와중이므로..

    public LinearLayout getViewContainer() {
        LinearLayout view_container = (LinearLayout) inflater.inflate(R.layout.container, null);
        return view_container;
    }

    //TODO 이 부분은 view container로 그냥 뷰들을 붙이기 위한 도화지 같은 역할

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
            Button see_all_button = view.findViewById(R.id.button_see_all);

            see_all_button.setOnClickListener(v -> {
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

        /* TODO 지상
    ExecutorService, collable -작업 완료 후 리턴값이 있음, future- 객체는 작업이 완료될때까지 기다렸다가 최종 결과를 얻는데 사용
    일단 유연하고 효과적인 비동기 작업을 위해 사용한다고 이해.
    쓰레드 그룹과 쓰레드 풀 차이?
     */

    class GroupViewFactory implements Callable<View> {
        String title;
        List<dummyDataForGroup> groupList;


        public GroupViewFactory(String title, List<dummyDataForGroup> groupList) {
            this.title = title;
            this.groupList = groupList;
        }


        @Override
        public View call() throws Exception {
            View view = inflater.inflate(R.layout.group_view, null);

            // recycler view setting
            recyclerView = view.findViewById(R.id.group_recycler_view);

            recyclerView.setAdapter(new GroupRecyclerViewAdapter(groupList, context, groupList.size()));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


            // title setting
            ((TextView) view.findViewById(R.id.text_view_title2)).setText(title);

            return view;
        }
    }

    public View getGroupView(String title, List<dummyDataForGroup> list) {
        Future<View> f = executor.submit(new GroupViewFactory(title, list));


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
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new DiscoverRecyclerViewAdapter());
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

    String id;

    public View getYourClassesView() {
        Future<View> f = executor.submit(
                () -> {
                    View view = inflater.inflate(R.layout.your_classes_view, null);
                    ImageView your_classes_video_thumbnail = view.findViewById(R.id._your_classes_video_thumbnail);
                    your_classes_video_thumbnail.setOnClickListener(v -> {
//                        interactionInterface.seeAll(id);

                        // TODO 지상 클릭 이벤트 수정-------------------------------------------------
                        Intent intent = new Intent(context, SavedActivity.class);
                        v.getContext().startActivity(intent);

                        // ----------------------------------------------------------
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

    public View getMeView() {
        Future<View> f = executor.submit(
                () -> {
                    // main thread 에서 굳이 해주지 않아도 된다
                    View view = inflater.inflate(R.layout.me_view, null);

                    // 이름, 닉네임 세팅
                    ((TextView) view.findViewById(R.id.me_name)).setText("name");
                    ((TextView) view.findViewById(R.id.me_nickname)).setText("@nickname");

                    // followers, following <<< onClick setting...
                    ((TextView) view.findViewById(R.id.me_followers)).setText(/*number + */1 + " Followers");
                    ((TextView) view.findViewById(R.id.me_following)).setText("Following " +/*number + */2);

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


    public View getMeSkillView(ArrayList<String> selectData) {

        Future<View> f = executor.submit(
                () -> {
                    View view = inflater.inflate(R.layout.me_skill_view, null);
                    Button personalize = view.findViewById(R.id.personalize);
                    personalize.setOnClickListener(v -> interactionInterface.select());
                    selectRecyclerView = view.findViewById(R.id.recycler_vIew_selectSkill);
                    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
                    layoutManager.setFlexDirection(FlexDirection.ROW);

                    selectRecyclerView.setLayoutManager(layoutManager);
                    selectRecyclerView.setAdapter(new GetMeViewRecylerViewAdapter(context, selectData));

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
