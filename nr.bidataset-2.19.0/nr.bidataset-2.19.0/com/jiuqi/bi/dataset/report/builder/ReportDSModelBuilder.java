/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.common.util.TimeDimHelper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_TIMEKEY
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
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
 */
package com.jiuqi.bi.dataset.report.builder;

import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.bi.dataset.report.model.ReportDSField;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.helper.PeriodHelper;
import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.common.util.TimeDimHelper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_TIMEKEY;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDSModelBuilder {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private BSSchemeService BSSchemeService;
    @Autowired
    private PeriodHelper periodHelper;

    public void buildModel(ReportDSModel dsModel) throws Exception {
        SummaryScheme summaryScheme;
        dsModel.getCommonFields().clear();
        dsModel.getParameterModels().clear();
        dsModel.getHiers().clear();
        ReportDsModelDefine dsModelDefine = dsModel.getReportDsModelDefine();
        String fromSchemeKey = null;
        String taskKey = dsModelDefine.getReportTaskKey();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new Exception("\u62a5\u8868\u6570\u636e\u96c6\u6a21\u578b\u521d\u59cb\u5316\u51fa\u9519\uff0c\u6ca1\u6709\u627e\u5230key\u4e3a " + taskKey + " \u7684\u4efb\u52a1");
        }
        dsModel.setTaskDefine(taskDefine);
        IEntityDefine unitEntityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        dsModel.setUnitEntityDefnie(unitEntityDefine);
        List dataDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        IPeriodEntity periodEntity = null;
        for (DataDimension dataDimension : dataDimensions) {
            DimensionType dimensionType = dataDimension.getDimensionType();
            if ((dimensionType == DimensionType.UNIT || dimensionType == DimensionType.DIMENSION) && dataDimension.getDimKey() != null) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                if (entityDefine == null) continue;
                dsModel.getSchemeDims().add(entityDefine.getDimensionName());
                continue;
            }
            if (dimensionType != DimensionType.PERIOD) continue;
            periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dataDimension.getDimKey());
        }
        if (periodEntity != null && periodEntity.getPeriodType() == PeriodType.MONTH) {
            dsModel.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
            dsModel.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
        }
        String currentPeriod = this.periodHelper.getCurrentPeriod(taskDefine, periodEntity);
        dsModel.setTaskCurrentPeriod(currentPeriod);
        List schemePeriodLinks = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
        if (schemePeriodLinks != null && schemePeriodLinks.size() > 0) {
            Optional<SchemePeriodLinkDefine> currentPeriodOptional = schemePeriodLinks.stream().filter(link -> link.getPeriodKey().equals(currentPeriod)).findAny();
            fromSchemeKey = currentPeriodOptional.isPresent() ? currentPeriodOptional.get().getSchemeKey() : ((SchemePeriodLinkDefine)schemePeriodLinks.get(schemePeriodLinks.size() - 1)).getSchemeKey();
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, fromSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)env);
        QueryContext context = new QueryContext(executorContext, (IMonitor)new AbstractMonitor());
        List<ReportExpField> reportExpFields = dsModelDefine.getFields();
        for (ReportExpField reportExpField : reportExpFields) {
            this.addExpDSField(context, dsModel, reportExpField, periodEntity);
        }
        String gatherSchemeCode = dsModel.getReportDsModelDefine().getGatherSchemeCode();
        if (StringUtils.isNotEmpty((String)gatherSchemeCode) && (summaryScheme = this.BSSchemeService.findScheme(taskKey, gatherSchemeCode)) != null) {
            dsModel.setGaterDimType(summaryScheme.getTargetDim().getTargetDimType().value);
            dsModel.setGaterDimName(summaryScheme.getTargetDim().getDimValue());
            dsModel.setGatherSchemeKey(summaryScheme.getKey());
        }
        List<Integer> sortFieldIndexes = dsModel.getSortFieldIndexes();
        if (dsModel.needUnitTreeBuilder()) {
            for (int i = sortFieldIndexes.size() - 1; i >= 0; --i) {
                int index = sortFieldIndexes.get(i);
                if (index != dsModel.getUnitKeyIndex()) continue;
                sortFieldIndexes.remove(i);
            }
            sortFieldIndexes.add(dsModel.getUnitOrderIndex());
        } else {
            sortFieldIndexes.clear();
        }
        this.addParameters(dsModel);
    }

    private void addParameters(ReportDSModel dsModel) throws Exception {
        ReportDsModelDefine dsModelDefine = dsModel.getReportDsModelDefine();
        List<ReportDsParameter> paras = dsModelDefine.getParameters();
        ParameterModel periodParaModel = null;
        ParameterModel unitParaModel = null;
        for (ReportDsParameter para : paras) {
            ParameterModel parameterModel = this.buildParameterModel(para, dsModel.getTaskDefine(), dsModel);
            if (parameterModel.getDatasource() instanceof NrPeriodDataSourceModel) {
                periodParaModel = parameterModel;
            } else if (dsModel.getUnitEntityId().startsWith(para.getEntityId())) {
                unitParaModel = parameterModel;
            }
            dsModel.getParameterModels().add(parameterModel);
        }
        if (unitParaModel != null) {
            if (unitParaModel.getSelectMode() == ParameterSelectMode.SINGLE) {
                unitParaModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
                unitParaModel.getDatasource().setBusinessType(DataBusinessType.UNIT_DIM);
            } else if (dsModel.getGaterDimType() == 2) {
                unitParaModel.setWidgetType(ParameterWidgetType.POPUP.value());
            } else {
                unitParaModel.setWidgetType(ParameterWidgetType.UNITSELECTOR.value());
            }
            if (periodParaModel != null) {
                unitParaModel.getValueConfig().getDepends().add(new ParameterDependMember(periodParaModel.getName(), null));
            }
        }
    }

    public ParameterModel buildParameterModel(ReportDsParameter para, TaskDefine taskDefine, ReportDSModel dsModel) {
        ParameterModel parameterModel = new ParameterModel();
        parameterModel.setName(para.getName());
        parameterModel.setMessageAlias(para.getMessageAlias());
        parameterModel.setSelectMode(para.getSelectMode());
        parameterModel.setTitle(para.getTitle());
        String entityId = para.getEntityId();
        IPeriodEntity periodEntity = null;
        if (StringUtils.isNotEmpty((String)entityId)) {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            if (periodAdapter.isPeriodEntity(entityId)) {
                periodEntity = periodAdapter.getPeriodEntity(entityId);
                NrPeriodDataSourceModel dataSourceModel = new NrPeriodDataSourceModel();
                dataSourceModel.setEntityViewId(entityId);
                dataSourceModel.setDataType(6);
                PeriodType periodType = periodEntity.getPeriodType();
                dataSourceModel.setPeriodType(periodType.type());
                com.jiuqi.bi.dataset.model.field.TimeGranularity timeGranularity = TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodType);
                if (timeGranularity != null) {
                    dataSourceModel.setTimegranularity(timeGranularity.value());
                    if (timeGranularity == com.jiuqi.bi.dataset.model.field.TimeGranularity.MONTH && !"Y".equals(periodEntity.getKey())) {
                        dataSourceModel.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
                        dataSourceModel.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
                    }
                }
                if (periodType == PeriodType.CUSTOM) {
                    parameterModel.setOnlyLeafSelectable(true);
                    parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
                } else {
                    parameterModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
                }
                dataSourceModel.setTimekey(true);
                dataSourceModel.setRemote(false);
                dataSourceModel.setBusinessType(DataBusinessType.TIME_DIM);
                parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
                if (dsModel != null) {
                    dsModel.getDimParamMap().put("DATATIME", parameterModel);
                }
            } else if (dsModel.getGaterDimType() == 2) {
                NrCaliberDimDataSourceModel dataSourceModel = new NrCaliberDimDataSourceModel();
                dataSourceModel.setEntityViewId(dsModel.getGaterDimName());
                dataSourceModel.setDataType(6);
                dataSourceModel.setBusinessType(DataBusinessType.GENERAL_DIM);
                parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
                if (dsModel != null) {
                    dsModel.getDimParamMap().put(this.dimensionProvider.getDimensionNameByEntityId(entityId), parameterModel);
                }
            } else {
                NrEntityDataSourceModel dataSourceModel = new NrEntityDataSourceModel();
                if (dsModel.getGaterDimType() == 1) {
                    dataSourceModel.setEntityViewId(dsModel.getGaterDimName());
                } else {
                    dataSourceModel.setEntityViewId(entityId);
                }
                dataSourceModel.setDataType(6);
                dataSourceModel.setBusinessType(DataBusinessType.GENERAL_DIM);
                parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
                if (dsModel != null) {
                    dsModel.getDimParamMap().put(this.dimensionProvider.getDimensionNameByEntityId(entityId), parameterModel);
                }
            }
        }
        parameterModel.setValueConfig(this.getValueConfig(para, periodEntity, dsModel));
        return parameterModel;
    }

    private AbstractParameterValueConfig getValueConfig(ReportDsParameter para, IPeriodEntity periodEntity, ReportDSModel dsModel) {
        FixedMemberParameterValue defaultValue;
        TimeDimHelper helper;
        ExpressionParameterValue expValue;
        FixedMemberParameterValue defaultValue2;
        String currentPeriod;
        boolean isRange = para.getSelectMode() == ParameterSelectMode.RANGE;
        ParameterRangeValueConfig valueConfig = isRange ? new ParameterRangeValueConfig() : new ParameterValueConfig();
        DefaultValueMode defaultValueMode = para.getDefaultValueMode();
        TaskDefine taskDefine = dsModel.getTaskDefine();
        if (defaultValueMode == DefaultValueMode.NONE) {
            valueConfig.setDefaultValueMode("none");
        } else if (defaultValueMode == DefaultValueMode.FIRST) {
            valueConfig.setDefaultValueMode("first");
        } else if (defaultValueMode == DefaultValueMode.FIRST_CHILD) {
            valueConfig.setDefaultValueMode("firstChild");
        } else if (defaultValueMode == DefaultValueMode.CURRENT) {
            try {
                currentPeriod = dsModel.getTaskCurrentPeriod();
                valueConfig.setDefaultValueMode("appoint");
                TimeDimHelper helper2 = new TimeDimHelper();
                defaultValue2 = new FixedMemberParameterValue(Collections.singletonList(helper2.periodToTimeKey(currentPeriod)));
                valueConfig.setDefaultValue((AbstractParameterValue)defaultValue2);
            }
            catch (Exception e) {
                valueConfig.setDefaultValueMode("expr");
                String exp = "-0N";
                int offset = taskDefine.getTaskPeriodOffset();
                if (offset != 0) {
                    exp = String.valueOf(offset) + String.valueOf((char)taskDefine.getPeriodType().code());
                }
                expValue = new ExpressionParameterValue(exp);
                valueConfig.setDefaultValue((AbstractParameterValue)expValue);
            }
        } else if (defaultValueMode == DefaultValueMode.PREVIOUS) {
            try {
                currentPeriod = dsModel.getTaskCurrentPeriod();
                valueConfig.setDefaultValueMode("appoint");
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                String priorPeriod = periodAdapter.getPeriodProvider(periodEntity.getKey()).priorPeriod(currentPeriod);
                helper = new TimeDimHelper();
                defaultValue = new FixedMemberParameterValue(Collections.singletonList(helper.periodToTimeKey(priorPeriod)));
                valueConfig.setDefaultValue((AbstractParameterValue)defaultValue);
            }
            catch (Exception e) {
                valueConfig.setDefaultValueMode("expr");
                ExpressionParameterValue expValue2 = new ExpressionParameterValue("-1" + (char)periodEntity.getPeriodType().code());
                valueConfig.setDefaultValue((AbstractParameterValue)expValue2);
            }
        } else if (defaultValueMode == DefaultValueMode.APPOINT) {
            valueConfig.setDefaultValueMode("appoint");
            String[] defaultValues = para.getDefaultValues();
            FixedMemberParameterValue defaultValue3 = new FixedMemberParameterValue(defaultValues != null ? Arrays.asList(defaultValues) : Collections.emptyList());
            valueConfig.setDefaultValue((AbstractParameterValue)defaultValue3);
        }
        if (isRange) {
            String currentPeriod2;
            ParameterRangeValueConfig rangeValueConfig = valueConfig;
            DefaultValueMode defaultMaxValueMode = para.getDefaultMaxValueMode();
            if (defaultMaxValueMode == DefaultValueMode.NONE) {
                rangeValueConfig.setDefaultMaxValueMode("none");
            } else if (defaultMaxValueMode == DefaultValueMode.FIRST) {
                rangeValueConfig.setDefaultMaxValueMode("first");
            } else if (defaultMaxValueMode == DefaultValueMode.FIRST_CHILD) {
                rangeValueConfig.setDefaultMaxValueMode("firstChild");
            } else if (defaultMaxValueMode == DefaultValueMode.CURRENT && periodEntity != null) {
                try {
                    currentPeriod2 = dsModel.getTaskCurrentPeriod();
                    rangeValueConfig.setDefaultMaxValueMode("appoint");
                    helper = new TimeDimHelper();
                    defaultValue = new FixedMemberParameterValue(Collections.singletonList(helper.periodToTimeKey(currentPeriod2)));
                    rangeValueConfig.setDefaultMaxValue((AbstractParameterValue)defaultValue);
                }
                catch (Exception e) {
                    rangeValueConfig.setDefaultMaxValueMode("expr");
                    String exp = "-0N";
                    int offset = taskDefine.getTaskPeriodOffset();
                    if (offset != 0) {
                        exp = String.valueOf(offset) + String.valueOf((char)taskDefine.getPeriodType().code());
                    }
                    ExpressionParameterValue expValue3 = new ExpressionParameterValue(exp);
                    rangeValueConfig.setDefaultMaxValue((AbstractParameterValue)expValue3);
                }
            } else if (defaultMaxValueMode == DefaultValueMode.PREVIOUS && periodEntity != null) {
                try {
                    currentPeriod2 = dsModel.getTaskCurrentPeriod();
                    rangeValueConfig.setDefaultMaxValueMode("appoint");
                    IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                    String priorPeriod = periodAdapter.getPeriodProvider(periodEntity.getKey()).priorPeriod(currentPeriod2);
                    TimeDimHelper helper3 = new TimeDimHelper();
                    FixedMemberParameterValue defaultValue4 = new FixedMemberParameterValue(Collections.singletonList(helper3.periodToTimeKey(priorPeriod)));
                    rangeValueConfig.setDefaultMaxValue((AbstractParameterValue)defaultValue4);
                }
                catch (Exception e) {
                    rangeValueConfig.setDefaultMaxValueMode("expr");
                    expValue = new ExpressionParameterValue("-1" + (char)periodEntity.getPeriodType().code());
                    rangeValueConfig.setDefaultMaxValue((AbstractParameterValue)expValue);
                }
            } else if (defaultMaxValueMode == DefaultValueMode.APPOINT) {
                rangeValueConfig.setDefaultMaxValueMode("appoint");
                defaultValue2 = new FixedMemberParameterValue(Collections.singletonList(para.getDefaultMaxValue()));
                rangeValueConfig.setDefaultMaxValue((AbstractParameterValue)defaultValue2);
            }
        }
        return valueConfig;
    }

    private void addHierarchys(ReportDSModel dsModel, String keyField, String parentField, String prefix) {
        DSHierarchy hierarchy = new DSHierarchy();
        hierarchy.setName(prefix + "_TREE");
        hierarchy.setTitle(prefix + "\u7ea7\u6b21");
        hierarchy.setType(DSHierarchyType.PARENT_HIERARCHY);
        hierarchy.setParentFieldName(parentField);
        hierarchy.getLevels().add(keyField);
        List hiers = dsModel.getHiers();
        hiers.add(hierarchy);
    }

    private ReportDSField addExpDSField(QueryContext context, ReportDSModel dsModel, ReportExpField reportExpField, IPeriodEntity periodEntity) throws SyntaxException {
        List fields = dsModel.getCommonFields();
        ReportDSField dsField = new ReportDSField();
        String exp = reportExpField.getExp();
        DefinitionsCache cache = context.getExeContext().getCache();
        PeriodType periodType = periodEntity.getPeriodType();
        int fieldIndex = fields.size();
        dsField.setName(reportExpField.getCode());
        dsField.setTitle(reportExpField.getTitle());
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        String messageAlias = reportExpField.getMessageAlias();
        if (StringUtils.isEmpty((String)messageAlias)) {
            messageAlias = reportExpField.getCode();
        }
        dsField.setMessageAlias(messageAlias);
        dsField.setKeyField(reportExpField.getKeyField());
        dsField.setIndex(fieldIndex);
        FieldType fieldType = reportExpField.getFieldType();
        dsField.setFieldType(fieldType);
        int dataType = reportExpField.getDataType();
        if (dataType == 3) {
            dataType = 10;
        }
        dsField.setValType(dataType);
        try {
            IExpression expression = cache.getFormulaParser(true).parseEval(exp, (IContext)context);
            dsField.setEvalExpression(expression);
            if (fieldType == FieldType.TIME_DIM) {
                dsField.setTimekey(true);
                dsField.setTimegranularity(TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodType));
                dsField.setDataPattern("yyyyMMdd");
                if (dsField.getTimegranularity() != null) {
                    dsField.setShowPattern(BqlTimeDimUtils.getTimeKeyFieldShowPattern((TimeGranularity)TimeGranularity.valueOf((int)dsField.getTimegranularity().value()), (String)periodEntity.getKey()));
                }
                dsModel.getPublicDimFields().add(dsField.getName());
            } else if (fieldType == FieldType.GENERAL_DIM) {
                VariableDataNode varNode;
                IASTNode root = expression.getChild(0);
                if (root instanceof DynamicDataNode) {
                    DynamicDataNode dataNode = (DynamicDataNode)root;
                    QueryField queryField = dataNode.getQueryField();
                    QueryTable table = queryField.getTable();
                    if (table.isDimensionTable()) {
                        String entityId = this.entityMetaService.getEntityIdByCode(table.getTableName());
                        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
                        if (entityModel.getBizKeyField() != null && entityModel.getBizKeyField().getCode().equals(queryField.getFieldCode())) {
                            if (entityId.equals(dsModel.getUnitEntityId())) {
                                dsModel.setUnitKeyIndex(fieldIndex);
                            }
                            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                            if (dsModel.getSchemeDims().contains(entityDefine.getDimensionName())) {
                                dsModel.getPublicDimFields().add(dsField.getName());
                                dsModel.getSortFieldIndexes().add(fieldIndex);
                            }
                        } else if (entityModel.getParentField() != null && entityModel.getParentField().getCode().equals(queryField.getFieldCode())) {
                            if (entityId.equals(dsModel.getUnitEntityId())) {
                                dsModel.setUnitParentIndex(fieldIndex);
                            }
                            this.addHierarchys(dsModel, dsField.getKeyField(), dsField.getName(), table.getTableName());
                        } else if (entityModel.getOrderField() != null && entityModel.getOrderField().getCode().equals(queryField.getFieldCode()) && entityId.equals(dsModel.getUnitEntityId())) {
                            dsModel.setUnitOrderIndex(fieldIndex);
                        }
                    }
                } else if (root instanceof VariableDataNode && (varNode = (VariableDataNode)root).getVariable() instanceof VarCUR_TIMEKEY) {
                    dsModel.getPublicDimFields().add(dsField.getName());
                }
            } else if (fieldType == FieldType.MEASURE) {
                boolean isFloat = false;
                for (IASTNode node : expression) {
                    DynamicDataNode dataNode;
                    if (!(node instanceof DynamicDataNode) || (dataNode = (DynamicDataNode)node).getStatisticInfo() != null) continue;
                    QueryField queryField = dataNode.getQueryField();
                    QueryTable table = queryField.getTable();
                    TableModelRunInfo tableInfo = cache.getDataModelDefinitionsCache().getTableInfo(table.getTableName());
                    if (tableInfo.getBizOrderField() == null) continue;
                    isFloat = true;
                }
                dsField.setFixZb(!isFloat);
            }
        }
        catch (Exception e) {
            context.getMonitor().exception(e);
        }
        fields.add(dsField);
        return dsField;
    }
}

