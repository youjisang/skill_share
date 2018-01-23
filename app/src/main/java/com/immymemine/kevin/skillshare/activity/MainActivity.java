package com.immymemine.kevin.skillshare.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        // state
        stateUtil = StateUtil.getInstance(); // user state check util
        setState();

        // view
        initiateView(); // initiate view
        setBottomNavigation(); // navigation view setting

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

    List<Fragment> fragments = new ArrayList<>();
    int stack = 0;

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

        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.BottomNaviNotiBackground));

        bottomNavigation.setOnTabSelectedListener((int position, boolean wasSelected) -> {
            switch (position) {
                case 0:
                    if (wasSelected) {
                        Log.d("JUWONLEE", "0 was selected");
                        return false;
                    } else {
                        if(homeFragment == null) {
                            Log.d("JUWONLEE", "0 null");
                            homeFragment = new HomeFragment();

                            fragments.add(homeFragment);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, homeFragment)
                                    .commit();

                            stack = fragments.indexOf(homeFragment);
                        } else if(fragments.contains(homeFragment)) {
                            Log.d("JUWONLEE", "0 contains");
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .show(homeFragment)
                                    .commit();

                            stack = fragments.indexOf(homeFragment);
                        } else {
                            Log.d("JUWONLEE", "0 else");
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .show(homeFragment)
                                    .commit();

                            stack = fragments.indexOf(homeFragment);
                        }
                        return true;
                    }
                case 1:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(groupFragment == null) {
                            Log.d("JUWONLEE", "1 null");
                            groupFragment = new GroupFragment();

                            fragments.add(groupFragment);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .add(R.id.fragment_container, groupFragment)
                                    .commit();

                            stack = fragments.indexOf(groupFragment);
                        } else if(!fragments.contains(groupFragment)) {
                            Log.d("JUWONLEE", "1 not contains");
                            fragments.add(groupFragment);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .add(R.id.fragment_container, groupFragment)
                                    .commit();

                            stack = fragments.indexOf(groupFragment);
                        } else {
                            Log.d("JUWONLEE", "1 else");
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .show(groupFragment)
                                    .commit();

                            stack = fragments.indexOf(groupFragment);
                        }
                        return true;
                    }
                case 2:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(discoverFragment == null) {
                            Log.d("JUWONLEE", "2 null");
                            discoverFragment = new DiscoverFragment();

                            fragments.add(discoverFragment);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .add(R.id.fragment_container, discoverFragment)
                                    .commit();

                            stack = fragments.indexOf(discoverFragment);
                        } else {
                            Log.d("JUWONLEE", "2 else");

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .show(discoverFragment)
                                    .commit();

                            stack = fragments.indexOf(discoverFragment);
                        }
                        return true;
                    }
                case 3:
                    if (wasSelected) {
                        return false;
                    } else {
                        if(yourClassesFragment == null) {
                            Log.d("JUWONLEE", "3 null");

                            yourClassesFragment = new YourClassesFragment();

                            fragments.add(yourClassesFragment);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .add(R.id.fragment_container, yourClassesFragment)
                                    .commit();

                            stack = fragments.indexOf(discoverFragment);
                        } else {
                            Log.d("JUWONLEE", "3 else");

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragments.get(stack))
                                    .show(yourClassesFragment)
                                    .commit();

                            stack = fragments.indexOf(yourClassesFragment);
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

                                fragments.add(meFragment);

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .hide(fragments.get(stack))
                                        .add(R.id.fragment_container, meFragment)
                                        .commit();

                                stack = fragments.indexOf(meFragment);
                            } else {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .hide(fragments.get(stack))
                                        .show(meFragment)
                                        .commit();

                                stack = fragments.indexOf(meFragment);
                            }

                        } else {
                            if(offlineMeFragment == null) {
                                offlineMeFragment = new OfflineMeFragment();

                                fragments.add(offlineMeFragment);

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .hide(fragments.get(stack))
                                        .add(R.id.fragment_container, offlineMeFragment)
                                        .commit();

                                stack = fragments.indexOf(offlineMeFragment);
                            } else {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .hide(fragments.get(stack))
                                        .show(offlineMeFragment)
                                        .commit();

                                stack = fragments.indexOf(offlineMeFragment);
                            }
                        }
                        return true;
                    }
            }
            return false;
        });

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);
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
        Log.d("JUWONLEE","MainActivity result");
        if (resultCode == RESULT_OK) {
            Log.d("JUWONLEE","MainActivity result ok");
            if(requestCode == ConstantUtil.INIT_SKILLS_REQUEST_CODE) {
                List<String> skills = data.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);

                RetrofitHelper.createApi(UserService.class)
                        .followSkills(user.get_id(), skills)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (Response response) -> {
                                    if(ConstantUtil.SUCCESS.equals(response.getResult())) {
                                        user.setFollowingSkills(skills);
                                    }
                                }, (Throwable error) -> {

                                }
                        );
            }
        }
    }

}


