/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.taskscheduling.lockmgr.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.taskscheduling.lockmgr.dao.TaskManageDao;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TaskManageDaoImpl
implements TaskManageDao {
    @Override
    public int updateBeginHandle(TaskManageDO condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_TASKMANAGE").append(" T \n");
        sql.append("   SET BEGINTIME = ? \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.TASKNAME = ? \n");
        sql.append("   AND T.UNITCODE = ? \n");
        return OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{condi.getBeginTime(), condi.getTaskName(), condi.getUnitCode()});
    }

    @Override
    public int updateEndHandle(TaskManageDO condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_TASKMANAGE").append(" T \n");
        sql.append("   SET BATCHNUM = ? \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.TASKNAME = ? \n");
        sql.append("   AND T.UNITCODE = ? \n");
        return OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{condi.getBatchNum(), condi.getTaskName(), condi.getUnitCode()});
    }

    @Override
    public List<String> getUnitCodesByTask(TaskManageDO condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT UNITCODE \n");
        sql.append("  FROM ").append("DC_TASKMANAGE").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.TASKNAME = ? \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper(), new Object[]{condi.getTaskName()});
    }

    @Override
    public void insert(List<TaskManageDO> newTasks) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("DC_TASKMANAGE").append(" \n");
        sql.append("    (ID, VER, TASKNAME, UNITCODE, BEGINTIME, BATCHNUM) \n");
        sql.append("VALUES (?, ?, ?, ?, ?, ?)");
        ArrayList argList = CollectionUtils.newArrayList();
        for (TaskManageDO task : newTasks) {
            Object[] args = new Object[]{task.getId(), task.getVer(), task.getTaskName(), task.getUnitCode(), task.getBeginTime(), task.getBatchNum()};
            argList.add(args);
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), (List)argList);
    }

    @Override
    public TaskManageDO getTaskManageByName(String taskType, String unitCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, VER, TASKNAME, UNITCODE, BEGINTIME, BATCHNUM \n");
        sql.append("  FROM ").append("DC_TASKMANAGE").append(" T \n");
        sql.append(" WHERE T.TASKNAME = ? \n");
        sql.append("   AND T.UNITCODE = ? ");
        return (TaskManageDO)((Object)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new ResultSetExtractor<TaskManageDO>(){

            public TaskManageDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    TaskManageDO task = new TaskManageDO();
                    task.setId(rs.getString(1));
                    task.setVer(rs.getLong(2));
                    task.setTaskName(rs.getString(3));
                    task.setUnitCode(rs.getString(4));
                    task.setBeginTime(rs.getTimestamp(5));
                    task.setBatchNum(rs.getInt(6));
                    return task;
                }
                return null;
            }
        }, new Object[]{taskType, unitCode}));
    }

    @Override
    public List<TaskManageDO> getTaskMangeListByName(String taskType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, VER, TASKNAME, UNITCODE, BEGINTIME, BATCHNUM \n");
        sql.append("  FROM ").append("DC_TASKMANAGE").append(" T \n");
        sql.append(" WHERE T.TASKNAME = ? \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(TaskManageDO.class), new Object[]{taskType});
    }
}

