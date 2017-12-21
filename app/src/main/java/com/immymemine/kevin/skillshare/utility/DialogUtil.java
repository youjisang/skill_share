package com.immymemine.kevin.skillshare.utility;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.MainActivity;
import com.immymemine.kevin.skillshare.activity.SignInActivity;
import com.immymemine.kevin.skillshare.activity.SignUpActivity;
import com.jakewharton.rxbinding2.widget.RxTextView;

/**
 * Created by quf93 on 2017-11-26.
 */

public class DialogUtil {
    // TODO custom dialog
    private void showAlertDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void showCreateGroupDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.create_group_dialog, null, false);
        int limit = 40;
        ImageButton toolbarCloseButton = view.findViewById(R.id.toolbar_close_button);
        EditText editTextGroupName = view.findViewById(R.id.edit_text_group_name);
        TextView textViewCharactersLeft = view.findViewById(R.id.text_view_characters_left);

        RxTextView.textChanges(editTextGroupName)
                .subscribe(charSequence -> {
                    int left = limit - charSequence.toString().length();
                    textViewCharactersLeft.setText(left + " characters left");

                    if(left < 40) {
                        // button enabled
                    } else {
                        // disabled
                    }
                });

        builder.setView(view);
        builder.show();
    }

    public static void searchAlertDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.show();
    }



    //TODO 지상 회원가입 및 로그인이 안되어있을 때, 다이얼로그 처리
    public static void showGroupDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_group_sign_out,null,false);
        Button button_sign_in = view.findViewById(R.id.button_sign_In);
        Button button_sign_up = view.findViewById(R.id.button_sign_up);
        button_sign_in.setOnClickListener(v -> view.getContext().startActivity(new Intent(context,SignInActivity.class)));
        button_sign_up.setOnClickListener(v -> view.getContext().startActivity(new Intent(context,SignUpActivity.class)));
        builder.setView(view);
        builder.show();
    }
}
