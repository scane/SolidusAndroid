package com.scanba.solidusandroid.models;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.scanba.solidusandroid.models.taxonomy.Taxon;

import java.sql.SQLException;
import java.util.List;

public class Base {

    public static <M,I> M first(Dao<M, I> dao) {
        QueryBuilder<M, I> query = dao.queryBuilder();
        query.limit(1L);
        try {
            List<M> list = dao.query(query.prepare());
            return list.size() > 0 ? list.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <M,I> boolean deleteAll(Dao<M, I> dao) {
        DeleteBuilder<M, I> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.delete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
