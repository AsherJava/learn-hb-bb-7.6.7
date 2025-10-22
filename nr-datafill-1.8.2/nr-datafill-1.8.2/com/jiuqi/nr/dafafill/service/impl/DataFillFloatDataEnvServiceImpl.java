/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.DuplicateRowKeysException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFieldWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataDeleteRow;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.DataFillQueryResult;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.FieldReadWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.OrderField;
import com.jiuqi.nr.dafafill.model.PageInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.OrderMode;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDataFillEnvBaseDataService;
import com.jiuqi.nr.dafafill.util.DateUtils;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataFillFloatDataEnvServiceImpl
extends IDataFillEnvBaseDataService {
    private static final Logger logger = LoggerFactory.getLogger(DataFillFloatDataEnvServiceImpl.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    DFDimensionValueGetService dfDimensionValueGetService;

    @Override
    public TableType getTableType() {
        return TableType.FLOAT;
    }

    @Override
    public DataFillQueryResult query(DataFillDataQueryInfo queryInfo) {
        String dataSchemeKey;
        LinkedHashMap<DimensionValueSet, List<AbstractData>> resultMap = new LinkedHashMap<DimensionValueSet, List<AbstractData>>();
        DataFillContext context = queryInfo.getContext();
        Map<String, QueryField> queryFieldsMap = this.dFDimensionParser.getQueryFieldsMap(context);
        Map<String, QueryField> simplifyQueryFieldsMap = this.dFDimensionParser.getSimplifyQueryFieldsMap(context);
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Iterator<DFDimensionValue> iterator = dimensionValues.iterator();
        ArrayList<DFDimensionValue> filterConditions = new ArrayList<DFDimensionValue>();
        while (iterator.hasNext()) {
            DFDimensionValue dfDimensionValue = iterator.next();
            String name = dfDimensionValue.getName();
            QueryField queryField = simplifyQueryFieldsMap.get(name);
            if (queryField.getFieldType() != FieldType.TABLEDIMENSION) continue;
            iterator.remove();
            if (!StringUtils.hasLength(this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel()))) continue;
            filterConditions.add(dfDimensionValue);
        }
        List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
        List<QueryField> zbQueryFields = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
        DimensionValueSet entityDimensionValueSet = this.dFDimensionParser.parserGetEntityDimensionValueSet(context);
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, entityDimensionValueSet);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.setMasterKeys(sqlDimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        executorContext.setJQReportModel(true);
        executorContext.setAutoDataMasking(true);
        List<FieldDefine> floatOrderFields = null;
        List<TableDefine> tableDefines = null;
        List<DataTable> dataTables = null;
        try {
            executorContext.setOrgEntityId(this.entityMetaService.getEntityIdByCode(masterField.getSimplifyFullCode()));
            tableDefines = this.getTableDefines(zbQueryFields);
            floatOrderFields = this.getOrderFields(tableDefines);
            dataTables = this.getDataTables(tableDefines);
            VariableManager variableManager = executorContext.getVariableManager();
            dataSchemeKey = dataTables.get(0).getDataSchemeKey();
            variableManager.add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataSchemeKey));
            context.getCaches().put("ADJUST", dataSchemeKey);
            ArrayList<QueryField> hiddenQueryField = new ArrayList<QueryField>();
            ArrayList<String> hiddenId = new ArrayList<String>();
            this.dFDimensionParser.hideDimensionsProcess(context, dataSchemeKey, hiddenQueryField, hiddenId);
        }
        catch (Exception e2) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6d6e\u52a8\u884cid\u548c\u6392\u5e8f\u6307\u6807\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e2.getMessage());
            logger.error(logInfo.toString(), e2);
            throw new DataFillRuntimeException(logInfo.toString());
        }
        ReportFmlExecEnvironment execEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, null);
        execEnvironment.setDataScehmeKey(dataSchemeKey);
        execEnvironment.getVariableManager().add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataSchemeKey));
        executorContext.setEnv((IFmlExecEnvironment)execEnvironment);
        ArrayList<FieldDefine> allFields = new ArrayList<FieldDefine>();
        allFields.addAll(floatOrderFields);
        for (QueryField queryField : zbQueryFields) {
            FieldDefine fieldDefine = null;
            try {
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryField.getId());
                if (null == fieldDefine) {
                    throw new DataFillRuntimeException(NrDataFillI18nUtil.parseMsg("{{nr.dataquery.Field}}") + queryField.getTitle() + ";" + NrDataFillI18nUtil.parseMsg("{{nr.dataFill.zbLostError}}"));
                }
            }
            catch (Exception e3) {
                StringBuilder logInfo = new StringBuilder();
                logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(queryField.getId()).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e3.getMessage());
                logger.error(logInfo.toString(), e3);
                throw new DataFillRuntimeException(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.getZbError}}") + "[" + queryField.getTitle() + "]" + NrDataFillI18nUtil.parseMsg("{{nr.dataFill.errorReason}}") + e3.getMessage());
            }
            allFields.add(fieldDefine);
        }
        HashMap<String, Integer> addColumnMap = new HashMap<String, Integer>();
        for (FieldDefine fieldDefine : allFields) {
            int addColumn = dataQuery.addColumn(fieldDefine);
            addColumnMap.put(fieldDefine.getKey(), addColumn);
        }
        FloatTypeDTO floatTypeDTO = this.getFloatType(dataTables, sqlDimensionValueSet, this.dFDimensionParser.getDimensionNameByField(periodField));
        this.addFilter(simplifyQueryFieldsMap, filterConditions, dataQuery, addColumnMap, context);
        this.addOrder(context, queryFieldsMap, dataQuery, floatOrderFields, floatTypeDTO);
        PageInfo pageInfo = queryInfo.getPagerInfo();
        if (null != pageInfo) {
            dataQuery.setPagingInfo(pageInfo.getLimit(), pageInfo.getOffset());
        }
        IReadonlyTable readonlyTable = null;
        try {
            readonlyTable = dataQuery.executeReader(executorContext);
        }
        catch (Exception e4) {
            DimensionValueSet masterKeys = dataQuery.getMasterKeys();
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e4.getMessage(), e4);
            throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString());
        }
        for (int i = 0; i < readonlyTable.getCount(); ++i) {
            IDataRow dataRow = readonlyTable.getItem(i);
            DimensionValueSet sqlRowKeys = dataRow.getRowKeys();
            DimensionValueSet parseRowKeys = this.parseRowKeys(floatTypeDTO, sqlRowKeys);
            DimensionValueSet rowKeys = this.dFDimensionParser.sqlDimensionToEntityId(context, parseRowKeys);
            ArrayList<AbstractData> values = new ArrayList<AbstractData>();
            for (int j = 0; j < allFields.size(); ++j) {
                AbstractData npData = dataRow.getValue(j);
                values.add(npData);
            }
            resultMap.put(rowKeys, values);
        }
        ArrayList<QueryField> addZbs = new ArrayList<QueryField>();
        DataFillQueryResult dataFillQueryResult = new DataFillQueryResult();
        dataFillQueryResult.setDatas(resultMap);
        if (null != pageInfo) {
            dataFillQueryResult.setPageInfo(pageInfo);
            int totalCount = readonlyTable.getTotalCount();
            dataFillQueryResult.setTotalCount(totalCount);
        }
        QueryField orderField = new QueryField();
        orderField.setTitle(NrDataFillI18nUtil.buildCode("nr.dataFill.floatRowSort"));
        orderField.setFieldType(FieldType.FIELD);
        orderField.setFullCode("FLOATORDER");
        orderField.setCode("FLOATORDER");
        orderField.setId("FLOATORDER");
        addZbs.add(orderField);
        dataFillQueryResult.setAddZbs(addZbs);
        return dataFillQueryResult;
    }

    protected void addOrder(DataFillContext context, Map<String, QueryField> queryFieldsMap, IDataQuery dataQuery, List<FieldDefine> floatOrderFields, FloatTypeDTO floatType) {
        List<OrderField> orderFields = context.getModel().getOrderFields();
        if (null != orderFields && !orderFields.isEmpty()) {
            for (OrderField orderField : orderFields) {
                String fullCode = orderField.getFullCode();
                QueryField queryField = queryFieldsMap.get(fullCode);
                if (queryField.getFieldType() == FieldType.ZB || queryField.getFieldType() == FieldType.FIELD || queryField.getFieldType() == FieldType.TABLEDIMENSION) {
                    FieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryField.getId());
                    }
                    catch (Exception e) {
                        StringBuilder logInfo = new StringBuilder();
                        logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(queryField.getId()).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
                        logger.error(logInfo.toString(), e);
                        throw new DataFillRuntimeException(logInfo.toString());
                    }
                    dataQuery.addOrderByItem(fieldDefine, orderField.getMode() == OrderMode.DESC);
                    continue;
                }
                String dimensionName = this.dFDimensionParser.getDimensionNameByField(queryField);
                FieldDefine fieldDefine = floatType.getDimenNameFieldMap().get(dimensionName);
                if (queryField.getFieldType() == FieldType.MASTER) {
                    dataQuery.addSpecifiedOrderByItem(fieldDefine);
                    continue;
                }
                dataQuery.addOrderByItem(fieldDefine, orderField.getMode() == OrderMode.DESC);
            }
            for (FieldDefine fieldDefine : floatOrderFields) {
                dataQuery.addOrderByItem(fieldDefine, false);
            }
        } else {
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            String dimensionNameP = this.dFDimensionParser.getDimensionNameByField(fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0));
            FieldDefine fieldDefineP = floatType.getDimenNameFieldMap().get(dimensionNameP);
            dataQuery.addOrderByItem(fieldDefineP, true);
            String dimensionNameM = this.dFDimensionParser.getDimensionNameByField(fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0));
            FieldDefine fieldDefineM = floatType.getDimenNameFieldMap().get(dimensionNameM);
            dataQuery.addSpecifiedOrderByItem(fieldDefineM);
            for (FieldDefine fieldDefine : floatOrderFields) {
                dataQuery.addOrderByItem(fieldDefine, false);
            }
        }
    }

    private void addFilter(Map<String, QueryField> simplifyQueryFieldsMap, List<DFDimensionValue> filterConditions, IDataQuery dataQuery, Map<String, Integer> addColumnMap, DataFillContext context) {
        if (!filterConditions.isEmpty()) {
            StringBuilder filterBuilder = new StringBuilder();
            for (int i = 0; i < filterConditions.size(); ++i) {
                DFDimensionValue dfDimensionValue = filterConditions.get(i);
                QueryField queryField = simplifyQueryFieldsMap.get(dfDimensionValue.getName());
                FieldDefine fieldDefine = null;
                try {
                    fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryField.getId());
                }
                catch (Exception e) {
                    StringBuilder logInfo = new StringBuilder();
                    logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(queryField.getId()).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
                    logger.error(logInfo.toString(), e);
                    throw new DataFillRuntimeException(logInfo.toString());
                }
                List deployInfoByDataFieldKeys = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                String tableName = ((DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0)).getTableName();
                String values = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                if (filterBuilder.length() != 0) {
                    filterBuilder.append(" AND ");
                }
                DataFieldType dataType = queryField.getDataType();
                if (!values.contains(";")) {
                    filterBuilder.append(" ").append(tableName).append("[").append(fieldDefine.getCode()).append("]").append("=");
                    switch (dataType) {
                        case STRING: {
                            filterBuilder.append(" '");
                            filterBuilder.append(values);
                            filterBuilder.append("' ");
                            break;
                        }
                        case DATE: {
                            filterBuilder.insert(filterBuilder.lastIndexOf(" ") + 1, "FormatDate(");
                            filterBuilder.insert(filterBuilder.lastIndexOf("="), ",'yyyymmdd')");
                            filterBuilder.append(" '");
                            filterBuilder.append(values);
                            filterBuilder.append("' ");
                            break;
                        }
                        case INTEGER: {
                            filterBuilder.append(" ");
                            filterBuilder.append(values);
                            filterBuilder.append(" ");
                            break;
                        }
                        case BIGDECIMAL: {
                            filterBuilder.append(" ");
                            filterBuilder.append(values);
                            filterBuilder.append(" ");
                            break;
                        }
                    }
                    continue;
                }
                Integer index = addColumnMap.get(fieldDefine.getKey());
                if (null == index) {
                    index = dataQuery.addColumn(fieldDefine);
                    addColumnMap.put(fieldDefine.getKey(), index);
                }
                ArrayList<Object> valueList = new ArrayList<Object>();
                List<String> list = Arrays.asList(values.split(";"));
                switch (dataType) {
                    case STRING: {
                        valueList.addAll(list);
                        break;
                    }
                    case DATE: {
                        for (String value : list) {
                            try {
                                Date stringToDate = DateUtils.stringToDate(value);
                                valueList.add(stringToDate);
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                        }
                        break;
                    }
                    case INTEGER: {
                        valueList.addAll(list);
                        break;
                    }
                    case BIGDECIMAL: {
                        valueList.addAll(list);
                        break;
                    }
                }
                if (valueList.isEmpty()) continue;
                dataQuery.setColumnFilterValueList(index.intValue(), valueList);
            }
            if (filterBuilder.length() != 0) {
                dataQuery.setRowFilter(filterBuilder.toString());
            }
        }
    }

    @Override
    public DataFillResult save(DataFillDataSaveInfo saveInfo) {
        DimensionValueSet sqlRowDimensionValueSet;
        Object dataSaveErrorDataInfo;
        IDataTable iDataTable;
        String dataSchemeKey;
        DataFillResult dataFillResult = new DataFillResult();
        ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
        dataFillResult.setErrors(errors);
        dataFillResult.setSuccess(true);
        dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveSuccess"));
        DataFillContext context = saveInfo.getContext();
        List<DataFillDataSaveRow> adds = saveInfo.getAdds();
        HashMap<Integer, Integer> addsIndex2RowNum = new HashMap<Integer, Integer>();
        List<DataFillDataSaveRow> modifys = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deletes = saveInfo.getDeletes();
        if (!(null != modifys && !modifys.isEmpty() || null != deletes && !deletes.isEmpty() || null != adds && !adds.isEmpty())) {
            return dataFillResult;
        }
        List<DFDimensionValue> dimensionValues = this.mergeDimensionValues(saveInfo);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.setStatic(false);
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValues);
        dataQuery.setMasterKeys(sqlDimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
        List<QueryField> zbQueryFields = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
        List<FieldDefine> floatOrderFields = null;
        List<TableDefine> tableDefines = null;
        List<DataTable> dataTables = null;
        Map<FieldType, List<QueryField>> fieldTypeListMap = this.dFDimensionParser.getFieldTypeQueryFields(context);
        String dwSqlKey = this.dFDimensionParser.getSqlDimensionName(fieldTypeListMap.get((Object)FieldType.MASTER).get(0));
        String periodSqlKey = this.dFDimensionParser.getSqlDimensionName(fieldTypeListMap.get((Object)FieldType.PERIOD).get(0));
        QueryField master = fieldTypeListMap.get((Object)FieldType.MASTER).get(0);
        QueryField period = fieldTypeListMap.get((Object)FieldType.PERIOD).get(0);
        QueryField sceneField = null;
        if (fieldTypeListMap.containsKey((Object)FieldType.SCENE) && fieldTypeListMap.get((Object)FieldType.SCENE) != null) {
            sceneField = fieldTypeListMap.get((Object)FieldType.SCENE).get(0);
        }
        ArrayList<QueryField> hiddenQueryField = new ArrayList<QueryField>();
        ArrayList<String> hiddenId = new ArrayList<String>();
        try {
            executorContext.setOrgEntityId(this.entityMetaService.getEntityIdByCode(master.getSimplifyFullCode()));
            tableDefines = this.getTableDefines(zbQueryFields);
            floatOrderFields = this.getOrderFields(tableDefines);
            dataTables = this.getDataTables(tableDefines);
            VariableManager variableManager = executorContext.getVariableManager();
            variableManager.add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataTables.get(0).getDataSchemeKey()));
            dataSchemeKey = dataTables.get(0).getDataSchemeKey();
            this.dFDimensionParser.hideDimensionsProcess(context, dataSchemeKey, hiddenQueryField, hiddenId);
        }
        catch (Exception e2) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6d6e\u52a8\u884cid\u548c\u6392\u5e8f\u6307\u6807\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e2.getMessage());
            logger.error(logInfo.toString(), e2);
            throw new DataFillRuntimeException(logInfo.toString());
        }
        ArrayList<FieldDefine> allFields = new ArrayList<FieldDefine>();
        allFields.addAll(floatOrderFields);
        HashMap<String, QueryField> zbIdQueryField = new HashMap<String, QueryField>();
        for (QueryField queryField : zbQueryFields) {
            FieldDefine fieldDefine = null;
            try {
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryField.getId());
                zbIdQueryField.put(queryField.getId(), queryField);
            }
            catch (Exception e3) {
                StringBuilder logInfo = new StringBuilder();
                logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(queryField.getId()).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e3.getMessage());
                logger.error(logInfo.toString(), e3);
                throw new DataFillRuntimeException(logInfo.toString());
            }
            allFields.add(fieldDefine);
        }
        HashMap<String, FieldDefine> addColumnMap = new HashMap<String, FieldDefine>();
        for (FieldDefine fieldDefine : allFields) {
            dataQuery.addColumn(fieldDefine);
            addColumnMap.put(fieldDefine.getKey(), fieldDefine);
        }
        Object var30_34 = null;
        try {
            iDataTable = dataQuery.executeQuery(executorContext);
        }
        catch (Exception e4) {
            String errorStr = "\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + sqlDimensionValueSet + ";\u51fa\u9519\u539f\u56e0\uff1a" + e4.getMessage();
            logger.error(errorStr, e4);
            throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + sqlDimensionValueSet.toString());
        }
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        FloatTypeDTO floatTypeDTO = this.getFloatType(dataTables, sqlDimensionValueSet, this.dFDimensionParser.getDimensionNameByField(periodField));
        FieldReadWriteAccessResultInfo accessResult = this.getAccess(saveInfo);
        HashSet<DimensionValueSet> sqlRowDimensionValueSetDistinctSet = new HashSet<DimensionValueSet>();
        if (null != modifys && modifys.size() > 0) {
            int excelRowNum = 1;
            for (DataFillDataSaveRow dataFillDataSaveRow : modifys) {
                Object dataFillSaveErrorDataInfo;
                ++excelRowNum;
                List<String> zbs = dataFillDataSaveRow.getZbs();
                List<String> values = dataFillDataSaveRow.getValues();
                dataSaveErrorDataInfo = new ArrayList<DataFillSaveErrorDataInfo>();
                sqlRowDimensionValueSet = this.getModifysRowDimensionValueSet(context, dataFillDataSaveRow, floatTypeDTO, (List<DataFillSaveErrorDataInfo>)dataSaveErrorDataInfo, (Map<String, FieldDefine>)addColumnMap, (Map<String, QueryField>)zbIdQueryField, master, period, (List<QueryField>)hiddenQueryField, (List<String>)hiddenId);
                String dwCode = (String)sqlRowDimensionValueSet.getValue(dwSqlKey);
                String periodValue = (String)sqlRowDimensionValueSet.getValue(periodSqlKey);
                if (sceneField != null) {
                    List<String> sceneValues = this.dFDimensionParser.searchReferRelation(dwCode, master.getId(), sceneField, sqlRowDimensionValueSet, master, period, dataSchemeKey);
                    String sceneValue = (String)sqlRowDimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(sceneField));
                    if (sceneValues != null && !Arrays.asList(sceneValues.get(0).split(";")).contains(sceneValue)) {
                        Map<String, DataFillDimensionTitle> dimensionOtherTitleMap = this.dFDimensionParser.getDimensionTitle(sceneField, Collections.singletonList(sceneValue), null, context.getModel());
                        Map<String, DataFillDimensionTitle> dimensionTitleMap = this.dFDimensionParser.getDwDimensionTitle(master, Collections.singletonList(dwCode), Collections.singletonList(periodValue), context.getModel());
                        DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo2 = new DataFillSaveErrorDataInfo();
                        dataFillSaveErrorDataInfo2.setErrorLocY(excelRowNum);
                        dataFillSaveErrorDataInfo2.setErrorLocX(zbs.indexOf(floatTypeDTO.getFieldKeys().get(0)) + 1 + 2);
                        ResultErrorInfo errorInfo = new ResultErrorInfo();
                        errorInfo.setErrorCode(ErrorCode.DATAERROR);
                        errorInfo.setErrorInfo(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.scene}}") + dimensionOtherTitleMap.get(sceneValue).getTitle() + NrDataFillI18nUtil.parseMsg("{{nr.dataFill.andOrg}}") + dimensionTitleMap.get(periodValue + ";" + dwCode).getTitle() + NrDataFillI18nUtil.parseMsg("{{nr.dataFill.match}}"));
                        dataFillSaveErrorDataInfo2.setZb(dataFillDataSaveRow.getZbs().get(0));
                        dataFillSaveErrorDataInfo2.setValue((Serializable)((Object)dataFillDataSaveRow.getValues().get(0)));
                        dataFillSaveErrorDataInfo2.setDataError(errorInfo);
                        errors.add(dataFillSaveErrorDataInfo2);
                    }
                }
                if (floatTypeDTO.getFloatType() == 2) {
                    if (!sqlRowDimensionValueSetDistinctSet.contains(sqlRowDimensionValueSet)) {
                        sqlRowDimensionValueSetDistinctSet.add(sqlRowDimensionValueSet);
                    } else {
                        dataFillResult.setSuccess(false);
                        dataFillSaveErrorDataInfo = new DataFillSaveErrorDataInfo();
                        ((DataFillSaveErrorDataInfo)dataFillSaveErrorDataInfo).setErrorLocY(excelRowNum);
                        ((DataFillSaveErrorDataInfo)dataFillSaveErrorDataInfo).setErrorLocX(zbs.indexOf(floatTypeDTO.getFieldKeys().get(0)) + 1 + 2);
                        ResultErrorInfo errorInfo = new ResultErrorInfo();
                        errorInfo.setErrorCode(ErrorCode.DATAERROR);
                        errorInfo.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataFill.floatRowBusiPK") + NrDataFillI18nUtil.buildCode("nr.dataFill.repeat"));
                        ((DataFillSaveErrorDataInfo)dataFillSaveErrorDataInfo).setDataError(errorInfo);
                        errors.add((DataFillSaveErrorDataInfo)dataFillSaveErrorDataInfo);
                        return dataFillResult;
                    }
                }
                if (!dataSaveErrorDataInfo.isEmpty()) {
                    dataFillSaveErrorDataInfo = dataSaveErrorDataInfo.iterator();
                    while (dataFillSaveErrorDataInfo.hasNext()) {
                        DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo3 = (DataFillSaveErrorDataInfo)dataFillSaveErrorDataInfo.next();
                        dataFillSaveErrorDataInfo3.setErrorLocY(excelRowNum);
                        errors.add(dataFillSaveErrorDataInfo3);
                    }
                    continue;
                }
                IDataRow modifiedRow = null;
                try {
                    modifiedRow = iDataTable.findRow(sqlRowDimensionValueSet);
                }
                catch (Exception e5) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e5.getMessage(), e5);
                    throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u67e5\u8be2\u4fee\u6539\u884c\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + sqlRowDimensionValueSet.toString());
                }
                if (null == modifiedRow) {
                    adds.add(dataFillDataSaveRow);
                    addsIndex2RowNum.put(adds.size() - 1, excelRowNum);
                    continue;
                }
                if (this.isValidDataRow(dataFillDataSaveRow, modifiedRow)) {
                    int idIndex = zbs.indexOf("ID");
                    if (idIndex <= -1 || !StringUtils.hasLength(values.get(idIndex))) continue;
                    DataFillDataDeleteRow deleteRow = new DataFillDataDeleteRow();
                    deleteRow.setDimensionValues(dimensionValues);
                    ArrayList<String> ids = new ArrayList<String>();
                    ids.add(values.get(idIndex));
                    deleteRow.setValues(ids);
                    if (null == deletes) {
                        deletes = new ArrayList<DataFillDataDeleteRow>();
                    }
                    deletes.add(deleteRow);
                    continue;
                }
                boolean haveAccess = false;
                for (int i = 0; i < zbs.size(); ++i) {
                    String zbId = zbs.get(i);
                    String zbValue = values.get(i);
                    if ("ID".equals(zbId)) continue;
                    if (addColumnMap.containsKey(zbId)) {
                        DimensionValueSet tableValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dataFillDataSaveRow.getDimensionValues());
                        if (!hiddenQueryField.isEmpty()) {
                            String dwKey = this.dFDimensionParser.getSqlDimensionName(master);
                            String dwValue = (String)tableValueSet.getValue(dwKey);
                            this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, tableValueSet, master, period, hiddenQueryField, hiddenId, true);
                        }
                        QueryField queryField = (QueryField)zbIdQueryField.get(zbId);
                        DataFieldWriteAccessResultInfo access = accessResult.getAccess(tableValueSet, zbId, context);
                        if (haveAccess || access.haveAccess()) {
                            haveAccess = true;
                            FieldDefine zb = (FieldDefine)addColumnMap.get(zbId);
                            DataFillSaveErrorDataInfo saveErrorDataInfo = this.toSqlValue(zbValue, queryField, context, dataFillDataSaveRow.getDimensionValues(), null, zb, floatTypeDTO);
                            if (null != saveErrorDataInfo.getDataError()) {
                                saveErrorDataInfo.setErrorLocX(i + 1 + 2);
                                saveErrorDataInfo.setErrorLocY(excelRowNum);
                                errors.add(saveErrorDataInfo);
                                continue;
                            }
                            modifiedRow.setValue(zb, (Object)saveErrorDataInfo.getValue());
                            continue;
                        }
                        DataFillSaveErrorDataInfo saveErrorDataInfo = accessResult.getMessage(tableValueSet, zbId, queryField, context, dataFillDataSaveRow.getDimensionValues(), access);
                        saveErrorDataInfo.setErrorLocX(i + 1 + 2);
                        saveErrorDataInfo.setErrorLocY(excelRowNum);
                        errors.add(saveErrorDataInfo);
                        continue;
                    }
                    if (!"FLOATORDER".equals(zbId)) continue;
                    modifiedRow.setValue((FieldDefine)addColumnMap.get(floatOrderFields.get(0).getKey()), (Object)zbValue);
                }
            }
        }
        if (null != deletes && deletes.size() > 0) {
            for (DataFillDataDeleteRow dataFillDataDeleteRow : deletes) {
                DimensionValueSet tableValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dataFillDataDeleteRow.getDimensionValues());
                if (!hiddenQueryField.isEmpty()) {
                    String dwKey = this.dFDimensionParser.getSqlDimensionName(master);
                    String dwValue = (String)tableValueSet.getValue(dwKey);
                    this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, tableValueSet, master, period, hiddenQueryField, hiddenId, true);
                }
                List<DimensionValueSet> sqlRowDimensionValueSets = this.getDeleteRowDimensionValueSet(context, dataFillDataDeleteRow, floatTypeDTO);
                String zbId = ((FieldDefine)allFields.get(allFields.size() - 1)).getKey();
                dataSaveErrorDataInfo = sqlRowDimensionValueSets.iterator();
                while (dataSaveErrorDataInfo.hasNext()) {
                    sqlRowDimensionValueSet = (DimensionValueSet)dataSaveErrorDataInfo.next();
                    try {
                        DataFieldWriteAccessResultInfo access = accessResult.getAccess(tableValueSet, zbId, context);
                        if (access.haveAccess()) {
                            iDataTable.deleteRow(sqlRowDimensionValueSet);
                            continue;
                        }
                        DataFillSaveErrorDataInfo saveErrorDataInfo = accessResult.getMessage(tableValueSet, zbId, null, context, null, access);
                        errors.add(saveErrorDataInfo);
                    }
                    catch (IncorrectQueryException e6) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e6.getMessage(), e6);
                        throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u5220\u9664\u884c\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + sqlRowDimensionValueSet.toString());
                    }
                }
            }
        }
        if (null != adds && adds.size() > 0) {
            HashMap<DimensionValueSet, Double> floatOrderMap = new HashMap<DimensionValueSet, Double>();
            List<String> fieldKeys = floatTypeDTO.getFieldKeys();
            int addsIndex = -1;
            for (DataFillDataSaveRow dataFillDataSaveRow : adds) {
                ++addsIndex;
                List<String> zbs = dataFillDataSaveRow.getZbs();
                List<String> values = dataFillDataSaveRow.getValues();
                if (this.isValidDataRow(dataFillDataSaveRow, null)) continue;
                if (!zbs.contains("FLOATORDER")) {
                    zbs.add("FLOATORDER");
                    values.add("");
                }
                ArrayList<DataFillSaveErrorDataInfo> saveErrorDataInfo = new ArrayList<DataFillSaveErrorDataInfo>();
                DimensionValueSet sqlRowDimensionValueSet2 = this.getAddRowDimensionValueSet(context, dataFillDataSaveRow, floatTypeDTO, saveErrorDataInfo, addColumnMap, zbIdQueryField, master, period, hiddenQueryField, hiddenId);
                if (!saveErrorDataInfo.isEmpty()) {
                    errors.addAll(saveErrorDataInfo);
                    continue;
                }
                IDataRow addRow = null;
                try {
                    addRow = iDataTable.appendRow(sqlRowDimensionValueSet2);
                }
                catch (Exception e7) {
                    dataFillResult.setSuccess(false);
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e7.getMessage(), e7);
                    if (floatTypeDTO.getFloatType() == 2 && e7 instanceof DuplicateRowKeysException) {
                        ResultErrorInfo dataError = new ResultErrorInfo();
                        dataError.setErrorCode(ErrorCode.DATAERROR);
                        List<String> zbBalues = dataFillDataSaveRow.getValues();
                        StringBuilder errorBuilder = new StringBuilder();
                        errorBuilder.append(NrDataFillI18nUtil.buildCode("nr.dataFill.floatRowBusiPK") + " ");
                        DataFillSaveErrorDataInfo errorDataInfo = new DataFillSaveErrorDataInfo();
                        for (int i = 0; i < fieldKeys.size(); ++i) {
                            String fieldKey = fieldKeys.get(i);
                            int indexOf = zbs.indexOf(fieldKey);
                            if (indexOf < 0) continue;
                            String dimensionValue = (String)zbBalues.get(indexOf);
                            FieldDefine fieldDefine = null;
                            try {
                                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                            }
                            catch (Exception e1) {
                                StringBuilder logInfo = new StringBuilder();
                                logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e1.getMessage());
                                logger.error(logInfo.toString(), e1);
                                throw new DataFillRuntimeException(logInfo.toString());
                            }
                            if (null == errorDataInfo.getDataError()) {
                                errorBuilder.append(fieldDefine.getTitle());
                            } else {
                                errorBuilder.append("\u3001").append(fieldDefine.getTitle());
                            }
                            errorBuilder.append("[").append(dimensionValue).append("]");
                            errorDataInfo.setDataError(dataError);
                            errorDataInfo.setErrorLocX(indexOf + 1 + 2);
                            errorDataInfo.setErrorLocY((Integer)addsIndex2RowNum.get(addsIndex));
                            errors.add(errorDataInfo);
                        }
                        errorBuilder.append(" " + NrDataFillI18nUtil.buildCode("nr.dataFill.repeat") + "\uff01");
                        dataError.setErrorInfo(errorBuilder.toString());
                        return dataFillResult;
                    }
                    throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u65b0\u589e\u884c\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + sqlRowDimensionValueSet2.toString());
                }
                boolean finshAddFloat = false;
                boolean haveAccess = false;
                for (QueryField zbQueryField : zbQueryFields) {
                    int i;
                    String zbId = zbQueryField.getId();
                    String zbValue = "";
                    for (i = 0; i < zbs.size(); ++i) {
                        if (!zbs.get(i).equals(zbId)) continue;
                        zbValue = values.get(i);
                        break;
                    }
                    if ("ID".equals(zbId)) continue;
                    if (addColumnMap.containsKey(zbId)) {
                        QueryField queryField = (QueryField)zbIdQueryField.get(zbId);
                        DataFillSaveErrorDataInfo errorDataInfo = new DataFillSaveErrorDataInfo();
                        DimensionValueSet tableValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dataFillDataSaveRow.getDimensionValues());
                        if (hiddenQueryField != null && !hiddenQueryField.isEmpty()) {
                            String dwKey = this.dFDimensionParser.getSqlDimensionName(master);
                            String dwValue = (String)tableValueSet.getValue(dwKey);
                            this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, tableValueSet, master, period, hiddenQueryField, hiddenId, true);
                        }
                        DataFieldWriteAccessResultInfo access = accessResult.getAccess(tableValueSet, zbId, context);
                        if (haveAccess || access.haveAccess()) {
                            haveAccess = true;
                            FieldDefine zb = (FieldDefine)addColumnMap.get(zbId);
                            errorDataInfo = this.toSqlValue(zbValue, queryField, context, dataFillDataSaveRow.getDimensionValues(), null, zb, floatTypeDTO);
                            if (null != errorDataInfo.getDataError()) {
                                errorDataInfo.setErrorLocX(i + 1 + 2);
                                errorDataInfo.setErrorLocY((Integer)addsIndex2RowNum.get(addsIndex));
                                errors.add(errorDataInfo);
                                continue;
                            }
                            addRow.setValue(zb, (Object)errorDataInfo.getValue());
                            continue;
                        }
                        errorDataInfo = accessResult.getMessage(tableValueSet, zbId, queryField, context, dataFillDataSaveRow.getDimensionValues(), access);
                        errorDataInfo.setErrorLocX(i + 1 + 2);
                        errorDataInfo.setErrorLocY((Integer)addsIndex2RowNum.get(addsIndex));
                        errors.add(errorDataInfo);
                        continue;
                    }
                    if (!"FLOATORDER".equals(zbId)) continue;
                    finshAddFloat = true;
                    if (!StringUtils.hasLength(zbValue)) {
                        DimensionValueSet rowDimensionValue = new DimensionValueSet();
                        DimensionSet dimensionSet = sqlDimensionValueSet.getDimensionSet();
                        for (int j = 0; j < dimensionSet.size(); ++j) {
                            rowDimensionValue.setValue(dimensionSet.get(j), sqlRowDimensionValueSet2.getValue(dimensionSet.get(j)));
                        }
                        Double floatOrder = (Double)floatOrderMap.get(rowDimensionValue);
                        if (null == floatOrder) {
                            floatOrder = this.getMaxFloatOrder(rowDimensionValue, floatOrderFields, allFields);
                        }
                        floatOrder = floatOrder + 1000.0;
                        addRow.setValue((FieldDefine)addColumnMap.get(floatOrderFields.get(0).getKey()), (Object)floatOrder);
                        floatOrderMap.put(rowDimensionValue, floatOrder);
                        continue;
                    }
                    addRow.setValue((FieldDefine)addColumnMap.get(floatOrderFields.get(0).getKey()), (Object)zbValue);
                }
                if (finshAddFloat) continue;
                DimensionValueSet rowDimensionValue = new DimensionValueSet();
                DimensionSet dimensionSet = sqlDimensionValueSet.getDimensionSet();
                for (int j = 0; j < dimensionSet.size(); ++j) {
                    rowDimensionValue.setValue(dimensionSet.get(j), sqlRowDimensionValueSet2.getValue(dimensionSet.get(j)));
                }
                Double floatOrder = (Double)floatOrderMap.get(rowDimensionValue);
                if (null == floatOrder) {
                    floatOrder = this.getMaxFloatOrder(rowDimensionValue, floatOrderFields, allFields);
                }
                floatOrder = floatOrder + 1000.0;
                addRow.setValue((FieldDefine)addColumnMap.get(floatOrderFields.get(0).getKey()), (Object)floatOrder);
                floatOrderMap.put(rowDimensionValue, floatOrder);
            }
        }
        if (floatTypeDTO.getFloatType() == 2) {
            iDataTable.needCheckDuplicateKeys(true);
        }
        try {
            if (errors.isEmpty()) {
                iDataTable.commitChanges(true);
            } else {
                dataFillResult.setSuccess(false);
                dataFillResult.setMessage("\u4fdd\u5b58\u5931\u8d25\uff01");
            }
        }
        catch (Exception e8) {
            DimensionValueSet masterKeys = iDataTable.getMasterKeys();
            if (e8 instanceof DuplicateRowKeysException) {
                List<QueryField> allQueryField = this.dFDimensionParser.getAllQueryFields(context);
                ResultErrorInfo dataError = new ResultErrorInfo();
                List dimensionValueSet = ((DuplicateRowKeysException)e8).getDuplicateKeys();
                dataError.setErrorCode(ErrorCode.DATAERROR);
                StringBuilder errorBuilder = new StringBuilder();
                ArrayList dimensionQueryField = new ArrayList();
                HashMap<String, String> simpleCode2Value = new HashMap<String, String>();
                dimensionQueryField.addAll(fieldTypeQueryFields.get((Object)FieldType.PERIOD));
                dimensionQueryField.addAll(fieldTypeQueryFields.get((Object)FieldType.MASTER));
                dimensionQueryField.addAll(fieldTypeQueryFields.get((Object)FieldType.SCENE) == null ? new ArrayList() : (Collection)fieldTypeQueryFields.get((Object)FieldType.SCENE));
                DimensionValueSet valueSet = (DimensionValueSet)dimensionValueSet.get(0);
                for (int j = 0; j < valueSet.size(); ++j) {
                    String dimensionName = valueSet.getName(j);
                    for (int k = 0; k < dimensionQueryField.size(); ++k) {
                        QueryField queryField = (QueryField)dimensionQueryField.get(k);
                        String sqlDimensionName = this.dFDimensionParser.getSqlDimensionName(queryField);
                        if (!StringUtils.hasLength(sqlDimensionName) || !sqlDimensionName.equals(dimensionName)) continue;
                        simpleCode2Value.put(queryField.getSimplifyFullCode(), valueSet.getValue(j).toString());
                    }
                }
                errorBuilder.append(NrDataFillI18nUtil.buildCode("nr.dataFill.floatRowBusiPK")).append("[");
                List<String> fieldKeys = floatTypeDTO.getFieldKeys();
                for (int i = 0; i < fieldKeys.size(); ++i) {
                    String fieldKey = fieldKeys.get(i);
                    FieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                    }
                    catch (Exception e1) {
                        StringBuilder logInfo = new StringBuilder();
                        logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e1.getMessage());
                        logger.error(logInfo.toString(), e1);
                        throw new DataFillRuntimeException(logInfo.toString());
                    }
                    for (QueryField queryField : allQueryField) {
                        if (!queryField.getId().equals(fieldDefine.getKey())) continue;
                        if (StringUtils.hasLength(queryField.getAlias())) {
                            errorBuilder.append(" ").append(queryField.getAlias());
                            break;
                        }
                        errorBuilder.append(" ").append(queryField.getTitle());
                        break;
                    }
                    if (i >= fieldKeys.size() - 1) continue;
                    errorBuilder.append(", ");
                }
                List<DFDimensionValue> rowDimensionValue = null;
                for (DataFillDataSaveRow row : modifys) {
                    boolean flag = true;
                    for (DFDimensionValue dimensionValue : row.getDimensionValues()) {
                        if (dimensionValue.getName().equals("ID") || ((String)simpleCode2Value.get(dimensionValue.getName())).equals(this.dfDimensionValueGetService.getValues(dimensionValue, context.getModel()))) continue;
                        flag = false;
                        break;
                    }
                    block37: for (int j = 0; j < row.getZbs().size() && flag; ++j) {
                        for (int i = 0; i < fieldKeys.size(); ++i) {
                            if (fieldKeys.get(i).equals(row.getZbs().get(j))) {
                                String dimensionName = this.dFDimensionParser.getDimensionName(fieldKeys.get(i));
                                if (row.getValues().get(j).equals(valueSet.getValue(dimensionName).toString())) continue block37;
                                flag = false;
                                continue block37;
                            }
                            if (i < fieldKeys.size() - 1) continue;
                            flag = false;
                            continue block37;
                        }
                    }
                    if (!flag) continue;
                    rowDimensionValue = row.getDimensionValues();
                    break;
                }
                errorBuilder.append(" ]").append(NrDataFillI18nUtil.buildCode("nr.dataFill.repeat")).append("\uff01");
                dataError.setErrorInfo(errorBuilder.toString());
                for (int i = 0; i < fieldKeys.size(); ++i) {
                    DataFillSaveErrorDataInfo errorDataInfo = new DataFillSaveErrorDataInfo();
                    errorDataInfo.setDimensionValues(rowDimensionValue);
                    errorDataInfo.setDataError(dataError);
                    errorDataInfo.setZb(fieldKeys.get(i));
                    errors.add(errorDataInfo);
                }
                dataFillResult.setSuccess(false);
                dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveError"));
                return dataFillResult;
            }
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u6d6e\u52a8\u6307\u6807\u81ea\u5b9a\u4e49\u4fdd\u5b58\u51fa\u9519\uff1a\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + "; \u51fa\u9519\u539f\u56e0\uff1a" + e8.getMessage(), e8);
            throw new DataFillRuntimeException("\u5f53\u524d\u7ef4\u5ea6\uff1a" + this.dFDimensionParser.getDimensionInfoString(masterKeys, context) + "; \u51fa\u9519\u539f\u56e0\uff1a" + e8.getMessage());
        }
        return dataFillResult;
    }

    private Double getMaxFloatOrder(DimensionValueSet rowDimensionValue, List<FieldDefine> floatOrderFields, List<FieldDefine> allFields) {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.setStatic(false);
        dataQuery.setMasterKeys(new DimensionValueSet(rowDimensionValue));
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        for (FieldDefine fieldDefine : allFields) {
            dataQuery.addColumn(fieldDefine);
        }
        dataQuery.addOrderByItem(floatOrderFields.get(0), true);
        dataQuery.setPagingInfo(1, 0);
        IReadonlyTable readonlyTable = null;
        try {
            readonlyTable = dataQuery.executeReader(executorContext);
        }
        catch (Exception e) {
            DimensionValueSet masterKeys = dataQuery.getMasterKeys();
            logger.error("\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new DataFillRuntimeException(e.getMessage());
        }
        if (readonlyTable.getCount() > 0) {
            IDataRow dataRow = readonlyTable.getItem(0);
            try {
                AbstractData fieldValue = dataRow.getValue(floatOrderFields.get(0));
                return fieldValue.getAsFloat();
            }
            catch (DataTypeException e) {
                logger.error("\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + dataRow.getRowDimensions().toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataFillRuntimeException(e.getMessage());
            }
        }
        return 1000.0;
    }

    private List<DFDimensionValue> mergeDimensionValues(DataFillDataSaveInfo saveInfo) {
        List<DataFillDataSaveRow> adds = saveInfo.getAdds();
        List<DataFillDataSaveRow> modifys = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deletes = saveInfo.getDeletes();
        HashMap<String, Set<String>> entityIdMap = new HashMap<String, Set<String>>();
        if (null != adds && !adds.isEmpty()) {
            this.collectDimension(adds, entityIdMap, saveInfo.getContext().getModel());
        }
        if (null != modifys && !modifys.isEmpty()) {
            this.collectDimension(modifys, entityIdMap, saveInfo.getContext().getModel());
        }
        if (null != deletes && !deletes.isEmpty()) {
            this.collectDimension(entityIdMap, deletes, saveInfo.getContext().getModel());
        }
        ArrayList<DFDimensionValue> dimensionValues = new ArrayList<DFDimensionValue>();
        for (Map.Entry entry : entityIdMap.entrySet()) {
            DFDimensionValue dFDimensionValue = new DFDimensionValue();
            String entityName = (String)entry.getKey();
            StringBuffer stringBuffer = new StringBuffer();
            Set entityValues = (Set)entry.getValue();
            for (String entityValue : entityValues) {
                stringBuffer.append(entityValue).append(";");
            }
            dFDimensionValue.setName(entityName);
            dFDimensionValue.setValues(stringBuffer.substring(0, stringBuffer.length() - 1));
            dimensionValues.add(dFDimensionValue);
        }
        Optional<DFDimensionValue> findAny = dimensionValues.stream().filter(e -> e.getName().equals("ID")).findAny();
        if (findAny.isPresent()) {
            dimensionValues.remove(findAny.get());
        }
        return dimensionValues;
    }

    private void collectDimension(Map<String, Set<String>> entityIdMap, List<DataFillDataDeleteRow> deletes, DataFillModel model) {
        for (DataFillDataDeleteRow dataFillDataSaveRow : deletes) {
            List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
            for (DFDimensionValue dimensionValue : dimensionValues) {
                Set<String> dimSet = entityIdMap.get(dimensionValue.getName());
                if (null == dimSet) {
                    dimSet = new HashSet<String>();
                    entityIdMap.put(dimensionValue.getName(), dimSet);
                }
                dimSet.add(this.dfDimensionValueGetService.getValues(dimensionValue, model));
            }
        }
    }

    private void collectDimension(List<DataFillDataSaveRow> modifys, Map<String, Set<String>> entityIdMap, DataFillModel model) {
        for (DataFillDataSaveRow dataFillDataSaveRow : modifys) {
            List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
            for (DFDimensionValue dimensionValue : dimensionValues) {
                Set<String> dimSet = entityIdMap.get(dimensionValue.getName());
                if (null == dimSet) {
                    dimSet = new HashSet<String>();
                    entityIdMap.put(dimensionValue.getName(), dimSet);
                }
                dimSet.add(this.dfDimensionValueGetService.getValues(dimensionValue, model));
            }
        }
    }

    protected DimensionValueSet getAddRowDimensionValueSet(DataFillContext context, DataFillDataSaveRow dataFillDataSaveRow, FloatTypeDTO floatTypeDTO, List<DataFillSaveErrorDataInfo> saveErrorDataInfo, Map<String, FieldDefine> addColumnMap, Map<String, QueryField> zbIdQueryField, QueryField master, QueryField period, List<QueryField> hiddenFields, List<String> hiddenIds) {
        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(NrDataFillI18nUtil.buildCode("nr.dataFill.floatRowBusiPK") + " ");
        List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
        String dwKey = this.dFDimensionParser.getSqlDimensionName(master);
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValues);
        String dwValue = (String)sqlDimensionValueSet.getValue(dwKey);
        this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, sqlDimensionValueSet, master, period, hiddenFields, hiddenIds, true);
        HashSet<String> hiddenKeys = new HashSet<String>();
        for (QueryField hiddenField : hiddenFields) {
            hiddenKeys.add(hiddenField.getCode());
        }
        List<String> zbs = dataFillDataSaveRow.getZbs();
        List<String> values = dataFillDataSaveRow.getValues();
        int idIndex = zbs.indexOf("ID");
        String idValue = "";
        if (idIndex > -1) {
            idValue = values.get(idIndex);
        }
        String bizKey = floatTypeDTO.getBizKey();
        int floatType = floatTypeDTO.getFloatType();
        DataFillSaveErrorDataInfo errorDataInfo = new DataFillSaveErrorDataInfo();
        if (floatType == 0) {
            if (!StringUtils.hasLength(idValue)) {
                idValue = UUID.randomUUID().toString();
            }
            sqlDimensionValueSet.setValue(bizKey, (Object)idValue);
        } else {
            List<String> tableDimensionNames = floatTypeDTO.getTableDimensionNames();
            List<String> fieldKeys = floatTypeDTO.getFieldKeys();
            for (int i = 0; i < fieldKeys.size(); ++i) {
                String fieldKey = fieldKeys.get(i);
                int indexOf = zbs.indexOf(fieldKey);
                if (indexOf > -1) {
                    String dimensionValue = values.get(indexOf);
                    FieldDefine zb = addColumnMap.get(fieldKey);
                    QueryField queryField = zbIdQueryField.get(fieldKey);
                    DataFillSaveErrorDataInfo saveErrorData = this.toSqlValue(dimensionValue, queryField, context, dataFillDataSaveRow.getDimensionValues(), null, zb, floatTypeDTO);
                    if (null != saveErrorData.getDataError()) {
                        errorDataInfo.setDataError(saveErrorData.getDataError());
                        errorDataInfo.setZb(saveErrorData.getZb());
                        errorDataInfo.setDimensionValues(saveErrorData.getDimensionValues());
                        errorDataInfo.setValue(saveErrorData.getValue());
                        errorDataInfo.setErrorLocX(indexOf + 1 + 2);
                        saveErrorDataInfo.add(errorDataInfo);
                        return null;
                    }
                    sqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)saveErrorData.getValue());
                    continue;
                }
                if (floatType == 1 && bizKey.equals(tableDimensionNames.get(i))) {
                    sqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)UUID.randomUUID().toString());
                    continue;
                }
                FieldDefine fieldDefine = null;
                try {
                    fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                }
                catch (Exception e) {
                    StringBuilder logInfo = new StringBuilder();
                    logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
                    logger.error(logInfo.toString(), e);
                    throw new DataFillRuntimeException(logInfo.toString());
                }
                if (hiddenKeys.contains(fieldDefine.getCode())) continue;
                if (null == errorDataInfo.getDataError()) {
                    errorBuilder.append(fieldDefine.getTitle());
                } else {
                    errorBuilder.append("\u3001").append(fieldDefine.getTitle());
                }
                ResultErrorInfo dataError = new ResultErrorInfo();
                dataError.setErrorCode(ErrorCode.DATAERROR);
                errorDataInfo.setDataError(dataError);
                errorDataInfo.setZb(fieldDefine.getKey());
            }
        }
        errorBuilder.append(" " + NrDataFillI18nUtil.buildCode("nr.dataFill.cantNull"));
        if (null != errorDataInfo.getDataError()) {
            errorDataInfo.getDataError().setErrorInfo(errorBuilder.toString());
            errorDataInfo.setDimensionValues(dimensionValues);
            saveErrorDataInfo.add(errorDataInfo);
        }
        return sqlDimensionValueSet;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DimensionValueSet getModifysRowDimensionValueSet(DataFillContext context, DataFillDataSaveRow dataFillDataSaveRow, FloatTypeDTO floatTypeDTO, List<DataFillSaveErrorDataInfo> saveErrorDataInfo, Map<String, FieldDefine> addColumnMap, Map<String, QueryField> zbIdQueryField, QueryField master, QueryField period, List<QueryField> hiddenFields, List<String> hiddenIds) {
        List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValues);
        List<String> zbs = dataFillDataSaveRow.getZbs();
        List<String> values = dataFillDataSaveRow.getValues();
        int idIndex = zbs.indexOf("ID");
        String idValue = "";
        if (idIndex < 0) {
            Optional<DFDimensionValue> findAny = dimensionValues.stream().filter(e -> e.getName().equals("ID")).findAny();
            if (!findAny.isPresent()) return this.getAddRowDimensionValueSet(context, dataFillDataSaveRow, floatTypeDTO, saveErrorDataInfo, addColumnMap, zbIdQueryField, master, period, hiddenFields, hiddenIds);
            idValue = this.dfDimensionValueGetService.getValues(findAny.get(), context.getModel());
            if (!StringUtils.hasLength(idValue)) {
                return this.getAddRowDimensionValueSet(context, dataFillDataSaveRow, floatTypeDTO, saveErrorDataInfo, addColumnMap, zbIdQueryField, master, period, hiddenFields, hiddenIds);
            }
        } else {
            idValue = values.get(idIndex);
            if (!StringUtils.hasLength(idValue)) {
                return this.getAddRowDimensionValueSet(context, dataFillDataSaveRow, floatTypeDTO, saveErrorDataInfo, addColumnMap, zbIdQueryField, master, period, hiddenFields, hiddenIds);
            }
        }
        String[] bizKeys = idValue.split("\\#\\^\\$");
        List<String> tableDimensionNames = floatTypeDTO.getTableDimensionNames();
        String bizKey = floatTypeDTO.getBizKey();
        int floatType = floatTypeDTO.getFloatType();
        if (floatType == 0) {
            sqlDimensionValueSet.setValue(bizKey, (Object)idValue);
            return sqlDimensionValueSet;
        } else if (bizKeys.length != tableDimensionNames.size()) {
            sqlDimensionValueSet.setValue(bizKey, (Object)idValue);
            return sqlDimensionValueSet;
        } else {
            for (int i = 0; i < tableDimensionNames.size(); ++i) {
                sqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)bizKeys[i]);
            }
        }
        return sqlDimensionValueSet;
    }

    private List<DimensionValueSet> getDeleteRowDimensionValueSet(DataFillContext context, DataFillDataDeleteRow fillDataDeleteRow, FloatTypeDTO floatTypeDTO) {
        Optional<DFDimensionValue> findAny;
        ArrayList<DimensionValueSet> returnList = new ArrayList<DimensionValueSet>();
        List<String> idValues = fillDataDeleteRow.getValues();
        List<DFDimensionValue> dimensionValues = fillDataDeleteRow.getDimensionValues();
        if ((null == idValues || idValues.isEmpty()) && (findAny = dimensionValues.stream().filter(e -> e.getName().equals("ID")).findAny()).isPresent()) {
            idValues = new ArrayList<String>();
            String idValue = this.dfDimensionValueGetService.getValues(findAny.get(), context.getModel());
            idValues.add(idValue);
            fillDataDeleteRow.setValues(idValues);
        }
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValues);
        for (String idValue : idValues) {
            DimensionValueSet tempSqlDimensionValueSet = new DimensionValueSet(sqlDimensionValueSet);
            String[] bizKeys = idValue.split("\\#\\^\\$");
            List<String> tableDimensionNames = floatTypeDTO.getTableDimensionNames();
            String bizKey = floatTypeDTO.getBizKey();
            int floatType = floatTypeDTO.getFloatType();
            if (floatType == 0) {
                tempSqlDimensionValueSet.setValue(bizKey, (Object)idValue);
            } else {
                if (tableDimensionNames.size() != bizKeys.length) continue;
                for (int i = 0; i < tableDimensionNames.size(); ++i) {
                    tempSqlDimensionValueSet.setValue(tableDimensionNames.get(i), (Object)bizKeys[i]);
                }
            }
            returnList.add(tempSqlDimensionValueSet);
        }
        return returnList;
    }

    private boolean isValidDataRow(DataFillDataSaveRow dataFillDataSaveRow, IDataRow sqlRow) {
        boolean isEmpty = true;
        List<String> values = dataFillDataSaveRow.getValues();
        if (null != sqlRow) {
            isEmpty = false;
        } else if (null != values && !values.isEmpty()) {
            for (String value : values) {
                if (!StringUtils.hasLength(value) || !StringUtils.hasLength(value.trim())) continue;
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    public FloatTypeDTO getFloatType(List<DataTable> dataTables, DimensionValueSet sqlDimensionValueSet, String periodDimensionName) {
        boolean repeatCode = dataTables.get(0).isRepeatCode();
        ArrayList<String> tableDimensionNames = new ArrayList<String>();
        ArrayList<String> fieldKeys = new ArrayList<String>();
        HashMap<String, FieldDefine> dimenNameFieldMap = new HashMap<String, FieldDefine>();
        String bizKey = "";
        String[] bizKeys = dataTables.get(0).getBizKeys();
        DimensionSet dimensionSet = sqlDimensionValueSet.getDimensionSet();
        for (String fieldKey : bizKeys) {
            FieldDefine fieldDefine = null;
            try {
                fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
            }
            catch (Exception e) {
                StringBuilder logInfo = new StringBuilder();
                logInfo.append("\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u7ef4\u5ea6\u540d\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
                logger.error(logInfo.toString(), e);
                throw new DataFillRuntimeException(logInfo.toString());
            }
            String dimensionName = this.dFDimensionParser.getDimensionName(fieldKey);
            if (!(dimensionSet.contains(dimensionName) || dimensionName.equals(periodDimensionName) || dimensionName.equals("ADJUST"))) {
                tableDimensionNames.add(dimensionName);
                fieldKeys.add(fieldKey);
            }
            dimenNameFieldMap.put(dimensionName, fieldDefine);
            if (fieldDefine.getValueType().getValue() != FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) continue;
            bizKey = dimensionName;
        }
        int floatType = 0;
        floatType = repeatCode && tableDimensionNames.size() == 1 ? 0 : (repeatCode && tableDimensionNames.size() > 1 ? 1 : 2);
        FloatTypeDTO floatTypeDto = new FloatTypeDTO();
        floatTypeDto.setFloatType(floatType);
        floatTypeDto.setBizKey(bizKey);
        floatTypeDto.setTableDimensionNames(tableDimensionNames);
        floatTypeDto.setFieldKeys(fieldKeys);
        floatTypeDto.setDimenNameFieldMap(dimenNameFieldMap);
        return floatTypeDto;
    }

    private DimensionValueSet parseRowKeys(FloatTypeDTO floatType, DimensionValueSet sqlRowKeys) {
        DimensionValueSet parseRowKeys = new DimensionValueSet();
        int size = sqlRowKeys.size();
        for (int j = 0; j < size; ++j) {
            String name = sqlRowKeys.getName(j);
            Object value = sqlRowKeys.getValue(j);
            if (floatType.getTableDimensionNames().contains(name)) continue;
            parseRowKeys.setValue(name, value);
        }
        if (floatType.getFloatType() == 0) {
            Object value = sqlRowKeys.getValue(floatType.getBizKey());
            parseRowKeys.setValue("ID", value);
        } else {
            StringBuilder idValueBuilder = new StringBuilder();
            for (String tableDimensionName : floatType.getTableDimensionNames()) {
                Object tableDimensionNameValue = sqlRowKeys.getValue(tableDimensionName);
                if (null == tableDimensionNameValue) {
                    tableDimensionNameValue = "";
                }
                if (idValueBuilder.length() == 0) {
                    idValueBuilder.append(tableDimensionNameValue);
                    continue;
                }
                idValueBuilder.append("#^$").append(tableDimensionNameValue);
            }
            parseRowKeys.setValue("ID", (Object)idValueBuilder.toString());
        }
        return parseRowKeys;
    }

    public class FloatTypeDTO {
        List<String> tableDimensionNames;
        List<String> fieldKeys;
        String bizKey;
        int floatType;
        Map<String, FieldDefine> dimenNameFieldMap;

        public List<String> getTableDimensionNames() {
            return this.tableDimensionNames;
        }

        public void setTableDimensionNames(List<String> tableDimensionNames) {
            this.tableDimensionNames = tableDimensionNames;
        }

        public String getBizKey() {
            return this.bizKey;
        }

        public void setBizKey(String bizKey) {
            this.bizKey = bizKey;
        }

        public int getFloatType() {
            return this.floatType;
        }

        public void setFloatType(int floatType) {
            this.floatType = floatType;
        }

        public List<String> getFieldKeys() {
            return this.fieldKeys;
        }

        public void setFieldKeys(List<String> fieldKeys) {
            this.fieldKeys = fieldKeys;
        }

        public Map<String, FieldDefine> getDimenNameFieldMap() {
            return this.dimenNameFieldMap;
        }

        public void setDimenNameFieldMap(Map<String, FieldDefine> dimenNameFieldMap) {
            this.dimenNameFieldMap = dimenNameFieldMap;
        }
    }
}

