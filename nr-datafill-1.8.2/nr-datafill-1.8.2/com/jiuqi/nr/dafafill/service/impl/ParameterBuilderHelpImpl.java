/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.select.common.RunType
 *  com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.DefaultValueType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.enums.SelectType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.ParameterBuilderHelp;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ParameterBuilderHelpImpl
implements ParameterBuilderHelp {
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    PeriodEngineService periodEngineService;
    @Autowired
    IRuntimePeriodModuleService runtimePeriodModuleService;

    @Override
    public List<ParameterModel> getParameterModels(DataFillContext context, ConditionField fromCondition, boolean addConditionField) throws Exception {
        ArrayList<ParameterModel> list = new ArrayList<ParameterModel>();
        DataFillModel model = context.getModel();
        Map<String, QueryField> queryFieldsMap = this.dFDimensionParser.getQueryFieldsMap(context);
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        List<ConditionField> conditionFields = model.getConditionFields();
        QueryField periodField = null;
        List<QueryField> periodList = fieldTypeQueryFields.get((Object)FieldType.PERIOD);
        if (null != periodList && periodList.size() > 0) {
            periodField = periodList.get(0);
        }
        QueryField masterQueryField = null;
        List<QueryField> masterQueryFieldLists = fieldTypeQueryFields.get((Object)FieldType.MASTER);
        if (null != masterQueryFieldLists && masterQueryFieldLists.size() > 0) {
            masterQueryField = masterQueryFieldLists.get(0);
        }
        ArrayList<ConditionField> newConditionFields = new ArrayList<ConditionField>();
        if (fromCondition != null) {
            newConditionFields.add(fromCondition);
        } else {
            if (addConditionField) {
                if (null == conditionFields) {
                    conditionFields = new ArrayList<ConditionField>();
                    model.setConditionFields(conditionFields);
                }
                QueryField tempPeriod = periodField;
                Optional<ConditionField> findPeriod = conditionFields.stream().filter(e -> e.getFullCode().equals(tempPeriod.getFullCode())).findAny();
                if (!findPeriod.isPresent()) {
                    ConditionField priodContion = new ConditionField();
                    priodContion.setFullCode(tempPeriod.getFullCode());
                    priodContion.setDefaultBinding("");
                    priodContion.setDefaultValueType(DefaultValueType.CURRENT);
                    priodContion.setDefaultMaxValueType(DefaultValueType.CURRENT);
                    priodContion.setSelectType(SelectType.SINGLE);
                    conditionFields.add(0, priodContion);
                }
                QueryField tempMaster = masterQueryField;
                Optional<ConditionField> findMaster = conditionFields.stream().filter(e -> e.getFullCode().equals(tempMaster.getFullCode())).findAny();
                if (!findMaster.isPresent()) {
                    ConditionField contion = new ConditionField();
                    contion.setFullCode(tempMaster.getFullCode());
                    contion.setDefaultBinding("");
                    contion.setDefaultValueType(DefaultValueType.UCURRENTDIRECTSUB);
                    contion.setDefaultMaxValueType(DefaultValueType.UCURRENTDIRECTSUB);
                    contion.setSelectType(SelectType.NONE);
                    conditionFields.add(1, contion);
                }
            }
            newConditionFields.addAll(conditionFields);
        }
        String taskKey = model.getExtendedData().get("TASKKEY");
        String defaultBiPeriod = "";
        if (periodField != null && StringUtils.hasText(taskKey)) {
            TaskDefine taskDefine = this.runTime.queryTaskDefine(taskKey);
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            if (periodProvider.getPeriodEntity().getPeriodType() == PeriodType.CUSTOM) {
                periodField.setMinValue(StringUtils.hasText(taskDefine.getFromPeriod()) ? taskDefine.getFromPeriod() : periodProvider.getPeriodCodeRegion()[0]);
                periodField.setMaxValue(StringUtils.hasText(taskDefine.getToPeriod()) ? taskDefine.getToPeriod() : periodProvider.getPeriodCodeRegion()[periodProvider.getPeriodCodeRegion().length - 1]);
            } else {
                List periodLinkDefines = this.runTime.querySchemePeriodLinkByTask(taskKey);
                List periods = periodLinkDefines.stream().map(SchemePeriodLinkDefine::getPeriodKey).sorted().collect(Collectors.toList());
                periodField.setMinValue((String)periods.get(0));
                periodField.setMaxValue((String)periods.get(periods.size() - 1));
                String periodFullCode = periodField.getFullCode();
                Optional<ConditionField> periodCondition = newConditionFields.stream().filter(e -> e.getFullCode().equals(periodFullCode)).findFirst();
                if (periodCondition.isPresent() && (periodCondition.get().getDefaultValueType() == DefaultValueType.CURRENT || periodCondition.get().getDefaultValueType() == DefaultValueType.LAST)) {
                    String defaultPeriod;
                    String currentPeriod = this.runtimePeriodModuleService.queryOffsetPeriod(taskKey, RunType.RUNTIME);
                    String string = defaultPeriod = periods.contains(currentPeriod) ? currentPeriod : (String)periods.get(periods.size() - 1);
                    if (periodCondition.get().getDefaultValueType() == DefaultValueType.LAST) {
                        String priorPeriod = periodProvider.priorPeriod(currentPeriod);
                        defaultPeriod = periods.contains(priorPeriod) ? priorPeriod : currentPeriod;
                    }
                    String finalDefaultPeriod = defaultPeriod;
                    Optional<IPeriodRow> first = periodProvider.getPeriodItems().stream().filter(e -> e.getCode().equals(finalDefaultPeriod)).findFirst();
                    if (first.isPresent()) {
                        defaultBiPeriod = first.get().getTimeKey();
                    } else {
                        PeriodWrapper pw = new PeriodWrapper(defaultPeriod);
                        defaultBiPeriod = TimeDimUtils.periodWrapperToTimeKey((PeriodWrapper)pw);
                    }
                }
            }
        }
        for (ConditionField conditionField : newConditionFields) {
            QueryField queryField = queryFieldsMap.get(conditionField.getFullCode());
            if (null == fromCondition && queryField.getFieldType() == FieldType.PERIOD && !this.showPeriodCondition(model, queryFieldsMap)) continue;
            ParameterModel parameterModel = new ParameterModel();
            if (queryField.getFieldType() == FieldType.SCENE) {
                parameterModel.setNullable(false);
            }
            this.buildName(parameterModel, queryField);
            this.buildAlias(parameterModel, queryField);
            this.buildDataSource(parameterModel, queryField, conditionField, model.getExtendedData());
            this.buildDataType(parameterModel, queryField);
            SelectType selectType = conditionField.getSelectType();
            if (conditionField.getDefaultValueType() == DefaultValueType.USELECTION) {
                parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
                parameterModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
            } else if (queryField.getFieldType() == FieldType.MASTER) {
                parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
                parameterModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
            } else {
                switch (selectType) {
                    case MULTIPLE: {
                        parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
                        parameterModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
                        break;
                    }
                    case RANGE: {
                        parameterModel.setSelectMode(ParameterSelectMode.RANGE);
                        parameterModel.setValueConfig((AbstractParameterValueConfig)new ParameterRangeValueConfig());
                        break;
                    }
                    default: {
                        parameterModel.setSelectMode(ParameterSelectMode.SINGLE);
                        parameterModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
                    }
                }
            }
            if (queryField.getFieldType() == FieldType.PERIOD && StringUtils.hasText(taskKey)) {
                this.buildDefaultValue(parameterModel, conditionField, fieldTypeQueryFields, false, defaultBiPeriod);
            } else {
                this.buildDefaultValue(parameterModel, conditionField, fieldTypeQueryFields, false, null);
            }
            if (selectType == SelectType.RANGE) {
                this.buildDefaultValue(parameterModel, conditionField, fieldTypeQueryFields, true, null);
            }
            this.buildOther(parameterModel, queryField, conditionField);
            if (null != periodField && queryField.getFieldType() == FieldType.MASTER && this.masterEnableVersion(model, queryFieldsMap)) {
                parameterModel.getValueConfig().getDepends().add(new ParameterDependMember("NR_PERIOD_" + periodField.getSimplifyFullCode(), null));
            }
            parameterModel.setHidden(!conditionField.isQuickCondition());
            list.add(parameterModel);
        }
        return list;
    }

    private boolean showPeriodCondition(DataFillModel model, Map<String, QueryField> queryFieldsMap) {
        if (model.getModelType() == ModelType.TASK) {
            IEntityDefine entityDefine;
            List<ConditionField> conditionFields = model.getConditionFields();
            QueryField unit = null;
            for (ConditionField condition : conditionFields) {
                QueryField queryField = queryFieldsMap.get(condition.getFullCode());
                if (queryField.getFieldType() != FieldType.MASTER) continue;
                unit = queryField;
                break;
            }
            if (unit != null && (entityDefine = this.entityMetaService.queryEntity(unit.getId())) != null) {
                return entityDefine.getVersion() == 1;
            }
            return false;
        }
        return true;
    }

    private boolean masterEnableVersion(DataFillModel model, Map<String, QueryField> queryFieldsMap) {
        IEntityDefine entityDefine;
        List<ConditionField> conditionFields = model.getConditionFields();
        QueryField unit = null;
        for (ConditionField condition : conditionFields) {
            QueryField queryField = queryFieldsMap.get(condition.getFullCode());
            if (queryField.getFieldType() != FieldType.MASTER) continue;
            unit = queryField;
            break;
        }
        if (unit != null && (entityDefine = this.entityMetaService.queryEntity(unit.getId())) != null) {
            return entityDefine.getVersion() == 1;
        }
        return false;
    }

    private void buildOther(ParameterModel parameterModel, QueryField queryField, ConditionField conditionField) {
        SelectType selectType = conditionField.getSelectType();
        if (queryField.getFieldType() == FieldType.PERIOD) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
            if (periodEntity.getPeriodType() != PeriodType.CUSTOM) {
                if (this.enableAdjust(queryField, conditionField)) {
                    parameterModel.setWidgetType(101);
                } else {
                    parameterModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
                }
            } else if (selectType == SelectType.MULTIPLE) {
                parameterModel.setWidgetType(ParameterWidgetType.POPUP.value());
            } else {
                parameterModel.setShowSearchWidget(false);
                parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
            }
        } else if (selectType == SelectType.SINGLE) {
            parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
            parameterModel.setShowSearchWidget(false);
        } else if (queryField.getFieldType() == FieldType.MASTER) {
            parameterModel.setWidgetType(ParameterWidgetType.UNITSELECTOR.value());
        } else {
            parameterModel.setWidgetType(ParameterWidgetType.POPUP.value());
        }
    }

    private void buildDataSource(ParameterModel parameterModel, QueryField queryField, ConditionField conditionField, Map<String, String> extendedData) throws Exception {
        String dsType = null;
        if (queryField.getFieldType() == FieldType.PERIOD) {
            dsType = this.enableAdjust(queryField, conditionField) ? "com.jiuqi.publicparam.datasource.adjustDate" : "com.jiuqi.publicparam.datasource.date";
        } else if (queryField.getFieldType() != FieldType.TABLEDIMENSION || queryField.getFieldType() == FieldType.TABLEDIMENSION && StringUtils.hasLength(queryField.getExpression())) {
            dsType = "com.jiuqi.publicparam.datasource.dimension";
        }
        NonDataSourceModel dataSourceModel = new NonDataSourceModel();
        if (dsType != null) {
            dataSourceModel = ParameterDataSourceManager.getInstance().getFactory(dsType).newInstance();
            JSONObject modelJson = new JSONObject();
            dataSourceModel.toJson(modelJson);
            if (queryField.getFieldType() == FieldType.PERIOD) {
                DataScheme scheme;
                if ("com.jiuqi.publicparam.datasource.adjustDate".equals(dsType) && (scheme = this.runtimeDataSchemeService.getDataSchemeByCode(queryField.getDataSchemeCode())) != null) {
                    modelJson.put("dataScheme", (Object)scheme.getKey());
                }
                modelJson.put("entityViewId", (Object)queryField.getId());
                IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
                modelJson.put("periodType", periodEntity.getType().type());
                String maxValue = queryField.getMaxValue();
                String minValue = queryField.getMinValue();
                if (StringUtils.hasLength(minValue) && StringUtils.hasLength(maxValue)) {
                    modelJson.put("periodStartEnd", (Object)(minValue + "-" + maxValue));
                }
                parameterModel.setOnlyLeafSelectable(true);
            } else if (queryField.getFieldType() == FieldType.TABLEDIMENSION) {
                modelJson.put("entityViewId", (Object)queryField.getExpression());
            } else {
                modelJson.put("entityViewId", (Object)queryField.getId());
                if (StringUtils.hasLength(queryField.getFilterExpression())) {
                    modelJson.put("formSchemeKey", (Object)queryField.getFilterExpression());
                }
                if (!CollectionUtils.isEmpty(extendedData)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    modelJson.put("properties", (Object)objectMapper.writeValueAsString(extendedData));
                }
            }
            dataSourceModel.fromJson(modelJson);
        }
        parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
    }

    private void buildDataType(ParameterModel parameterModel, QueryField queryField) {
        DataType dataType;
        switch (queryField.getFieldType()) {
            case ZB: 
            case FIELD: 
            case EXPRESSION: 
            case TABLEDIMENSION: {
                dataType = DataType.valueOf((int)queryField.getDataType().getValue());
                break;
            }
            default: {
                dataType = DataType.STRING;
            }
        }
        parameterModel.getDatasource().setDataType(dataType.value());
    }

    private void buildAlias(ParameterModel parameterModel, QueryField queryField) {
        parameterModel.setTitle(queryField.getTitle());
    }

    private void buildName(ParameterModel parameterModel, QueryField queryField) {
        String simplifyFullCode = queryField.getSimplifyFullCode();
        if (queryField.getFieldType() == FieldType.PERIOD) {
            parameterModel.setName("NR_PERIOD_" + simplifyFullCode);
        } else {
            parameterModel.setName(simplifyFullCode);
        }
    }

    private void buildDefaultValue(ParameterModel parameterModel, ConditionField conditionField, Map<FieldType, List<QueryField>> fieldTypeQueryFields, boolean isMaxDefalutValue, String defaultValue) {
        DefaultValueType valueMode = conditionField.getDefaultValueType();
        List<String> values = conditionField.getDefaultValues();
        if (isMaxDefalutValue) {
            valueMode = conditionField.getDefaultMaxValueType();
            if (conditionField.getDefaultMaxValue() != null) {
                values = new ArrayList<String>();
                values.add(conditionField.getDefaultMaxValue());
            }
        }
        String _valueMode = null;
        ExpressionParameterValue _value = null;
        switch (valueMode) {
            case CURRENT: 
            case LAST: {
                if (!StringUtils.hasText(defaultValue)) {
                    String id = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0).getId();
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(id);
                    _valueMode = "expr";
                    _value = new ExpressionParameterValue((valueMode == DefaultValueType.CURRENT ? "-0" : "-1") + String.valueOf((char)periodEntity.getType().code()));
                    break;
                }
                values.add(defaultValue);
                _value = new FixedMemberParameterValue(values);
                break;
            }
            case UCURRENT: {
                _valueMode = "first";
                break;
            }
            case FIRST: {
                _valueMode = "first";
                break;
            }
            case USELECTION: 
            case SPECIFIC: {
                _valueMode = "appoint";
                if (values != null && values.size() > 0) {
                    _value = new FixedMemberParameterValue(values);
                    _value.setBindingData(conditionField.getDefaultBinding());
                    break;
                }
                _valueMode = "none";
                break;
            }
            case UCURRENTDIRECTSUB: {
                _valueMode = "firstChild";
                break;
            }
            case UFILTER: {
                _valueMode = "expr";
                _value = new ExpressionParameterValue(values.get(0));
                break;
            }
            default: {
                _valueMode = "none";
            }
        }
        if (!isMaxDefalutValue) {
            parameterModel.getValueConfig().setDefaultValueMode(_valueMode);
            parameterModel.getValueConfig().setDefaultValue((AbstractParameterValue)_value);
        } else {
            ((ParameterRangeValueConfig)parameterModel.getValueConfig()).setDefaultMaxValueMode(_valueMode);
            ((ParameterRangeValueConfig)parameterModel.getValueConfig()).setDefaultMaxValue((AbstractParameterValue)_value);
        }
    }

    private boolean enableAdjust(QueryField queryField, ConditionField conditionField) {
        if (conditionField.getSelectType() != SelectType.SINGLE) {
            return false;
        }
        DataScheme scheme = this.runtimeDataSchemeService.getDataSchemeByCode(queryField.getDataSchemeCode());
        if (scheme == null) {
            return false;
        }
        return this.runtimeDataSchemeService.enableAdjustPeriod(scheme.getKey());
    }
}

