package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zesmart.auth.Auth;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView textLogin = findViewById(R.id.suruh_login);

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahKeLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(pindahKeLogin);
            }
        });



    }

    public void doRegister(View view) {
        try {
            Log.i("test", "test");
            EditText password = findViewById(R.id.password_register);
            EditText email = findViewById(R.id.email_register);

            String getPassword = password.getText().toString();
            String getEmail = email.getText().toString();

            final Auth auth = new Auth(RegisterActivity.this);
            auth.register(getEmail, getPassword);

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}