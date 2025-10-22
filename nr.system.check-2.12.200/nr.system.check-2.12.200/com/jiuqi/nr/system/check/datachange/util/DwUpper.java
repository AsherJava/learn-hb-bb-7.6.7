/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.util;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.util.FetchReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class DwUpper
extends FetchReader {
    public DwUpper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<DataChangeRecord> doExec(String selectSql, String updateSql, Object[] args) {
        return (List)this.query(selectSql, args, rs -> {
            ArrayList<DataChangeRecord> records = new ArrayList<DataChangeRecord>();
            ArrayList<List<String>> batchArgs = new ArrayList<List<String>>();
            while (rs.next()) {
                batchArgs.add(this.convert(rs));
                if (batchArgs.size() != 1000) continue;
                records.addAll(this.commit(updateSql, batchArgs));
                batchArgs.clear();
            }
            records.addAll(this.commit(updateSql, batchArgs));
            batchArgs.clear();
            return records;
        });
    }

    private List<DataChangeRecord> commit(String updateSql, final List<List<String>> batchArgs) {
        final ArrayList<DataChangeRecord> records = new ArrayList<DataChangeRecord>();
        this.jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                List values = (List)batchArgs.get(i);
                ps.setString(1, (String)values.get(0));
                ps.setString(2, (String)values.get(1));
                ps.setString(3, (String)values.get(2));
                DataChangeRecord record = new DataChangeRecord();
                record.setOldUnitCode((String)values.get(1));
                record.setNewUnitCode((String)values.get(0));
                record.setPeriod((String)values.get(2));
                record.setRecordType("dataTable");
                records.add(record);
            }

            public int getBatchSize() {
                return batchArgs.size();
            }
        });
        return records;
    }

    public List<String> convert(ResultSet rs) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        String oldCode = rs.getString(1);
        String period = rs.getString(2);
        String newCode = oldCode.toUpperCase();
        list.add(newCode);
        list.add(oldCode);
        list.add(period);
        return list;
    }
}

