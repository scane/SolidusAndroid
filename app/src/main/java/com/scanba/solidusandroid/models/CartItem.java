package com.scanba.solidusandroid.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "cart_items")
public class CartItem {

    public CartItem() {
        variantInfo = "";
    }

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public int productId;

    @DatabaseField
    public int variantId;

    public String name;

    public String variantInfo;

    public String displayPrice;

    public void destroy(Dao<CartItem, Integer> cartItemDao) {
        DeleteBuilder<CartItem, Integer> deleteBuilder = cartItemDao.deleteBuilder();
        try {
            deleteBuilder.where().eq("id", this.id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
