/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrTimerJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.TimerJobDao;
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
public class TimerJobDaoImpl
implements TimerJobDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrTimerJobEntityImpl nrTimerJobEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrTimerJobEntity.getId(), nrTimerJobEntity.getRev(), nrTimerJobEntity.getType(), nrTimerJobEntity.getLockExpTime(), nrTimerJobEntity.getLockOwner(), nrTimerJobEntity.getExclusive(), nrTimerJobEntity.getExecutionId(), nrTimerJobEntity.getProcessInstanceId(), nrTimerJobEntity.getProcDefId(), nrTimerJobEntity.getRetries(), nrTimerJobEntity.getExceptionStackId(), nrTimerJobEntity.getExceptionMsg(), nrTimerJobEntity.getDueDate(), nrTimerJobEntity.getRepeat(), nrTimerJobEntity.getHandlerType(), nrTimerJobEntity.getHandlerCfg(), nrTimerJobEntity.getTenantId()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrTimerJobEntityImpl> nrTimerJobEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrTimerJobEntityImpl nrTimerJobEntity = (NrTimerJobEntityImpl)nrTimerJobEntityList.get(i);
                ps.setString(1, nrTimerJobEntity.getId());
                ps.setBigDecimal(2, nrTimerJobEntity.getRev());
                ps.setString(3, nrTimerJobEntity.getType());
                ps.setTimestamp(4, nrTimerJobEntity.getLockExpTime());
                ps.setString(5, nrTimerJobEntity.getLockOwner());
                ps.setBigDecimal(6, nrTimerJobEntity.getExclusive());
                ps.setString(7, nrTimerJobEntity.getExecutionId());
                ps.setString(8, nrTimerJobEntity.getProcessInstanceId());
                ps.setString(9, nrTimerJobEntity.getProcDefId());
                ps.setBigDecimal(10, nrTimerJobEntity.getRetries());
                ps.setString(11, nrTimerJobEntity.getExceptionStackId());
                ps.setString(12, nrTimerJobEntity.getExceptionMsg());
                ps.setTimestamp(13, nrTimerJobEntity.getDueDate());
                ps.setString(14, nrTimerJobEntity.getRepeat());
                ps.setString(15, nrTimerJobEntity.getHandlerType());
                ps.setString(16, nrTimerJobEntity.getHandlerCfg());
                ps.setString(17, nrTimerJobEntity.getTenantId());
            }

            public int getBatchSize() {
                return nrTimerJobEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrTimerJobEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrTimerJobEntityImpl> result = new ArrayList<NrTimerJobEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_TIMER_JOB where process_instance_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrTimerJobEntityImpl nrTimerJobEntity = new NrTimerJobEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrTimerJobEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrTimerJobEntity.setRev(rev);
            }
            if ((obj = map.get("type_")) != null) {
                nrTimerJobEntity.setType(obj.toString());
            }
            if ((obj = map.get("lock_exp_time_")) != null && obj instanceof Timestamp) {
                nrTimerJobEntity.setLockExpTime((Timestamp)obj);
            }
            if ((obj = map.get("lock_owner_")) != null) {
                nrTimerJobEntity.setLockOwner(obj.toString());
            }
            if ((obj = map.get("exclusive_")) != null && obj instanceof BigDecimal) {
                nrTimerJobEntity.setExclusive((BigDecimal)obj);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrTimerJobEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("process_instance_id_")) != null) {
                nrTimerJobEntity.setProcessInstanceId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrTimerJobEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("retries_")) != null && obj instanceof BigDecimal) {
                nrTimerJobEntity.setRetries((BigDecimal)obj);
            }
            if ((obj = map.get("exception_stack_id_")) != null) {
                nrTimerJobEntity.setExceptionStackId(obj.toString());
            }
            if ((obj = map.get("exception_msg_")) != null) {
                nrTimerJobEntity.setExceptionMsg(obj.toString());
            }
            if ((obj = map.get("duedate_")) != null && obj instanceof Timestamp) {
                nrTimerJobEntity.setDueDate((Timestamp)obj);
            }
            if ((obj = map.get("repeat_")) != null) {
                nrTimerJobEntity.setRepeat(obj.toString());
            }
            if ((obj = map.get("handler_type_")) != null) {
                nrTimerJobEntity.setHandlerType(obj.toString());
            }
            if ((obj = map.get("handler_cfg_")) != null) {
                nrTimerJobEntity.setHandlerCfg(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrTimerJobEntity.setTenantId(obj.toString());
            }
            result.add(nrTimerJobEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_TIMER_JOB where ");
        sql.append("process_instance_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_TIMER_JOB");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("type_,");
        sql.append("lock_exp_time_,");
        sql.append("lock_owner_,");
        sql.append("exclusive_,");
        sql.append("execution_id_,");
        sql.append("process_instance_id_,");
        sql.append("proc_def_id_,");
        sql.append("retries_,");
        sql.append("exception_stack_id_,");
        sql.append("exception_msg_,");
        sql.append("duedate_,");
        sql.append("repeat_,");
        sql.append("handler_type_,");
        sql.append("handler_cfg_,");
        sql.append("tenant_id_ ");
    }
}

