package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PoliceRegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText fullname, age, email, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_register);
        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.p_fullname);
        age = findViewById(R.id.p_age);
        email = findViewById(R.id.p_email);
        password = findViewById(R.id.p_password);
        register = findViewById(R.id.p_btregister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fullname = fullname.getText().toString().trim();
                String Age = age.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Pass = password.getText().toString().trim();

//                if (Fullname.isEmpty()){
//                    fullname.setError("Full name is required");
//                    fullname.requestFocus();
//                    return;
//                }
//                if (Age.isEmpty()){
//                    age.setError("Full name is required");
//                    age.requestFocus();
//                    return;
//                }
//                if (Email.isEmpty()){
//                    email.setError("Full name is required");
//                    email.requestFocus();
//                    return;
//                }
//                if (Pass.isEmpty()){
//                    password.setError("Full name is required");
//                    password.requestFocus();
//                    return;
//                }
//                if (Pass.length() < 6){
//                    password.setError("Must be more than 6 ocharacters");
//                    password.requestFocus();
//                    return;
//                }
//                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
//                    email.setError("Provide a valid Email address");
//                }

                mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(Fullname,Age, Email);

                            FirebaseDatabase.getInstance().getReference("Users").child("Police").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(PoliceRegisterActivity.this, "User has been registered succesfully", Toast.LENGTH_LONG).show();
                                        Intent log = new Intent(PoliceRegisterActivity.this, PoliceLoginActivity.class);
                                        startActivity(log);
                                    }else {
                                        Toast.makeText(PoliceRegisterActivity.this, "Failed to Register User", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });

            }

        });

    }
}