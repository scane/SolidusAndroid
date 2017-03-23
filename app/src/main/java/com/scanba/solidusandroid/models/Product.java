package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.product.ProductImage;

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
    @SerializedName("description")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public class Variant {
        @SerializedName("images")
        private List<ProductImage> images;

        public List<ProductImage> getImages() {
            return images;
        }
    }
}
