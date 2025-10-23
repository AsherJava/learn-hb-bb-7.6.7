/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
public class MCResOrgDao {
    private static final int SIZE = 11;
    private static final String RESORG;
    private static final Function<ResultSet, MultcheckResOrg> ENTITY_READER_MULTCHECK_RESORG;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchAdd(List<MultcheckResOrg> orgs, List<String> dims, String tableName) {
        String dimSql = this.getFieldSql(dims);
        int fieldSize = CollectionUtils.isEmpty(dims) ? 11 : 11 + dims.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldSize; ++i) {
            sb.append("?");
            if (i >= fieldSize - 1) continue;
            sb.append(",");
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String markStr = sb.toString();
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, dimSql, markStr);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        if (fieldSize > 11) {
            for (MultcheckResOrg r : orgs) {
                ArrayList<String> list = new ArrayList<String>();
                for (String dim : dims) {
                    list.add(r.getDims().get(dim));
                }
                Collections.addAll(list, r.getKey(), r.getRecordKey(), r.getItemKey(), r.getItemType(), r.getOrg(), r.getResource(), r.getResult(), r.getTime() == null ? now : r.getTime());
                args.add(list.toArray(new Object[0]));
            }
        } else {
            for (MultcheckResOrg r : orgs) {
                Object[] param = new Object[]{r.getKey(), r.getRecordKey(), r.getItemKey(), r.getItemType(), r.getOrg(), r.getResource(), r.getResult(), r.getTime()};
                args.add(param);
            }
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public List<MultcheckResOrg> getByRecord(String recordKey, List<String> dims, String tableName) {
        String dimSql = this.getFieldSql(dims);
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", dimSql, tableName, "MRR_KEY");
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> {
            MultcheckResOrg resOrg = ENTITY_READER_MULTCHECK_RESORG.apply(rs);
            if (!CollectionUtils.isEmpty(dims)) {
                HashMap<String, String> dimMap = new HashMap<String, String>();
                resOrg.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, rs.getString(dim));
                }
            }
            return resOrg;
        }, new Object[]{recordKey});
    }

    private String getFieldSql(List<String> dims) {
        String dimSql = RESORG;
        if (!CollectionUtils.isEmpty(dims)) {
            dimSql = dims.stream().collect(Collectors.joining(",")) + "," + RESORG;
        }
        return dimSql;
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        String sql = String.format("DELETE FROM %s WHERE %s < ?", tableName, "MRIO_UPDATE_TIME");
        this.jdbcTemplate.update(sql, new Object[]{cleanDate});
    }

    public void cleanAllRecords(String tableName) {
        String sql = String.format("delete from %s where 1=1", tableName);
        this.jdbcTemplate.update(sql);
    }

    static {
        StringBuilder builder = new StringBuilder();
        RESORG = builder.append("MRIO_KEY").append(",").append("MRR_KEY").append(",").append("MSI_KEY").append(",").append("MSI_TYPE").append(",").append("MRIO_ORG").append(",").append("MRIO_RESOURCE").append(",").append("MRIO_RESULT").append(",").append("MRIO_UPDATE_TIME").toString();
        ENTITY_READER_MULTCHECK_RESORG = rs -> {
            MultcheckResOrg org = new MultcheckResOrg();
            int index = 1;
            try {
                org.setKey(rs.getString(index++));
                org.setRecordKey(rs.getString(index++));
                org.setItemKey(rs.getString(index++));
                org.setItemType(rs.getString(index++));
                org.setOrg(rs.getString(index++));
                org.setResource(rs.getString(index++));
                org.setResult(rs.getString(index++));
                org.setTime(rs.getTime(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read MultcheckResItem error.", e);
            }
            return org;
        };
    }
}

