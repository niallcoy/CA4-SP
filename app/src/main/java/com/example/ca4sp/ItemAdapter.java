package com.example.ca4sp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.manufacturerTextView.setText(item.getManufacturer());
        holder.priceTextView.setText(String.valueOf(item.getPrice()));
        holder.categoryTextView.setText(item.getCategory());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        String image = item.getImageUrl();
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(image,"drawable",holder.itemView.getContext().getPackageName());
        holder.imageView.setImageResource(drawableResourceId);

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, manufacturerTextView, priceTextView, categoryTextView, quantityTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            manufacturerTextView = itemView.findViewById(R.id.manufacturerTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
