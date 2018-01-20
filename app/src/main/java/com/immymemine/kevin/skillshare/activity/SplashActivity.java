package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.PreferenceUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    // 0. background thread 에서 data 준비하기

    // 1. 사용자 이전에 로그인 이력 있으면 건너뛰기

    // 2. 없으면 splash page 보여주기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = PreferenceUtil.getStringValue(this, ConstantUtil.AUTHORIZATION_FLAG);

        if(!ValidationUtil.isEmpty(token)) {
            RetrofitHelper.createApi(UserService.class)
                    .getMyInfo(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (UserResponse userResponse) -> {
                                if(ConstantUtil.SUCCESS.equals(userResponse.getResult())) {
                                    StateUtil.getInstance().setUserInstance(userResponse.getData());
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    intent.setAction(ConstantUtil.SIGN_IN_SUCCESS);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(this, userResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }, (Throwable error) -> {

                            }
                    );
        } else {
            setContentView(R.layout.activity_splash);

            // glide gif
            // gif 프레임 추출 > 프레임 사이즈 최적화 > 프레임 저장
            Glide.with(this)
                    .load(R.raw.splash)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)) // decoded resource 를 Disk Cache 저장소에 저장해둔다
                    .into((ImageView) findViewById(R.id.splash_image));

            // button get_started
            findViewById(R.id.get_started).setOnClickListener(v -> {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            });

            // textview sign_in
            findViewById(R.id.text_view_sign_in).setOnClickListener(v -> {
                // Sign in page
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            });
        }
    }
}
