/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcFloatCopyRowDataAnalyser;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyParaParser;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyParam;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.QueryCondition;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcSimpleCopyExecutor {
    private GcSimpleCopyParam param;
    private IFormulaRunTimeController iFormulaRunTimeController;
    private IRunTimeViewController runTimeViewController;
    private final Logger logger = LoggerFactory.getLogger(GcSimpleCopyExecutor.class);

    public GcSimpleCopyExecutor(GcSimpleCopyParaParser parser) {
        this.param = parser.getParam();
        this.iFormulaRunTimeController = (IFormulaRunTimeController)SpringBeanUtils.getBean(IFormulaRunTimeController.class);
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    }

    public boolean execute(QueryContext qContext) throws Exception {
        this.logger.info("GcFloatCopy\u89e3\u6790\u5b8c\u6210,\u5f00\u59cb\u6267\u884c, \u89e3\u6790\u540e\u7684\u53c2\u6570\u4e3a\uff1a" + JsonUtils.writeValueAsString((Object)this.param));
        DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
        DimensionValueSet srcValueSet = this.param.getDimValueSetList(qContext);
        IDataTable srcResult = this.createDataQuery(qContext, this.param.getSrcCondition(), srcValueSet, this.param.getReportName(), this.param.getAlias());
        Map<String, List<IDataRow>> srcRowDataMap = this.queryRowDatas(this.param.getSrcCondition(), srcResult);
        Map<String, IDataRow> srcRowDatas = this.buildSrcRowDatas(this.param.getSrcCondition(), srcRowDataMap);
        this.logger.info("GcFloatCopy\u67e5\u8be2\u5230\u7684\u6e90\u533a\u57df\u6570\u636e\u4e3a\uff1a" + srcRowDatas.toString());
        IDataTable destResult = this.createDataQuery(qContext, this.param.getDesCondition(), currValueSet, qContext.getDefaultGroupName(), null);
        this.clearDest(destResult, this.param.isClearBeforeCopy());
        Map<String, List<IDataRow>> destRowDatas = this.queryRowDatas(this.param.getDesCondition(), destResult);
        this.logger.info("GcFloatCopy\u67e5\u8be2\u5230\u7684\u76ee\u6807\u533a\u57df\u6570\u636e\u4e3a\uff1a" + destRowDatas.toString());
        return this.copy(srcRowDatas, destRowDatas, destResult, this.param.getDesCondition().getQueryColumns());
    }

    private void clearDest(IDataTable result, boolean clearBeforeCopy) throws Exception {
        if (!clearBeforeCopy) {
            return;
        }
        result.deleteAll();
        result.commitChanges(false);
        this.logger.info("GcFloatCopy\u6e05\u9664\u76ee\u6807\u533a\u57df\u6570\u636e\u5b8c\u6bd5");
    }

    private IDataTable createDataQuery(QueryContext qContext, QueryCondition row, DimensionValueSet dimValueSet, String reportName, String alias) throws Exception {
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
        QueryEnvironment environment = new QueryEnvironment();
        String formSchemeKey = env.getFormSchemeKey();
        if (!StringUtils.isEmpty((String)alias)) {
            TaskLinkDefine taskLinkDefine = this.runTimeViewController.queryTaskLinkByCurrentFormSchemeAndNumber(env.getFormSchemeKey(), alias);
            if (Objects.isNull(taskLinkDefine)) {
                throw new BusinessRuntimeException("\u672a\u67e5\u8be2\u5230\u5173\u8054\u4efb\u52a1");
            }
            formSchemeKey = taskLinkDefine.getRelatedFormSchemeKey();
        }
        environment.setFormSchemeKey(formSchemeKey);
        environment.setRegionKey(row.getRegionKey());
        String defaultFormulaSchemeId = this.getDefaultFormulaSchemeId(formSchemeKey);
        if (!StringUtils.isEmpty((String)defaultFormulaSchemeId)) {
            environment.setFormulaSchemeKey(defaultFormulaSchemeId);
        }
        IDataQuery request = accessProvider.newDataQuery(environment);
        for (String column : row.getQueryColumns()) {
            request.addExpressionColumn(column);
        }
        if (row.getFloatOrderField() != null) {
            request.addColumn(row.getFloatOrderField());
        }
        request.setQueryParam(qContext.getQueryParam());
        request.setDefaultGroupName(reportName);
        request.setRowFilter(row.getFilter());
        request.setMasterKeys(dimValueSet);
        IDataTable result = request.executeQuery(qContext.getExeContext());
        return result;
    }

    private Map<String, List<IDataRow>> queryRowDatas(QueryCondition row, IDataTable result) throws DataTypeException {
        HashMap rowDataMap = CollectionUtils.newHashMap();
        for (int i = 0; i < result.getCount(); ++i) {
            IDataRow dataRow = result.getItem(i);
            String dataKey = this.getLinkKey(row, dataRow);
            if (StringUtils.isEmpty((String)dataKey)) continue;
            List rows = (List)rowDataMap.get(dataKey);
            if (rows == null) {
                rows = CollectionUtils.newArrayList();
                rowDataMap.put(dataKey, rows);
            }
            rows.add(dataRow);
        }
        return rowDataMap;
    }

    private String getLinkKey(QueryCondition row, IDataRow dataRow) throws DataTypeException {
        StringBuffer keyDataStr = new StringBuffer();
        List<Integer> keyColumns = row.getKeyColumns();
        for (int col : keyColumns) {
            String value = dataRow.getAsString(col);
            if (value == null) continue;
            keyDataStr.append(value).append("#");
        }
        return keyDataStr.toString();
    }

    private Map<String, IDataRow> buildSrcRowDatas(QueryCondition row, Map<String, List<IDataRow>> rowDataMap) throws DataTypeException {
        HashMap dataMap = CollectionUtils.newHashMap();
        for (String key : rowDataMap.keySet()) {
            if (rowDataMap.get(key).size() > 1) {
                dataMap.put(key, this.buildSrcRowDatas(row, rowDataMap.get(key)));
                continue;
            }
            dataMap.put(key, rowDataMap.get(key).get(0));
        }
        return dataMap;
    }

    private IDataRow buildSrcRowDatas(QueryCondition row, List<IDataRow> rows) throws DataTypeException {
        IDataRow first = rows.get(0);
        StatUnit[] sumDatas = new StatUnit[row.getQueryColumns().size()];
        for (int i = 0; i < row.getQueryColumns().size(); ++i) {
            sumDatas[i] = StatItem.createStatUnit((int)first.getValue((int)i).dataType);
        }
        for (int col = 0; col < row.getQueryColumns().size(); ++col) {
            StatUnit sumData = sumDatas[col];
            for (int i = 0; i < rows.size(); ++i) {
                AbstractData colData = rows.get(i).getValue(col);
                sumData.statistic(colData);
            }
            first.setValue(col, sumData.getResult().getAsObject());
        }
        return first;
    }

    private boolean copy(Map<String, IDataRow> srcRowDatas, Map<String, List<IDataRow>> destRowDatas, IDataTable destResult, List<String> destQueryCols) throws Exception {
        GcFloatCopyRowDataAnalyser analyser = new GcFloatCopyRowDataAnalyser(srcRowDatas, destRowDatas);
        this.update(analyser.getUpdatedDatas());
        if (!CollectionUtils.isEmpty(this.param.getInsertColumns().entrySet())) {
            this.append(destResult, analyser.getUnMatchedSrcDatas());
        }
        for (int i = 0; i < destResult.getCount(); ++i) {
            IDataRow row = destResult.getItem(i);
            DimensionValueSet rowKey = row.getRowKeys();
            DimensionValueSet masterKeys = row.getMasterKeys();
            for (int j = 0; j < masterKeys.size(); ++j) {
                String keyName = masterKeys.getName(j);
                if (!rowKey.hasValue(keyName)) continue;
                rowKey.setValue(keyName, masterKeys.getValue(j));
            }
        }
        destResult.commitChanges(true);
        this.logger.info("GcFloatCopy\u62f7\u8d1d\u5b8c\u6210");
        return true;
    }

    private void append(IDataTable destResult, List<IDataRow> matchedRowDatas) throws IncorrectQueryException, DataTypeException {
        int destColSize = this.param.getDesCondition().getQueryColumns().size();
        for (IDataRow srcRow : matchedRowDatas) {
            DimensionValueSet rowKey = new DimensionValueSet(srcRow.getRowKeys());
            rowKey.setValue("RECORDKEY", (Object)UUIDUtils.newUUIDStr());
            IDataRow newDataRow = destResult.appendRow(rowKey);
            if (newDataRow.getFieldsInfo().getFieldCount() == destColSize + 1) {
                newDataRow.setValue(destColSize, (Object)(destResult.getCount() * 1000));
            }
            this.copyRow(newDataRow, this.param.getInsertColumns(), srcRow);
        }
    }

    private void update(Map<IDataRow, IDataRow> matchedRowDatas) throws DataTypeException {
        for (Map.Entry<IDataRow, IDataRow> key : matchedRowDatas.entrySet()) {
            IDataRow srcRow = key.getValue();
            IDataRow destRow = key.getKey();
            this.copyRow(destRow, this.param.getUpdateColumns(), srcRow);
        }
    }

    private void copyRow(IDataRow dataRow, Map<Integer, Integer> keyFields, IDataRow row) throws DataTypeException {
        Map<Integer, Integer> allColumnFields = this.param.getDesCondition().getQueryColumnTypes();
        for (Map.Entry<Integer, Integer> field : keyFields.entrySet()) {
            if (Objects.isNull(row)) {
                Integer type = allColumnFields.get(field.getKey());
                if (FieldType.FIELD_TYPE_DECIMAL.getValue() == type.intValue() || FieldType.FIELD_TYPE_INTEGER.getValue() == type.intValue() || FieldType.FIELD_TYPE_FLOAT.getValue() == type.intValue()) {
                    dataRow.setValue(field.getKey().intValue(), (Object)0);
                    continue;
                }
                if (FieldType.FIELD_TYPE_STRING.getValue() == type.intValue()) {
                    dataRow.setValue(field.getKey().intValue(), (Object)"");
                    continue;
                }
                dataRow.setValue(field.getKey().intValue(), null);
                continue;
            }
            dataRow.setValue(field.getKey().intValue(), row.getValue(field.getValue().intValue()).getAsObject());
        }
    }

    private String getDefaultFormulaSchemeId(String schemeId) {
        List definesByFormScheme = this.iFormulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(schemeId);
        if (!org.springframework.util.CollectionUtils.isEmpty(definesByFormScheme)) {
            List formulaSchemeDefines = definesByFormScheme.stream().filter(formulaSchemeDefine -> {
                FormulaSchemeType schemeType = formulaSchemeDefine.getFormulaSchemeType();
                return schemeType.equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) && formulaSchemeDefine.isDefault();
            }).collect(Collectors.toList());
            if (formulaSchemeDefines.isEmpty()) {
                return null;
            }
            return ((FormulaSchemeDefine)formulaSchemeDefines.get(0)).getKey();
        }
        return null;
    }
}

