package com.scanba.solidusandroid.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "orders")
public class Order extends Base {
    @DatabaseField
    @SerializedName("id")
    private int id;

    @DatabaseField
    @SerializedName("number")
    private String number;

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
}
