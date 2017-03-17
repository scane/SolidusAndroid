package com.scanba.solidusandroid.models.taxonomy;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "taxonomy_taxons")
public class Taxon {
    @DatabaseField(generatedId = true)
    private int dbId;

    @DatabaseField
    @SerializedName("id")
    private int id;

    @DatabaseField
    @SerializedName("name")
    private String name;

    @DatabaseField
    @SerializedName("pretty_name")
    private String prettyName;

    @DatabaseField
    @SerializedName("parent_id")
    private Integer parentId;

    @SerializedName("taxons")
    private List<Taxon> taxons;

    public Taxon() {}

    public int getDbId() {
        return dbId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public List<Taxon> getTaxons() {
        return taxons;
    }

    public static List<Taxon> getTaxonsByParentID(Integer parentID, Dao<Taxon, Integer> taxonDao) {
        Where<Taxon, Integer> where = taxonDao.queryBuilder().where();
        List<Taxon> taxons = new ArrayList<>();
        try {
            if(parentID == null)
                taxons = taxonDao.query(where.isNull("parentID").prepare());
            else
                taxons = taxonDao.query(where.eq("parentID", parentID).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxons;
    }

    public boolean hasChildren(Dao<Taxon, Integer> taxonDao) {
        Where<Taxon, Integer> where = taxonDao.queryBuilder().where();
        try {
            if(where.eq("parentId", getId()).countOf() > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
