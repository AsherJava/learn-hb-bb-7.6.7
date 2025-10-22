/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
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

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.unit.treestore.uselector.bean.FilterCacheSetItem;
import com.jiuqi.nr.unit.treestore.uselector.dao.IFilterCacheSetDao;
import com.jiuqi.nr.unit.treestore.uselector.dao.IFilterCacheSetTemDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.docx4j.com.google.common.collect.Iterators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SFilterSetDaoImpl
implements IFilterCacheSetDao {
    private static final double ORDER_RANGE = 50.0;
    private static final Logger log = LoggerFactory.getLogger(SFilterSetDaoImpl.class);
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IFilterCacheSetTemDao temTable;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insert(String selector, Set<String> entDataKeys) {
        int count = 0;
        if (null != entDataKeys && !entDataKeys.isEmpty()) {
            int pagesize = 1000;
            int totalCount = entDataKeys.size();
            double orderValue = this.maxOrder(selector);
            if (totalCount < 1000) {
                count = this.batchInsert(selector, orderValue, entDataKeys.iterator(), entDataKeys.size());
            } else {
                int totalPage = totalCount % 1000 == 0 ? totalCount / 1000 : totalCount / 1000 + 1;
                for (int currentPage = 0; currentPage < totalPage; ++currentPage) {
                    int startIndex = currentPage * 1000;
                    int endIndex = startIndex + 1000 >= totalCount ? totalCount : startIndex + 1000;
                    orderValue = (double)startIndex * 50.0;
                    count += this.batchInsert(selector, orderValue, Iterators.limit(entDataKeys.iterator(), (int)1000), endIndex - startIndex);
                }
            }
        }
        return count;
    }

    private int batchInsert(String selector, double orderValue, Iterator<String> iterator, int pagesize) {
        MapSqlParameterSource[] sources = this.buildBatchSqlParameter(selector, orderValue, iterator, pagesize);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        return jdbcInsert.withTableName("unit_selector_filter_set").executeBatch((SqlParameterSource[])sources).length;
    }

    private MapSqlParameterSource[] buildBatchSqlParameter(String selector, double orderValue, Iterator<String> iterator, int pagesize) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[pagesize];
        int i = 0;
        while (iterator.hasNext()) {
            sources[i] = this.buildSqlParameter(selector, iterator.next(), orderValue);
            iterator.remove();
            ++i;
            orderValue += 50.0;
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameter(String selector, String entityDataKey, double order) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        source.addValue("fs_entity_data", (Object)entityDataKey);
        source.addValue("fs_order", (Object)order);
        return source;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int update(String selector, List<FilterCacheSetItem> rows) {
        String sql = String.format("UPDATE %s SET fs_order = :%s WHERE fs_selector = :%s AND fs_entity_data = :%s", "unit_selector_filter_set", "fs_order", "fs_selector", "fs_entity_data");
        MapSqlParameterSource[] sources = new MapSqlParameterSource[rows.size()];
        for (int i = 0; i < rows.size(); ++i) {
            FilterCacheSetItem rowInfo = rows.get(i);
            sources[i] = new MapSqlParameterSource();
            sources[i].addValue("fs_selector", (Object)selector);
            sources[i].addValue("fs_entity_data", (Object)rowInfo.getEntityDataKey());
            sources[i].addValue("fs_order", (Object)rowInfo.getOrder());
        }
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])sources).length;
    }

    @Override
    public int count(String selector) {
        String sql = String.format("SELECT count(fs_entity_data) AS fs_count FROM %s WHERE fs_selector = :%s ORDER BY %s ", "unit_selector_filter_set", "fs_selector", "fs_order");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        Integer count = (Integer)template.queryForObject(sql, (SqlParameterSource)source, Integer.class);
        return null != count ? count : 0;
    }

    @Override
    public double maxOrder(String selector) {
        String sql = String.format("SELECT MAX(fs_order) AS fs_order FROM %s WHERE fs_selector = :%s ", "unit_selector_filter_set", "fs_selector");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        Double maxOrder = (Double)template.queryForObject(sql, (SqlParameterSource)source, Double.class);
        maxOrder = null == maxOrder ? 0.0 : Double.sum(maxOrder.intValue(), 50.0);
        return maxOrder;
    }

    @Override
    public List<String> findOnePage(String selector, int fromIndex, int toIndex) {
        List onePage = null;
        try (Connection conn = this.jdbcTemplate.getDataSource().getConnection();){
            String pageQuerySQL = this.buildPageQuerySQL(conn, selector, fromIndex, toIndex);
            onePage = this.jdbcTemplate.query(pageQuerySQL, this::readResultSet);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return onePage;
    }

    @Override
    public List<String> findAllPages(String selector) {
        String sql = String.format("SELECT %s FROM %s WHERE fs_selector = :%s ORDER BY %s ", "fs_entity_data", "unit_selector_filter_set", "fs_selector", "fs_order");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readResultSet);
    }

    @Override
    public List<String> findRows(String selector, List<String> entityDataKeys) {
        int pagesize = 1000;
        if (entityDataKeys.size() <= 1000) {
            return this.queryRows(selector, entityDataKeys);
        }
        ArrayList<String> list = new ArrayList<String>();
        int totalCount = entityDataKeys.size();
        int totalPage = totalCount % 1000 == 0 ? totalCount / 1000 : totalCount / 1000 + 1;
        for (int currentPage = 0; currentPage < totalPage; ++currentPage) {
            int fromIndex = currentPage * 1000;
            int toIndex = fromIndex + 1000 >= totalCount ? totalCount : fromIndex + 1000;
            List<String> subList = entityDataKeys.subList(fromIndex, toIndex);
            List<String> rs = this.queryRows(selector, subList);
            list.addAll(rs);
        }
        return list;
    }

    @Override
    public List<FilterCacheSetItem> findRowDetails(String selector, List<String> entityDataKeys) {
        String sql = String.format("SELECT %s, %s FROM %s WHERE fs_selector = :%s AND fs_entity_data in(:%s) ORDER BY %s ", "fs_entity_data", "fs_order", "unit_selector_filter_set", "fs_selector", "fs_entity_data", "fs_order");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        source.addValue("fs_entity_data", entityDataKeys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readRow);
    }

    @Override
    public int remove(List<String> selectors) {
        String sql = String.format("DELETE FROM %s WHERE fs_selector IN (:%s)", "unit_selector_filter_set", "fs_selector");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", selectors);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    @Override
    public int removeAll(String selector, Set<String> entityDataKeys) {
        int count = 0;
        if (entityDataKeys != null && !entityDataKeys.isEmpty()) {
            int pagesize = 1000;
            int totalCount = entityDataKeys.size();
            if (totalCount < 1000) {
                count = this.removeItems(selector, entityDataKeys.iterator());
            } else {
                int totalPage = totalCount % 1000 == 0 ? totalCount / 1000 : totalCount / 1000 + 1;
                for (int currentPage = 0; currentPage < totalPage; ++currentPage) {
                    count += this.removeItems(selector, Iterators.limit(entityDataKeys.iterator(), (int)1000));
                }
            }
        }
        return count;
    }

    private int removeItems(String selector, Iterator<String> limit) {
        ArrayList<String> entityDataKeys = new ArrayList<String>();
        while (limit.hasNext()) {
            entityDataKeys.add(limit.next());
        }
        String sql = String.format("DELETE FROM %s WHERE fs_selector = :%s AND fs_entity_data IN (:%s)", "unit_selector_filter_set", "fs_selector", "fs_entity_data");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        source.addValue("fs_entity_data", entityDataKeys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private List<String> queryRows(String selector, List<String> entityDataKeys) {
        String sql = String.format("SELECT %s FROM %s WHERE fs_selector = :%s AND fs_entity_data in(:%s) ORDER BY %s ", "fs_entity_data", "unit_selector_filter_set", "fs_selector", "fs_entity_data", "fs_order");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        source.addValue("fs_entity_data", entityDataKeys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readResultSet);
    }

    private String buildPageQuerySQL(Connection conn, String selector, int startIdx, int endIndex) throws SQLException, DBException {
        StringBuilder baseSQL = new StringBuilder();
        baseSQL.append(" SELECT fs_entity_data");
        baseSQL.append(" FROM ").append("unit_selector_filter_set");
        baseSQL.append(" WHERE fs_selector = ").append("'").append(selector).append("'");
        baseSQL.append(" ORDER BY fs_order");
        IDatabase idatabase = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        IPagingSQLBuilder pageBuilder = idatabase.createPagingSQLBuilder();
        pageBuilder.setRawSQL(baseSQL.toString());
        return pageBuilder.buildSQL(startIdx, endIndex);
    }

    private String readResultSet(ResultSet rs, int rowIdx) throws SQLException {
        return rs.getString("fs_entity_data");
    }

    private FilterCacheSetItem readRow(ResultSet rs, int rowIdx) throws SQLException {
        FilterCacheSetItem item = new FilterCacheSetItem();
        item.setEntityDataKey(rs.getString("fs_entity_data"));
        item.setOrder(rs.getDouble("fs_order"));
        return item;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int intersection(String selector, Set<String> filterSet) {
        this.temTable.clear(selector);
        this.temTable.insert(selector, filterSet);
        String sql = this.intersectionSql();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fs_selector", (Object)selector);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private String intersectionSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE FROM ");
        sql.append("unit_selector_filter_set");
        sql.append(" WHERE ");
        sql.append("unit_selector_filter_set").append(".").append("fs_selector").append(" = ");
        sql.append(" :").append("fs_selector");
        sql.append(" AND NOT EXISTS ");
        sql.append(" (");
        sql.append(" SELECT 1 ");
        sql.append(" FROM ");
        sql.append("unit_selector_filter_set_temp");
        sql.append(" WHERE ");
        sql.append("unit_selector_filter_set").append(".").append("fs_entity_data").append(" = ");
        sql.append("unit_selector_filter_set_temp").append(".").append("fs_entity_data");
        sql.append(" AND ");
        sql.append("unit_selector_filter_set").append(".").append("fs_selector").append(" = ");
        sql.append("unit_selector_filter_set_temp").append(".").append("fs_selector");
        sql.append(" )");
        return sql.toString();
    }
}

