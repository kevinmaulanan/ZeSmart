package com.example.zesmart.firestore;

import android.app.Activity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Firestrore {
    private FirebaseFirestore db;
    private final Activity activity;

    public Firestrore(Activity myActivity) {
        db = FirebaseFirestore.getInstance();
        activity = myActivity;
    }
}
