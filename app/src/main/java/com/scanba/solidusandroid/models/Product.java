package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.product.Image;
import com.scanba.solidusandroid.models.product.Variant;

import java.util.List;

public class Product {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("display_price")
    private String displayPrice;
    @SerializedName("total_on_hand")
    private int quantityInStock;

    @SerializedName("master")
    private Variant masterVariant;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public Variant getMasterVariant() {
        return masterVariant;
    }
}
