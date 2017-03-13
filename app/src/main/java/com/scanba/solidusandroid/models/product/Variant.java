package com.scanba.solidusandroid.models.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variant {
    @SerializedName("images")
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }
}
