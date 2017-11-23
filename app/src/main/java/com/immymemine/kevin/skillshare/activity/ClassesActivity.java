package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.FragmentAdapter;
import com.immymemine.kevin.skillshare.fragment.AboutFragment;
import com.immymemine.kevin.skillshare.fragment.DiscussionsFragment;
import com.immymemine.kevin.skillshare.fragment.LessonsFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassesActivity extends AppCompatActivity {

    // widgets
    VideoView videoView;
    ViewPager tabPager;
    TabLayout tabLayout;

    // fragments
    AboutFragment aboutfragment;
    DiscussionsFragment discussionsfragment;
    LessonsFragment lessonsfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        // 1. Intent 값을 통해 넘어온 data 를 이용해서 서버와 통신

        // 2. model object 에 담아주고

        // 3. view 에서 model object 를 사용

        initiateView();
        setTabLayout();
        setTabPager();
        connectTabAndPager();
    }

    private void initiateView() {
        videoView = findViewById(R.id.video_view);
        tabLayout = findViewById(R.id.tabLayout);
        tabPager = findViewById(R.id.tabPager);
    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("LessonsFragment"));
        tabLayout.addTab(tabLayout.newTab().setText("AboutFragment"));
        tabLayout.addTab(tabLayout.newTab().setText("DiscussionModel"));
    }

    private void setTabPager() {
        List<Fragment> fragmentList = new ArrayList<>();

        aboutfragment = new AboutFragment();
        discussionsfragment = new DiscussionsFragment();
        lessonsfragment = new LessonsFragment();

        fragmentList.add(lessonsfragment);
        fragmentList.add(aboutfragment);
        fragmentList.add(discussionsfragment);

        tabPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
    }

    // 탭 레이아웃과 뷰페이저를 연결한다.
    private void connectTabAndPager() {
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tabPager));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void share(View view) {
        // share button 클릭시
    }

    public void back(View view) {
        // back button 클릭시
    }

    public void subscribe(View view) {
        // subscribe 버튼 클릭시
    }

}