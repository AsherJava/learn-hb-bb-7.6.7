/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.unit.treestore.uselector.daoimpl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.treestore.uselector.dao.USerarchTempDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
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
@Transactional(rollbackFor={NpRollbackException.class})
public class SerarchTempDaoImpl
implements USerarchTempDao {
    private static final Logger log = LoggerFactory.getLogger(SerarchTempDaoImpl.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insert(String selector, List<IEntityRow> rows) {
        int count = 0;
        int pagesize = 5000;
        int totalCount = rows.size();
        double orderStartIdx = 0.0;
        if (totalCount <= pagesize) {
            count = this.batchInsert(selector, orderStartIdx, rows);
        } else {
            int totalPage = totalCount % pagesize == 0 ? totalCount / pagesize : totalCount / pagesize + 1;
            for (int currentPage = 0; currentPage < totalPage; ++currentPage) {
                int fromIndex = currentPage * pagesize;
                int toIndex = fromIndex + pagesize >= totalCount ? totalCount : fromIndex + pagesize;
                count += this.batchInsert(selector, orderStartIdx += (double)fromIndex, rows.subList(fromIndex, toIndex));
            }
        }
        return count;
    }

    private int batchInsert(String selector, double orderStartIdx, List<IEntityRow> rows) {
        MapSqlParameterSource[] sources = this.buildBatchSqlParameter(selector, orderStartIdx, rows);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        return jdbcInsert.withTableName("unit_selector_search_temp").executeBatch((SqlParameterSource[])sources).length;
    }

    private MapSqlParameterSource[] buildBatchSqlParameter(String selector, double orderStartIdx, List<IEntityRow> rows) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[rows.size()];
        double orderIndex = orderStartIdx;
        for (int i = 0; i < rows.size(); ++i) {
            sources[i] = this.buildSqlParameter(selector, rows.get(i), orderIndex += 1.0);
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameter(String selector, IEntityRow row, double index) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("st_selector", (Object)selector);
        source.addValue("st_id", (Object)row.getEntityKeyData());
        source.addValue("st_code", (Object)row.getCode());
        source.addValue("st_title", (Object)row.getTitle());
        source.addValue("st_order", (Object)index);
        return source;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int clear(String selector) {
        ArrayList<String> selectors = new ArrayList<String>();
        selectors.add(selector);
        return this.clear(selectors);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int clear(List<String> selectors) {
        String sql = String.format("DELETE FROM %s WHERE st_selector IN (:%s)", "unit_selector_search_temp", "st_selector");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("st_selector", selectors);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    @Override
    public List<String> contains(String selector, boolean isExactMatch, List<String> codeValues, List<String> titleValues) {
        String baseSQL = this.buildBaseSql(false, isExactMatch, selector, codeValues, titleValues);
        return this.jdbcTemplate.query(baseSQL, this::readResultSet);
    }

    @Override
    public List<String> containsOnePage(String selector, boolean isExactMatch, int fromIndex, int toIndex, List<String> codeValues, List<String> titleValues) {
        List onePage = null;
        try (Connection conn = this.jdbcTemplate.getDataSource().getConnection();){
            String baseSQL = this.buildBaseSql(false, isExactMatch, selector, codeValues, titleValues);
            IDatabase idatabase = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            IPagingSQLBuilder pageBuilder = idatabase.createPagingSQLBuilder();
            pageBuilder.setRawSQL(baseSQL);
            String pageQuerySQL = pageBuilder.buildSQL(fromIndex, toIndex);
            onePage = this.jdbcTemplate.query(pageQuerySQL, this::readResultSet);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return onePage;
    }

    @Override
    public int count(String selector, boolean isExactMatch, List<String> codeValues, List<String> titleValues) {
        String baseSQL = this.buildBaseSql(true, isExactMatch, selector, codeValues, titleValues);
        List query = this.jdbcTemplate.query(baseSQL, this::readCount);
        return !query.isEmpty() ? (Integer)query.get(0) : 0;
    }

    private String buildBaseSql(boolean isCount, boolean isExactMatch, String selector, List<String> codeValues, List<String> titleValues) {
        StringBuilder sql = new StringBuilder();
        if (isCount) {
            sql.append(" SELECT count(").append("t.").append("st_id").append(")");
        } else {
            sql.append(" SELECT ").append("t.").append("st_id");
        }
        sql.append(" FROM ").append("unit_selector_search_temp").append(" t ");
        sql.append(" WHERE ");
        sql.append(" t.").append("st_selector").append(" = '").append(selector).append("'");
        sql.append(" AND ");
        sql.append(" ( ");
        if (this.appendCodeCondi(sql, isExactMatch, codeValues) && !titleValues.isEmpty()) {
            sql.append(" OR ");
        }
        this.appendTitleCondi(sql, isExactMatch, titleValues);
        sql.append(" ) ");
        sql.append(" ORDER BY t.").append("st_order");
        return sql.toString();
    }

    private boolean appendCodeCondi(StringBuilder sql, boolean isExactMatch, List<String> codeValues) {
        if (!codeValues.isEmpty()) {
            if (isExactMatch) {
                sql.append(" t.").append("st_code");
                sql.append(" IN ( ");
                sql.append("'").append(codeValues.get(0)).append("'");
                for (int i = 1; i < codeValues.size(); ++i) {
                    sql.append(", ");
                    sql.append("'").append(codeValues.get(i)).append("'");
                }
                sql.append(" ) ");
            } else {
                sql.append(" t.").append("st_code").append(" LIKE '%").append(codeValues.get(0)).append("%'");
                for (int i = 1; i < codeValues.size(); ++i) {
                    sql.append(" OR ");
                    sql.append(" t.").append("st_code").append(" LIKE '%").append(codeValues.get(i)).append("%'");
                }
            }
            return true;
        }
        return false;
    }

    private boolean appendTitleCondi(StringBuilder sql, boolean isExactMatch, List<String> titleValues) {
        if (!titleValues.isEmpty()) {
            if (isExactMatch) {
                sql.append(" t.").append("st_title");
                sql.append(" IN ( ");
                sql.append("'").append(titleValues.get(0)).append("'");
                for (int i = 1; i < titleValues.size(); ++i) {
                    sql.append(", ");
                    sql.append("'").append(titleValues.get(i)).append("'");
                }
                sql.append(" ) ");
            } else {
                sql.append(" t.").append("st_title").append(" LIKE '%").append(titleValues.get(0)).append("%'");
                for (int i = 1; i < titleValues.size(); ++i) {
                    sql.append(" OR ");
                    sql.append(" t.").append("st_title").append(" LIKE '%").append(titleValues.get(i)).append("%'");
                }
            }
            return true;
        }
        return false;
    }

    private String readResultSet(ResultSet rs, int rowIdx) throws SQLException {
        return rs.getString("st_id");
    }

    private int readCount(ResultSet rs, int rowIdx) throws SQLException {
        return rs.getInt(1);
    }
}

