package com.example.zesmart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.view.LayoutInflater;

public class LoadingClass {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingClass(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoadingDialog(Runnable runnable) {
        try {
            if (runnable != null) runnable.run();

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.custom_dialog, null));
            builder.setCancelable(true);
            dialog = builder.create();
            dialog.show();


        } catch (Exception e) {
            throw e;
        }

    }

    public void dissmissDialog() {
        dialog.dismiss();
    }
}
