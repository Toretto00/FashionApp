package com.example.fashionapp;

public class cartProduct {
    String id, name, color, size, linkImage;
    int quantity, price;

    public cartProduct(String id, String name, String color, String size, int quantity, int price, String linkImage){
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.linkImage = linkImage;
    }

    public cartProduct(cartProduct cartproduct){
        this.id = cartproduct.id;
        this.name = cartproduct.name;
        this.color = cartproduct.color;
        this.size = cartproduct.size;
        this.quantity = cartproduct.quantity;
        this.price =cartproduct.price;
        this.linkImage = cartproduct.linkImage;
    }

    public String getId(){ return id; }

    public String getName(){ return name; }

    public String getColor(){ return color; }

    public String getSize(){ return size; }

    public int getQuantity(){ return quantity; }

    public int getPrice(){ return price; }

    public String getLinkImage(){ return linkImage; }
}
