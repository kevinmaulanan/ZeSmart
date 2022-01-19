package com.example.zesmart.localstorage;


import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class LocalStorage {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public LocalStorage(Activity myActivity) {
        activity = myActivity;
    }

    public void setString(String key, String data) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, data);
        editor.apply();
    }
}
