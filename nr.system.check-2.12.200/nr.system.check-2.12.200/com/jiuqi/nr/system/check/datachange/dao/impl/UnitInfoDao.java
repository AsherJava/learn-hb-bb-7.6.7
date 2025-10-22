/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.object.BatchSqlUpdate
 */
package com.jiuqi.nr.system.check.datachange.dao.impl;

import com.jiuqi.nr.system.check.datachange.bean.UnitInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
public class UnitInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "NR_SYS_CHECK_UNIT_MISS_INFO";
    public static final String FIELD_ORG = "FIELD_ORG";
    public static final String FIELD_PERIOD = "FIELD_PERIOD";
    public static final String FIELD_UNIT = "FIELD_UNIT";
    private static final String insertSql = String.format("INSERT INTO %s(%s,%s,%s) VALUES (?,?,?)", "NR_SYS_CHECK_UNIT_MISS_INFO", "FIELD_ORG", "FIELD_PERIOD", "FIELD_UNIT");
    private static final String querySql = String.format("SELECT * FROM %s WHERE %s = ?", "NR_SYS_CHECK_UNIT_MISS_INFO", "FIELD_ORG");
    private static final String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", "NR_SYS_CHECK_UNIT_MISS_INFO", "FIELD_ORG");
    private static final String truncate = String.format("TRUNCATE TABLE %s", "NR_SYS_CHECK_UNIT_MISS_INFO");
    static final RowMapper<UnitInfo> ROW_MAPPER = new RowMapper<UnitInfo>(){

        public UnitInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            UnitInfo unitInfo = new UnitInfo();
            unitInfo.setEntityId(rs.getString(UnitInfoDao.FIELD_ORG));
            unitInfo.setUnit(rs.getString(UnitInfoDao.FIELD_UNIT));
            unitInfo.setPeriod(rs.getString(UnitInfoDao.FIELD_PERIOD));
            return unitInfo;
        }
    };

    public void insert(UnitInfo unitMissInfo) {
        Object[] value = new Object[]{unitMissInfo.getEntityId(), unitMissInfo.getPeriod(), unitMissInfo.getUnit()};
        this.jdbcTemplate.update(insertSql, value);
    }

    public void insert(List<UnitInfo> unitMissInfos) {
        int[] types = new int[]{12, 12, 12};
        BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(this.jdbcTemplate.getDataSource(), insertSql, types, 2000);
        for (UnitInfo unitMissInfo : unitMissInfos) {
            Object[] value = new Object[]{unitMissInfo.getEntityId(), unitMissInfo.getPeriod(), unitMissInfo.getUnit()};
            batchSqlUpdate.update(value);
        }
        batchSqlUpdate.flush();
    }

    public List<UnitInfo> findByOrg(String org) {
        Object[] value = new Object[]{org};
        return this.jdbcTemplate.query(querySql, ROW_MAPPER, value);
    }

    public void deleteOldInfoByDataSchemeKey(String dataSchemeKey) {
        Object[] value = new Object[]{dataSchemeKey};
        this.jdbcTemplate.update(deleteSql, value);
    }

    public void truncate() {
        this.jdbcTemplate.update(truncate);
    }
}

