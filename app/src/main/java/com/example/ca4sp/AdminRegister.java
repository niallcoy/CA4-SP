package com.example.ca4sp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegister extends AppCompatActivity {

    private EditText fullName;
    private EditText emailAddress;
    private EditText employeeID;
    private EditText password;
    private Button registerButton;
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

        // Get a reference to the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Admins");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString().trim();
                String email = emailAddress.getText().toString().trim();
                String id = employeeID.getText().toString().trim();
                String pass = password.getText().toString().trim();

                // Show a progress bar while the registration process is in progress
                progressBar.setVisibility(View.VISIBLE);

                // Create a User object to store the user's registration information
                User user = new User(name, email, id, pass);

                // Save the User object to the Firebase Realtime Database
                databaseReference.child(id).setValue(user)
                        .addOnCompleteListener(task -> {
                            // Hide the progress bar
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Registration successful
                                Toast.makeText(AdminRegister.this, "Registration successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AdminRegister.this, AdminLogin.class);


                            } else {
                                // Registration failed
                                Toast.makeText(AdminRegister.this, "Registration failed", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
