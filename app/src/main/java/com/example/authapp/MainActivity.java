package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button BtPolice, BtWithness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtPolice = findViewById(R.id.police);
        BtWithness = findViewById(R.id.withness);


        BtPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                police();
            }
        });

        BtWithness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withness();
            }
        });
    }
    private void police(){
        Intent intent = new Intent(MainActivity.this, PoliceLoginActivity.class);
        startActivity(intent);
    }

    private void withness(){
        Intent intent1 = new Intent(MainActivity.this, WithnessLoginActivity.class);
        startActivity(intent1);

    }
}