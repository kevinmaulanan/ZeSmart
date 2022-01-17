package com.example.zesmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class GiftActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"Zestar T-Shirt","Zestar Pink Hat", "Zestar Bllack Hat"};
    String mDescription[] = {"Zestar T-Shirt Description", "Zestar Pink Hat Description", "Zestar Bllack Description"};
    int images[] = {R.drawable.baju, R.drawable.hat_pink, R.drawable.hat_black};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        listView = findViewById(R.id.listview);
    }
}