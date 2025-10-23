/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
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
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
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
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceWithOperation;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ProcessInstanceJoinOperationQuery {
    private static final String IST_ALIAS = "ist";
    private static final String IST_ALIASDOT = "ist.";
    private static final String OPT_ALIAS = "opt";
    private static final String OPT_ALIASDOT = "opt.";
    private ProcessInstanceQuery istQuery;
    private ProcessOperationQuery optQuery;

    public ProcessInstanceJoinOperationQuery(ProcessInstanceQuery istQuery, ProcessOperationQuery optQuery) {
        this.istQuery = istQuery;
        this.optQuery = optQuery;
    }

    private String makeSQLForSingleQueryInstanceByBizObject() {
        QueryModel.ProcessInstanceQueryModel istQueryModel = this.istQuery.getQueryModel();
        QueryModel.ProcessOperationQueryModel optQueryModel = this.optQuery.getQueryModel();
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(IST_ALIASDOT).append(istQueryModel.getIdColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getTaskIdColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getNodeColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getStatusColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getStartUserColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getStartTimeColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getUpdateTimeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getIdColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getIstIdColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getFromNodeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getActionColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getToNodeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getNewStatusColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateTimeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateUserColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateIdentityColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getCommentColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateTypeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getForceReportColumn().getName()).append(" FROM ").append(istQueryModel.getTableName()).append(" ").append(IST_ALIAS).append(" LEFT JOIN ").append(optQueryModel.getTableName()).append(" ").append(OPT_ALIAS).append(" ON ").append(IST_ALIASDOT).append(istQueryModel.getLastOperationIdColumn().getName()).append(" = ").append(OPT_ALIASDOT).append(optQueryModel.getIdColumn().getName());
        boolean firstCondition = true;
        for (QueryModel.DimensionColumnModelDefine dimensionColumn : istQueryModel.getDimensionColumns()) {
            if (firstCondition) {
                sqlBuilder.append(" WHERE ").append(dimensionColumn.getName()).append("=?");
                firstCondition = false;
                continue;
            }
            sqlBuilder.append(" AND ").append(dimensionColumn.getName()).append("=?");
        }
        if (istQueryModel.getFormOrFormGroupColumn() != null) {
            sqlBuilder.append(" AND ").append(istQueryModel.getFormOrFormGroupColumn().getName()).append("=?");
        }
        return sqlBuilder.toString();
    }

    public ProcessInstanceWithOperation queryInstanceWithOperation(IBusinessKey businessKey) {
        QueryModel.ProcessInstanceQueryModel istQueryModel = this.istQuery.getQueryModel();
        ArrayList<Object> args = new ArrayList<Object>(4);
        IBusinessObject businessObject = businessKey.getBusinessObject();
        DimensionCombination dimensions = businessObject.getDimensions();
        for (String dimensionName : istQueryModel.getDimensionNames()) {
            args.add(dimensions.getValue(dimensionName));
        }
        if (istQueryModel.getFormOrFormGroupColumn() != null) {
            if (businessObject instanceof IFormObject) {
                args.add(((IFormObject)businessObject).getFormKey());
            } else if (businessObject instanceof IFormGroupObject) {
                args.add(((IFormGroupObject)businessObject).getFormGroupKey());
            }
        }
        return (ProcessInstanceWithOperation)this.istQuery.getJdbcTemplate().query(this.makeSQLForSingleQueryInstanceByBizObject(), rs -> {
            if (rs.next()) {
                ProcessInstanceWithOperation result = new ProcessInstanceWithOperation();
                ProcessInstanceDO instance = new ProcessInstanceDO();
                int fi = 1;
                instance.setId(rs.getString(fi++));
                instance.setProcessDefinitionId(rs.getString(fi++));
                instance.setCurTaskId(rs.getString(fi++));
                instance.setCurNode(rs.getString(fi++));
                instance.setCurStatus(rs.getString(fi++));
                instance.setStartUser(rs.getString(fi++));
                Calendar starttime = Calendar.getInstance();
                starttime.setTime(rs.getTimestamp(fi++));
                instance.setStartTime(starttime);
                Calendar updatetime = Calendar.getInstance();
                updatetime.setTime(rs.getTimestamp(fi++));
                instance.setUpdateTime(updatetime);
                instance.setBusinessKey(businessKey);
                result.setInstance(instance);
                ProcessOperationDO operation = new ProcessOperationDO();
                operation.setId(rs.getString(fi++));
                operation.setInstanceId(rs.getString(fi++));
                operation.setFromNode(rs.getString(fi++));
                operation.setAction(rs.getString(fi++));
                operation.setToNode(rs.getString(fi++));
                operation.setNewStatus(rs.getString(fi++));
                Calendar operateTime = Calendar.getInstance();
                operateTime.setTime(rs.getTimestamp(fi++));
                operation.setOperateTime(operateTime);
                operation.setOperate_user(rs.getString(fi++));
                operation.setOperate_identity(rs.getString(fi++));
                operation.setComment(rs.getString(fi++));
                operation.setOperate_type(rs.getString(fi++));
                operation.setForceReport(rs.getInt(fi++) == 1);
                result.setOperation(operation);
                return result;
            }
            return null;
        }, args.toArray());
    }

    private String makeSQLForBatchQueryInstanceByBizKey(BusinessKeyCollectionValueProvider valueProvider) {
        ITempTable tempTable;
        QueryModel.ProcessInstanceQueryModel istQueryModel = this.istQuery.getQueryModel();
        QueryModel.ProcessOperationQueryModel optQueryModel = this.optQuery.getQueryModel();
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(IST_ALIASDOT).append(istQueryModel.getIdColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getDefinitionIdColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getTaskIdColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getNodeColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getStatusColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getStartUserColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getStartTimeColumn().getName()).append(", ").append(IST_ALIASDOT).append(istQueryModel.getUpdateTimeColumn().getName());
        for (QueryModel.DimensionColumnModelDefine dimensionCol : istQueryModel.getDimensionColumns()) {
            sqlBuilder.append(", ").append(IST_ALIASDOT).append(dimensionCol.getName());
        }
        if (istQueryModel.getFormOrFormGroupColumn() != null) {
            sqlBuilder.append(", ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName());
        }
        sqlBuilder.append(", ").append(OPT_ALIASDOT).append(optQueryModel.getIdColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getIstIdColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getFromNodeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getActionColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getToNodeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getNewStatusColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateTimeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateUserColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateIdentityColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getCommentColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getOperateTypeColumn().getName()).append(", ").append(OPT_ALIASDOT).append(optQueryModel.getForceReportColumn().getName());
        sqlBuilder.append(" FROM ").append(istQueryModel.getTableName()).append(" ").append(IST_ALIAS).append(" LEFT JOIN ").append(optQueryModel.getTableName()).append(" ").append(OPT_ALIAS).append(" ON ").append(IST_ALIASDOT).append(istQueryModel.getLastOperationIdColumn().getName()).append(" = ").append(OPT_ALIASDOT).append(optQueryModel.getIdColumn().getName());
        for (QueryModel.DimensionColumnModelDefine dimensionCol : istQueryModel.getDimensionColumns()) {
            if (!valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            ITempTable tempTable2 = valueProvider.getDimensionValueTable(dimensionCol.getDimensionName());
            sqlBuilder.append(" JOIN ").append(tempTable2.getTableName()).append(" ON ").append(IST_ALIASDOT).append(dimensionCol.getName()).append(" = ").append(tempTable2.getTableName()).append(".").append(((LogicField)tempTable2.getMeta().getLogicFields().get(0)).getFieldName());
        }
        if (istQueryModel.isIncludeFormKeyColumn()) {
            if (valueProvider.isFormNeedUsingTempTable()) {
                tempTable = valueProvider.getFormValueTable();
                sqlBuilder.append(" JOIN ").append(tempTable.getTableName()).append(" ON ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName()).append(" = ").append(tempTable.getTableName()).append(".").append(((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName());
            }
        } else if (istQueryModel.isIncludeFormGroupKeyColumn() && valueProvider.isFormGroupNeedUsingTempTable()) {
            tempTable = valueProvider.getFormValueTable();
            sqlBuilder.append(" JOIN ").append(tempTable.getTableName()).append(" ON ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName()).append(" = ").append(tempTable.getTableName()).append(".").append(((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName());
        }
        int lengthUntilWhere = sqlBuilder.length();
        for (QueryModel.DimensionColumnModelDefine dimensionCol : istQueryModel.getDimensionColumns()) {
            if (valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            if (sqlBuilder.length() == lengthUntilWhere) {
                sqlBuilder.append(" WHERE ");
            } else {
                sqlBuilder.append(" AND ");
            }
            if (valueProvider.getDimensionValues(dimensionCol.getDimensionName()).size() == 1) {
                sqlBuilder.append(IST_ALIASDOT).append(dimensionCol.getName()).append(" =:").append(dimensionCol.getName());
                continue;
            }
            sqlBuilder.append(IST_ALIASDOT).append(dimensionCol.getName()).append(" IN(:").append(dimensionCol.getName()).append(")");
        }
        if (istQueryModel.isIncludeFormKeyColumn()) {
            if (!valueProvider.isFormNeedUsingTempTable()) {
                if (valueProvider.getFormValues().size() == 1) {
                    sqlBuilder.append(" AND ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName()).append(" =:").append(istQueryModel.getFormOrFormGroupColumn().getName());
                } else {
                    sqlBuilder.append(" AND ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName()).append(" IN(:").append(istQueryModel.getFormOrFormGroupColumn().getName()).append(")");
                }
            }
        } else if (istQueryModel.isIncludeFormGroupKeyColumn() && !valueProvider.isFormGroupNeedUsingTempTable()) {
            if (valueProvider.getFormGroupValues().size() == 1) {
                sqlBuilder.append(" AND ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName()).append(" =:").append(istQueryModel.getFormOrFormGroupColumn().getName());
            } else {
                sqlBuilder.append(" AND ").append(IST_ALIASDOT).append(istQueryModel.getFormOrFormGroupColumn().getName()).append(" IN(:").append(istQueryModel.getFormOrFormGroupColumn().getName()).append(")");
            }
        }
        return sqlBuilder.toString();
    }

    private Map<String, Object> makeArgsForBatchQueryInstance(BusinessKeyCollectionValueProvider valueProvider) {
        Collection<String> values;
        QueryModel.ProcessInstanceQueryModel istQueryModel = this.istQuery.getQueryModel();
        HashMap<String, Object> args = new HashMap<String, Object>(4);
        for (QueryModel.DimensionColumnModelDefine dimensionCol : istQueryModel.getDimensionColumns()) {
            if (valueProvider.isDimensionNeedUsingTempTable(dimensionCol.getDimensionName())) continue;
            Collection<String> values2 = valueProvider.getDimensionValues(dimensionCol.getDimensionName());
            if (values2.size() == 1) {
                args.put(dimensionCol.getName(), values2.stream().findFirst().get());
                continue;
            }
            args.put(dimensionCol.getName(), values2);
        }
        if (istQueryModel.isIncludeFormKeyColumn()) {
            if (!valueProvider.isFormNeedUsingTempTable()) {
                values = valueProvider.getFormValues();
                if (values.size() == 1) {
                    args.put(istQueryModel.getFormOrFormGroupColumn().getName(), values.stream().findFirst().get());
                } else {
                    args.put(istQueryModel.getFormOrFormGroupColumn().getName(), values);
                }
            }
        } else if (istQueryModel.isIncludeFormGroupKeyColumn() && !valueProvider.isFormGroupNeedUsingTempTable()) {
            values = valueProvider.getFormGroupValues();
            if (values.size() == 1) {
                args.put(istQueryModel.getFormOrFormGroupColumn().getName(), values.stream().findFirst().get());
            } else {
                args.put(istQueryModel.getFormOrFormGroupColumn().getName(), values);
            }
        }
        return args;
    }

    public Map<IBusinessObject, ProcessInstanceWithOperation> queryInstancesWithOperation(IBusinessKeyCollection bizKeyCollection) {
        if (bizKeyCollection.getBusinessObjects().size() == 0) {
            return Collections.emptyMap();
        }
        try (BusinessKeyCollectionValueProvider valueProvider = new BusinessKeyCollectionValueProvider(bizKeyCollection, this.istQuery.getTempTableManager());){
            String sql = this.makeSQLForBatchQueryInstanceByBizKey(valueProvider);
            Map<String, Object> namedArgs = this.makeArgsForBatchQueryInstance(valueProvider);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.istQuery.getJdbcTemplate());
            Map map = (Map)namedParameterJdbcTemplate.query(sql, namedArgs, rs -> {
                QueryModel.ProcessInstanceQueryModel istQueryModel = this.istQuery.getQueryModel();
                HashMap<DimensionObject, ProcessInstanceWithOperation> result = new HashMap<DimensionObject, ProcessInstanceWithOperation>();
                BusinessObjectSet businessObjectSet = new BusinessObjectSet(bizKeyCollection.getBusinessObjects());
                while (rs.next()) {
                    ProcessInstanceWithOperation istWithOpt = new ProcessInstanceWithOperation();
                    ProcessInstanceDO instance = new ProcessInstanceDO();
                    int fi = 1;
                    instance.setId(rs.getString(fi++));
                    instance.setProcessDefinitionId(rs.getString(fi++));
                    instance.setCurTaskId(rs.getString(fi++));
                    instance.setCurNode(rs.getString(fi++));
                    instance.setCurStatus(rs.getString(fi++));
                    instance.setStartUser(rs.getString(fi++));
                    Calendar starttime = Calendar.getInstance();
                    starttime.setTime(rs.getTimestamp(fi++));
                    instance.setStartTime(starttime);
                    Calendar updatetime = Calendar.getInstance();
                    updatetime.setTime(rs.getTimestamp(fi++));
                    instance.setUpdateTime(updatetime);
                    DimensionCombinationBuilder dimensionBuilder = new DimensionCombinationBuilder();
                    for (QueryModel.DimensionColumnModelDefine dimensionCol : istQueryModel.getDimensionColumns()) {
                        String dimensionName = dimensionCol.getDimensionName();
                        String dimensionVal = rs.getString(fi++);
                        dimensionBuilder.setValue(dimensionName, dimensionCol.getEntityId(), (Object)dimensionVal);
                    }
                    Object businessObject = istQueryModel.isIncludeFormKeyColumn() ? new FormObject(dimensionBuilder.getCombination(), rs.getString(fi++)) : (istQueryModel.isIncludeFormGroupKeyColumn() ? new FormGroupObject(dimensionBuilder.getCombination(), rs.getString(fi++)) : new DimensionObject(dimensionBuilder.getCombination()));
                    if (!businessObjectSet.contains((IBusinessObject)businessObject)) continue;
                    BusinessKey businessKey = new BusinessKey(bizKeyCollection.getTask(), (IBusinessObject)businessObject);
                    instance.setBusinessKey((IBusinessKey)businessKey);
                    istWithOpt.setInstance(instance);
                    ProcessOperationDO operation = new ProcessOperationDO();
                    operation.setId(rs.getString(fi++));
                    operation.setInstanceId(rs.getString(fi++));
                    operation.setFromNode(rs.getString(fi++));
                    operation.setAction(rs.getString(fi++));
                    operation.setToNode(rs.getString(fi++));
                    operation.setNewStatus(rs.getString(fi++));
                    Timestamp rsTimestamp = rs.getTimestamp(fi++);
                    if (rsTimestamp != null) {
                        Calendar operateTime = Calendar.getInstance();
                        operateTime.setTime(rsTimestamp);
                        operation.setOperateTime(operateTime);
                    }
                    operation.setOperate_user(rs.getString(fi++));
                    operation.setOperate_identity(rs.getString(fi++));
                    operation.setComment(rs.getString(fi++));
                    operation.setOperate_type(rs.getString(fi++));
                    operation.setForceReport(rs.getInt(fi++) == 1);
                    istWithOpt.setOperation(operation);
                    result.put((DimensionObject)businessObject, istWithOpt);
                }
                return result;
            });
            return map;
        }
    }
}

