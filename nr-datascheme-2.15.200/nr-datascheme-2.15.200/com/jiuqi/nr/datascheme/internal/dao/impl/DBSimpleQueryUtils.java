/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class DBSimpleQueryUtils {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public Map<String, String> queryForMap(String tableName, String keyFieldName, String valueFieldName) {
        String sql = String.format("SELECT %s, %s FROM %s ", keyFieldName, valueFieldName, tableName);
        return (Map)this.jdbcTemplate.query(sql, rs -> {
            HashMap<String, String> result = new HashMap<String, String>();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
            return result;
        });
    }

    public Map<String, String> queryForMap(String tableName, String keyFieldName, String valueFieldName, String valueFieldValue) {
        String sql = String.format("SELECT %s, %s FROM %s WHERE %s = ? ", keyFieldName, valueFieldName, tableName, valueFieldName);
        return (Map)this.jdbcTemplate.query(sql, ps -> ps.setString(1, valueFieldValue), rs -> {
            HashMap<String, String> result = new HashMap<String, String>();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
            return result;
        });
    }

    public Map<String, Map<String, String>> queryForMap(String tableName, List<String> keyFieldNames, String valueFieldName) {
        if (CollectionUtils.isEmpty(keyFieldNames)) {
            return Collections.emptyMap();
        }
        String sql = String.format("SELECT %s, %s FROM %s ", valueFieldName, keyFieldNames.stream().collect(Collectors.joining(",")), tableName);
        return (Map)this.jdbcTemplate.query(sql, rs -> {
            HashMap<String, Map> result = new HashMap<String, Map>();
            while (rs.next()) {
                String value = rs.getString(valueFieldName);
                for (String keyFieldName : keyFieldNames) {
                    String key = rs.getString(keyFieldName);
                    result.compute(keyFieldName, (k, v) -> null == v ? new HashMap() : v).put(key, value);
                }
            }
            return result;
        });
    }

    public Map<String, Map<String, String>> queryForMap(String tableName, List<String> keyFieldNames, String valueFieldName, String valueFieldValue) {
        if (CollectionUtils.isEmpty(keyFieldNames)) {
            return Collections.emptyMap();
        }
        String sql = String.format("SELECT %s, %s FROM %s WHERE %s = ? ", valueFieldName, String.join((CharSequence)",", keyFieldNames), tableName, valueFieldName);
        return (Map)this.jdbcTemplate.query(sql, ps -> ps.setString(1, valueFieldValue), rs -> {
            HashMap<String, Map> result = new HashMap<String, Map>();
            while (rs.next()) {
                String value = rs.getString(valueFieldName);
                for (String keyFieldName : keyFieldNames) {
                    String key = rs.getString(keyFieldName);
                    result.compute(keyFieldName, (k, v) -> null == v ? new HashMap() : v).put(key, value);
                }
            }
            return result;
        });
    }

    public Map<String, String> queryForMap(String sql, Object[] args, String keyFieldName, String valueFieldName) {
        return (Map)this.jdbcTemplate.query(sql, ps -> {
            if (null != args && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    ps.setObject(i + 1, args[i]);
                }
            }
        }, rs -> {
            HashMap<String, String> result = new HashMap<String, String>();
            while (rs.next()) {
                result.put(rs.getString(keyFieldName), rs.getString(valueFieldName));
            }
            return result;
        });
    }
}

