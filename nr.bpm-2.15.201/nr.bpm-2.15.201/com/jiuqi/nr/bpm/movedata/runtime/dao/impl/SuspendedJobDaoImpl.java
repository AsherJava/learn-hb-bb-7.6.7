/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrSuspendedJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.SuspendedJobDao;
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
public class SuspendedJobDaoImpl
implements SuspendedJobDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrSuspendedJobEntityImpl nrSuspendedJobEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrSuspendedJobEntity.getId(), nrSuspendedJobEntity.getRev(), nrSuspendedJobEntity.getType(), nrSuspendedJobEntity.getExclusive(), nrSuspendedJobEntity.getExecutionId(), nrSuspendedJobEntity.getProcessInstanceId(), nrSuspendedJobEntity.getProcDefId(), nrSuspendedJobEntity.getRetries(), nrSuspendedJobEntity.getExceptionStackId(), nrSuspendedJobEntity.getExceptionMsg(), nrSuspendedJobEntity.getDueDate(), nrSuspendedJobEntity.getRepeat(), nrSuspendedJobEntity.getHandlerType(), nrSuspendedJobEntity.getHandlerCfg(), nrSuspendedJobEntity.getTenantId()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrSuspendedJobEntityImpl> nrSuspendedJobEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrSuspendedJobEntityImpl nrSuspendedJobEntity = (NrSuspendedJobEntityImpl)nrSuspendedJobEntityList.get(i);
                ps.setString(1, nrSuspendedJobEntity.getId());
                ps.setBigDecimal(2, nrSuspendedJobEntity.getRev());
                ps.setString(3, nrSuspendedJobEntity.getType());
                ps.setBigDecimal(4, nrSuspendedJobEntity.getExclusive());
                ps.setString(5, nrSuspendedJobEntity.getExecutionId());
                ps.setString(6, nrSuspendedJobEntity.getProcessInstanceId());
                ps.setString(7, nrSuspendedJobEntity.getProcDefId());
                ps.setBigDecimal(8, nrSuspendedJobEntity.getRetries());
                ps.setString(9, nrSuspendedJobEntity.getExceptionStackId());
                ps.setString(10, nrSuspendedJobEntity.getExceptionMsg());
                ps.setTimestamp(11, nrSuspendedJobEntity.getDueDate());
                ps.setString(12, nrSuspendedJobEntity.getRepeat());
                ps.setString(13, nrSuspendedJobEntity.getHandlerType());
                ps.setString(14, nrSuspendedJobEntity.getHandlerCfg());
                ps.setString(15, nrSuspendedJobEntity.getTenantId());
            }

            public int getBatchSize() {
                return nrSuspendedJobEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrSuspendedJobEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrSuspendedJobEntityImpl> result = new ArrayList<NrSuspendedJobEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_SUSPENDED_JOB where process_instance_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrSuspendedJobEntityImpl nrSuspendedJobEntity = new NrSuspendedJobEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrSuspendedJobEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrSuspendedJobEntity.setRev(rev);
            }
            if ((obj = map.get("type_")) != null) {
                nrSuspendedJobEntity.setType(obj.toString());
            }
            if ((obj = map.get("exclusive_")) != null && obj instanceof BigDecimal) {
                nrSuspendedJobEntity.setExclusive((BigDecimal)obj);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrSuspendedJobEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("process_instance_id_")) != null) {
                nrSuspendedJobEntity.setProcessInstanceId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrSuspendedJobEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("retries_")) != null && obj instanceof BigDecimal) {
                nrSuspendedJobEntity.setRetries((BigDecimal)obj);
            }
            if ((obj = map.get("exception_stack_id_")) != null) {
                nrSuspendedJobEntity.setExceptionStackId(obj.toString());
            }
            if ((obj = map.get("exception_msg_")) != null) {
                nrSuspendedJobEntity.setExceptionMsg(obj.toString());
            }
            if ((obj = map.get("duedate_")) != null && obj instanceof Timestamp) {
                nrSuspendedJobEntity.setDueDate((Timestamp)obj);
            }
            if ((obj = map.get("repeat_")) != null) {
                nrSuspendedJobEntity.setRepeat(obj.toString());
            }
            if ((obj = map.get("handler_type_")) != null) {
                nrSuspendedJobEntity.setHandlerType(obj.toString());
            }
            if ((obj = map.get("handler_cfg_")) != null) {
                nrSuspendedJobEntity.setHandlerCfg(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrSuspendedJobEntity.setTenantId(obj.toString());
            }
            result.add(nrSuspendedJobEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_SUSPENDED_JOB where ");
        sql.append("process_instance_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_SUSPENDED_JOB");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("type_,");
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

