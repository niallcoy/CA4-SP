package com.example.ca4sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {
    //Views
    Button mUserBtn,mAdministratorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mUserBtn=findViewById(R.id.userBtn);
        mAdministratorBtn=findViewById(R.id.administratorBtn);

        mUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, UserLogin.class)); // User activity
            }
        });
        mAdministratorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminLogin.class)); //Admin activity
            }
        });

    }
}