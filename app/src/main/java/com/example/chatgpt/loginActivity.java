package com.example.chatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginActivity extends AppCompatActivity {

    TextView textSignUp,txtForgotPassword;
    EditText edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignIn;
    String strEmail, strPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
//    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        textSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.signInProgressBar);

        mAuth = FirebaseAuth.getInstance();
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ins= new Intent(loginActivity.this,SignupActivity.class);
                startActivity(ins);
                finish();
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ins= new Intent(loginActivity.this, ForgotPasswordActivity.class);
                startActivity(ins);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = edtEmail.getText().toString().trim();
                strPassword = edtPassword.getText().toString();
                if(isValidate()){
                    SignIn();
                }
            }
        });


    }

    private void SignIn() {
        btnSignIn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(strEmail,strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>(){

            @Override
            public void onSuccess(AuthResult authResult) {
               Intent intent = new Intent(loginActivity.this,MainActivity.class);
               startActivity(intent);
              // finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginActivity.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
                btnSignIn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean isValidate(){
        if (TextUtils.isEmpty(strEmail)) {
            edtEmail.setError("Email can't be empty.");
            return false;
        }
        if (TextUtils.isEmpty(strPassword)) {
            edtPassword.setError("Password can't be empty.");
            return false;
        }
        if (!strEmail.matches(emailPattern)) {
            edtEmail.setError("Enter a Valid Email.");
        }
        return true;
    }
}