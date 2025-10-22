/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.Convert
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricVariableInstanceEntityDao;
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
public class HistoricVariableInstanceEntityDaoImpl
implements IHistoricVariableInstanceEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrHistoricVariableInstanceEntityImpl> nrHistoricVariableInstanceEntities) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricVariableInstanceEntityImpl nrHistoricVariableInstanceEntityImpl = (NrHistoricVariableInstanceEntityImpl)nrHistoricVariableInstanceEntities.get(i);
                ps.setString(1, nrHistoricVariableInstanceEntityImpl.getId());
                ps.setString(2, nrHistoricVariableInstanceEntityImpl.getProcessInstanceId());
                ps.setString(3, nrHistoricVariableInstanceEntityImpl.getExecutionId());
                ps.setString(4, nrHistoricVariableInstanceEntityImpl.getTaskId());
                ps.setString(5, nrHistoricVariableInstanceEntityImpl.getName());
                ps.setString(6, nrHistoricVariableInstanceEntityImpl.getVarType());
                ps.setBigDecimal(7, nrHistoricVariableInstanceEntityImpl.getRev());
                ps.setString(8, nrHistoricVariableInstanceEntityImpl.getByteArrayId());
                Double doubleValue = nrHistoricVariableInstanceEntityImpl.getDoubleValue();
                if (doubleValue != null) {
                    ps.setDouble(9, doubleValue);
                } else {
                    ps.setNull(9, 8);
                }
                Long longValue = nrHistoricVariableInstanceEntityImpl.getLongValue();
                if (longValue != null) {
                    ps.setLong(10, longValue);
                } else {
                    ps.setNull(10, 4);
                }
                ps.setString(11, nrHistoricVariableInstanceEntityImpl.getText());
                ps.setString(12, nrHistoricVariableInstanceEntityImpl.getText2());
                Date createTime = nrHistoricVariableInstanceEntityImpl.getCreateTime();
                if (createTime != null) {
                    ps.setTimestamp(13, new Timestamp(createTime.getTime()));
                } else {
                    ps.setTimestamp(13, null);
                }
                Date lastUpdatedTime = nrHistoricVariableInstanceEntityImpl.getLastUpdatedTime();
                if (lastUpdatedTime != null) {
                    ps.setTimestamp(14, new Timestamp(lastUpdatedTime.getTime()));
                } else {
                    ps.setTimestamp(14, null);
                }
            }

            public int getBatchSize() {
                return nrHistoricVariableInstanceEntities.size();
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
        deleteSql.append("act_hi_varinst");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
    }

    @Override
    public List<NrHistoricVariableInstanceEntityImpl> queryByProcessId(String processInstanceId) {
        ArrayList<NrHistoricVariableInstanceEntityImpl> result = new ArrayList<NrHistoricVariableInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_varinst where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{processInstanceId});
        for (Map map : resultList) {
            NrHistoricVariableInstanceEntityImpl nrHistoricVariableInstanceEntity = new NrHistoricVariableInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricVariableInstanceEntity.setId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrHistoricVariableInstanceEntity.setProcessInstanceId(processInstanceId);
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrHistoricVariableInstanceEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrHistoricVariableInstanceEntity.setTaskId(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrHistoricVariableInstanceEntity.setName(obj.toString());
            }
            if ((obj = map.get("var_type_")) != null) {
                nrHistoricVariableInstanceEntity.setVarType(obj.toString());
            }
            if ((obj = map.get("rev_")) != null) {
                nrHistoricVariableInstanceEntity.setRev((BigDecimal)obj);
            }
            if ((obj = map.get("bytearray_id_")) != null) {
                nrHistoricVariableInstanceEntity.setByteArrayId(obj.toString());
            }
            if ((obj = map.get("double_")) != null) {
                nrHistoricVariableInstanceEntity.setDoubleValue(Convert.toDouble(obj));
            }
            if ((obj = map.get("long_")) != null) {
                nrHistoricVariableInstanceEntity.setLongValue(Convert.toLong(obj));
            }
            if ((obj = map.get("text_")) != null) {
                nrHistoricVariableInstanceEntity.setText(obj.toString());
            }
            if ((obj = map.get("text2_")) != null) {
                nrHistoricVariableInstanceEntity.setText2(obj.toString());
            }
            if ((obj = map.get("create_time_")) != null) {
                nrHistoricVariableInstanceEntity.setCreateTime((Date)obj);
            }
            if ((obj = map.get("last_updated_time_")) != null) {
                nrHistoricVariableInstanceEntity.setLastUpdatedTime((Date)obj);
            }
            result.add(nrHistoricVariableInstanceEntity);
        }
        return result;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_varinst");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?,?,");
        sql.append("?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("proc_inst_id_,");
        sql.append("execution_id_,");
        sql.append("task_id_,");
        sql.append("name_,");
        sql.append("var_type_,");
        sql.append("rev_,");
        sql.append("bytearray_id_,");
        sql.append("double_,");
        sql.append("long_,");
        sql.append("text_,");
        sql.append("text2_,");
        sql.append("create_time_,");
        sql.append("last_updated_time_ ");
    }
}

