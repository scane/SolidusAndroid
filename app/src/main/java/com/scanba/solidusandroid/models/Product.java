package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;

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

    public class Variant {
        @SerializedName("images")
        private List<Image> images;

        public List<Image> getImages() {
            return images;
        }
    }

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
}
