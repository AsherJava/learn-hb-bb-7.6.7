/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.idtocode.impl;

import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.idtocode.IBpmIdToCodeTransferDao;
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
public class BpmIdToCodeTransferDao
implements IBpmIdToCodeTransferDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NrExecutionEntityImpl> queryExecutionByBuinessKey() {
        ArrayList<NrExecutionEntityImpl> result = new ArrayList<NrExecutionEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append("id_,");
        sql.append("business_key_ ");
        sql.append("from ACT_RU_EXECUTION where business_key_ is not null ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString());
        for (Map map : resultList) {
            NrExecutionEntityImpl nrExecutionEntityImpl = new NrExecutionEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrExecutionEntityImpl.setId(obj.toString());
            }
            if ((obj = map.get("business_key_")) != null) {
                nrExecutionEntityImpl.setBusinessKey(obj.toString());
            }
            result.add(nrExecutionEntityImpl);
        }
        return result;
    }

    @Override
    public List<NrHistoricProcessInstanceEntityImpl> queryHistoricProcessInstanceByBuinessKey() {
        ArrayList<NrHistoricProcessInstanceEntityImpl> result = new ArrayList<NrHistoricProcessInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append("id_,");
        sql.append("business_key_ ");
        sql.append("from act_hi_procinst where business_key_ is not null ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString());
        for (Map map : resultList) {
            NrHistoricProcessInstanceEntityImpl nrHistoricProcessInstanceEntityImpl = new NrHistoricProcessInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricProcessInstanceEntityImpl.setId(obj.toString());
            }
            if ((obj = map.get("business_key_")) != null) {
                nrHistoricProcessInstanceEntityImpl.setBusinessKey(obj.toString());
            }
            result.add(nrHistoricProcessInstanceEntityImpl);
        }
        return result;
    }

    @Override
    public boolean updateExecutionById(final List<NrExecutionEntityImpl> nrExecutionEntities) {
        int[] results;
        StringBuffer sql = new StringBuffer();
        sql.append("update ACT_RU_EXECUTION");
        sql.append(" set ");
        sql.append("business_key_ =? ");
        sql.append("where ");
        sql.append("id_ =? ");
        for (int result : results = this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrExecutionEntityImpl nrExecutionEntity = (NrExecutionEntityImpl)nrExecutionEntities.get(i);
                ps.setString(1, nrExecutionEntity.getBusinessKey());
                ps.setString(2, nrExecutionEntity.getId());
            }

            public int getBatchSize() {
                return nrExecutionEntities.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean updateHistoricProcessInstanceById(final List<NrHistoricProcessInstanceEntityImpl> nrHistoricProcessInstanceEntities) {
        int[] results;
        StringBuffer sql = new StringBuffer();
        sql.append("update act_hi_procinst");
        sql.append(" set ");
        sql.append("business_key_ = ? ");
        sql.append("where ");
        sql.append("id_ =?");
        for (int result : results = this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricProcessInstanceEntityImpl nrHistoricProcessInstanceEntity = (NrHistoricProcessInstanceEntityImpl)nrHistoricProcessInstanceEntities.get(i);
                ps.setString(1, nrHistoricProcessInstanceEntity.getBusinessKey());
                ps.setString(2, nrHistoricProcessInstanceEntity.getId());
            }

            public int getBatchSize() {
                return nrHistoricProcessInstanceEntities.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public List<NrVariableInstanceEntityImpl> queryNrVariableInstanceEntityImpl() {
        ArrayList<NrVariableInstanceEntityImpl> result = new ArrayList<NrVariableInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append("id_,");
        sql.append("text_ ");
        sql.append("from ACT_RU_VARIABLE where text_ is not null ");
        sql.append("and name_ = 'businessKey' ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString());
        for (Map map : resultList) {
            NrVariableInstanceEntityImpl nrVariableInstanceEntityImpl = new NrVariableInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrVariableInstanceEntityImpl.setId(obj.toString());
            }
            if ((obj = map.get("text_")) != null) {
                nrVariableInstanceEntityImpl.setText(obj.toString());
            }
            result.add(nrVariableInstanceEntityImpl);
        }
        return result;
    }

    @Override
    public List<NrHistoricVariableInstanceEntityImpl> queryNrHistoricVariableInstanceEntityImpl() {
        ArrayList<NrHistoricVariableInstanceEntityImpl> result = new ArrayList<NrHistoricVariableInstanceEntityImpl>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append("id_,");
        sql.append("text_ ");
        sql.append("from act_hi_varinst where text_ is not null ");
        sql.append("and name_ = 'businessKey' ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString());
        for (Map map : resultList) {
            NrHistoricVariableInstanceEntityImpl nrHistoricVariableInstanceEntityImpl = new NrHistoricVariableInstanceEntityImpl();
            Object obj = map.get("id_");
            if (obj != null) {
                nrHistoricVariableInstanceEntityImpl.setId(obj.toString());
            }
            if ((obj = map.get("text_")) != null) {
                nrHistoricVariableInstanceEntityImpl.setText(obj.toString());
            }
            result.add(nrHistoricVariableInstanceEntityImpl);
        }
        return result;
    }

    @Override
    public boolean updateNrVariableInstanceEntityImpl(final List<NrVariableInstanceEntityImpl> nrVariableInstanceEntityImpls) {
        int[] results;
        StringBuffer sql = new StringBuffer();
        sql.append("update ACT_RU_VARIABLE");
        sql.append(" set ");
        sql.append("text_ =? ");
        sql.append("where ");
        sql.append("id_ =? ");
        for (int result : results = this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrVariableInstanceEntityImpl nrVariableInstanceEntityImpl = (NrVariableInstanceEntityImpl)nrVariableInstanceEntityImpls.get(i);
                ps.setString(1, nrVariableInstanceEntityImpl.getText());
                ps.setString(2, nrVariableInstanceEntityImpl.getId());
            }

            public int getBatchSize() {
                return nrVariableInstanceEntityImpls.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean updateNrHistoricVariableInstanceEntityImpl(final List<NrHistoricVariableInstanceEntityImpl> nrHistoricVariableInstanceEntityImpls) {
        int[] results;
        StringBuffer sql = new StringBuffer();
        sql.append("update act_hi_varinst");
        sql.append(" set ");
        sql.append("text_ =? ");
        sql.append("where ");
        sql.append("id_ =? ");
        for (int result : results = this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrHistoricVariableInstanceEntityImpl nrHistoricVariableInstanceEntityImpl = (NrHistoricVariableInstanceEntityImpl)nrHistoricVariableInstanceEntityImpls.get(i);
                ps.setString(1, nrHistoricVariableInstanceEntityImpl.getText());
                ps.setString(2, nrHistoricVariableInstanceEntityImpl.getId());
            }

            public int getBatchSize() {
                return nrHistoricVariableInstanceEntityImpls.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }
}

