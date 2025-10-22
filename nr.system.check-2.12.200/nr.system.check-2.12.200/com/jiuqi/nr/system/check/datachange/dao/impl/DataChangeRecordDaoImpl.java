/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.object.BatchSqlUpdate
 */
package com.jiuqi.nr.system.check.datachange.dao.impl;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.dao.IDataChangeRecordDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
public class DataChangeRecordDaoImpl
implements IDataChangeRecordDao {
    private static final String TABLE_NAME = "NR_SYS_CHECK_DATACHANGE_RESULT";
    private static final String FIELD_ID = "FIELD_ID";
    private static final String FIELD_OLD_UNIT = "FIELD_OLD_UNIT";
    private static final String FIELD_NEW_UNIT = "FIELD_NEW_UNIT";
    private static final String FIELD_PERIOD = "FIELD_PERIOD";
    private static final String FIELD_RECORD_TYPE = "FIELD_RECORD_TYPE";
    private static final String FIELD_CHANGE_TIME = "FIELD_CHANGE_TIME";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String insertSql = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)", "NR_SYS_CHECK_DATACHANGE_RESULT", "FIELD_ID", "FIELD_OLD_UNIT", "FIELD_NEW_UNIT", "FIELD_PERIOD", "FIELD_RECORD_TYPE", "FIELD_CHANGE_TIME");
    static final RowMapper<DataChangeRecord> ROW_MAPPER = new RowMapper<DataChangeRecord>(){

        public DataChangeRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            DataChangeRecord dataChangeResultDO = new DataChangeRecord();
            dataChangeResultDO.setId(rs.getString(DataChangeRecordDaoImpl.FIELD_ID));
            dataChangeResultDO.setOldUnitCode(rs.getString(DataChangeRecordDaoImpl.FIELD_OLD_UNIT));
            dataChangeResultDO.setNewUnitCode(rs.getString(DataChangeRecordDaoImpl.FIELD_NEW_UNIT));
            dataChangeResultDO.setPeriod(rs.getString(DataChangeRecordDaoImpl.FIELD_PERIOD));
            dataChangeResultDO.setRecordType(rs.getString(DataChangeRecordDaoImpl.FIELD_RECORD_TYPE));
            dataChangeResultDO.setChangeTime(rs.getTimestamp(DataChangeRecordDaoImpl.FIELD_CHANGE_TIME));
            return dataChangeResultDO;
        }
    };

    @Override
    public int insert(DataChangeRecord resultDO) {
        Date date = new Date();
        String uuid = UUID.randomUUID().toString();
        Object[] value = new Object[]{uuid, resultDO.getOldUnitCode(), resultDO.getNewUnitCode(), resultDO.getPeriod(), resultDO.getRecordType(), date};
        return this.jdbcTemplate.update(insertSql, value);
    }

    @Override
    public List<DataChangeRecord> get(String type) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, FIELD_RECORD_TYPE);
        Object[] arg = new Object[]{type};
        return this.jdbcTemplate.query(sql, ROW_MAPPER, arg);
    }

    @Override
    public void insert(List<DataChangeRecord> resultDO) {
        int[] types = new int[]{12, 12, 12, 12, 12, 93};
        BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(this.jdbcTemplate.getDataSource(), insertSql, types, 2000);
        Date date = new Date();
        for (DataChangeRecord DO : resultDO) {
            String uuid = UUID.randomUUID().toString();
            Object[] arg = new Object[]{uuid, DO.getOldUnitCode(), DO.getNewUnitCode(), DO.getPeriod(), DO.getRecordType(), date};
            batchSqlUpdate.update(arg);
        }
        batchSqlUpdate.flush();
    }
}

