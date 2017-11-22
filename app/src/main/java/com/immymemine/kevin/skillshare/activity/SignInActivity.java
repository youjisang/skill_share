package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;

public class SignInActivity extends AppCompatActivity{
    // Complete (1) Google login 연동
    // Complete (2) validation check util
    // TODO (3) login 처리
    // TODO (4) 비밀번호 찾기 >>> EMAIL... 비밀번호 찾는

    // for google sign_in
    public static GoogleSignInClient mGoogleSignInClient;

    ImageButton toolbarCloseButton;
    SignInButton googleSignIn;
    EditText editTextEmail, editTextPassword;
    Button buttonSignIn, buttonForgotPw;

    private static final int RC_SIGN_IN = 239;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initiateView();
        setGoogleSignIn();

        setReactEditText();
    }

    private void initiateView() {
        // back / close 버튼
        toolbarCloseButton = findViewById(R.id.toolbar_close_button);
        findViewById(R.id.toolbar_close_button).setOnClickListener( v -> {
            finish();
        });

        // google login 버튼
        googleSignIn = findViewById(R.id.google_sign_in);
        findViewById(R.id.google_sign_in).setOnClickListener( v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        // Validity check
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        // email password 조건 충족하면 enabled >> 색 바뀜
        buttonSignIn = findViewById(R.id.button_sign_in);

        // 비밀번호 찾기 >>> move Activity 실제 서비스에서는 web view 를 보여준다.
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
        if (requestCode == RC_SIGN_IN) {
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
                // 로그인이 성공하면 Splash Activity 와 SignIn Activity 를 종료시켜준다.
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Account null error", Toast.LENGTH_LONG).show();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    // rx binding 을 사용해서 edit text 에 text watcher 기능 구현
    private void setReactEditText() {
        Observable<CharSequence> o1 = RxTextView.textChanges(editTextEmail);
        Observable<CharSequence> o2 = RxTextView.textChanges(editTextPassword);

        // Observable 1 과 Observable 2 를 결합해서 결과값을 반환
        Observable.combineLatest(o1, o2, (e, p) -> ValidationUtil.isValidEmailAddress(e.toString()) && ValidationUtil.isValidPassword(p.toString()))
        .subscribe(
                (validity) -> {
                    if(validity) {
                        buttonSignIn.setEnabled(true);
                        buttonSignIn.setTextColor(Color.BLACK);
                    } else {
                        buttonSignIn.setEnabled(false);
                        buttonSignIn.setTextColor(Color.LTGRAY);
                    }
                }
        );
    }
}
