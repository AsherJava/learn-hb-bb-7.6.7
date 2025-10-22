/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricCommentImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricCommentEntityDao;
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
public class HistoricCommentEntityDaoImpl
implements IHistoricCommentEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricCommentImpl> nrHistoricCommentImpls) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricCommentImpl nrHistoricCommentImpl = (NrHistoricCommentImpl)nrHistoricCommentImpls.get(i);
                ps.setString(1, nrHistoricCommentImpl.getId());
                ps.setString(2, nrHistoricCommentImpl.getType());
                Date time = nrHistoricCommentImpl.getTime();
                if (time != null) {
                    ps.setTimestamp(3, new Timestamp(time.getTime()));
                } else {
                    ps.setTimestamp(3, null);
                }
                ps.setString(4, nrHistoricCommentImpl.getUserId());
                ps.setString(5, nrHistoricCommentImpl.getTaskId());
                ps.setString(6, nrHistoricCommentImpl.getProcessInstanceId());
                ps.setString(7, nrHistoricCommentImpl.getAction());
                ps.setString(8, nrHistoricCommentImpl.getMessage());
                ps.setBytes(9, nrHistoricCommentImpl.getFullMessage());
            }

            public int getBatchSize() {
                return nrHistoricCommentImpls.size();
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
        deleteSql.append("act_hi_comment");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    @Override
    public List<NrHistoricCommentImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricCommentImpl> result = new ArrayList<NrHistoricCommentImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_comment where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricCommentImpl nrHistoricCommentImpl = new NrHistoricCommentImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricCommentImpl.setId(obj.toString());
            }
            if ((obj = map.get("type_")) != null) {
                nrHistoricCommentImpl.setType(obj.toString());
            }
            if ((obj = map.get("time_")) != null) {
                nrHistoricCommentImpl.setTime((Date)obj);
            }
            if ((obj = map.get("user_id_")) != null) {
                nrHistoricCommentImpl.setUserId(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrHistoricCommentImpl.setTaskId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricCommentImpl.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("action_")) != null) {
                nrHistoricCommentImpl.setAction(obj.toString());
            }
            if ((obj = map.get("message_")) != null) {
                nrHistoricCommentImpl.setMessage(obj.toString());
            }
            if ((obj = map.get("full_msg_")) != null) {
                nrHistoricCommentImpl.setFullMessage((byte[])obj);
            }
            result.add(nrHistoricCommentImpl);
        }
        return result;
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("type_,");
        sql.append("time_,");
        sql.append("user_id_,");
        sql.append("task_id_,");
        sql.append("proc_inst_id_,");
        sql.append("action_,");
        sql.append("message_,");
        sql.append("full_msg_ ");
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_comment");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?");
        sql.append(")");
        return sql.toString();
    }
}

