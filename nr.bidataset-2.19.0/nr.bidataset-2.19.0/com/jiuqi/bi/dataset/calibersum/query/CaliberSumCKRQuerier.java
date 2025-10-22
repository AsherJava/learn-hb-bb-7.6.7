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
public class CaliberSumCKRQuerier {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void load(CaliberSumContext context, final CaliberSumResultSet resultSet) {
        if (resultSet.getCheckHintFieldIndex() < 0) {
            return;
        }
        String tableName = "SYS_CKR_" + context.getFormSchemeCode();
        List<String> mainDimKeys = resultSet.getAllDestUnits();
        if (mainDimKeys.size() == 0) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(" t.MDCODE as unitcode,");
        sql.append(" sum(case when t.CKR_FORMULACHECKTYPE=1 then 1 else 0 end) as check_hint,");
        sql.append(" sum(case when t.CKR_FORMULACHECKTYPE=2 then 1 else 0 end) as check_warn,");
        sql.append(" sum(case when t.CKR_FORMULACHECKTYPE=4 then 1 else 0 end) as check_error");
        sql.append(" from ").append(tableName).append(" t ");
        sql.append(" where ");
        sql.append(" t.PERIOD='").append(context.getPeriod()).append("'");
        sql.append(" and t.CKR_FORMULASCHEMEKEY='").append(context.getFormulaSchemeKey()).append("'");
        sql.append(" and t.MDCODE in (");
        for (String value : mainDimKeys) {
            sql.append("'").append(value).append("',");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
        sql.append("group by t.MDCODE");
        String sqlStr = sql.toString();
        context.getLogger().debug(sqlStr);
        this.jdbcTemplate.query(sql.toString(), new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                String mainDimKey = rs.getString(1);
                CaliberSumDSRow row = resultSet.findRowByMainDim(mainDimKey);
                if (row != null) {
                    row.setValue(resultSet.getCheckHintFieldIndex(), rs.getInt(2));
                    row.setValue(resultSet.getCheckWarnFieldIndex(), rs.getInt(3));
                    row.setValue(resultSet.getCheckErrorFieldIndex(), rs.getInt(4));
                }
            }
        });
    }
}

