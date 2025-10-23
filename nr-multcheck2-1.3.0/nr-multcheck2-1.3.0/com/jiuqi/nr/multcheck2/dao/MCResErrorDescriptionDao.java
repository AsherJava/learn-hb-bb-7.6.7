/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@Deprecated
public class MCResErrorDescriptionDao {
    private static final int SIZE = 9;
    private static final String ERROR;
    private static final Function<ResultSet, MCErrorDescription> ENTITY_READER_MULTCHECK_ERROR;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String tableName, MCErrorDescription error, List<String> dims) {
        String dimSql = this.getFieldSql(dims);
        int fieldSize = CollectionUtils.isEmpty(dims) ? 9 : 9 + dims.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldSize; ++i) {
            sb.append("?");
            if (i >= fieldSize - 1) continue;
            sb.append(",");
        }
        String markStr = sb.toString();
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, dimSql, markStr);
        this.jdbcTemplate.update(SQL_INSERT, this.getFieldValues(error, dims).toArray());
    }

    public void batchAdd(String tableName, List<MCErrorDescription> errorDescriptions, List<String> dims) {
        String dimSql = this.getFieldSql(dims);
        int fieldSize = CollectionUtils.isEmpty(dims) ? 9 : 9 + dims.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldSize; ++i) {
            sb.append("?");
            if (i >= fieldSize - 1) continue;
            sb.append(",");
        }
        String markStr = sb.toString();
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, dimSql, markStr);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (MCErrorDescription error : errorDescriptions) {
            Object[] param = this.getFieldValues(error, dims).toArray();
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public List<MCErrorDescription> getByResource(String tableName, MCErrorDescription item, List<String> dims) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(this.getFieldSql(dims)).append(" FROM ").append(tableName).append(" WHERE ").append("MEI_TASK").append("=?").append(" AND ").append("MEI_PERIOD").append("=?").append(" AND ").append("MSI_TYPE").append("=?").append(" AND ").append("MEI_RESOURCE").append("=?");
        ArrayList<String> params = new ArrayList<String>();
        params.add(item.getTask());
        params.add(item.getPeriod());
        params.add(item.getItemType());
        params.add(item.getResource());
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> {
            MCErrorDescription error = ENTITY_READER_MULTCHECK_ERROR.apply(rs);
            if (!CollectionUtils.isEmpty(dims)) {
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, rs.getString(dim));
                }
            }
            return error;
        }, params.toArray());
    }

    public List<MCErrorDescription> getByOrg(String tableName, MCErrorDescription item, List<String> dims) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(this.getFieldSql(dims)).append(" FROM ").append(tableName).append(" WHERE ").append("MEI_TASK").append("=?").append(" AND ").append("MEI_PERIOD").append("=?").append(" AND ").append("MSI_TYPE").append("=?").append(" AND ").append("MEI_ORG").append("=?");
        ArrayList<String> params = new ArrayList<String>();
        params.add(item.getTask());
        params.add(item.getPeriod());
        params.add(item.getItemType());
        params.add(item.getOrg());
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> {
            MCErrorDescription error = ENTITY_READER_MULTCHECK_ERROR.apply(rs);
            if (!CollectionUtils.isEmpty(dims)) {
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, rs.getString(dim));
                }
            }
            return error;
        }, params.toArray());
    }

    private List<Object> getFieldValues(MCErrorDescription error, List<String> dims) {
        ArrayList<Object> params = new ArrayList<Object>();
        if (!CollectionUtils.isEmpty(dims)) {
            for (String dim : dims) {
                params.add(error.getDims().get(dim));
            }
        }
        params.add(error.getKey());
        params.add(error.getTask());
        params.add(error.getPeriod());
        params.add(error.getOrg());
        params.add(error.getItemType());
        params.add(error.getResource());
        params.add(error.getDescription());
        params.add(error.getTime());
        params.add(error.getUser());
        return params;
    }

    private String getFieldSql(List<String> dims) {
        String dimSql = ERROR;
        if (!CollectionUtils.isEmpty(dims)) {
            dimSql = dims.stream().collect(Collectors.joining(",")) + "," + ERROR;
        }
        return dimSql;
    }

    public void modify(String tableName, MCErrorDescription errorDescription) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ? ", tableName, "MEI_DESCRIPTION", "MEI_KEY");
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{errorDescription.getDescription(), errorDescription.getKey()});
    }

    public void deleteByKeys(String tableName, List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", tableName, "MEI_KEY");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    public void deleteByOrgAndModel(String tableName, List<List<String>> orgModelList) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", tableName, "MEI_ORG", "MEI_RESOURCE");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (List<String> l : orgModelList) {
            Object[] param = new Object[]{l.get(0), l.get(1)};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    public void deleteByKey(String tableName, String key) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", tableName, "MEI_KEY");
        this.jdbcTemplate.update(SQL_BATCH_DELETE, new Object[]{key});
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        String sql = String.format("delete from %s where %s < ?", tableName, "MEI_UPDATE_TIME");
        this.jdbcTemplate.update(sql, new Object[]{cleanDate});
    }

    public void cleanAllRecords(String tableName) {
        String sql = String.format("delete from %s where 1=1", tableName);
        this.jdbcTemplate.update(sql);
    }

    static {
        StringBuilder builder = new StringBuilder();
        ERROR = builder.append("MEI_KEY").append(",").append("MEI_TASK").append(",").append("MEI_PERIOD").append(",").append("MEI_ORG").append(",").append("MSI_TYPE").append(",").append("MEI_RESOURCE").append(",").append("MEI_DESCRIPTION").append(",").append("MEI_UPDATE_TIME").append(",").append("MEI_USER").toString();
        ENTITY_READER_MULTCHECK_ERROR = rs -> {
            MCErrorDescription error = new MCErrorDescription();
            try {
                error.setKey(rs.getString("MEI_KEY"));
                error.setTask(rs.getString("MEI_TASK"));
                error.setPeriod(rs.getString("MEI_PERIOD"));
                error.setOrg(rs.getString("MEI_ORG"));
                error.setItemType(rs.getString("MSI_TYPE"));
                error.setResource(rs.getString("MEI_RESOURCE"));
                error.setDescription(rs.getString("MEI_DESCRIPTION"));
                error.setTime(rs.getTimestamp("MEI_UPDATE_TIME"));
                error.setUser(rs.getString("MEI_USER"));
            }
            catch (SQLException e) {
                throw new RuntimeException("read MultcheckResRecord error.", e);
            }
            return error;
        };
    }
}

