/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.ExecutionDao;
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
public class ExecutionDaoImpl
implements ExecutionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrExecutionEntityImpl nrExecutionEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrExecutionEntity.getId(), nrExecutionEntity.getRev(), nrExecutionEntity.getProcInstId(), nrExecutionEntity.getBusinessKey(), nrExecutionEntity.getParentId(), nrExecutionEntity.getProcDefId(), nrExecutionEntity.getSuperExec(), nrExecutionEntity.getRootProcInstId(), nrExecutionEntity.getActId(), nrExecutionEntity.getActive(), nrExecutionEntity.getConcurrent(), nrExecutionEntity.getScope(), nrExecutionEntity.getEventScope(), nrExecutionEntity.getMiRoot(), nrExecutionEntity.getSuspensionState(), nrExecutionEntity.getCachedEntState(), nrExecutionEntity.getTenantId(), nrExecutionEntity.getName(), nrExecutionEntity.getStartTime(), nrExecutionEntity.getStartUserId(), nrExecutionEntity.getLockTime(), nrExecutionEntity.getCountEnabled(), nrExecutionEntity.getEvtSubscrCount(), nrExecutionEntity.getTaskCount(), nrExecutionEntity.getJobCount(), nrExecutionEntity.getTimerJobCount(), nrExecutionEntity.getSuspJobCount(), nrExecutionEntity.getDeadletterJobCount(), nrExecutionEntity.getVarCount(), nrExecutionEntity.getIdLinkCount()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrExecutionEntityImpl> nrExecutionEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrExecutionEntityImpl nrExecutionEntity = (NrExecutionEntityImpl)nrExecutionEntityList.get(i);
                ps.setString(1, nrExecutionEntity.getId());
                ps.setBigDecimal(2, nrExecutionEntity.getRev());
                ps.setString(3, nrExecutionEntity.getProcInstId());
                ps.setString(4, nrExecutionEntity.getBusinessKey());
                ps.setString(5, nrExecutionEntity.getParentId());
                ps.setString(6, nrExecutionEntity.getProcDefId());
                ps.setString(7, nrExecutionEntity.getSuperExec());
                ps.setString(8, nrExecutionEntity.getRootProcInstId());
                ps.setString(9, nrExecutionEntity.getActId());
                ps.setBigDecimal(10, nrExecutionEntity.getActive());
                ps.setBigDecimal(11, nrExecutionEntity.getConcurrent());
                ps.setBigDecimal(12, nrExecutionEntity.getScope());
                ps.setBigDecimal(13, nrExecutionEntity.getEventScope());
                ps.setBigDecimal(14, nrExecutionEntity.getMiRoot());
                ps.setBigDecimal(15, nrExecutionEntity.getSuspensionState());
                ps.setBigDecimal(16, nrExecutionEntity.getCachedEntState());
                ps.setString(17, nrExecutionEntity.getTenantId());
                ps.setString(18, nrExecutionEntity.getName());
                ps.setTimestamp(19, nrExecutionEntity.getStartTime());
                ps.setString(20, nrExecutionEntity.getStartUserId());
                ps.setTimestamp(21, nrExecutionEntity.getLockTime());
                ps.setBigDecimal(22, nrExecutionEntity.getCountEnabled());
                ps.setBigDecimal(23, nrExecutionEntity.getEvtSubscrCount());
                ps.setBigDecimal(24, nrExecutionEntity.getTaskCount());
                ps.setBigDecimal(25, nrExecutionEntity.getJobCount());
                ps.setBigDecimal(26, nrExecutionEntity.getTimerJobCount());
                ps.setBigDecimal(27, nrExecutionEntity.getSuspJobCount());
                ps.setBigDecimal(28, nrExecutionEntity.getDeadletterJobCount());
                ps.setBigDecimal(29, nrExecutionEntity.getVarCount());
                ps.setBigDecimal(30, nrExecutionEntity.getIdLinkCount());
            }

            public int getBatchSize() {
                return nrExecutionEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrExecutionEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrExecutionEntityImpl> result = new ArrayList<NrExecutionEntityImpl>();
        ArrayList<NrExecutionEntityImpl> parentsResult = new ArrayList<NrExecutionEntityImpl>();
        ArrayList<NrExecutionEntityImpl> childrenResult = new ArrayList<NrExecutionEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_EXECUTION where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrExecutionEntityImpl nrExecutionEntity = new NrExecutionEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrExecutionEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrExecutionEntity.setRev(rev);
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrExecutionEntity.setProcInstId(obj.toString());
            }
            if ((obj = map.get("business_key_")) != null) {
                nrExecutionEntity.setBusinessKey(obj.toString());
            }
            if ((obj = map.get("parent_id_")) != null) {
                nrExecutionEntity.setParentId(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrExecutionEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("super_exec_")) != null) {
                nrExecutionEntity.setSuperExec(obj.toString());
            }
            if ((obj = map.get("root_proc_inst_id_")) != null) {
                nrExecutionEntity.setRootProcInstId(obj.toString());
            }
            if ((obj = map.get("act_id_")) != null) {
                nrExecutionEntity.setActId(obj.toString());
            }
            if ((obj = map.get("is_active_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setActive((BigDecimal)obj);
            }
            if ((obj = map.get("is_concurrent_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setConcurrent((BigDecimal)obj);
            }
            if ((obj = map.get("is_scope_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setScope((BigDecimal)obj);
            }
            if ((obj = map.get("is_event_scope_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setEventScope((BigDecimal)obj);
            }
            if ((obj = map.get("is_mi_root_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setMiRoot((BigDecimal)obj);
            }
            if ((obj = map.get("suspension_state_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setSuspensionState((BigDecimal)obj);
            }
            if ((obj = map.get("cached_ent_state_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setCachedEntState((BigDecimal)obj);
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrExecutionEntity.setTenantId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrExecutionEntity.setName(obj.toString());
            }
            if ((obj = map.get("start_time_")) != null && obj instanceof Timestamp) {
                nrExecutionEntity.setStartTime((Timestamp)obj);
            }
            if ((obj = map.get("start_user_id_")) != null) {
                nrExecutionEntity.setStartUserId(obj.toString());
            }
            if ((obj = map.get("lock_time_")) != null && obj instanceof Timestamp) {
                nrExecutionEntity.setLockTime((Timestamp)obj);
            }
            if ((obj = map.get("IS_COUNT_ENABLED_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setCountEnabled((BigDecimal)obj);
            }
            if ((obj = map.get("EVT_SUBSCR_COUNT_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setEvtSubscrCount((BigDecimal)obj);
            }
            if ((obj = map.get("task_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setTaskCount((BigDecimal)obj);
            }
            if ((obj = map.get("job_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setJobCount((BigDecimal)obj);
            }
            if ((obj = map.get("timer_job_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setTimerJobCount((BigDecimal)obj);
            }
            if ((obj = map.get("susp_job_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setSuspJobCount((BigDecimal)obj);
            }
            if ((obj = map.get("deadletter_job_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setDeadletterJobCount((BigDecimal)obj);
            }
            if ((obj = map.get("var_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setVarCount((BigDecimal)obj);
            }
            if ((obj = map.get("id_link_count_")) != null && obj instanceof BigDecimal) {
                nrExecutionEntity.setIdLinkCount((BigDecimal)obj);
            }
            if (map.get("parent_id_") == null) {
                parentsResult.add(nrExecutionEntity);
                continue;
            }
            childrenResult.add(nrExecutionEntity);
        }
        result.addAll(parentsResult);
        result.addAll(childrenResult);
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_EXECUTION where ");
        sql.append("proc_inst_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_EXECUTION");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("proc_inst_id_,");
        sql.append("business_key_,");
        sql.append("parent_id_,");
        sql.append("proc_def_id_,");
        sql.append("super_exec_,");
        sql.append("root_proc_inst_id_,");
        sql.append("act_id_,");
        sql.append("is_active_,");
        sql.append("is_concurrent_,");
        sql.append("is_scope_,");
        sql.append("is_event_scope_,");
        sql.append("is_mi_root_,");
        sql.append("suspension_state_,");
        sql.append("cached_ent_state_,");
        sql.append("tenant_id_,");
        sql.append("name_,");
        sql.append("start_time_,");
        sql.append("start_user_id_,");
        sql.append("lock_time_,");
        sql.append("IS_COUNT_ENABLED_,");
        sql.append("EVT_SUBSCR_COUNT_,");
        sql.append("task_count_,");
        sql.append("job_count_,");
        sql.append("timer_job_count_,");
        sql.append("susp_job_count_,");
        sql.append("deadletter_job_count_,");
        sql.append("var_count_,");
        sql.append("id_link_count_ ");
    }
}

