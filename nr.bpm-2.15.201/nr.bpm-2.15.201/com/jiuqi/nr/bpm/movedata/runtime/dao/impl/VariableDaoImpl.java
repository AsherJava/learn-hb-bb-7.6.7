/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao.impl;

import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VariableDaoImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void change(List<NrVariableInstanceEntityImpl> nrVariableInstanceEntityList) {
        if (nrVariableInstanceEntityList == null || nrVariableInstanceEntityList.size() == 0) {
            return;
        }
        ArrayList<String> processInstIdList = new ArrayList<String>();
        HashMap<String, NrVariableInstanceEntityImpl> changeVariableInstanceEntityMap = new HashMap<String, NrVariableInstanceEntityImpl>();
        for (NrVariableInstanceEntityImpl nrVariableInstanceEntityImpl : nrVariableInstanceEntityList) {
            String processInstId = nrVariableInstanceEntityImpl.getProcInstId();
            processInstIdList.add(processInstId);
            String id2 = nrVariableInstanceEntityImpl.getId();
            changeVariableInstanceEntityMap.put(id2, nrVariableInstanceEntityImpl);
        }
        HashMap<String, NrVariableInstanceEntityImpl> originVariableInstanceEntityMap = new HashMap<String, NrVariableInstanceEntityImpl>();
        for (String processInstId : processInstIdList) {
            List<NrVariableInstanceEntityImpl> originVariableList = this.queryByProcInstId(processInstId);
            if (originVariableList == null) continue;
            for (NrVariableInstanceEntityImpl originVariable : originVariableList) {
                String id3 = originVariable.getId();
                originVariableInstanceEntityMap.put(id3, originVariable);
            }
        }
        HashSet hashSet = new HashSet(changeVariableInstanceEntityMap.keySet());
        HashSet originVariableIdList = new HashSet(originVariableInstanceEntityMap.keySet());
        hashSet.removeAll(originVariableIdList);
        HashSet addList = new HashSet(hashSet);
        ArrayList<NrVariableInstanceEntityImpl> addEntityList = new ArrayList<NrVariableInstanceEntityImpl>();
        addList.forEach(id -> addEntityList.add((NrVariableInstanceEntityImpl)changeVariableInstanceEntityMap.get(id)));
        if (addList.size() > 0) {
            this.batchInsert(addEntityList);
        }
        HashSet hashSet2 = new HashSet(changeVariableInstanceEntityMap.keySet());
        originVariableIdList = new HashSet(originVariableInstanceEntityMap.keySet());
        originVariableIdList.removeAll(hashSet2);
        HashSet<String> deleteList = new HashSet<String>(originVariableIdList);
        if (deleteList.size() > 0) {
            this.deleteInIds(deleteList);
        }
        HashSet hashSet3 = new HashSet(changeVariableInstanceEntityMap.keySet());
        originVariableIdList = new HashSet(originVariableInstanceEntityMap.keySet());
        hashSet3.retainAll(originVariableIdList);
        HashSet temUpdateList = new HashSet(hashSet3);
        ArrayList<NrVariableInstanceEntityImpl> updateEntityList = new ArrayList<NrVariableInstanceEntityImpl>();
        for (String temUpdateId : temUpdateList) {
            NrVariableInstanceEntityImpl changeEntity;
            NrVariableInstanceEntityImpl originEntity = (NrVariableInstanceEntityImpl)originVariableInstanceEntityMap.get(temUpdateId);
            boolean flag = this.isCompleteEqual(originEntity, changeEntity = (NrVariableInstanceEntityImpl)changeVariableInstanceEntityMap.get(temUpdateId));
            if (flag) continue;
            updateEntityList.add(changeEntity);
        }
        if (updateEntityList.size() > 0) {
            this.batchUpdate(updateEntityList);
        }
    }

    public boolean batchUpdate(List<NrVariableInstanceEntityImpl> changeEntityList) {
        String sql = this.generateUpdateSql();
        Map[] paramArray = this.getParamArray(changeEntityList);
        this.namedParameterJdbcTemplate.batchUpdate(sql, paramArray);
        return true;
    }

    public boolean insert(NrVariableInstanceEntityImpl nrVariableInstanceEntity) {
        String sql = this.generateInsertSql();
        int result = this.jdbcTemplate.update(sql, new Object[]{nrVariableInstanceEntity.getId(), nrVariableInstanceEntity.getRev(), nrVariableInstanceEntity.getType(), nrVariableInstanceEntity.getName(), nrVariableInstanceEntity.getExecutionId(), nrVariableInstanceEntity.getProcInstId(), nrVariableInstanceEntity.getTaskId(), nrVariableInstanceEntity.getByteArrayId(), nrVariableInstanceEntity.getDoubleValue(), nrVariableInstanceEntity.getLongValue(), nrVariableInstanceEntity.getText(), nrVariableInstanceEntity.getText2()});
        return result == 1;
    }

    public boolean batchInsert(final List<NrVariableInstanceEntityImpl> nrVariableInstanceEntityList) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrVariableInstanceEntityImpl nrVariableInstanceEntity = (NrVariableInstanceEntityImpl)nrVariableInstanceEntityList.get(i);
                ps.setString(1, nrVariableInstanceEntity.getId());
                ps.setBigDecimal(2, nrVariableInstanceEntity.getRev());
                ps.setString(3, nrVariableInstanceEntity.getType());
                ps.setString(4, nrVariableInstanceEntity.getName());
                ps.setString(5, nrVariableInstanceEntity.getExecutionId());
                ps.setString(6, nrVariableInstanceEntity.getProcInstId());
                ps.setString(7, nrVariableInstanceEntity.getTaskId());
                ps.setString(8, nrVariableInstanceEntity.getByteArrayId());
                ps.setBigDecimal(9, nrVariableInstanceEntity.getDoubleValue());
                ps.setBigDecimal(10, nrVariableInstanceEntity.getLongValue());
                ps.setString(11, nrVariableInstanceEntity.getText());
                ps.setString(12, nrVariableInstanceEntity.getText2());
            }

            public int getBatchSize() {
                return nrVariableInstanceEntityList.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    private Map<String, Object>[] getParamArray(List<NrVariableInstanceEntityImpl> entityList) {
        Map[] paramArray = new Map[entityList.size()];
        for (int i = 0; i < entityList.size(); ++i) {
            NrVariableInstanceEntityImpl entity = entityList.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id_", entity.getId());
            map.put("rev_", entity.getRev());
            map.put("type_", entity.getType());
            map.put("name_", entity.getName());
            map.put("execution_id_", entity.getExecutionId());
            map.put("proc_inst_id_", entity.getProcInstId());
            map.put("task_id_", entity.getTaskId());
            map.put("bytearray_id_", entity.getByteArrayId());
            map.put("double_", entity.getDoubleValue());
            map.put("long_", entity.getLongValue());
            map.put("text_", entity.getText());
            map.put("text2_", entity.getText2());
            paramArray[i] = map;
        }
        return paramArray;
    }

    public List<NrVariableInstanceEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrVariableInstanceEntityImpl> result = new ArrayList<NrVariableInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_RU_VARIABLE where proc_inst_id_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId});
        for (Map map : resultList) {
            NrVariableInstanceEntityImpl nrVariableInstanceEntity = new NrVariableInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrVariableInstanceEntity.setId(id);
            }
            if ((obj = map.get("rev_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrVariableInstanceEntity.setRev(rev);
            }
            if ((obj = map.get("type_")) != null) {
                nrVariableInstanceEntity.setType(obj.toString());
            }
            if ((obj = map.get("name_")) != null) {
                nrVariableInstanceEntity.setName(obj.toString());
            }
            if ((obj = map.get("execution_id_")) != null) {
                nrVariableInstanceEntity.setExecutionId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrVariableInstanceEntity.setProcInstId(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrVariableInstanceEntity.setTaskId(obj.toString());
            }
            if ((obj = map.get("bytearray_id_")) != null) {
                nrVariableInstanceEntity.setByteArrayId(obj.toString());
            }
            if ((obj = map.get("double_")) != null && obj instanceof BigDecimal) {
                nrVariableInstanceEntity.setDoubleValue((BigDecimal)obj);
            }
            if ((obj = map.get("long_")) != null && obj instanceof BigDecimal) {
                nrVariableInstanceEntity.setLongValue((BigDecimal)obj);
            }
            if ((obj = map.get("text_")) != null) {
                nrVariableInstanceEntity.setText(obj.toString());
            }
            if ((obj = map.get("text2_")) != null) {
                nrVariableInstanceEntity.setText2(obj.toString());
            }
            result.add(nrVariableInstanceEntity);
        }
        return result;
    }

    public boolean deleteInIds(Set<String> ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_VARIABLE where ");
        sql.append("id_ in (:ids) ");
        HashMap<String, Set<String>> param = new HashMap<String, Set<String>>();
        param.put("ids", ids);
        this.namedParameterJdbcTemplate.update(sql.toString(), param);
        return true;
    }

    public boolean deleteByProcInstId(String procInstId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_RU_VARIABLE where ");
        sql.append("proc_inst_id_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{procInstId});
        return true;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_RU_VARIABLE");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?,");
        sql.append("?,?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private String generateUpdateSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append("ACT_RU_VARIABLE").append(" ");
        sql.append("set ").append("rev_").append(" =:").append("rev_").append(", ");
        sql.append("type_").append(" =:").append("type_").append(", ");
        sql.append("name_").append(" =:").append("name_").append(", ");
        sql.append("execution_id_").append(" =:").append("execution_id_").append(", ");
        sql.append("proc_inst_id_").append(" =:").append("proc_inst_id_").append(", ");
        sql.append("task_id_").append(" =:").append("task_id_").append(", ");
        sql.append("bytearray_id_").append(" =:").append("bytearray_id_").append(", ");
        sql.append("double_").append(" =:").append("double_").append(", ");
        sql.append("long_").append(" =:").append("long_").append(", ");
        sql.append("text_").append(" =:").append("text_").append(", ");
        sql.append("text2_").append(" =:").append("text2_").append(" ");
        sql.append("where ").append("id_").append(" =:").append("id_");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("rev_,");
        sql.append("type_,");
        sql.append("name_,");
        sql.append("execution_id_,");
        sql.append("proc_inst_id_,");
        sql.append("task_id_,");
        sql.append("bytearray_id_,");
        sql.append("double_,");
        sql.append("long_,");
        sql.append("text_,");
        sql.append("text2_ ");
    }

    private boolean isCompleteEqual(NrVariableInstanceEntityImpl originalNrVariable, NrVariableInstanceEntityImpl changeNrVariable) {
        if (!Objects.equals(originalNrVariable.getId(), changeNrVariable.getId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getRev(), changeNrVariable.getRev())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getType(), changeNrVariable.getType())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getName(), changeNrVariable.getName())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getExecutionId(), changeNrVariable.getExecutionId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getProcInstId(), changeNrVariable.getProcInstId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getTaskId(), changeNrVariable.getTaskId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getByteArrayId(), changeNrVariable.getByteArrayId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getDoubleValue(), changeNrVariable.getDoubleValue())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getLongValue(), changeNrVariable.getLongValue())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getText(), changeNrVariable.getText())) {
            return false;
        }
        return Objects.equals(originalNrVariable.getText2(), changeNrVariable.getText2());
    }
}

