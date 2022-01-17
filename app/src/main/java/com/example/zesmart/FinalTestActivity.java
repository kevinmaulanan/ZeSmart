package com.example.zesmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


public class FinalTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finaltest);


    }

    public void pindah(View view) {
        Intent intent = new Intent(FinalTestActivity.this,TestActivity.class);
        startActivity(intent);
    }
}