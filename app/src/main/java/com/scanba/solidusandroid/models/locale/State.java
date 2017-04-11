package com.scanba.solidusandroid.models.locale;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "states")
public class State {
    @DatabaseField(id = true)
    @SerializedName("id")
    private int id;

    @DatabaseField
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
