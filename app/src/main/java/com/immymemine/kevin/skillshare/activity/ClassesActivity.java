package com.immymemine.kevin.skillshare.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.immymemine.kevin.skillshare.adapter.FragmentAdapter;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.fragment.AboutFragment;
import com.immymemine.kevin.skillshare.fragment.DiscussionsFragment;
import com.immymemine.kevin.skillshare.fragment.LessonsFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassesActivity extends AppCompatActivity {

    ViewPager tabPager;
    TabLayout tabLayout;
    AboutFragment aboutfragment;
    DiscussionsFragment discussionsfragment;
    LessonsFragment lessonsfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        init();
        setTabLayout();
        initTabPager();
        connectTabAndPager();
    }


    private void init() {
        tabLayout = findViewById(R.id.tabLayout);
        tabPager = findViewById(R.id.tabPager);

    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("LessonsFragment"));
        tabLayout.addTab(tabLayout.newTab().setText("AboutFragment"));
        tabLayout.addTab(tabLayout.newTab().setText("DiscussionModel"));
    }

    private void initTabPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        aboutfragment = new AboutFragment();
        discussionsfragment = new DiscussionsFragment();
        lessonsfragment = new LessonsFragment();
        fragmentList.add(lessonsfragment);
        fragmentList.add(aboutfragment);
        fragmentList.add(discussionsfragment);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        tabPager.setAdapter(adapter);

    }

    void connectTabAndPager() {// 탭 레이아웃과 뷰페이저를 연결한다.
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tabPager));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}