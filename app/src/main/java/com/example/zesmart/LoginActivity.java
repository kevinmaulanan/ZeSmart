package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zesmart.api.Auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    Pattern pattern;
    Matcher matcher;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        try {
            TextView textRegister = findViewById(R.id.suruh_register);

            textRegister.setOnClickListener(v -> {
                Intent pindahKeRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(pindahKeRegister);
            });


            Button buttonLogin = findViewById(R.id.buttonLogin);

            buttonLogin.setOnClickListener(v -> {
                try {
                    doLogin();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.i("test", e.getMessage());
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return false;
        }
        return false;
    }

    void doLogin() throws Exception {
        EditText password = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);

        String getPassword = password.getText().toString();
        String getEmail = email.getText().toString();
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(getEmail);


        if (matcher.matches()) {
            final Auth auth = new Auth(LoginActivity.this);
            auth.doLogin(getEmail, getPassword);

        } else {
            throw new Exception("Format email salah!");
        }

    }
}