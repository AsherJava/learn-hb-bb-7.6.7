/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricTaskInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricTaskInstanceEntityDao;
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
public class HistoricTaskInstanceEntityDaoImpl
implements IHistoricTaskInstanceEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricTaskInstanceEntityImpl> nrHistoricTaskInstanceEntities) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricTaskInstanceEntityImpl nrHistoricTaskInstanceEntityImpl = (NrHistoricTaskInstanceEntityImpl)nrHistoricTaskInstanceEntities.get(i);
                ps.setString(1, nrHistoricTaskInstanceEntityImpl.getId());
                ps.setString(2, nrHistoricTaskInstanceEntityImpl.getProcessDefineId());
                ps.setString(3, nrHistoricTaskInstanceEntityImpl.getTaskDefineId());
                ps.setString(4, nrHistoricTaskInstanceEntityImpl.getProcessInstanceId());
                ps.setString(5, nrHistoricTaskInstanceEntityImpl.getExecutionId());
                ps.setString(6, nrHistoricTaskInstanceEntityImpl.getParentTaskId());
                ps.setString(7, nrHistoricTaskInstanceEntityImpl.getName());
                ps.setString(8, nrHistoricTaskInstanceEntityImpl.getDescription());
                ps.setString(9, nrHistoricTaskInstanceEntityImpl.getOwner());
                ps.setString(10, nrHistoricTaskInstanceEntityImpl.getAssignee());
                Date startTime = nrHistoricTaskInstanceEntityImpl.getStartTime();
                if (startTime != null) {
                    ps.setTimestamp(11, new Timestamp(startTime.getTime()));
                } else {
                    ps.setTimestamp(11, null);
                }
                Date claimTime = nrHistoricTaskInstanceEntityImpl.getClaimTime();
                if (claimTime != null) {
                    ps.setTimestamp(12, new Timestamp(claimTime.getTime()));
                } else {
                    ps.setTimestamp(12, null);
                }
                Date endTime = nrHistoricTaskInstanceEntityImpl.getEndTime();
                if (endTime != null) {
                    ps.setTimestamp(13, new Timestamp(endTime.getTime()));
                } else {
                    ps.setTimestamp(13, null);
                }
                ps.setBigDecimal(14, nrHistoricTaskInstanceEntityImpl.getDuration());
                ps.setString(15, nrHistoricTaskInstanceEntityImpl.getDeleteReason());
                ps.setBigDecimal(16, nrHistoricTaskInstanceEntityImpl.getPriority());
                Date dueDate = nrHistoricTaskInstanceEntityImpl.getDueDate();
                if (dueDate != null) {
                    ps.setTimestamp(17, new Timestamp(dueDate.getTime()));
                } else {
                    ps.setTimestamp(17, null);
                }
                ps.setString(18, nrHistoricTaskInstanceEntityImpl.getFormKey());
                ps.setString(19, nrHistoricTaskInstanceEntityImpl.getCategory());
                ps.setString(20, nrHistoricTaskInstanceEntityImpl.getTenantId());
            }

            public int getBatchSize() {
                return nrHistoricTaskInstanceEntities.size();
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
        deleteSql.append("act_hi_taskinst");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_taskinst");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("proc_def_id_,");
        sql.append("task_def_key_,");
        sql.append("proc_inst_id_,");
        sql.append("execution_id_,");
        sql.append("parent_task_id_,");
        sql.append("name_,");
        sql.append("description_,");
        sql.append("oener_,");
        sql.append("assignee_,");
        sql.append("start_time_,");
        sql.append("claim_time_,");
        sql.append("end_time_,");
        sql.append("duration_,");
        sql.append("delete_reason_,");
        sql.append("priority_,");
        sql.append("due_date_,");
        sql.append("form_key_,");
        sql.append("category_,");
        sql.append("tenant_id_ ");
    }

    @Override
    public List<NrHistoricTaskInstanceEntityImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricTaskInstanceEntityImpl> result = new ArrayList<NrHistoricTaskInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_taskinst where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricTaskInstanceEntityImpl nrHistoricTaskInstanceEntityImpl = new NrHistoricTaskInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricTaskInstanceEntityImpl.setId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setProcessDefineId(obj.toString());
            }
            if ((obj = map.get("task_def_key_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setTaskDefineId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setExecutionId(obj.toString());
            }
            if ((obj = map.get("parent_task_id_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setParentTaskId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setName(obj.toString());
            }
            if ((obj = map.get("description_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setDescription(obj.toString());
            }
            if ((obj = map.get("oener_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setOwner(obj.toString());
            }
            if ((obj = map.get("assignee_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setAssignee(obj.toString());
            }
            if ((obj = map.get("start_time_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setStartTime((Date)obj);
            }
            if ((obj = map.get("claim_time_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setStartTime((Date)obj);
            }
            if ((obj = map.get("end_time_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setEndTime((Date)obj);
            }
            if ((obj = map.get("duration_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setDuration((BigDecimal)obj);
            }
            if ((obj = map.get("delete_reason_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setDeleteReason(obj.toString());
            }
            if ((obj = map.get("priority_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setPriority((BigDecimal)obj);
            }
            if ((obj = map.get("due_date_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setDueDate((Date)obj);
            }
            if ((obj = map.get("form_key_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setFormKey(obj.toString());
            }
            if ((obj = map.get("category_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setCategory(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrHistoricTaskInstanceEntityImpl.setTenantId(obj.toString());
            }
            result.add(nrHistoricTaskInstanceEntityImpl);
        }
        return result;
    }
}

