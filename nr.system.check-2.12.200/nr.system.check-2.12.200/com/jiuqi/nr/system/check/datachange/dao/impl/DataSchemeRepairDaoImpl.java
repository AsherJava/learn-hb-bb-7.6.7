/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.system.check.datachange.dao.impl;

import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;
import com.jiuqi.nr.system.check.datachange.dao.DataSchemeRepairRecordDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeRepairDaoImpl
implements DataSchemeRepairRecordDao {
    private static final String TABLE_NAME = "NR_SYS_CHECK_REPAIR_RECORD";
    private static final String FIELD_KEY = "FIELD_KEY";
    private static final String FIELD_TYPE = "FIELD_TYPE";
    private static final String FIELD_START_TIME = "FIELD_START_TIME";
    private static final String FIELD_END_TIME = "FIELD_END_TIME";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String insertSql = String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES (?,?,?,?)", "NR_SYS_CHECK_REPAIR_RECORD", "FIELD_KEY", "FIELD_TYPE", "FIELD_START_TIME", "FIELD_END_TIME");
    private static final String querySql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", "NR_SYS_CHECK_REPAIR_RECORD", "FIELD_KEY", "FIELD_TYPE");
    private static final String queryLast = String.format("SELECT * FROM %s WHERE %s = ? ORDER BY %s DESC", "NR_SYS_CHECK_REPAIR_RECORD", "FIELD_TYPE", "FIELD_START_TIME");
    private static final String updateSql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ? AND %s = ? ", "NR_SYS_CHECK_REPAIR_RECORD", "FIELD_START_TIME", "FIELD_END_TIME", "FIELD_KEY", "FIELD_TYPE");
    private static final String completeSql = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", "NR_SYS_CHECK_REPAIR_RECORD", "FIELD_END_TIME", "FIELD_KEY", "FIELD_TYPE");
    static final RowMapper<DataSchemeRepairRecord> ROW_MAPPER = new RowMapper<DataSchemeRepairRecord>(){

        public DataSchemeRepairRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            DataSchemeRepairRecord repairRecord = new DataSchemeRepairRecord();
            repairRecord.setDataSchemeKey(rs.getString(DataSchemeRepairDaoImpl.FIELD_KEY));
            repairRecord.setRepairType(rs.getString(DataSchemeRepairDaoImpl.FIELD_TYPE));
            repairRecord.setStartTime(rs.getTimestamp(DataSchemeRepairDaoImpl.FIELD_START_TIME));
            repairRecord.setEndTime(rs.getTimestamp(DataSchemeRepairDaoImpl.FIELD_END_TIME));
            return repairRecord;
        }
    };

    @Override
    public int insert(DataSchemeRepairRecord repairRecord) {
        Date date = new Date();
        Object[] value = new Object[]{repairRecord.getDataSchemeKey(), repairRecord.getRepairType(), date, null};
        return this.jdbcTemplate.update(insertSql, value);
    }

    @Override
    public DataSchemeRepairRecord getRecordByKey(String dataSchemeKey, String repairType) {
        Object[] arg = new Object[]{dataSchemeKey, repairType};
        List query = this.jdbcTemplate.query(querySql, ROW_MAPPER, arg);
        return !query.isEmpty() ? (DataSchemeRepairRecord)query.get(0) : null;
    }

    @Override
    public DataSchemeRepairRecord getLastRecord(String repairType) {
        Object[] arg = new Object[]{repairType};
        List query = this.jdbcTemplate.query(queryLast, ROW_MAPPER, arg);
        return !query.isEmpty() ? (DataSchemeRepairRecord)query.get(0) : null;
    }

    @Override
    public void updateRepairRecord(DataSchemeRepairRecord dataSchemeRepairRecord) {
        Object[] args = new Object[]{dataSchemeRepairRecord.getStartTime(), dataSchemeRepairRecord.getEndTime(), dataSchemeRepairRecord.getDataSchemeKey(), dataSchemeRepairRecord.getRepairType()};
        this.jdbcTemplate.update(updateSql, args);
    }

    @Override
    public void repairComplete(String dataSchemeKey, String repairType) {
        Object[] args = new Object[]{new Date(), dataSchemeKey, repairType};
        this.jdbcTemplate.update(completeSql, args);
    }
}

