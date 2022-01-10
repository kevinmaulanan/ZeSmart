package com.example.zesmart.auth;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zesmart.DetailMateriActivity;
import com.example.zesmart.ListMateriActivity;
import com.example.zesmart.LoadingClass;
import com.example.zesmart.LoginActivity;
import com.example.zesmart.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth  {
    private FirebaseAuth mAuth;
    private final Activity activity;

    public Auth(Activity myActivity) {
        mAuth = FirebaseAuth.getInstance();
        activity = myActivity;
    }


    public void getUserLogin() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent pindahActivity;
        if (currentUser == null) pindahActivity = new Intent(activity, LoginActivity.class);
        else pindahActivity = new Intent(activity, ListMateriActivity.class);

        activity.startActivity(pindahActivity);;
    }

    public void doLogin(String email, String password) {
        final LoadingClass loadingDialog = new LoadingClass(activity);
        loadingDialog.startLoadingDialog(null);
        try {
            if (email != null && password != null) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingDialog.dissmissDialog();
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.i("Test", String.valueOf(user));
                                    Intent pindahKeHome = new Intent(activity, ListMateriActivity.class);
                                    activity.startActivity(pindahKeHome);
                                } else {
                                    Toast.makeText(activity, "Error Login", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                throw new Error("Email atau Password harus di isi");
            }
        } catch(Exception e) {
            loadingDialog.dissmissDialog();
            throw e;
        }
    }

    public void register(String email, String password) {
        final LoadingClass loadingDialog = new LoadingClass(activity);
        loadingDialog.startLoadingDialog(null);
        try {
            if (email != null && password != null) {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            loadingDialog.dissmissDialog();
                            if (task.isSuccessful()) {
                                Intent pindahKeLogin = new Intent(activity, LoginActivity.class);
                                activity.startActivity(pindahKeLogin);
                            } else {
                                Toast.makeText(activity, "Error Register", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            } else {
                throw new Error("Email atau Password harus di isi");
            }
        } catch(Exception e) {
            loadingDialog.dissmissDialog();
            throw e;
        }
    }
}
