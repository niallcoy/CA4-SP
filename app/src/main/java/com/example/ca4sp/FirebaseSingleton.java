package com.example.ca4sp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSingleton {
    private static FirebaseSingleton instance;
    private DatabaseReference reference;

    private FirebaseSingleton() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseSingleton getInstance() {
        if (instance == null) {
            instance = new FirebaseSingleton();
        }
        return instance;
    }

    public DatabaseReference getReference(String path) {
        return reference.child(path);
    }
}
