package com.scanba.solidusandroid.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.models.CartItem;
import com.scanba.solidusandroid.models.taxonomy.Taxon;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "solidusandroid.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<Taxon, Integer> taxonDao;
    private Dao<CartItem, Integer> cartItemDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Taxon.class);
            TableUtils.createTable(connectionSource, CartItem.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, Taxon.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }

    public Dao<Taxon, Integer> getTaxonDao() throws SQLException {
        if (taxonDao == null) {
            taxonDao = getDao(Taxon.class);
        }
        return taxonDao;
    }

    public Dao<CartItem, Integer> getCartItemDao() throws SQLException {
        if (cartItemDao == null) {
            cartItemDao = getDao(CartItem.class);
        }
        return cartItemDao;
    }
}
