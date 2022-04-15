package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.models.UserModel;

public class RegistrationActivity extends AppCompatActivity {

    Button signUp;
    EditText name, email, password;
    TextView signIn;

    FirebaseAuth auth;
   // DatabaseReference database;


//   FirebaseDatabase database;
//    DatabaseReference dbRef;

    private DatabaseReference mDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        initUi();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
              //  progressBar.setVisibility(View.VISIBLE);
            }
        });
    }


    private void initUi() {
        signUp = findViewById(R.id.login_btn);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        signIn = findViewById(R.id.sign_in);

    }

    private void createUser() {
        auth = FirebaseAuth.getInstance();
       // database = FirebaseDatabase.getInstance("https://quanlychamcong-95a83-default-rtdb.firebaseio.com/").getReference().child("Users");
      //  database = FirebaseDatabase.getInstance();

        String userName = name.getText().toString();
        String userEmail =  email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
            return;
        }
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

        //create user auth
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    try {
                        String id = task.getResult().getUser().getUid();

//                        UserModel userModel = new UserModel(userName,userEmail,userPassword);
//                        dbRef = database.getReference();
//                        dbRef.child(id).setValue(userModel);

                        writeNewUser(id,userName,userEmail,userPassword);

                    }catch (Exception e){
                        Toast.makeText(RegistrationActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }



//                    database.getReference("Users/"+id).setValue(user, new DatabaseReference.CompletionListener() {
//                       @Override
//                       public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                           Toast.makeText(RegistrationActivity.this, "Push data success", Toast.LENGTH_SHORT).show();
//                       }
//                   });
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "Error!: " + task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    public void writeNewUser(String userId, String name, String email, String password) {
        UserModel user = new UserModel(name, email, password);

        mDatabase.child("Users").child(userId).setValue(user);
    }
}