/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrDeadLetterJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.DeadLetterJobDao;
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
public class DeadLetterJobDaoImpl
implements DeadLetterJobDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrDeadLetterJobEntityImpl nrDeadLetterJobEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrDeadLetterJobEntity.getId(), nrDeadLetterJobEntity.getRev(), nrDeadLetterJobEntity.getType(), nrDeadLetterJobEntity.getExclusive(), nrDeadLetterJobEntity.getExecutionId(), nrDeadLetterJobEntity.getProcessInstanceId(), nrDeadLetterJobEntity.getProcDefId(), nrDeadLetterJobEntity.getExceptionStackId(), nrDeadLetterJobEntity.getExceptionMsg(), nrDeadLetterJobEntity.getDueDate(), nrDeadLetterJobEntity.getRepeat(), nrDeadLetterJobEntity.getHandlerType(), nrDeadLetterJobEntity.getHandlerCfg(), nrDeadLetterJobEntity.getTenantId()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrDeadLetterJobEntityImpl> nrDeadLetterJobEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrDeadLetterJobEntityImpl nrDeadLetterJobEntity = (NrDeadLetterJobEntityImpl)nrDeadLetterJobEntityList.get(i);
                ps.setString(1, nrDeadLetterJobEntity.getId());
                ps.setBigDecimal(2, nrDeadLetterJobEntity.getRev());
                ps.setString(3, nrDeadLetterJobEntity.getType());
                ps.setBigDecimal(4, nrDeadLetterJobEntity.getExclusive());
                ps.setString(5, nrDeadLetterJobEntity.getExecutionId());
                ps.setString(6, nrDeadLetterJobEntity.getProcessInstanceId());
                ps.setString(7, nrDeadLetterJobEntity.getProcDefId());
                ps.setString(8, nrDeadLetterJobEntity.getExceptionStackId());
                ps.setString(9, nrDeadLetterJobEntity.getExceptionMsg());
                ps.setTimestamp(10, nrDeadLetterJobEntity.getDueDate());
                ps.setString(11, nrDeadLetterJobEntity.getRepeat());
                ps.setString(12, nrDeadLetterJobEntity.getHandlerType());
                ps.setString(13, nrDeadLetterJobEntity.getHandlerCfg());
                ps.setString(14, nrDeadLetterJobEntity.getTenantId());
            }

            public int getBatchSize() {
                return nrDeadLetterJobEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrDeadLetterJobEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrDeadLetterJobEntityImpl> result = new ArrayList<NrDeadLetterJobEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_DEADLETTER_JOB where process_instance_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrDeadLetterJobEntityImpl nrDeadLetterJobEntity = new NrDeadLetterJobEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrDeadLetterJobEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrDeadLetterJobEntity.setRev(rev);
            }
            if ((obj = map.get("type_")) != null) {
                nrDeadLetterJobEntity.setType(obj.toString());
            }
            if ((obj = map.get("exclusive_")) != null && obj instanceof BigDecimal) {
                nrDeadLetterJobEntity.setExclusive((BigDecimal)obj);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrDeadLetterJobEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("process_instance_id_")) != null) {
                nrDeadLetterJobEntity.setProcessInstanceId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrDeadLetterJobEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("exception_stack_id_")) != null) {
                nrDeadLetterJobEntity.setExceptionStackId(obj.toString());
            }
            if ((obj = map.get("exception_msg_")) != null) {
                nrDeadLetterJobEntity.setExceptionMsg(obj.toString());
            }
            if ((obj = map.get("duedate_")) != null && obj instanceof Timestamp) {
                nrDeadLetterJobEntity.setDueDate((Timestamp)obj);
            }
            if ((obj = map.get("repeat_")) != null) {
                nrDeadLetterJobEntity.setRepeat(obj.toString());
            }
            if ((obj = map.get("handler_type_")) != null) {
                nrDeadLetterJobEntity.setHandlerType(obj.toString());
            }
            if ((obj = map.get("handler_cfg_")) != null) {
                nrDeadLetterJobEntity.setHandlerCfg(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrDeadLetterJobEntity.setTenantId(obj.toString());
            }
            result.add(nrDeadLetterJobEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_DEADLETTER_JOB where ");
        sql.append("process_instance_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_DEADLETTER_JOB");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?");
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
        sql.append("exception_stack_id_,");
        sql.append("exception_msg_,");
        sql.append("duedate_,");
        sql.append("repeat_,");
        sql.append("handler_type_,");
        sql.append("handler_cfg_,");
        sql.append("tenant_id_ ");
    }
}

