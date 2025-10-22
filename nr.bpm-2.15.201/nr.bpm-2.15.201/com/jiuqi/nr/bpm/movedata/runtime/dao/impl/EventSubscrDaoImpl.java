/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrEventSubscrEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.EventSubscrDao;
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
public class EventSubscrDaoImpl
implements EventSubscrDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrEventSubscrEntityImpl nrEventSubscrEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrEventSubscrEntity.getId(), nrEventSubscrEntity.getRev(), nrEventSubscrEntity.getEventType(), nrEventSubscrEntity.getEventName(), nrEventSubscrEntity.getExecutionId(), nrEventSubscrEntity.getProcInstId(), nrEventSubscrEntity.getActivityId(), nrEventSubscrEntity.getConfiguration(), nrEventSubscrEntity.getCreated(), nrEventSubscrEntity.getProcDefId(), nrEventSubscrEntity.getTenantId()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrEventSubscrEntityImpl> nrEventSubscrEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrEventSubscrEntityImpl nrEventSubscrEntity = (NrEventSubscrEntityImpl)nrEventSubscrEntityList.get(i);
                ps.setString(1, nrEventSubscrEntity.getId());
                ps.setBigDecimal(2, nrEventSubscrEntity.getRev());
                ps.setString(3, nrEventSubscrEntity.getEventType());
                ps.setString(4, nrEventSubscrEntity.getEventName());
                ps.setString(5, nrEventSubscrEntity.getExecutionId());
                ps.setString(6, nrEventSubscrEntity.getProcInstId());
                ps.setString(7, nrEventSubscrEntity.getActivityId());
                ps.setString(8, nrEventSubscrEntity.getConfiguration());
                ps.setTimestamp(9, nrEventSubscrEntity.getCreated());
                ps.setString(10, nrEventSubscrEntity.getProcDefId());
                ps.setString(11, nrEventSubscrEntity.getTenantId());
            }

            public int getBatchSize() {
                return nrEventSubscrEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrEventSubscrEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrEventSubscrEntityImpl> result = new ArrayList<NrEventSubscrEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_EVENT_SUBSCR where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrEventSubscrEntityImpl nrEventSubscrEntity = new NrEventSubscrEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrEventSubscrEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrEventSubscrEntity.setRev(rev);
            }
            if ((obj = map.get("event_type_")) != null) {
                nrEventSubscrEntity.setEventType(obj.toString());
            }
            if ((obj = map.get("event_name_")) != null) {
                nrEventSubscrEntity.setEventName(obj.toString());
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrEventSubscrEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrEventSubscrEntity.setProcInstId(obj.toString());
            }
            if ((obj = map.get("activity_id_")) != null) {
                nrEventSubscrEntity.setActivityId(obj.toString());
            }
            if ((obj = map.get("configuration_")) != null) {
                nrEventSubscrEntity.setConfiguration(obj.toString());
            }
            if ((obj = map.get("created_")) != null && obj instanceof Timestamp) {
                nrEventSubscrEntity.setCreated((Timestamp)obj);
            }
            if ((obj = map.get("proc_def_id_")) != null) {
                nrEventSubscrEntity.setProcDefId(obj.toString());
            }
            if ((obj = map.get("tenant_id_")) != null) {
                nrEventSubscrEntity.setTenantId(obj.toString());
            }
            result.add(nrEventSubscrEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_EVENT_SUBSCR where ");
        sql.append("proc_inst_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_EVENT_SUBSCR");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("event_type_,");
        sql.append("event_name_,");
        sql.append("execution_id_,");
        sql.append("proc_inst_id_,");
        sql.append("activity_id_,");
        sql.append("configuration_,");
        sql.append("created_,");
        sql.append("proc_def_id_,");
        sql.append("tenant_id_ ");
    }
}

