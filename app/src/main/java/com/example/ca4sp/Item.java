
package com.example.ca4sp;

public class Item {

    String itemId, title, manufacturer, category, imageURL;
    int price, quantity;


    public Item() {
    }

    //Constructor
    public Item( String title, String manufacturer, String category, String imageURL, int price, int quantity) {

        this.title = title;
        this.manufacturer = manufacturer;
        this.category = category;
        this.imageURL = imageURL;
        this.price = price;
        this.quantity = quantity;

    }

    public Item(String title, String manufacturer,  String category,String imageURL, String price, String quantity) {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}