package com.example.zesmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.zesmart.R;
import com.example.zesmart.api.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentDashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentList()).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
                Fragment f = null;
                switch (id){
                    case R.id.menu_dashboard:
                        f = new FragmentList();
                        break;
                    case R.id.menu_activity:
                        f = new FragmentActivity();
                        break;
                    case R.id.menu_schedule:
                        f = new FragmentSchedule();
                        break;
                    case R.id.menu_profile:

                        f = new FragmentProfile();
                        break;
                }

            assert f != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,f).commit();
                return true;
        });
    }

    public void redirectMateri(View v){
        Intent redirectListMateri = new Intent(this, ListMateriActivity.class);
        startActivity(redirectListMateri);
    }

    public void redirectSubMateri(View v, int id){
        Intent redirectListMateri = new Intent(this, ListMateriActivity.class);
        startActivity(redirectListMateri);
    }
}




