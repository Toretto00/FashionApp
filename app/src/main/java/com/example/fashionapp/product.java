package com.example.fashionapp;

public class product {

    private int image;
    private String name, price;

    public product (String name, String price, int image)
    {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public product(product product) {
        this.name = product.name;
        this.price = product.price;
        this.image = product.image;
    }

    public String getName()
    {
        return name;
    }

    public String getPrice()
    {
        return price;
    }

    public int getImage()
    {
        return image;
    }

}
