/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.nr.impl.dao.impl;

import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.nr.impl.dao.GcTaskDataCopyDAO;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcTaskDataCopyDAOImpl
implements GcTaskDataCopyDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryMapping(String tableName, String mdCode, String dataTime) {
        String sql = "select SRCRPCODE, SRCZBCODE, STDRPCODE, STDZBCODE from " + tableName + " where MDCODE = ? and DATATIME = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{mdCode, dataTime});
    }

    @Override
    public List<Map<String, Object>> queryFloatMapping(String tableName, String mdCode, String dataTime) {
        String sql = "select SRCRPCODE, SRCZBCODE, STDRPCODE, STDZBCODE, SRC from " + tableName + " where MDCODE = ? and DATATIME = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{mdCode, dataTime});
    }

    @Override
    public List<Map<String, Object>> querySrcData(String tableName, String mdCode, String dataTime, List<String> fields) {
        String fieldSql = String.join((CharSequence)",", fields);
        String sql = "select " + fieldSql + " from " + tableName + " where MDCODE = ? and DATATIME = ? ";
        if (fields.contains("FLOATORDER")) {
            sql = sql + " order by FLOATORDER asc";
        }
        return this.jdbcTemplate.queryForList(sql, new Object[]{mdCode, dataTime});
    }

    @Override
    public void saveData(String tableName, List<String> fields, List<Object[]> values) {
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        StringJoiner valueJoiner = new StringJoiner(",", "(", ")");
        fields.forEach(item -> {
            stringJoiner.add((CharSequence)item);
            valueJoiner.add("?");
        });
        String fieldSql = String.join((CharSequence)",", fields);
        String sql = " insert into " + tableName + " " + stringJoiner + " values" + valueJoiner;
        this.jdbcTemplate.batchUpdate(sql, values);
    }

    @Override
    public void deleteData(String tableName, String mdCode, String dataTime) {
        String sql = " delete from " + tableName + " where MDCODE = ? and DATATIME = ?";
        this.jdbcTemplate.update(sql, new Object[]{mdCode, dataTime});
    }

    @Override
    public void deleteFloatData(String tableName, List<String> fieldCode, String mdCode, String dataTime) {
        String zbcode = SqlUtils.getConditionOfIdsUseOr(fieldCode, (String)"ZBCODE");
        String sql = " delete from " + tableName + "  where MDCODE = ? and DATATIME = ? and " + zbcode;
        this.jdbcTemplate.update(sql, new Object[]{mdCode, dataTime});
    }

    @Override
    public void saveFloatData(String tableName, List<Object[]> values) {
        StringJoiner valueJoiner = new StringJoiner(",", "(", ")");
        for (int i = 0; i < 9; ++i) {
            valueJoiner.add("?");
        }
        String sql = " insert into " + tableName + " (ZBMODEL, ZBCODE, ZBNAME, ZBCATEGORY, ZBVALUE, MDCODE, DATATIME, FLOATORDER, BIZKEYORDER) values" + valueJoiner;
        this.jdbcTemplate.batchUpdate(sql, values);
    }
}

