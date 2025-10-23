/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
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
public class MCResItemDao {
    private static final String RESITEM;
    private static final Function<ResultSet, MultcheckResItem> ENTITY_READER_MULTCHECK_RESITEM;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchAdd(List<MultcheckResItem> items, String tableName) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", tableName, RESITEM);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (MultcheckResItem r : items) {
            Object[] param = new Object[]{r.getKey(), r.getRecordKey(), r.getSchemeKey(), r.getItemKey(), r.getState().value(), r.getSuccess(), r.getFailed(), r.getIgnore(), r.getBegin(), r.getEnd(), r.getRunConfig()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public List<MultcheckResItem> getByRecord(String recordKey, String tableName) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", RESITEM, tableName, "MRR_KEY");
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_RESITEM.apply(rs), new Object[]{recordKey});
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        String sql = String.format("DELETE FROM %s WHERE %s < ?", tableName, "MRI_END_TIME");
        this.jdbcTemplate.update(sql, new Object[]{cleanDate});
    }

    public void cleanAllRecords(String tableName) {
        String sql = String.format("delete from %s where 1=1", tableName);
        this.jdbcTemplate.update(sql);
    }

    static {
        StringBuilder builder = new StringBuilder();
        RESITEM = builder.append("MRI_KEY").append(",").append("MRR_KEY").append(",").append("MS_KEY").append(",").append("MSI_KEY").append(",").append("MRI_STATE").append(",").append("MRI_SUCCESS").append(",").append("MRI_FAILED").append(",").append("MRI_IGNORE").append(",").append("MRI_BEGIN_TIME").append(",").append("MRI_END_TIME").append(",").append("MRI_RUN_CONFIG").toString();
        ENTITY_READER_MULTCHECK_RESITEM = rs -> {
            MultcheckResItem resItem = new MultcheckResItem();
            int index = 1;
            try {
                resItem.setKey(rs.getString(index++));
                resItem.setRecordKey(rs.getString(index++));
                resItem.setSchemeKey(rs.getString(index++));
                resItem.setItemKey(rs.getString(index++));
                resItem.setState(CheckRestultState.fromValue(rs.getInt(index++)));
                resItem.setSuccess(rs.getInt(index++));
                resItem.setFailed(rs.getInt(index++));
                resItem.setIgnore(rs.getInt(index++));
                resItem.setBegin(rs.getTimestamp(index++));
                resItem.setEnd(rs.getTimestamp(index++));
                resItem.setRunConfig(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read MultcheckResItem error.", e);
            }
            return resItem;
        };
    }
}

