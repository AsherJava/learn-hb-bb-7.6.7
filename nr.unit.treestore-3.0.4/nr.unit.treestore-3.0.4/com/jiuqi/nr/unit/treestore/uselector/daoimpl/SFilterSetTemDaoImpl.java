/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  org.docx4j.com.google.common.collect.Iterators
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.unit.treestore.uselector.daoimpl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.unit.treestore.uselector.dao.IFilterCacheSetTemDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.docx4j.com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor={NpRollbackException.class})
public class SFilterSetTemDaoImpl
implements IFilterCacheSetTemDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Set<String> notExist(String selector, Set<String> filterSet) {
        this.clear(selector);
        this.insert(selector, filterSet);
        String baseSql = this.baseSql();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List list = template.query(baseSql, (SqlParameterSource)source, this::readResultSet);
        return new LinkedHashSet<String>(list);
    }

    private String readResultSet(ResultSet rs, int rowIdx) throws SQLException {
        return rs.getString("fs_entity_data");
    }

    private String baseSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ").append("t1.fs_entity_data");
        sql.append(" FROM ").append("unit_selector_filter_set_temp").append(" t1");
        sql.append("  WHERE  ");
        sql.append("t1.").append("fs_selector").append(" = ").append(" :").append("fs_selector");
        sql.append(" AND NOT EXISTS ");
        sql.append(" (");
        sql.append(" SELECT 1 ");
        sql.append(" FROM ").append("unit_selector_filter_set").append(" t2");
        sql.append(" WHERE ");
        sql.append("t1.").append("fs_entity_data").append(" = ");
        sql.append("t2.").append("fs_entity_data");
        sql.append(" AND ");
        sql.append("t1.").append("fs_selector").append(" = ");
        sql.append("t2.").append("fs_selector");
        sql.append(" ) ORDER BY ").append("t1.fs_order");
        return sql.toString();
    }

    @Override
    public int insert(String selector, Set<String> rows) {
        int count = 0;
        int pagesize = 5000;
        int totalCount = rows.size();
        if (totalCount <= 5000) {
            count = this.batchInsert(selector, rows.iterator(), 0.0, rows.size());
        } else {
            int totalPage = totalCount % 5000 == 0 ? totalCount / 5000 : totalCount / 5000 + 1;
            for (int currentPage = 0; currentPage < totalPage; ++currentPage) {
                int startIndex = currentPage * 5000;
                int endIndex = startIndex + 5000 >= totalCount ? totalCount : startIndex + 5000;
                count += this.batchInsert(selector, Iterators.limit(rows.iterator(), (int)5000), startIndex, endIndex - startIndex);
            }
        }
        return count;
    }

    private int batchInsert(String selector, Iterator<String> iterator, double orderStartIdx, int pagesize) {
        MapSqlParameterSource[] sources = this.buildBatchSqlParameter(selector, iterator, orderStartIdx, pagesize);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        return jdbcInsert.withTableName("unit_selector_filter_set_temp").executeBatch((SqlParameterSource[])sources).length;
    }

    private MapSqlParameterSource[] buildBatchSqlParameter(String selector, Iterator<String> iterator, double orderStartIdx, int pagesize) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[pagesize];
        int i = 0;
        double orderIndex = orderStartIdx;
        while (iterator.hasNext()) {
            sources[i] = this.buildSqlParameter(selector, iterator.next(), orderIndex);
            ++i;
            orderIndex += 1.0;
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameter(String selector, String entDataKey, double index) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        source.addValue("fs_entity_data", (Object)entDataKey);
        source.addValue("fs_order", (Object)index);
        return source;
    }

    @Override
    public int clear(String selector) {
        ArrayList<String> selectors = new ArrayList<String>();
        selectors.add(selector);
        return this.clear(selectors);
    }

    @Override
    public int clear(List<String> selectors) {
        String sql = String.format("DELETE FROM %s WHERE fs_selector IN (:%s)", "unit_selector_filter_set_temp", "fs_selector");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", selectors);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }
}

