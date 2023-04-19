package com.example.ca4sp;

import com.example.ca4sp.ItemFactory;


public class ItemFactory {
    public static Item createItem(String title, String manufacturer, String category, String imageURL, int price, int quantity) {
        return new Item(title, manufacturer, category, imageURL, price, quantity);
    }
}
