/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.history.impl;

import com.jiuqi.nr.bpm.movedata.NrHistoricIdentityLinkEntityImpl;
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
public class HistoricIdentityLinkEntityDaoImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void change(List<NrHistoricIdentityLinkEntityImpl> nrVariableInstanceEntityList) {
        if (nrVariableInstanceEntityList == null || nrVariableInstanceEntityList.size() == 0) {
            return;
        }
        ArrayList<String> processInstIdList = new ArrayList<String>();
        HashMap<String, NrHistoricIdentityLinkEntityImpl> changeVariableInstanceEntityMap = new HashMap<String, NrHistoricIdentityLinkEntityImpl>();
        for (NrHistoricIdentityLinkEntityImpl nrHistoricIdentityLinkEntityImpl : nrVariableInstanceEntityList) {
            String processInstId = nrHistoricIdentityLinkEntityImpl.getProcessInstanceId();
            processInstIdList.add(processInstId);
            String id2 = nrHistoricIdentityLinkEntityImpl.getId();
            changeVariableInstanceEntityMap.put(id2, nrHistoricIdentityLinkEntityImpl);
        }
        HashMap<String, NrHistoricIdentityLinkEntityImpl> originVariableInstanceEntityMap = new HashMap<String, NrHistoricIdentityLinkEntityImpl>();
        for (String processInstId : processInstIdList) {
            List<NrHistoricIdentityLinkEntityImpl> originVariableList = this.queryByProcInstId(processInstId);
            if (originVariableList == null) continue;
            for (NrHistoricIdentityLinkEntityImpl originVariable : originVariableList) {
                String id3 = originVariable.getId();
                originVariableInstanceEntityMap.put(id3, originVariable);
            }
        }
        HashSet hashSet = new HashSet(changeVariableInstanceEntityMap.keySet());
        HashSet originVariableIdList = new HashSet(originVariableInstanceEntityMap.keySet());
        hashSet.removeAll(originVariableIdList);
        HashSet addList = new HashSet(hashSet);
        ArrayList<NrHistoricIdentityLinkEntityImpl> addEntityList = new ArrayList<NrHistoricIdentityLinkEntityImpl>();
        addList.forEach(id -> addEntityList.add((NrHistoricIdentityLinkEntityImpl)changeVariableInstanceEntityMap.get(id)));
        if (addEntityList.size() > 0) {
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
        ArrayList<NrHistoricIdentityLinkEntityImpl> updateEntityList = new ArrayList<NrHistoricIdentityLinkEntityImpl>();
        for (String temUpdateId : temUpdateList) {
            NrHistoricIdentityLinkEntityImpl changeEntity;
            NrHistoricIdentityLinkEntityImpl originEntity = (NrHistoricIdentityLinkEntityImpl)originVariableInstanceEntityMap.get(temUpdateId);
            boolean flag = this.isCompleteEqual(originEntity, changeEntity = (NrHistoricIdentityLinkEntityImpl)changeVariableInstanceEntityMap.get(temUpdateId));
            if (flag) continue;
            updateEntityList.add(changeEntity);
        }
        if (updateEntityList.size() > 0) {
            this.batchUpdate(updateEntityList);
        }
    }

    public boolean batchUpdate(List<NrHistoricIdentityLinkEntityImpl> changeEntityList) {
        String sql = this.generateUpdateSql();
        Map[] paramArray = this.getParamArray(changeEntityList);
        this.namedParameterJdbcTemplate.batchUpdate(sql, paramArray);
        return true;
    }

    private String generateUpdateSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append("act_hi_identitylink").append(" ");
        sql.append("set ").append("group_id_").append(" =:").append("group_id_").append(", ");
        sql.append("type_").append(" =:").append("type_").append(", ");
        sql.append("user_id_").append(" =:").append("user_id_").append(", ");
        sql.append("task_id_").append(" =:").append("task_id_").append(", ");
        sql.append("proc_inst_id_").append(" =:").append("proc_inst_id_").append(" ");
        sql.append("where ").append("id_").append(" =:").append("id_");
        return sql.toString();
    }

    private Map<String, Object>[] getParamArray(List<NrHistoricIdentityLinkEntityImpl> entityList) {
        Map[] paramArray = new Map[entityList.size()];
        for (int i = 0; i < entityList.size(); ++i) {
            NrHistoricIdentityLinkEntityImpl entity = entityList.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id_", entity.getId());
            map.put("group_id_", entity.getGroupId());
            map.put("type_", entity.getType());
            map.put("user_id_", entity.getUserId());
            map.put("task_id_", entity.getTaskId());
            map.put("proc_inst_id_", entity.getProcessInstanceId());
            paramArray[i] = map;
        }
        return paramArray;
    }

    public boolean batchInsert(final List<NrHistoricIdentityLinkEntityImpl> nrHistoricIdentityLinkEntities) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricIdentityLinkEntityImpl nrHistoricIdentityLinkEntity = (NrHistoricIdentityLinkEntityImpl)nrHistoricIdentityLinkEntities.get(i);
                ps.setString(1, nrHistoricIdentityLinkEntity.getId());
                ps.setString(2, nrHistoricIdentityLinkEntity.getGroupId());
                ps.setString(3, nrHistoricIdentityLinkEntity.getType());
                ps.setString(4, nrHistoricIdentityLinkEntity.getUserId());
                ps.setString(5, nrHistoricIdentityLinkEntity.getTaskId());
                ps.setString(6, nrHistoricIdentityLinkEntity.getProcessInstanceId());
            }

            public int getBatchSize() {
                return nrHistoricIdentityLinkEntities.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    public void deleteByProcessId(String processInstanceId) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        deleteSql.append("act_hi_identitylink");
        deleteSql.append(" where ");
        deleteSql.append("proc_inst_id_");
        deleteSql.append("=?");
        this.jdbcTemplate.update(deleteSql.toString(), new Object[]{processInstanceId});
        StringBuilder deleteSql2 = new StringBuilder();
        deleteSql2.append("delete from ");
        deleteSql2.append("act_hi_identitylink");
        deleteSql2.append(" where ").append("task_id_");
        deleteSql2.append(" in (");
        deleteSql2.append(" select ").append("id_").append(" from ").append("act_hi_taskinst").append(" where ").append("proc_inst_id_").append("=?");
        deleteSql2.append(")");
        this.jdbcTemplate.update(deleteSql2.toString(), new Object[]{processInstanceId});
    }

    public boolean deleteInIds(Set<String> ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("act_hi_identitylink where ");
        sql.append("id_ in (:ids) ");
        HashMap<String, Set<String>> param = new HashMap<String, Set<String>>();
        param.put("ids", ids);
        this.namedParameterJdbcTemplate.update(sql.toString(), param);
        return true;
    }

    public List<NrHistoricIdentityLinkEntityImpl> queryByProcInstId(String procInstId) {
        ArrayList<NrHistoricIdentityLinkEntityImpl> result = new ArrayList<NrHistoricIdentityLinkEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from act_hi_identitylink where proc_inst_id_ = ?");
        sql.append(" union all  ");
        sql.append(" select ");
        this.addSqlParam(sql);
        sql.append(" from act_hi_identitylink where ");
        sql.append("task_id_").append(" in (");
        sql.append(" select ").append("id_").append(" from ").append("act_hi_taskinst").append(" where ").append("proc_inst_id_").append("=?");
        sql.append(")");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{procInstId, procInstId});
        for (Map map : resultList) {
            NrHistoricIdentityLinkEntityImpl nrIdentityLinkEntity = new NrHistoricIdentityLinkEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                String id = obj.toString();
                nrIdentityLinkEntity.setId(id);
            }
            if ((obj = map.get("group_id_")) != null) {
                nrIdentityLinkEntity.setGroupId(obj.toString());
            }
            if ((obj = map.get("type_")) != null) {
                nrIdentityLinkEntity.setType(obj.toString());
            }
            if ((obj = map.get("user_id_")) != null) {
                nrIdentityLinkEntity.setUserId(obj.toString());
            }
            if ((obj = map.get("task_id_")) != null) {
                nrIdentityLinkEntity.setTaskId(obj.toString());
            }
            if ((obj = map.get("proc_inst_id_")) != null) {
                nrIdentityLinkEntity.setProcessInstanceId(obj.toString());
            }
            result.add(nrIdentityLinkEntity);
        }
        return result;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into act_hi_identitylink");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,");
        sql.append("?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("id_,");
        sql.append("group_id_,");
        sql.append("type_,");
        sql.append("user_id_,");
        sql.append("task_id_,");
        sql.append("proc_inst_id_ ");
    }

    private boolean isCompleteEqual(NrHistoricIdentityLinkEntityImpl originalNrVariable, NrHistoricIdentityLinkEntityImpl changeNrVariable) {
        if (!Objects.equals(originalNrVariable.getId(), changeNrVariable.getId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getGroupId(), changeNrVariable.getGroupId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getType(), changeNrVariable.getType())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getUserId(), changeNrVariable.getUserId())) {
            return false;
        }
        if (!Objects.equals(originalNrVariable.getTaskId(), changeNrVariable.getTaskId())) {
            return false;
        }
        return Objects.equals(originalNrVariable.getProcessInstanceId(), changeNrVariable.getProcessInstanceId());
    }
}

