/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.bi.dataset.calibersum.query;

import com.jiuqi.bi.dataset.calibersum.CaliberSumContext;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumDSRow;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

@Component
public class CaliberSumUnitStateQuerier {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void load(CaliberSumContext context, final CaliberSumResultSet resultSet) {
        if (resultSet.getUnitStatetFieldIndex() < 0) {
            return;
        }
        String tableName = "SYS_UP_ST_" + context.getFormSchemeCode();
        List<String> mainDimKeys = resultSet.getAllDestUnits();
        if (mainDimKeys.size() == 0) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(" t.MDCODE as unitcode,");
        sql.append(" t.PREVEVENT");
        sql.append(" from ").append(tableName).append(" t ");
        sql.append(" where ");
        sql.append(" t.PERIOD='").append(context.getPeriod()).append("'");
        sql.append(" and t.MDCODE in (");
        for (String value : mainDimKeys) {
            sql.append("'").append(value).append("',");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
        String sqlStr = sql.toString();
        context.getLogger().debug(sqlStr);
        this.jdbcTemplate.query(sqlStr, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                String mainDimKey = rs.getString(1);
                CaliberSumDSRow row = resultSet.findRowByMainDim(mainDimKey);
                if (row != null) {
                    String unitState = rs.getString(2);
                    row.setValue(resultSet.getUnitStatetFieldIndex(), unitState);
                }
            }
        });
        for (int i = 0; i < resultSet.size(); ++i) {
            CaliberSumDSRow row = resultSet.getRow(i);
            String unitState = (String)row.getValue(resultSet.getUnitStatetFieldIndex());
            if (!"act_upload".equals(unitState) || !row.hasParents()) continue;
            for (CaliberSumDSRow parentRow : row.getParentRows()) {
                String parentState;
                if (parentRow.getMainDimKey() == null || "act_upload".equals(parentState = (String)parentRow.getValue(resultSet.getUnitStatetFieldIndex()))) continue;
                parentState = "act_somechilds_upload";
                parentRow.setValue(resultSet.getUnitStatetFieldIndex(), parentState);
            }
        }
    }
}

