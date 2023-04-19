package com.example.ca4sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    private EditText mEmployeeIDEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mNoAccountTextView;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize views
        mEmployeeIDEditText = findViewById(R.id.employeeID);
        mPasswordEditText = findViewById(R.id.aPassword);
        mLoginButton = findViewById(R.id.signIn);
        mNoAccountTextView = findViewById(R.id.noAccount);

        // Get a reference to the Firebase Realtime Database using the FirebaseSingleton
        FirebaseSingleton firebaseSingleton = FirebaseSingleton.getInstance();
        mDatabase = firebaseSingleton.getReference("Admins");

        // Set click listener for login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String employeeID = mEmployeeIDEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                // Query Firebase Realtime Database for admin with matching employee ID
                mDatabase.orderByChild("employeeID").equalTo(employeeID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if admin with matching employee ID exists
                        if (dataSnapshot.exists()) {
                            // Get admin data
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Admin admin = snapshot.getValue(Admin.class);
                                // Check if password is correct
                                if (admin.getPassword().equals(password)) {
                                    // Password is correct, allow access to next screen
                                    Intent intent = new Intent(AdminLogin.this, AdminHome.class);
                                    startActivity(intent);
                                    finish(); // Finish current activity to prevent user from going back to login screen
                                } else {
                                    // Password is incorrect, show error message
                                    Toast.makeText(AdminLogin.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // No admin with matching employee ID, show error message
                            Toast.makeText(AdminLogin.this, "Invalid employee ID", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Error occurred while querying database, show error message
                        Toast.makeText(AdminLogin.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set click listener for no account text view
        mNoAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to admin registration activity
                Intent intent = new Intent(AdminLogin.this, AdminRegister.class);
                startActivity(intent);
            }
        });
    }
}
