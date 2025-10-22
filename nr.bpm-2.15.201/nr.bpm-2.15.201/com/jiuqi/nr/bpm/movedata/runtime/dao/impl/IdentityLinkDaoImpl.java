/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrIdentityLinkEntityImpl;
import com.jiuqi.nr.bpm.movedata.runtime.dao.IdentityLinkDao;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdentityLinkDaoImpl
implements IdentityLinkDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(NrIdentityLinkEntityImpl nrIdentityLinkEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrIdentityLinkEntity.getId(), nrIdentityLinkEntity.getRev(), nrIdentityLinkEntity.getGroupId(), nrIdentityLinkEntity.getType(), nrIdentityLinkEntity.getUserId(), nrIdentityLinkEntity.getTaskId(), nrIdentityLinkEntity.getProcInstId(), nrIdentityLinkEntity.getProcDefId()});
        return result == 1;
    }

    @Override
    public boolean batchInsert(final List<NrIdentityLinkEntityImpl> nrIdentityLinkEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrIdentityLinkEntityImpl nrIdentityLinkEntity = (NrIdentityLinkEntityImpl)nrIdentityLinkEntityList.get(i);
                ps.setString(1, nrIdentityLinkEntity.getId());
                ps.setBigDecimal(2, nrIdentityLinkEntity.getRev());
                ps.setString(3, nrIdentityLinkEntity.getGroupId());
                ps.setString(4, nrIdentityLinkEntity.getType());
                ps.setString(5, nrIdentityLinkEntity.getUserId());
                ps.setString(6, nrIdentityLinkEntity.getTaskId());
                ps.setString(7, nrIdentityLinkEntity.getProcInstId());
                ps.setString(8, nrIdentityLinkEntity.getProcDefId());
            }

            public int getBatchSize() {
                return nrIdentityLinkEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrIdentityLinkEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrIdentityLinkEntityImpl> result = new ArrayList<NrIdentityLinkEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_IDENTITYLINK where PROC_INST_ID_ = ?");
        sql.append(" union all  ");
        sql.append(" select ");
        this.addSqlParam(sql);
        sql.append(" from ACT_RU_IDENTITYLINK where ");
        sql.append("TASK_ID_").append(" in (");
        sql.append(" select ").append("id_").append(" from ").append("ACT_RU_TASK").append(" where ").append("proc_inst_id_").append("=?");
        sql.append(")");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId, procInstId});
        for (Map map : resultList) {
            NrIdentityLinkEntityImpl nrIdentityLinkEntity = new NrIdentityLinkEntityImpl();
            Object obj = map.get("ID_");
            if (obj != null) {
                String id = obj.toString();
                nrIdentityLinkEntity.setId(id);
            }
            if ((obj = map.get("REV_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrIdentityLinkEntity.setRev(rev);
            }
            if ((obj = map.get("GROUP_ID_")) != null) {
                nrIdentityLinkEntity.setGroupId(obj.toString());
            }
            if ((obj = map.get("TYPE_")) != null) {
                nrIdentityLinkEntity.setType(obj.toString());
            }
            if ((obj = map.get("USER_ID_")) != null) {
                nrIdentityLinkEntity.setUserId(obj.toString());
            }
            if ((obj = map.get("TASK_ID_")) != null) {
                nrIdentityLinkEntity.setTaskId(obj.toString());
            }
            if ((obj = map.get("PROC_INST_ID_")) != null) {
                nrIdentityLinkEntity.setProcInstId(obj.toString());
            }
            if ((obj = map.get("PROC_DEF_ID_")) != null) {
                nrIdentityLinkEntity.setProcDefId(obj.toString());
            }
            result.add(nrIdentityLinkEntity);
        }
        return result;
    }

    @Override
    public List<NrIdentityLinkEntityImpl> queryByTaskId(String taskId) {
        ArrayList<NrIdentityLinkEntityImpl> result = new ArrayList<NrIdentityLinkEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_IDENTITYLINK where TASK_ID_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{taskId});
        for (Map map : resultList) {
            NrIdentityLinkEntityImpl nrIdentityLinkEntity = new NrIdentityLinkEntityImpl();
            Object obj = map.get("ID_");
            if (obj != null) {
                String id = obj.toString();
                nrIdentityLinkEntity.setId(id);
            }
            if ((obj = map.get("REV_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrIdentityLinkEntity.setRev(rev);
            }
            if ((obj = map.get("GROUP_ID_")) != null) {
                nrIdentityLinkEntity.setGroupId(obj.toString());
            }
            if ((obj = map.get("TYPE_")) != null) {
                nrIdentityLinkEntity.setType(obj.toString());
            }
            if ((obj = map.get("USER_ID_")) != null) {
                nrIdentityLinkEntity.setUserId(obj.toString());
            }
            if ((obj = map.get("TASK_ID_")) != null) {
                nrIdentityLinkEntity.setTaskId(obj.toString());
            }
            if ((obj = map.get("PROC_INST_ID_")) != null) {
                nrIdentityLinkEntity.setProcInstId(obj.toString());
            }
            if ((obj = map.get("PROC_DEF_ID_")) != null) {
                nrIdentityLinkEntity.setProcDefId(obj.toString());
            }
            result.add(nrIdentityLinkEntity);
        }
        return result;
    }

    @Override
    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_IDENTITYLINK where ");
        sql.append("PROC_INST_ID_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        StringBuffer sql2 = new StringBuffer();
        sql2.append("delete from ");
        sql2.append("ACT_RU_IDENTITYLINK where ");
        sql2.append("TASK_ID_");
        sql2.append(" in (");
        sql2.append(" select ").append("id_").append(" from ").append("ACT_RU_TASK").append(" where ").append("proc_inst_id_").append("=?");
        sql2.append(")");
        this.jdbcTemplate.update(sql2.toString(), new Object[]{procInstId});
        return true;
    }

    @Override
    public boolean deleteByTaskId(String taskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_IDENTITYLINK where ");
        sql.append("TASK_ID_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{taskId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_IDENTITYLINK");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("ID_,");
        sql.append("REV_,");
        sql.append("GROUP_ID_,");
        sql.append("TYPE_,");
        sql.append("USER_ID_,");
        sql.append("TASK_ID_,");
        sql.append("PROC_INST_ID_,");
        sql.append("PROC_DEF_ID_ ");
    }
}

