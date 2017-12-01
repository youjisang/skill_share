package com.immymemine.kevin.skillshare.utility;

import android.content.Context;
import android.support.v7.app.AlertDialog;

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
}
