package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
        final LoadingClass loadingDialog = new LoadingClass(OnBoardingActivity.this);
        loadingDialog.startLoadingDialog(null,OnBoardingActivity.this, LoginActivity.class);
    }
}