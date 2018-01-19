package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.UserService;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.PreferenceUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignInActivity extends AppCompatActivity{
    // TODO (4) 비밀번호 찾기 >>> EMAIL... 비밀번호 찾는

    // for google sign_in
    GoogleSignInClient mGoogleSignInClient;

    // view
    EditText editTextEmail, editTextPassword;
    Button buttonSignIn, buttonForgotPw;
    TextView warning_email, warning_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initiateView();
        setReactEditText();
        setGoogleSignIn();
    }

    private void initiateView() {
        // back / close 버튼
        findViewById(R.id.toolbar_close_button).setOnClickListener( v ->
            finish()
        );

        // google login 버튼
        findViewById(R.id.google_sign_in).setOnClickListener( v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, ConstantUtil.REQUEST_CODE_SIGN_IN);
        });

        // to Validity check
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        // warning
        warning_email = findViewById(R.id.text_view_warning_email);
        warning_password = findViewById(R.id.text_view_warning_password);
        // email password 조건 충족하면 enabled >> 색 바뀜
        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignIn.setOnClickListener(v -> {
            // String authToken = Credentials.basic(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            if(ValidationUtil.isValidEmailAddress(email)) {
                warning_email.setVisibility(View.INVISIBLE);
                if(ValidationUtil.isValidPassword(password)) {
                    warning_password.setVisibility(View.INVISIBLE);

                    RetrofitHelper
                            .createApi(UserService.class)
                            .signIn(email, password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::handleResponse, this::handleError);
                } else {
                    warning_password.setVisibility(View.VISIBLE);
                }
            } else {
                warning_email.setVisibility(View.VISIBLE);
                warning_password.setVisibility(View.INVISIBLE);
            }
        });

        // 비밀번호 찾기 >>> move Activity or web view , 실제 서비스에서는 web view 를 보여준다.
        buttonForgotPw = findViewById(R.id.button_forgot_pw);
    }

    private void setGoogleSignIn() {
        // DEFAULT_SIGN_IN >>> user id, email, profile 이 포함되어 있다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == ConstantUtil.REQUEST_CODE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account != null) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.setAction(ConstantUtil.SIGN_IN_BY_GOOGLE);
                // 로그인이 성공하면 기존 Activity Stack 에 있는 Activities 종료
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", e.getMessage());
        }
    }

    // rx binding 을 사용해서 edit text 에 text watcher 기능 구현
    private void setReactEditText() {
        Observable<CharSequence> o1 = RxTextView.textChanges(editTextEmail);
        Observable<CharSequence> o2 = RxTextView.textChanges(editTextPassword);

        // Observable 1 과 Observable 2 를 결합해서 결과값을 반환
        Observable.combineLatest(o1, o2,
                (e, p) -> ValidationUtil.isValidEmailAddress(e.toString()) &&
                        ValidationUtil.isValidPassword(p.toString()))
        .subscribe(
                (validity) -> {
                    if(validity) {
                        buttonSignIn.setTextColor(Color.BLACK);
                    } else {
                        buttonSignIn.setTextColor(Color.LTGRAY);
                    }
                }
        );
    }

    private void handleResponse(UserResponse response) {
        if( ConstantUtil.SUCCESS.equals(response.getResult()) ) {
            // user instance manage
            StateUtil state = StateUtil.getInstance();
            state.setUserInstance(response.getData());

            // save token to sharedpreferences
            PreferenceUtil.setStringValue(this, ConstantUtil.AUTHORIZATION_FLAG, response.getToken());

            // intent clear all activity stack and move to MainActivity
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.setAction(ConstantUtil.SIGN_IN_SUCCESS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // activity stack 정리
            startActivity(intent);
        } else {
            Toast.makeText(SignInActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(SignInActivity.this, "error : " + error.toString(), Toast.LENGTH_LONG).show();
    }
}
