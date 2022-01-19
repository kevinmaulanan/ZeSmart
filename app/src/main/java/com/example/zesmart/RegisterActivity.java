package com.example.zesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zesmart.api.Auth;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        TextView textLogin = findViewById(R.id.suruh_login);

        textLogin.setOnClickListener(v -> {
            Intent pindahKeLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(pindahKeLogin);
        });
    }

    public void doRegister(View view) {
        try {
            Log.i("test", "test");
            EditText password = findViewById(R.id.password_register);
            EditText confirmPassword = findViewById(R.id.confirm_password_register);
            EditText email = findViewById(R.id.email_register);
            EditText username = findViewById(R.id.username_register);

            String getPassword = password.getText().toString();
            String getEmail = email.getText().toString();
            String getConfirmPassword = confirmPassword.getText().toString();
            String getUsername = username.getText().toString();

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(getEmail);

            if (matcher.matches()) {
                if (getUsername.equals("")) throw new Exception("Username harus di isi");
                else if (getPassword.equals("")) throw new Exception("Password harus di isi");
                else if (getConfirmPassword.equals("")) throw new Exception("Confirm Password harus di isi");
                else if (!getPassword.equals(getConfirmPassword)) {
                    throw new Exception("Password dan Confirm Password tidak sama");
                }
                final Auth auth = new Auth(RegisterActivity.this);
                auth.register(getEmail, getPassword, getUsername);

            } else {
                throw new Exception("Format email salah!");
            }

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}