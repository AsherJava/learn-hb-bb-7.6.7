/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.quantity.dao;

import com.jiuqi.nr.quantity.bean.QuantityCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class QuantityCategoryDao {
    private static final String TABLE_QUAN_CATEGORY = "NR_QUAN_CATEGORY";
    private static final String QC_ID = "QC_ID";
    private static final String QI_ID = "QI_ID";
    private static final String QC_NAME = "QC_NAME";
    private static final String QC_TITLE = "QC_TITLE";
    private static final String QC_ORDER = "QC_ORDER";
    private static final String QC_BASE = "QC_BASE";
    private static final String QC_RATE = "QC_RATE";
    private static final String QC_MODIFYTIME = "QC_MODIFYTIME";
    private static final String QUERY_F_QUAN_CATEGORY = String.format("%s, %s, %s, %s, %s, %s, %s, %s", "QC_ID", "QI_ID", "QC_NAME", "QC_TITLE", "QC_ORDER", "QC_BASE", "QC_RATE", "QC_MODIFYTIME");
    private static final Function<ResultSet, QuantityCategory> ENTITY_READER_QUANTITY_CATEGORY = rs -> {
        QuantityCategory quantityCategory = new QuantityCategory();
        int index = 1;
        try {
            quantityCategory.setId(rs.getString(index));
            quantityCategory.setQuantityId(rs.getString(++index));
            quantityCategory.setName(rs.getString(++index));
            quantityCategory.setTitle(rs.getString(++index));
            quantityCategory.setOrder(rs.getString(++index));
            quantityCategory.setBase(rs.getBoolean(++index));
            quantityCategory.setRate(rs.getDouble(++index));
            quantityCategory.setModifyTime(rs.getTimestamp(++index).getTime());
        }
        catch (SQLException e) {
            throw new RuntimeException("read QuantityCategory category error.", e);
        }
        return quantityCategory;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addQuantityCategory(QuantityCategory quantityCategory) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", TABLE_QUAN_CATEGORY, QC_ID, QI_ID, QC_NAME, QC_TITLE, QC_ORDER, QC_BASE, QC_RATE, QC_MODIFYTIME);
        this.jdbcTemplate.update(addSql, new Object[]{quantityCategory.getId(), quantityCategory.getQuantityId(), quantityCategory.getName(), quantityCategory.getTitle(), quantityCategory.getOrder(), quantityCategory.isBase(), quantityCategory.getRate(), new Timestamp(quantityCategory.getModifyTime())});
    }

    public void updateQuantityCategory(QuantityCategory quantityCategory) {
        String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_QUAN_CATEGORY, QC_TITLE, QC_ORDER, QC_BASE, QC_RATE, QC_MODIFYTIME, QC_ID);
        this.jdbcTemplate.update(updateSql, new Object[]{quantityCategory.getTitle(), quantityCategory.getOrder(), quantityCategory.isBase(), quantityCategory.getRate(), new Timestamp(quantityCategory.getModifyTime()), quantityCategory.getId()});
    }

    public void updateQuantityCategoryByName(QuantityCategory quantityCategory) {
        String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_QUAN_CATEGORY, QC_ID, QC_TITLE, QC_ORDER, QC_BASE, QC_RATE, QC_MODIFYTIME, QC_TITLE);
        this.jdbcTemplate.update(updateSql, new Object[]{quantityCategory.getId(), quantityCategory.getTitle(), quantityCategory.getOrder(), quantityCategory.isBase(), quantityCategory.getRate(), new Timestamp(quantityCategory.getModifyTime()), quantityCategory.getName()});
    }

    public void deleteQuantityCategoryById(String id) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s=?", TABLE_QUAN_CATEGORY, QC_ID);
        this.jdbcTemplate.update(deleteSql, new Object[]{id});
    }

    public void deleteQuantityCategoryByQuanId(String quantityId) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s=?", TABLE_QUAN_CATEGORY, QI_ID);
        this.jdbcTemplate.update(deleteSql, new Object[]{quantityId});
    }

    public QuantityCategory getQuantityCategoryById(String id) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_CATEGORY, TABLE_QUAN_CATEGORY, QC_ID);
        return (QuantityCategory)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return ENTITY_READER_QUANTITY_CATEGORY.apply(rs);
            }
            return null;
        }, new Object[]{id});
    }

    public QuantityCategory getQuantityCategoryByName(String name) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_CATEGORY, TABLE_QUAN_CATEGORY, QC_NAME);
        return (QuantityCategory)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return ENTITY_READER_QUANTITY_CATEGORY.apply(rs);
            }
            return null;
        }, new Object[]{name});
    }

    public List<QuantityCategory> getQuantityCategoryByQuanId(String quantityId) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_CATEGORY, TABLE_QUAN_CATEGORY, QI_ID);
        return this.jdbcTemplate.query(querySql, (rs, row) -> ENTITY_READER_QUANTITY_CATEGORY.apply(rs), new Object[]{quantityId});
    }

    public List<QuantityCategory> fuzzyQueryQuantityCategory(String keywords) {
        String fuzzyQuerySql = String.format("SELECT %s FROM %s WHERE %s like ? or %s like ?", QUERY_F_QUAN_CATEGORY, TABLE_QUAN_CATEGORY, QC_NAME, QC_TITLE);
        return this.jdbcTemplate.query(fuzzyQuerySql, (rs, row) -> ENTITY_READER_QUANTITY_CATEGORY.apply(rs), new Object[]{"%" + keywords + "%", "%" + keywords + "%"});
    }

    public int validateName(String id, String name) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s ";
        ArrayList<String> args = new ArrayList<String>(2);
        String nameSQL = String.format("%s = ?", QC_NAME);
        args.add(name);
        String idSQL = String.format("AND %s <> ?", QC_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s ", QC_ID, TABLE_QUAN_CATEGORY, nameSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }

    public int validateTitle(String id, String quantityId, String title) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s %s";
        ArrayList<String> args = new ArrayList<String>(4);
        String qiIdSQL = String.format("%s = ?", QI_ID);
        args.add(quantityId);
        String titleSQL = String.format("AND %s = ?", QC_TITLE);
        args.add(title);
        String idSQL = String.format("AND %s <> ?", QC_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s %s", QC_ID, TABLE_QUAN_CATEGORY, qiIdSQL, titleSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }

    public int validateBase(String id, String quantityId) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s %s";
        ArrayList<String> args = new ArrayList<String>(3);
        String qiIdSQL = String.format("%s = ?", QI_ID);
        args.add(quantityId);
        String baseSQL = String.format("AND %s = 1", QC_BASE);
        String idSQL = String.format("AND %s <> ?", QC_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s %s", QC_ID, TABLE_QUAN_CATEGORY, qiIdSQL, baseSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }
}

