package com.immymemine.kevin.skillshare.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.fragment.main_f.DiscoverFragment;
import com.immymemine.kevin.skillshare.fragment.main_f.GroupFragment;
import com.immymemine.kevin.skillshare.fragment.main_f.HomeFragment;
import com.immymemine.kevin.skillshare.fragment.main_f.MeFragment;
import com.immymemine.kevin.skillshare.fragment.main_f.OfflineMeFragment;
import com.immymemine.kevin.skillshare.fragment.main_f.YourClassesFragment;
import com.immymemine.kevin.skillshare.gcm.RegistrationIntentService;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AHBottomNavigation bottomNavigation; // bottom navigation view
    ProgressBar progressBar; // progress bar

    // google sign in / out
    GoogleApiClient mGoogleApiClient;
    GoogleSignInAccount account;

    // user
    StateUtil stateUtil;
    User user;

    // user followed skills
    List<String> followSkills = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // view
        initiateView(); // initiate view
        setBottomNavigation(); // navigation view setting

        stateUtil = StateUtil.getInstance(); // user state check util
        setState();

        // BroadCast Receiver 등록 for gcm
        registerReceiver();
        startRegisterService();
    }

    private void setState() {
        // 로그인 상태 확인
        Intent intent = getIntent();

        if (stateUtil.getState()) {
            switch (intent.getAction()) {
                case ConstantUtil.SIGN_IN_SUCCESS:
                    break;
                case ConstantUtil.SIGN_IN_BY_GOOGLE:
                    account = GoogleSignIn.getLastSignedInAccount(this);
                    break;
                case ConstantUtil.SIGN_UP_SUCCESS:
                    user = stateUtil.getUserInstance();
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.INIT_SKILLS_REQUEST_CODE);
                    break;
                case ConstantUtil.SIGN_UP_BY_GOOGLE:
                    startActivityForResult(new Intent(MainActivity.this, SelectSkillsActivity.class), ConstantUtil.INIT_SKILLS_REQUEST_CODE);
                    account = GoogleSignIn.getLastSignedInAccount(this);
                    break;
                case ConstantUtil.SIGN_OUT:
                    stateUtil.setUserInstance(null);
                    bottomNavigation.setCurrentItem(4);
                    break;
            }
        }
    }

    HomeFragment homeFragment;
    GroupFragment groupFragment;
    DiscoverFragment discoverFragment;
    YourClassesFragment yourClassesFragment;
    MeFragment meFragment;
    OfflineMeFragment offlineMeFragment;

    private void initiateView() {
        progressBar = findViewById(R.id.progress_bar);
    }

    // 네비게이션바
    private void setBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Add items <<< created items (R.string.TITLE, R.drawable.ICON_IMG, R.color.NAVIGATION_BAR_BACKGROUND_COLOR)
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_home, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.groups, R.drawable.ic_group, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.discover, R.drawable.ic_discover, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.your_classes, R.drawable.ic_list, R.color.BottomNaviBackground));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.me, R.drawable.ic_profile, R.color.BottomNaviBackground));

        // Set Navigation Icon & Background Colors
        bottomNavigation.setColoredModeColors(getResources().getColor(R.color.IcActive), getResources().getColor(R.color.IcInactive));
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));

        // Change colors
//        bottomNavigation.setAccentColor(getResources().getColor(R.color.IcActive));
//        bottomNavigation.setInactiveColor(getResources().getColor(R.color.IcInactive));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Display color under navigation bar (API 21+)
        bottomNavigation.setTranslucentNavigationEnabled(true);

        // Manage titles (SHOW_WHEN_ACTIVE, ALWAYS_SHOW, ALWAYS_HIDE)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.BottomNaviNotiBackground));

        bottomNavigation.setOnTabSelectedListener((int position, boolean wasSelected) -> {
            switch (position) {
                case 0:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(homeFragment == null) {
                            homeFragment = new HomeFragment();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, homeFragment)
                                    .commit();
                            Log.e("Fragment Check","home1");
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("show", false);
                            homeFragment.setArguments(bundle);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, homeFragment)
                                    .commit();
                            Log.e("Fragment Check","home2");
                        }

                        return true;
                    }
                case 1:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(groupFragment == null) {
                            groupFragment = new GroupFragment();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, groupFragment)
                                    .commit();
                            Log.e("Fragment Check","group1");
                        } else {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, groupFragment)
                                    .commit();
                            Log.e("Fragment Check","group2");
                        }
                        return true;
                    }
                case 2:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(discoverFragment == null) {
                            discoverFragment = new DiscoverFragment();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, discoverFragment)
                                    .commit();

                            Log.e("Fragment Check","dis1");
                        } else {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, discoverFragment)
                                    .commit();
                            Log.e("Fragment Check","dis2");
                        }
                        return true;
                    }
                case 3:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(yourClassesFragment == null) {
                            yourClassesFragment = new YourClassesFragment();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, yourClassesFragment)
                                    .commit();
                            Log.e("Fragment Check","yourClass1");
                        } else {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, yourClassesFragment)
                                    .commit();
                            Log.e("Fragment Check","yourClass2");
                        }

                        return true;
                    }
                case 4:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(stateUtil.getState()) {
                            if(meFragment == null) {
                                meFragment = new MeFragment();

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.fragment_container, meFragment)
                                        .commit();
                                Log.e("Fragment Check","meFrag1");
                            } else {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, meFragment)
                                        .commit();
                                Log.e("Fragment Check","meFrag2");
                            }

                        } else {
                            if(offlineMeFragment == null) {
                                offlineMeFragment = new OfflineMeFragment();

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.fragment_container, offlineMeFragment)
                                        .commit();
                            } else {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, offlineMeFragment)
                                        .commit();
                            }
                        }
                        return true;
                    }
            }
            return false;
        });
    }

    // Broad Cast Receiver
    private void registerReceiver() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantUtil.RECEIVED);
        localBroadcastManager.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConstantUtil.RECEIVED)) {
                    // notification 받으면 bottom navigation view 에 noti 달기
                    bottomNavigation.setNotification(" ", 1);
                }
            }
        }, intentFilter);
    }

    private void startRegisterService() {
        if(stateUtil.getState()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("USER_ID", stateUtil.getUserInstance().get_id());
            startService(intent);
        }
    }

    public void signOut() {
//        if(isSignIn) {
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
//                // TODO me page 변경 >>> 1. sign in / sign up 2. token 삭제
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                intent.setAction("SIGN_OUT");
//                startActivity(intent);
//                finish();
//            });
//        }

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setAction(ConstantUtil.SIGN_OUT);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ConstantUtil.SELECT_SKILLS_REQUEST_CODE) {
                List<String> skills = data.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);
                user.setFollowingSkills(skills);
            } else if(requestCode == ConstantUtil.INIT_SKILLS_REQUEST_CODE) {
                List<String> skills = data.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);
                user.setFollowingSkills(skills);

                // TODO ConstantUtil 에서 쓰는 것들 삭제...
                followSkills.add(ConstantUtil.FEATURED_ON_SKILLSHARE);
                followSkills.addAll(skills);
                followSkills.add(ConstantUtil.TRENDING_NOW);
                followSkills.add(ConstantUtil.BEST_THIS_MONTH);
            }
        }
    }
}


