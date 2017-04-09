package com.scanba.solidusandroid.models.order;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.product.ProductVariant;

public class OrderLineItem {
    @SerializedName("id")
    private int id;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("display_amount")
    private String displayAmount;

    @SerializedName("variant")
    private ProductVariant variant;

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDisplayAmount() {
        return displayAmount;
    }

    public ProductVariant getVariant() {
        return variant;
    }
}
