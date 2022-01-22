package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zesmart.api.Category;
import com.example.zesmart.api.Materi;
import com.example.zesmart.api.SubCategory;

public class ListMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_materi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            String title = extras.getString("category");
            TextView textView = (TextView) findViewById(R.id.title_category);
            textView.setText(title);
            SubCategory subCategory = new SubCategory(ListMateriActivity.this);
            subCategory.subCategoryList(value);

        }


    }

    public void redirectDetailMateri(View view){
        int getId = (int) view.getTag();

        Materi materi = new Materi(ListMateriActivity.this);
        materi.materiList(getId);
        Intent redirectDetailMateri = new Intent(this, SubDetailMateriActivity.class);
        startActivity(redirectDetailMateri);
    }

    public void redirectSubmitMateri(View view) {
        Intent redirectSubmitMateri = new Intent(this, SubmitCategoryActivity.class);
        startActivity(redirectSubmitMateri);
    }
}