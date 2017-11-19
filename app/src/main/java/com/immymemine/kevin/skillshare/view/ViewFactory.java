package com.immymemine.kevin.skillshare.view;

import android.content.Context;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.adapter.GroupRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.utility.Const;

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

        if(context instanceof InteractionInterface) {
            interactionInterface = (InteractionInterface) context;
        }
    }

    // type 에 따라 view 를 생성 / 반환
    public View getViewInstance(int viewType, String title) {
        // main thread 확인 결과 == main 에서 돈다 >>> TODO new thread 에서 돌리고 main 에서 필요한 부분만 main 에서 돌려야 함...
        if(Looper.myLooper() == Looper.getMainLooper()) {
            Log.d("getViewInstance ","main thread 에서 동작합니다.");
        }

        View view = null;
        if(viewType == Const.GENERAL_VIEW) {
            // view initiate
            view = inflater.inflate(R.layout.general_view, null);
            // recycler view setting
            recyclerView = view.findViewById(R.id.general_recycler_view);
            recyclerView.setAdapter(new GeneralRecyclerViewAdapter(/* data input */));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // title setting
            ((TextView)view.findViewById(R.id.text_view_title)).setText(title);
            // button onClickListener setting
            Button see_all_button = view.findViewById(R.id.button_see_all);
            see_all_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // see all page 이동
                }
            });

        } else if(viewType == Const.WELCOME_VIEW) {
            view = inflater.inflate(R.layout.welcome_view, null);
            view.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interactionInterface.close();
                }
            });
        } else if(viewType == Const.GROUP_VIEW) {
            view = inflater.inflate(R.layout.group_view, null);
            // recycler view setting
            recyclerView = view.findViewById(R.id.group_recycler_view);
            recyclerView.setAdapter(new GroupRecyclerViewAdapter(/* data input */));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // title setting
            ((TextView) view.findViewById(R.id.text_view_title2)).setText(title);
        }

        return view;
    }

    public View getYourClassesViewInstance() {
        View view = inflater.inflate(R.layout.your_classes_view, null);
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
    public View getMeViewInstance() {
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

    public View getMeSkillViewInstance() {
        View view = inflater.inflate(R.layout.me_skill_view, null);
        Button personalize = view.findViewById(R.id.personalize);
        personalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interactionInterface.select();
            }
        });

        return view;
    }

    public interface InteractionInterface {
        // welcome view 닫기
        void close();

        // select activity 로 이동
        void select();
    }
}
