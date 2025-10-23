/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.QueryModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.BusinessKeyCollectionValueProvider;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.InstanceIdValueProvider;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstacneValuePool;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ProcessInstanceQuery {
    private final JdbcTemplate jdbcTemplate;
    private final ITempTableManager tempTableManager;
    private final QueryModel.ProcessInstanceQueryModel istQueryModel;
    private static final String IST_TABLEALIAS = "T1";
    private static final String IST_TABLEALIAS_DOT = "T1.";

    public ProcessInstanceQuery(JdbcTemplate jdbcTemplate, ITempTableManager tempTableManager, QueryModel.ProcessInstanceQueryModel tableModel) {
        this.jdbcTemplate = jdbcTemplate;
        this.tempTableManager = tempTableManager;
        this.istQueryModel = tableModel;
    }

    public QueryModel.ProcessInstanceQueryModel getQueryModel() {
        return this.istQueryModel;
    }

    JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    ITempTableManager getTempTableManager() {
        return this.tempTableManager;
    }

    private String makeSQLForInsertInstance() {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(this.istQueryModel.getTableName()).append(" (").append(this.istQueryModel.getIdColumn().getName()).append(", ").append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(this.istQueryModel.getStartUserColumn().getName()).append(", ").append(this.istQueryModel.getStartTimeColumn().getName()).append(", ").append(this.istQueryModel.getUpdateTimeColumn().getName()).append(", ").append(this.istQueryModel.getTaskIdColumn().getName()).append(", ").append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(this.istQueryModel.getStatusColumn().getName()).append(", ").append(this.istQueryModel.getLockColumn().getName()).append(", ").append(this.istQueryModel.getLastOperationIdColumn().getName());
        for (QueryModel.DimensionColumnModelDefine dimensionColumn : this.istQueryModel.getDimensionColumns()) {
            sqlBuilder.append(", ").append(dimensionColumn.getName());
        }
        int fieldCount = 10 + this.istQueryModel.getDimensionColumns().size();
        if (this.istQueryModel.getFormOrFormGroupColumn() != null) {
            sqlBuilder.append(", ").append(this.istQueryModel.getFormOrFormGroupColumn().getName());
            ++fieldCount;
        }
        sqlBuilder.append(") VALUES (");
        for (int i = 0; i < fieldCount - 1; ++i) {
            sqlBuilder.append("?, ");
        }
        sqlBuilder.append("?)");
        return sqlBuilder.toString();
    }

    public void insertInstance(ProcessInstanceDO instanceDO) {
        this.insertInstances(Arrays.asList(instanceDO));
    }

    public void insertInstances(Collection<ProcessInstanceDO> instanceDOs) {
        if (instanceDOs.isEmpty()) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(instanceDOs.size());
        for (ProcessInstanceDO instanceDO : instanceDOs) {
            ArrayList<Object> args = new ArrayList<Object>(16);
            args.add(instanceDO.getId());
            args.add(instanceDO.getProcessDefinitionId());
            args.add(instanceDO.getStartUser());
            args.add(instanceDO.getStartTime());
            args.add(instanceDO.getUpdateTime());
            args.add(instanceDO.getCurTaskId());
            args.add(instanceDO.getCurNode());
            args.add(instanceDO.getCurStatus());
            args.add("-");
            args.add(instanceDO.getLastOperationId());
            IBusinessObject bizObject = instanceDO.getBusinessKey().getBusinessObject();
            DimensionCombination dimensions = bizObject.getDimensions();
            for (String dimensionName : this.istQueryModel.getDimensionNames()) {
                args.add(dimensions.getValue(dimensionName));
            }
            if (bizObject instanceof IFormObject) {
                args.add(((IFormObject)bizObject).getFormKey());
            } else if (bizObject instanceof IFormGroupObject) {
                args.add(((IFormGroupObject)bizObject).getFormGroupKey());
            }
            batchArgs.add(args.toArray());
        }
        this.jdbcTemplate.batchUpdate(this.makeSQLForInsertInstance(), batchArgs);
    }

    private String makeSQLForUpdateInstance() {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(this.istQueryModel.getTableName()).append(" SET ").append(this.istQueryModel.getUpdateTimeColumn().getName()).append("=? ").append(", ").append(this.istQueryModel.getTaskIdColumn().getName()).append("=? ").append(", ").append(this.istQueryModel.getNodeColumn().getName()).append("=? ").append(", ").append(this.istQueryModel.getStatusColumn().getName()).append("=? ").append(", ").append(this.istQueryModel.getLastOperationIdColumn().getName()).append("=? ").append("WHERE ").append(this.istQueryModel.getIdColumn().getName()).append("=? ").append("AND ").append(this.istQueryModel.getLockColumn().getName()).append("=? ");
        return sqlBuilder.toString();
    }

    private String makeSQLForQueryUpdateResult() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(this.istQueryModel.getIdColumn().getName()).append(", ").append(this.istQueryModel.getTaskIdColumn().getName()).append(" FROM ").append(this.istQueryModel.getTableName()).append(" WHERE ").append(this.istQueryModel.getLockColumn().getName()).append("=?");
        return sqlBuilder.toString();
    }

    public boolean modifyInstance(ProcessInstanceDO instanceDO, String lockId) {
        return this.modifyInstances(Arrays.asList(instanceDO), lockId).size() > 0;
    }

    public Set<String> modifyInstances(Collection<ProcessInstanceDO> instanceDOs, String lockId) {
        if (instanceDOs.isEmpty()) {
            return Collections.emptySet();
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(instanceDOs.size());
        HashSet<String> modifiedInstanceIds = new HashSet<String>(instanceDOs.size());
        for (ProcessInstanceDO instanceDO : instanceDOs) {
            ArrayList<Object> args = new ArrayList<Object>(8);
            args.add(instanceDO.getUpdateTime());
            args.add(instanceDO.getCurTaskId());
            args.add(instanceDO.getCurNode());
            args.add(instanceDO.getCurStatus());
            args.add(instanceDO.getLastOperationId());
            args.add(instanceDO.getId());
            args.add(lockId);
            batchArgs.add(args.toArray());
            modifiedInstanceIds.add(instanceDO.getId());
        }
        int[] updateRowCounts = this.jdbcTemplate.batchUpdate(this.makeSQLForUpdateInstance(), batchArgs);
        if (Arrays.stream(updateRowCounts).sum() < instanceDOs.size()) {
            HashMap<String, ProcessInstanceDO> instanceModifyMap = new HashMap<String, ProcessInstanceDO>(instanceDOs.size());
            for (ProcessInstanceDO instanceDO : instanceDOs) {
                instanceModifyMap.put(instanceDO.getId(), instanceDO);
            }
            this.jdbcTemplate.query(this.makeSQLForQueryUpdateResult(), r -> {
                String instanceId = r.getString(1);
                String taskId = r.getString(2);
                if (!taskId.equals(((ProcessInstanceDO)instanceModifyMap.get(instanceId)).getCurTaskId())) {
                    modifiedInstanceIds.remove(instanceId);
                }
            }, new Object[]{lockId});
        }
        return modifiedInstanceIds;
    }

    private String makeSQLForDeleteInstanceById() {
        StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ").append(this.istQueryModel.getTableName()).append(" WHERE ").append(this.istQueryModel.getIdColumn().getName()).append(" IN(:").append(this.istQueryModel.getIdColumn().getName()).append(")");
        return sqlBuilder.toString();
    }

    public void deleteInstance(String instanceId) {
        this.deleteInstances(Arrays.asList(instanceId));
    }

    public void deleteInstances(Collection<String> instanceIds) {
        if (instanceIds.isEmpty()) {
            return;
        }
        HashMap<String, Collection<String>> namedArgs = new HashMap<String, Collection<String>>(1);
        namedArgs.put(this.istQueryModel.getIdColumn().getName(), instanceIds);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        namedParameterJdbcTemplate.update(this.makeSQLForDeleteInstanceById(), namedArgs);
    }

    private String makeSQLForSingleQueryInstanceByInstanceId() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(this.istQueryModel.getIdColumn().getName()).append(", ").append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(this.istQueryModel.getTaskIdColumn().getName()).append(", ").append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(this.istQueryModel.getStatusColumn().getName()).append(", ").append(this.istQueryModel.getStartUserColumn().getName()).append(", ").append(this.istQueryModel.getStartTimeColumn().getName()).append(", ").append(this.istQueryModel.getUpdateTimeColumn().getName());
        for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
            sqlBuilder.append(", ").append(dimensionCol.getName());
        }
        if (this.istQueryModel.getFormOrFormGroupColumn() != null) {
            sqlBuilder.append(", ").append(this.istQueryModel.getFormOrFormGroupColumn().getName());
        }
        sqlBuilder.append(" FROM ").append(this.istQueryModel.getTableName());
        sqlBuilder.append(" WHERE ").append(this.istQueryModel.getIdColumn().getName()).append("=?");
        return sqlBuilder.toString();
    }

    private String makeSQLForSingleQueryInstanceByBizObject() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(this.istQueryModel.getIdColumn().getName()).append(", ").append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(this.istQueryModel.getTaskIdColumn().getName()).append(", ").append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(this.istQueryModel.getStatusColumn().getName()).append(", ").append(this.istQueryModel.getStartUserColumn().getName()).append(", ").append(this.istQueryModel.getStartTimeColumn().getName()).append(", ").append(this.istQueryModel.getUpdateTimeColumn().getName()).append(" FROM ").append(this.istQueryModel.getTableName());
        boolean firstCondition = true;
        for (QueryModel.DimensionColumnModelDefine dimensionColumn : this.istQueryModel.getDimensionColumns()) {
            if (firstCondition) {
                sqlBuilder.append(" WHERE ").append(dimensionColumn.getName()).append("=?");
                firstCondition = false;
                continue;
            }
            sqlBuilder.append(" AND ").append(dimensionColumn.getName()).append("=?");
        }
        if (this.istQueryModel.getFormOrFormGroupColumn() != null) {
            sqlBuilder.append(" AND ").append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append("=?");
        }
        return sqlBuilder.toString();
    }

    ProcessInstanceDO readInstanceFrom(ResultSet rs, QueryFieldMode queryMode, String taskKey, ProcessInstacneValuePool valuePool) {
        try {
            ProcessInstanceDO instanceDO = new ProcessInstanceDO();
            int fi = 1;
            switch (queryMode) {
                case QUERY_ALL_FIELD: 
                case QUERY_FIX_FIELD: {
                    instanceDO.setId(rs.getString(fi++));
                    instanceDO.setProcessDefinitionId(valuePool.putProcessDefinitionIdIfAbsent(rs.getString(fi++)));
                    instanceDO.setCurTaskId(rs.getString(fi++));
                    instanceDO.setCurNode(valuePool.putNodeIdIfAbsent(rs.getString(fi++)));
                    instanceDO.setCurStatus(valuePool.putStatusIdIfAbsent(rs.getString(fi++)));
                    instanceDO.setStartUser(valuePool.putUserIdIfAbsent(rs.getString(fi++)));
                    Calendar starttime = Calendar.getInstance();
                    starttime.setTime(rs.getTimestamp(fi++));
                    instanceDO.setStartTime(starttime);
                    Calendar updatetime = Calendar.getInstance();
                    updatetime.setTime(rs.getTimestamp(fi++));
                    instanceDO.setUpdateTime(updatetime);
                    break;
                }
                case QUERY_SUMMARY_FIELD: {
                    instanceDO.setId(rs.getString(fi++));
                    instanceDO.setProcessDefinitionId(valuePool.putProcessDefinitionIdIfAbsent(rs.getString(fi++)));
                    instanceDO.setCurTaskId(rs.getString(fi++));
                    instanceDO.setCurNode(valuePool.putNodeIdIfAbsent(rs.getString(fi++)));
                    instanceDO.setCurStatus(valuePool.putStatusIdIfAbsent(rs.getString(fi++)));
                    break;
                }
                case QUERY_STATUS_FIELD: {
                    instanceDO.setId(rs.getString(fi++));
                    instanceDO.setProcessDefinitionId(valuePool.putProcessDefinitionIdIfAbsent(rs.getString(fi++)));
                    instanceDO.setCurStatus(valuePool.putStatusIdIfAbsent(rs.getString(fi++)));
                    break;
                }
            }
            if (queryMode == QueryFieldMode.QUERY_ALL_FIELD || queryMode == QueryFieldMode.QUERY_SUMMARY_FIELD || queryMode == QueryFieldMode.QUERY_STATUS_FIELD) {
                DimensionCombinationBuilder dimensionBuilder = new DimensionCombinationBuilder();
                for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
                    String dimensionName = dimensionCol.getDimensionName();
                    String dimensionVal = valuePool.putDimensionValueIfAbsent(dimensionName, rs.getString(fi++));
                    dimensionBuilder.setValue(dimensionName, dimensionCol.getEntityId(), (Object)dimensionVal);
                }
                Object businessObject = this.istQueryModel.isIncludeFormKeyColumn() ? new FormObject(dimensionBuilder.getCombination(), valuePool.putFormOrGroupIfAbsent(rs.getString(fi++))) : (this.istQueryModel.isIncludeFormGroupKeyColumn() ? new FormGroupObject(dimensionBuilder.getCombination(), valuePool.putFormOrGroupIfAbsent(rs.getString(fi++))) : new DimensionObject(dimensionBuilder.getCombination()));
                BusinessKey businessKey = new BusinessKey(taskKey, (IBusinessObject)businessObject);
                instanceDO.setBusinessKey((IBusinessKey)businessKey);
            }
            return instanceDO;
        }
        catch (SQLException e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u4ece\u6570\u636e\u96c6\u8bfb\u53d6\u6d41\u7a0b\u5b9e\u4f8b\u9519\u8bef\u3002", (Throwable)e);
        }
    }

    public ProcessInstanceDO queryInstance(String instanceId) {
        ArrayList<String> args = new ArrayList<String>(1);
        args.add(instanceId);
        return (ProcessInstanceDO)this.jdbcTemplate.query(this.makeSQLForSingleQueryInstanceByInstanceId(), rs -> {
            if (rs.next()) {
                return this.readInstanceFrom(rs, QueryFieldMode.QUERY_ALL_FIELD, null, ProcessInstacneValuePool.EMPTY);
            }
            return null;
        }, new Object[]{instanceId});
    }

    public ProcessInstanceDO queryInstance(IBusinessKey businessKey) {
        ArrayList<Object> args = new ArrayList<Object>(4);
        IBusinessObject businessObject = businessKey.getBusinessObject();
        DimensionCombination dimensions = businessObject.getDimensions();
        for (String dimensionName : this.istQueryModel.getDimensionNames()) {
            args.add(dimensions.getValue(dimensionName));
        }
        if (this.istQueryModel.getFormOrFormGroupColumn() != null) {
            if (businessObject instanceof IFormObject) {
                args.add(((IFormObject)businessObject).getFormKey());
            } else if (businessObject instanceof IFormGroupObject) {
                args.add(((IFormGroupObject)businessObject).getFormGroupKey());
            }
        }
        return (ProcessInstanceDO)this.jdbcTemplate.query(this.makeSQLForSingleQueryInstanceByBizObject(), rs -> {
            if (rs.next()) {
                ProcessInstanceDO instance = this.readInstanceFrom(rs, QueryFieldMode.QUERY_FIX_FIELD, null, ProcessInstacneValuePool.EMPTY);
                instance.setBusinessKey(businessKey);
                return instance;
            }
            return null;
        }, args.toArray());
    }

    private String makeSQLForBatchQueryInstanceByInstanceId(QueryFieldMode queryMode, InstanceIdValueProvider valueProvider) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        this.makeSelectForBatchQuery(sqlBuilder, queryMode);
        sqlBuilder.append(" FROM ").append(this.istQueryModel.getTableName()).append(" ").append(IST_TABLEALIAS);
        if (valueProvider.isNeedUsingTempTable()) {
            ITempTable tempTable = valueProvider.getTempTable();
            sqlBuilder.append(" JOIN ").append(tempTable.getTableName()).append(" ON ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getIdColumn().getName()).append(" = ").append(tempTable.getTableName()).append(".").append(((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName());
        }
        if (!valueProvider.isNeedUsingTempTable()) {
            sqlBuilder.append(" WHERE ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getIdColumn().getName()).append(" IN(:").append(this.istQueryModel.getIdColumn().getName()).append(")");
        }
        boolean firstOrderField = true;
        for (QueryModel.DimensionColumnModelDefine dimensionColumn : this.istQueryModel.getDimensionColumns()) {
            if (firstOrderField) {
                sqlBuilder.append(" ORDER BY ").append(IST_TABLEALIAS_DOT).append(dimensionColumn.getName());
                firstOrderField = false;
                continue;
            }
            sqlBuilder.append(",").append(IST_TABLEALIAS_DOT).append(dimensionColumn.getName());
        }
        return sqlBuilder.toString();
    }

    private String makeSQLForBatchQueryInstanceByBizKey(QueryFieldMode queryMode, BusinessKeyCollectionValueProvider valueProvider) {
        ITempTable tempTable;
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        this.makeSelectForBatchQuery(sqlBuilder, queryMode);
        sqlBuilder.append(" FROM ").append(this.istQueryModel.getTableName()).append(" ").append(IST_TABLEALIAS);
        for (QueryModel.DimensionColumnModelDefine dimensionColumnModelDefine : this.istQueryModel.getDimensionColumns()) {
            if (!valueProvider.isDimensionNeedUsingTempTable(dimensionColumnModelDefine.getDimensionName())) continue;
            ITempTable tempTable2 = valueProvider.getDimensionValueTable(dimensionColumnModelDefine.getDimensionName());
            sqlBuilder.append(" JOIN ").append(tempTable2.getTableName()).append(" ON ").append(IST_TABLEALIAS_DOT).append(dimensionColumnModelDefine.getName()).append(" = ").append(tempTable2.getTableName()).append(".").append(((LogicField)tempTable2.getMeta().getLogicFields().get(0)).getFieldName());
        }
        if (this.istQueryModel.isIncludeFormKeyColumn()) {
            if (valueProvider.isFormNeedUsingTempTable()) {
                tempTable = valueProvider.getFormValueTable();
                sqlBuilder.append(" JOIN ").append(tempTable.getTableName()).append(" ON ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(" = ").append(tempTable.getTableName()).append(".").append(((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName());
            }
        } else if (this.istQueryModel.isIncludeFormGroupKeyColumn() && valueProvider.isFormGroupNeedUsingTempTable()) {
            tempTable = valueProvider.getFormValueTable();
            sqlBuilder.append(" JOIN ").append(tempTable.getTableName()).append(" ON ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(" = ").append(tempTable.getTableName()).append(".").append(((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName());
        }
        int lengthUntilWhere = sqlBuilder.length();
        for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
            if (valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            if (sqlBuilder.length() == lengthUntilWhere) {
                sqlBuilder.append(" WHERE ");
            } else {
                sqlBuilder.append(" AND ");
            }
            if (valueProvider.getDimensionValues(dimensionCol.getDimensionName()).size() == 1) {
                sqlBuilder.append(IST_TABLEALIAS_DOT).append(dimensionCol.getName()).append(" =:").append(dimensionCol.getName());
                continue;
            }
            sqlBuilder.append(IST_TABLEALIAS_DOT).append(dimensionCol.getName()).append(" IN(:").append(dimensionCol.getName()).append(")");
        }
        if (this.istQueryModel.isIncludeFormKeyColumn()) {
            if (!valueProvider.isFormNeedUsingTempTable()) {
                if (valueProvider.getFormValues().size() == 1) {
                    sqlBuilder.append(" AND ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(" =:").append(this.istQueryModel.getFormOrFormGroupColumn().getName());
                } else {
                    sqlBuilder.append(" AND ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(" IN(:").append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(")");
                }
            }
        } else if (this.istQueryModel.isIncludeFormGroupKeyColumn() && !valueProvider.isFormGroupNeedUsingTempTable()) {
            if (valueProvider.getFormGroupValues().size() == 1) {
                sqlBuilder.append(" AND ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(" =:").append(this.istQueryModel.getFormOrFormGroupColumn().getName());
            } else {
                sqlBuilder.append(" AND ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(" IN(:").append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(")");
            }
        }
        boolean bl = true;
        for (QueryModel.DimensionColumnModelDefine dimensionColumn : this.istQueryModel.getDimensionColumns()) {
            boolean bl2;
            if (bl2) {
                sqlBuilder.append(" ORDER BY ").append(IST_TABLEALIAS_DOT).append(dimensionColumn.getName());
                bl2 = false;
                continue;
            }
            sqlBuilder.append(",").append(IST_TABLEALIAS_DOT).append(dimensionColumn.getName());
        }
        return sqlBuilder.toString();
    }

    private void makeSelectForBatchQuery(StringBuilder sqlBuilder, QueryFieldMode queryMode) {
        switch (queryMode) {
            case QUERY_ALL_FIELD: {
                sqlBuilder.append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getTaskIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStatusColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStartUserColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStartTimeColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getUpdateTimeColumn().getName());
                for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
                    sqlBuilder.append(", ").append(IST_TABLEALIAS_DOT).append(dimensionCol.getName());
                }
                if (this.istQueryModel.getFormOrFormGroupColumn() == null) break;
                sqlBuilder.append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName());
                break;
            }
            case QUERY_FIX_FIELD: {
                sqlBuilder.append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getTaskIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStatusColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStartUserColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStartTimeColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getUpdateTimeColumn().getName());
                break;
            }
            case QUERY_SUMMARY_FIELD: {
                sqlBuilder.append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getTaskIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStatusColumn().getName());
                for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
                    sqlBuilder.append(", ").append(IST_TABLEALIAS_DOT).append(dimensionCol.getName());
                }
                if (this.istQueryModel.getFormOrFormGroupColumn() == null) break;
                sqlBuilder.append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName());
                break;
            }
            case QUERY_STATUS_FIELD: {
                sqlBuilder.append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getStatusColumn().getName());
                for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
                    sqlBuilder.append(", ").append(IST_TABLEALIAS_DOT).append(dimensionCol.getName());
                }
                if (this.istQueryModel.getFormOrFormGroupColumn() == null) break;
                sqlBuilder.append(", ").append(IST_TABLEALIAS_DOT).append(this.istQueryModel.getFormOrFormGroupColumn().getName());
                break;
            }
        }
    }

    private Map<String, Object> makeArgsForBatchQueryInstance(BusinessKeyCollectionValueProvider valueProvider) {
        Collection<String> values;
        HashMap<String, Object> args = new HashMap<String, Object>(4);
        for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
            if (valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            Collection<String> values2 = valueProvider.getDimensionValues(dimensionCol.getDimensionName());
            if (values2.size() == 1) {
                args.put(dimensionCol.getName(), values2.stream().findFirst().get());
                continue;
            }
            args.put(dimensionCol.getName(), values2);
        }
        if (this.istQueryModel.isIncludeFormKeyColumn()) {
            if (!valueProvider.isFormNeedUsingTempTable()) {
                values = valueProvider.getFormValues();
                if (values.size() == 1) {
                    args.put(this.istQueryModel.getFormOrFormGroupColumn().getName(), values.stream().findFirst().get());
                } else {
                    args.put(this.istQueryModel.getFormOrFormGroupColumn().getName(), values);
                }
            }
        } else if (this.istQueryModel.isIncludeFormGroupKeyColumn() && !valueProvider.isFormGroupNeedUsingTempTable()) {
            values = valueProvider.getFormGroupValues();
            if (values.size() == 1) {
                args.put(this.istQueryModel.getFormOrFormGroupColumn().getName(), values.stream().findFirst().get());
            } else {
                args.put(this.istQueryModel.getFormOrFormGroupColumn().getName(), values);
            }
        }
        return args;
    }

    public Map<String, ProcessInstanceDO> queryInstances(String taskKey, Collection<String> instanceIds, QueryFieldMode queryMode) {
        if (instanceIds.isEmpty()) {
            return Collections.emptyMap();
        }
        try (InstanceIdValueProvider valueProvider = new InstanceIdValueProvider(instanceIds, this.tempTableManager);){
            String sql = this.makeSQLForBatchQueryInstanceByInstanceId(queryMode, valueProvider);
            if (valueProvider.isNeedUsingTempTable()) {
                Map map = (Map)this.jdbcTemplate.query(sql, rs -> {
                    LinkedHashMap<String, ProcessInstanceDO> result = new LinkedHashMap<String, ProcessInstanceDO>();
                    ProcessInstacneValuePool valuePool = ProcessInstacneValuePool.createPool();
                    while (rs.next()) {
                        ProcessInstanceDO instanceDO = this.readInstanceFrom(rs, queryMode, taskKey, valuePool);
                        result.put(instanceDO.getId(), instanceDO);
                    }
                    return result;
                });
                return map;
            }
            HashMap<String, Collection<String>> namedArgs = new HashMap<String, Collection<String>>(1);
            namedArgs.put(this.istQueryModel.getIdColumn().getName(), valueProvider.getInstanceIds());
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            Map map = (Map)namedParameterJdbcTemplate.query(sql, namedArgs, rs -> {
                LinkedHashMap<String, ProcessInstanceDO> result = new LinkedHashMap<String, ProcessInstanceDO>();
                ProcessInstacneValuePool valuePool = ProcessInstacneValuePool.createPool();
                while (rs.next()) {
                    ProcessInstanceDO instanceDO = this.readInstanceFrom(rs, queryMode, taskKey, valuePool);
                    result.put(instanceDO.getId(), instanceDO);
                }
                return result;
            });
            return map;
        }
    }

    public Map<IBusinessObject, ProcessInstanceDO> queryInstances(IBusinessKeyCollection bizKeyCollection, QueryFieldMode queryMode) {
        if (bizKeyCollection.getBusinessObjects().size() == 0) {
            return Collections.emptyMap();
        }
        try (BusinessKeyCollectionValueProvider valueProvider = new BusinessKeyCollectionValueProvider(bizKeyCollection, this.tempTableManager);){
            String sql = this.makeSQLForBatchQueryInstanceByBizKey(queryMode, valueProvider);
            Map<String, Object> namedArgs = this.makeArgsForBatchQueryInstance(valueProvider);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            Map map = (Map)namedParameterJdbcTemplate.query(sql, namedArgs, rs -> {
                LinkedHashMap<IBusinessObject, ProcessInstanceDO> result = new LinkedHashMap<IBusinessObject, ProcessInstanceDO>();
                ProcessInstacneValuePool valuePool = ProcessInstacneValuePool.createPool();
                BusinessObjectSet businessObjectSet = new BusinessObjectSet(bizKeyCollection.getBusinessObjects());
                while (rs.next()) {
                    ProcessInstanceDO instanceDO = this.readInstanceFrom(rs, queryMode, bizKeyCollection.getTask(), valuePool);
                    if (!businessObjectSet.contains(instanceDO.getBusinessKey().getBusinessObject())) continue;
                    result.put(instanceDO.getBusinessKey().getBusinessObject(), instanceDO);
                }
                return result;
            });
            return map;
        }
    }

    public Set<IBusinessObject> queryExistsInstances(IBusinessKeyCollection bizKeyCollection) {
        if (bizKeyCollection.getBusinessObjects().size() == 0) {
            return Collections.emptySet();
        }
        try (BusinessKeyCollectionValueProvider valueProvider = new BusinessKeyCollectionValueProvider(bizKeyCollection, this.tempTableManager);){
            String sql = this.makeSQLForBatchQueryInstanceByBizKey(QueryFieldMode.QUERY_SUMMARY_FIELD, valueProvider);
            Map<String, Object> namedArgs = this.makeArgsForBatchQueryInstance(valueProvider);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            Set set = (Set)namedParameterJdbcTemplate.query(sql, namedArgs, rs -> {
                HashSet<IBusinessObject> result = new HashSet<IBusinessObject>();
                while (rs.next()) {
                    ProcessInstacneValuePool valuePool = ProcessInstacneValuePool.createPool();
                    ProcessInstanceDO instanceDO = this.readInstanceFrom(rs, QueryFieldMode.QUERY_SUMMARY_FIELD, bizKeyCollection.getTask(), valuePool);
                    result.add(instanceDO.getBusinessKey().getBusinessObject());
                }
                return result;
            });
            return set;
        }
    }

    public boolean existsInstance() {
        String sql = "SELECT " + this.istQueryModel.getIdColumn().getName() + " FROM " + this.istQueryModel.getTableName();
        return (Boolean)this.jdbcTemplate.query(sql, rs -> rs.next());
    }

    private String makeSQLForSingleQueryFormOrGroupInstance() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(this.istQueryModel.getFormOrFormGroupColumn().getName()).append(", ").append(this.istQueryModel.getNodeColumn().getName()).append(", ").append(this.istQueryModel.getStatusColumn().getName()).append(" FROM ").append(this.istQueryModel.getTableName());
        boolean firstCondition = true;
        for (QueryModel.DimensionColumnModelDefine dimensionColumn : this.istQueryModel.getDimensionColumns()) {
            if (firstCondition) {
                sqlBuilder.append(" WHERE ").append(dimensionColumn.getName()).append("=?");
                firstCondition = false;
                continue;
            }
            sqlBuilder.append(" AND ").append(dimensionColumn.getName()).append("=?");
        }
        return sqlBuilder.toString();
    }

    public Map<String, ProcessInstanceDO> queryFormOrGroupInstance(IBusinessKey businessKey) {
        ArrayList<Object> args = new ArrayList<Object>(4);
        IBusinessObject businessObject = businessKey.getBusinessObject();
        DimensionCombination dimensions = businessObject.getDimensions();
        for (String dimensionName : this.istQueryModel.getDimensionNames()) {
            args.add(dimensions.getValue(dimensionName));
        }
        return (Map)this.jdbcTemplate.query(this.makeSQLForSingleQueryFormOrGroupInstance(), rs -> {
            HashMap<String, ProcessInstanceDO> instances = new HashMap<String, ProcessInstanceDO>();
            while (rs.next()) {
                ProcessInstanceDO instance = new ProcessInstanceDO();
                instance.setCurNode(rs.getString(2));
                instance.setCurStatus(rs.getString(3));
                String formOrGroupKey = rs.getString(1);
                if (StringUtils.isEmpty(formOrGroupKey) || formOrGroupKey.equals("_NULL_")) continue;
                instances.put(rs.getString(1), instance);
            }
            return instances;
        }, args.toArray());
    }

    private String makeSQLForBatchQueryFormOrGroupInstance(BusinessKeyCollectionValueProvider valueProvider) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        this.makeSelectForBatchQuery(sqlBuilder, QueryFieldMode.QUERY_SUMMARY_FIELD);
        sqlBuilder.append(" FROM ").append(this.istQueryModel.getTableName()).append(" ").append(IST_TABLEALIAS);
        for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
            if (!valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            ITempTable tempTable = valueProvider.getDimensionValueTable(dimensionCol.getDimensionName());
            sqlBuilder.append(" JOIN ").append(tempTable.getTableName()).append(" ON ").append(IST_TABLEALIAS_DOT).append(dimensionCol.getName()).append(" = ").append(tempTable.getTableName()).append(".").append(((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName());
        }
        int lengthUntilWhere = sqlBuilder.length();
        for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
            if (valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            if (sqlBuilder.length() == lengthUntilWhere) {
                sqlBuilder.append(" WHERE ");
            } else {
                sqlBuilder.append(" AND ");
            }
            if (valueProvider.getDimensionValues(dimensionCol.getDimensionName()).size() == 1) {
                sqlBuilder.append(IST_TABLEALIAS_DOT).append(dimensionCol.getName()).append(" =:").append(dimensionCol.getName());
                continue;
            }
            sqlBuilder.append(IST_TABLEALIAS_DOT).append(dimensionCol.getName()).append(" IN(:").append(dimensionCol.getName()).append(")");
        }
        return sqlBuilder.toString();
    }

    public Map<DimensionCombination, Map<String, ProcessInstanceDO>> batchQueryFormOrGroupInstance(IBusinessKeyCollection businessKeys) {
        if (!this.istQueryModel.isIncludeFormKeyColumn() && !this.istQueryModel.isIncludeFormGroupKeyColumn()) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u4e0d\u652f\u6301\u7684\u6d41\u7a0b\u7c7b\u578b\u3002");
        }
        if (businessKeys.getBusinessObjects().size() == 0) {
            return Collections.emptyMap();
        }
        try (BusinessKeyCollectionValueProvider valueProvider = new BusinessKeyCollectionValueProvider(businessKeys, this.tempTableManager);){
            String sql = this.makeSQLForBatchQueryFormOrGroupInstance(valueProvider);
            HashMap<String, Object> namedArgs = new HashMap<String, Object>(4);
            for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
                if (valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
                Collection<String> values = valueProvider.getDimensionValues(dimensionCol.getDimensionName());
                if (values.size() == 1) {
                    namedArgs.put(dimensionCol.getName(), values.stream().findFirst().get());
                    continue;
                }
                namedArgs.put(dimensionCol.getName(), values);
            }
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            Map map = (Map)namedParameterJdbcTemplate.query(sql, namedArgs, rs -> {
                HashMap<DimensionCombination, HashMap<String, ProcessInstanceDO>> instances = new HashMap<DimensionCombination, HashMap<String, ProcessInstanceDO>>();
                ProcessInstacneValuePool valuePool = ProcessInstacneValuePool.createPool();
                while (rs.next()) {
                    ProcessInstanceDO instance = new ProcessInstanceDO();
                    int fi = 4;
                    instance.setCurNode(valuePool.putNodeIdIfAbsent(rs.getString(fi++)));
                    instance.setCurStatus(valuePool.putStatusIdIfAbsent(rs.getString(fi++)));
                    DimensionCombinationBuilder dimensionBuilder = new DimensionCombinationBuilder();
                    for (QueryModel.DimensionColumnModelDefine dimensionCol : this.istQueryModel.getDimensionColumns()) {
                        String dimensionName = dimensionCol.getDimensionName();
                        String dimensionVal = valuePool.putDimensionValueIfAbsent(dimensionName, rs.getString(fi++));
                        dimensionBuilder.setValue(dimensionName, dimensionCol.getEntityId(), (Object)dimensionVal);
                    }
                    DimensionCombination dimension = dimensionBuilder.getCombination();
                    String formOrGroupKey = rs.getString(fi++);
                    HashMap<String, ProcessInstanceDO> ist2Dim = (HashMap<String, ProcessInstanceDO>)instances.get(dimension);
                    if (ist2Dim == null) {
                        ist2Dim = new HashMap<String, ProcessInstanceDO>();
                        instances.put(dimension, ist2Dim);
                    }
                    ist2Dim.put(formOrGroupKey, instance);
                }
                return instances;
            });
            return map;
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM " + this.istQueryModel.getTableName();
        this.jdbcTemplate.execute(sql);
    }

    public static enum QueryFieldMode {
        QUERY_ALL_FIELD,
        QUERY_FIX_FIELD,
        QUERY_SUMMARY_FIELD,
        QUERY_STATUS_FIELD;

    }
}

