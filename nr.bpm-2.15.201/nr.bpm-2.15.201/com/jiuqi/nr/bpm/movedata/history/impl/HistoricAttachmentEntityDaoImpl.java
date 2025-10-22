/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricAttachmentImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricAttachmentEntityDao;
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
public class HistoricAttachmentEntityDaoImpl
implements IHistoricAttachmentEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricAttachmentImpl> nrHistoricAttachments) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricAttachmentImpl nrHistoricAttachmentImpl = (NrHistoricAttachmentImpl)nrHistoricAttachments.get(i);
                ps.setString(1, nrHistoricAttachmentImpl.getId());
                ps.setBigDecimal(2, nrHistoricAttachmentImpl.getRev());
                ps.setString(3, nrHistoricAttachmentImpl.getUserId());
                ps.setString(4, nrHistoricAttachmentImpl.getName());
                ps.setString(5, nrHistoricAttachmentImpl.getDescription());
                ps.setString(6, nrHistoricAttachmentImpl.getType());
                ps.setString(7, nrHistoricAttachmentImpl.getTaskId());
                ps.setString(8, nrHistoricAttachmentImpl.getProcessInstanceId());
                ps.setString(9, nrHistoricAttachmentImpl.getUrl());
                ps.setString(10, nrHistoricAttachmentImpl.getContentId());
                Date time = nrHistoricAttachmentImpl.getTime();
                if (time != null) {
                    ps.setTimestamp(11, new Timestamp(time.getTime()));
                } else {
                    ps.setTimestamp(11, null);
                }
            }

            public int getBatchSize() {
                return nrHistoricAttachments.size();
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
        deleteSql.append("act_hi_attachment_");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    @Override
    public List<NrHistoricAttachmentImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricAttachmentImpl> result = new ArrayList<NrHistoricAttachmentImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_attachment_ where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricAttachmentImpl nrHistoricAttachmentImpl = new NrHistoricAttachmentImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricAttachmentImpl.setId(obj.toString());
            }
            if ((obj = map.get("rev_")) != null) {
                nrHistoricAttachmentImpl.setRev((BigDecimal)obj);
            }
            if ((obj = map.get("user_id_")) != null) {
                nrHistoricAttachmentImpl.setUserId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrHistoricAttachmentImpl.setName(obj.toString());
            }
            if ((obj = map.get("description_")) != null) {
                nrHistoricAttachmentImpl.setDescription(obj.toString());
            }
            if ((obj = map.get("type_")) != null) {
                nrHistoricAttachmentImpl.setType(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrHistoricAttachmentImpl.setTaskId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricAttachmentImpl.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("url_")) != null) {
                nrHistoricAttachmentImpl.setUrl(obj.toString());
            }
            if ((obj = map.get("content_id_")) != null) {
                nrHistoricAttachmentImpl.setContentId(obj.toString());
            }
            if ((obj = map.get("time_")) != null) {
                nrHistoricAttachmentImpl.setTime((Date)obj);
            }
            result.add(nrHistoricAttachmentImpl);
        }
        return result;
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("user_id_,");
        sql.append("name_,");
        sql.append("description_,");
        sql.append("type_,");
        sql.append("task_id_,");
        sql.append("proc_inst_id_,");
        sql.append("url_,");
        sql.append("content_id_,");
        sql.append("time_ ");
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_attachment_");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?,");
        sql.append("?");
        sql.append(")");
        return sql.toString();
    }
}

