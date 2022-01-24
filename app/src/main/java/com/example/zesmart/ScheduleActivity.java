package com.example.zesmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
    }


    public void redirectFinalTest(View view) {
        Intent redirectFinalTest = new Intent(ScheduleActivity.this, FinalTestActivity.class);
        startActivity(redirectFinalTest);
    }
}