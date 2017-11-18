package com.immymemine.kevin.skillshare.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.utility.Const;
import com.immymemine.kevin.skillshare.activity.adapter.GeneralRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.activity.adapter.GroupRecyclerViewAdapter;

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
                    interactionInterface.interact();
                }
            });
        } else if(viewType == Const.GROUP_VIEW) {
            view = inflater.inflate(R.layout.group_view, null);
            // recycler view setting
            recyclerView = view.findViewById(R.id.group_recycler_view);
            recyclerView.setAdapter(new GroupRecyclerViewAdapter(/* data input */));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // title setting
            ((TextView)view.findViewById(R.id.text_view_title2)).setText(title);
        }

        return view;
    }

    public interface InteractionInterface {
        void interact();
    }
}
