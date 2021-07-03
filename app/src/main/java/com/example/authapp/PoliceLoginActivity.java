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

public class PoliceLoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button login;
    private TextView reg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        reg = findViewById(R.id.register);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(PoliceLoginActivity.this, PoliceRegisterActivity.class);
                startActivity(register);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String Pass = password.getText().toString().trim();

                if (Email.isEmpty()){
                    email.setError("Provide Your Email");
                    email.requestFocus();
                    return;
                }
                if (Pass.isEmpty()){
                    password.setError("Provide Your Email");
                    password.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Provide a Valid Email Address");
                    email.requestFocus();
                    return;
                }
                if (Pass.length() < 6){
                    password.setError("Password Contains more than 6 characters");
                    password.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent profile = new Intent(PoliceLoginActivity.this, PoliceMapsActivity.class);
                            startActivity(profile);

                        }else{
                            Toast.makeText(PoliceLoginActivity.this, "Failed to Login! Check your Credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}