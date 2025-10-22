/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyDestCopyCondition;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyQueryInfo;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyRowData;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyRowDataAnalyser;
import com.jiuqi.nr.function.func.floatcopy.FloatCopySrcQueryCondition;
import com.jiuqi.nr.function.func.floatcopy.FloatCopySumedRowData;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FloatCopyExecutor {
    private static final Logger logger = LogFactory.getLogger(FloatCopyExecutor.class);
    protected FloatCopySrcQueryCondition queryCondition;
    protected FloatCopyDestCopyCondition copyCondition;
    protected String assignExp;
    private QueryContext qContext;
    private IReportFunction function;

    public FloatCopyExecutor(FloatCopyParaParser parser, IReportFunction function) {
        this.queryCondition = parser.getQueryCondition();
        this.copyCondition = parser.getCopyCondition();
        this.assignExp = parser.getAssignExp();
        this.function = function;
    }

    public boolean execute(QueryContext qContext) throws Exception {
        this.qContext = qContext;
        DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
        List<DimensionValueSet> vsList = this.queryCondition.getDimValueSetList(qContext.getExeContext(), currValueSet);
        List<FloatCopyRowData> srcRowDatas = this.getSrcRowDatas(qContext, vsList);
        IDataQuery destRequest = this.createDataQuery(qContext, this.copyCondition.getRow(), currValueSet, this.getDestRegionFilter(qContext));
        IDataTable destResult = destRequest.executeQuery(qContext.getExeContext());
        int destCount = destResult.getCount();
        IMonitor monitor = qContext.getMonitor();
        if (monitor != null && monitor.isDebug()) {
            this.tableModelInfoToMonitor(destResult.getFieldsInfo(), this.copyCondition.getRow(), monitor, "\u76ee\u6807");
        }
        this.clearDest(destResult, this.copyCondition.isClearBeforeCopy());
        if (monitor != null && monitor.isDebug()) {
            if (this.copyCondition.isClearBeforeCopy()) {
                monitor.message("\u5199\u6570\u636e\u524d\u6e05\u8868\u8bbe\u7f6e\u4e3atrue\uff0c\u5df2\u6e05\u9664\u76ee\u6807\u8868" + destCount + "\u6761\u8bb0\u5f55", (Object)this.function);
            } else {
                monitor.message("\u5199\u6570\u636e\u524d\u6e05\u8868\u8bbe\u7f6e\u4e3afalse\uff0c\u4e0d\u6e05\u9664\u76ee\u6807\u8868\u8bb0\u5f55", (Object)this.function);
            }
            monitor.message("\u62f7\u8d1d\u6a21\u5f0f\uff1a" + this.copyCondition.getCopyModeExplain(), (Object)this.function);
            monitor.message("\u539f\u6620\u5c04\u8868\u8fbe\u5f0f\uff1a" + this.assignExp, (Object)this.function);
            ReportFormulaParser parser = qContext.getFormulaParser();
            FormulaShowInfo showInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
            String[] exps = this.assignExp.split(";");
            QueryContext srcQueryContext = FuncExpressionParseUtil.createSrcQueryContext(qContext, this.queryCondition.getRow().getReportName(), this.queryCondition.getLinkedAlias());
            for (int i = 0; i < exps.length; ++i) {
                String exp = exps[i];
                StringBuilder expBuff = new StringBuilder();
                expBuff.append(exp).append(" -> ");
                String parsedAssginExp = this.queryCondition.getRow().getParsedAssginExps().get(i);
                IExpression expNode = srcQueryContext.getFormulaParser().parseEval(parsedAssginExp, (IContext)srcQueryContext);
                int leftType = expNode.getType((IContext)qContext);
                expNode.interpret((IContext)qContext, expBuff, Language.FORMULA, (Object)showInfo);
                expBuff.append("=");
                parsedAssginExp = this.copyCondition.getRow().getParsedAssginExps().get(i);
                expNode = parser.parseEval(parsedAssginExp, (IContext)qContext);
                int rightType = expNode.getType((IContext)qContext);
                expNode.interpret((IContext)qContext, expBuff, Language.FORMULA, (Object)showInfo);
                expBuff.append("\uff1a");
                expBuff.append(DataTypes.toString((int)leftType)).append("=").append(DataTypes.toString((int)rightType));
                monitor.message(expBuff.toString(), (Object)this.function);
            }
        }
        List<FloatCopyRowData> destRowDatas = this.queryRowDatas(this.copyCondition.getRow(), destResult);
        return this.copy(srcRowDatas, destRowDatas, destResult, this.copyCondition.getRow().getQueryColumns());
    }

    private String getDestRegionFilter(QueryContext qContext) {
        String destRegionFilter = null;
        IDataModelLinkFinder dataModelLinkFinder = qContext.getExeContext().getEnv().getDataModelLinkFinder();
        destRegionFilter = dataModelLinkFinder.getRegionCondition(qContext.getExeContext(), this.copyCondition.getRow().getRegionKey());
        return destRegionFilter;
    }

    protected void clearDest(IDataTable result, boolean clearBeforeCopy) throws Exception {
        if (!clearBeforeCopy) {
            return;
        }
        LogHelper.info((String)"\u8fd0\u7b97\u5ba1\u6838", (String)"floatCopy\u51fd\u6570", (String)("\u6309\u4e3b\u7ef4\u5ea6" + result.getMasterKeys() + "\u6e05\u9664\u6570\u636e\u8868" + this.copyCondition.getRow().getTableInfo().getTableModelDefine().getName() + "\u7684\u8bb0\u5f55"));
        result.deleteAll();
        result.commitChanges(false);
    }

    protected IDataQuery createDataQuery(QueryContext qContext, FloatCopyQueryInfo row, DimensionValueSet dimValueSet, String filter) {
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IDataQuery request = accessProvider.newDataQuery();
        for (String column : row.getQueryColumns()) {
            request.addExpressionColumn(column);
        }
        if (row.getFloatOrderField() != null) {
            request.addColumn(row.getFloatOrderField());
        }
        request.setQueryParam(qContext.getQueryParam());
        request.setDefaultGroupName(row.getReportName());
        request.setRowFilter(filter);
        request.setMasterKeys(dimValueSet);
        return request;
    }

    protected List<FloatCopyRowData> queryRowDatas(FloatCopyQueryInfo row, IDataTable result) throws DataTypeException {
        ArrayList<FloatCopyRowData> rowList = new ArrayList<FloatCopyRowData>();
        for (int i = 0; i < result.getCount(); ++i) {
            IDataRow dataRow = result.getItem(i);
            FloatCopyRowData rowData = new FloatCopyRowData(row.getQueryColumns().size());
            rowData.setRowKey(dataRow.getRowKeys());
            StringBuffer keyDataStr = new StringBuffer();
            List<Integer> keyColumns = row.getKeyColumns();
            for (int col : keyColumns) {
                String value = dataRow.getAsString(col);
                if (value == null) continue;
                keyDataStr.append(value).append("#");
            }
            if (keyDataStr.length() == 0) continue;
            rowData.setKeyColumnValue(keyDataStr.toString());
            for (int j = 0; j < row.getQueryColumns().size(); ++j) {
                rowData.setValue(j, dataRow.getValue(j));
            }
            rowList.add(rowData);
        }
        return rowList;
    }

    protected List<FloatCopyRowData> getSrcRowDatas(QueryContext qContext, List<DimensionValueSet> vsList) throws Exception {
        IMonitor monitor;
        ExecutorContext destExeContext;
        ArrayList<FloatCopyRowData> rowDatas = new ArrayList<FloatCopyRowData>();
        ExecutorContext srcExeContext = destExeContext = qContext.getExeContext();
        IFieldsInfo fieldsInfo = null;
        FloatCopyQueryInfo queryInfo = this.queryCondition.getRow();
        if (this.queryCondition.getLinkedAlias() != null) {
            srcExeContext = new ExecutorContext(destExeContext.getRuntimeController());
            ReportFmlExecEnvironment destEnv = (ReportFmlExecEnvironment)destExeContext.getEnv();
            String destUnitEntitId = destEnv.getEntityViewDefine(destExeContext, destExeContext.getUnitDimension()).getEntityId();
            TaskLinkDefine linkDefine = destEnv.getController().queryTaskLinkByCurrentFormSchemeAndNumber(destEnv.getFormSchemeKey(), this.queryCondition.getLinkedAlias());
            ReportFmlExecEnvironment srcEnv = new ReportFmlExecEnvironment(destEnv.getController(), destEnv.getRuntimeController(), destEnv.getEntityViewRunTimeController(), linkDefine.getRelatedFormSchemeKey());
            srcExeContext.setEnv((IFmlExecEnvironment)srcEnv);
            TaskLinkEntityMappingRule relatedEntity = linkDefine.getRelatedEntity(destUnitEntitId);
            if (relatedEntity != null) {
                srcExeContext.setCurrentEntityId(relatedEntity.getSourceEntity());
            }
            for (DimensionValueSet dv : vsList) {
                IDataQuery request = this.createDataQuery(qContext, queryInfo, dv, this.queryCondition.getFilter());
                this.setOrderBy(srcExeContext, request);
                IDataTable result = request.executeQuery(srcExeContext);
                if (fieldsInfo == null) {
                    fieldsInfo = result.getFieldsInfo();
                }
                List<FloatCopyRowData> rows = this.queryRowDatas(queryInfo, result);
                for (FloatCopyRowData row : rows) {
                    DimensionValueSet rowKey = row.getRowKey();
                    Object srcUnitKeyValue = rowKey.getValue(this.queryCondition.getSrcUnitDimension());
                    Object destUnitKeyValue = this.queryCondition.getSrcDestUnitKeyMap().get(srcUnitKeyValue.toString());
                    if (destUnitKeyValue == null) continue;
                    rowKey.clearValue(this.queryCondition.getSrcUnitDimension());
                    rowKey.setValue(this.queryCondition.getDestUnitDimension(), destUnitKeyValue);
                }
                rowDatas.addAll(rows);
            }
        } else {
            for (DimensionValueSet dv : vsList) {
                IDataQuery request = this.createDataQuery(qContext, queryInfo, dv, this.queryCondition.getFilter());
                this.setOrderBy(srcExeContext, request);
                IDataTable result = request.executeQuery(srcExeContext);
                fieldsInfo = result.getFieldsInfo();
                rowDatas.addAll(this.queryRowDatas(queryInfo, result));
            }
        }
        if ((monitor = qContext.getMonitor()) != null && monitor.isDebug()) {
            ReportFmlExecEnvironment srcEnv = (ReportFmlExecEnvironment)srcExeContext.getEnv();
            monitor.message("\u6765\u6e90\u4efb\u52a1: " + srcEnv.getTaskDefine().getTitle(), (Object)this.function);
            monitor.message("\u6765\u6e90\u62a5\u8868\u65b9\u6848: " + srcEnv.getFormSchemeDefine().getTitle(), (Object)this.function);
            monitor.message("\u6765\u6e90\u62a5\u8868: " + queryInfo.getReportName() + "|" + queryInfo.getReportTitle(), (Object)this.function);
            monitor.message("\u6765\u6e90\u53d6\u6570\u671f\u6570: " + this.queryCondition.getPeriodCount(), (Object)this.function);
            monitor.message("\u6765\u6e90\u53d6\u6570\u65f6\u671f: " + this.queryCondition.getPeriodList(), (Object)this.function);
            monitor.message("\u6765\u6e90\u53d6\u6570\u5355\u4f4d: " + this.queryCondition.getSrcDestUnitKeyMap().keySet(), (Object)this.function);
            String queryType = "\u6765\u6e90";
            this.tableModelInfoToMonitor(fieldsInfo, queryInfo, monitor, queryType);
            StringBuilder countInfo = new StringBuilder();
            if (StringUtils.isNotEmpty((String)this.queryCondition.getFilter())) {
                countInfo.append("\u6ee1\u8db3\u53d6\u6570\u6761\u4ef6").append(this.queryCondition.getFilter()).append("\u7684");
            }
            countInfo.append("\u6765\u6e90\u8bb0\u5f55\u5171\u6709").append(rowDatas.size()).append("\u6761");
            monitor.message(countInfo.toString(), (Object)this.function);
        }
        return rowDatas;
    }

    private void tableModelInfoToMonitor(IFieldsInfo fieldsInfo, FloatCopyQueryInfo queryInfo, IMonitor monitor, String queryType) {
        StringBuilder keyBuff = this.getKeyBuff(fieldsInfo, queryInfo);
        monitor.message(queryType + "\u6807\u8bc6\u5217: " + keyBuff.toString(), (Object)this.function);
        TableModelRunInfo tableInfo = queryInfo.getTableInfo();
        String keyType = tableInfo.getDimensions().contains("RECORDKEY") ? "\u5141\u8bb8\u91cd\u7801" : "\u4e0d\u5141\u8bb8\u91cd\u7801";
        monitor.message(queryType + "\u5b58\u50a8\u8868\uff1a" + tableInfo.getTableModelDefine().getCode() + "|" + tableInfo.getTableModelDefine().getTitle() + ", " + keyType, (Object)this.function);
        monitor.message(queryType + "\u5b58\u50a8\u8868\u4e3b\u952e\uff1a" + this.getTableDimBuff(tableInfo).toString(), (Object)this.function);
    }

    private StringBuilder getKeyBuff(IFieldsInfo fieldsInfo, FloatCopyQueryInfo queryInfo) {
        StringBuilder keyBuff = new StringBuilder();
        ArrayList<String> fieldTitles = new ArrayList<String>();
        for (int i = 0; i < queryInfo.getKeyColumns().size(); ++i) {
            int keyIndex = queryInfo.getKeyColumns().get(i);
            keyBuff.append(queryInfo.getKeyColumnExps().get(i));
            keyBuff.append(";");
            fieldTitles.add(fieldsInfo.getFieldDefine(keyIndex).getTitle());
        }
        keyBuff.setLength(keyBuff.length() - 1);
        keyBuff.append(" -> ").append(fieldTitles);
        return keyBuff;
    }

    private StringBuilder getTableDimBuff(TableModelRunInfo tableInfo) {
        StringBuilder keyBuff = new StringBuilder();
        List dimFields = tableInfo.getDimFields();
        for (ColumnModelDefine dimField : dimFields) {
            keyBuff.append(dimField.getCode()).append("|").append(dimField.getTitle()).append(",");
        }
        keyBuff.setLength(keyBuff.length() - 1);
        return keyBuff;
    }

    public void setOrderBy(ExecutorContext srcExeContext, IDataQuery request) {
        if (StringUtils.isNotEmpty((String)this.queryCondition.getSortingInfoList())) {
            try {
                String sortingInfoList = this.queryCondition.getSortingInfoList();
                String[] sortingInfos = sortingInfoList.split(";");
                for (int i = 0; i < sortingInfos.length; ++i) {
                    String sortingInfo = sortingInfos[i];
                    boolean desc = false;
                    String fieldKey = "";
                    if (sortingInfo.endsWith("+")) {
                        desc = false;
                        fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
                    } else if (sortingInfo.endsWith("-")) {
                        desc = true;
                        fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
                    } else {
                        fieldKey = sortingInfo;
                    }
                    FieldDefine orderField = srcExeContext.getCache().getDataDefinitionsCache().findField(fieldKey);
                    request.addOrderByItem(orderField, desc);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        request.addOrderByItem(this.queryCondition.getRow().getFloatOrderField(), false);
    }

    protected boolean copy(List<FloatCopyRowData> srcRowDatas, List<FloatCopyRowData> destRowDatas, IDataTable destResult, List<String> destQueryCols) throws Exception {
        String copyMode = this.copyCondition.getCopyMode();
        int destColSize = destQueryCols.size();
        if (copyMode.equals("false") || copyMode.equals("0")) {
            this.list(srcRowDatas, destRowDatas, destResult, destColSize);
        } else {
            List<FloatCopyRowData> newSrcRowDatas;
            Map<String, List<FloatCopyRowData>> classifyMap;
            FloatCopyRowDataAnalyser analyser = new FloatCopyRowDataAnalyser(srcRowDatas, destRowDatas, copyMode);
            if (copyMode.equals("true") || copyMode.equals("1") || copyMode.equals("4") || copyMode.equals("9")) {
                this.sumByKey(destResult, destQueryCols, destColSize, srcRowDatas, destRowDatas);
            } else if (copyMode.equals("8")) {
                classifyMap = this.getClassifyMap(srcRowDatas);
                newSrcRowDatas = this.buildSrcRowDatas(classifyMap);
                analyser = new FloatCopyRowDataAnalyser(newSrcRowDatas, destRowDatas, copyMode);
                this.update(destResult, destQueryCols, destColSize, analyser.getUpdatedDataMap());
            } else if (copyMode.equals("2") || copyMode.equals("3")) {
                this.update(destResult, destQueryCols, destColSize, analyser.getUpdatedDataMap());
                this.append(destResult, destQueryCols, destColSize, analyser.getUnMatchedSrcDatas());
            } else if (copyMode.equals("5")) {
                this.updateNV1(destResult, destQueryCols, destColSize, analyser.getUpdatedDataMapNV1());
            } else if (copyMode.equals("6") || copyMode.equals("7")) {
                classifyMap = this.getClassifyMap(srcRowDatas);
                newSrcRowDatas = this.buildSrcRowDatas(classifyMap);
                analyser = new FloatCopyRowDataAnalyser(newSrcRowDatas, destRowDatas, copyMode);
                this.update(destResult, destQueryCols, destColSize, analyser.getUpdatedDataMap());
                this.append(destResult, destQueryCols, destColSize, analyser.getUnMatchedSrcDatas());
            }
            if (copyMode.equals("3") || copyMode.equals("4") || copyMode.equals("7")) {
                List<FloatCopyRowData> toDelDestDatas = analyser.getUnMatchedDestDatas();
                for (FloatCopyRowData rowData : toDelDestDatas) {
                    destResult.deleteRow(rowData.getRowKey());
                }
            }
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
        return true;
    }

    protected List<FloatCopyRowData> buildSrcRowDatas(Map<String, List<FloatCopyRowData>> classifyMap) {
        ArrayList<FloatCopyRowData> newSrcRowDatas = new ArrayList<FloatCopyRowData>(classifyMap.size());
        for (String key : classifyMap.keySet()) {
            List<FloatCopyRowData> tempList = classifyMap.get(key);
            FloatCopyRowData first = tempList.get(0);
            FloatCopyRowData rowData = new FloatCopyRowData(first.size());
            rowData.setKeyColumnValue(key);
            rowData.setRowKey(first.getRowKey());
            StatUnit[] sumDatas = new StatUnit[first.size()];
            for (int i = 0; i < first.size(); ++i) {
                sumDatas[i] = StatItem.createStatUnit((int)first.getValue((int)i).dataType);
            }
            for (int col = 0; col < first.size(); ++col) {
                StatUnit sumData = sumDatas[col];
                for (int i = 0; i < tempList.size(); ++i) {
                    AbstractData colData = tempList.get(i).getValue(col);
                    sumData.statistic(colData);
                }
                rowData.setValue(col, sumData.getResult());
            }
            newSrcRowDatas.add(rowData);
        }
        return newSrcRowDatas;
    }

    protected Map<String, List<FloatCopyRowData>> getClassifyMap(List<FloatCopyRowData> srcRowDatas) {
        HashMap<String, List<FloatCopyRowData>> classifyMap = new HashMap<String, List<FloatCopyRowData>>();
        for (FloatCopyRowData srcRowData : srcRowDatas) {
            String key = srcRowData.getKeyColumnValue();
            if (classifyMap.containsKey(key)) {
                ((List)classifyMap.get(key)).add(srcRowData);
                continue;
            }
            ArrayList<FloatCopyRowData> list = new ArrayList<FloatCopyRowData>();
            list.add(srcRowData);
            classifyMap.put(key, list);
        }
        return classifyMap;
    }

    protected void append(IDataTable destResult, List<String> destQueryCols, int destColSize, List<FloatCopyRowData> newDataList) throws IncorrectQueryException, DataTypeException {
        for (FloatCopyRowData srcRow : newDataList) {
            IDataRow newDataRow = destResult.appendRow(this.getDestRowKey(srcRow.getRowKey()));
            if (newDataRow.getFieldsInfo().getFieldCount() == destColSize + 1) {
                newDataRow.setValue(destColSize, (Object)(destResult.getCount() * 1000));
            }
            this.copyRow(newDataRow, destColSize, srcRow);
        }
    }

    protected void sumByKey(IDataTable destResult, List<String> destQueryCols, int destColSize, List<FloatCopyRowData> srcRowDatas, List<FloatCopyRowData> destRowDatas) throws IncorrectQueryException, DataTypeException {
        HashMap<String, DimensionValueSet> keyRowIdMap = new HashMap<String, DimensionValueSet>();
        HashMap<String, Integer> keyPositionMap = new HashMap<String, Integer>();
        for (FloatCopyRowData srcRow : srcRowDatas) {
            boolean isAppendRow = false;
            String key = srcRow.getKeyColumnValue();
            IDataRow destDataRow = null;
            if (keyRowIdMap.containsKey(key)) {
                DimensionValueSet rowId = (DimensionValueSet)keyRowIdMap.get(key);
                destDataRow = destResult.findRow(rowId);
            } else if (keyPositionMap.containsKey(key)) {
                Integer position = (Integer)keyPositionMap.get(key);
                destDataRow = destResult.getItem(position.intValue());
            } else {
                int rowIndex = this.findSameKeyDestRow(srcRow, destRowDatas);
                if (rowIndex >= 0) {
                    FloatCopyRowData destRow = destRowDatas.get(rowIndex);
                    destRowDatas.remove(rowIndex);
                    DimensionValueSet rowId = destRow.getRowKey();
                    keyRowIdMap.put(key, rowId);
                    destDataRow = destResult.findRow(rowId);
                } else {
                    destDataRow = destResult.appendRow(this.getDestRowKey(srcRow.getRowKey()));
                    isAppendRow = true;
                    int position = destResult.getCount() - 1;
                    keyPositionMap.put(key, position);
                }
            }
            if (isAppendRow) {
                if (destDataRow.getFieldsInfo().getFieldCount() == destColSize + 1) {
                    destDataRow.setValue(destColSize, (Object)(destResult.getCount() * 1000));
                }
                this.copyRow(destDataRow, destColSize, srcRow);
                continue;
            }
            this.accumulateRow(destDataRow, destQueryCols, destColSize, srcRow);
        }
    }

    protected int findSameKeyDestRow(FloatCopyRowData toCheck, List<FloatCopyRowData> datas) {
        if (datas == null || datas.size() <= 0) {
            return -1;
        }
        FloatCopyRowData rowData = null;
        for (int r = 0; r < datas.size(); ++r) {
            rowData = datas.get(r);
            if (!toCheck.getKeyColumnValue().equals(rowData.getKeyColumnValue()) || !this.isDataTypesSame(toCheck, rowData)) continue;
            return r;
        }
        return -1;
    }

    protected boolean isDataTypesSame(FloatCopyRowData toCheckColDatas, FloatCopyRowData tempColDatas) {
        for (int i = 0; i < toCheckColDatas.size(); ++i) {
            if (i >= tempColDatas.size()) continue;
            AbstractData toCheckData = toCheckColDatas.getValue(i);
            AbstractData tempData = tempColDatas.getValue(i);
            if (toCheckData == null || tempData == null || DataType.isCompatible((int)toCheckData.dataType, (int)tempData.dataType)) continue;
            return false;
        }
        return true;
    }

    protected void list(List<FloatCopyRowData> srcRowDatas, List<FloatCopyRowData> destRowDatas, IDataTable destResult, int destColSize) throws IncorrectQueryException, DataTypeException {
        int srcRowsize = srcRowDatas.size();
        int destRowSize = destRowDatas.size();
        IFieldValueUpdateProcessor fieldValueUpdateProcessor = this.qContext.getExeContext().getEnv().getFieldValueUpdateProcessor();
        for (int i = 0; i < srcRowsize; ++i) {
            FloatCopyRowData srcRowData = srcRowDatas.get(i);
            int newFloatOrder = (destRowSize + i) * 1000;
            IDataRow dataRow = destResult.appendRow(this.getDestRowKey(srcRowData.getRowKey()));
            for (int j = 0; j < destColSize; ++j) {
                FieldDefine fieldDefine;
                Object value = srcRowData.getValue(j).getAsObject();
                if (fieldValueUpdateProcessor != null && (fieldDefine = dataRow.getFieldsInfo().getFieldDefine(j)) != null) {
                    value = fieldValueUpdateProcessor.processValue(this.qContext, fieldDefine, value);
                }
                dataRow.setValue(j, value);
            }
            if (dataRow.getFieldsInfo().getFieldCount() != destColSize + 1) continue;
            dataRow.setValue(destColSize, (Object)newFloatOrder);
        }
    }

    protected void merge(IDataTable destResult, List<String> destQueryCols, int destColSize, FloatCopyRowDataAnalyser analyser) throws IncorrectQueryException, DataTypeException {
        IDataRow destDataRow;
        Map<FloatCopyRowData, FloatCopySumedRowData> matchedRowDatas = analyser.getSumedDataMap();
        for (FloatCopyRowData destRow : matchedRowDatas.keySet()) {
            FloatCopySumedRowData srcRow = matchedRowDatas.get(destRow);
            destDataRow = destResult.findRow(destRow.getRowKey());
            this.sumSrcRowToDestRow(destDataRow, destQueryCols, destColSize, destRow, srcRow);
        }
        List<FloatCopyRowData> unmatchedDatas = analyser.getUnMatchedSrcDatas();
        if (unmatchedDatas != null) {
            for (FloatCopyRowData row : unmatchedDatas) {
                destDataRow = destResult.appendRow(this.getDestRowKey(row.rowKey));
                if (destDataRow.getFieldsInfo().getFieldCount() == destColSize + 1) {
                    destDataRow.setValue(destColSize, (Object)(destResult.getCount() * 1000));
                }
                this.copyRow(destDataRow, destColSize, row);
            }
        }
    }

    protected void copyRow(IDataRow dataRow, int colSizes, FloatCopyRowData row) throws DataTypeException {
        IFieldValueUpdateProcessor fieldValueUpdateProcessor = this.qContext.getExeContext().getEnv().getFieldValueUpdateProcessor();
        for (int j = 0; j < colSizes; ++j) {
            FieldDefine fieldDefine;
            Object value = row.getValue(j).getAsObject();
            if (fieldValueUpdateProcessor != null && (fieldDefine = dataRow.getFieldsInfo().getFieldDefine(j)) != null) {
                value = fieldValueUpdateProcessor.processValue(this.qContext, fieldDefine, value);
            }
            dataRow.setValue(j, value);
        }
    }

    protected void sumSrcRowToDestRow(IDataRow dataRow, List<String> destQueryCols, int destColSize, FloatCopyRowData destRow, FloatCopySumedRowData srcRow) {
        for (int j = 0; j < destColSize; ++j) {
            AbstractData destValue = destRow.getValue(j);
            srcRow.setValue(j, destValue);
            dataRow.setValue(j, (Object)srcRow.getValue(j));
        }
    }

    protected void accumulateRow(IDataRow dataRow, List<String> queryCols, int colSizes, FloatCopyRowData row) throws DataTypeException {
        FloatCopySumedRowData srcRow = new FloatCopySumedRowData(row);
        for (int j = 0; j < colSizes; ++j) {
            AbstractData destValue = dataRow.getValue(j);
            srcRow.setValue(j, destValue);
            dataRow.setValue(j, srcRow.getValue(j).getAsObject());
        }
    }

    protected void update(IDataTable destResult, List<String> destQueryCols, int destColSize, Map<FloatCopyRowData, FloatCopyRowData> matchedRowDatas) throws DataTypeException {
        for (FloatCopyRowData destRow : matchedRowDatas.keySet()) {
            FloatCopyRowData srcRow = matchedRowDatas.get(destRow);
            IDataRow dataRow = destResult.findRow(destRow.getRowKey());
            this.copyRow(dataRow, destColSize, srcRow);
        }
    }

    protected void updateNV1(IDataTable destResult, List<String> destQueryCols, int destColSize, Map<FloatCopyRowData, List<FloatCopyRowData>> matchedRowDatas) throws DataTypeException {
        for (Map.Entry<FloatCopyRowData, List<FloatCopyRowData>> entry : matchedRowDatas.entrySet()) {
            FloatCopyRowData srcRow = entry.getKey();
            List<FloatCopyRowData> matchedRowDataList = entry.getValue();
            for (FloatCopyRowData destRow : matchedRowDataList) {
                IDataRow dataRow = destResult.findRow(destRow.getRowKey());
                this.copyRow(dataRow, destColSize, srcRow);
            }
        }
    }

    private DimensionValueSet getDestRowKey(DimensionValueSet srcRowKey) {
        DimensionValueSet rowKey = new DimensionValueSet(srcRowKey);
        if (rowKey.hasValue("RECORDKEY")) {
            rowKey.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
        }
        return rowKey;
    }
}

