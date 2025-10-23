/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class MCResSchemeDao {
    private static final String RESSCHEME;
    private static final Function<ResultSet, MultcheckResScheme> ENTITY_READER_MULTCHECK_RESSCHEME;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchAdd(List<MultcheckResScheme> resSchemes, String tableName) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?,?, ?, ?)", tableName, RESSCHEME);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (MultcheckResScheme r : resSchemes) {
            Object[] param = new Object[]{r.getKey(), r.getRecordKey(), r.getSchemeKey(), r.getBegin(), r.getEnd(), r.getOrgs()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public List<MultcheckResScheme> getByRecord(String recordKey, String tableName) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", RESSCHEME, tableName, "MRR_KEY");
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_RESSCHEME.apply(rs), new Object[]{recordKey});
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        String sql = String.format("DELETE FROM %s WHERE %s < ?", tableName, "MRS_END_TIME");
        this.jdbcTemplate.update(sql, new Object[]{cleanDate});
    }

    public void cleanAllRecords(String tableName) {
        String sql = String.format("delete from %s where 1=1", tableName);
        this.jdbcTemplate.update(sql);
    }

    static {
        StringBuilder builder = new StringBuilder();
        RESSCHEME = builder.append("MRS_KEY").append(",").append("MRR_KEY").append(",").append("MS_KEY").append(",").append("MRS_BEGIN_TIME").append(",").append("MRS_END_TIME").append(",").append("MRS_ORGS").toString();
        ENTITY_READER_MULTCHECK_RESSCHEME = rs -> {
            MultcheckResScheme resScheme = new MultcheckResScheme();
            int index = 1;
            try {
                resScheme.setKey(rs.getString(index++));
                resScheme.setRecordKey(rs.getString(index++));
                resScheme.setSchemeKey(rs.getString(index++));
                resScheme.setBegin(rs.getTimestamp(index++));
                resScheme.setEnd(rs.getTimestamp(index++));
                resScheme.setOrgs(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read MultcheckResScheme error.", e);
            }
            return resScheme;
        };
    }
}

