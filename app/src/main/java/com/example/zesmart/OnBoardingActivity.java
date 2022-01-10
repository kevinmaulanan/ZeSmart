package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.zesmart.auth.Auth;

public class OnBoardingActivity extends AppCompatActivity {
    float x1, y1, x2, y2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        Log.i("test", String.valueOf(touchEvent.getAction() == MotionEvent.ACTION_DOWN));
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 > x2) setContentView(R.layout.activity_on_boarding2);
                else if(x1 < x2) setContentView(R.layout.activity_on_boarding);
                break;
        }
        return false;
    }

    public void redirectOnBoarding1(View view) {
        setContentView(R.layout.activity_on_boarding);
    }

    public void redirectOnBoarding2(View view) {
        setContentView(R.layout.activity_on_boarding2);
    }

    public void redirectLogin(View view) {
        saveData("onBoarding", "true");
        final LoadingClass loadingDialog = new LoadingClass(OnBoardingActivity.this);
        loadingDialog.startLoadingDialog(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dissmissDialog();
                Intent redirectLogin = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(redirectLogin);
            }
        }, 1500);
    }
    public void saveData(String key, String data) {

        SharedPreferences sharedPref = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, data);
        editor.commit();
    }

}