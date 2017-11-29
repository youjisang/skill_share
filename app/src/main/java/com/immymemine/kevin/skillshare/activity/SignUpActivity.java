package com.immymemine.kevin.skillshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.network.sign_up.SignUpInterface;
import com.immymemine.kevin.skillshare.network.sign_up.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.sign_up.SignUpResponseBody;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    Button googleSignUp;
    EditText editTextFirstName, editTextLastName, editTextEmailAddress, editTextPassword;

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
                        signUpRequestBody.setName(first_name+" "+last_name);

                        // retrofit
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ConstantUtil.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        SignUpInterface signUpInterface = retrofit.create(SignUpInterface.class);
                        Call<SignUpResponseBody> call = signUpInterface.signUpUser(signUpRequestBody);
                        call.enqueue(new Callback<SignUpResponseBody>() {
                            @Override
                            public void onResponse(Call<SignUpResponseBody> call, Response<SignUpResponseBody> response) {
                                SignUpResponseBody signUpResponseBody = response.body();
                                if( "failure".equals(signUpResponseBody.getResult()) ) {
                                    Toast.makeText(SignUpActivity.this, signUpResponseBody.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //
                                    intent.setAction(ConstantUtil.SIGN_UP_SUCCESS); // 회원 가입 성공 Action >>> Main 에서 처리 ( follow skills 띄우기 )
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<SignUpResponseBody> call, Throwable t) {
                                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        );

    }
}
