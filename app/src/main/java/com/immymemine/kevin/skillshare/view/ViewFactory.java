package com.immymemine.kevin.skillshare.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.SelectSkillsActivity;
import com.immymemine.kevin.skillshare.activity.SignUpActivity;
import com.immymemine.kevin.skillshare.adapter.main_adapter.SkillsRecyclerViewAdapter;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity 에서 사용하는 View Factory
 * Created by quf93 on 2017-11-18.
 */

public class ViewFactory {

    Context context;
    LayoutInflater inflater;

    // Singleton
    private static ViewFactory instance;

    private ViewFactory(Context context) {
        // context
        this.context = context;
        // inflater
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public View getWelcomeView() {
        View view = inflater.inflate(R.layout.welcome_view, null);
        view.findViewById(R.id.button_sign_up).setOnClickListener(v -> context.startActivity(new Intent(context, SignUpActivity.class)));
        return view;
    }

    public View getMeSkillView(List<String> skills) {
        View view = inflater.inflate(R.layout.me_skill_view,null);

        RecyclerView recyclerViewSkills = view.findViewById(R.id.recycler_view_skills);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        recyclerViewSkills.setLayoutManager(layoutManager);
        recyclerViewSkills.setAdapter(new SkillsRecyclerViewAdapter(context, skills));

        view.findViewById(R.id.personalize).setOnClickListener(
                v -> {
                    Intent intent = new Intent(context, SelectSkillsActivity.class);
                    if (skills != null)
                        intent.putStringArrayListExtra(ConstantUtil.SKILLS_FLAG, (ArrayList<String>) skills);
                    ((AppCompatActivity)context).startActivityForResult(intent, ConstantUtil.SELECT_SKILLS_REQUEST_CODE);
                }
        );

        if (skills == null || skills.size() == 0)
            view.findViewById(R.id.divider).setVisibility(View.GONE);
        else
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);

        return view;
    }
}
