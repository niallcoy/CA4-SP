package com.example.ca4sp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ca4sp.FirebaseSingleton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegister extends AppCompatActivity {

    private EditText fullName;
    private EditText emailAddress;
    private EditText employeeID;
    private EditText password;
    private Button registerButton;
    private TextView Register;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        fullName = findViewById(R.id.rName);
        emailAddress = findViewById(R.id.rEmail);
        employeeID = findViewById(R.id.rrEmployeeID);
        password = findViewById(R.id.rPassword);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        Register = findViewById(R.id.title);

        // Get a reference to the Firebase Realtime Database

        FirebaseSingleton firebaseSingleton = FirebaseSingleton.getInstance();
        DatabaseReference databaseReference = firebaseSingleton.getReference("Admins");



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString().trim();
                String email = emailAddress.getText().toString().trim();
                String id = employeeID.getText().toString().trim();
                String pass = password.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                // Create an Admin object to store the admin's registration information
                Admin admin = new Admin(name, email, pass, id);

                // Save the Admin object to the Firebase Realtime Database
                databaseReference.child(id).setValue(admin)
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminRegister.this, "Registration successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AdminRegister.this, AdminLogin.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AdminRegister.this, "Registration failed", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        // Set click listener for "no account" text view
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to user registration activity
                Intent intent = new Intent(AdminRegister.this, AdminLogin.class);
                startActivity(intent);
            }
        });
    }
}
