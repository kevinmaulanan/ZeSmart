package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zesmart.api.Category;
import com.example.zesmart.api.Materi;

public class ListMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_materi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Category category = new Category(ListMateriActivity.this);
        category.categoryList();
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