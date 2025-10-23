/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MultcheckItemDao {
    private static final String TABLE_NAME = "NR_MULTCHECK_SCHEME_ITEM";
    private static final String KEY = "MSI_KEY";
    private static final String SCHEME = "MS_KEY";
    private static final String TITLE = "MSI_TITLE";
    private static final String TYPE = "MSI_TYPE";
    private static final String CONFIG = "MSI_CONFIG";
    private static final String ORDER = "MSI_ORDER";
    private static final String UPDATE_TIME = "MSI_UPDATE_TIME";
    private static final String MULTCHECK_ITEM;
    private static final Function<ResultSet, MultcheckItem> ENTITY_READER_MULTCHECK_ITEM;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(MultcheckItem item) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, MULTCHECK_ITEM);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{item.getKey(), item.getScheme(), item.getTitle(), item.getType(), item.getConfig(), item.getOrder(), new Timestamp(System.currentTimeMillis())});
    }

    public void batchAdd(List<MultcheckItem> items) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, MULTCHECK_ITEM);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (MultcheckItem item : items) {
            Object[] param = new Object[]{item.getKey(), item.getScheme(), item.getTitle(), item.getType(), item.getConfig(), item.getOrder(), new Timestamp(System.currentTimeMillis())};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public String modify(MultcheckItem mi) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ", TABLE_NAME, SCHEME, TITLE, TYPE, CONFIG, UPDATE_TIME, KEY);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{mi.getScheme(), mi.getTitle(), mi.getType(), mi.getConfig(), new Timestamp(System.currentTimeMillis()), mi.getKey()});
        return mi.getKey();
    }

    public void deleteByKey(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, KEY);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    public void deleteByScheme(String scheme) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, SCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{scheme});
    }

    public List<MultcheckItem> getByScheme(String multcheckScheme) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", MULTCHECK_ITEM, TABLE_NAME, SCHEME, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_ITEM.apply(rs), new Object[]{multcheckScheme});
    }

    public MultcheckItem getByKey(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MULTCHECK_ITEM, TABLE_NAME, KEY);
        return (MultcheckItem)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_MULTCHECK_ITEM.apply(rs);
            }
            return null;
        }, new Object[]{key});
    }

    public List<MultcheckItem> getInfoByScheme(String multcheckScheme) {
        StringBuilder builder = new StringBuilder();
        String fieldSql = builder.append(KEY).append(",").append(SCHEME).append(",").append(TITLE).append(",").append(TYPE).append(",").append(ORDER).append(",").append(UPDATE_TIME).toString();
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", fieldSql, TABLE_NAME, SCHEME, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> {
            MultcheckItem multcheckItem = new MultcheckItem();
            int index = 1;
            try {
                multcheckItem.setKey(rs.getString(index++));
                multcheckItem.setScheme(rs.getString(index++));
                multcheckItem.setTitle(rs.getString(index++));
                multcheckItem.setType(rs.getString(index++));
                multcheckItem.setOrder(rs.getString(index++));
                multcheckItem.setUpdateTime(rs.getTimestamp(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read multcheckItem error.", e);
            }
            return multcheckItem;
        }, new Object[]{multcheckScheme});
    }

    public String getConfig(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", CONFIG, TABLE_NAME, KEY);
        return (String)this.jdbcTemplate.queryForObject(SQL_QUERY, String.class, new Object[]{key});
    }

    public void move(MultcheckItem source, MultcheckItem target) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ? ", TABLE_NAME, ORDER, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[]{target.getOrder(), source.getKey()});
        args.add(new Object[]{source.getOrder(), target.getKey()});
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, args);
    }

    static {
        StringBuilder builder = new StringBuilder();
        MULTCHECK_ITEM = builder.append(KEY).append(",").append(SCHEME).append(",").append(TITLE).append(",").append(TYPE).append(",").append(CONFIG).append(",").append(ORDER).append(",").append(UPDATE_TIME).toString();
        ENTITY_READER_MULTCHECK_ITEM = rs -> {
            MultcheckItem multcheckItem = new MultcheckItem();
            int index = 1;
            try {
                multcheckItem.setKey(rs.getString(index++));
                multcheckItem.setScheme(rs.getString(index++));
                multcheckItem.setTitle(rs.getString(index++));
                multcheckItem.setType(rs.getString(index++));
                multcheckItem.setConfig(rs.getString(index++));
                multcheckItem.setOrder(rs.getString(index++));
                multcheckItem.setUpdateTime(rs.getTimestamp(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read multcheckItem error.", e);
            }
            return multcheckItem;
        };
    }
}

