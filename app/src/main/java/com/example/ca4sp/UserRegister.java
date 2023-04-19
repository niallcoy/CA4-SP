package com.example.ca4sp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
//import com.example.finalca.UserSingleton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserRegister extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mAddressEditText;
    private Button mRegisterButton;
    private TextView Register;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Initialize Firebase auth and database reference

        FirebaseSingleton firebaseSingleton = FirebaseSingleton.getInstance();
         databaseReference = firebaseSingleton.getReference("users");

        // Initialize views
        mAuth = FirebaseAuth.getInstance();
        mNameEditText = findViewById(R.id.rName);
        mEmailEditText = findViewById(R.id.rEmail);
        mPasswordEditText = findViewById(R.id.rPassword);
        mRegisterButton = findViewById(R.id.rRegisterBtn);
        mAddressEditText = findViewById(R.id.rAddress);
        progressBar = findViewById(R.id.progressBar);
        Register = findViewById(R.id.title);

        // Set click listener for register button
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String name = mNameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String address = mAddressEditText.getText().toString().trim();

                // Validate input fields
                if (name.isEmpty()) {
                    mNameEditText.setError("Please enter your name");
                    mNameEditText.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    mEmailEditText.setError("Please enter your email");
                    mEmailEditText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmailEditText.setError("Please enter a valid email address");
                    mEmailEditText.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    mPasswordEditText.setError("Please enter a password");
                    mPasswordEditText.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    mAddressEditText.setError("Please enter your employee ID");
                    mAddressEditText.requestFocus();
                    return;
                }


                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(UserRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                User user = new User(name, email, address, password);
                                  registerUser(user,mAuth.getUid());
                            }});


                // Save user data to Firebase database



                // Set click listener for "no account" text view
                Register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Navigate to user registration activity
                        Intent intent = new Intent(UserRegister.this, UserLogin.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void registerUser(User user,String userId){
        databaseReference.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(UserRegister.this, "Registration successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UserRegister.this, UserLogin.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UserRegister.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
