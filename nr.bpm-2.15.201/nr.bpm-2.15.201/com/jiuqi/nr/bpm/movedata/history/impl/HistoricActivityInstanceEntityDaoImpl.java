/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricActivityInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricActivityInstanceEntityDao;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HistoricActivityInstanceEntityDaoImpl
implements IHistoricActivityInstanceEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricActivityInstanceEntityImpl> nrHistoricActivityInstanceEntities) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricActivityInstanceEntityImpl nrHistoricActivityInstanceEntity = (NrHistoricActivityInstanceEntityImpl)nrHistoricActivityInstanceEntities.get(i);
                ps.setString(1, nrHistoricActivityInstanceEntity.getId());
                ps.setString(2, nrHistoricActivityInstanceEntity.getProcessDefineId());
                ps.setString(3, nrHistoricActivityInstanceEntity.getProcessInstanceId());
                ps.setString(4, nrHistoricActivityInstanceEntity.getExecutionId());
                ps.setString(5, nrHistoricActivityInstanceEntity.getActionId());
                ps.setString(6, nrHistoricActivityInstanceEntity.getTaskId());
                ps.setString(7, nrHistoricActivityInstanceEntity.getCallProcessInstanceId());
                ps.setString(8, nrHistoricActivityInstanceEntity.getActionName());
                ps.setString(9, nrHistoricActivityInstanceEntity.getActionType());
                ps.setString(10, nrHistoricActivityInstanceEntity.getAssignee());
                Date startTime = nrHistoricActivityInstanceEntity.getStartTime();
                if (startTime != null) {
                    ps.setTimestamp(11, new Timestamp(startTime.getTime()));
                } else {
                    ps.setTimestamp(11, null);
                }
                Date endTime = nrHistoricActivityInstanceEntity.getEndTime();
                if (endTime != null) {
                    ps.setTimestamp(12, new Timestamp(endTime.getTime()));
                } else {
                    ps.setTimestamp(12, null);
                }
                ps.setBigDecimal(13, nrHistoricActivityInstanceEntity.getDuration());
                ps.setString(14, nrHistoricActivityInstanceEntity.getDeleteReason());
                ps.setString(15, nrHistoricActivityInstanceEntity.getTenantId());
            }

            public int getBatchSize() {
                return nrHistoricActivityInstanceEntities.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public void deleteByProcessId(String processInstanceId) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        deleteSql.append("act_hi_actinst");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_actinst");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("proc_def_id_,");
        sql.append("proc_inst_id_,");
        sql.append("execution_id_,");
        sql.append("act_id_,");
        sql.append("task_id_,");
        sql.append("call_proc_inst_id_,");
        sql.append("act_name_,");
        sql.append("act_type_,");
        sql.append("assignee_,");
        sql.append("start_time_,");
        sql.append("end_time_,");
        sql.append("duration_,");
        sql.append("delete_reason_,");
        sql.append("tenant_id_ ");
    }

    @Override
    public List<NrHistoricActivityInstanceEntityImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricActivityInstanceEntityImpl> result = new ArrayList<NrHistoricActivityInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_actinst where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricActivityInstanceEntityImpl nrHistoricActivityInstanceEntity = new NrHistoricActivityInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricActivityInstanceEntity.setId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrHistoricActivityInstanceEntity.setProcessDefineId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricActivityInstanceEntity.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrHistoricActivityInstanceEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("act_id_")) != null) {
                nrHistoricActivityInstanceEntity.setActionId(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrHistoricActivityInstanceEntity.setTaskId(obj.toString());
            }
            if ((obj = map.get("call_proc_inst_id_")) != null) {
                nrHistoricActivityInstanceEntity.setCallProcessInstanceId(obj.toString());
            }
            if ((obj = map.get("act_type_")) != null) {
                nrHistoricActivityInstanceEntity.setActionType(obj.toString());
            }
            if ((obj = map.get("assignee_")) != null) {
                nrHistoricActivityInstanceEntity.setActionType(obj.toString());
            }
            if ((obj = map.get("start_time_")) != null) {
                nrHistoricActivityInstanceEntity.setStartTime((Date)obj);
            }
            if ((obj = map.get("end_time_")) != null) {
                nrHistoricActivityInstanceEntity.setEndTime((Date)obj);
            }
            if ((obj = map.get("duration_")) != null) {
                nrHistoricActivityInstanceEntity.setDuration((BigDecimal)obj);
            }
            if ((obj = map.get("delete_reason_")) != null) {
                nrHistoricActivityInstanceEntity.setDeleteReason(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrHistoricActivityInstanceEntity.setTenantId(obj.toString());
            }
            result.add(nrHistoricActivityInstanceEntity);
        }
        return result;
    }
}

