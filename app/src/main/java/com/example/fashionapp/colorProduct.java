package com.example.fashionapp;

public class colorProduct {
    String color;
    String linkImage;
    int sizeS;
    int sizeM;
    int sizeL;
    int sizeXL;

    public colorProduct(){

    }

    public colorProduct(String color, String linkImage, int sizeS, int sizeM, int sizeL, int sizeXL){
        this.color = color;
        this.linkImage = linkImage;
        this.sizeS = sizeS;
        this.sizeM = sizeM;
        this.sizeL = sizeL;
        this.sizeXL = sizeXL;
    }

    public colorProduct(colorProduct colorProduct){
        this.color = colorProduct.color;
        this.linkImage = colorProduct.linkImage;
        this.sizeS = colorProduct.sizeS;
        this.sizeM = colorProduct.sizeM;
        this.sizeL = colorProduct.sizeL;
        this.sizeXL = colorProduct.sizeXL;
    }

    public String getColor(){ return color; }

    public String getLinkImage(){ return linkImage; }

    public int getSizeS(){ return sizeS; }

    public int getSizeM(){ return sizeM; }

    public int getSizeL(){ return sizeL; }

    public int getSizeXL(){ return sizeXL; }
}
