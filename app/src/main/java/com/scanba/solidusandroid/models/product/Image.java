package com.scanba.solidusandroid.models.product;


import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    private int id;
    @SerializedName("mini_url")
    private String miniURL;
    @SerializedName("small_url")
    private String smallURL;
    @SerializedName("product_url")
    private String productURL;
    @SerializedName("large_url")
    private String largeURL;

    public int getId() {
        return id;
    }

    public String getMiniURL() {
        return miniURL;
    }

    public String getSmallURL() {
        return smallURL;
    }

    public String getProductURL() {
        return productURL;
    }

    public String getLargeURL() {
        return largeURL;
    }
}
