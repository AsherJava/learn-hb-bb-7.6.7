/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
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
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class DataFillFixedDataEnvServiceImpl
extends IDataFillEnvBaseDataService {
    private static final Logger logger = LoggerFactory.getLogger(DataFillFixedDataEnvServiceImpl.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DFDimensionValueGetService dfDimensionValueGetService;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;

    @Override
    public TableType getTableType() {
        return TableType.FIXED;
    }

    @Override
    public DataFillQueryResult query(DataFillDataQueryInfo queryInfo) {
        LinkedHashMap<DimensionValueSet, List<AbstractData>> resultMap = new LinkedHashMap<DimensionValueSet, List<AbstractData>>();
        DataFillContext context = queryInfo.getContext();
        Map<String, QueryField> queryFieldsMap = this.dFDimensionParser.getQueryFieldsMap(context);
        List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
        List<QueryField> zbQueryFields = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD).collect(Collectors.toList());
        DimensionValueSet entityDimensionValueSet = this.dFDimensionParser.parserGetEntityDimensionValueSet(context);
        DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, entityDimensionValueSet);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.setMasterKeys(sqlDimensionValueSet);
        dataQuery.setQueryModule(false);
        DimensionSet sqlDimensionSet = sqlDimensionValueSet.getDimensionSet();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Optional<DFDimensionValue> findPeriod = dimensionValues.stream().filter(e -> e.getName().equals(periodField.getSimplifyFullCode())).findAny();
        if (findPeriod.isPresent()) {
            for (int i = 0; i < sqlDimensionSet.size(); ++i) {
                dataQuery.addRightJoinDimTable(sqlDimensionSet.get(i));
            }
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        String dataSchemeKey = null;
        try {
            executorContext.setOrgEntityId(this.entityMetaService.getEntityIdByCode(masterField.getSimplifyFullCode()));
            List<DataTable> dataTables = this.getDataTables(this.getTableDefines(zbQueryFields));
            VariableManager variableManager = executorContext.getVariableManager();
            dataSchemeKey = dataTables.get(0).getDataSchemeKey();
            variableManager.add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataTables.get(0).getDataSchemeKey()));
            context.getCaches().put("ADJUST", dataSchemeKey);
            ArrayList<QueryField> hiddenQueryField = new ArrayList<QueryField>();
            ArrayList<String> hiddenId = new ArrayList<String>();
            this.dFDimensionParser.hideDimensionsProcess(context, dataSchemeKey, hiddenQueryField, hiddenId);
        }
        catch (Exception e2) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6570\u636e\u65b9\u6848\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e2.getMessage());
            logger.error(logInfo.toString(), e2);
            throw new DataFillRuntimeException(logInfo.toString());
        }
        Map<String, String> extendedData = context.getModel().getExtendedData();
        String formschemeKey = extendedData.get("FORMSCHEMEKEY");
        ReportFmlExecEnvironment reportFmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formschemeKey);
        reportFmlExecEnvironment.setDataScehmeKey(dataSchemeKey);
        reportFmlExecEnvironment.getVariableManager().add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataSchemeKey));
        executorContext.setEnv((IFmlExecEnvironment)reportFmlExecEnvironment);
        executorContext.setJQReportModel(true);
        executorContext.setAutoDataMasking(true);
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
            dataQuery.addColumn(fieldDefine);
        }
        PageInfo pageInfo = queryInfo.getPagerInfo();
        IReadonlyTable readonlyTable = null;
        Optional<DFDimensionValue> findMaster = dimensionValues.stream().filter(e -> e.getName().equals(masterField.getSimplifyFullCode())).findAny();
        PageResult pageResult = null;
        if (!findMaster.isPresent()) {
            pageResult = new PageResult();
            pageResult.setItems(new ArrayList<IDataRow>());
        } else {
            try {
                readonlyTable = dataQuery.executeReader(executorContext);
            }
            catch (Exception e4) {
                DimensionValueSet masterKeys = dataQuery.getMasterKeys();
                logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e4.getMessage(), e4);
                throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString());
            }
            Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null");
            List<IDataRow> dataRows = this.dFDimensionParser.hiddenRowKeysProcess(readonlyTable, dataSchemeKey, context);
            boolean enableAdjust = this.dataSchemeService.enableAdjustPeriod(dataSchemeKey) != false && sqlDimensionValueSet.getValue("ADJUST") == null;
            PageResult tableWithEmptyRows = this.fillInEmptyRows(dataRows, sqlDimensionValueSet, enableAdjust, context, zbQueryFields, readonlyTable, dataSchemeKey);
            pageResult = this.sortAndPage(tableWithEmptyRows, pageInfo, context, queryFieldsMap, zbQueryFields, enableAdjust);
        }
        List<IDataRow> items = pageResult.getItems();
        for (int i = 0; i < items.size(); ++i) {
            IDataRow dataRow = items.get(i);
            DimensionValueSet rowKeys = this.dFDimensionParser.sqlDimensionToEntityId(context, dataRow.getRowKeys());
            ArrayList<AbstractData> values = new ArrayList<AbstractData>();
            for (int j = 0; j < zbQueryFields.size(); ++j) {
                AbstractData npData = dataRow.getValue(j);
                values.add(npData);
            }
            resultMap.put(rowKeys, values);
        }
        DataFillQueryResult dataFillQueryResult = new DataFillQueryResult();
        dataFillQueryResult.setDatas(resultMap);
        if (null != pageInfo) {
            dataFillQueryResult.setPageInfo(pageInfo);
            dataFillQueryResult.setTotalCount(pageResult.getTotal());
        }
        return dataFillQueryResult;
    }

    private PageResult fillInEmptyRows(List<IDataRow> dataRows, DimensionValueSet sqlDimensionValueSet, boolean enableAdjust, DataFillContext context, List<QueryField> zbQueryFields, IReadonlyTable readonlyTable, String dataSchemeKey) {
        String sceneValue;
        List adjustPeriodList;
        Map<String, List<AdjustPeriod>> adjustPeriodKeyMap = null;
        if (enableAdjust && null != (adjustPeriodList = this.adjustPeriodService.queryAdjustPeriods(dataSchemeKey))) {
            adjustPeriodKeyMap = adjustPeriodList.stream().collect(Collectors.groupingBy(AdjustPeriod::getPeriod));
        }
        PageResult pageResult = new PageResult();
        pageResult.items = new ArrayList();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        QueryField sceneField = null;
        if (fieldTypeQueryFields.containsKey((Object)FieldType.SCENE)) {
            sceneField = fieldTypeQueryFields.get((Object)FieldType.SCENE).get(0);
        }
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        if (sceneField != null && StringUtils.hasLength(sceneValue = (String)sqlDimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(sceneField)))) {
            List<String> referRelation = this.dFDimensionParser.searchReferRelation(sceneValue, sceneField.getId(), sceneField, sqlDimensionValueSet, masterField, periodField, dataSchemeKey);
            String dwKey = this.dFDimensionParser.getSqlDimensionName(masterField);
            sqlDimensionValueSet.setValue(dwKey, referRelation);
        }
        DimensionValueSet endSet = new DimensionValueSet(sqlDimensionValueSet);
        String periodName = this.dFDimensionParser.getSqlDimensionName(periodField);
        int size = zbQueryFields.size();
        ArrayList dimensionValueSetLists = new ArrayList();
        ArrayList<String> periodValues = new ArrayList<String>();
        for (int i = 0; i < endSet.size(); ++i) {
            String name = endSet.getName(i);
            boolean isPeriod = name.equals(periodName);
            List<String> values = null;
            values = endSet.getValue(i) instanceof List ? (List)endSet.getValue(i) : Arrays.asList(((String)endSet.getValue(i)).split(";"));
            if (isPeriod) {
                periodValues.addAll(values);
                continue;
            }
            ArrayList<DimensionValueSet> newSetLists = new ArrayList<DimensionValueSet>();
            for (String value : values) {
                DimensionValueSet dimensionValueSet2 = new DimensionValueSet();
                dimensionValueSet2.setValue(endSet.getName(i), (Object)value);
                if (dimensionValueSetLists.isEmpty()) {
                    newSetLists.add(dimensionValueSet2);
                    continue;
                }
                for (DimensionValueSet ValueSet : dimensionValueSetLists) {
                    DimensionValueSet newValue = new DimensionValueSet(dimensionValueSet2);
                    newValue.combine(ValueSet);
                    newSetLists.add(newValue);
                }
            }
            dimensionValueSetLists = newSetLists;
        }
        ArrayList<DimensionValueSet> dimensionValueAllSet = new ArrayList<DimensionValueSet>();
        for (String period : periodValues) {
            for (DimensionValueSet dimensionValueSet3 : dimensionValueSetLists) {
                if (enableAdjust && null != adjustPeriodKeyMap && adjustPeriodKeyMap.containsKey(period)) {
                    List<AdjustPeriod> adjustList = adjustPeriodKeyMap.get(period);
                    for (AdjustPeriod adjustPeriod : adjustList) {
                        DimensionValueSet dimensionValueSetWithPeriod = new DimensionValueSet(dimensionValueSet3);
                        dimensionValueSetWithPeriod.setValue(periodName, (Object)period);
                        dimensionValueSetWithPeriod.setValue("ADJUST", (Object)adjustPeriod.getCode());
                        dimensionValueAllSet.add(dimensionValueSetWithPeriod);
                    }
                    continue;
                }
                DimensionValueSet dimensionValueSetWithPeriod = new DimensionValueSet(dimensionValueSet3);
                dimensionValueSetWithPeriod.setValue(periodName, (Object)period);
                if (enableAdjust) {
                    dimensionValueSetWithPeriod.setValue("ADJUST", (Object)"0");
                }
                dimensionValueAllSet.add(dimensionValueSetWithPeriod);
            }
        }
        for (IDataRow dataRow : dataRows) {
            DimensionValueSet toBeRemoved = dataRow.getRowKeys();
            dimensionValueAllSet.removeIf(dimensionValueSet -> dimensionValueSet.isSubsetOf(toBeRemoved));
            pageResult.items.add(dataRow);
        }
        for (DimensionValueSet dimensionValueSet4 : dimensionValueAllSet) {
            ArrayList<Object> nullList = new ArrayList<Object>();
            for (int i = 0; i < size; ++i) {
                nullList.add(null);
            }
            DataRowImpl dataRow = new DataRowImpl((ReadonlyTableImpl)readonlyTable, dimensionValueSet4, nullList);
            pageResult.items.add(dataRow);
        }
        return pageResult;
    }

    /*
     * WARNING - void declaration
     */
    private PageResult sortAndPage(PageResult readonlyTable, PageInfo pageInfo, DataFillContext context, Map<String, QueryField> queryFieldsMap, List<QueryField> zbQueryFields, boolean enableAdjust) {
        void var19_44;
        PageResult pageResult = new PageResult();
        ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<OrderItemInfo>();
        List<OrderField> orderFields = context.getModel().getOrderFields();
        String masterDimension = "";
        String periodDimension = "";
        QueryField masterField = null;
        if (null != orderFields && !orderFields.isEmpty()) {
            for (OrderField orderField : orderFields) {
                String fullCode = orderField.getFullCode();
                QueryField queryField = queryFieldsMap.get(fullCode);
                if (queryField.getFieldType() == FieldType.ZB || queryField.getFieldType() == FieldType.FIELD) {
                    int zbIndex = -1;
                    for (int i = 0; i < zbQueryFields.size(); ++i) {
                        if (!fullCode.equals(zbQueryFields.get(i).getFullCode())) continue;
                        zbIndex = i;
                        break;
                    }
                    orderItemInfos.add(new OrderItemInfo(queryField.getFullCode(), false, orderField.getMode(), zbIndex));
                    continue;
                }
                FieldType fieldType = queryField.getFieldType();
                String dimensionName = this.dFDimensionParser.getDimensionNameByField(queryField);
                if (fieldType == FieldType.MASTER) {
                    masterField = queryField;
                    masterDimension = dimensionName;
                    String string = queryField.getSimplifyFullCode();
                    ArrayList<String> dwList = new ArrayList<String>();
                    String[] findAny = context.getDimensionValues().stream().filter(e -> e.getName().equals(simplifyFullCode)).findAny();
                    DFDimensionValue dfDimensionValue = findAny.get();
                    String value = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                    if (null != value) {
                        if (!value.contains(";")) {
                            dwList.add(value);
                        } else {
                            String[] split;
                            for (String dwCode : split = value.split(";")) {
                                dwList.add(dwCode);
                            }
                        }
                    }
                    orderItemInfos.add(new OrderItemInfo(dimensionName, dwList, orderField.getMode()));
                    continue;
                }
                if (fieldType == FieldType.PERIOD) {
                    periodDimension = dimensionName;
                    if (enableAdjust) {
                        orderItemInfos.add(new OrderItemInfo("ADJUST", true, orderField.getMode(), -1));
                    }
                }
                orderItemInfos.add(new OrderItemInfo(dimensionName, true, orderField.getMode(), -1));
            }
        } else {
            DFDimensionValue dfDimensionValue;
            String string;
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            QueryField period = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
            masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            periodDimension = this.dFDimensionParser.getDimensionNameByField(period);
            orderItemInfos.add(new OrderItemInfo(periodDimension, true, OrderMode.DESC, -1));
            if (enableAdjust) {
                orderItemInfos.add(new OrderItemInfo("ADJUST", true, OrderMode.DESC, -1));
            }
            masterDimension = this.dFDimensionParser.getDimensionNameByField(masterField);
            String simplifyFullCode = masterField.getSimplifyFullCode();
            ArrayList<String> dwList = new ArrayList<String>();
            Optional<DFDimensionValue> findAny = context.getDimensionValues().stream().filter(e -> e.getName().equals(simplifyFullCode)).findAny();
            if (findAny.isPresent() && null != (string = this.dfDimensionValueGetService.getValues(dfDimensionValue = findAny.get(), context.getModel()))) {
                if (!string.contains(";")) {
                    dwList.add(string);
                } else {
                    String[] split;
                    for (String dwCode : split = string.split(";")) {
                        dwList.add(dwCode);
                    }
                }
            }
            orderItemInfos.add(new OrderItemInfo(masterDimension, dwList, OrderMode.ASC));
        }
        boolean mayExistMasterDirtyData = this.dFDimensionParser.isMasterMultipleVersion(context);
        ArrayList<String> periodLists = new ArrayList<String>();
        ArrayList<String> masterLists = new ArrayList<String>();
        ArrayList<IDataRow> allRows = new ArrayList<IDataRow>();
        if (!StringUtils.hasLength(masterDimension)) {
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            masterDimension = this.dFDimensionParser.getDimensionNameByField(masterField);
        }
        for (int i = 0; i < readonlyTable.items.size(); ++i) {
            IDataRow dataRow = (IDataRow)readonlyTable.items.get(i);
            allRows.add(dataRow);
            if (!mayExistMasterDirtyData) continue;
            DimensionValueSet dimensionValueSet = dataRow.getRowKeys();
            String periodValue = (String)dimensionValueSet.getValue(periodDimension);
            String masterValue = (String)dimensionValueSet.getValue(masterDimension);
            periodLists.add(periodValue);
            masterLists.add(masterValue);
        }
        if (mayExistMasterDirtyData) {
            Map<String, DataFillDimensionTitle> dimensionTitle = this.dFDimensionParser.getDwDimensionTitle(masterField, masterLists, periodLists, context.getModel());
            pageResult.setMasterDimensionTitles(dimensionTitle);
            Iterator iterator = allRows.iterator();
            while (iterator.hasNext()) {
                IDataRow iDataRow = (IDataRow)iterator.next();
                DimensionValueSet rowKeys = iDataRow.getRowKeys();
                String periodValue = (String)rowKeys.getValue(periodDimension);
                String masterValue = (String)rowKeys.getValue(masterDimension);
                String key = periodValue + ";" + masterValue;
                if (dimensionTitle.containsKey(key)) continue;
                iterator.remove();
            }
        }
        pageResult.setTotal(allRows.size());
        ArrayList<ComparatorIDataRow> comparators = new ArrayList<ComparatorIDataRow>();
        for (OrderItemInfo orderItemInfo : orderItemInfos) {
            ComparatorIDataRow itemComparator = new ComparatorIDataRow(orderItemInfo);
            comparators.add(itemComparator);
        }
        Comparator<IDataRow> finalComparator = null;
        for (ComparatorIDataRow comparatorIDataRow : comparators) {
            if (null == finalComparator) {
                finalComparator = comparatorIDataRow;
                continue;
            }
            finalComparator = finalComparator.thenComparing(comparatorIDataRow);
        }
        List list = allRows.stream().sorted(finalComparator).collect(Collectors.toList());
        if (null != pageInfo) {
            int limit = pageInfo.getLimit();
            int offset = pageInfo.getOffset();
            int beginIndex = limit * offset;
            int endIndex = limit * (offset + 1);
            if (list.size() >= endIndex) {
                List list2 = list.subList(beginIndex, endIndex);
            } else if (list.size() > beginIndex) {
                List list3 = list.subList(beginIndex, list.size());
            }
        }
        pageResult.setItems((List<IDataRow>)var19_44);
        return pageResult;
    }

    @Override
    public DataFillResult save(DataFillDataSaveInfo saveInfo) {
        DataFillResult dataFillResult = new DataFillResult();
        ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
        dataFillResult.setErrors(errors);
        dataFillResult.setSuccess(true);
        dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveSuccess"));
        DataFillContext context = saveInfo.getContext();
        List<DataFillDataSaveRow> adds = saveInfo.getAdds();
        List<DataFillDataSaveRow> modifys = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deletes = saveInfo.getDeletes();
        List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
        List<QueryField> zbQueryFields = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD).collect(Collectors.toList());
        if (null != adds && adds.size() > 0) {
            throw new DataFillRuntimeException("\u56fa\u5b9a\u8868\u8bf7\u5168\u90e8\u4f7f\u7528\u4fee\u6539\u6765\u64cd\u4f5c\uff01");
        }
        if (null != deletes && deletes.size() > 0) {
            throw new DataFillRuntimeException("\u56fa\u5b9a\u8868\u8bf7\u5168\u90e8\u4f7f\u7528\u4fee\u6539\u6765\u64cd\u4f5c\uff01");
        }
        if (null != modifys && modifys.size() > 0) {
            String dataSchemeKey;
            HashSet<String> zbSets = new HashSet<String>();
            HashMap<String, HashSet<String>> entityIdMap = new HashMap<String, HashSet<String>>();
            for (DataFillDataSaveRow dataFillDataSaveRow : modifys) {
                List<DFDimensionValue> list = dataFillDataSaveRow.getDimensionValues();
                for (DFDimensionValue dimensionValue : list) {
                    HashSet<String> dimSet = (HashSet<String>)entityIdMap.get(dimensionValue.getName());
                    if (null == dimSet) {
                        dimSet = new HashSet<String>();
                        entityIdMap.put(dimensionValue.getName(), dimSet);
                    }
                    dimSet.add(this.dfDimensionValueGetService.getValues(dimensionValue, context.getModel()));
                }
                zbSets.addAll(dataFillDataSaveRow.getZbs());
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
            Map<FieldType, List<QueryField>> map = this.dFDimensionParser.getFieldTypeQueryFields(context);
            String string = this.dFDimensionParser.getSqlDimensionName(map.get((Object)FieldType.MASTER).get(0));
            String periodSqlKey = this.dFDimensionParser.getSqlDimensionName(map.get((Object)FieldType.PERIOD).get(0));
            QueryField master = map.get((Object)FieldType.MASTER).get(0);
            QueryField period = map.get((Object)FieldType.PERIOD).get(0);
            QueryField scene = null;
            if (map.containsKey((Object)FieldType.SCENE) && map.get((Object)FieldType.SCENE) != null) {
                scene = map.get((Object)FieldType.SCENE).get(0);
            }
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ArrayList<QueryField> hiddenQueryField = new ArrayList<QueryField>();
            ArrayList<String> hiddenId = new ArrayList<String>();
            try {
                executorContext.setOrgEntityId(this.entityMetaService.getEntityIdByCode(master.getSimplifyFullCode()));
                List<DataTable> dataTables = this.getDataTables(this.getTableDefines(zbQueryFields));
                VariableManager variableManager = executorContext.getVariableManager();
                variableManager.add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataTables.get(0).getDataSchemeKey()));
                dataSchemeKey = dataTables.get(0).getDataSchemeKey();
            }
            catch (Exception e2) {
                StringBuilder logInfo = new StringBuilder();
                logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6570\u636e\u65b9\u6848\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e2.getMessage());
                logger.error(logInfo.toString(), e2);
                throw new DataFillRuntimeException(logInfo.toString());
            }
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
            dataQuery.setStatic(false);
            DimensionValueSet sqlDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValues);
            dataQuery.setMasterKeys(sqlDimensionValueSet);
            ReportFmlExecEnvironment reportFmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, null);
            reportFmlExecEnvironment.setDataScehmeKey(dataSchemeKey);
            reportFmlExecEnvironment.getVariableManager().add(new Variable("NR.var.dataScheme", "NR.var.dataScheme", 6, (Object)dataSchemeKey));
            executorContext.setEnv((IFmlExecEnvironment)reportFmlExecEnvironment);
            executorContext.setJQReportModel(true);
            HashMap<String, FieldDefine> zbFieldsMap = new HashMap<String, FieldDefine>();
            Map<String, QueryField> zbIdQueryField = this.dFDimensionParser.getAllQueryFields(context).stream().collect(Collectors.toMap(QueryField::getId, e -> e, (existing, replacement) -> existing));
            for (String zbId : zbSets) {
                FieldDefine fieldDefine = null;
                QueryField queryField = zbIdQueryField.get(zbId);
                if (queryField.getFieldType() != FieldType.ZB) continue;
                try {
                    fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(zbId);
                    zbFieldsMap.put(zbId, fieldDefine);
                }
                catch (Exception e3) {
                    StringBuilder logInfo = new StringBuilder();
                    logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(zbId).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e3.getMessage());
                    logger.error(logInfo.toString(), e3);
                    throw new DataFillRuntimeException(logInfo.toString());
                }
                dataQuery.addColumn(fieldDefine);
            }
            IDataTable dataTable = null;
            try {
                dataTable = dataQuery.executeQuery(executorContext);
            }
            catch (Exception e4) {
                DimensionValueSet masterKeys = dataQuery.getMasterKeys();
                logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e4.getMessage(), e4);
                throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u81ea\u5b9a\u4e49\u67e5\u8be2\u51fa\u9519\uff1a\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString());
            }
            this.dFDimensionParser.hideDimensionsProcess(context, dataSchemeKey, hiddenQueryField, hiddenId);
            FieldReadWriteAccessResultInfo accessResult = this.getAccess(saveInfo);
            for (DataFillDataSaveRow dataFillDataSaveRow : modifys) {
                DimensionValueSet sqlRowValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dataFillDataSaveRow.getDimensionValues());
                String dwCode = (String)sqlRowValueSet.getValue(string);
                String periodValue = (String)sqlRowValueSet.getValue(periodSqlKey);
                if (scene != null) {
                    List<String> sceneValues = this.dFDimensionParser.searchReferRelation(dwCode, master.getId(), scene, sqlRowValueSet, master, period, dataSchemeKey);
                    String sceneValue = (String)sqlRowValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(scene));
                    if (sceneValues != null && !Arrays.asList(sceneValues.get(0).split(";")).contains(sceneValue)) {
                        Map<String, DataFillDimensionTitle> dimensionOtherTitleMap = this.dFDimensionParser.getDimensionTitle(scene, Collections.singletonList(sceneValue), null, context.getModel());
                        Map<String, DataFillDimensionTitle> dimensionTitleMap = this.dFDimensionParser.getDwDimensionTitle(master, Collections.singletonList(dwCode), Collections.singletonList(periodValue), context.getModel());
                        DataFillSaveErrorDataInfo errorDataInfo = this.buildErrorInfo(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.scene}}") + dimensionOtherTitleMap.get(sceneValue).getTitle() + NrDataFillI18nUtil.parseMsg("{{nr.dataFill.andOrg}}") + dimensionTitleMap.get(periodValue + ";" + dwCode).getTitle() + NrDataFillI18nUtil.parseMsg("{{nr.dataFill.match}}"), dataFillDataSaveRow.getDimensionValues());
                        errorDataInfo.setZb(dataFillDataSaveRow.getZbs().get(0));
                        errorDataInfo.setValue((Serializable)((Object)dataFillDataSaveRow.getValues().get(0)));
                        errors.add(errorDataInfo);
                    }
                }
                try {
                    this.dFDimensionParser.searchDwHiddenDimensionValue(dwCode, sqlRowValueSet, master, period, hiddenQueryField, hiddenId, false);
                }
                catch (DataFillRuntimeException e5) {
                    StringBuilder errorInfo = new StringBuilder("\u81ea\u5b9a\u4e49\u5f55\u5165\u4fdd\u5b58\u51fa\u9519\uff1a\u7ef4\u5ea6\u4e3a");
                    errorInfo.append(":[");
                    Map<String, DataFillDimensionTitle> dimensionTitleMap = this.dFDimensionParser.getDwDimensionTitle(master, Collections.singletonList(dwCode), Collections.singletonList(periodValue), context.getModel());
                    Map<String, DataFillDimensionTitle> dimensionOtherTitleMap = this.dFDimensionParser.getDimensionTitle(period, Collections.singletonList(periodValue), Collections.singletonList(periodValue), context.getModel());
                    errorInfo.append(dimensionTitleMap.get(periodValue + ";" + dwCode).getTitle());
                    errorInfo.append(",").append(dimensionOtherTitleMap.get(periodValue).getTitle());
                    errorInfo.append("]");
                    errorInfo.append("\u7684\u5355\u4f4d").append(e5.getMessage());
                    dataFillResult.setSuccess(false);
                    dataFillResult.setMessage(errorInfo.toString());
                    return dataFillResult;
                }
                List<String> zbs = dataFillDataSaveRow.getZbs();
                List<String> values = dataFillDataSaveRow.getValues();
                IDataRow dataRow = dataTable.findRow(sqlRowValueSet);
                if (dataRow == null) {
                    try {
                        dataRow = dataTable.appendRow(sqlRowValueSet);
                    }
                    catch (Exception e6) {
                        DimensionValueSet masterKeys = dataQuery.getMasterKeys();
                        logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u4fdd\u5b58\u51fa\u9519\uff0c\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e6.getMessage(), e6);
                        throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u81ea\u5b9a\u4e49\u4fdd\u5b58\u51fa\u9519\uff1a\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString());
                    }
                }
                for (int i = 0; i < zbs.size(); ++i) {
                    String zbId = zbs.get(i);
                    QueryField queryField = zbIdQueryField.get(zbId);
                    if (queryField.getFieldType() != FieldType.ZB) continue;
                    DataFieldWriteAccessResultInfo access = accessResult.getAccess(sqlRowValueSet, zbId, context);
                    if (access.haveAccess()) {
                        String zbValue = values.get(i);
                        FieldDefine zb = (FieldDefine)zbFieldsMap.get(zbId);
                        DataFillSaveErrorDataInfo saveErrorDataInfo = this.toSqlValue(zbValue, queryField, context, dataFillDataSaveRow.getDimensionValues(), null, zb, null);
                        if (null != saveErrorDataInfo.getDataError()) {
                            errors.add(saveErrorDataInfo);
                            continue;
                        }
                        dataRow.setValue(zb, (Object)saveErrorDataInfo.getValue());
                        continue;
                    }
                    DataFillSaveErrorDataInfo saveErrorDataInfo = accessResult.getMessage(sqlRowValueSet, zbId, queryField, context, dataFillDataSaveRow.getDimensionValues(), access);
                    errors.add(saveErrorDataInfo);
                }
            }
            try {
                if (errors.isEmpty()) {
                    dataTable.commitChanges(true);
                } else {
                    dataFillResult.setSuccess(false);
                    dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveFailed"));
                }
            }
            catch (Exception e7) {
                DimensionValueSet masterKeys = dataTable.getMasterKeys();
                logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u81ea\u5b9a\u4e49\u4fdd\u5b58\u51fa\u9519\uff1a\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e7.getMessage(), e7);
                throw new DataFillRuntimeException("\u81ea\u5b9a\u4e49\u5f55\u5165\u56fa\u5b9a\u6307\u6807\u81ea\u5b9a\u4e49\u4fdd\u5b58\u51fa\u9519\uff1a\u5f53\u524d\u7ef4\u5ea6\uff1a" + masterKeys.toString() + ";\u51fa\u9519\u539f\u56e0\uff1a" + e7.getMessage());
            }
        }
        return dataFillResult;
    }

    private DataFillSaveErrorDataInfo buildErrorInfo(String reason, List<DFDimensionValue> dimensionValues) {
        DataFillSaveErrorDataInfo saveErrorDataInfo = new DataFillSaveErrorDataInfo();
        saveErrorDataInfo.setDimensionValues(dimensionValues);
        ResultErrorInfo dataError = new ResultErrorInfo();
        dataError.setErrorInfo(reason);
        dataError.setErrorCode(ErrorCode.DATAERROR);
        saveErrorDataInfo.setDataError(dataError);
        return saveErrorDataInfo;
    }

    public class OrderItemInfo {
        private String name;
        private int zbIndex;
        private boolean dim;
        private boolean dw;
        private List<String> dwList;
        private OrderMode orderMode;

        public OrderItemInfo(String name, boolean isDim, OrderMode orderMode, int zbIndex) {
            this.name = name;
            this.dim = isDim;
            this.dw = false;
            this.orderMode = orderMode;
            this.zbIndex = zbIndex;
        }

        public OrderItemInfo(String name, List<String> dwList, OrderMode orderMode) {
            this.name = name;
            this.dim = true;
            this.dw = true;
            this.dwList = dwList;
            this.orderMode = orderMode;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDim() {
            return this.dim;
        }

        public void setDim(boolean dim) {
            this.dim = dim;
        }

        public boolean isDw() {
            return this.dw;
        }

        public void setDw(boolean isDw) {
            this.dw = isDw;
        }

        public OrderMode getOrderMode() {
            return this.orderMode;
        }

        public void setOrderMode(OrderMode orderMode) {
            this.orderMode = orderMode;
        }

        public List<String> getDwList() {
            return this.dwList;
        }

        public void setDwList(List<String> dwList) {
            this.dwList = dwList;
        }

        public int getZbIndex() {
            return this.zbIndex;
        }

        public void setZbIndex(int zbIndex) {
            this.zbIndex = zbIndex;
        }
    }

    public class ComparatorIDataRow
    implements Comparator<IDataRow> {
        private OrderItemInfo orderItemInfo;

        public ComparatorIDataRow(OrderItemInfo orderItemInfo) {
            this.orderItemInfo = orderItemInfo;
        }

        @Override
        public int compare(IDataRow o1, IDataRow o2) {
            OrderMode orderMode = this.orderItemInfo.getOrderMode();
            String name = this.orderItemInfo.getName();
            boolean isDw = this.orderItemInfo.isDw();
            boolean dim = this.orderItemInfo.isDim();
            if (dim) {
                DimensionValueSet rowKeys1 = o1.getRowKeys();
                String value1 = (String)rowKeys1.getValue(name);
                DimensionValueSet rowKeys2 = o2.getRowKeys();
                String value2 = (String)rowKeys2.getValue(name);
                if (isDw) {
                    List<String> dwList = this.orderItemInfo.getDwList();
                    return OrderMode.ASC == orderMode ? dwList.indexOf(value1) - dwList.indexOf(value2) : dwList.indexOf(value2) - dwList.indexOf(value1);
                }
                if (null == value1) {
                    value1 = "";
                }
                if (null == value2) {
                    value2 = "";
                }
                int compareTo = value1.compareTo(value2);
                return OrderMode.ASC == orderMode ? compareTo : -compareTo;
            }
            int zbIndex = this.orderItemInfo.getZbIndex();
            AbstractData value1 = o1.getValue(zbIndex);
            AbstractData value2 = o2.getValue(zbIndex);
            if (null == value1 && null == value2) {
                return 0;
            }
            if (null == value1) {
                return OrderMode.ASC == orderMode ? 1 : -1;
            }
            int compareTo = value1.compareTo(value2);
            return OrderMode.ASC == orderMode ? compareTo : -compareTo;
        }
    }

    public class PageResult {
        private List<IDataRow> items;
        private int total;
        private Map<String, DataFillDimensionTitle> masterDimensionTitles;

        public List<IDataRow> getItems() {
            return this.items;
        }

        public void setItems(List<IDataRow> items) {
            this.items = items;
        }

        public int getTotal() {
            return this.total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public Map<String, DataFillDimensionTitle> getMasterDimensionTitles() {
            return this.masterDimensionTitles;
        }

        public void setMasterDimensionTitles(Map<String, DataFillDimensionTitle> masterDimensionTitles) {
            this.masterDimensionTitles = masterDimensionTitles;
        }
    }
}

