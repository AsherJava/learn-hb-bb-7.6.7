/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.enums.RatioType;
import com.jiuqi.nr.dafafill.model.enums.ShowContent;
import com.jiuqi.nr.dafafill.model.enums.TableSample;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.tree.DataFillSchemeTree;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DFDimensionQueryFieldParserImpl
implements IDFDimensionQueryFieldParser {
    private static final Logger logger = LoggerFactory.getLogger(DFDimensionQueryFieldParserImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DFDimensionValueGetService dfDimensionValueGetService;
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService schemeService;
    @Autowired
    private DataFillSchemeTree schemeTree;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private static final String QUERY_ENTITY_ERROR_MESSAGE = "\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5b9e\u4f53\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef";

    @Override
    public DimensionValueSet parserGetEntityDimensionValueSet(DataFillContext context) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Map<String, DFDimensionValue> dimensionMap = dimensionValues.stream().collect(Collectors.toMap(DFDimensionValue::getName, v -> v));
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        Set<Map.Entry<FieldType, List<QueryField>>> entrySet = fieldTypeQueryFields.entrySet();
        for (Map.Entry<FieldType, List<QueryField>> entry : entrySet) {
            DFDimensionValue dfDimensionValue;
            String dimensionName;
            QueryField queryField;
            FieldType fieldType = entry.getKey();
            List<QueryField> queryFields = entry.getValue();
            if (fieldType == FieldType.MASTER) {
                queryField = queryFields.get(0);
                dimensionName = queryField.getSimplifyFullCode();
                dfDimensionValue = dimensionMap.get(dimensionName);
                if (null == dfDimensionValue) continue;
                String value = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                if (null != value) {
                    if (!value.contains(";")) {
                        dimensionValueSet.setValue(dimensionName, (Object)value);
                        continue;
                    }
                    dimensionValueSet.setValue(dimensionName, Arrays.asList(value.split(";")));
                    continue;
                }
                dimensionValueSet.setValue(dimensionName, (Object)"");
                continue;
            }
            if (fieldType == FieldType.PERIOD) {
                queryField = queryFields.get(0);
                dimensionName = queryField.getSimplifyFullCode();
                dfDimensionValue = dimensionMap.get(dimensionName);
                DFDimensionValue adjustDimensionValue = dimensionMap.get("ADJUST");
                if (null == dfDimensionValue) continue;
                String values = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                String maxValues = this.dfDimensionValueGetService.getMaxValues(dfDimensionValue, context.getModel());
                if (StringUtils.hasLength(maxValues)) {
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
                    if (periodEntity.getPeriodType() != PeriodType.CUSTOM) {
                        PeriodWrapper startPeriod = new PeriodWrapper(values);
                        PeriodWrapper stopPeriod = new PeriodWrapper(maxValues);
                        ArrayList peiodWrapperList = PeriodUtil.getPeiodWrapperList((PeriodWrapper)startPeriod, (PeriodWrapper)stopPeriod);
                        List priodList = peiodWrapperList.stream().map(e -> e.toString()).collect(Collectors.toList());
                        dimensionValueSet.setValue(dimensionName, priodList);
                    } else {
                        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(queryField.getId());
                        List periodItems = periodProvider.getPeriodItems();
                        ArrayList<String> priodList = new ArrayList<String>();
                        boolean begin = false;
                        for (IPeriodRow periodRow : periodItems) {
                            String code = periodRow.getCode();
                            if (values.equals(code)) {
                                begin = true;
                            }
                            if (begin) {
                                priodList.add(code);
                            }
                            if (!begin || !maxValues.equals(code)) continue;
                            begin = false;
                            break;
                        }
                        dimensionValueSet.setValue(dimensionName, priodList);
                    }
                } else if (null != values) {
                    if (!values.contains(";")) {
                        dimensionValueSet.setValue(dimensionName, (Object)values);
                    } else {
                        dimensionValueSet.setValue(dimensionName, Arrays.asList(values.split(";")));
                    }
                } else {
                    dimensionValueSet.setValue(dimensionName, (Object)"");
                }
                if (null == adjustDimensionValue) continue;
                String adjustValue = this.dfDimensionValueGetService.getValues(adjustDimensionValue, context.getModel());
                if (StringUtils.hasLength(maxValues)) continue;
                if (null != values) {
                    if (values.contains(";")) continue;
                    dimensionValueSet.setValue("ADJUST", (Object)adjustValue);
                    continue;
                }
                dimensionValueSet.setValue(dimensionName, (Object)"");
                continue;
            }
            if (fieldType != FieldType.SCENE) continue;
            for (QueryField queryField2 : queryFields) {
                String dimensionName2 = queryField2.getSimplifyFullCode();
                DFDimensionValue dfDimensionValue2 = dimensionMap.get(dimensionName2);
                if (null == dfDimensionValue2) continue;
                String value = this.dfDimensionValueGetService.getValues(dfDimensionValue2, context.getModel());
                if (null != value) {
                    if (!value.contains(";")) {
                        dimensionValueSet.setValue(dimensionName2, (Object)value);
                        continue;
                    }
                    dimensionValueSet.setValue(dimensionName2, Arrays.asList(value.split(";")));
                    continue;
                }
                dimensionValueSet.setValue(dimensionName2, (Object)"");
            }
        }
        return dimensionValueSet;
    }

    @Override
    public DimensionValueSet convert(List<DFDimensionValue> dimensionValues, DataFillContext context) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        HashSet dimensionNameSet = new HashSet();
        HashSet dfDimensionValueSet = new HashSet();
        dimensionValues.forEach(each -> {
            if (!dimensionNameSet.contains(each.getName())) {
                dimensionNameSet.add(each.getName());
                dfDimensionValueSet.add(each);
            }
        });
        Map<String, DFDimensionValue> dimensionMap = dfDimensionValueSet.stream().collect(Collectors.toMap(DFDimensionValue::getName, v -> v));
        Set<Map.Entry<String, DFDimensionValue>> entrySet = dimensionMap.entrySet();
        for (Map.Entry<String, DFDimensionValue> entry : entrySet) {
            String dimensionName = entry.getKey();
            DFDimensionValue dimensionValue = entry.getValue();
            String value = this.dfDimensionValueGetService.getValues(dimensionValue, context.getModel());
            if (null != value) {
                if (!value.contains(";")) {
                    dimensionValueSet.setValue(dimensionName, (Object)value);
                    continue;
                }
                dimensionValueSet.setValue(dimensionName, Arrays.asList(value.split(";")));
                continue;
            }
            dimensionValueSet.setValue(dimensionName, (Object)"");
        }
        return dimensionValueSet;
    }

    @Override
    public List<DFDimensionValue> reverseParser(DimensionValueSet dimensionValueSet) {
        ArrayList<DFDimensionValue> list = new ArrayList<DFDimensionValue>();
        int size = dimensionValueSet.size();
        for (int i = 0; i < size; ++i) {
            String name = dimensionValueSet.getName(i);
            Object value = dimensionValueSet.getValue(i);
            DFDimensionValue dFDimensionValue = new DFDimensionValue();
            dFDimensionValue.setName(name);
            if (null != value) {
                if (value instanceof List) {
                    List values = (List)value;
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Object object : values) {
                        stringBuffer.append(object.toString()).append(";");
                    }
                    dFDimensionValue.setValues(stringBuffer.substring(0, stringBuffer.length() - 1));
                } else {
                    dFDimensionValue.setValues(value.toString());
                }
            }
            list.add(dFDimensionValue);
        }
        return list;
    }

    @Override
    public Map<FieldType, List<QueryField>> getFieldTypeQueryFields(DataFillContext context) {
        Boolean enableAdjust;
        DataScheme dataScheme;
        HashMap<FieldType, List<QueryField>> result = new HashMap<FieldType, List<QueryField>>();
        DataFillModel model = context.getModel();
        ArrayList<QueryField> allFields = new ArrayList<QueryField>();
        List<QueryField> assistFields = model.getAssistFields();
        List<QueryField> queryFields = model.getQueryFields();
        if (null != queryFields) {
            allFields.addAll(queryFields);
        }
        if (null != assistFields) {
            allFields.addAll(assistFields);
        }
        if (!ModelType.TASK.equals((Object)model.getModelType()) && null != queryFields && !queryFields.isEmpty() && (dataScheme = this.schemeService.getDataSchemeByCode(queryFields.get(0).getDataSchemeCode())) != null && Boolean.TRUE.equals(enableAdjust = this.schemeService.enableAdjustPeriod(dataScheme.getKey().toString()))) {
            QueryField adjustQueryField = new QueryField();
            adjustQueryField.setFieldType(FieldType.ADJUST);
            adjustQueryField.setTitle(FieldType.ADJUST.title());
            adjustQueryField.setFullCode(dataScheme.getCode());
            allFields.add(adjustQueryField);
        }
        for (QueryField queryField : allFields) {
            FieldType fieldType = queryField.getFieldType();
            ArrayList<QueryField> list = (ArrayList<QueryField>)result.get((Object)fieldType);
            if (null == list) {
                list = new ArrayList<QueryField>();
                result.put(fieldType, list);
            }
            list.add(queryField);
        }
        return result;
    }

    @Override
    public Map<String, QueryField> getQueryFieldsMap(DataFillContext context) {
        HashMap<String, QueryField> result = new HashMap<String, QueryField>();
        DataFillModel model = context.getModel();
        ArrayList<QueryField> allFields = new ArrayList<QueryField>();
        List<QueryField> assistFields = model.getAssistFields();
        List<QueryField> queryFields = model.getQueryFields();
        if (null != queryFields) {
            allFields.addAll(queryFields);
        }
        if (null != assistFields) {
            allFields.addAll(assistFields);
        }
        for (QueryField queryField : allFields) {
            String fullCode = queryField.getFullCode();
            result.put(fullCode, queryField);
        }
        return result;
    }

    @Override
    public Map<String, QueryField> getSimplifyQueryFieldsMap(DataFillContext context) {
        HashMap<String, QueryField> result = new HashMap<String, QueryField>();
        DataFillModel model = context.getModel();
        ArrayList<QueryField> allFields = new ArrayList<QueryField>();
        List<QueryField> assistFields = model.getAssistFields();
        List<QueryField> queryFields = model.getQueryFields();
        if (null != queryFields) {
            allFields.addAll(queryFields);
        }
        if (null != assistFields) {
            allFields.addAll(assistFields);
        }
        for (QueryField queryField : allFields) {
            result.put(queryField.getSimplifyFullCode(), queryField);
        }
        return result;
    }

    @Override
    public DimensionValueSet entityIdToSqlDimension(DataFillContext context, DimensionValueSet entityDimensionValueSet) {
        Map simplifyQueryFieldsMap;
        DimensionValueSet newValueSet = new DimensionValueSet();
        String mapCacheKey = "SimplifyQueryFieldsMap";
        if (!context.getCaches().containsKey(mapCacheKey)) {
            simplifyQueryFieldsMap = this.getSimplifyQueryFieldsMap(context);
            context.getCaches().put(mapCacheKey, simplifyQueryFieldsMap);
        } else {
            simplifyQueryFieldsMap = (Map)context.getCaches().get(mapCacheKey);
        }
        DimensionSet dimensionSet = entityDimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String entityDimensionName = dimensionSet.get(i);
            QueryField queryField = (QueryField)simplifyQueryFieldsMap.get(entityDimensionName);
            if (null != queryField && !queryField.getCode().equals("ADJUST")) {
                if (FieldType.PERIOD != queryField.getFieldType()) {
                    String sqlDimensionName = this.entityMetaService.getDimensionName(entityDimensionName);
                    newValueSet.setValue(sqlDimensionName, entityDimensionValueSet.getValue(i));
                    continue;
                }
                String periodDimensionName = this.periodEntityAdapter.getPeriodDimensionName(entityDimensionName);
                newValueSet.setValue(periodDimensionName, entityDimensionValueSet.getValue(i));
                continue;
            }
            if (!entityDimensionName.equals("ADJUST")) continue;
            newValueSet.setValue(entityDimensionName, entityDimensionValueSet.getValue(i));
        }
        return newValueSet;
    }

    @Override
    public String getDimensionName(String fieldKey) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u7ef4\u5ea6\u540d\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
            logger.error(logInfo.toString(), e);
        }
        return dataAssist.getDimensionName(fieldDefine);
    }

    @Override
    public String getDimensionNameByField(QueryField queryField) {
        if (FieldType.PERIOD != queryField.getFieldType()) {
            String sqlDimensionName = this.entityMetaService.getDimensionName(queryField.getSimplifyFullCode());
            return sqlDimensionName;
        }
        String periodDimensionName = this.periodEntityAdapter.getPeriodDimensionName(queryField.getSimplifyFullCode());
        return periodDimensionName;
    }

    @Override
    public DimensionValueSet entityIdToSqlDimension(DataFillContext context, List<DFDimensionValue> dimensionValues) {
        DimensionValueSet newValueSet = new DimensionValueSet();
        Map<String, QueryField> simplifyQueryFieldsMap = this.getSimplifyQueryFieldsMap(context);
        for (int i = 0; i < dimensionValues.size(); ++i) {
            DFDimensionValue dfDimensionValue = dimensionValues.get(i);
            String entityDimensionName = dfDimensionValue.getName();
            String value = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
            Object sqlValue = null;
            sqlValue = null != value ? (!value.contains(";") ? value : Arrays.asList(value.split(";"))) : "";
            QueryField queryField = simplifyQueryFieldsMap.get(entityDimensionName);
            if (null != queryField && !queryField.getCode().equals("ADJUST")) {
                if (FieldType.PERIOD != queryField.getFieldType()) {
                    String sqlDimensionName = this.entityMetaService.getDimensionName(entityDimensionName);
                    newValueSet.setValue(sqlDimensionName, sqlValue);
                    continue;
                }
                String periodDimensionName = this.periodEntityAdapter.getPeriodDimensionName(entityDimensionName);
                newValueSet.setValue(periodDimensionName, sqlValue);
                continue;
            }
            if (!entityDimensionName.equals("ADJUST")) continue;
            newValueSet.setValue(entityDimensionName, sqlValue);
        }
        return newValueSet;
    }

    @Override
    public DimensionValueSet sqlDimensionToEntityId(DataFillContext context, DimensionValueSet dimensionValueSet) {
        HashMap<String, String> dimensionEntityMap = new HashMap<String, String>();
        Map<String, QueryField> simplifyQueryFieldsMap = this.getSimplifyQueryFieldsMap(context);
        Set<Map.Entry<String, QueryField>> entrySet = simplifyQueryFieldsMap.entrySet();
        for (Map.Entry<String, QueryField> entry : entrySet) {
            QueryField queryField = entry.getValue();
            if (FieldType.ZB != queryField.getFieldType() && FieldType.FIELD != queryField.getFieldType() && FieldType.EXPRESSION != queryField.getFieldType() && FieldType.TABLEDIMENSION != queryField.getFieldType()) {
                if (FieldType.PERIOD == queryField.getFieldType()) {
                    String periodDimensionName = this.periodEntityAdapter.getPeriodDimensionName(entry.getKey());
                    dimensionEntityMap.put(periodDimensionName, entry.getKey());
                    continue;
                }
                String sqlDimensionName = this.entityMetaService.getDimensionName(entry.getKey());
                dimensionEntityMap.put(sqlDimensionName, entry.getKey());
                continue;
            }
            dimensionEntityMap.put(entry.getKey(), entry.getKey());
        }
        DimensionValueSet newValueSet = new DimensionValueSet();
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String dimensionName = dimensionSet.get(i);
            if (dimensionEntityMap.containsKey(dimensionName)) {
                newValueSet.setValue((String)dimensionEntityMap.get(dimensionName), dimensionValueSet.getValue(i));
                continue;
            }
            newValueSet.setValue(dimensionName, dimensionValueSet.getValue(i));
        }
        return newValueSet;
    }

    @Override
    public List<QueryField> getAllQueryFields(DataFillContext context) {
        DataFillModel model = context.getModel();
        ArrayList<QueryField> allFields = new ArrayList<QueryField>();
        List<QueryField> assistFields = model.getAssistFields();
        List<QueryField> queryFields = model.getQueryFields();
        if (null != queryFields) {
            allFields.addAll(queryFields);
        }
        if (null != assistFields) {
            allFields.addAll(assistFields);
        }
        return allFields;
    }

    @Override
    public List<QueryField> getDisplayColsQueryFields(DataFillContext context) {
        DataFillModel model = context.getModel();
        ArrayList<QueryField> returnList = new ArrayList<QueryField>();
        ArrayList<QueryField> allFields = new ArrayList<QueryField>();
        List<QueryField> assistFields = model.getAssistFields();
        List<QueryField> queryFields = model.getQueryFields();
        List<String> displayCols = model.getDisplayCols();
        if (null != queryFields) {
            allFields.addAll(queryFields);
        }
        if (null != assistFields) {
            allFields.addAll(assistFields);
        }
        if (null == displayCols || displayCols.isEmpty()) {
            return allFields.stream().filter(queryField -> queryField.getFieldType() == FieldType.ZB || queryField.getFieldType() == FieldType.FIELD || queryField.getFieldType() == FieldType.EXPRESSION || queryField.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
        }
        Map<String, QueryField> map = allFields.stream().collect(Collectors.toMap(QueryField::getFullCode, e -> e));
        for (String fullCode : displayCols) {
            QueryField queryField2 = map.get(fullCode);
            if (queryField2.getFieldType() != FieldType.ZB && queryField2.getFieldType() != FieldType.FIELD && queryField2.getFieldType() != FieldType.EXPRESSION && queryField2.getFieldType() != FieldType.TABLEDIMENSION) continue;
            returnList.add(queryField2);
        }
        return returnList;
    }

    @Override
    public Map<String, DataFillDimensionTitle> getDimensionTitle(QueryField queryField, List<String> values, List<String> periods, DataFillModel model) {
        HashMap<String, DataFillDimensionTitle> resMap;
        block27: {
            block29: {
                block28: {
                    block26: {
                        if (queryField.getFieldType() != FieldType.ADJUST) {
                            HashSet<String> sets = new HashSet<String>();
                            sets.addAll(values);
                            values = new ArrayList<String>();
                            values.addAll(sets);
                        }
                        resMap = new HashMap<String, DataFillDimensionTitle>();
                        if (queryField.getFieldType() != FieldType.PERIOD) break block26;
                        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
                        if (periodEntity.getPeriodType() != PeriodType.CUSTOM) {
                            for (String value : values) {
                                DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                                dimensionTitle.setCode(value);
                                dimensionTitle.setTitle(this.periodEntityAdapter.getPeriodProvider(periodEntity.getCode()).getPeriodTitle(value));
                                resMap.put(value, dimensionTitle);
                            }
                        } else {
                            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(queryField.getId());
                            for (String value : values) {
                                PeriodWrapper periodWrapper = new PeriodWrapper(value);
                                DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                                dimensionTitle.setCode(value);
                                dimensionTitle.setTitle(periodProvider.getPeriodTitle(periodWrapper));
                                resMap.put(value, dimensionTitle);
                            }
                        }
                        break block27;
                    }
                    if (queryField.getFieldType() != FieldType.MASTER) break block28;
                    String sqlDimensionName = this.entityMetaService.getDimensionName(queryField.getId());
                    DimensionValueSet newValueSet = new DimensionValueSet();
                    newValueSet.setValue(sqlDimensionName, values);
                    ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
                    IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                    iEntityQuery.setMasterKeys(newValueSet);
                    iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(queryField.getId()));
                    IEntityTable iEntityTable = null;
                    try {
                        iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
                    }
                    catch (Exception e) {
                        throw new DataFillRuntimeException(QUERY_ENTITY_ERROR_MESSAGE, e);
                    }
                    FieldFormat showFormat = queryField.getShowFormat();
                    ShowContent showContext = ShowContent.TITLE;
                    if (null != showFormat && showFormat.getShowContent() != null) {
                        showContext = showFormat.getShowContent();
                    }
                    List allRows = iEntityTable.getAllRows();
                    for (IEntityRow entityRow : allRows) {
                        AbstractData abstractData = entityRow.getValue("name");
                        AbstractData value = entityRow.getValue("code");
                        String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
                        String showTitle = "";
                        String title = abstractData.getAsString();
                        String code = value.getAsString();
                        switch (showContext) {
                            case TITLE: {
                                showTitle = title;
                                break;
                            }
                            case CODE: {
                                showTitle = code;
                                break;
                            }
                            case CODE_TITLE: {
                                showTitle = code + "|" + title;
                                break;
                            }
                            case TITLE_CODE: {
                                showTitle = title + "|" + code;
                                break;
                            }
                            default: {
                                showTitle = title;
                            }
                        }
                        DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                        dimensionTitle.setCode(code);
                        dimensionTitle.setTitle(showTitle);
                        if (null != model && null != model.getOtherOption() && model.getTableSample() != TableSample.PERIODZBUNIT && model.getOtherOption().isDisplayIndent()) {
                            dimensionTitle.setLevel(parentsEntityKeyDataPath.length);
                        }
                        resMap.put(code, dimensionTitle);
                    }
                    break block27;
                }
                if (queryField.getFieldType() != FieldType.ADJUST) break block29;
                DataScheme scheme = this.runtimeDataSchemeService.getDataSchemeByCode(queryField.getDataSchemeCode());
                List adjustPeriodList = this.adjustPeriodDesignService.query(scheme.getKey());
                HashMap<String, String> adjustPeriodMap = new HashMap<String, String>();
                for (DesignAdjustPeriodDTO adjustPeriod : adjustPeriodList) {
                    adjustPeriodMap.put(adjustPeriod.getCode() + ";" + adjustPeriod.getPeriod(), adjustPeriod.getTitle());
                }
                for (String value : values) {
                    for (String period : periods) {
                        String key = value + ";" + period;
                        DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                        dimensionTitle.setCode(value);
                        if (adjustPeriodMap.containsKey(key)) {
                            dimensionTitle.setTitle((String)adjustPeriodMap.get(key));
                        } else if (value.equals("0")) {
                            dimensionTitle.setTitle("\u4e0d\u8c03\u6574");
                        } else {
                            dimensionTitle.setTitle(value);
                        }
                        resMap.put(key, dimensionTitle);
                    }
                }
                break block27;
            }
            if (queryField.getFieldType() != FieldType.SCENE) break block27;
            String sqlDimensionName = this.entityMetaService.getDimensionName(queryField.getId());
            DimensionValueSet newValueSet = new DimensionValueSet();
            newValueSet.setValue(sqlDimensionName, values);
            ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(newValueSet);
            iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(queryField.getId()));
            IEntityTable iEntityTable = null;
            try {
                iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
            }
            catch (Exception e) {
                throw new DataFillRuntimeException(QUERY_ENTITY_ERROR_MESSAGE, e);
            }
            List allRows = iEntityTable.getAllRows();
            for (IEntityRow entityRow : allRows) {
                AbstractData abstractData = entityRow.getValue("name");
                AbstractData value = entityRow.getValue("code");
                DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                dimensionTitle.setCode(value.getAsString());
                dimensionTitle.setTitle(abstractData.getAsString());
                resMap.put(value.getAsString(), dimensionTitle);
            }
        }
        return resMap;
    }

    @Override
    public Map<String, DataFillDimensionTitle> getDwDimensionTitle(QueryField queryField, List<String> dwValues, List<String> periods, DataFillModel model) {
        HashMap<String, DataFillDimensionTitle> resMap = new HashMap<String, DataFillDimensionTitle>();
        String sqlDimensionName = this.entityMetaService.getDimensionName(queryField.getId());
        HashSet<String> periodSets = new HashSet<String>();
        periodSets.addAll(periods);
        HashMap groupMap = new HashMap();
        for (String periodValue : periodSets) {
            HashSet<String> dwSet = new HashSet<String>();
            dwSet.addAll(dwValues);
            groupMap.put(periodValue, dwSet);
        }
        Set entrySet = groupMap.entrySet();
        DataFillContext context = new DataFillContext();
        context.setModel(model);
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        String dimensionNameByField = this.getDimensionNameByField(periodField);
        for (Map.Entry entry : entrySet) {
            List periodDimension;
            String periodValue = (String)entry.getKey();
            ArrayList dws = new ArrayList();
            Set dwValueSet = (Set)entry.getValue();
            dws.addAll(dwValueSet);
            if (dws.isEmpty()) continue;
            DimensionValueSet newValueSet = new DimensionValueSet();
            newValueSet.setValue(sqlDimensionName, dws);
            newValueSet.setValue(dimensionNameByField, (Object)periodValue);
            ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
            DataScheme dataScheme = this.schemeService.getDataSchemeByCode(queryField.getDataSchemeCode());
            if (null != dataScheme && (periodDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.PERIOD)) != null && !periodDimension.isEmpty()) {
                executorContext.setPeriodView(((DataDimension)periodDimension.get(0)).getDimKey());
            }
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(newValueSet);
            iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(queryField.getId()));
            IEntityTable iEntityTable = null;
            try {
                iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
            }
            catch (Exception e) {
                throw new DataFillRuntimeException(QUERY_ENTITY_ERROR_MESSAGE, e);
            }
            FieldFormat showFormat = queryField.getShowFormat();
            ShowContent showContext = ShowContent.TITLE;
            if (null != showFormat && showFormat.getShowContent() != null) {
                showContext = showFormat.getShowContent();
            }
            List allRows = iEntityTable.getAllRows();
            for (IEntityRow entityRow : allRows) {
                AbstractData abstractData = entityRow.getValue("name");
                AbstractData value = entityRow.getValue("code");
                String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
                String showTitle = "";
                String title = abstractData.getAsString();
                String code = value.getAsString();
                switch (showContext) {
                    case TITLE: {
                        showTitle = title;
                        break;
                    }
                    case CODE: {
                        showTitle = code;
                        break;
                    }
                    case CODE_TITLE: {
                        showTitle = code + "|" + title;
                        break;
                    }
                    case TITLE_CODE: {
                        showTitle = title + "|" + code;
                        break;
                    }
                    default: {
                        showTitle = title;
                    }
                }
                DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                dimensionTitle.setCode(code);
                dimensionTitle.setTitle(showTitle);
                if (null != model && null != model.getOtherOption() && model.getTableSample() != TableSample.PERIODZBUNIT && model.getOtherOption().isDisplayIndent()) {
                    dimensionTitle.setLevel(parentsEntityKeyDataPath.length);
                }
                resMap.put(periodValue + ";" + code, dimensionTitle);
            }
        }
        return resMap;
    }

    @Override
    public String getFormat(QueryField queryField) {
        DataFieldType dataType = queryField.getDataType();
        String format = "";
        FieldFormat showFormat = queryField.getShowFormat();
        if (dataType == DataFieldType.DATE) {
            format = "yyyy-MM-dd";
        } else if (dataType != DataFieldType.BOOLEAN) {
            if (dataType == DataFieldType.INTEGER || dataType == DataFieldType.BIGDECIMAL) {
                if (null == showFormat) {
                    format = "0";
                    return "0";
                }
                int decimal = showFormat.getDecimal();
                boolean permil = showFormat.isPermil();
                StringBuilder builder = new StringBuilder("0");
                if (permil) {
                    builder.append(",0");
                }
                if (decimal > 0) {
                    builder.append(".");
                    for (int i = 0; i < decimal; ++i) {
                        builder.append("0");
                    }
                }
                if (showFormat.getRatioType() == RatioType.PERCENT) {
                    builder.append("%");
                } else if (showFormat.getRatioType() == RatioType.PERMIL) {
                    builder.append("\u2030");
                }
                format = builder.toString();
            } else if (dataType == DataFieldType.STRING && StringUtils.hasLength(queryField.getExpression())) {
                format = null != showFormat ? showFormat.getShowContent().toString() : ShowContent.NONE.toString();
            }
        }
        return format;
    }

    @Override
    public DimensionValueSet dimensionValueIntegration(DataFillContext context, List<DimensionValueSet> value) {
        boolean enableAdjust;
        DimensionValueSet resultDimsionValueSet = new DimensionValueSet();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        Set<Map.Entry<FieldType, List<QueryField>>> entrySet = fieldTypeQueryFields.entrySet();
        for (Map.Entry<FieldType, List<QueryField>> entry : entrySet) {
            FieldType key = entry.getKey();
            if (key != FieldType.PERIOD && key != FieldType.MASTER && key != FieldType.SCENE) continue;
            for (QueryField queryField : entry.getValue()) {
                HashSet<String> dimsionValueSet = new HashSet<String>();
                String entityIdName = queryField.getSimplifyFullCode();
                for (DimensionValueSet dimensionValueSet : value) {
                    Object value1 = dimensionValueSet.getValue(entityIdName);
                    if (value1 instanceof List) {
                        dimsionValueSet.addAll((List)value1);
                        continue;
                    }
                    String dimsionValue = (String)dimensionValueSet.getValue(entityIdName);
                    dimsionValueSet.add(dimsionValue);
                }
                List collect = dimsionValueSet.stream().collect(Collectors.toList());
                if (collect.size() == 1) {
                    resultDimsionValueSet.setValue(entityIdName, collect.get(0));
                    continue;
                }
                resultDimsionValueSet.setValue(entityIdName, collect);
            }
        }
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        DataScheme scheme = this.dataSchemeService.getDataSchemeByCode(periodField.getDataSchemeCode());
        if (scheme != null && (enableAdjust = this.dataSchemeService.enableAdjustPeriod(scheme.getKey()).booleanValue())) {
            HashSet<String> adjustDimsionValueSet = new HashSet<String>();
            for (DimensionValueSet dimensionValueSet : value) {
                String dimsionValue = (String)dimensionValueSet.getValue("ADJUST");
                if (!StringUtils.hasLength(dimsionValue)) {
                    dimsionValue = "0";
                }
                adjustDimsionValueSet.add(dimsionValue);
            }
            if (!adjustDimsionValueSet.isEmpty()) {
                List list = adjustDimsionValueSet.stream().collect(Collectors.toList());
                if (list.size() == 1) {
                    resultDimsionValueSet.setValue("ADJUST", list.get(0));
                } else {
                    resultDimsionValueSet.setValue("ADJUST", list);
                }
            }
        }
        return this.entityIdToSqlDimension(context, resultDimsionValueSet);
    }

    @Override
    public String getSqlDimensionName(QueryField queryField) {
        String entityDimensionName = queryField.getSimplifyFullCode();
        if (FieldType.PERIOD != queryField.getFieldType()) {
            return this.entityMetaService.getDimensionName(entityDimensionName);
        }
        return this.periodEntityAdapter.getPeriodDimensionName(entityDimensionName);
    }

    @Override
    public boolean isMasterMultipleVersion(DataFillContext context) {
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        QueryField unit = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(unit.getId());
        return entityDefine != null && entityDefine.getVersion() == 1;
    }

    @Override
    public List<DataFillDimensionTitle> getPeriodList(DataFillContext context) {
        List<Object> priodList = new ArrayList();
        ArrayList<DataFillDimensionTitle> resList = new ArrayList<DataFillDimensionTitle>();
        List<DFDimensionValue> dimensionValues = context.getDimensionValues();
        Map<String, DFDimensionValue> dimensionMap = dimensionValues.stream().collect(Collectors.toMap(DFDimensionValue::getName, v -> v));
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        Set<Map.Entry<FieldType, List<QueryField>>> entrySet = fieldTypeQueryFields.entrySet();
        for (Map.Entry<FieldType, List<QueryField>> entry : entrySet) {
            IPeriodEntity periodEntity;
            FieldType fieldType = entry.getKey();
            List<QueryField> queryFields = entry.getValue();
            if (fieldType != FieldType.PERIOD) continue;
            QueryField queryField = queryFields.get(0);
            String dimensionName = queryField.getSimplifyFullCode();
            DFDimensionValue dfDimensionValue = dimensionMap.get(dimensionName);
            if (null != dfDimensionValue) {
                String values = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                String maxValue = this.dfDimensionValueGetService.getMaxValues(dfDimensionValue, context.getModel());
                if (StringUtils.hasLength(maxValue)) {
                    IPeriodEntity iPeriodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
                    if (iPeriodEntity.getPeriodType() != PeriodType.CUSTOM) {
                        PeriodWrapper periodWrapper = new PeriodWrapper(values);
                        PeriodWrapper stopPeriod = new PeriodWrapper(maxValue);
                        ArrayList peiodWrapperList = PeriodUtil.getPeiodWrapperList((PeriodWrapper)periodWrapper, (PeriodWrapper)stopPeriod);
                        priodList = peiodWrapperList.stream().map(e -> e.toString()).collect(Collectors.toList());
                    } else {
                        IPeriodProvider iPeriodProvider = this.periodEntityAdapter.getPeriodProvider(queryField.getId());
                        List periodItems = iPeriodProvider.getPeriodItems();
                        priodList = new ArrayList();
                        boolean begin = false;
                        for (IPeriodRow periodRow : periodItems) {
                            String code = periodRow.getCode();
                            if (values.equals(code)) {
                                begin = true;
                            }
                            if (begin) {
                                priodList.add(code);
                            }
                            if (!begin || !maxValue.equals(code)) continue;
                            begin = false;
                            break;
                        }
                    }
                }
            }
            if ((periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId())).getPeriodType() != PeriodType.CUSTOM) {
                for (String string : priodList) {
                    PeriodWrapper periodWrapper = new PeriodWrapper(string);
                    DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                    dimensionTitle.setCode(string);
                    dimensionTitle.setTitle(periodWrapper.toTitleString());
                    resList.add(dimensionTitle);
                }
                continue;
            }
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(queryField.getId());
            for (String string : priodList) {
                PeriodWrapper periodWrapper = new PeriodWrapper(string);
                DataFillDimensionTitle dimensionTitle = new DataFillDimensionTitle();
                dimensionTitle.setCode(string);
                dimensionTitle.setTitle(periodProvider.getPeriodTitle(periodWrapper));
                resList.add(dimensionTitle);
            }
        }
        return resList;
    }

    @Override
    public int getDecimal(String zbID, String fullCode) {
        int res = 0;
        try {
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(zbID);
            IEntityAttribute iEntityAttribute = this.getEntityAttribute(zbID, fullCode);
            if (null != fieldDefine) {
                res = fieldDefine.getFractionDigits();
            } else if (null != iEntityAttribute) {
                res = iEntityAttribute.getDecimal();
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5c0f\u6570\u4f4d\u6570\u9519\u8bef", e);
        }
        return res;
    }

    private IEntityAttribute getEntityAttribute(String zbID, String fullCode) {
        String dsDimensionName;
        String string = dsDimensionName = fullCode.contains("@") ? fullCode.split("@")[0] : null;
        if (dsDimensionName == null) {
            return null;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dsDimensionName);
        if (entityModel == null) {
            return null;
        }
        List showFields = entityModel.getShowFields();
        Map<String, IEntityAttribute> showFieldsMap = showFields.stream().collect(Collectors.toMap(IModelDefineItem::getID, e -> e));
        return showFieldsMap.get(zbID);
    }

    @Override
    public void hideDimensionsProcess(DataFillContext context, String dataSchemeKey, List<QueryField> hiddenQueryField, List<String> hiddenId) {
        if (!StringUtils.hasLength(dataSchemeKey)) {
            return;
        }
        context.setCaches(new HashMap<String, Object>());
        DataScheme dataScheme = this.schemeService.getDataScheme(dataSchemeKey);
        List dims = this.schemeService.getDataSchemeDimension(dataSchemeKey);
        for (DataDimension dim : dims) {
            DimensionType dimensionType = dim.getDimensionType();
            if (dimensionType != DimensionType.DIMENSION || !DataSchemeUtils.isSingleSelect((DataDimension)dim)) continue;
            QueryField f = this.schemeTree.convertSchemeDimToQueryField(dim, dataScheme);
            context.getModel().getAssistFields().add(f);
            DFDimensionValue dfDimensionValue = new DFDimensionValue();
            dfDimensionValue.setName(f.getCode());
            if (context.getDimensionValues() != null) {
                context.getDimensionValues().add(dfDimensionValue);
            }
            hiddenQueryField.add(f);
            hiddenId.add(dim.getDimAttribute());
        }
        if (!hiddenQueryField.isEmpty()) {
            context.getCaches().put("hiddenQueryField", hiddenQueryField);
            context.getCaches().put("hiddenId", hiddenId);
        }
    }

    @Override
    public List<IDataRow> hiddenRowKeysProcess(IReadonlyTable readonlyTable, String dataSchemeKey, DataFillContext context) {
        Map<FieldType, List<QueryField>> fieldTypeListMap = this.getFieldTypeQueryFields(context);
        String dwSqlKey = this.getSqlDimensionName(fieldTypeListMap.get((Object)FieldType.MASTER).get(0));
        QueryField master = fieldTypeListMap.get((Object)FieldType.MASTER).get(0);
        QueryField period = fieldTypeListMap.get((Object)FieldType.PERIOD).get(0);
        ArrayList<QueryField> hiddenField = new ArrayList<QueryField>();
        ArrayList<String> hiddenIds = new ArrayList<String>();
        ArrayList<IDataRow> dataRows = new ArrayList<IDataRow>();
        DataScheme dataScheme = this.schemeService.getDataScheme(dataSchemeKey);
        List dims = this.schemeService.getDataSchemeDimension(dataSchemeKey);
        HashSet<String> rowKeysNameToBeRemoved = new HashSet<String>();
        for (DataDimension dim : dims) {
            DimensionType dimensionType = dim.getDimensionType();
            if (dimensionType != DimensionType.DIMENSION || !DataSchemeUtils.isSingleSelect((DataDimension)dim)) continue;
            QueryField f = this.schemeTree.convertSchemeDimToQueryField(dim, dataScheme);
            rowKeysNameToBeRemoved.add(f.getCode());
            hiddenField.add(f);
            hiddenIds.add(dim.getDimAttribute());
        }
        for (int i = 0; i < readonlyTable.getCount(); ++i) {
            IDataRow dataRow = readonlyTable.getItem(i);
            DimensionValueSet oldValueSet = new DimensionValueSet(dataRow.getRowKeys());
            for (String key : rowKeysNameToBeRemoved) {
                dataRow.getRowKeys().clearValue(key);
            }
            DimensionValueSet newValueSet = new DimensionValueSet(dataRow.getRowKeys());
            this.searchDwHiddenDimensionValue((String)newValueSet.getValue(dwSqlKey), newValueSet, master, period, hiddenField, hiddenIds, true);
            if (!newValueSet.equals((Object)oldValueSet)) continue;
            dataRows.add(dataRow);
        }
        return dataRows;
    }

    @Override
    public void searchDwHiddenDimensionValue(String dwCode, DimensionValueSet newValueSet, QueryField master, QueryField period, List<QueryField> hiddenFields, List<String> hiddenIds, boolean canNull) {
        ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setMasterKeys(newValueSet);
        String rowFilterExpression = " " + master.getTableCode() + "[CODE] = '" + dwCode + "'";
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(master.getId(), rowFilterExpression);
        iEntityQuery.setEntityView(entityView);
        executorContext.setPeriodView(period.getId());
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
            List allRows = iEntityTable.getAllRows();
            for (int i = 0; i < hiddenIds.size(); ++i) {
                String id = hiddenIds.get(i);
                QueryField hiddenField = hiddenFields.get(i);
                AbstractData data = ((IEntityRow)allRows.get(0)).getValue(id);
                newValueSet.setValue(hiddenField.getCode(), (Object)data.getAsString());
                if (!data.isNull || canNull) continue;
                throw new DataFillRuntimeException(hiddenField.getTitle() + "\u4e3a\u7a7a\uff01");
            }
        }
        catch (Exception e) {
            throw new DataFillRuntimeException("\u67e5\u8be2\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
    }

    @Override
    public List<String> searchReferRelation(String searchValue, String searchKey, QueryField scene, DimensionValueSet dimensionValueSet, QueryField master, QueryField period, String dataSchemeKey) {
        String entityId = master.getId();
        String sceneCode = scene.getId();
        ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setMasterKeys(dimensionValueSet);
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(master.getId());
        iEntityQuery.setEntityView(entityView);
        executorContext.setPeriodView(period.getId());
        ReferRelation referRelation = new ReferRelation();
        RunTimeEntityViewDefineImpl define = new RunTimeEntityViewDefineImpl();
        define.setEntityId(searchKey);
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DataDimension report = null;
        if (dimensions != null) {
            report = dimensions.stream().filter(dataDimension -> sceneCode.equals(dataDimension.getDimKey())).findFirst().orElse(null);
        }
        String masterSqlDimensionName = this.getSqlDimensionName(master);
        String sceneSqlDimensionName = this.getSqlDimensionName(scene);
        if (report == null) {
            Object values = null;
            values = searchKey.equals(sceneCode) ? dimensionValueSet.getValue(masterSqlDimensionName) : dimensionValueSet.getValue(sceneSqlDimensionName);
            if (values instanceof List) {
                return (List)values;
            }
            return Arrays.asList(((String)values).split(";"));
        }
        String dimId = report.getDimAttribute();
        if (!StringUtils.hasLength(dimId)) {
            Object values = null;
            values = searchKey.equals(sceneCode) ? dimensionValueSet.getValue(masterSqlDimensionName) : dimensionValueSet.getValue(sceneSqlDimensionName);
            if (values instanceof List) {
                return (List)values;
            }
            return Arrays.asList(((String)values).split(";"));
        }
        List referRelations = this.entityMetaService.getEntityRefer(entityId);
        IEntityRefer refer = null;
        for (IEntityRefer entityRefer : referRelations) {
            if (!entityRefer.getOwnField().equals(dimId)) continue;
            refer = entityRefer;
        }
        if (refer == null) {
            return null;
        }
        referRelation.setRefer(refer);
        referRelation.setRange(Collections.singletonList(searchValue));
        referRelation.setViewDefine((EntityViewDefine)define);
        iEntityQuery.addReferRelation(referRelation);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new DataFillRuntimeException("\u67e5\u8be2\u60c5\u666f\u548c\u673a\u6784\u5173\u8054\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List allRows = iEntityTable.getAllRows();
        ArrayList<String> values = new ArrayList<String>();
        for (IEntityRow allRow : allRows) {
            AbstractData data = searchKey.equals(sceneCode) ? allRow.getValue("code") : allRow.getValue(dimId);
            values.add(data.getAsString());
        }
        return values;
    }

    @Override
    public String getDataSchemeKey(List<QueryField> zbQueryFields) {
        new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        ArrayList<DataTable> dataTables = new ArrayList<DataTable>();
        try {
            List fieldKeys = zbQueryFields.stream().map(e -> e.getId()).collect(Collectors.toList());
            List defines = this.dataDefinitionRuntimeController.queryTableDefinesByFields(fieldKeys);
            for (TableDefine tableDefine : defines) {
                dataTables.add(this.dataSchemeService.getDataTable(tableDefine.getKey()));
            }
            return ((DataTable)dataTables.get(0)).getDataSchemeKey();
        }
        catch (Exception e2) {
            return "";
        }
    }

    @Override
    public Map<String, QueryField> getDimensionNameQueryFieldsMap(DataFillContext context) {
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        HashMap<String, QueryField> dimensionNameQueryFieldsMap = new HashMap<String, QueryField>();
        ArrayList needAdd = new ArrayList();
        needAdd.addAll(fieldTypeQueryFields.getOrDefault((Object)FieldType.PERIOD, new ArrayList()));
        needAdd.addAll(fieldTypeQueryFields.getOrDefault((Object)FieldType.MASTER, new ArrayList()));
        needAdd.addAll(fieldTypeQueryFields.getOrDefault((Object)FieldType.SCENE, new ArrayList()));
        needAdd.addAll(fieldTypeQueryFields.getOrDefault((Object)FieldType.TABLEDIMENSION, new ArrayList()));
        for (QueryField queryField : needAdd) {
            dimensionNameQueryFieldsMap.put(this.getDimensionNameByField(queryField), queryField);
        }
        return dimensionNameQueryFieldsMap;
    }

    @Override
    public List<QueryField> getHideQueryFields(String dataSchemeCode) {
        ArrayList<QueryField> list = new ArrayList<QueryField>();
        DataScheme dataScheme = this.schemeService.getDataSchemeByCode(dataSchemeCode);
        List dims = this.schemeService.getDataSchemeDimension(dataScheme.getKey());
        for (DataDimension dim : dims) {
            DimensionType dimensionType = dim.getDimensionType();
            if (dimensionType != DimensionType.DIMENSION || !DataSchemeUtils.isSingleSelect((DataDimension)dim)) continue;
            QueryField f = this.schemeTree.convertSchemeDimToQueryField(dim, dataScheme);
            list.add(f);
        }
        return list;
    }

    @Override
    public String getDimensionInfoString(DimensionValueSet dimensionValueSet, DataFillContext context) {
        List<QueryField> masterFields;
        dimensionValueSet = this.sqlDimensionToEntityId(context, dimensionValueSet);
        StringBuilder stringBuilder = new StringBuilder();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        if (periodField != null) {
            String pValue = (String)dimensionValueSet.getValue(periodField.getSimplifyFullCode());
            Map<String, DataFillDimensionTitle> map = this.getDimensionTitle(periodField, Collections.singletonList(pValue), null, context.getModel());
            stringBuilder.append("\u65f6\u671f\uff1a").append(map.get(pValue).getTitle());
        }
        if ((masterFields = fieldTypeQueryFields.get((Object)FieldType.MASTER)) != null && !masterFields.isEmpty()) {
            QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            String dValue = (String)dimensionValueSet.getValue(masterField.getSimplifyFullCode());
            Map<String, DataFillDimensionTitle> map = this.getDimensionTitle(masterField, Collections.singletonList(dValue), null, context.getModel());
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\uff0c");
            }
            stringBuilder.append("\u5355\u4f4d\uff1a").append(map.get(dValue).getTitle());
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("  ");
        }
        return stringBuilder.toString();
    }
}

