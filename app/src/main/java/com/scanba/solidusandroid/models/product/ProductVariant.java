package com.scanba.solidusandroid.models.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductVariant {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("display_price")
    private String displayPrice;

    @SerializedName("images")
    private List<ProductImage> images;

    @SerializedName("option_values")
    private List<OptionValue> optionValues;

    @SerializedName("options_text")
    private String optionsText;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getOptionsText() {
        return optionsText;
    }

//    @Override
//    public String toString() {
//        StringBuilder variantInfo = new StringBuilder("");
//        for(ProductVariant.OptionValue optionValue : getOptionValues())
//            variantInfo.append(optionValue.getOptionTypePresentation() + ": " + optionValue.getPresentation() + ", ");
//        variantInfo.delete(variantInfo.length() - 2, variantInfo.length() - 1);
//        return variantInfo.toString();
//    }

    public class OptionValue {
        @SerializedName("id")
        private int id;

        @SerializedName("option_type_id")
        private int optionTypeId;

        @SerializedName("presentation")
        private String presentation;

        @SerializedName("option_type_presentation")
        private String optionTypePresentation;

        public int getId() {
            return id;
        }

        public int getOptionTypeId() {
            return optionTypeId;
        }

        public String getPresentation() {
            return presentation;
        }

        public String getOptionTypePresentation() {
            return optionTypePresentation;
        }
    }
}
