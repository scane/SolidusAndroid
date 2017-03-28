package com.scanba.solidusandroid.models.product;

import com.google.gson.annotations.SerializedName;

public class ProductOptionType {
    @SerializedName("id")
    private int id;

    @SerializedName("presentation")
    private String presentation;

    public int getId() {
        return id;
    }

    public String getPresentation() {
        return presentation;
    }
}
