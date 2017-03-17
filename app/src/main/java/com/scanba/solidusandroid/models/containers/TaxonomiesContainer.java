package com.scanba.solidusandroid.models.containers;

import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.Taxonomy;

import java.util.List;

public class TaxonomiesContainer {
    @SerializedName("taxonomies")
    private List<Taxonomy> taxonomies;

    public List<Taxonomy> getTaxonomies() {
        return taxonomies;
    }
}
