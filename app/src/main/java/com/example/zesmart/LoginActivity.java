package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        actionBar.hide();

        try {
            TextView textRegister = findViewById(R.id.suruh_register);

            textRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindahKeRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(pindahKeRegister);
                }
            });


            Button buttonLogin = findViewById(R.id.buttonLogin);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        checkLogin();
                        final LoadingClass loadingDialog = new LoadingClass(LoginActivity.this);
                        loadingDialog.startLoadingDialog(null,LoginActivity.this, ListMateriActivity.class);
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
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

    void checkLogin() throws Exception {
        try {
            EditText password = findViewById(R.id.password);
            String getPassword = password.getText().toString();
            EditText email = findViewById(R.id.email);
            String getEmail = email.getText().toString();
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(getEmail);
            Log.i("passowrd", getPassword);
            Log.i("email", getEmail + "t" );
            Boolean checkEmail = TextUtils.equals(getEmail, "superadmin@gmail.com");
            Boolean checkPassowrd = TextUtils.equals(getPassword, "pass123");

            if (matcher.matches()) {
                if (!checkEmail || !checkPassowrd) {
                    throw new Exception("Email atau password salah!");
                }
            } else {
                throw new Exception("Format email salah!");
            }

        } catch (Exception e) {
            throw (e);
        }
    }
}