/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.data.engine.validation.CompareType
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpression
 *  com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  io.netty.util.internal.StringUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.datawarning.serviceimpl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.data.engine.validation.CompareType;
import com.jiuqi.nr.data.engine.validation.DataValidationExpression;
import com.jiuqi.nr.data.engine.validation.DataValidationExpressionFactory;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datawarning.dao.IWarningRow;
import com.jiuqi.nr.datawarning.dao.impl.WarningRowDefine;
import com.jiuqi.nr.datawarning.defines.DataWarnigScop;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningProperties;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import com.jiuqi.nr.datawarning.service.IDataWarningExecutor;
import com.jiuqi.nr.datawarning.service.IDataWarningTable;
import com.jiuqi.nr.datawarning.serviceimpl.DataWarningTable;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.common.QueryConst;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataWarningExecutor
implements IDataWarningExecutor {
    private static final Logger log = LoggerFactory.getLogger(DataWarningExecutor.class);
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    DataWarningIdentify identify;
    DataWarningType type = null;
    MemoryDataSet<FieldDefine> dataSet = new MemoryDataSet();
    Map<String, Integer> fieldIndex = new HashMap<String, Integer>();
    Map<Integer, DataWarningDefine> fieldWarningConfig = new HashMap<Integer, DataWarningDefine>();
    ArrayList<DataWarningDefine> allWarningConfigs = new ArrayList();
    Map<Integer, Map<String, List<String>>> fieldIndexWithpreIdAndFormula = new HashMap<Integer, Map<String, List<String>>>();
    Map<Integer, Map<String, JSONObject>> fieldIndexWithpreIdAndResult = new HashMap<Integer, Map<String, JSONObject>>();
    Map<String, Map<String, String>> duplicateFieldValues = new HashMap<String, Map<String, String>>();
    Map<String, DataWarningDefine> warnIdWithPreWarn;
    Map<String, Map<String, String>> warnIdWithTypeAndFormula = new HashMap<String, Map<String, String>>();
    Map<String, List<Object>> fieldCodeAndValue = new HashMap<String, List<Object>>();
    public Map<String, Integer> showedFieldsIndex;
    Map<String, String> expressAndWarnId = new HashMap<String, String>();
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    int totalCount = 0;
    QueryHelper helper = new QueryHelper();

    public DataWarningExecutor() {
        this.iRuntimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
    }

    @Override
    public void setIdentify(DataWarningIdentify identify) {
        this.identify = identify;
    }

    @Override
    public void setWarningType(DataWarningType type) {
        this.type = type;
    }

    @Override
    public int setField(List<FieldDefine> fieldList) {
        for (int i = 0; i < fieldList.size(); ++i) {
            FieldDefine field = fieldList.get(i);
            Column col = new Column(field.getCode(), field.getType().getValue());
            this.fieldIndex.put(field.getKey(), i);
            this.dataSet.getMetadata().addColumn(col);
        }
        return 0;
    }

    @Override
    public void setFieldValues(Map<String, List<Object>> fieldCodeWithValue) {
        this.fieldCodeAndValue = fieldCodeWithValue;
        for (String fieldCode : this.fieldIndex.keySet()) {
            int index = this.fieldIndex.get(fieldCode);
            List<Object> values = fieldCodeWithValue.get(fieldCode);
            for (int row = 0; row < values.size(); ++row) {
                Object value = values.get(row);
                DataRow drow = null;
                if (this.dataSet.size() > row) {
                    drow = this.dataSet.get(row);
                }
                if (drow == null) {
                    drow = this.dataSet.add();
                }
                drow.setValue(index, value);
            }
        }
    }

    @Override
    public void setWarnigItems(List<DataWarningDefine> values) {
        this.warnIdWithPreWarn = new LinkedHashMap<String, DataWarningDefine>();
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        for (int i = 0; i < values.size(); ++i) {
            DataWarningDefine warnDefine = values.get(i);
            if (!warnDefine.getProperty().getPreIsEnable().booleanValue()) continue;
            if (this.fieldIndex.containsKey(warnDefine.getFieldCode())) {
                int index = this.fieldIndex.get(warnDefine.getFieldCode());
                this.handlePreWarnMes(warnDefine, index);
            } else if ("\u516c\u5f0f\u6761\u4ef6".equals(warnDefine.getProperty().getPreFieldLabel())) {
                this.expressAndWarnId.put(warnDefine.getProperty().getFieldFormulaInput(), warnDefine.getId());
            }
            this.warnIdWithPreWarn.put(warnDefine.getId(), warnDefine);
        }
    }

    private void handlePreWarnMes(DataWarningDefine warnDefine, int FieldIndex) {
        DataWarningProperties preWarn = warnDefine.getProperty();
        try {
            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(preWarn.getFieldCode());
            if (field == null) {
                if ("CELL".equals(warnDefine.getWarnType().toString())) {
                    this.expressAndWarnId.put(this.getCustomWarnFormula(warnDefine), warnDefine.getId());
                    return;
                }
                if ("ICON".equals(warnDefine.getWarnType().toString())) {
                    this.expressAndWarnId.put(this.getCustomWarnFormula(warnDefine), warnDefine.getId());
                    return;
                }
            }
            String expression = "";
            DataWarningType type = warnDefine.getWarnType();
            JSONObject list = new JSONObject();
            switch (type) {
                case CELL: {
                    if (!preWarn.getPreFieldLabel().equals("\u6307\u6807\u6570\u503c")) {
                        this.expressAndWarnId.put(warnDefine.getProperty().getFieldFormulaInput(), warnDefine.getId());
                        return;
                    }
                    expression = this.creatExpressionByPreWarn(field, preWarn.getFieldCompareValue(), preWarn.getFieldCompareInput(), preWarn);
                    this.putFormulaInMap(FieldIndex, warnDefine.getId(), expression);
                    break;
                }
                case ICON: {
                    if (preWarn.getPreFieldLabel().equals("\u6307\u6807\u6570\u503c")) {
                        list.put("preWarnType", (Object)"ICON");
                        List<String> compareList = preWarn.getFieldIconCompareList();
                        List<Integer> valueList = preWarn.getIconInputList();
                        int index = compareList.size();
                        for (int i = 0; i < index; ++i) {
                            String curValue;
                            FilterSymbols current;
                            String curExpression = "";
                            if (i == 0) {
                                current = FilterSymbols.valueOf(compareList.get(i));
                                curValue = String.valueOf(valueList.get(i));
                                curExpression = this.creatExpressionByPreWarn(field, current, curValue, preWarn);
                                expression = this.handlingFormula(curExpression, "", Integer.toString(i), expression);
                                continue;
                            }
                            current = FilterSymbols.valueOf(compareList.get(i));
                            curValue = String.valueOf(valueList.get(i));
                            curExpression = this.creatExpressionByPreWarn(field, current, curValue, preWarn);
                            FilterSymbols parent = FilterSymbols.getAntonym(compareList.get(i - 1));
                            String parentValue = String.valueOf(valueList.get(i - 1));
                            String preExpression = this.creatExpressionByPreWarn(field, parent, parentValue, preWarn);
                            expression = this.handlingFormula(curExpression, preExpression, Integer.toString(i), expression);
                            if (i + 1 != index) continue;
                            FilterSymbols lastSymbols = FilterSymbols.getAntonym(compareList.get(i));
                            String lasterValue = String.valueOf(valueList.get(i));
                            String lastExpression = this.creatExpressionByPreWarn(field, lastSymbols, lasterValue, preWarn);
                            expression = this.handlingFormula(lastExpression, "", Integer.toString(i + 1), expression);
                        }
                    } else {
                        this.expressAndWarnId.put(warnDefine.getProperty().getFieldFormulaInput(), warnDefine.getId());
                        return;
                    }
                    this.putFormulaInMap(FieldIndex, warnDefine.getId(), expression);
                    break;
                }
                case BAR: {
                    list.put("preWarnType", (Object)"BAR");
                    this.handleminMaxValue(warnDefine, FieldIndex, "bar", list);
                    String barFillModel = preWarn.getBarFillModel();
                    String barBorderModel = preWarn.getBarBorderModel();
                    String barFillColor = preWarn.getBarFillColor();
                    String barBorderColor = preWarn.getBarBorderColor();
                    String barDiretionModel = preWarn.getBarDirection();
                    Boolean barDisplayModel = preWarn.isOnlyShowBar();
                    list.put("barFillModel", (Object)barFillModel);
                    list.put("barFillColor", (Object)barFillColor);
                    list.put("barBorderModel", (Object)barBorderModel);
                    list.put("barBorderColor", (Object)barBorderColor);
                    list.put("barDiretionModel", (Object)barDiretionModel);
                    list.put("isOnlyShowBar", (Object)barDisplayModel.toString());
                    list.put("preIsEnable", (Object)preWarn.getPreIsEnable().toString());
                    list.put("negativefillColorRadio", (Object)preWarn.getNegativefillColorRadio());
                    list.put("negativefillColorValue", (Object)preWarn.getNegativefillColorValue());
                    list.put("negativeBorderColorRadio", (Object)preWarn.getNegativeBorderColorRadio());
                    list.put("negativeBorderColorValue", (Object)preWarn.getNegativeBorderColorValue());
                    list.put("axisSettingPosRadio", (Object)preWarn.getAxisSettingPosRadio());
                    list.put("axisColorValue", (Object)preWarn.getAxisColorValue());
                    list.put("negativeHiddenSettingModel", (Object)preWarn.getNegativeHiddenSettingModel().toString());
                    break;
                }
                case TWOCOLOR: {
                    list.put("preWarnType", (Object)"TWOCOLOR");
                    this.handleminMaxValue(warnDefine, FieldIndex, "two", list);
                    break;
                }
                case THREECOLOR: {
                    list.put("preWarnType", (Object)"THREECOLOR");
                    this.handleminMaxValue(warnDefine, FieldIndex, "three", list);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void handleminMaxValue(DataWarningDefine warnDefine, int fieldIndex, String type, JSONObject list) {
        DataWarningProperties preWarn = warnDefine.getProperty();
        String fieldCode = warnDefine.getFieldCode();
        String minBarValue = preWarn.getMinBarModel();
        String maxBarValue = preWarn.getMaxBarModel();
        String middleBarValue = preWarn.getMiddleBarModel();
        String minColor = preWarn.getFirstGradualChangeColor();
        String maxColor = preWarn.getSecondGradualChangeColor();
        String middleColor = null;
        LinkedHashMap<String, String> typeAndFormula = new LinkedHashMap<String, String>();
        Map<Object, Object> warnIdWithResult = new LinkedHashMap<String, JSONObject>();
        List<Object> data = this.fieldCodeAndValue.get(warnDefine.getFieldCode());
        Double maxValue = 0.0;
        Double minValue = 0.0;
        if (data != null) {
            for (int i = 0; i < data.size(); ++i) {
                Double cur = Double.valueOf(data.get(i) == null ? "0.0" : data.get(i).toString());
                if (maxValue < cur) {
                    maxValue = cur;
                }
                if (minValue == 0.0) {
                    minValue = cur;
                    continue;
                }
                if (!(minValue > cur)) continue;
                minValue = cur;
            }
            if (type == "three") {
                middleColor = preWarn.getSecondGradualChangeColor();
                maxColor = preWarn.getThirdGradualChangeColor();
                list.put("middleColor", (Object)middleColor);
            }
            list.put("minColor", (Object)minColor);
            list.put("maxColor", (Object)maxColor);
            if (minBarValue != null && minBarValue.equals("percentage")) {
                Double minPercent = Double.valueOf(preWarn.getMinBarInputModel()) / 100.0;
                list.put("minValue", (Object)String.valueOf(maxValue * minPercent));
            }
            if (maxBarValue != null && maxBarValue.equals("percentage")) {
                Double maxPercent = Double.valueOf(preWarn.getMaxBarInputModel()) / 100.0;
                list.put("maxValue", (Object)String.valueOf(maxValue * maxPercent));
            }
            if (minBarValue != null && minBarValue.equals("zbmin")) {
                list.put("minValue", (Object)minValue);
            }
            if (maxBarValue != null && maxBarValue.equals("zbmax")) {
                list.put("maxValue", (Object)maxValue);
            }
            if (minBarValue != null && minBarValue.equals("formula")) {
                typeAndFormula.put("minValue", preWarn.getMinBarInputModel());
                this.putFormulaInMap(fieldIndex, warnDefine.getId(), preWarn.getMinBarInputModel());
            }
            if (maxBarValue != null && maxBarValue.equals("formula")) {
                typeAndFormula.put("maxValue", preWarn.getMaxBarInputModel());
                this.putFormulaInMap(fieldIndex, warnDefine.getId(), preWarn.getMaxBarInputModel());
            }
            list.put("preIsEnable", (Object)preWarn.getPreIsEnable().toString());
            if (type == "three") {
                list.put("middleColor", (Object)middleColor);
                if (middleBarValue != null && middleBarValue.equals("percentage")) {
                    Double middlePercent = Double.valueOf(preWarn.getMiddleBarInputModel()) / 100.0;
                    list.put("middleValue", (Object)String.valueOf(maxValue * middlePercent));
                }
                if (middleBarValue != null && middleBarValue.equals("formula")) {
                    typeAndFormula.put("middleValue", middleBarValue);
                    this.putFormulaInMap(fieldIndex, warnDefine.getId(), middleBarValue);
                }
            }
            if (typeAndFormula.size() > 0) {
                this.warnIdWithTypeAndFormula.put(warnDefine.getId(), typeAndFormula);
            }
            if (!this.fieldIndexWithpreIdAndResult.containsKey(fieldIndex)) {
                warnIdWithResult.put(warnDefine.getId(), list);
                this.fieldIndexWithpreIdAndResult.put(fieldIndex, warnIdWithResult);
            } else {
                warnIdWithResult = this.fieldIndexWithpreIdAndResult.get(fieldIndex);
                if (warnIdWithResult.containsKey(warnDefine.getId())) {
                    JSONObject old = (JSONObject)warnIdWithResult.get(warnDefine.getId());
                    old = list;
                } else {
                    warnIdWithResult.put(warnDefine.getId(), list);
                }
            }
        }
    }

    private void putFormulaInMap(int fieldIndex, String preId, String formula) {
        Map<Object, Object> list = new LinkedHashMap();
        List<String> formulaList = new ArrayList<String>();
        if (this.fieldIndexWithpreIdAndFormula.containsKey(fieldIndex)) {
            list = this.fieldIndexWithpreIdAndFormula.get(fieldIndex);
            if (list.containsKey(preId)) {
                formulaList = (List)list.get(preId);
                formulaList.add(formula);
            } else {
                formulaList.add(formula);
                list.put(preId, formulaList);
            }
        } else {
            formulaList.add(formula);
            list.put(preId, formulaList);
            this.fieldIndexWithpreIdAndFormula.put(fieldIndex, list);
        }
    }

    private String creatExpressionByPreWarn(FieldDefine field, FilterSymbols type, String value, DataWarningProperties preWarn) {
        List deployInfoByDataFieldKeys = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
        if (deployInfoByDataFieldKeys.size() > 0) {
            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        }
        String expression = "";
        if (QueryHelper.isNumField(field)) {
            DataValidationExpression exp;
            switch (this.helper.getSymbol(type)) {
                case BETWEEN: 
                case NOT_BETWEEN: {
                    exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.helper.getSymbol(type), (Object)value, (Object)preWarn.getFieldCompareInputTwo());
                    break;
                }
                default: {
                    exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.helper.getSymbol(type), (Object)value);
                }
            }
            expression = exp.toFormula();
        } else {
            StringBuilder fieldStr = new StringBuilder();
            fieldStr.append(deployInfoByColumnKey.getTableName()).append("[").append(deployInfoByColumnKey.getFieldName()).append("]");
            String fieldCondition = fieldStr.toString();
            switch (type.toString()) {
                case "Start": 
                case "End": 
                case "Contain": {
                    expression = fieldCondition + "$ like '%" + value + "%' ";
                    break;
                }
                case "NotStart": 
                case "NotEnd": 
                case "NotContain": {
                    expression = "not (" + fieldCondition + "$ like '%" + value + "%' )";
                    break;
                }
                case "Eque": {
                    if ("FIELD_TYPE_DATE".equals(field.getType().toString())) {
                        expression = fieldCondition + "='" + value + "'";
                        break;
                    }
                    expression = fieldCondition + "$='" + value + "'";
                    break;
                }
                case "NotEque": {
                    if ("FIELD_TYPE_DATE".equals(field.getType().toString())) {
                        expression = "!(" + fieldCondition + " = '" + value + "')";
                        break;
                    }
                    expression = fieldCondition + "$<>'" + value + "'";
                    break;
                }
                case "MoreThan": {
                    if (!"FIELD_TYPE_DATE".equals(field.getType().toString())) break;
                    expression = fieldCondition + ">'" + value + "'";
                    break;
                }
                case "LessThan": {
                    if (!"FIELD_TYPE_DATE".equals(field.getType().toString())) break;
                    expression = fieldCondition + "<'" + value + "'";
                    break;
                }
                case "MoreAndEque": {
                    if (!"FIELD_TYPE_DATE".equals(field.getType().toString())) break;
                    expression = fieldCondition + ">='" + value + "'";
                    break;
                }
                case "LessAndEque": {
                    if (!"FIELD_TYPE_DATE".equals(field.getType().toString())) break;
                    expression = fieldCondition + "<='" + value + "'";
                    break;
                }
                case "Between": 
                case "NotBetween": {
                    if (!"FIELD_TYPE_DATE".equals(field.getType().toString())) break;
                    value = "'" + value + "'";
                    String value2 = "'" + preWarn.getFieldCompareInputTwo() + "'";
                    DataValidationExpression exp = DataValidationExpressionFactory.createExpression((FieldDefine)field, (CompareType)this.helper.getSymbol(type), (Object)value, (Object)value2);
                    expression = exp.toFormula();
                }
            }
        }
        return expression;
    }

    private String handlingFormula(String curExpression, String condition, String value, String expression) {
        String keyWord = "if ";
        String addExpression = "";
        if (!StringUtil.isNullOrEmpty((String)condition)) {
            addExpression = " and " + condition;
        }
        if (!StringUtil.isNullOrEmpty((String)expression)) {
            keyWord = " else if ";
        }
        expression = expression + keyWord + curExpression + addExpression + " then " + value;
        return expression;
    }

    public IDataRow getDetialRow(IGroupingTable dataTable, int j, IDataRow row) {
        if (row.getGroupingFlag() >= 0) {
            ++this.totalCount;
            row = dataTable.getItem(j + this.totalCount);
            row = this.getDetialRow(dataTable, j, row);
        }
        return row;
    }

    @Override
    public IDataWarningTable execute(IGroupingTable dataTable, Map<String, Integer> fieldCodeWithIndex) {
        IRunTimeViewController viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(viewController, this.dataDefinitionRuntimeController, entityViewRunTimeController, null);
        context.setEnv((IFmlExecEnvironment)environment);
        DataWarningTable table = new DataWarningTable();
        LinkedHashMap<Integer, Map<String, Map<DimensionValueSet, JSONObject>>> result = new LinkedHashMap<Integer, Map<String, Map<DimensionValueSet, JSONObject>>>();
        DataSetExprEvaluator evaluator = new DataSetExprEvaluator(this.dataSet);
        for (Integer fieldIndex : this.fieldIndexWithpreIdAndFormula.keySet()) {
            String formula = "";
            LinkedHashMap<String, LinkedHashMap<DimensionValueSet, JSONObject>> preIdWithRowAndResult = (LinkedHashMap<String, LinkedHashMap<DimensionValueSet, JSONObject>>)result.get(fieldIndex);
            if (preIdWithRowAndResult == null) {
                preIdWithRowAndResult = new LinkedHashMap<String, LinkedHashMap<DimensionValueSet, JSONObject>>();
            }
            Map<String, List<String>> ff = this.fieldIndexWithpreIdAndFormula.get(fieldIndex);
            for (String preId : ff.keySet()) {
                List<String> formulaList = ff.get(preId);
                DataWarningDefine pre = this.warnIdWithPreWarn.get(preId);
                try {
                    for (int i = 0; i < formulaList.size(); ++i) {
                        formula = formulaList.get(i);
                        evaluator.prepare(context, null, formula);
                        DimensionValueSet keyset = new DimensionValueSet();
                        for (int j = 0; j < this.dataSet.size(); ++j) {
                            IDataRow row = dataTable.getItem(j + this.totalCount);
                            row = this.getDetialRow(dataTable, j, row);
                            AbstractData value = evaluator.evaluate(this.dataSet.get(j));
                            LinkedHashMap<DimensionValueSet, JSONObject> rowIdWithResult = (LinkedHashMap<DimensionValueSet, JSONObject>)preIdWithRowAndResult.get(preId);
                            if (this.fieldIndexWithpreIdAndResult.containsKey(fieldIndex) && !pre.getWarnType().equals((Object)DataWarningType.CELL) && !pre.getWarnType().equals((Object)DataWarningType.ICON)) {
                                this.handlerResultValue(fieldIndex, preId, formula, value, row, rowIdWithResult);
                                continue;
                            }
                            JSONObject newjson = new JSONObject();
                            if (rowIdWithResult == null) {
                                rowIdWithResult = new LinkedHashMap<DimensionValueSet, JSONObject>();
                                newjson.put("result", value.getAsObject());
                                rowIdWithResult.put(row.getRowKeys(), newjson);
                            } else {
                                newjson.put("result", value.getAsObject());
                                rowIdWithResult.put(row.getRowKeys(), newjson);
                            }
                            preIdWithRowAndResult.put(preId, rowIdWithResult);
                        }
                        this.totalCount = 0;
                    }
                    if (preIdWithRowAndResult == null) continue;
                    result.put(fieldIndex, preIdWithRowAndResult);
                }
                catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                    LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)("\u9884\u8b66\u516c\u5f0f\uff1a\u2018" + formula + "\u2019,\u5ba1\u6838\u9519\u8bef" + ex.getMessage()));
                }
            }
        }
        try {
            this.handlerFormulaResult(result, table);
            this.handlerNoFormulaResult(dataTable, table);
            this.handlerExpressCondition(dataTable, table, fieldCodeWithIndex);
        }
        catch (Exception e) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)e.getMessage());
            log.error(e.getMessage(), e);
        }
        return table;
    }

    private void handlerExpressCondition(IGroupingTable dataTable, DataWarningTable table, Map<String, Integer> fieldCodeWithIndex) throws Exception {
        for (String express : this.expressAndWarnId.keySet()) {
            for (int i = 0; i < dataTable.getTotalCount(); ++i) {
                JSONObject list = new JSONObject();
                IDataRow row = dataTable.getItem(i);
                if (row.getGroupingFlag() >= 0) continue;
                int index = fieldCodeWithIndex.get(express);
                AbstractData value = row.getValue(index);
                list.put("result", value.getAsObject());
                this.commonMehthod(list, row.getRowKeys(), this.expressAndWarnId.get(express), table, -1, row);
            }
        }
    }

    private void handlerResultValue(int fieldIndex, String preId, String formula, AbstractData result, IDataRow row, Map<DimensionValueSet, JSONObject> rowIdWithResult) {
        Map<String, JSONObject> preIdAndResult = this.fieldIndexWithpreIdAndResult.get(fieldIndex);
        JSONObject resultList = new JSONObject();
        if (preIdAndResult.containsKey(preId)) {
            Map<String, String> typeAndFormula;
            resultList = preIdAndResult.get(preId);
            if (this.warnIdWithTypeAndFormula.containsKey(preId) && (typeAndFormula = this.warnIdWithTypeAndFormula.get(preId)).containsValue(formula)) {
                for (String key : typeAndFormula.keySet()) {
                    if (!typeAndFormula.get(key).equals(formula)) continue;
                    resultList.put(key, (Object)result.getAsString());
                    break;
                }
            }
        }
    }

    private void handlerNoFormulaResult(IGroupingTable dataTable, DataWarningTable table) throws Exception {
        for (Integer fieldIndex : this.fieldIndexWithpreIdAndResult.keySet()) {
            Map<String, JSONObject> preIdAndResult = this.fieldIndexWithpreIdAndResult.get(fieldIndex);
            for (String preId : preIdAndResult.keySet()) {
                JSONObject list = preIdAndResult.get(preId);
                for (int i = 0; i < dataTable.getTotalCount(); ++i) {
                    IDataRow rowData = dataTable.getItem(i);
                    this.commonMehthod(list, rowData.getRowKeys(), preId, table, fieldIndex, rowData);
                }
            }
        }
    }

    private void handlerFormulaResult(Map<Integer, Map<String, Map<DimensionValueSet, JSONObject>>> result, DataWarningTable table) throws Exception {
        for (Integer fieldIndex : result.keySet()) {
            Map<String, Map<DimensionValueSet, JSONObject>> preIdWithRowAndResult = result.get(fieldIndex);
            for (String preId : preIdWithRowAndResult.keySet()) {
                Map<DimensionValueSet, JSONObject> rowAndResult = preIdWithRowAndResult.get(preId);
                for (DimensionValueSet row : rowAndResult.keySet()) {
                    JSONObject list = rowAndResult.get(row);
                    this.commonMehthod(list, row, preId, table, fieldIndex, null);
                }
            }
        }
    }

    private void commonMehthod(JSONObject list, DimensionValueSet row, String preId, DataWarningTable table, Integer fieldIndex, IDataRow rowData) throws Exception {
        IWarningRow warRow;
        boolean isHasNewValue = false;
        JSONObject curList = new JSONObject();
        JSONObject warRowResult = new JSONObject();
        DataWarningDefine pre = this.warnIdWithPreWarn.get(preId);
        DataWarningProperties preWarn = pre.getProperty();
        Set<String> keyset = this.fieldCodeAndValue.keySet();
        String preFieldCode = pre.getFieldCode() != null && pre.getFieldCode().equals(pre.getFieldSettingCode()) ? pre.getFieldCode() : pre.getFieldSettingCode();
        String fieldCode = pre.getScop().equals((Object)DataWarnigScop.ROW) ? (String)keyset.stream().findFirst().get() : preFieldCode;
        List<IWarningRow> warRowList = table.getRowsByFieldCode(fieldCode, row);
        FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
        if (warRowList.size() > 0) {
            warRow = warRowList.get(0);
            warRowResult = warRow.getResultList();
            boolean bl = isHasNewValue = Integer.valueOf(warRow.getOrder()) <= Integer.valueOf(pre.getOrder());
            if (isHasNewValue) {
                warRow.setOrder(pre.getOrder());
                warRow.setWarnId(pre.getId());
                warRow.setFieldSettingCode(pre.getFieldSettingCode());
            } else if (list.has("preWarnType") && "BAR".equals(list.getString("preWarnType")) && warRowResult.has("preWarnType") && "ICON".equals(warRowResult.getString("preWarnType"))) {
                return;
            }
        } else {
            warRow = new WarningRowDefine(fieldIndex, row, pre, fieldCode);
            table.appendRow(warRow);
        }
        switch (pre.getWarnType()) {
            case CELL: {
                for (String key : list.keySet()) {
                    Object value = list.get(key);
                    boolean cellShowWarn = this.isWarn(preWarn.getPreFieldLabel(), preWarn.getIsFieldFormulaIsCheck(), value);
                    if (cellShowWarn) {
                        curList.put("preWarnType", (Object)"CELL");
                        curList.put("textColor", (Object)pre.getProperty().getPreviewTextColor());
                        curList.put("backGroundColor", (Object)pre.getProperty().getPreviewBackgroundColor());
                        curList.put("FontStyle", (Object)pre.getProperty().getPreviewTextFontStyle());
                        curList.put("FontWeight", (Object)pre.getProperty().getPreviewTextFontWeight());
                        this.isHasKey("preWarnType", "CELL", warRowResult, isHasNewValue);
                        this.isHasKey("textColor", pre.getProperty().getPreviewTextColor(), warRowResult, isHasNewValue);
                        this.isHasKey("backGroundColor", pre.getProperty().getPreviewBackgroundColor(), warRowResult, isHasNewValue);
                        this.isHasKey("FontStyle", pre.getProperty().getPreviewTextFontStyle(), warRowResult, isHasNewValue);
                        this.isHasKey("FontWeight", pre.getProperty().getPreviewTextFontWeight(), warRowResult, isHasNewValue);
                    }
                    warRow.setResultList(warRowResult);
                }
                break;
            }
            case ICON: {
                for (String key : list.keySet()) {
                    Object value = list.get(key);
                    String iconIndex = "";
                    boolean cellShowWarn = this.isWarn(preWarn.getPreFieldLabel(), preWarn.getIsFieldFormulaIsCheck(), value);
                    if (!cellShowWarn && !"\u6307\u6807\u6570\u503c".equals(preWarn.getPreFieldLabel())) continue;
                    curList.put("preWarnType", (Object)"ICON");
                    iconIndex = String.valueOf(value).isEmpty() && "\u516c\u5f0f\u6761\u4ef6".equals(preWarn.getPreFieldLabel()) && !preWarn.getIsFieldFormulaIsCheck() ? String.valueOf(preWarn.getIconInputList().get(0)) : String.valueOf(value);
                    if ("\u516c\u5f0f\u6761\u4ef6".equals(preWarn.getPreFieldLabel())) {
                        curList.put("iconIndex", (Object)String.valueOf(cellShowWarn));
                        this.isHasKey("iconIndex", String.valueOf(cellShowWarn), warRowResult, isHasNewValue);
                    } else {
                        curList.put("iconIndex", (Object)iconIndex);
                        this.isHasKey("iconIndex", String.valueOf(value), warRowResult, isHasNewValue);
                    }
                    curList.put("iconGroupIndex", (Object)pre.getProperty().getIconGroupValue());
                    this.isHasKey("preWarnType", "ICON", warRowResult, isHasNewValue);
                    this.isHasKey("iconGroupIndex", pre.getProperty().getIconGroupValue(), warRowResult, isHasNewValue);
                }
                warRow.setResultList(warRowResult);
                break;
            }
            case BAR: {
                AbstractData barData = null;
                if (field == null) {
                    Integer index = this.showedFieldsIndex.get(fieldCode);
                    barData = rowData.getValue(index.intValue());
                } else {
                    barData = rowData.getValue(field);
                }
                if (barData.isNull) break;
                JSONObject backGroudColor = new JSONObject();
                Object value = list.get("axisSettingPosRadio");
                Double x = 0.0;
                if (!"\u2014\u2014".equals(barData.getAsString())) {
                    x = Double.valueOf(barData.getAsString());
                }
                Double y = Double.valueOf(String.valueOf(list.get("minValue")));
                Double z = Double.valueOf(String.valueOf(list.get("maxValue")));
                Double xPosition = Math.abs(y) / (Math.abs(y) + Math.abs(z)) * 100.0;
                String cellTextStyle = "";
                String negaBarStyle = "";
                String barStyle = "";
                String axisStyle = "";
                cellTextStyle = cellTextStyle + "fontSize:11px;textAlign:right;position:absolute;right:0px;zIndex:9;";
                if (value.equals("auto")) {
                    backGroudColor.put("axis", (Object)xPosition);
                    if (x < 0.0) {
                        backGroudColor.put("negative", (0.0 - x) / (0.0 - y) * xPosition);
                    } else {
                        if (x <= y) {
                            x = 0.0;
                        }
                        backGroudColor.put("positive", x / z * (100.0 - xPosition));
                    }
                    if (x < 0.0) {
                        negaBarStyle = negaBarStyle + "width:" + Math.abs((0.0 - x) / (0.0 - y) * Math.abs(xPosition)) + "%;";
                        negaBarStyle = negaBarStyle + "height:92%;";
                        negaBarStyle = negaBarStyle + "position:absolute;";
                        negaBarStyle = negaBarStyle + "right:" + (100.0 - Math.abs(xPosition)) + "%;";
                        if (list.get("barFillModel").equals("solid")) {
                            if (list.get("negativefillColorRadio").equals("fill")) {
                                negaBarStyle = negaBarStyle + "background-color:" + list.get("negativefillColorValue").toString() + ";";
                            } else if (list.get("negativefillColorRadio").equals("samecolor")) {
                                negaBarStyle = negaBarStyle + "background-color:" + list.get("barFillColor").toString() + ";";
                            }
                        } else if (list.get("negativefillColorRadio").equals("fill")) {
                            negaBarStyle = negaBarStyle + "backgroundImage: linear-gradient(to left," + list.getString("negativefillColorValue") + ", white);";
                        } else if (list.get("negativefillColorRadio").equals("samecolor")) {
                            negaBarStyle = negaBarStyle + "backgroundImage: linear-gradient(to left," + list.getString("barFillColor") + ", white);";
                        }
                        if (list.get("negativeBorderColorRadio").equals("border")) {
                            negaBarStyle = negaBarStyle + "border:1px solid;";
                            negaBarStyle = negaBarStyle + "borderColor:" + list.get("negativeBorderColorValue").toString() + ";";
                        } else if (list.get("negativeBorderColorRadio").equals("samebordercolor")) {
                            if (list.get("barBorderModel").equals("NONE")) {
                                negaBarStyle = negaBarStyle + "borderWidth:1px;";
                                negaBarStyle = negaBarStyle + "borderColor:transparent;";
                            } else {
                                negaBarStyle = negaBarStyle + "border:1px solid;";
                                negaBarStyle = negaBarStyle + "borderColor:" + list.getString("barBorderColor") + ";";
                            }
                        }
                    } else {
                        barStyle = barStyle + "width:" + x / z * (100.0 - Math.abs(xPosition)) + "%;";
                        barStyle = barStyle + "height:92%;";
                        barStyle = list.get("barFillModel").equals("solid") ? barStyle + "background-color:" + list.getString("barFillColor") + ";" : barStyle + "backgroundImage: linear-gradient(to right," + list.getString("barFillColor") + ", white);";
                        if (list.get("barBorderModel").equals("NONE")) {
                            barStyle = barStyle + "borderWidth:1px;";
                            barStyle = barStyle + "borderColor:transparent;";
                        } else {
                            barStyle = barStyle + "borderWidth:1px solid;";
                            barStyle = barStyle + "borderColor:" + list.get("barBorderColor") + ";";
                        }
                        barStyle = barStyle + "position:absolute;";
                        barStyle = barStyle + "left:" + Math.abs(xPosition) + "%;";
                    }
                    axisStyle = axisStyle + "background-color:" + list.getString("axisColorValue") + ";";
                    axisStyle = axisStyle + "width:1px;height:100%;position:absolute;";
                    axisStyle = axisStyle + "left:" + Math.abs(xPosition) + "%;";
                } else if (value.equals("center")) {
                    if (x < 0.0) {
                        backGroudColor.put("negative", (0.0 - x) / (0.0 - y) * 0.5 * 100.0);
                    } else {
                        if (x <= y) {
                            x = 0.0;
                        }
                        backGroudColor.put("positive", x / z * 0.5 * 100.0);
                    }
                    if (x < 0.0) {
                        negaBarStyle = negaBarStyle + "width:" + Math.abs((0.0 - x) / (0.0 - y) * 0.5 * 100.0) + "%;";
                        negaBarStyle = negaBarStyle + "height:92%;";
                        negaBarStyle = negaBarStyle + "position:absolute;";
                        negaBarStyle = negaBarStyle + "right:50%;";
                        if (list.get("barFillModel").equals("solid")) {
                            if (list.get("negativefillColorRadio").equals("fill")) {
                                negaBarStyle = negaBarStyle + "background-color:" + list.get("negativefillColorValue").toString() + ";";
                            } else if (list.get("negativefillColorRadio").equals("samecolor")) {
                                negaBarStyle = negaBarStyle + "background-color:" + list.get("barFillColor").toString() + ";";
                            }
                        } else if (list.get("negativefillColorRadio").equals("fill")) {
                            negaBarStyle = negaBarStyle + "backgroundImage: linear-gradient(to left," + list.getString("negativefillColorValue") + ", white);";
                        } else if (list.get("negativefillColorRadio").equals("samecolor")) {
                            negaBarStyle = negaBarStyle + "backgroundImage: linear-gradient(to left," + list.getString("barFillColor") + ", white);";
                        }
                        if (list.get("negativeBorderColorRadio").equals("border")) {
                            negaBarStyle = negaBarStyle + "border:1px solid;";
                            negaBarStyle = negaBarStyle + "borderColor:" + list.get("negativeBorderColorValue").toString() + ";";
                        } else if (list.get("negativeBorderColorRadio").equals("samebordercolor")) {
                            if (list.get("barBorderModel").equals("NONE")) {
                                negaBarStyle = negaBarStyle + "borderWidth:1px;";
                                negaBarStyle = negaBarStyle + "borderColor:transparent;";
                            } else {
                                negaBarStyle = negaBarStyle + "border:1px solid;";
                                negaBarStyle = negaBarStyle + "borderColor:" + list.getString("barBorderColor") + ";";
                            }
                        }
                    } else {
                        barStyle = barStyle + "width:" + x / z * 0.5 * 100.0 + "%;";
                        barStyle = barStyle + "height:92%;";
                        barStyle = list.get("barFillModel").equals("solid") ? barStyle + "background-color:" + list.getString("barFillColor") + ";" : barStyle + "backgroundImage: linear-gradient(to right," + list.getString("barFillColor") + ", white);";
                        if (list.get("barBorderModel").equals("NONE")) {
                            barStyle = barStyle + "borderWidth:1px;";
                            barStyle = barStyle + "borderColor:transparent;";
                        } else {
                            barStyle = barStyle + "borderWidth:1px solid;";
                            barStyle = barStyle + "borderColor:" + list.get("barBorderColor") + ";";
                        }
                        barStyle = barStyle + "position:absolute;";
                        barStyle = barStyle + "left:50%;";
                    }
                    axisStyle = axisStyle + "background-color:" + list.getString("axisColorValue") + ";";
                    axisStyle = axisStyle + "width:1px;height:100%;position:absolute;";
                    axisStyle = axisStyle + "right:50%;";
                } else if (value.equals("NONE")) {
                    if (x < 0.0) {
                        backGroudColor.put("negative", (0.0 - x) / (0.0 - y) * 100.0);
                    } else {
                        if (x <= y) {
                            x = 0.0;
                        }
                        backGroudColor.put("positive", x / z * 100.0);
                    }
                    if (x < 0.0) {
                        negaBarStyle = negaBarStyle + "width:" + Math.abs((0.0 - x) / (0.0 - y) * 100.0) + "%;";
                        negaBarStyle = negaBarStyle + "height:92%;";
                        negaBarStyle = negaBarStyle + "position:absolute;";
                        if (list.get("barFillModel").equals("solid")) {
                            if (list.get("negativefillColorRadio").equals("fill")) {
                                negaBarStyle = negaBarStyle + "background-color:" + list.get("negativefillColorValue").toString() + ";";
                            } else if (list.get("negativefillColorRadio").equals("samecolor")) {
                                negaBarStyle = negaBarStyle + "background-color:" + list.get("barFillColor").toString() + ";";
                            }
                        } else if (list.get("negativefillColorRadio").equals("fill")) {
                            negaBarStyle = negaBarStyle + "backgroundImage: linear-gradient(to left," + list.getString("negativefillColorValue") + ", white);";
                        } else if (list.get("negativefillColorRadio").equals("samecolor")) {
                            negaBarStyle = negaBarStyle + "backgroundImage: linear-gradient(to left," + list.getString("barFillColor") + ", white);";
                        }
                        if (list.get("negativeBorderColorRadio").equals("border")) {
                            negaBarStyle = negaBarStyle + "border:1px solid;";
                            negaBarStyle = negaBarStyle + "borderColor:" + list.get("negativeBorderColorValue").toString() + ";";
                        } else if (list.get("negativeBorderColorRadio").equals("samebordercolor")) {
                            if (list.get("barBorderModel").equals("NONE")) {
                                negaBarStyle = negaBarStyle + "borderWidth:1px;";
                                negaBarStyle = negaBarStyle + "borderColor:transparent;";
                            } else {
                                negaBarStyle = negaBarStyle + "border:1px solid;";
                                negaBarStyle = negaBarStyle + "borderColor:" + list.getString("barBorderColor") + ";";
                            }
                        }
                    } else {
                        barStyle = barStyle + "width:" + x / z * 100.0 + "%;";
                        barStyle = barStyle + "height:92%;";
                        barStyle = list.get("barFillModel").equals("solid") ? barStyle + "background-color:" + list.getString("barFillColor") + ";" : barStyle + "backgroundImage: linear-gradient(to right," + list.getString("barFillColor") + ", white);";
                        if (list.get("barBorderModel").equals("NONE")) {
                            barStyle = barStyle + "borderWidth:1px;";
                            barStyle = barStyle + "borderColor:transparent;";
                        } else {
                            barStyle = barStyle + "borderWidth:1px solid;";
                            barStyle = barStyle + "borderColor:" + list.get("barBorderColor") + ";";
                        }
                        barStyle = barStyle + "position:absolute;";
                    }
                }
                curList.put("preWarnType", (Object)"BAR");
                curList.put("axisStyle", (Object)axisStyle);
                curList.put("barStyle", (Object)barStyle);
                curList.put("cellTextStyle", (Object)cellTextStyle);
                curList.put("negaBarStyle", (Object)negaBarStyle);
                curList.put("backGroundColor", (Object)backGroudColor);
                this.isHasKey("preWarnType", "BAR", warRowResult, isHasNewValue);
                this.isHasKey("axisStyle", axisStyle, warRowResult, isHasNewValue);
                this.isHasKey("barStyle", barStyle, warRowResult, isHasNewValue);
                this.isHasKey("cellTextStyle", cellTextStyle, warRowResult, isHasNewValue);
                this.isHasKey("negaBarStyle", negaBarStyle, warRowResult, isHasNewValue);
                this.isHasKey("backGroundColor", backGroudColor.toString(), warRowResult, isHasNewValue);
                for (String key : list.keySet()) {
                    Object value1 = list.get(key);
                    curList.put(key, value1);
                    this.isHasKey(key, String.valueOf(value1), warRowResult, isHasNewValue);
                }
                warRow.setResultList(warRowResult);
                break;
            }
            case TWOCOLOR: 
            case THREECOLOR: {
                AbstractData twoOrThreeColordata = null;
                if (field == null) {
                    Integer index = this.showedFieldsIndex.get(fieldCode);
                    twoOrThreeColordata = rowData.getValue(index.intValue());
                } else {
                    twoOrThreeColordata = rowData.getValue(field);
                }
                if (twoOrThreeColordata.isNull || twoOrThreeColordata.getAsString().equals("")) break;
                int colorIndex = 0;
                ArrayList<Object> backColor = new ArrayList();
                List<Object> valuelist = this.fieldCodeAndValue.get(pre.getFieldCode());
                if (field == null) {
                    valuelist = valuelist.stream().filter(v -> v != null).collect(Collectors.toList());
                }
                valuelist = valuelist.parallelStream().distinct().collect(Collectors.toList());
                List listDouble = valuelist.stream().sorted().collect(Collectors.toList());
                for (int i = 0; i < listDouble.size(); ++i) {
                    if ("\u2014\u2014".equals(twoOrThreeColordata.getAsString()) || !((Double)listDouble.get(i)).equals(Double.valueOf(twoOrThreeColordata.getAsString()))) continue;
                    colorIndex = i;
                    break;
                }
                String minColor = String.valueOf(list.get("minColor"));
                String maxColor = String.valueOf(list.get("maxColor"));
                String middleColor = null;
                Double middleValue = null;
                if (list.has("middleColor") && list.has("middleValue")) {
                    middleColor = String.valueOf(list.get("middleColor"));
                    middleValue = Double.valueOf(list.get("middleValue").toString());
                    int len = 0;
                    for (int i = 0; i < listDouble.size(); ++i) {
                        if (!((Double)listDouble.get(i) > middleValue)) continue;
                        ++len;
                    }
                    ArrayList<Integer> backColorUp = this.gradient(QueryConst.htmlColorToInt(minColor), QueryConst.htmlColorToInt(middleColor), valuelist.size() - len);
                    ArrayList<Integer> backColorDown = this.gradient(QueryConst.htmlColorToInt(maxColor), QueryConst.htmlColorToInt(middleColor), len);
                    backColorUp.remove(backColorUp.size() - 1);
                    backColorDown.remove(backColorDown.size() - 1);
                    Collections.reverse(backColorDown);
                    backColorUp.addAll(backColorDown);
                    backColor = backColorUp;
                } else {
                    backColor = this.gradient(QueryConst.htmlColorToInt(minColor), QueryConst.htmlColorToInt(maxColor), valuelist.size() + 1);
                }
                if (backColor.size() > colorIndex) {
                    curList.put("backGroundColor", backColor.get(colorIndex));
                    if (warRowResult.has("preWarnType") && "BAR".equals(warRowResult.get("preWarnType"))) {
                        warRowResult.remove("preWarnType");
                        warRowResult.remove("axisStyle");
                        warRowResult.remove("barStyle");
                        warRowResult.remove("cellTextStyle");
                        warRowResult.remove("negaBarStyle");
                        warRowResult.remove("backGroundColor");
                    }
                    this.isHasKey("backGroundColor", ((Integer)backColor.get(colorIndex)).toString(), warRowResult, isHasNewValue);
                }
                warRow.setResultList(warRowResult);
            }
        }
        if (pre.getScop().equals((Object)DataWarnigScop.ROW)) {
            for (String code : keyset) {
                this.tableAddWarnRow(table, code, row, pre, curList);
            }
        }
    }

    private boolean isWarn(String fieldLabel, Boolean isFieldFormulaIsCheck, Object value) {
        boolean cellShowWarn = false;
        if ("\u516c\u5f0f\u6761\u4ef6".equals(fieldLabel)) {
            if (Boolean.valueOf(value.toString()).booleanValue()) {
                if (isFieldFormulaIsCheck.booleanValue()) {
                    cellShowWarn = true;
                }
            } else if (!isFieldFormulaIsCheck.booleanValue()) {
                cellShowWarn = true;
            }
        } else if (Boolean.valueOf(value.toString()).booleanValue()) {
            cellShowWarn = true;
        }
        return cellShowWarn;
    }

    private void tableAddWarnRow(DataWarningTable table, String fieldCode, DimensionValueSet row, DataWarningDefine pre, JSONObject curList) {
        List<IWarningRow> fieldRowList = table.getRowsByFieldCode(fieldCode, row);
        if (fieldRowList.size() > 0) {
            IWarningRow fieldRow = fieldRowList.get(0);
            boolean curHasNewValue = Integer.valueOf(fieldRow.getOrder()) <= Integer.valueOf(pre.getOrder());
            for (String key : curList.keySet()) {
                String curValue = String.valueOf(curList.get(key));
                this.isHasKey(key, curValue, fieldRow.getResultList(), curHasNewValue);
            }
        } else {
            int index = -1;
            if (this.fieldIndex.containsKey(fieldCode)) {
                index = this.fieldIndex.get(fieldCode);
            }
            WarningRowDefine newrow = new WarningRowDefine(index, row, pre, fieldCode);
            newrow.setFieldCode(fieldCode);
            JSONObject newlist = new JSONObject();
            for (String key : curList.keySet()) {
                newlist.put(key, curList.get(key));
            }
            newrow.setResultList(newlist);
            table.appendRow(newrow);
        }
    }

    private ArrayList<Integer> gradient(int c0, int c1, int len) {
        Double curLen = Double.valueOf(String.valueOf(len));
        int[] fc = new int[]{(c0 & 0xFF0000) >> 16, (c0 & 0xFF00) >> 8, c0 & 0xFF};
        int[] tc = new int[]{(c1 & 0xFF0000) >> 16, (c1 & 0xFF00) >> 8, c1 & 0xFF};
        curLen = Math.min(curLen, (double)Math.max(Math.max(Math.abs(fc[0] - tc[0]), Math.abs(fc[1] - tc[1])), Math.abs(fc[2] - tc[2])));
        double[] s = new double[]{(double)(tc[0] - fc[0]) / curLen, (double)(tc[1] - fc[1]) / curLen, (double)(tc[2] - fc[2]) / curLen};
        ArrayList<Integer> r = new ArrayList<Integer>();
        int i = 0;
        while ((double)i < curLen) {
            r.add(fc[0] + (int)((double)i * s[0]) << 16 | fc[1] + (int)((double)i * s[1]) << 8 | fc[2] + (int)((double)i * s[2]));
            ++i;
        }
        r.add(c1);
        return r;
    }

    public String getCustomWarnFormula(DataWarningDefine preWarn) {
        String expression = "";
        DataWarningProperties warnProperty = preWarn.getProperty();
        try {
            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(preWarn.getFieldCode());
            block1 : switch (preWarn.getWarnType()) {
                case CELL: {
                    if (field == null && "\u6307\u6807\u6570\u503c".equals(warnProperty.getPreFieldLabel())) {
                        switch (warnProperty.getFieldCompareValue()) {
                            case BETWEEN: {
                                expression = preWarn.getFieldCode() + ">=" + warnProperty.getFieldCompareInput() + " and " + preWarn.getFieldCode() + "<=" + warnProperty.getFieldCompareInputTwo();
                                break block1;
                            }
                            case NOTBETWEEN: {
                                expression = preWarn.getFieldCode() + "<=" + warnProperty.getFieldCompareInput() + " or " + preWarn.getFieldCode() + ">=" + warnProperty.getFieldCompareInputTwo();
                                break block1;
                            }
                        }
                        expression = preWarn.getFieldCode() + this.helper.getSymbol(warnProperty.getFieldCompareValue()).getSign() + warnProperty.getFieldCompareInput();
                        break;
                    }
                    if (field != null || !"\u516c\u5f0f\u6761\u4ef6".equals(warnProperty.getPreFieldLabel())) break;
                    expression = warnProperty.getFieldFormulaInput();
                    break;
                }
                case ICON: {
                    if (field == null && "\u6307\u6807\u6570\u503c".equals(warnProperty.getPreFieldLabel())) {
                        List<String> compareList = warnProperty.getFieldIconCompareList();
                        List<Integer> valueList = warnProperty.getIconInputList();
                        int index = compareList.size();
                        for (int i = 0; i < index; ++i) {
                            String curValue;
                            FilterSymbols current;
                            String curExpression = "";
                            if (i == 0) {
                                current = FilterSymbols.valueOf(compareList.get(i));
                                curValue = String.valueOf(valueList.get(i));
                                curExpression = preWarn.getFieldCode() + this.helper.getSymbol(current).getSign() + curValue;
                                expression = this.handlingFormula(curExpression, "", Integer.toString(i), expression);
                                continue;
                            }
                            current = FilterSymbols.valueOf(compareList.get(i));
                            curValue = String.valueOf(valueList.get(i));
                            curExpression = preWarn.getFieldCode() + this.helper.getSymbol(current).getSign() + curValue;
                            FilterSymbols parent = FilterSymbols.getAntonym(compareList.get(i - 1));
                            String parentValue = String.valueOf(valueList.get(i - 1));
                            String preExpression = preWarn.getFieldCode() + this.helper.getSymbol(parent).getSign() + parentValue;
                            expression = this.handlingFormula(curExpression, preExpression, Integer.toString(i), expression);
                            if (i + 1 != index) continue;
                            FilterSymbols lastSymbols = FilterSymbols.getAntonym(compareList.get(i));
                            String lasterValue = String.valueOf(valueList.get(i));
                            String lastExpression = preWarn.getFieldCode() + this.helper.getSymbol(lastSymbols).getSign() + lasterValue;
                            expression = this.handlingFormula(lastExpression, "", Integer.toString(i + 1), expression);
                        }
                        break;
                    }
                    if (field != null || !"\u516c\u5f0f\u6761\u4ef6".equals(warnProperty.getPreFieldLabel())) break;
                    expression = warnProperty.getFieldFormulaInput();
                    break;
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return expression;
    }

    private JSONObject isHasKey(String key, String Value, JSONObject list, boolean isHasNewValue) {
        if (list.has(key)) {
            if (isHasNewValue) {
                list.put(key, (Object)Value);
            }
        } else {
            list.put(key, (Object)Value);
        }
        return list;
    }

    @Override
    public void setShowedFieldsIndex(Map<String, Integer> showedFieldsIndex) {
        this.showedFieldsIndex = showedFieldsIndex;
    }
}

