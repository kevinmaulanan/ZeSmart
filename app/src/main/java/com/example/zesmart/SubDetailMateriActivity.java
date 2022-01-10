
package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SubDetailMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_detail_materi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
}

    public void redirectSubDetailMateri(View view){
        Intent redirectDetailMateri = new Intent(this, DetailMateriActivity.class);
        startActivity(redirectDetailMateri);

    }
}