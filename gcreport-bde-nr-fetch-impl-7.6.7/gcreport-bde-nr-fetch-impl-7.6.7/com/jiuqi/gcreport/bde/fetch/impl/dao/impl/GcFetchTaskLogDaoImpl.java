/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetch.impl.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetch.impl.dao.GcFetchTaskLogDao;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogEO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcFetchTaskLogDaoImpl
implements GcFetchTaskLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(GcFetchTaskLogEO fetchTaskLog) {
        if (StringUtils.isEmpty((String)fetchTaskLog.getId())) {
            fetchTaskLog.setId(UUIDUtils.newHalfGUIDStr());
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("GC_FETCH_TASKLOG").append(" (ID,createTime,finishTime,EXECUTESTATE,UNITCODE,GROUPID,ASYNCTASKID) \n");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
        this.jdbcTemplate.update(sql.toString(), new Object[]{fetchTaskLog.getId(), fetchTaskLog.getCreateTime(), fetchTaskLog.getFinishTime(), fetchTaskLog.getExecuteState(), fetchTaskLog.getUnitCode(), fetchTaskLog.getGroupId(), fetchTaskLog.getAsyncTaskId()});
    }

    @Override
    public GcFetchTaskLogEO get(String requestTaskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select createTime,finishTime,groupid \n");
        sql.append("    from ").append("GC_FETCH_TASKLOG").append(" T \n");
        sql.append("   where id = ? ");
        return (GcFetchTaskLogEO)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (!rs.next()) {
                return null;
            }
            GcFetchTaskLogEO gcFetchTaskLogEO = new GcFetchTaskLogEO();
            gcFetchTaskLogEO.setId(requestTaskId);
            gcFetchTaskLogEO.setCreateTime(rs.getDate(1));
            gcFetchTaskLogEO.setFinishTime(rs.getDate(2));
            gcFetchTaskLogEO.setGroupId(rs.getString(3));
            return gcFetchTaskLogEO;
        }, new Object[]{requestTaskId});
    }

    @Override
    public int countExecuteTask(String groupId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) FROM ").append("GC_FETCH_TASKLOG");
        sql.append(" WHERE GROUPID = ? AND EXECUTESTATE = 0 ");
        return (Integer)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (rs.next() && rs.getObject(1) != null) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[]{groupId});
    }

    @Override
    public void update(GcFetchTaskLogEO fetchTaskLog) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ").append("GC_FETCH_TASKLOG");
        sql.append(" SET finishTime = ?,  EXECUTESTATE = ?, UNITCODE = ?");
        sql.append(" WHERE ID = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{new Date(), fetchTaskLog.getExecuteState(), fetchTaskLog.getUnitCode(), fetchTaskLog.getId()});
    }

    @Override
    public void bindAsyncTaskID(String asyncTaskId, String fetchTaskLogId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ").append("GC_FETCH_TASKLOG");
        sql.append(" SET ASYNCTASKID = ? ");
        sql.append(" WHERE ID = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{asyncTaskId, fetchTaskLogId});
    }

    @Override
    public List<GcFetchTaskLogEO> getTaskByState(String groupId, Integer executeState) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, ASYNCTASKID FROM ").append("GC_FETCH_TASKLOG");
        sql.append(" WHERE GROUPID = ? AND EXECUTESTATE = ? ");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> {
            GcFetchTaskLogEO gcFetchTaskLogEO = new GcFetchTaskLogEO();
            gcFetchTaskLogEO.setId(rs.getString(1));
            gcFetchTaskLogEO.setAsyncTaskId(rs.getString(2));
            return gcFetchTaskLogEO;
        }, new Object[]{groupId, executeState});
    }
}

