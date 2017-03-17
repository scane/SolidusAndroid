package com.scanba.solidusandroid.models.containers;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.Product;

import java.util.List;

public class ProductsContainer {
    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }
}
