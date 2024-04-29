package com.example.chatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginSignupActivity extends AppCompatActivity {
    Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        btnLogin = findViewById(R.id.btnMainLogin);
        btnRegister = findViewById(R.id.btnMainSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSignupActivity.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSignupActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}