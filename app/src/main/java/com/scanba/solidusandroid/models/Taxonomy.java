package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.scanba.solidusandroid.models.taxonomy.Taxon;

import java.sql.SQLException;
import java.util.List;

public class Taxonomy {

    @SerializedName("root")
    private Taxon root;

    public Taxon getRoot() {
        return root;
    }
}
