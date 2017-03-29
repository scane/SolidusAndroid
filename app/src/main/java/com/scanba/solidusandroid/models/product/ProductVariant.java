package com.scanba.solidusandroid.models.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductVariant {
    @SerializedName("id")
    private int id;

    @SerializedName("display_price")
    private String displayPrice;

    @SerializedName("images")
    private List<ProductImage> images;

    @SerializedName("option_values")
    private List<OptionValue> optionValues;

    public int getId() {
        return id;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public List<OptionValue> getOptionValues() {
        return optionValues;
    }

    public class OptionValue {
        @SerializedName("id")
        private int id;

        @SerializedName("option_type_id")
        private int optionTypeId;

        @SerializedName("presentation")
        private String presentation;

        public int getId() {
            return id;
        }

        public int getOptionTypeId() {
            return optionTypeId;
        }

        public String getPresentation() {
            return presentation;
        }
    }
}
