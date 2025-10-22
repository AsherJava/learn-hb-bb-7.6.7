/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrTaskEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.TaskDao;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDaoImpl
implements TaskDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrTaskEntityImpl nrTaskEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrTaskEntity.getId(), nrTaskEntity.getRev(), nrTaskEntity.getExecutionId(), nrTaskEntity.getProcInstId(), nrTaskEntity.getProcDefId(), nrTaskEntity.getName(), nrTaskEntity.getParentTaskId(), nrTaskEntity.getDescription(), nrTaskEntity.getTaskDefKey(), nrTaskEntity.getOwner(), nrTaskEntity.getAssignee(), nrTaskEntity.getDelegation(), nrTaskEntity.getPriority(), nrTaskEntity.getCreateTime(), nrTaskEntity.getDueDate(), nrTaskEntity.getCategory(), nrTaskEntity.getSuspensionState(), nrTaskEntity.getTenantId(), nrTaskEntity.getFormKey(), nrTaskEntity.getClaimTime()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrTaskEntityImpl> nrTaskEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrTaskEntityImpl nrTaskEntity = (NrTaskEntityImpl)nrTaskEntityList.get(i);
                ps.setString(1, nrTaskEntity.getId());
                ps.setBigDecimal(2, nrTaskEntity.getRev());
                ps.setString(3, nrTaskEntity.getExecutionId());
                ps.setString(4, nrTaskEntity.getProcInstId());
                ps.setString(5, nrTaskEntity.getProcDefId());
                ps.setString(6, nrTaskEntity.getName());
                ps.setString(7, nrTaskEntity.getParentTaskId());
                ps.setString(8, nrTaskEntity.getDescription());
                ps.setString(9, nrTaskEntity.getTaskDefKey());
                ps.setString(10, nrTaskEntity.getOwner());
                ps.setString(11, nrTaskEntity.getAssignee());
                ps.setString(12, nrTaskEntity.getDelegation());
                ps.setBigDecimal(13, nrTaskEntity.getPriority());
                ps.setTimestamp(14, nrTaskEntity.getCreateTime());
                ps.setTimestamp(15, nrTaskEntity.getDueDate());
                ps.setString(16, nrTaskEntity.getCategory());
                ps.setBigDecimal(17, nrTaskEntity.getSuspensionState());
                ps.setString(18, nrTaskEntity.getTenantId());
                ps.setString(19, nrTaskEntity.getFormKey());
                ps.setTimestamp(20, nrTaskEntity.getClaimTime());
            }

            public int getBatchSize() {
                return nrTaskEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrTaskEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrTaskEntityImpl> result = new ArrayList<NrTaskEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_TASK where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrTaskEntityImpl nrTaskEntity = new NrTaskEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrTaskEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrTaskEntity.setRev(rev);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrTaskEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrTaskEntity.setProcInstId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrTaskEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrTaskEntity.setName(obj.toString());
            }
            if ((obj = map.get("parent_task_id_")) != null) {
                nrTaskEntity.setParentTaskId(obj.toString());
            }
            if ((obj = map.get("description_")) != null) {
                nrTaskEntity.setDescription(obj.toString());
            }
            if ((obj = map.get("task_def_key_")) != null) {
                nrTaskEntity.setTaskDefKey(obj.toString());
            }
            if ((obj = map.get("owner_")) != null) {
                nrTaskEntity.setOwner(obj.toString());
            }
            if ((obj = map.get("assignee_")) != null) {
                nrTaskEntity.setAssignee(obj.toString());
            }
            if ((obj = map.get("delegation_")) != null) {
                nrTaskEntity.setDelegation(obj.toString());
            }
            if ((obj = map.get("perioriry_")) != null && obj instanceof BigDecimal) {
                nrTaskEntity.setPriority((BigDecimal)obj);
            }
            if ((obj = map.get("creatw_time_")) != null && obj instanceof Timestamp) {
                nrTaskEntity.setCreateTime((Timestamp)obj);
            }
            if ((obj = map.get("due_date_")) != null && obj instanceof Timestamp) {
                nrTaskEntity.setDueDate((Timestamp)obj);
            }
            if ((obj = map.get("category_")) != null) {
                nrTaskEntity.setCategory(obj.toString());
            }
            if ((obj = map.get("suspension_state_")) != null && obj instanceof BigDecimal) {
                nrTaskEntity.setSuspensionState((BigDecimal)obj);
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrTaskEntity.setTenantId(obj.toString());
            }
            if ((obj = map.get("form_key_")) != null) {
                nrTaskEntity.setFormKey(obj.toString());
            }
            if ((obj = map.get("claim_time_")) != null && obj instanceof Timestamp) {
                nrTaskEntity.setClaimTime((Timestamp)obj);
            }
            result.add(nrTaskEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_TASK where ");
        sql.append("proc_inst_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_TASK");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("execution_id_,");
        sql.append("proc_inst_id_,");
        sql.append("proc_def_id_,");
        sql.append("name_,");
        sql.append("parent_task_id_,");
        sql.append("description_,");
        sql.append("task_def_key_,");
        sql.append("owner_,");
        sql.append("assignee_,");
        sql.append("delegation_,");
        sql.append("perioriry_,");
        sql.append("creatw_time_,");
        sql.append("due_date_,");
        sql.append("category_,");
        sql.append("suspension_state_,");
        sql.append("tenant_id_,");
        sql.append("form_key_,");
        sql.append("claim_time_ ");
    }
}

