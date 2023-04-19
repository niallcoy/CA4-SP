package com.example.ca4sp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import com.example.ca4sp.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

public class AddItem extends AppCompatActivity {

    private EditText iTitle, iManufacturer, iPrice, iCategory, iQuantity;
    private Button addItemBtn, uploadImageBtn;
    private ImageView productImage;
    private ProgressBar progressBar;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Initialize views
        iTitle = findViewById(R.id.iTitle);
        iManufacturer = findViewById(R.id.iManufacturer);
        iPrice = findViewById(R.id.iPrice);
        iCategory = findViewById(R.id.iCatagory);
        iQuantity = findViewById(R.id.iQuantity);
        addItemBtn = findViewById(R.id.addItemBtn);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        productImage = findViewById(R.id.ProductImage);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase database reference
        databaseRef = FirebaseDatabase.getInstance().getReference("items");

        // Set click listeners for buttons
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void addItem() {
        String title = iTitle.getText().toString().trim();
        String manufacturer = iManufacturer.getText().toString().trim();
        int price = Integer.parseInt(iPrice.getText().toString().trim());
        String category = iCategory.getText().toString().trim();
        int quantity = Integer.parseInt(iQuantity.getText().toString().trim());
        String imageURL = "";

        // Validate input fields
        if (title.isEmpty()) {
            iTitle.setError("Title is required");
            iTitle.requestFocus();
            return;
        }

        if (manufacturer.isEmpty()) {
            iManufacturer.setError("Manufacturer is required");
            iManufacturer.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            iCategory.setError("Category is required");
            iCategory.requestFocus();
            return;
        }

        // Create item object using the factory method
        Item item = ItemFactory.createItem(title, manufacturer, category, imageURL,  price, quantity);


        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Push item to Firebase database
        databaseRef.push().setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Hide progress bar
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Toast.makeText(AddItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddItem.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            productImage.setImageURI(imageUri);

            // Get a reference to the Firebase Storage location where you want to upload the image
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUri.getLastPathSegment());

            // Upload the image to Firebase Storage
            storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the URL of the uploaded image
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();

                            // TODO: Save the image URL to the Firebase Realtime Database or Firestore database
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddItem.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

