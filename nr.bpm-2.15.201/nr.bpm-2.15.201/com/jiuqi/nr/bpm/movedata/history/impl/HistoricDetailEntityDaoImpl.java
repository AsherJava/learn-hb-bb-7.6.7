/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricDetailImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricDetailEntityDao;
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
public class HistoricDetailEntityDaoImpl
implements IHistoricDetailEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricDetailImpl> nrHistoricDetailImpls) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricDetailImpl nrHistoricDetailImpl = (NrHistoricDetailImpl)nrHistoricDetailImpls.get(i);
                ps.setString(1, nrHistoricDetailImpl.getId());
                ps.setString(2, nrHistoricDetailImpl.getType());
                ps.setString(3, nrHistoricDetailImpl.getProcessInstanceId());
                ps.setString(4, nrHistoricDetailImpl.getExecutionId());
                ps.setString(5, nrHistoricDetailImpl.getTaskId());
                ps.setString(6, nrHistoricDetailImpl.getActivityInstanceId());
                ps.setString(7, nrHistoricDetailImpl.getName());
                ps.setString(8, nrHistoricDetailImpl.getVarType());
                ps.setBigDecimal(9, nrHistoricDetailImpl.getRev());
                Date time = nrHistoricDetailImpl.getTime();
                if (time != null) {
                    ps.setTimestamp(10, new Timestamp(time.getTime()));
                } else {
                    ps.setTimestamp(10, null);
                }
                ps.setString(11, nrHistoricDetailImpl.getByteArrayId());
                ps.setBigDecimal(12, nrHistoricDetailImpl.getDoubleValue());
                ps.setBigDecimal(13, nrHistoricDetailImpl.getLongValue());
                ps.setString(14, nrHistoricDetailImpl.getText());
                ps.setString(15, nrHistoricDetailImpl.getText2());
            }

            public int getBatchSize() {
                return nrHistoricDetailImpls.size();
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
        deleteSql.append("act_hi_detail");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    @Override
    public List<NrHistoricDetailImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricDetailImpl> result = new ArrayList<NrHistoricDetailImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_detail where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricDetailImpl nrHistoricDetailImpl = new NrHistoricDetailImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricDetailImpl.setId(obj.toString());
            }
            if ((obj = map.get("type_")) != null) {
                nrHistoricDetailImpl.setType(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricDetailImpl.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrHistoricDetailImpl.setExecutionId(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrHistoricDetailImpl.setTaskId(obj.toString());
            }
            if ((obj = map.get("act_inst_id_")) != null) {
                nrHistoricDetailImpl.setActivityInstanceId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrHistoricDetailImpl.setName(obj.toString());
            }
            if ((obj = map.get("var_type_")) != null) {
                nrHistoricDetailImpl.setVarType(obj.toString());
            }
            if ((obj = map.get("rev_")) != null) {
                nrHistoricDetailImpl.setRev((BigDecimal)obj);
            }
            if ((obj = map.get("time_")) != null) {
                nrHistoricDetailImpl.setTime((Date)obj);
            }
            if ((obj = map.get("bytearray_id_")) != null) {
                nrHistoricDetailImpl.setByteArrayId(obj.toString());
            }
            if ((obj = map.get("double_")) != null) {
                nrHistoricDetailImpl.setDoubleValue((BigDecimal)obj);
            }
            if ((obj = map.get("long_")) != null) {
                nrHistoricDetailImpl.setLongValue((BigDecimal)obj);
            }
            if ((obj = map.get("text_")) != null) {
                nrHistoricDetailImpl.setText(obj.toString());
            }
            if ((obj = map.get("text2_")) != null) {
                nrHistoricDetailImpl.setText2(obj.toString());
            }
            result.add(nrHistoricDetailImpl);
        }
        return result;
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("type_,");
        sql.append("proc_inst_id_,");
        sql.append("execution_id_,");
        sql.append("task_id_,");
        sql.append("act_inst_id_,");
        sql.append("name_,");
        sql.append("var_type_,");
        sql.append("rev_,");
        sql.append("time_,");
        sql.append("bytearray_id_,");
        sql.append("double_,");
        sql.append("long_,");
        sql.append("text_,");
        sql.append("text2_ ");
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_detail");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }
}

