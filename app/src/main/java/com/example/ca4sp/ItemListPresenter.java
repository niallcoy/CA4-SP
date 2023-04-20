package com.example.ca4sp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemListPresenter implements ItemListContract.Presenter {

    private ItemListContract.View view;
    private DatabaseReference databaseRef;

    public ItemListPresenter(ItemListContract.View view) {
        this.view = view;
        databaseRef = FirebaseDatabase.getInstance().getReference("items");
    }

    @Override
    public void loadItems() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Item> items = new ArrayList<>();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    items.add(item);
                }
                view.showItems(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                view.showError(databaseError.getMessage());
            }
        });
    }
}
