/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.table.df.BlockData
 *  com.jiuqi.nr.table.df.DataFrame
 *  com.jiuqi.nr.table.df.IKey
 *  com.jiuqi.nr.table.df.Index
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataItemDTO
 *  com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataReqDTO
 *  com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterController
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.exception.DuplicateRowKeysRuntimeException;
import com.jiuqi.nr.dafafill.formula.DataFillWorksheet;
import com.jiuqi.nr.dafafill.formula.DatafillCellProvider;
import com.jiuqi.nr.dafafill.formula.DatafillDynamicNodeProvider;
import com.jiuqi.nr.dafafill.formula.DatafillFormulaContext;
import com.jiuqi.nr.dafafill.formula.DatafillformulaCellContext;
import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DFDAddRowConfirmResult;
import com.jiuqi.nr.dafafill.model.DFDAddRowQueryInfo;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFieldWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataDeleteRow;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataResult;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.DataFillEntityData;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataResult;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.DataFillQueryResult;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.DimensionZbKeyDataMap;
import com.jiuqi.nr.dafafill.model.FieldReadWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.CellType;
import com.jiuqi.nr.dafafill.model.enums.ColWidthType;
import com.jiuqi.nr.dafafill.model.enums.DefaultValueType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.enums.SelectType;
import com.jiuqi.nr.dafafill.model.enums.TableSample;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import com.jiuqi.nr.dafafill.model.table.DataFillEnumCell;
import com.jiuqi.nr.dafafill.model.table.DataFillExpressionCell;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexData;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexInfo;
import com.jiuqi.nr.dafafill.model.table.DataFillZBIndexData;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataService;
import com.jiuqi.nr.dafafill.service.IDataFillEnvBaseDataService;
import com.jiuqi.nr.dafafill.service.ParameterBuilderHelp;
import com.jiuqi.nr.dafafill.service.impl.BatchFieldWriteAccessInfoHelper;
import com.jiuqi.nr.dafafill.util.DateUtils;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.table.df.BlockData;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataItemDTO;
import com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataReqDTO;
import com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterController;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class DataFillDataEnvServiceImpl
implements IDataFillDataEnvService {
    private static Logger logger = LoggerFactory.getLogger(DataFillDataEnvServiceImpl.class);
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private List<IDataFillEnvBaseDataService> dataFillEnvBaseDataServices;
    @Autowired
    private IDataFillEntityDataService dataFillEntityDataService;
    @Autowired
    private ParameterBuilderHelp parameterBuilderHelp;
    @Autowired
    private ParameterController parameterController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    DFDimensionValueGetService dfDimensionValueGetService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;
    @Autowired
    protected IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private BatchFieldWriteAccessInfoHelper batchFieldWriteAccessInfoHelper;
    private static final String SAVE_FAILED_CODE = "nr.dataentry.saveFailed";

    @Override
    public DataFillDataResult query(DataFillDataQueryInfo queryInfo, AsyncTaskMonitor asyncTaskMonitor) {
        DataFillDataResult dataFillDataResult = null;
        try {
            DataFillContext context = queryInfo.getContext();
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.2, "\u67e5\u8be2\u9884\u5904\u7406");
            }
            if (!(dataFillDataResult = this.checkDimensionValue(context)).isSuccess()) {
                return dataFillDataResult;
            }
            IDataFillEnvBaseDataService envBaseDataService = this.getEnvBaseDataService(context);
            List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
            List<QueryField> zbList = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
            if (null == zbList || zbList.isEmpty()) {
                dataFillDataResult = new DataFillDataResult();
                dataFillDataResult.setSuccess(false);
                dataFillDataResult.setMessage("nr.dataFill.queryColCantZero");
                return dataFillDataResult;
            }
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.25, "\u5f00\u59cb\u6570\u636e\u5e93\u67e5\u8be2");
            }
            DataFillQueryResult dataFillQueryResult = envBaseDataService.query(queryInfo);
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.4, "\u6784\u5efa\u57fa\u7840\u5355\u5143");
            }
            Map<DimensionValueSet, List<DataFillBaseCell>> result = this.buildBaseCell(context, zbList, dataFillQueryResult, asyncTaskMonitor != null);
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.progressAndMessage(0.5, "\u6784\u5efa\u67e5\u8be2\u7ed3\u679c");
            }
            dataFillDataResult = this.buildDataFillDataResult(context, result, dataFillQueryResult);
            dataFillDataResult.setTableType(context.getModel().getTableType());
            if (dataFillQueryResult.getPageInfo() != null) {
                dataFillDataResult.setPageInfo(dataFillQueryResult.getPageInfo());
                dataFillDataResult.setTotalCount(dataFillQueryResult.getTotalCount());
            }
            dataFillDataResult.setOtherOption(context.getModel().getOtherOption());
        }
        catch (Exception e2) {
            logger.error("\u6784\u5efa\u8868\u683cJSON\u6570\u636e\u62a5\u9519\uff01", e2);
            dataFillDataResult = new DataFillDataResult();
            dataFillDataResult.setSuccess(false);
            dataFillDataResult.setMessage("\u6784\u5efa\u8868\u683cJSON\u6570\u636e\u62a5\u9519!");
            return dataFillDataResult;
        }
        return dataFillDataResult;
    }

    private DataFillDataResult checkDimensionValue(DataFillContext context) throws Exception {
        String values;
        DataFillDataResult dataFillDataResult = new DataFillDataResult();
        dataFillDataResult.setSuccess(true);
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        DFDimensionValue periodDimensionValue = null;
        DFDimensionValue masterDimensionValue = null;
        Optional<DFDimensionValue> findPeriod = dimensionValues.stream().filter(e -> e.getName().equals(periodField.getSimplifyFullCode())).findAny();
        Optional<DFDimensionValue> findMaster = dimensionValues.stream().filter(e -> e.getName().equals(masterField.getSimplifyFullCode())).findAny();
        if (findPeriod.isPresent()) {
            periodDimensionValue = findPeriod.get();
        }
        if (findMaster.isPresent()) {
            masterDimensionValue = findMaster.get();
        }
        if (periodDimensionValue != null) {
            values = this.dfDimensionValueGetService.getValues(periodDimensionValue, context.getModel());
            String maxValues = this.dfDimensionValueGetService.getMaxValues(periodDimensionValue, context.getModel());
            if (!StringUtils.hasLength(values) && !StringUtils.hasLength(maxValues)) {
                dimensionValues.remove(periodDimensionValue);
            }
        }
        if (masterDimensionValue != null && !StringUtils.hasLength(values = this.dfDimensionValueGetService.getValues(masterDimensionValue, context.getModel()))) {
            dimensionValues.remove(masterDimensionValue);
            masterDimensionValue = null;
        }
        if (null == masterDimensionValue) {
            try {
                boolean masterMultipleVersion = this.dFDimensionParser.isMasterMultipleVersion(context);
                ParameterControlDataReqDTO parameter = new ParameterControlDataReqDTO();
                if (masterMultipleVersion) {
                    List<ParameterModel> paramModels = this.parameterBuilderHelp.getParameterModels(context, true);
                    List<ConditionField> conditionFields = context.getModel().getConditionFields();
                    ParameterModel periodJson = null;
                    ParameterModel masterJson = null;
                    for (int i = 0; i < paramModels.size(); ++i) {
                        ParameterModel paramModel = paramModels.get(i);
                        String fullCode = conditionFields.get(i).getFullCode();
                        if (fullCode.equals(periodField.getFullCode())) {
                            periodJson = paramModel;
                            continue;
                        }
                        if (!fullCode.equals(masterField.getFullCode())) continue;
                        masterJson = paramModel;
                    }
                    if (null != periodJson && null != masterJson) {
                        if (null == periodDimensionValue) {
                            throw new RuntimeException("\u4e3b\u7ef4\u5ea6\u5f00\u542f\u591a\u7248\u672c\uff0c\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a\uff01");
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        parameter.setConfig(ParameterConvertor.toJson(null, masterJson, (boolean)false).toString());
                        parameter.setOnlyRootLevel(false);
                        parameter.setAllSubLevel(true);
                        String test = ParameterConvertor.toJson(null, (ParameterModel)periodJson, (boolean)false).toString();
                        Map map = (Map)objectMapper.readValue(test, Map.class);
                        map.put("name", periodJson.getName());
                        ArrayList<String> values2 = new ArrayList<String>();
                        String periodValues = this.dfDimensionValueGetService.getValues(periodDimensionValue, context.getModel());
                        String realVaule = this.dfDimensionValueGetService.getMaxValues(periodDimensionValue, context.getModel());
                        if (!StringUtils.hasLength(realVaule)) {
                            if (!periodValues.contains(";")) {
                                realVaule = periodValues;
                            } else {
                                String[] arrays = periodValues.split(";");
                                realVaule = arrays[arrays.length - 1];
                            }
                        }
                        values2.add(realVaule);
                        map.put("values", values2);
                        Object object = map.get("_model_");
                        map.put("model", object);
                        ArrayList<Map> tempList = new ArrayList<Map>();
                        tempList.add(map);
                        String writeValueAsString = objectMapper.writeValueAsString(tempList);
                        parameter.setParameterValues(writeValueAsString);
                    }
                } else {
                    ConditionField conditionField = new ConditionField();
                    conditionField.setDefaultValueType(DefaultValueType.UCURRENT);
                    conditionField.setQuickCondition(true);
                    conditionField.setFullCode(masterField.getFullCode());
                    conditionField.setSelectType(SelectType.NONE);
                    List<ParameterModel> parameterModels = this.parameterBuilderHelp.getParameterModels(context, conditionField);
                    ParameterModel parameterModel = parameterModels.get(0);
                    JSONObject json = ParameterConvertor.toJson(null, (ParameterModel)parameterModel, (boolean)false);
                    parameter.setConfig(json.toString());
                    parameter.setOnlyRootLevel(false);
                    parameter.setAllSubLevel(true);
                }
                List items = this.parameterController.getChoiceValues(parameter);
                if (items.size() == 0) {
                    dataFillDataResult.setSuccess(false);
                    dataFillDataResult.setMessage("nr.dataFill.queryPeriodNoMaster");
                    return dataFillDataResult;
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < items.size(); ++i) {
                    ParameterControlDataItemDTO item = (ParameterControlDataItemDTO)items.get(i);
                    if (i != 0) {
                        builder.append(";");
                    }
                    builder.append(item.getId());
                }
                masterDimensionValue = new DFDimensionValue();
                masterDimensionValue.setName(masterField.getSimplifyFullCode());
                masterDimensionValue.setValues(builder.toString());
                dimensionValues.add(masterDimensionValue);
            }
            catch (Exception e2) {
                logger.error("\u6839\u636e\u53c2\u6570\u89e3\u6790\uff0c\u83b7\u53d6\u6240\u6709\u5355\u4f4d\u62a5\u9519\uff01", e2);
                throw e2;
            }
        }
        this.adjustFieldProcess(context);
        this.removeIllegalSceneMasterCombination(context);
        return dataFillDataResult;
    }

    private void adjustFieldProcess(DataFillContext context) {
        DataScheme scheme;
        boolean enableAdjust;
        if (context.getModel().getTableType().equals((Object)TableType.MASTER)) {
            return;
        }
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        DFDimensionValue adjustDimensionValue = null;
        Optional<DFDimensionValue> findAdjust = dimensionValues.stream().filter(e -> e.getName().equals(periodField.getSimplifyFullCode() + "._BINDING_")).findAny();
        if (findAdjust.isPresent()) {
            adjustDimensionValue = findAdjust.get();
        }
        if (enableAdjust = this.dataSchemeService.enableAdjustPeriod((scheme = this.dataSchemeService.getDataSchemeByCode(periodField.getDataSchemeCode())).getKey()).booleanValue()) {
            context.getModel().getQueryFields().add(this.getVirtualAdjustQueryField(periodField));
            if (null == adjustDimensionValue) {
                adjustDimensionValue = new DFDimensionValue();
                adjustDimensionValue.setValues("0");
                dimensionValues.add(adjustDimensionValue);
            }
            adjustDimensionValue.setName("ADJUST");
        }
    }

    private void removeIllegalSceneMasterCombination(DataFillContext context) {
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        QueryField sceneField = null;
        if (fieldTypeQueryFields.containsKey((Object)FieldType.SCENE) && fieldTypeQueryFields.get((Object)FieldType.SCENE) != null) {
            sceneField = fieldTypeQueryFields.get((Object)FieldType.SCENE).get(0);
        }
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
        List<QueryField> zbList = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
        Map<String, DFDimensionValue> dimensionMap = dimensionValues.stream().collect(Collectors.toMap(DFDimensionValue::getName, v -> v));
        String dataSchemeKey = this.dFDimensionParser.getDataSchemeKey(zbList);
        ArrayList<QueryField> hiddenQueryField = new ArrayList<QueryField>();
        ArrayList<String> hiddenId = new ArrayList<String>();
        this.dFDimensionParser.hideDimensionsProcess(context, dataSchemeKey, hiddenQueryField, hiddenId);
        DimensionValueSet dimensionValueSet = this.dFDimensionParser.parserGetEntityDimensionValueSet(context);
        Object dwValue = dimensionValueSet.getValue(masterField.getSimplifyFullCode());
        if (dwValue instanceof List) {
            List dwValues = (List)dwValue;
            StringBuilder legalValues = new StringBuilder();
            if (sceneField != null) {
                int index;
                Object periodValue = dimensionValueSet.getValue(periodField.getSimplifyFullCode());
                if (periodValue instanceof List) {
                    dimensionValueSet.setValue(periodField.getSimplifyFullCode(), ((List)periodValue).get(0));
                }
                DimensionValueSet tempDimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValueSet);
                List<String> sceneLegalValues = this.dFDimensionParser.searchReferRelation((String)dwValues.get(0), masterField.getId(), sceneField, tempDimensionValueSet, masterField, periodField, dataSchemeKey);
                for (int i = 0; i < dwValues.size(); ++i) {
                    String sceneValue = (String)dimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(sceneField));
                    if (sceneLegalValues != null && !sceneLegalValues.isEmpty()) {
                        int temp;
                        int n = temp = i >= sceneLegalValues.size() ? 0 : i;
                        if (sceneLegalValues.get(temp) != null) {
                            if (!Arrays.asList(sceneLegalValues.get(temp).split(";")).contains(sceneValue)) continue;
                            legalValues.append((String)dwValues.get(i)).append(";");
                            continue;
                        }
                        legalValues.append((String)dwValues.get(i)).append(";");
                        continue;
                    }
                    legalValues.append((String)dwValues.get(i)).append(";");
                }
                if (legalValues.length() <= 0) {
                    context.getDimensionValues().removeIf(value -> value.getName().equals(masterField.getSimplifyFullCode()));
                }
                if ((index = legalValues.lastIndexOf(";")) != -1) {
                    legalValues.replace(index, index + 1, "");
                }
                DFDimensionValue dfDimensionValue = dimensionMap.get(masterField.getSimplifyFullCode());
                dfDimensionValue.setValues(legalValues.toString());
            }
        } else if (sceneField != null) {
            String sceneValue = (String)dimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(sceneField));
            List<String> sceneLegalValues = this.dFDimensionParser.searchReferRelation((String)dwValue, masterField.getId(), sceneField, this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValueSet), masterField, periodField, dataSchemeKey);
            if (sceneLegalValues != null && !sceneLegalValues.isEmpty() && sceneLegalValues.get(0) != null && !Arrays.asList(sceneLegalValues.get(0).split(";")).contains(sceneValue)) {
                context.getDimensionValues().removeIf(value -> value.getName().equals(masterField.getSimplifyFullCode()));
            }
        }
        if (!hiddenQueryField.isEmpty()) {
            for (QueryField queryField : hiddenQueryField) {
                context.getModel().getAssistFields().remove(queryField);
                context.getDimensionValues().removeIf(value -> value.getName().equals(queryField.getCode()));
            }
        }
    }

    private void adjustFieldAfterQueryProcess(List<DataFillIndexInfo> rows, List<DataFillIndexInfo> cols, QueryField periodField) {
        for (DataFillIndexInfo row : rows) {
            if (!row.getDimensionName().equals("ADJUST")) continue;
            row.setDimensionName(periodField.getSimplifyFullCode() + "._BINDING_");
        }
        for (DataFillIndexInfo col : cols) {
            if (!col.getDimensionName().equals("ADJUST")) continue;
            col.setDimensionName(periodField.getSimplifyFullCode() + "._BINDING_");
        }
    }

    private QueryField getVirtualAdjustQueryField(QueryField periodField) {
        QueryField adjustField = new QueryField();
        adjustField.setCode("ADJUST");
        adjustField.setFullCode("ADJUST");
        adjustField.setTitle("\u8c03\u6574\u671f");
        adjustField.setDataType(DataFieldType.STRING);
        adjustField.setColWidth(periodField.getColWidth());
        adjustField.setDataSchemeCode(periodField.getDataSchemeCode());
        adjustField.setEditorType(periodField.getEditorType());
        adjustField.setFieldType(FieldType.ADJUST);
        return adjustField;
    }

    private Map<DimensionValueSet, List<DataFillBaseCell>> buildBaseCell(DataFillContext context, List<QueryField> zbList, DataFillQueryResult dataFillQueryResult, boolean isExport) {
        DataFillModel model = context.getModel();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        Map<DimensionValueSet, List<AbstractData>> baseQueryResult = dataFillQueryResult.getDatas();
        LinkedHashMap<DimensionValueSet, List<DataFillBaseCell>> result = new LinkedHashMap<DimensionValueSet, List<DataFillBaseCell>>();
        Set<Map.Entry<DimensionValueSet, List<AbstractData>>> entrySet = baseQueryResult.entrySet();
        List<QueryField> addZbs = dataFillQueryResult.getAddZbs();
        ArrayList<QueryField> allZbs = new ArrayList<QueryField>();
        if (null != addZbs) {
            allZbs.addAll(addZbs);
        }
        allZbs.addAll(zbList);
        ArrayList<DimensionValueSet> list = new ArrayList<DimensionValueSet>();
        for (Map.Entry<DimensionValueSet, List<AbstractData>> entry : entrySet) {
            DimensionValueSet rowDimension = entry.getKey();
            list.add(rowDimension);
            List<AbstractData> values = entry.getValue();
            ArrayList<DataFillBaseCell> dataCells = new ArrayList<DataFillBaseCell>();
            int length = values.size() - 1;
            for (int i = 0; i < allZbs.size(); ++i) {
                QueryField zbField = (QueryField)allZbs.get(i);
                AbstractData vData = length >= i ? values.get(i) : null;
                DataFillBaseCell dataCell = new DataFillBaseCell();
                if (null == vData || vData.isNull) {
                    dataCell = null;
                } else {
                    int dataType = vData.dataType;
                    String expression = zbField.getExpression();
                    if (StringUtils.hasLength(expression) && vData.getAsObject() != null) {
                        String value = vData.getAsObject().toString();
                        if ("-".equals(value)) {
                            DataFillEnumCell dataEnumCell = new DataFillEnumCell();
                            dataEnumCell.setCode("-");
                            dataEnumCell.setTitle("-");
                            dataEnumCell.setShowTitle("-");
                            dataEnumCell.setValue((Serializable)((Object)value));
                            dataCell = dataEnumCell;
                        } else {
                            DataFillContext entityContext = new DataFillContext();
                            entityContext.setModel(model);
                            entityContext.setDimensionValues(this.dFDimensionParser.reverseParser(rowDimension));
                            DataFillEntityDataQueryInfo dataFillEntityDataIdCodeQueryInfo = new DataFillEntityDataQueryInfo();
                            dataFillEntityDataIdCodeQueryInfo.setContext(entityContext);
                            dataFillEntityDataIdCodeQueryInfo.setFullCode(zbField.getFullCode());
                            dataFillEntityDataIdCodeQueryInfo.setCode(value);
                            DataFillEntityData entityData = null;
                            if (!value.contains(";")) {
                                entityData = this.dataFillEntityDataService.queryByIdOrCode(dataFillEntityDataIdCodeQueryInfo);
                                if (null != entityData) {
                                    DataFillEnumCell dataEnumCell = new DataFillEnumCell();
                                    dataEnumCell.setCode(entityData.getCode());
                                    dataEnumCell.setTitle(entityData.getTitle());
                                    dataEnumCell.setShowTitle(entityData.getRowCaption());
                                    dataEnumCell.setValue((Serializable)vData.getAsObject());
                                    dataCell = dataEnumCell;
                                } else {
                                    dataCell.setValue((Serializable)vData.getAsObject());
                                }
                            } else {
                                List<DataFillEntityData> entityDataList = this.dataFillEntityDataService.queryMultiValByIdOrCode(dataFillEntityDataIdCodeQueryInfo);
                                StringBuilder code = new StringBuilder();
                                StringBuilder title = new StringBuilder();
                                StringBuilder showTitle = new StringBuilder();
                                for (DataFillEntityData eachData : entityDataList) {
                                    code.append(eachData.getCode() + ";");
                                    title.append(eachData.getTitle() + ";");
                                    showTitle.append(eachData.getRowCaption() + ";");
                                }
                                DataFillEnumCell dataEnumCell = new DataFillEnumCell();
                                dataEnumCell.setCode(code.toString().substring(0, code.length() - 1));
                                dataEnumCell.setTitle(title.toString().substring(0, title.length() - 1));
                                dataEnumCell.setShowTitle(showTitle.toString().substring(0, showTitle.length() - 1));
                                dataEnumCell.setValue((Serializable)vData.getAsObject());
                                dataCell = dataEnumCell;
                            }
                        }
                    } else {
                        switch (dataType) {
                            case 5: {
                                Date date = vData.getAsDateObj();
                                dataCell.setValue((Serializable)((Object)DateUtils.dateToString(date)));
                                break;
                            }
                            case 2: {
                                Date dateTime = vData.getAsDateObj();
                                dataCell.setValue((Serializable)((Object)DateUtils.dateToString(dateTime)));
                                break;
                            }
                            case 1: {
                                boolean asBool = vData.getAsBool();
                                dataCell.setValue(Boolean.valueOf(asBool));
                                break;
                            }
                            default: {
                                dataCell.setValue((Serializable)vData.getAsObject());
                            }
                        }
                    }
                }
                dataCells.add(dataCell);
            }
            result.put(rowDimension, dataCells);
        }
        if (isExport) {
            return result;
        }
        TableType tableType = model.getTableType();
        List queryZb = allZbs.stream().filter(zbQueryField -> {
            if (zbQueryField.getFieldType() == FieldType.ZB || zbQueryField.getFieldType() == FieldType.TABLEDIMENSION || zbQueryField.getFieldType() == FieldType.FIELD) {
                return !zbQueryField.getId().equals("ID") && !zbQueryField.getId().equals("FLOATORDER");
            }
            return false;
        }).collect(Collectors.toList());
        ArrayList<String> queryZbIds = new ArrayList<String>();
        boolean isMater = tableType == TableType.MASTER;
        HashMap<String, String> authMap = new HashMap<String, String>();
        for (QueryField temp : queryZb) {
            if (tableType == TableType.MASTER) {
                queryZbIds.add(temp.getCode());
                authMap.put(temp.getId(), temp.getCode());
                continue;
            }
            queryZbIds.add(temp.getId());
            authMap.put(temp.getId(), temp.getId());
        }
        if (list.isEmpty()) {
            return result;
        }
        DimensionValueSet integranDimensionValue = this.dFDimensionParser.dimensionValueIntegration(context, list);
        List hiddenQueryField = (List)context.getCaches().get("hiddenQueryField");
        List hiddenId = (List)context.getCaches().get("hiddenId");
        FieldReadWriteAccessResultInfo accessResultInfo = this.batchFieldWriteAccessInfoHelper.buildAccessResult(context, integranDimensionValue, queryZbIds);
        Set resultEntity = result.entrySet();
        String dwKey = this.dFDimensionParser.getSqlDimensionName(masterField);
        for (Map.Entry entry : resultEntity) {
            DimensionValueSet key = (DimensionValueSet)entry.getKey();
            DimensionValueSet entityIdToSqlDimension = this.dFDimensionParser.entityIdToSqlDimension(context, key);
            String dwValue = (String)entityIdToSqlDimension.getValue(dwKey);
            if (hiddenQueryField != null && !hiddenQueryField.isEmpty()) {
                this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, entityIdToSqlDimension, masterField, periodField, hiddenQueryField, hiddenId, true);
            }
            List value = (List)entry.getValue();
            for (int i = 0; i < allZbs.size(); ++i) {
                DataFillBaseCell dataFillBaseCell;
                QueryField zbField = (QueryField)allZbs.get(i);
                CellType cellType = this.getCellType(zbField);
                if ((!isMater || !queryZbIds.contains(zbField.getCode())) && (isMater || !authMap.containsKey(zbField.getId()))) continue;
                DimensionValueSet dimensionValueSetCopy = new DimensionValueSet(entityIdToSqlDimension);
                DimensionSet dimensionSet = dimensionValueSetCopy.getDimensionSet();
                if (dimensionSet.contains("ID")) {
                    dimensionValueSetCopy.clearValue("ID");
                }
                if ((dataFillBaseCell = (DataFillBaseCell)value.get(i)) == null) {
                    dataFillBaseCell = new DataFillBaseCell();
                    value.set(i, dataFillBaseCell);
                }
                try {
                    DataFieldWriteAccessResultInfo access = accessResultInfo.getAccess(dimensionValueSetCopy, (String)authMap.get(zbField.getId()), context);
                    if (!access.haveAccess()) {
                        dataFillBaseCell.setReadOnly(true);
                        dataFillBaseCell.setMessage(access.getMessage());
                        continue;
                    }
                    if (cellType != CellType.PICTURE && cellType != CellType.FILE) continue;
                    dataFillBaseCell.setReadOnly(true);
                    dataFillBaseCell.setMessage("nr.dataFill.cellTypeNotSupport");
                    continue;
                }
                catch (Exception e) {
                    logger.error("\u67e5\u8be2\u5355\u5143\u683c\u53ef\u5199\u6743\u9650\u62a5\u9519", e);
                    dataFillBaseCell.setReadOnly(true);
                    dataFillBaseCell.setMessage(e.getMessage());
                }
            }
        }
        return result;
    }

    private CellType getCellType(QueryField zbField) {
        DataFieldType dataType = null == zbField.getDataType() ? DataFieldType.STRING : zbField.getDataType();
        CellType cellType = null;
        if (zbField.getFieldType() == FieldType.EXPRESSION) {
            cellType = CellType.EXPRESSION;
        } else {
            switch (dataType) {
                case STRING: {
                    if (StringUtils.hasLength(zbField.getExpression())) {
                        cellType = CellType.ENUM;
                        break;
                    }
                    cellType = CellType.STRING;
                    break;
                }
                case INTEGER: {
                    cellType = CellType.NUMBER;
                    break;
                }
                case BOOLEAN: {
                    cellType = CellType.BOOLEAN;
                    break;
                }
                case BIGDECIMAL: {
                    cellType = CellType.NUMBER;
                    break;
                }
                case DATE_TIME: 
                case DATE: {
                    cellType = CellType.DATE;
                    break;
                }
                case FILE: {
                    cellType = CellType.FILE;
                    break;
                }
                case PICTURE: {
                    cellType = CellType.PICTURE;
                    break;
                }
                default: {
                    cellType = CellType.STRING;
                }
            }
        }
        return cellType;
    }

    /*
     * WARNING - void declaration
     */
    private DataFillDataResult buildDataFillDataResult(DataFillContext context, Map<DimensionValueSet, List<DataFillBaseCell>> result, DataFillQueryResult dataFillQueryResult) throws JsonProcessingException {
        int size;
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        Map<String, QueryField> dimensionQueryFields = this.dFDimensionParser.getSimplifyQueryFieldsMap(context);
        Map<String, DFDimensionValue> dimensionMap = context.getDimensionValues().stream().collect(Collectors.toMap(DFDimensionValue::getName, e -> e));
        boolean enableAdjust = dimensionMap.containsKey("ADJUST") | this.isEnableAdjust(result, context);
        DataFillModel model = context.getModel();
        TableType tableType = model.getTableType();
        TableSample tableSample = model.getTableSample();
        QueryField dwQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        QueryField periodQueryField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        List<QueryField> sceneQueryFields = fieldTypeQueryFields.get((Object)FieldType.SCENE);
        ArrayList<QueryField> zbExpQueryFields = new ArrayList<QueryField>();
        List<QueryField> addZbs = dataFillQueryResult.getAddZbs();
        if (null != addZbs) {
            zbExpQueryFields.addAll(addZbs);
        }
        zbExpQueryFields.addAll(this.dFDimensionParser.getDisplayColsQueryFields(context));
        List<QueryField> zbQueryFields = zbExpQueryFields.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
        List expQueryFields = zbExpQueryFields.stream().filter(e -> e.getFieldType() == FieldType.EXPRESSION).collect(Collectors.toList());
        Map<String, QueryField> expQueryFieldMap = expQueryFields.stream().collect(Collectors.toMap(QueryField::getId, e -> e));
        ArrayList<QueryField> otherDimensionQueryFields = new ArrayList<QueryField>();
        if (null != sceneQueryFields) {
            otherDimensionQueryFields.addAll(sceneQueryFields);
        }
        List otherDimensionNames = otherDimensionQueryFields.stream().map(QueryField::getSimplifyFullCode).collect(Collectors.toList());
        String dwDimensionName = dwQueryField.getSimplifyFullCode();
        String periodDimensionName = periodQueryField.getSimplifyFullCode();
        ArrayList<List<DataFillIndexData>> rowIndexDatas = new ArrayList<List<DataFillIndexData>>();
        ArrayList<DataFillIndexInfo> rowInfos = new ArrayList<DataFillIndexInfo>();
        ArrayList<List<DataFillIndexData>> colIndexDatas = new ArrayList<List<DataFillIndexData>>();
        ArrayList<DataFillIndexInfo> colInfos = new ArrayList<DataFillIndexInfo>();
        ArrayList<List<DataFillBaseCell>> datas = new ArrayList<List<DataFillBaseCell>>();
        ArrayList<DFDimensionValue> commonDimensionValues = new ArrayList<DFDimensionValue>();
        ArrayList<String> rowDimensions = new ArrayList<String>();
        ArrayList<String> colDimensions = new ArrayList<String>();
        ArrayList<Object> commonDimensions = new ArrayList<Object>();
        if (!otherDimensionNames.isEmpty()) {
            commonDimensions.addAll(otherDimensionNames);
        }
        boolean zbIsAlone = true;
        boolean multipleStyle = false;
        if (tableType == TableType.MASTER) {
            commonDimensions.add(periodDimensionName);
            rowDimensions.add(dwDimensionName);
            colDimensions.add("ZB");
        } else if (tableType == TableType.FIXED) {
            if (tableSample == TableSample.PERIODUNITZB || tableSample == TableSample.NOTSUPPORTED) {
                rowDimensions.add(periodDimensionName);
                if (enableAdjust) {
                    rowDimensions.add("ADJUST");
                }
                rowDimensions.add(dwDimensionName);
                colDimensions.add("ZB");
            } else if (tableSample == TableSample.UNITPERIODZB) {
                rowDimensions.add(dwDimensionName);
                rowDimensions.add(periodDimensionName);
                if (enableAdjust) {
                    rowDimensions.add("ADJUST");
                }
                colDimensions.add("ZB");
            } else if (tableSample == TableSample.PERIODZBUNIT) {
                rowDimensions.add(periodDimensionName);
                if (enableAdjust) {
                    rowDimensions.add("ADJUST");
                }
                rowDimensions.add("ZB");
                colDimensions.add(dwDimensionName);
                zbIsAlone = false;
                multipleStyle = true;
            } else if (tableSample == TableSample.UNITZBPERIOD) {
                rowDimensions.add(dwDimensionName);
                rowDimensions.add("ZB");
                colDimensions.add(periodDimensionName);
                if (enableAdjust) {
                    colDimensions.add("ADJUST");
                }
                zbIsAlone = false;
                multipleStyle = true;
            }
        } else if (tableType == TableType.FLOAT || tableType == TableType.ACCOUNT) {
            rowDimensions.add(periodDimensionName);
            if (enableAdjust) {
                rowDimensions.add("ADJUST");
            }
            rowDimensions.add(dwDimensionName);
            rowDimensions.add("ID");
            colDimensions.add("ZB");
        } else if (tableType == TableType.FMDM) {
            commonDimensions.add(periodDimensionName);
            if (enableAdjust) {
                commonDimensions.add("ADJUST");
            }
            rowDimensions.add(dwDimensionName);
            colDimensions.add("ZB");
        }
        for (String string : commonDimensions) {
            DFDimensionValue dfDimensionValue = dimensionMap.get(string);
            if (null == dfDimensionValue) continue;
            commonDimensionValues.add(dfDimensionValue);
        }
        Set<Map.Entry<DimensionValueSet, List<DataFillBaseCell>>> entrySet = result.entrySet();
        DimensionZbKeyDataMap dimensionZbKeyDataMap = new DimensionZbKeyDataMap();
        List rows = null;
        if (multipleStyle) {
            for (Map.Entry<DimensionValueSet, List<DataFillBaseCell>> rowDimensinData : entrySet) {
                DimensionValueSet dimensionValueSet = rowDimensinData.getKey();
                List<DataFillBaseCell> zbDataValues = rowDimensinData.getValue();
                Iterator<Object> hashHeader = dimensionZbKeyDataMap.putHeader(dimensionValueSet);
                int n = -1;
                for (int i = 0; i < zbExpQueryFields.size(); ++i) {
                    QueryField zbExpQuery = (QueryField)zbExpQueryFields.get(i);
                    DataFillBaseCell zbValue = null;
                    if (zbExpQuery.getFieldType() != FieldType.EXPRESSION) {
                        zbValue = zbDataValues.get(++n);
                    }
                    dimensionZbKeyDataMap.putZbKey((StringBuilder)((Object)hashHeader), zbExpQuery.getId(), zbValue);
                }
            }
        } else {
            rows = result.values().stream().collect(Collectors.toList());
        }
        HashMap<String, Collection<String>> dimensionDataMap = new HashMap<String, Collection<String>>();
        Set<DimensionValueSet> rowDimensionSets = result.keySet();
        LinkedHashSet<DimensionValueSet> rowSet = new LinkedHashSet<DimensionValueSet>();
        Set<Object> colSet = new LinkedHashSet();
        if (!zbIsAlone) {
            for (DimensionValueSet dimensionValueSet : rowDimensionSets) {
                size = dimensionValueSet.size();
                DimensionValueSet row = new DimensionValueSet();
                DimensionValueSet col = new DimensionValueSet();
                for (int i = 0; i < size; ++i) {
                    String dimensionName = dimensionValueSet.getName(i);
                    if (colDimensions.contains(dimensionName)) {
                        col.setValue(dimensionName, dimensionValueSet.getValue(i));
                        continue;
                    }
                    if (!rowDimensions.contains(dimensionName)) continue;
                    row.setValue(dimensionName, dimensionValueSet.getValue(i));
                }
                rowSet.add(row);
                colSet.add(col);
            }
            colSet.addAll(rowSet);
        } else {
            colSet = rowDimensionSets;
        }
        for (DimensionValueSet dimensionValueSet : colSet) {
            size = dimensionValueSet.size();
            for (int i = 0; i < size; ++i) {
                String dimensionName = dimensionValueSet.getName(i);
                ArrayList<String> dimensionDatas = (ArrayList<String>)dimensionDataMap.get(dimensionName);
                if (!colDimensions.contains(dimensionName) && !rowDimensions.contains(dimensionName)) continue;
                if (null == dimensionDatas) {
                    dimensionDatas = new ArrayList<String>();
                    dimensionDataMap.put(dimensionName, dimensionDatas);
                }
                dimensionDatas.add(dimensionValueSet.getValue(i).toString());
            }
        }
        if (!zbIsAlone) {
            void var42_48;
            ArrayList oneZbQueryFields = new ArrayList(zbExpQueryFields);
            zbExpQueryFields = new ArrayList();
            boolean bl = false;
            while (var42_48 < rowSet.size()) {
                zbExpQueryFields.addAll(oneZbQueryFields);
                ++var42_48;
            }
            for (Map.Entry dimensionSet : dimensionDataMap.entrySet()) {
                List dimensionData = (List)dimensionSet.getValue();
                ArrayList oneDimensionData = new ArrayList(dimensionData);
                if (!rowDimensions.contains(dimensionSet.getKey())) continue;
                for (int j = 0; j < oneZbQueryFields.size() - 1; ++j) {
                    for (int z = 0; z < oneDimensionData.size(); ++z) {
                        String dimensionDataVale = (String)oneDimensionData.get(z);
                        int lastIndexOf = dimensionData.lastIndexOf(dimensionDataVale);
                        dimensionData.add(lastIndexOf, dimensionDataVale);
                    }
                }
            }
        }
        if (enableAdjust && !dimensionQueryFields.containsKey("ADJUST")) {
            QueryField adjustField = this.getVirtualAdjustQueryField(periodQueryField);
            dimensionQueryFields.put("ADJUST", adjustField);
            context.getModel().getQueryFields().add(adjustField);
        }
        this.buildIndexDatas(true, zbExpQueryFields, dimensionQueryFields, colInfos, colIndexDatas, colDimensions, dimensionDataMap, dataFillQueryResult, context);
        this.buildIndexDatas(false, zbExpQueryFields, dimensionQueryFields, rowInfos, rowIndexDatas, rowDimensions, dimensionDataMap, dataFillQueryResult, context);
        HashMap<DimensionValueSet, Position> dimensionPositionMap = new HashMap<DimensionValueSet, Position>();
        ArrayList<DatafillformulaCellContext> arrayList = new ArrayList<DatafillformulaCellContext>();
        int colNum = colDimensions.size() + 1 - this.countHide(colInfos);
        int rowNum = rowDimensions.size() + 1 - this.countHide(rowInfos);
        boolean haveExp = !expQueryFields.isEmpty();
        for (int r = 0; r < rowIndexDatas.size(); ++r) {
            int dataRowHide = 0;
            ArrayList<DataFillBaseCell> rowCellDatas = new ArrayList<DataFillBaseCell>();
            DimensionValueSet rowDimenson = new DimensionValueSet();
            List rowlist = (List)rowIndexDatas.get(r);
            for (int i = 0; i < rowlist.size(); ++i) {
                DataFillIndexInfo rowINfo = (DataFillIndexInfo)rowInfos.get(i);
                DataFillIndexData rowData = (DataFillIndexData)rowlist.get(i);
                rowDimenson.setValue(rowINfo.getDimensionName(), (Object)rowData.getValue());
            }
            for (DFDimensionValue dfDimensionValue : commonDimensionValues) {
                rowDimenson.setValue(dfDimensionValue.getName(), (Object)this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel()));
            }
            for (int l = 0; l < colIndexDatas.size(); ++l) {
                DimensionValueSet rowColDimenson = new DimensionValueSet(rowDimenson);
                List colList = (List)colIndexDatas.get(l);
                for (int i = 0; i < colList.size(); ++i) {
                    DataFillIndexInfo colInfo = (DataFillIndexInfo)colInfos.get(i);
                    DataFillIndexData colINfo = (DataFillIndexData)colList.get(i);
                    if (colINfo.getHide() != null && colINfo.getHide().booleanValue()) {
                        ++dataRowHide;
                    }
                    rowColDimenson.setValue(colInfo.getDimensionName(), (Object)colINfo.getValue());
                }
                Object value = rowColDimenson.getValue("ZB");
                DataFillBaseCell cellValue = null;
                if (haveExp && expQueryFieldMap.containsKey(value)) {
                    QueryField queryField = expQueryFieldMap.get(value);
                    DatafillformulaCellContext cellContext = new DatafillformulaCellContext();
                    cellContext.setExpressionField(queryField);
                    cellContext.setRow(r + colNum);
                    cellContext.setCol(l + rowNum - dataRowHide);
                    cellContext.setHideNum(dataRowHide);
                    cellContext.setRowColDimenson(rowColDimenson);
                    arrayList.add(cellContext);
                } else if (multipleStyle) {
                    cellValue = dimensionZbKeyDataMap.getCell(rowColDimenson);
                } else {
                    List list = (List)rows.get(r);
                    cellValue = (DataFillBaseCell)list.get(l);
                }
                if (haveExp) {
                    Position p = new Position(l + rowNum - dataRowHide, r + colNum);
                    dimensionPositionMap.put(rowColDimenson, p);
                }
                rowCellDatas.add(cellValue);
            }
            datas.add(rowCellDatas);
        }
        if (!expQueryFields.isEmpty()) {
            boolean needGeneral = tableType == TableType.FLOAT || tableType == TableType.ACCOUNT;
            DatafillFormulaContext formulaContext = new DatafillFormulaContext();
            formulaContext.setQueryFieldMap(this.dFDimensionParser.getSimplifyQueryFieldsMap(context));
            formulaContext.setFullQueryFieldMap(this.dFDimensionParser.getQueryFieldsMap(context));
            formulaContext.setDimensionZbKeyDataMap(dimensionZbKeyDataMap);
            formulaContext.setDimensionPositionMap(dimensionPositionMap);
            formulaContext.setCurWorksheet(new DataFillWorksheet());
            formulaContext.setZbQueryFields(zbQueryFields);
            formulaContext.setBasicDataMap(result);
            formulaContext.setHbQueryFields((List)context.getCaches().get("hiddenQueryField"));
            FormulaParser parser = FormulaParser.getInstance();
            parser.registerCellProvider((ICellProvider)new DatafillCellProvider());
            parser.registerDynamicNodeProvider((IDynamicNodeProvider)new DatafillDynamicNodeProvider());
            for (DatafillformulaCellContext cellContext : arrayList) {
                formulaContext.setCellContext(cellContext);
                DataFillExpressionCell expressionCell = new DataFillExpressionCell();
                try {
                    String expressionStr = cellContext.getExpressionField().getExpression();
                    if (StringUtils.hasLength(expressionStr)) {
                        IExpression expression = parser.parseEval(cellContext.getExpressionField().getExpression(), (IContext)formulaContext);
                        String excelFormula = expression.interpret((IContext)formulaContext, Language.EXCEL, null);
                        Object evaluate = expression.evaluate((IContext)formulaContext);
                        expressionCell.setValue((Serializable)evaluate);
                        expressionCell.setExpression(excelFormula);
                    }
                }
                catch (Exception e2) {
                    logger.error("\u516c\u5f0f\u5217\u89e3\u6790\u548c\u8ba1\u7b97\u62a5\u9519\uff01" + cellContext.getExpressionField().getExpression(), e2);
                }
                expressionCell.setReadOnly(true);
                ((List)datas.get(cellContext.getRow() - colNum)).set(cellContext.getCol() - rowNum + cellContext.getHideNum(), expressionCell);
            }
            if (needGeneral) {
                ArrayList dataGenerals = new ArrayList();
                ArrayList<DatafillformulaCellContext> formulaCellGenerals = new ArrayList<DatafillformulaCellContext>();
                HashMap<DimensionValueSet, Position> dimensionPositionMapGenerals = new HashMap<DimensionValueSet, Position>();
                for (int r = 0; r < 2; ++r) {
                    int dataRowHide = 0;
                    ArrayList<Object> rowCellDatas = new ArrayList<Object>();
                    DimensionValueSet rowDimenson = new DimensionValueSet();
                    for (int i = 0; i < rowInfos.size(); ++i) {
                        DataFillIndexInfo rowINfo = (DataFillIndexInfo)rowInfos.get(i);
                        rowDimenson.setValue(rowINfo.getDimensionName(), (Object)(r + ""));
                    }
                    for (DFDimensionValue dfDimensionValue : commonDimensionValues) {
                        rowDimenson.setValue(dfDimensionValue.getName(), (Object)this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel()));
                    }
                    for (int l = 0; l < colIndexDatas.size(); ++l) {
                        DimensionValueSet rowColDimenson = new DimensionValueSet(rowDimenson);
                        List colList = (List)colIndexDatas.get(l);
                        for (int i = 0; i < colList.size(); ++i) {
                            DataFillIndexInfo colInfo = (DataFillIndexInfo)colInfos.get(i);
                            DataFillIndexData colINfo = (DataFillIndexData)colList.get(i);
                            if (colINfo.getHide() != null && colINfo.getHide().booleanValue()) {
                                ++dataRowHide;
                            }
                            rowColDimenson.setValue(colInfo.getDimensionName(), (Object)colINfo.getValue());
                        }
                        Object value = rowColDimenson.getValue("ZB");
                        Object cellValue = null;
                        if (expQueryFieldMap.containsKey(value)) {
                            QueryField queryField = expQueryFieldMap.get(value);
                            DatafillformulaCellContext cellContext = new DatafillformulaCellContext();
                            cellContext.setExpressionField(queryField);
                            cellContext.setRow(r + colNum);
                            cellContext.setCol(l + rowNum - dataRowHide);
                            cellContext.setHideNum(dataRowHide);
                            cellContext.setRowColDimenson(rowColDimenson);
                            formulaCellGenerals.add(cellContext);
                        } else {
                            cellValue = null;
                        }
                        Position p = new Position(l + rowNum - dataRowHide, r + colNum);
                        dimensionPositionMapGenerals.put(rowColDimenson, p);
                        rowCellDatas.add(cellValue);
                    }
                    dataGenerals.add(rowCellDatas);
                }
                DatafillFormulaContext formulaGeneralContext = new DatafillFormulaContext();
                formulaGeneralContext.setQueryFieldMap(formulaContext.getQueryFieldMap());
                formulaGeneralContext.setFullQueryFieldMap(formulaContext.getFullQueryFieldMap());
                formulaGeneralContext.setDimensionZbKeyDataMap(dimensionZbKeyDataMap);
                formulaGeneralContext.setDimensionPositionMap(dimensionPositionMapGenerals);
                formulaGeneralContext.setCurWorksheet(new DataFillWorksheet());
                formulaGeneralContext.setZbQueryFields(zbQueryFields);
                formulaGeneralContext.setBasicDataMap(result);
                FormulaParser generalParser = FormulaParser.getInstance();
                generalParser.registerCellProvider((ICellProvider)new DatafillCellProvider());
                generalParser.registerDynamicNodeProvider((IDynamicNodeProvider)new DatafillDynamicNodeProvider());
                Map<String, List<DatafillformulaCellContext>> collect = formulaCellGenerals.stream().collect(Collectors.groupingBy(DatafillformulaCellContext::getFullCode));
                Set<Map.Entry<String, List<DatafillformulaCellContext>>> entrySet2 = collect.entrySet();
                for (Map.Entry<String, List<DatafillformulaCellContext>> entry : entrySet2) {
                    ArrayList<String> excelFormals = new ArrayList<String>();
                    List<DatafillformulaCellContext> tempList = entry.getValue();
                    for (DatafillformulaCellContext cellContext : tempList) {
                        formulaGeneralContext.setCellContext(cellContext);
                        try {
                            String expressionStr = cellContext.getExpressionField().getExpression();
                            if (!StringUtils.hasLength(expressionStr)) continue;
                            IExpression expression = generalParser.parseEval(expressionStr, (IContext)formulaGeneralContext);
                            String excelFormula = expression.interpret((IContext)formulaGeneralContext, Language.EXCEL, null);
                            if (excelFormals.isEmpty()) {
                                excelFormals.add(excelFormula);
                                continue;
                            }
                            String lastFormal = (String)excelFormals.get(0);
                            if (null != excelFormula && null != lastFormal && excelFormula.length() == lastFormal.length()) {
                                StringBuilder builder = new StringBuilder();
                                for (int i = 0; i < excelFormula.length(); ++i) {
                                    char lastChar;
                                    char charAt = excelFormula.charAt(i);
                                    if (charAt == (lastChar = lastFormal.charAt(i))) {
                                        builder.append(charAt);
                                        continue;
                                    }
                                    builder.append("{rowNum}");
                                }
                                List list = (List)colIndexDatas.get(cellContext.getCol() - rowNum + cellContext.getHideNum());
                                DataFillIndexData dataFillIndexData = (DataFillIndexData)list.get(0);
                                dataFillIndexData.setGeneralFormale(builder.toString());
                                continue;
                            }
                            excelFormals.clear();
                        }
                        catch (Exception e3) {
                            logger.error("\u516c\u5f0f\u5217\u89e3\u6790\u548c\u8ba1\u7b97\u62a5\u9519\uff01" + cellContext.getExpressionField().getExpression(), e3);
                        }
                    }
                }
            }
        }
        this.adjustFieldAfterQueryProcess(rowInfos, colInfos, periodQueryField);
        DataFillDataResult dataFillDataResult = new DataFillDataResult();
        DataFrame<DataFillBaseCell> df = this.buildTable(rowIndexDatas, rowInfos, colIndexDatas, colInfos, datas);
        dataFillDataResult.setTable(df);
        dataFillDataResult.setPublicDimensions(commonDimensionValues);
        dataFillDataResult.setSuccess(true);
        dataFillDataResult.setMessage("nr.dataFill.optSucc");
        return dataFillDataResult;
    }

    private int countHide(List<DataFillIndexInfo> rowInfos) {
        int indexRowHide = 0;
        for (DataFillIndexInfo dataFillIndexInfo : rowInfos) {
            if (dataFillIndexInfo.getHide() == null || !dataFillIndexInfo.getHide().booleanValue()) continue;
            ++indexRowHide;
        }
        return indexRowHide;
    }

    private void buildIndexDatas(boolean isCol, List<QueryField> zbField, Map<String, QueryField> dimensionQueryFields, List<DataFillIndexInfo> infos, List<List<DataFillIndexData>> indexDatas, List<String> dimensions, Map<String, Collection<String>> dimensionDataMap, DataFillQueryResult dataFillQueryResult, DataFillContext context) {
        List<QueryField> addZbs = dataFillQueryResult.getAddZbs();
        DataFillModel model = context.getModel();
        List addFullCodes = null;
        if (null != addZbs) {
            addFullCodes = addZbs.stream().map(e -> e.getFullCode()).collect(Collectors.toList());
        }
        for (String dimensionName : dimensions) {
            boolean firstIndexDatas = false;
            if (indexDatas.isEmpty()) {
                firstIndexDatas = true;
            }
            DataFillIndexInfo indexInfo = new DataFillIndexInfo();
            if ("ZB".equals(dimensionName)) {
                indexInfo.setName(NrDataFillI18nUtil.parseMsg(NrDataFillI18nUtil.buildCode("nr.dataquery.Field")));
                indexInfo.setDimensionName(dimensionName);
                indexInfo.setReadOnly(true);
                if (!isCol) {
                    indexInfo.setColWidthType(ColWidthType.AUTOMATIC);
                }
                for (int j = 0; j < zbField.size(); ++j) {
                    List<Object> oneIndexDatas = null;
                    if (firstIndexDatas) {
                        oneIndexDatas = new ArrayList();
                        indexDatas.add(oneIndexDatas);
                    } else {
                        oneIndexDatas = indexDatas.get(j);
                    }
                    QueryField queryField = zbField.get(j);
                    DataFillZBIndexData indexData = new DataFillZBIndexData();
                    CellType cellType = this.getCellType(queryField);
                    indexData.setisMultiVal(this.isZBAllowMultiVal(queryField.getId(), queryField.getFullCode()));
                    indexData.setCellType(cellType);
                    indexData.setEditor(queryField.getEditorType());
                    if (CellType.NUMBER.equals((Object)cellType)) {
                        indexData.setDecimal(this.dFDimensionParser.getDecimal(queryField.getId(), queryField.getFullCode()));
                    }
                    indexData.setFormat(this.dFDimensionParser.getFormat(queryField));
                    indexData.setFieldFormat(queryField.getShowFormat());
                    indexData.setSource(queryField.getFullCode());
                    boolean displayZBCode = model.getOtherOption().isDisplayZBCode();
                    indexData.setTitle(StringUtils.hasLength(queryField.getAlias()) ? queryField.getAlias() : queryField.getTitle());
                    if (displayZBCode) {
                        indexData.setTitle(indexData.getTitle() + "\n" + queryField.getCode());
                    }
                    indexData.setType(queryField.getFieldType());
                    indexData.setValue(queryField.getId());
                    if (cellType == CellType.EXPRESSION) {
                        indexData.setCode(queryField.getExpression());
                    } else {
                        indexData.setCode(queryField.getCode());
                    }
                    indexData.setReadOnly(true);
                    indexData.setExpression(queryField.getExpression());
                    if (isCol) {
                        if (queryField.getColWidthType() == ColWidthType.DEFAULT) {
                            indexData.setColWidthType(ColWidthType.CUSTOM);
                            indexData.setColWidth(100);
                        } else {
                            indexData.setColWidthType(queryField.getColWidthType());
                            indexData.setColWidth(-1 == queryField.getColWidth() ? null : Integer.valueOf(queryField.getColWidth()));
                        }
                        if (context.getModel().getModelType() == ModelType.SCHEME) {
                            try {
                                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(queryField.getId());
                                if (fieldDefine != null) {
                                    indexData.setDefaultValue(fieldDefine.getDefaultValue());
                                    if (StringUtils.hasLength(fieldDefine.getDefaultValue()) && queryField.getDataType() == DataFieldType.STRING && StringUtils.hasLength(queryField.getExpression())) {
                                        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
                                        ArrayList<DFDimensionValue> newdimensionValues = new ArrayList<DFDimensionValue>();
                                        for (DFDimensionValue dimension : dimensionValues) {
                                            DFDimensionValue dimensionCopy = new DFDimensionValue();
                                            dimensionCopy.setName(dimension.getName());
                                            if (this.dfDimensionValueGetService.getValues(dimension, context.getModel()).contains(";")) {
                                                dimensionCopy.setValues(this.dfDimensionValueGetService.getValues(dimension, context.getModel()).split(";")[0]);
                                            } else {
                                                dimensionCopy.setValues(this.dfDimensionValueGetService.getValues(dimension, context.getModel()));
                                            }
                                            newdimensionValues.add(dimensionCopy);
                                        }
                                        context.setDimensionValues(newdimensionValues);
                                        DataFillEntityDataQueryInfo entityDataQueryInfo = new DataFillEntityDataQueryInfo();
                                        entityDataQueryInfo.setContext(context);
                                        entityDataQueryInfo.setAllChildren(true);
                                        entityDataQueryInfo.setFullCode(queryField.getFullCode());
                                        DataFillEntityDataResult entityDataResult = this.dataFillEntityDataService.query(entityDataQueryInfo);
                                        context.setDimensionValues(dimensionValues);
                                        HashSet<String> codes = new HashSet<String>(Arrays.asList(fieldDefine.getDefaultValue().replaceAll("\"", "").split(";")));
                                        StringBuilder defaultValue = new StringBuilder();
                                        if (entityDataResult.isSuccess()) {
                                            for (DataFillEntityData entityData : entityDataResult.getItems()) {
                                                if (!codes.contains(entityData.getCode())) continue;
                                                if (defaultValue.length() > 0) {
                                                    defaultValue.append("#~^%$");
                                                }
                                                defaultValue.append(entityData.getCode()).append("#^$").append(entityData.getRowCaption());
                                            }
                                        }
                                        indexData.setDefaultValue(defaultValue.toString());
                                    }
                                }
                            }
                            catch (Exception e2) {
                                StringBuilder logInfo = new StringBuilder();
                                logInfo.append("\u81ea\u5b9a\u4e49\u5f55\u5165\u83b7\u53d6\u6307\u6807").append("[").append(queryField.getId()).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e2.getMessage());
                                logger.error(logInfo.toString(), e2);
                            }
                        }
                    }
                    if (null != addFullCodes && addFullCodes.contains(queryField.getFullCode())) {
                        indexData.setHide(true);
                    }
                    oneIndexDatas.add(indexData);
                }
            } else if ("ID".equals(dimensionName)) {
                indexInfo.setName("ID");
                indexInfo.setDimensionName(dimensionName);
                indexInfo.setReadOnly(true);
                indexInfo.setHide(true);
                Collection<String> dimensionDataSets = dimensionDataMap.get(dimensionName);
                if (null == dimensionDataSets) {
                    dimensionDataSets = new ArrayList<String>();
                }
                ArrayList<String> dimensionDatas = new ArrayList<String>();
                dimensionDatas.addAll(dimensionDataSets);
                for (int j = 0; j < dimensionDatas.size(); ++j) {
                    List<Object> oneIndexDatas = null;
                    if (firstIndexDatas) {
                        oneIndexDatas = new ArrayList();
                        indexDatas.add(oneIndexDatas);
                    } else {
                        oneIndexDatas = indexDatas.get(j);
                    }
                    DataFillIndexData indexData = new DataFillIndexData();
                    String dimensionValue = (String)dimensionDatas.get(j);
                    indexData.setType(FieldType.FLOAT_ID);
                    indexData.setValue(dimensionValue);
                    indexData.setTitle(dimensionValue);
                    indexData.setReadOnly(true);
                    oneIndexDatas.add(indexData);
                }
            } else {
                Map<String, DataFillDimensionTitle> masterDimensionTitles;
                boolean masterOpenVersion;
                Collection<String> dimensionDataSets;
                QueryField queryField = dimensionQueryFields.get(dimensionName);
                indexInfo.setName(StringUtils.hasLength(queryField.getAlias()) ? queryField.getAlias() : queryField.getTitle());
                indexInfo.setDimensionName(dimensionName);
                indexInfo.setReadOnly(true);
                if (!isCol) {
                    if (queryField.getColWidthType() == ColWidthType.DEFAULT) {
                        indexInfo.setColWidthType(ColWidthType.CUSTOM);
                        indexInfo.setColWidth(100);
                    } else {
                        indexInfo.setColWidthType(queryField.getColWidthType());
                        indexInfo.setColWidth(-1 == queryField.getColWidth() ? null : Integer.valueOf(queryField.getColWidth()));
                    }
                }
                if (null == (dimensionDataSets = dimensionDataMap.get(dimensionName))) {
                    dimensionDataSets = new ArrayList<String>();
                }
                ArrayList<String> dimensionDatas = new ArrayList<String>();
                dimensionDatas.addAll(dimensionDataSets);
                Map<Object, Object> dimensionTitle = new HashMap();
                ArrayList<String> periodDimensionDatas = new ArrayList<String>();
                Set<Map.Entry<String, QueryField>> entrySet = dimensionQueryFields.entrySet();
                for (Map.Entry<String, QueryField> entry : entrySet) {
                    QueryField value = entry.getValue();
                    if (value.getFieldType() != FieldType.PERIOD) continue;
                    Collection<String> periodSets = dimensionDataMap.get(entry.getKey());
                    if (null == periodSets) {
                        periodSets = new ArrayList<String>();
                        Optional<DFDimensionValue> findAny = context.getDimensionValues().stream().filter(e -> e.getName().equals(value.getSimplifyFullCode())).findAny();
                        if (findAny.isPresent()) {
                            periodSets.add(this.dfDimensionValueGetService.getValues(findAny.get(), context.getModel()));
                        }
                    }
                    periodDimensionDatas.addAll(periodSets);
                    break;
                }
                boolean needPeriodMaster = false;
                TableType tableType = model.getTableType();
                if (queryField.getFieldType() == FieldType.MASTER && (masterOpenVersion = this.dFDimensionParser.isMasterMultipleVersion(context))) {
                    needPeriodMaster = true;
                }
                dimensionTitle = !needPeriodMaster ? this.dFDimensionParser.getDimensionTitle(queryField, dimensionDatas, periodDimensionDatas, model) : (null != (masterDimensionTitles = dataFillQueryResult.getMasterDimensionTitles()) ? masterDimensionTitles : this.dFDimensionParser.getDwDimensionTitle(queryField, dimensionDatas, periodDimensionDatas, model));
                for (int j = 0; j < dimensionDatas.size(); ++j) {
                    List<Object> oneIndexDatas = null;
                    if (firstIndexDatas) {
                        oneIndexDatas = new ArrayList();
                        indexDatas.add(oneIndexDatas);
                    } else {
                        oneIndexDatas = indexDatas.get(j);
                    }
                    DataFillIndexData indexData = new DataFillIndexData();
                    String dimensionValue = (String)dimensionDatas.get(j);
                    indexData.setType(queryField.getFieldType());
                    if (isCol) {
                        if (queryField.getColWidthType() == ColWidthType.DEFAULT) {
                            indexInfo.setColWidthType(ColWidthType.CUSTOM);
                            indexInfo.setColWidth(100);
                        } else {
                            indexData.setColWidthType(queryField.getColWidthType());
                            indexData.setColWidth(-1 == queryField.getColWidth() ? null : Integer.valueOf(queryField.getColWidth()));
                        }
                    }
                    indexData.setValue(dimensionValue);
                    indexData.setReadOnly(true);
                    DataFillDimensionTitle dataFillDimensionTitle = null;
                    if (!needPeriodMaster) {
                        dataFillDimensionTitle = queryField.getFieldType() == FieldType.ADJUST ? (DataFillDimensionTitle)dimensionTitle.get(dimensionValue + ";" + (String)periodDimensionDatas.get(j)) : (DataFillDimensionTitle)dimensionTitle.get(dimensionValue);
                    } else {
                        String priod = "";
                        priod = tableType == TableType.MASTER ? (String)periodDimensionDatas.get(0) : (j >= periodDimensionDatas.size() ? (String)periodDimensionDatas.get(periodDimensionDatas.size() - 1) : (String)periodDimensionDatas.get(j));
                        dataFillDimensionTitle = (DataFillDimensionTitle)dimensionTitle.get(priod + ";" + dimensionValue);
                    }
                    if (null != dataFillDimensionTitle) {
                        indexData.setTitle(dataFillDimensionTitle.getTitle());
                        indexData.setLevel(dataFillDimensionTitle.getLevel());
                    } else {
                        indexData.setTitle(dimensionValue);
                    }
                    oneIndexDatas.add(indexData);
                }
            }
            infos.add(indexInfo);
        }
    }

    private DataFrame<DataFillBaseCell> buildTable(List<List<DataFillIndexData>> rowIndexs, List<DataFillIndexInfo> rowInfos, List<List<DataFillIndexData>> colIndexs, List<DataFillIndexInfo> colInfos, List<List<DataFillBaseCell>> datas) {
        Index r = this.buildIndex(rowIndexs, rowInfos);
        Index c = this.buildIndex(colIndexs, colInfos);
        BlockData data = new BlockData(datas);
        DataFrame df = new DataFrame(r, c, data);
        return df;
    }

    private Index buildIndex(List<List<DataFillIndexData>> indexs, List<DataFillIndexInfo> infos) {
        String[] names = (String[])infos.stream().map(DataFillIndexInfo::getName).toArray(String[]::new);
        Object[] sources = (DataFillIndexInfo[])infos.stream().map(e -> e).toArray(DataFillIndexInfo[]::new);
        if (names.length > 1) {
            List keys = indexs.stream().map(e -> {
                Object[] elements = new DataFillIndexData[e.size()];
                for (int i = 0; i < e.size(); ++i) {
                    elements[i] = (DataFillIndexData)e.get(i);
                }
                return new IKey(elements);
            }).collect(Collectors.toList());
            return new Index(keys, names, sources, keys.size());
        }
        List keys = indexs.stream().map(e -> (DataFillIndexData)e.get(0)).collect(Collectors.toList());
        return new Index(keys, names[0], (Object)infos.get(0), keys.size());
    }

    @Override
    @Transactional
    public DataFillResult save(DataFillDataSaveInfo saveInfo) {
        try {
            DataFillContext context = saveInfo.getContext();
            IDataFillEnvBaseDataService envBaseDataService = this.getEnvBaseDataService(context);
            this.saveDataAdjustFieldProcess(saveInfo);
            return envBaseDataService.save(saveInfo);
        }
        catch (DuplicateRowKeysRuntimeException e) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u4fdd\u5b58\u51fa\u73b0\u672a\u77e5\u5f02\u5e38\uff01", e);
            DataFillResult result = new DataFillResult();
            result.setMessage(NrDataFillI18nUtil.buildCode(SAVE_FAILED_CODE));
            result.setSuccess(false);
            result.setErrors(e.getErrors());
            return result;
        }
        catch (DataFillRuntimeException e) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u4fdd\u5b58\u5931\u8d25\uff01", e);
            ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
            DataFillSaveErrorDataInfo errorInfo = new DataFillSaveErrorDataInfo();
            errors.add(errorInfo);
            ResultErrorInfo dataError = new ResultErrorInfo();
            errorInfo.setDataError(dataError);
            dataError.setErrorCode(ErrorCode.SYSTEMERROR);
            dataError.setErrorInfo(NrDataFillI18nUtil.buildCode(SAVE_FAILED_CODE) + "\uff01" + e.getMessage());
            DataFillResult result = new DataFillResult();
            result.setMessage(NrDataFillI18nUtil.buildCode(SAVE_FAILED_CODE));
            result.setSuccess(false);
            result.setErrors(errors);
            return result;
        }
        catch (Exception e) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u4fdd\u5b58\u51fa\u73b0\u672a\u77e5\u5f02\u5e38\uff01", e);
            ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
            DataFillSaveErrorDataInfo errorInfo = new DataFillSaveErrorDataInfo();
            errors.add(errorInfo);
            ResultErrorInfo dataError = new ResultErrorInfo();
            errorInfo.setDataError(dataError);
            dataError.setErrorCode(ErrorCode.SYSTEMERROR);
            dataError.setErrorInfo(String.format("{{%s}}", "nr.dataFill.customInputSaveError") + "\uff01" + e.getMessage());
            DataFillResult result = new DataFillResult();
            result.setMessage(NrDataFillI18nUtil.buildCode(SAVE_FAILED_CODE));
            result.setSuccess(false);
            result.setErrors(errors);
            return result;
        }
    }

    private IDataFillEnvBaseDataService getEnvBaseDataService(DataFillContext context) {
        Optional<IDataFillEnvBaseDataService> findFirst = this.dataFillEnvBaseDataServices.stream().filter(e -> e.getTableType().equals((Object)context.getModel().getTableType())).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        throw new DataFillRuntimeException("context:" + context + ";not found IDataFillEnvBaseDataService");
    }

    private Boolean isZBAllowMultiVal(String zbId, String fullCode) {
        FieldDefine fieldDefine = null;
        IEntityModel entityModel = null;
        String dsDimensionName = fullCode.contains("@") ? fullCode.split("@")[0] : null;
        try {
            List showFields;
            Map<String, IEntityAttribute> showFieldsMap;
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(zbId);
            if (dsDimensionName != null && dsDimensionName.length() != 0) {
                entityModel = this.entityMetaService.getEntityModel(dsDimensionName);
            }
            if (fieldDefine != null) {
                return fieldDefine.getAllowMultipleSelect();
            }
            if (entityModel != null && (showFieldsMap = (showFields = entityModel.getShowFields()).stream().collect(Collectors.toMap(IModelDefineItem::getID, e -> e))).get(zbId) != null) {
                return showFieldsMap.get(zbId).isMultival();
            }
        }
        catch (Exception e2) {
            logger.info(e2.getMessage());
        }
        return false;
    }

    @Override
    public DFDAddRowConfirmResult floatAddRowConfirm(DFDAddRowQueryInfo queryInfo) {
        DataFillContext context = queryInfo.getContext();
        this.adjustFieldProcess(context);
        DataFillModel model = context.getModel();
        DFDAddRowConfirmResult confirmResult = new DFDAddRowConfirmResult();
        confirmResult.setErrors(new ArrayList<List<DFDimensionValue>>());
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        QueryField sceneField = null;
        if (fieldTypeQueryFields.containsKey((Object)FieldType.SCENE) && fieldTypeQueryFields.get((Object)FieldType.SCENE) != null) {
            sceneField = fieldTypeQueryFields.get((Object)FieldType.SCENE).get(0);
        }
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
        List<QueryField> zbList = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
        List queryZb = zbList.stream().filter(zbQueryField -> {
            if (zbQueryField.getFieldType() == FieldType.ZB || zbQueryField.getFieldType() == FieldType.TABLEDIMENSION || zbQueryField.getFieldType() == FieldType.FIELD) {
                return !zbQueryField.getId().equals("ID") && !zbQueryField.getId().equals("FLOATORDER");
            }
            return false;
        }).collect(Collectors.toList());
        List defines = this.dataDefinitionRuntimeController.queryTableDefinesByFields(Collections.singleton(((QueryField)zbList.get(0)).getId()));
        String schemeKey = this.dataSchemeService.getDataTable(((TableDefine)defines.get(0)).getKey()).getDataSchemeKey();
        List<String> queryZbIds = null;
        boolean isFMDM = false;
        if (model.getModelType() != ModelType.TASK) {
            isFMDM = false;
            queryZbIds = queryZb.stream().map(QueryField::getId).collect(Collectors.toList());
        } else {
            isFMDM = true;
            queryZbIds = queryZb.stream().map(QueryField::getCode).collect(Collectors.toList());
        }
        boolean orgOnly = false;
        Map<String, QueryField> queryFieldMap = this.dFDimensionParser.getSimplifyQueryFieldsMap(context);
        List<DFDimensionValue> dimensionValueList = queryInfo.getAddRowDimensionList();
        DimensionValueSet endSet = new DimensionValueSet();
        for (DFDimensionValue dfDimensionValue : dimensionValueList) {
            FieldType fieldType = queryFieldMap.get(dfDimensionValue.getName()).getFieldType();
            if (fieldType != FieldType.PERIOD && fieldType != FieldType.MASTER && fieldType != FieldType.SCENE) continue;
            endSet.setValue(dfDimensionValue.getName(), (Object)this.dfDimensionValueGetService.getValues(dfDimensionValue, model));
            if (fieldType == FieldType.PERIOD && this.dfDimensionValueGetService.getMaxValues(dfDimensionValue, model) != null) {
                endSet.setValue(dfDimensionValue.getName(), (Object)this.maxValues2String(dfDimensionValue, context, queryFieldMap.get(dfDimensionValue.getName())));
            }
            if (fieldType != FieldType.PERIOD || ((String)endSet.getValue(dfDimensionValue.getName())).contains(";")) continue;
            orgOnly = true;
        }
        Object dimensionValueSetLists = new ArrayList();
        for (int i = 0; i < endSet.size(); ++i) {
            String[] values = ((String)endSet.getValue(i)).split(";");
            ArrayList newSetLists = new ArrayList();
            for (String value : values) {
                Object newSet;
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(endSet.getName(i), (Object)value);
                if (dimensionValueSetLists.isEmpty()) {
                    newSet = new ArrayList();
                    newSet.add(dimensionValueSet);
                    newSetLists.add(newSet);
                    continue;
                }
                newSet = dimensionValueSetLists.iterator();
                while (newSet.hasNext()) {
                    List set = (List)newSet.next();
                    ArrayList<DimensionValueSet> newSet2 = new ArrayList<DimensionValueSet>();
                    DimensionValueSet newValue = new DimensionValueSet(dimensionValueSet);
                    newSet2.add(newValue);
                    newValue.combine((DimensionValueSet)set.get(0));
                    newSetLists.add(newSet2);
                }
            }
            dimensionValueSetLists = newSetLists;
        }
        List<DFDimensionValue> allDimensionValues = context.getDimensionValues();
        DimensionValueSet addDimensionSet = new DimensionValueSet();
        for (DFDimensionValue dfDimensionValue : allDimensionValues) {
            FieldType fieldType = queryFieldMap.get(dfDimensionValue.getName()).getFieldType();
            if (fieldType != FieldType.PERIOD && fieldType != FieldType.MASTER && fieldType != FieldType.SCENE && fieldType != FieldType.ADJUST || ((DimensionValueSet)((List)dimensionValueSetLists.get(0)).get(0)).getValue(dfDimensionValue.getName()) != null) continue;
            addDimensionSet.setValue(dfDimensionValue.getName(), (Object)this.dfDimensionValueGetService.getValues(dfDimensionValue, model));
        }
        ArrayList<QueryField> hiddenQueryField = new ArrayList<QueryField>();
        ArrayList<String> hiddenId = new ArrayList<String>();
        this.dFDimensionParser.hideDimensionsProcess(context, schemeKey, hiddenQueryField, hiddenId);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder errorReasonMessage = new StringBuilder();
        Iterator iterator = dimensionValueSetLists.iterator();
        while (iterator.hasNext()) {
            List dimensionValueSets = (List)iterator.next();
            DimensionValueSet dimensionValueSet = new DimensionValueSet((DimensionValueSet)dimensionValueSets.get(0));
            dimensionValueSet.combine(addDimensionSet);
            dimensionValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionValueSet);
            boolean canAdd = true;
            String addMessage = null;
            String dataSchemeKey = this.dFDimensionParser.getDataSchemeKey(zbList);
            String dwValue = (String)dimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(masterField));
            String periodValue = (String)dimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(periodField));
            Map<String, DataFillDimensionTitle> dwMap = this.dFDimensionParser.getDwDimensionTitle(masterField, Collections.singletonList(dwValue), Collections.singletonList(periodValue), model);
            String key = periodValue + ";" + dwValue;
            if (!dwMap.containsKey(key) || dwMap.get(key) == null) {
                canAdd = false;
                addMessage = NrDataFillI18nUtil.parseMsg("{{nr.dataFill.periodOrgMatch}}");
            }
            FieldReadWriteAccessResultInfo accessResultInfo = this.batchFieldWriteAccessInfoHelper.buildAccessResult(context, dimensionValueSet, queryZbIds);
            if (canAdd) {
                for (QueryField queryField : model.getQueryFields()) {
                    if ((!isFMDM || !queryZbIds.contains(queryField.getCode())) && (isFMDM || !queryZbIds.contains(queryField.getId()))) continue;
                    try {
                        DataFieldWriteAccessResultInfo access;
                        boolean accessResult;
                        if (hiddenQueryField != null && !hiddenQueryField.isEmpty()) {
                            this.dFDimensionParser.searchDwHiddenDimensionValue(dwValue, dimensionValueSet, masterField, periodField, hiddenQueryField, hiddenId, true);
                        }
                        if (!(accessResult = (access = accessResultInfo.getAccess(dimensionValueSet, isFMDM ? queryField.getCode() : queryField.getId(), context)).haveAccess())) {
                            addMessage = access.getMessage();
                        }
                        canAdd = canAdd && accessResult;
                    }
                    catch (Exception e2) {
                        logger.error(e2.getMessage());
                        throw new RuntimeException(e2);
                    }
                }
            }
            String senceTitle = "";
            if (sceneField != null && canAdd) {
                String sceneValue = (String)dimensionValueSet.getValue(this.dFDimensionParser.getSqlDimensionName(sceneField));
                List<String> sceneLegalValues = this.dFDimensionParser.searchReferRelation(dwValue, masterField.getId(), sceneField, dimensionValueSet, masterField, periodField, dataSchemeKey);
                if (sceneLegalValues != null && !Arrays.asList(sceneLegalValues.get(0).split(";")).contains(sceneValue)) {
                    canAdd = false;
                    Map<String, DataFillDimensionTitle> senceMap = this.dFDimensionParser.getDimensionTitle(sceneField, Collections.singletonList(sceneValue), null, model);
                    senceTitle = senceMap.get(sceneValue).getTitle();
                }
            }
            boolean addReason = StringUtils.hasLength(addMessage);
            if (canAdd) continue;
            DimensionValueSet set = this.dFDimensionParser.sqlDimensionToEntityId(context, (DimensionValueSet)dimensionValueSets.get(0));
            List<List<DFDimensionValue>> errors = confirmResult.getErrors();
            ArrayList<DFDimensionValue> error = new ArrayList<DFDimensionValue>();
            if (errorMessage.length() == 0) {
                if (sceneField != null && StringUtils.hasLength(senceTitle)) {
                    errorMessage.append(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.scene}}")).append(senceTitle).append(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.andOrg}}")).append(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.match}}"));
                }
                if (!orgOnly) {
                    errorMessage.append(NrDataFillI18nUtil.buildCode("nr.dataFill.cantAddRowInBoth"));
                } else {
                    errorMessage.append(NrDataFillI18nUtil.buildCode("nr.dataFill.cantAddRowInOrg"));
                }
            }
            if (addReason) {
                errorReasonMessage.append(" ");
            }
            if (!orgOnly) {
                errorMessage.append("[");
            }
            for (int i = 0; i < set.size(); ++i) {
                DFDimensionValue value = new DFDimensionValue();
                String dValue = (String)set.getValue(i);
                value.setName(set.getName(i));
                value.setValues(dValue);
                error.add(value);
                QueryField queryField = queryFieldMap.get(set.getName(i));
                if (queryField.getFieldType() == FieldType.PERIOD && orgOnly) continue;
                Map<String, DataFillDimensionTitle> map = this.dFDimensionParser.getDimensionTitle(queryField, Collections.singletonList(dValue), null, model);
                errorMessage.append(map.get(dValue).getTitle()).append(" ,");
                if (!addReason) continue;
                errorReasonMessage.append(map.get(dValue).getTitle()).append(" ");
            }
            if (addReason) {
                errorReasonMessage.append(":").append(addMessage).append(" ");
            }
            if (!orgOnly) {
                errorMessage.deleteCharAt(errorMessage.length() - 1);
                errorMessage.append("] ");
            }
            errors.add(error);
        }
        if (orgOnly && errorMessage.length() > 1) {
            errorMessage.deleteCharAt(errorMessage.length() - 1);
        }
        if (errorMessage.length() > 150) {
            errorMessage.delete(150, errorMessage.length()).append("...");
        }
        confirmResult.setSuccess(confirmResult.getErrors().isEmpty());
        if (!confirmResult.isSuccess()) {
            errorMessage.append((CharSequence)errorReasonMessage);
            confirmResult.setMessage(errorMessage.toString());
        }
        return confirmResult;
    }

    private String maxValues2String(DFDimensionValue dfDimensionValue, DataFillContext context, QueryField queryField) {
        String values = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
        String maxValue = this.dfDimensionValueGetService.getMaxValues(dfDimensionValue, context.getModel());
        StringBuilder priodList = new StringBuilder();
        if (StringUtils.hasLength(maxValue)) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
            if (periodEntity.getPeriodType() != PeriodType.CUSTOM) {
                PeriodWrapper startPeriod = new PeriodWrapper(values);
                PeriodWrapper stopPeriod = new PeriodWrapper(maxValue);
                ArrayList peiodWrapperList = PeriodUtil.getPeiodWrapperList((PeriodWrapper)startPeriod, (PeriodWrapper)stopPeriod);
                for (PeriodWrapper periodWrapper : peiodWrapperList) {
                    priodList.append(periodWrapper.toString()).append(";");
                }
            } else {
                IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(queryField.getId());
                List periodItems = periodProvider.getPeriodItems();
                boolean begin = false;
                for (IPeriodRow periodRow : periodItems) {
                    String code = periodRow.getCode();
                    if (values.equals(code)) {
                        begin = true;
                    }
                    if (begin) {
                        priodList.append(code).append(";");
                    }
                    if (!begin || !maxValue.equals(code)) continue;
                    begin = false;
                    break;
                }
            }
        }
        if (priodList.lastIndexOf(";") == priodList.length() - 1) {
            priodList.deleteCharAt(priodList.length() - 1);
        }
        return priodList.toString();
    }

    private void saveDataAdjustFieldProcess(DataFillDataSaveInfo saveInfo) {
        List<DataFillDataSaveRow> saveRows = saveInfo.getAdds();
        List<DataFillDataSaveRow> modifyRows = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deleteRows = saveInfo.getDeletes();
        DataFillContext context = saveInfo.getContext();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        DataScheme scheme = this.dataSchemeService.getDataSchemeByCode(periodField.getDataSchemeCode());
        if (null == scheme) {
            return;
        }
        boolean enableAdjust = this.dataSchemeService.enableAdjustPeriod(scheme.getKey());
        String adjustName = periodField.getSimplifyFullCode() + "._BINDING_";
        if (enableAdjust) {
            DFDimensionValue dimensionValue;
            DFDimensionValue adjust;
            Optional<DFDimensionValue> findFirst;
            boolean find;
            List<DFDimensionValue> dimensionValueList;
            if (!saveRows.isEmpty()) {
                for (DataFillDataSaveRow dataFillDataSaveRow : saveRows) {
                    dimensionValueList = dataFillDataSaveRow.getDimensionValues();
                    find = false;
                    for (DFDimensionValue dimensionValue2 : dimensionValueList) {
                        if (!dimensionValue2.getName().equals(adjustName)) continue;
                        dimensionValue2.setName("ADJUST");
                        find = true;
                        break;
                    }
                    if (find || (findFirst = dimensionValueList.stream().filter(e -> e.getName().equals("ADJUST")).findFirst()).isPresent()) continue;
                    adjust = new DFDimensionValue();
                    adjust.setName("ADJUST");
                    adjust.setValues("0");
                    dimensionValueList.add(adjust);
                }
            }
            if (!modifyRows.isEmpty()) {
                for (DataFillDataSaveRow dataFillDataSaveRow : modifyRows) {
                    dimensionValueList = dataFillDataSaveRow.getDimensionValues();
                    find = false;
                    findFirst = dimensionValueList.iterator();
                    while (findFirst.hasNext()) {
                        dimensionValue = findFirst.next();
                        if (!dimensionValue.getName().equals(adjustName)) continue;
                        dimensionValue.setName("ADJUST");
                        find = true;
                        break;
                    }
                    if (find || (findFirst = dimensionValueList.stream().filter(e -> e.getName().equals("ADJUST")).findFirst()).isPresent()) continue;
                    adjust = new DFDimensionValue();
                    adjust.setName("ADJUST");
                    adjust.setValues("0");
                    dimensionValueList.add(adjust);
                }
            }
            if (!deleteRows.isEmpty()) {
                for (DataFillDataDeleteRow dataFillDataDeleteRow : deleteRows) {
                    dimensionValueList = dataFillDataDeleteRow.getDimensionValues();
                    find = false;
                    findFirst = dimensionValueList.iterator();
                    while (findFirst.hasNext()) {
                        dimensionValue = findFirst.next();
                        if (!dimensionValue.getName().equals(adjustName)) continue;
                        dimensionValue.setName("ADJUST");
                        find = true;
                    }
                    if (find || (findFirst = dimensionValueList.stream().filter(e -> e.getName().equals("ADJUST")).findFirst()).isPresent()) continue;
                    adjust = new DFDimensionValue();
                    adjust.setName("ADJUST");
                    adjust.setValues("0");
                    dimensionValueList.add(adjust);
                }
            }
            context.getModel().getQueryFields().add(this.getVirtualAdjustQueryField(periodField));
        }
    }

    private boolean isEnableAdjust(Map<DimensionValueSet, List<DataFillBaseCell>> result, DataFillContext context) {
        for (DimensionValueSet dimensionValueSet : result.keySet()) {
            if (dimensionValueSet.getValue("ADJUST") == null) continue;
            return true;
        }
        if (context.getCaches().containsKey("ADJUST")) {
            String schemeKey = (String)context.getCaches().get("ADJUST");
            List adjustPeriodList = this.adjustPeriodDesignService.query(schemeKey);
            return !adjustPeriodList.isEmpty();
        }
        return false;
    }
}

