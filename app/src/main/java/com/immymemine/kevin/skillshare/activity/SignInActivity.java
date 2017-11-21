package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.SignInButton;
import com.immymemine.kevin.skillshare.R;

public class SignInActivity extends AppCompatActivity{

    ImageButton toolbarCloseButton;
    SignInButton googleSignIn;
    EditText editTextEmail, editTextPassword;
    Button buttonSignIn, buttonForgotPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initiateView();
    }

    // TODO (1) Google login 연동
    // TODO (2) validation check util
    // TODO (3) login 처리
    // TODO (4) 비밀번호 찾기 >>> EMAIL... 비밀번호 찾는

    private void initiateView() {
        // back / close 버튼
        toolbarCloseButton = findViewById(R.id.toolbar_close_button);
        toolbarCloseButton.setOnClickListener( v -> {
            finish();
        });

        // google login 버튼
        googleSignIn = findViewById(R.id.google_sign_in);

        // Validity check
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        // email password 조건 충족하면 enabled >> 색 바뀜
        buttonSignIn = findViewById(R.id.button_sign_in);

        // 비밀번호 찾기 >>> move Activity 실제 서비스에서는 web view 를 보여준다.
        buttonForgotPw = findViewById(R.id.button_forgot_pw);
    }
}
