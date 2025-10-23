/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.quantity.dao;

import com.jiuqi.nr.quantity.bean.QuantityInfo;
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
public class QuantityInfoDao {
    private static final String TABLE_QUANTITY = "NR_QUANTITY_INFO";
    private static final String QI_ID = "QI_ID";
    private static final String QI_NAME = "QI_NAME";
    private static final String QI_TITLE = "QI_TITLE";
    private static final String QI_ORDER = "QI_ORDER";
    private static final String QI_MODIFYTIME = "QI_MODIFYTIME";
    private static final String QUERY_F_QUANTITY = String.format("%s, %s, %s, %s, %s", "QI_ID", "QI_NAME", "QI_TITLE", "QI_ORDER", "QI_MODIFYTIME");
    private static final Function<ResultSet, QuantityInfo> ENTITY_READER_QUANTITY_INFO = rs -> {
        QuantityInfo quantityInfo = new QuantityInfo();
        int index = 1;
        try {
            quantityInfo.setId(rs.getString(index));
            quantityInfo.setName(rs.getString(++index));
            quantityInfo.setTitle(rs.getString(++index));
            quantityInfo.setOrder(rs.getString(++index));
            quantityInfo.setModifyTime(rs.getTimestamp(++index).getTime());
        }
        catch (SQLException e) {
            throw new RuntimeException("read QuantityInfo quantity error.", e);
        }
        return quantityInfo;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addQuantityInfo(QuantityInfo quantityInfo) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES(?, ?, ?, ?, ?)", TABLE_QUANTITY, QI_ID, QI_NAME, QI_TITLE, QI_ORDER, QI_MODIFYTIME);
        this.jdbcTemplate.update(addSql, new Object[]{quantityInfo.getId(), quantityInfo.getName(), quantityInfo.getTitle(), quantityInfo.getOrder(), new Timestamp(quantityInfo.getModifyTime())});
    }

    public void updateQuantityInfo(QuantityInfo quantityInfo) {
        String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=? WHERE %s=?", TABLE_QUANTITY, QI_TITLE, QI_ORDER, QI_MODIFYTIME, QI_ID);
        this.jdbcTemplate.update(updateSql, new Object[]{quantityInfo.getTitle(), quantityInfo.getOrder(), new Timestamp(quantityInfo.getModifyTime()), quantityInfo.getId()});
    }

    public void updateQuantityInfoByName(QuantityInfo quantityInfo) {
        String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_QUANTITY, QI_ID, QI_TITLE, QI_ORDER, QI_MODIFYTIME, QI_NAME);
        this.jdbcTemplate.update(updateSql, new Object[]{quantityInfo.getId(), quantityInfo.getTitle(), quantityInfo.getOrder(), new Timestamp(quantityInfo.getModifyTime()), quantityInfo.getName()});
    }

    public void updateQuantityInfoModifyTime(String quantityId, long modifyTime) {
        String updateSql = String.format("UPDATE %s SET %s=? WHERE %s=?", TABLE_QUANTITY, QI_MODIFYTIME, QI_ID);
        this.jdbcTemplate.update(updateSql, new Object[]{new Timestamp(modifyTime), quantityId});
    }

    public void deleteQuantityInfoById(String id) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s=?", TABLE_QUANTITY, QI_ID);
        this.jdbcTemplate.update(deleteSql, new Object[]{id});
    }

    public QuantityInfo getQuantityInfoById(String id) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUANTITY, TABLE_QUANTITY, QI_ID);
        return (QuantityInfo)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return ENTITY_READER_QUANTITY_INFO.apply(rs);
            }
            return null;
        }, new Object[]{id});
    }

    public QuantityInfo getQuantityInfoByName(String name) {
        String querySql = String.format("SELECT %s from %s WHERE %s=?", QUERY_F_QUANTITY, TABLE_QUANTITY, QI_NAME);
        return (QuantityInfo)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return ENTITY_READER_QUANTITY_INFO.apply(rs);
            }
            return null;
        }, new Object[]{name});
    }

    public List<QuantityInfo> getAllQuantityInfo() {
        String querySql = String.format("SELECT %s from %s", QUERY_F_QUANTITY, TABLE_QUANTITY);
        return this.jdbcTemplate.query(querySql, (rs, row) -> ENTITY_READER_QUANTITY_INFO.apply(rs));
    }

    public List<QuantityInfo> fuzzyQueryQuantityInfo(String keywords) {
        String fuzzyQuerySql = String.format("SELECT %s FROM %s WHERE %s like ? or %s like ?", QUERY_F_QUANTITY, TABLE_QUANTITY, QI_NAME, QI_TITLE);
        return this.jdbcTemplate.query(fuzzyQuerySql, (rs, row) -> ENTITY_READER_QUANTITY_INFO.apply(rs), new Object[]{"%" + keywords + "%", "%" + keywords + "%"});
    }

    public int validateName(String id, String name) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s ";
        ArrayList<String> args = new ArrayList<String>(2);
        String nameSQL = String.format("%s = ?", QI_NAME);
        args.add(name);
        String idSQL = String.format("AND %s <> ?", QI_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s ", QI_ID, TABLE_QUANTITY, nameSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }

    public int validateTitle(String id, String title) {
        String VALIDATE_SQL = "SELECT COUNT(%s) FROM %s WHERE %s %s ";
        ArrayList<String> args = new ArrayList<String>(2);
        String titleSQL = String.format("%s = ?", QI_TITLE);
        args.add(title);
        String idSQL = String.format("AND %s <> ?", QI_ID);
        if (!StringUtils.hasLength(id)) {
            idSQL = "";
        } else {
            args.add(id);
        }
        String querySql = String.format("SELECT COUNT(%s) FROM %s WHERE %s %s ", QI_ID, TABLE_QUANTITY, titleSQL, idSQL);
        Integer query = (Integer)this.jdbcTemplate.query(querySql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, args.toArray());
        return query == null ? 0 : query;
    }
}

