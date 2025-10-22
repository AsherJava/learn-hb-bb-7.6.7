/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricProcessInstanceEntityDao;
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
public class HistoricProcessInstanceEntityDaoImpl
implements IHistoricProcessInstanceEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricProcessInstanceEntityImpl> nrHistoricProcessInstanceEntitys) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricProcessInstanceEntityImpl nrHistoricProcessInstanceEntity = (NrHistoricProcessInstanceEntityImpl)nrHistoricProcessInstanceEntitys.get(i);
                ps.setString(1, nrHistoricProcessInstanceEntity.getId());
                ps.setString(2, nrHistoricProcessInstanceEntity.getProcessInstanceId());
                ps.setString(3, nrHistoricProcessInstanceEntity.getBusinessKey());
                ps.setString(4, nrHistoricProcessInstanceEntity.getProcessDefineId());
                Date startTime = nrHistoricProcessInstanceEntity.getStartTime();
                if (startTime != null) {
                    ps.setTimestamp(5, new Timestamp(startTime.getTime()));
                } else {
                    ps.setTimestamp(5, null);
                }
                Date endTime = nrHistoricProcessInstanceEntity.getEndTime();
                if (endTime != null) {
                    ps.setTimestamp(6, new Timestamp(endTime.getTime()));
                } else {
                    ps.setTimestamp(6, null);
                }
                ps.setBigDecimal(7, nrHistoricProcessInstanceEntity.getDuration());
                ps.setString(8, nrHistoricProcessInstanceEntity.getStartUserId());
                ps.setString(9, nrHistoricProcessInstanceEntity.getStartActivityId());
                ps.setString(10, nrHistoricProcessInstanceEntity.getEndActivityId());
                ps.setString(11, nrHistoricProcessInstanceEntity.getSuperProcessInstanceId());
                ps.setString(12, nrHistoricProcessInstanceEntity.getReason());
                ps.setString(13, nrHistoricProcessInstanceEntity.getTenantId());
                ps.setString(14, nrHistoricProcessInstanceEntity.getName());
            }

            public int getBatchSize() {
                return nrHistoricProcessInstanceEntitys.size();
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
        deleteSql.append("act_hi_procinst");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_procinst");
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
        sql.append("proc_inst_id_,");
        sql.append("business_key_,");
        sql.append("proc_def_id_,");
        sql.append("start_time_,");
        sql.append("end_time_,");
        sql.append("duration_,");
        sql.append("start_user_id_,");
        sql.append("start_act_id_,");
        sql.append("end_act_id_,");
        sql.append("super_process_instance_id_,");
        sql.append("delete_reason_,");
        sql.append("tenant_id_,");
        sql.append("name_ ");
    }

    @Override
    public List<NrHistoricProcessInstanceEntityImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricProcessInstanceEntityImpl> result = new ArrayList<NrHistoricProcessInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_procinst where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricProcessInstanceEntityImpl nrHistoricProcessInstanceEntityImpl = new NrHistoricProcessInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricProcessInstanceEntityImpl.setId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("business_key_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setBusinessKey(obj.toString());
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setProcessDefineId(obj.toString());
            }
            if ((obj = map.get("start_time_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setStartTime((Date)obj);
            }
            if ((obj = map.get("end_time_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setEndTime((Date)obj);
            }
            if ((obj = map.get("duration_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setDuration((BigDecimal)obj);
            }
            if ((obj = map.get("start_user_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setStartUserId(obj.toString());
            }
            if ((obj = map.get("start_act_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setStartActivityId(obj.toString());
            }
            if ((obj = map.get("end_act_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setEndActivityId(obj.toString());
            }
            if ((obj = map.get("super_process_instance_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setSuperProcessInstanceId(obj.toString());
            }
            if ((obj = map.get("delete_reason_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setReason(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setTenantId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setName(obj.toString());
            }
            result.add(nrHistoricProcessInstanceEntityImpl);
        }
        return result;
    }
}

