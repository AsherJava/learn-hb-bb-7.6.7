/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.quantity.dao;

import com.jiuqi.nr.quantity.bean.QuantityUnit;
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
public class QuantityUnitDao {
    private static final String TABLE_QUAN_UNIT = "NR_QUAN_UNIT";
    private static final String QU_ID = "QU_ID";
    private static final String QI_ID = "QI_ID";
    private static final String QC_ID = "QC_ID";
    private static final String QU_NAME = "QU_NAME";
    private static final String QU_TITLE = "QU_TITLE";
    private static final String QU_ORDER = "QU_ORDER";
    private static final String QU_BASE = "QU_BASE";
    private static final String QU_RATE = "QU_RATE";
    private static final String QU_MODIFYTIME = "QU_MODIFYTIME";
    private static final String QUERY_F_QUAN_UNIT = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s", "QU_ID", "QI_ID", "QC_ID", "QU_NAME", "QU_TITLE", "QU_ORDER", "QU_BASE", "QU_RATE", "QU_MODIFYTIME");
    private static final Function<ResultSet, QuantityUnit> ENTITY_READER_QUANTITY_UNIT = rs -> {
        QuantityUnit quantityUnit = new QuantityUnit();
        int index = 1;
        try {
            quantityUnit.setId(rs.getString(index));
            quantityUnit.setQuantityId(rs.getString(++index));
            quantityUnit.setCategoryId(rs.getString(++index));
            quantityUnit.setName(rs.getString(++index));
            quantityUnit.setTitle(rs.getString(++index));
            quantityUnit.setOrder(rs.getString(++index));
            quantityUnit.setBase(rs.getBoolean(++index));
            quantityUnit.setRate(rs.getDouble(++index));
            quantityUnit.setModifyTime(rs.getTimestamp(++index).getTime());
        }
        catch (SQLException e) {
            throw new RuntimeException("read QuantityCategory category error.", e);
        }
        return quantityUnit;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addQuantityUnit(QuantityUnit quantityUnit) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_QUAN_UNIT, QU_ID, QI_ID, QC_ID, QU_NAME, QU_TITLE, QU_ORDER, QU_BASE, QU_RATE, QU_MODIFYTIME);
        this.jdbcTemplate.update(addSql, new Object[]{quantityUnit.getId(), quantityUnit.getQuantityId(), quantityUnit.getCategoryId(), quantityUnit.getName(), quantityUnit.getTitle(), quantityUnit.getOrder(), quantityUnit.isBase(), quantityUnit.getRate(), new Timestamp(quantityUnit.getModifyTime())});
    }

    public void updateQuantityUnit(QuantityUnit quantityUnit) {
        String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_QUAN_UNIT, QU_TITLE, QU_ORDER, QU_BASE, QU_RATE, QU_MODIFYTIME, QU_ID);
        this.jdbcTemplate.update(updateSql, new Object[]{quantityUnit.getTitle(), quantityUnit.getOrder(), quantityUnit.isBase(), quantityUnit.getRate(), new Timestamp(quantityUnit.getModifyTime()), quantityUnit.getId()});
    }

    public void updateQuantityUnitByName(QuantityUnit quantityUnit) {
        String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_QUAN_UNIT, QU_ID, QU_TITLE, QU_ORDER, QU_BASE, QU_RATE, QU_MODIFYTIME, QU_NAME);
        this.jdbcTemplate.update(updateSql, new Object[]{quantityUnit.getId(), quantityUnit.getTitle(), quantityUnit.getOrder(), quantityUnit.isBase(), quantityUnit.getRate(), new Timestamp(quantityUnit.getModifyTime()), quantityUnit.getName()});
    }

    public void deleteQuantityUnitById(String id) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s=?", TABLE_QUAN_UNIT, QU_ID);
        this.jdbcTemplate.update(deleteSql, new Object[]{id});
    }

    public void deleteQuantityUnitByCategoryId(String categoryId) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s=?", TABLE_QUAN_UNIT, QC_ID);
        this.jdbcTemplate.update(deleteSql, new Object[]{categoryId});
    }

    public void deleteQuantityUnitByQuanId(String quantityId) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s=?", TABLE_QUAN_UNIT, QI_ID);
        this.jdbcTemplate.update(deleteSql, new Object[]{quantityId});
    }

    public QuantityUnit getQuantityUnitById(String id) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_UNIT, TABLE_QUAN_UNIT, QU_ID);
        return (QuantityUnit)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return ENTITY_READER_QUANTITY_UNIT.apply(rs);
            }
            return null;
        }, new Object[]{id});
    }

    public QuantityUnit getQuantityUnitByName(String name) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_UNIT, TABLE_QUAN_UNIT, QU_NAME);
        return (QuantityUnit)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return ENTITY_READER_QUANTITY_UNIT.apply(rs);
            }
            return null;
        }, new Object[]{name});
    }

    public List<QuantityUnit> getQuantityUnitByCategoryId(String categoryId) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_UNIT, TABLE_QUAN_UNIT, QC_ID);
        return this.jdbcTemplate.query(querySql, (rs, row) -> ENTITY_READER_QUANTITY_UNIT.apply(rs), new Object[]{categoryId});
    }

    public List<QuantityUnit> getQuantityUnitByQuantityId(String quantityId) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUAN_UNIT, TABLE_QUAN_UNIT, QI_ID);
        return this.jdbcTemplate.query(querySql, (rs, row) -> ENTITY_READER_QUANTITY_UNIT.apply(rs), new Object[]{quantityId});
    }

    public List<QuantityUnit> fuzzyQueryQuantityUnit(String keywords) {
        String fuzzyQuerySql = String.format("SELECT %s FROM %s WHERE %s like ? or %s like ?", QUERY_F_QUAN_UNIT, TABLE_QUAN_UNIT, QU_NAME, QU_TITLE);
        return this.jdbcTemplate.query(fuzzyQuerySql, (rs, row) -> ENTITY_READER_QUANTITY_UNIT.apply(rs), new Object[]{"%" + keywords + "%", "%" + keywords + "%"});
    }

    public int validateName(String id, String name) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s ";
        ArrayList<String> args = new ArrayList<String>(2);
        String nameSQL = String.format("%s = ?", QU_NAME);
        args.add(name);
        String idSQL = String.format("AND %s <> ?", QU_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s ", QU_ID, TABLE_QUAN_UNIT, nameSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }

    public int validateTitle(String id, String categoryId, String title) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s %s";
        ArrayList<String> args = new ArrayList<String>(4);
        String qcIdSQL = String.format("%s = ?", QC_ID);
        args.add(categoryId);
        String titleSQL = String.format("AND %s = ?", QU_TITLE);
        args.add(title);
        String idSQL = String.format("AND %s <> ?", QU_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s %s", QU_ID, TABLE_QUAN_UNIT, qcIdSQL, titleSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }

    public int validateBase(String id, String categoryId) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s %s";
        ArrayList<String> args = new ArrayList<String>(3);
        String qcIdSQL = String.format("%s = ?", QC_ID);
        args.add(categoryId);
        String baseSQL = String.format("AND %s = 1", QU_BASE);
        String idSQL = String.format("AND %s <> ?", QU_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s %s", QU_ID, TABLE_QUAN_UNIT, qcIdSQL, baseSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }
}

