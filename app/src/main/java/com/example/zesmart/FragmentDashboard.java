package com.example.zesmart;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentDashboard extends AppCompatActivity {
    //@Nullable

    //public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //    return inflater.inflate(R.layout.fragment_dashboard, container, false);
    //





    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                    Fragment f = null;
                    switch (id){
                        case R.id.menu_dashboard:
                            f = new Fragment();
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

                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,f).commit();
                    return true;
            }

        });
    }
}




