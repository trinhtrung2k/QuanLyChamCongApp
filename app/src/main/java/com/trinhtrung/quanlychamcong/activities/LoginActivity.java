package com.trinhtrung.quanlychamcong.activities;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.trinhtrung.quanlychamcong.MainActivity;
import com.trinhtrung.quanlychamcong.R;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    EditText email, password;
    TextView signUp;
    FirebaseAuth auth;
    ProgressBar progressBar;
    TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        initUi();

        setEvent();


    }

    private void setEvent() {
        progressBar.setVisibility(View.GONE);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SendEmailActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initUi() {
        signIn = findViewById(R.id.login_btn);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        signUp = findViewById(R.id.sign_up);
        forgotPassword = findViewById(R.id.forgot_password);
        progressBar = findViewById(R.id.progressbar);
    }

    private void loginUser() {

        String userEmail =  email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6 ){
            Toast.makeText(this, "Password Length must be greater then 6 letter", Toast.LENGTH_SHORT).show();
            return;
        }

        //login user
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Error: " +task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}