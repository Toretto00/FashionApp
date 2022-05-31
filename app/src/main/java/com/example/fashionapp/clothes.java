package com.example.fashionapp;

public class clothes {
    String name;
    String price;
    String linkImage;
    String describe;
    String type;

    public clothes(){

    }

    public clothes (String name, String price, String linkImage, String describe, String type)
    {
        this.name = name;
        this.price = price;
        this.linkImage = linkImage;
        this.describe = describe;
        this.type = type;
    }

    public clothes(clothes clothes) {
        this.name = clothes.name;
        this.price = clothes.price;
        this.linkImage = clothes.linkImage;
        this.describe = clothes.describe;
        this.type = clothes.type;
    }

    public String getName()
    {
        return name;
    }

    public String getPrice()
    {
        return price;
    }

    public String getLinkImage(){ return linkImage; }

    public String getDescribe(){ return describe; }

    public String getType(){ return type; }
}
