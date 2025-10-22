/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.JobDao;
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
public class JobDaoImpl
implements JobDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrJobEntityImpl nrJobEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrJobEntity.getId(), nrJobEntity.getRev(), nrJobEntity.getType(), nrJobEntity.getLockExpTime(), nrJobEntity.getLockOwner(), nrJobEntity.getExclusive(), nrJobEntity.getExecutionId(), nrJobEntity.getProcessInstanceId(), nrJobEntity.getProcDefId(), nrJobEntity.getRetries(), nrJobEntity.getExceptionStackId(), nrJobEntity.getExceptionMsg(), nrJobEntity.getDueDate(), nrJobEntity.getRepeat(), nrJobEntity.getHandlerType(), nrJobEntity.getHandlerCfg(), nrJobEntity.getTenantId()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrJobEntityImpl> nrJobEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrJobEntityImpl nrJobEntity = (NrJobEntityImpl)nrJobEntityList.get(i);
                ps.setString(1, nrJobEntity.getId());
                ps.setBigDecimal(2, nrJobEntity.getRev());
                ps.setString(3, nrJobEntity.getType());
                ps.setTimestamp(4, nrJobEntity.getLockExpTime());
                ps.setString(5, nrJobEntity.getLockOwner());
                ps.setBigDecimal(6, nrJobEntity.getExclusive());
                ps.setString(7, nrJobEntity.getExecutionId());
                ps.setString(8, nrJobEntity.getProcessInstanceId());
                ps.setString(9, nrJobEntity.getProcDefId());
                ps.setBigDecimal(10, nrJobEntity.getRetries());
                ps.setString(11, nrJobEntity.getExceptionStackId());
                ps.setString(12, nrJobEntity.getExceptionMsg());
                ps.setTimestamp(13, nrJobEntity.getDueDate());
                ps.setString(14, nrJobEntity.getRepeat());
                ps.setString(15, nrJobEntity.getHandlerType());
                ps.setString(16, nrJobEntity.getHandlerCfg());
                ps.setString(17, nrJobEntity.getTenantId());
            }

            public int getBatchSize() {
                return nrJobEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrJobEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrJobEntityImpl> result = new ArrayList<NrJobEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_JOB where process_instance_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrJobEntityImpl nrJobEntity = new NrJobEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrJobEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrJobEntity.setRev(rev);
            }
            if ((obj = map.get("type_")) != null) {
                nrJobEntity.setType(obj.toString());
            }
            if ((obj = map.get("lock_exp_time_")) != null && obj instanceof Timestamp) {
                nrJobEntity.setLockExpTime((Timestamp)obj);
            }
            if ((obj = map.get("lock_owner_")) != null) {
                nrJobEntity.setLockOwner(obj.toString());
            }
            if ((obj = map.get("exclusive_")) != null && obj instanceof BigDecimal) {
                nrJobEntity.setExclusive((BigDecimal)obj);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrJobEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("process_instance_id_")) != null) {
                nrJobEntity.setProcessInstanceId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrJobEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("prtries_")) != null && obj instanceof BigDecimal) {
                nrJobEntity.setRetries((BigDecimal)obj);
            }
            if ((obj = map.get("exception_stack_id_")) != null) {
                nrJobEntity.setExceptionStackId(obj.toString());
            }
            if ((obj = map.get("exception_msg_")) != null) {
                nrJobEntity.setExceptionMsg(obj.toString());
            }
            if ((obj = map.get("duedate_")) != null && obj instanceof Timestamp) {
                nrJobEntity.setDueDate((Timestamp)obj);
            }
            if ((obj = map.get("repeat_")) != null) {
                nrJobEntity.setRepeat(obj.toString());
            }
            if ((obj = map.get("handler_type_")) != null) {
                nrJobEntity.setHandlerType(obj.toString());
            }
            if ((obj = map.get("handler_cfg_")) != null) {
                nrJobEntity.setHandlerCfg(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrJobEntity.setTenantId(obj.toString());
            }
            result.add(nrJobEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_JOB where ");
        sql.append("process_instance_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_JOB");
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
        sql.append("prtries_,");
        sql.append("exception_stack_id_,");
        sql.append("exception_msg_,");
        sql.append("duedate_,");
        sql.append("repeat_,");
        sql.append("handler_type_,");
        sql.append("handler_cfg_,");
        sql.append("tenant_id_ ");
    }
}

