package com.immymemine.kevin.skillshare.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GroupRecyclerViewAdapter;

/**
 * Created by quf93 on 2017-11-18.
 */

public class ViewFactory {
    Context context;
    LayoutInflater inflater;
    RecyclerView recyclerView;
    InteractionInterface interactionInterface;
    public ViewFactory(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(context instanceof ViewInteractionInterface) {
            interactionInterface = (ViewInteractionInterface) context;
        } else if (context instanceof BackButtonInterface) {
            interactionInterface = (BackButtonInterface) context;
        }
    }

    class WelcomeViewFactory implements Runnable {
        View view;
        @Override
        public void run() {
            view = inflater.inflate(R.layout.welcome_view, null);
            view.findViewById(R.id.close_button).setOnClickListener(v -> ((ViewInteractionInterface)interactionInterface).close());
        }

        public View getView() {
            return view;
        }
    }

    public View getWelcomeView() {
        WelcomeViewFactory welcomeViewFactory = new WelcomeViewFactory();
        Thread t = new Thread(welcomeViewFactory);
        t.start();

        try {
            t.join();
            return welcomeViewFactory.getView();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    class GeneralViewFactory implements Runnable {
        View view;
        String title;
        public GeneralViewFactory(String title) {
            this.title = title;
        }

        @Override
        public void run() {
            view = inflater.inflate(R.layout.general_view, null);
            // recycler view setting
            recyclerView = view.findViewById(R.id.general_recycler_view);
            recyclerView.setAdapter(new GeneralRecyclerViewAdapter(/* data input */ context));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // title setting
            ((TextView)view.findViewById(R.id.text_view_title)).setText(title);
            // button onClickListener setting
            Button see_all_button = view.findViewById(R.id.button_see_all);
            see_all_button.setOnClickListener(v -> {
                // see all page 이동
                ((ViewInteractionInterface)interactionInterface).seeAll(title);
            });
        }

        public View getView() {
            return view;
        }
    }

    public View getGeneralView(String title) {
        GeneralViewFactory generalViewFactory = new GeneralViewFactory(title);
        Thread t = new Thread(generalViewFactory);
        t.start();

        try {
            t.join();
            return generalViewFactory.getView();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    class GroupViewFactory implements Runnable {
        View view;
        String title;
        public GroupViewFactory(String title) {
            this.title = title;
        }

        @Override
        public void run() {
            view = inflater.inflate(R.layout.group_view, null);
            // recycler view setting
            recyclerView = view.findViewById(R.id.group_recycler_view);
            recyclerView.setAdapter(new GroupRecyclerViewAdapter(/* data input */ context));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // title setting
            ((TextView) view.findViewById(R.id.text_view_title2)).setText(title);
        }

        public View getView() {
            return view;
        }
    }

    public View getGroupView(String title) {
        GroupViewFactory groupViewFactory = new GroupViewFactory(title);
        Thread t = new Thread(groupViewFactory);
        t.start();

        try {
            t.join();
            return groupViewFactory.getView();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getYourClassesView() {
        View view = inflater.inflate(R.layout.your_classes_view, null);

        // main 영역
        // video thumbnail setting
        // ImageView video_thumbnail = view.findViewById(R.id._your_classes_video_thumbnail);
        // Glide.with(context).load(/*thumbnail*/).into(video_thumbnail);
        return view;
    }

    class MeViewFactory implements Runnable {
        private View view;
        @Override
        public void run() {
            // main thread 에서 굳이 해주지 않아도 된다
            view = inflater.inflate(R.layout.me_view, null);

            // main thread 영역 ------------------------------------------------------------
            // rounding image setting
            // ImageView me_image = view.findViewById(R.id.me_image);
            // Glide.with(context).load(R.drawable.ic_launcher_background).apply(RequestOptions.circleCropTransform()).into(me_image);
            // -----------------------------------------------------------------------------

            // 이게 왜 sub thread 에서 돌아가지?..............???????????
            // 이름, 닉네임 세팅
            ((TextView)view.findViewById(R.id.me_name)).setText("My name");
            ((TextView)view.findViewById(R.id.me_nickname)).setText("@nickname");
            // followers, following <<< onClick setting...
            ((TextView)view.findViewById(R.id.me_followers)).setText(/*number + */1+" Followers");
            ((TextView)view.findViewById(R.id.me_following)).setText("Following "+/*number + */2);
        }

        public View getView() {
            return view;
        }
    }

    public View getMeView() {
        MeViewFactory me_view_factory = new MeViewFactory();
        Thread t = new Thread(me_view_factory);
        t.start();

        try {
            t.join();
            return me_view_factory.getView();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    class MeSkillViewFactory implements Runnable {
        View view;
        @Override
        public void run() {
            view = inflater.inflate(R.layout.me_skill_view, null);
            Button personalize = view.findViewById(R.id.personalize);
            personalize.setOnClickListener(view -> ((ViewInteractionInterface)interactionInterface).select());
        }

        public View getView() {
            return view;
        }
    }

    public View getMeSkillView() {
        MeSkillViewFactory meSkillViewFactory = new MeSkillViewFactory();
        Thread t = new Thread(meSkillViewFactory);
        t.start();

        try {
            t.join();
            return meSkillViewFactory.getView();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    class ToolbarFactory implements Runnable {
        Toolbar toolbar_with_back_button;
        @Override
        public void run() {
            toolbar_with_back_button = (Toolbar) inflater.inflate(R.layout.toolbar_with_back_button, null);
            toolbar_with_back_button.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.toolbar_button_back) {
                    ((BackButtonInterface)interactionInterface).closeActivity();
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

    public interface ViewInteractionInterface extends InteractionInterface {
        // welcome view 닫기
        void close();

        // select activity 로 이동
        void select();

        // see all activity 이동
        void seeAll(String title);
    }

    public interface BackButtonInterface extends InteractionInterface{
        // back button >>> close Activity
        void closeActivity();
    }
}
