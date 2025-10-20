/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.dimension.basedatasync.dao.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao;
import com.jiuqi.gcreport.dimension.basedatasync.entity.BaseDataChangeInfoEO;
import com.jiuqi.gcreport.dimension.basedatasync.enums.BaseDataSyncHandleStateEnum;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDataChangeInfoDaoImpl
implements BaseDataChangeInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getUnSyncBaseCodeList() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT BASEDATACODE  \n");
        sql.append("  FROM DC_BASEDATACHANGEINFO  \n");
        sql.append(" WHERE HANDLESTATE IN (?,?)  \n");
        return this.jdbcTemplate.queryForList(sql.toString(), String.class, new Object[]{BaseDataSyncHandleStateEnum.UNHANDLED.getCode(), BaseDataSyncHandleStateEnum.MIDDLE.getCode()});
    }

    @Override
    public List<BaseDataChangeInfoEO> listUnSyncInfoByBaseCode(String baseDataCode, int handleState) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODE, CHANGETYPE, OPERATINGTIME  \n");
        sql.append("  FROM DC_BASEDATACHANGEINFO  \n");
        sql.append(" WHERE BASEDATACODE = ?  \n");
        sql.append("   AND HANDLESTATE = ?  \n");
        return this.jdbcTemplate.query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(BaseDataChangeInfoEO.class), new Object[]{baseDataCode, handleState});
    }

    @Override
    public void deleteSyncInfo(String baseDataCode, Integer handleState) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM DC_BASEDATACHANGEINFO  \n");
        sql.append(" WHERE BASEDATACODE = ?  \n");
        sql.append("  AND HANDLESTATE = ?  \n");
        this.jdbcTemplate.update(sql.toString(), new Object[]{baseDataCode, handleState});
    }

    @Override
    public void truncateTable() {
        this.jdbcTemplate.update("TRUNCATE TABLE DC_BASEDATACHANGEINFO");
    }

    @Override
    public void insertBaseDataChangeInfo(String code, String tableName, String type, Date now) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO DC_BASEDATACHANGEINFO  \n");
        sql.append("  (ID, CODE, BASEDATACODE, CHANGETYPE, OPERATINGTIME)  \n");
        sql.append("VALUES  \n");
        sql.append("  (?, ?, ?, ?, ?)  \n");
        this.jdbcTemplate.update(sql.toString(), new Object[]{UUIDUtils.newHalfGUIDStr(), code, tableName, type, now});
    }

    @Override
    public int updateSyncState(String baseDataCode, int sourceState, int targetState) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE DC_BASEDATACHANGEINFO  \n");
        sql.append("   SET HANDLESTATE = ?  \n");
        sql.append(" WHERE BASEDATACODE = ?  \n");
        sql.append("  AND HANDLESTATE = ?  \n");
        return this.jdbcTemplate.update(sql.toString(), new Object[]{targetState, baseDataCode, sourceState});
    }
}

