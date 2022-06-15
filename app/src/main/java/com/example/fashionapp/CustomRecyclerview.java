package com.example.fashionapp;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomRecyclerview {
    public String productImageRec;
    public String productName, colorSize;
    public int priceProductRec, quantityRec;

    public CustomRecyclerview() {
    }

    public CustomRecyclerview(String productImageRec, String productName, String colorSize,
                              int priceProductRec, int quantityRec) {
        this.productImageRec = productImageRec;
        this.productName = productName;
        this.colorSize = colorSize;
        this.priceProductRec = priceProductRec;
        this.quantityRec = quantityRec;
    }

    public String getProductImageRec() {
        return productImageRec;
    }

    public void setProductImageRec(String productImageRec) {
        this.productImageRec = productImageRec;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColorSize() {
        return colorSize;
    }

    public void setColorSize(String colorSize) {
        this.colorSize = colorSize;
    }

    public String getPriceProductRec() {
        return String.valueOf(priceProductRec);
    }

    public void setPriceProductRec(int priceProductRec) {
        this.priceProductRec = priceProductRec;
    }

    public String getQuantityRec() {
        return String.valueOf(quantityRec) ;
    }

    public void setQuantityRec(int quantityRec) {
        this.quantityRec = quantityRec;
    }
}
