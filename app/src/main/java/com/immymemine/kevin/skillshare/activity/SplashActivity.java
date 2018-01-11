package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.fragment.main_f.MeFragment;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.PreferenceUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;

public class SplashActivity extends AppCompatActivity {

    // 0. background thread 에서 data 준비하기

    // 1. 사용자 이전에 로그인 이력 있으면 건너뛰기

    // 2. 없으면 splash page 보여주기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //TODO 자상
//        autoLogin();

        // glide gif
        // gif 프레임 추출 > 프레임 사이즈 최적화 > 프레임 저장
        Glide.with(this)
                .load(R.raw.splash)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)) // decoded resource 를 Disk Cache 저장소에 저장해둔다
                .into((ImageView) findViewById(R.id.splash_image));

        // button get_started
        findViewById(R.id.get_started).setOnClickListener(v -> {
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            new Thread() {
                public void run() {
                    // data 가져오기
                    // RetrofitHelper.createApi()... 끝나면 넘어가기

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }.start();
        });

        // textview sign_in
        findViewById(R.id.text_view_sign_in).setOnClickListener(v -> {
            // Sign in page
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
        });
    }

    @Override
    protected void onResume() {
        // sign_in activity 로 갔다가 다시 돌아왔을 때
        // progress bar >>> GONE
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        super.onResume();
    }


    //TODO 지상 자동로그인 처리
    String success;
    String userId;
    StateUtil state = StateUtil.getInstance();

    UserResponse response;

    private void autoLogin() {



//        if (PreferenceUtil.getString(this, "auto_sign").equals("true")) {
//            state.setState(true);
//            state.setUserInstance(response.getUser());
//            Intent intent = new Intent(this, MainActivity.class);
//            success = PreferenceUtil.getString(this, "success");
//            userId = PreferenceUtil.getString(this, ConstantUtil.USER_ID_FLAG);
//            Log.e("success","check success = "+success);
//            Log.e("userId","check userId = "+userId);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
    }
}
