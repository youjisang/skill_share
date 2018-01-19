package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.network.user.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.PreferenceUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    Button googleSignUp;
    EditText editTextFirstName, editTextLastName, editTextEmailAddress, editTextPassword;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initiateView();
    }

    private void initiateView() {
        // 닫는 버튼
        findViewById(R.id.toolbar_close_button).setOnClickListener(
                v -> finish()
        );

        googleSignUp = findViewById(R.id.google_sign_up);

        editTextFirstName= findViewById(R.id.edit_text_first_name);
        editTextLastName = findViewById(R.id.edit_text_last_name);
        editTextEmailAddress = findViewById(R.id.edit_text_email_address);
        editTextPassword = findViewById(R.id.edit_text_password);

        findViewById(R.id.button_sign_up).setOnClickListener(
                v -> {
                    // progress bar
                    String email = editTextEmailAddress.getText().toString();
                    String password = editTextPassword.getText().toString();
                    String first_name =  editTextFirstName.getText().toString();
                    String last_name = editTextLastName.getText().toString();
                    if( !ValidationUtil.isValidEmailAddress(email) ) {
                        // email 형식이 틀렸습니다
                    } else if( !ValidationUtil.isValidPassword(password) ) {
                        // password 를 올바르게 입력해주세요 8 - 16 자
                    } else if ( ValidationUtil.isEmpty(first_name) || ValidationUtil.isEmpty(last_name) ) {
                        // 이름을 입력해주세요
                    } else {
                        // request body setting
                        SignUpRequestBody signUpRequestBody = new SignUpRequestBody();
                        signUpRequestBody.setEmail(email);
                        signUpRequestBody.setPassword(password);
                        name = first_name+" "+last_name;
                        signUpRequestBody.setName(first_name+" "+last_name);

                        // retrofit
                        RetrofitHelper.createApi(UserService.class).signUp(signUpRequestBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::handleResponse, this::handleError);
                    }
                }
        );
    }

    private void handleResponse(UserResponse response) {
        if(ConstantUtil.SUCCESS.equals(response.getResult())) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // activity stack 정리
            intent.setAction(ConstantUtil.SIGN_UP_SUCCESS); // 회원 가입 성공 Action >>> Main 에서 처리 ( follow skills 띄우기 )
            StateUtil.getInstance().setUserInstance(response.getData());
            PreferenceUtil.setStringValue(this, ConstantUtil.AUTHORIZATION_FLAG, response.getToken());
            startActivity(intent);
        } else {
            if(response.getMessage().equals(ConstantUtil.ALREADY_EXISTED_EMAIL)) {
                // 경고 메시지 보여주기 <<< 중복된 이메일 있다 바꿔라
            }
            Toast.makeText(SignUpActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}