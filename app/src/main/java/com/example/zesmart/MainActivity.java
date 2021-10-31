package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent redirect;
            SharedPreferences sharedPref = getSharedPreferences("MyPref", 0);
            String checkOnBoarding = sharedPref.getString("onBoarding", null);
            if (TextUtils.equals(checkOnBoarding, "true")) {
                redirect = new Intent(MainActivity.this, LoginActivity.class);
            } else {
                redirect = new Intent(MainActivity.this, OnBoardingActivity.class);
            }

            startActivity(redirect);
            finish();
        }, 3000);
    }
}