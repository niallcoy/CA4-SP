package com.example.ca4sp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Initialize views and variables
        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(itemList);

        databaseRef = FirebaseDatabase.getInstance().getReference("items");

        setProducts();

    }

    public void setProducts(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ItemList.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Item item1 = new Item("iphone", "Apple", "phone", "iphone.jpg", 500, 7 );
        Item item2 = new Item("MacBook", "Apple", "Laptop", "macbook.jpg", 1000, 5);
        Item item3 = new Item("Nokia blockia", "Nokia", "phone", "blockia.jpg", 500, 7 );

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);




    }


}
