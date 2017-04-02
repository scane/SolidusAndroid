package com.scanba.solidusandroid.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cart_items")
public class CartItem {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public int productId;

    @DatabaseField
    public int variantId;

    public String name;

    public String displayPrice;
}
