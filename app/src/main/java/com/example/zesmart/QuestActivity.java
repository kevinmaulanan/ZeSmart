package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zesmart.api.Quest;
import com.example.zesmart.api.SubCategory;

import org.json.JSONException;

public class QuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String category = extras.getString("category");
            TextView textView = (TextView) findViewById(R.id.quest_title_materi);
            textView.setText(category);
            int value = extras.getInt("id");
            Quest quest = new Quest(QuestActivity.this);
            quest.questList(value);



        }


    }


}