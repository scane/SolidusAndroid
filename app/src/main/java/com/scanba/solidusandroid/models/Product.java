package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.product.ProductOptionType;
import com.scanba.solidusandroid.models.product.ProductVariant;

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

    @SerializedName("option_types")
    private List<ProductOptionType> optionTypes;

    @SerializedName("master")
    private ProductVariant masterVariant;

    @SerializedName("has_variants")
    private boolean hasVariants;

    @SerializedName("variants")
    private List<ProductVariant> variants;

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

    public ProductVariant getMasterVariant() {
        return masterVariant;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductOptionType> getOptionTypes() {
        return optionTypes;
    }

    public boolean isHasVariants() {
        return hasVariants;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }
}
