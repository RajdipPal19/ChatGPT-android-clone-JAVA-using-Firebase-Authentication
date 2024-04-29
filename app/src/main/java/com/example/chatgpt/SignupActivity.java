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
import com.google.firebase.firestore.model.FieldIndex;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    TextView textSignin;
    EditText edtFullname, edtEmail, edtMobile, edtPassword, edtConfirmPassword;
    ProgressBar progressBar;
    Button btnSignUp;
    String strFullname, strEmail, strMobile, strPassword, strConfirmPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        textSignin = findViewById(R.id.txtSignIn);
        edtFullname = findViewById(R.id.edtSignUpFullName);
        edtEmail = findViewById(R.id.edtSignUpEmail);
        edtMobile = findViewById(R.id.edtSignUpMobile);
        edtPassword = findViewById(R.id.edtSignUpPassword);
        edtConfirmPassword = findViewById(R.id.edtSignUpConfirmPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        btnSignUp = findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ins= new Intent(SignupActivity.this,loginActivity.class);
                startActivity(ins);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFullname = edtFullname.getText().toString();
                strEmail = edtEmail.getText().toString().trim();
                strMobile = edtMobile.getText().toString().trim();
                strPassword = edtPassword.getText().toString();
                strConfirmPassword = edtConfirmPassword.getText().toString();

                if (isValidate()) {
                    Signup();
                }
            }
        });

    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(strFullname)) {
            edtFullname.setError("Full name can't be empty.");
            return false;
        }
        if (TextUtils.isEmpty(strEmail)) {
            edtEmail.setError("Email can't be empty.");
            return false;
        }
        if (TextUtils.isEmpty(strPassword)) {
            edtPassword.setError("Password can't be empty.");
            return false;
        }
        if (strEmail.matches(emailPattern)) {
            edtEmail.setError("Enter a Valid Email.");
        }
        if (TextUtils.isEmpty(strMobile)) {
            edtMobile.setError("Mobile number can't be empty.");
            return false;
        }
        if (TextUtils.isEmpty(strConfirmPassword)) {
            edtConfirmPassword.setError("Confirm password can't be empty.");
            return false;
        }
        if (!strPassword.equals(strConfirmPassword)) {
            edtConfirmPassword.setError("confirm password not matches");
            return false;
        }
        return true;
    }

    private void Signup() {
        btnSignUp.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String,Object> user = new HashMap<>();
                user.put("FullName",strFullname);
                user.put("Email",strEmail);
                user.put("Mobile",strMobile);

                db.collection("Users").document(strEmail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        btnSignUp.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
                btnSignUp.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}