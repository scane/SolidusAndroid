package com.scanba.solidusandroid.models.product;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.api.ApiClient;

/**
 * Created by Scanny on 22/03/17.
 */

public class ProductImage {
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
        return ApiClient.BASE_URL + miniURL;
    }

    public String getSmallURL() {
        return ApiClient.BASE_URL + smallURL;
    }

    public String getProductURL() {
        return ApiClient.BASE_URL + productURL;
    }

    public String getLargeURL() {
        return ApiClient.BASE_URL + largeURL;
    }
}
