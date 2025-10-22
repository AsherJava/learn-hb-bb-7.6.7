/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.summary.SummaryScheme
 *  com.jiuqi.nr.configuration.controller.ISystemOptionManager
 *  com.jiuqi.nr.data.engine.gather.GatherCondition
 *  com.jiuqi.nr.data.engine.gather.GatherDirection
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.data.engine.gather.IDataGather
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider
 *  com.jiuqi.nr.data.logic.facade.extend.param.AutoCalFormFmlParam
 *  com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CalPar
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportDataLinkFinder
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.xlib.utils.StringUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.configuration.controller.ISystemOptionManager;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.AutoCalFormFmlParam;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CalPar;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportDataLinkFinder;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.common.BatchSummaryConst;
import com.jiuqi.nr.jtable.common.SummarySchemeUtils;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.exception.NotFoundFieldException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.TableData;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodRegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.query.dataengine.env.ExecutorContextHelper;
import com.jiuqi.nr.jtable.service.ICheckResultExtraService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableFileService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.impl.VariableExectuteFormula;
import com.jiuqi.nr.jtable.util.CheckTransformUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.EntityFmlExecEnvironment;
import com.jiuqi.nr.jtable.util.GatherTableUtil;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nr.jtable.util.UUIDUtil;
import com.jiuqi.xlib.utils.StringUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableDataEngineServiceImpl
implements IJtableDataEngineService {
    private static final Logger logger = LoggerFactory.getLogger(JtableDataEngineServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IGroupingAccessProvider groupingAccessProvider;
    @Autowired
    IDataGatherProvider dataGatherProvider;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired(required=false)
    private ICheckResultExtraService checkResultExtraService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private ISystemOptionManager systemOptionManager;
    @Autowired
    private IJtableFileService jtableFileService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ICalculateService calculateService;
    @Autowired
    private ICheckService checkService;
    @Autowired
    private CheckTransformUtil checkTransformUtil;
    @Resource
    private ExecutorContextHelper executorContextHelper;

    @Override
    public String dataSum(JtableContext jtableContext, List<String> formKeys) {
        String targetKey = "";
        String periodCode = "";
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        targetKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        dimensionValueSet.clearValue(dwEntity.getDimensionName());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        periodCode = dimensionValueSet.getValue(dataTimeEntity.getDimensionName()).toString();
        dimensionValueSet.clearValue(dataTimeEntity.getDimensionName());
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(jtableContext.getFormSchemeKey());
        queryEnvironment.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
        EntityViewDefine unitEntityView = dwEntity.getEntityViewDefine();
        GatherCondition condition = new GatherCondition();
        condition.setFormSchemeKey(jtableContext.getFormSchemeKey());
        condition.setSourceDimensions(dimensionValueSet);
        condition.setTargetDimension(dimensionValueSet);
        condition.setUnitView(unitEntityView);
        if (StringUtils.isNotEmpty((String)periodCode)) {
            condition.setPeriodCode(periodCode);
        }
        condition.setRecursive(true);
        condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
        ArrayList<GatherTableDefine> gatherTableDefineList = new ArrayList<GatherTableDefine>();
        for (String formKey : formKeys) {
            FormDefine form = this.runtimeView.queryFormById(formKey);
            gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(this.runtimeView, this.dataDefinitionRuntimeController, form));
        }
        condition.setGatherTables(gatherTableDefineList);
        dataGather.setGatherCondition(condition);
        try {
            ExecutorContext executorContext = this.getExecutorContext(jtableContext);
            dataGather.executeNodeGather(executorContext, targetKey);
        }
        catch (SQLException e) {
            logger.error("\u6c47\u603b\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return e.getMessage();
        }
        catch (Exception e) {
            logger.error("\u6c47\u603b\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return e.getMessage();
        }
        if (formKeys.size() == 1) {
            this.calculateByCondition(jtableContext, jtableContext.getFormKey());
        }
        this.calcAfterDataSum(jtableContext, formKeys, targetKey);
        return "success";
    }

    public void calcAfterDataSum(JtableContext jtableContext, List<String> formKeys, String targetKey) {
        Object formulaScheme = this.systemOptionManager.getObject("CALCULATION_AFTER_DATASUM", jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
        if (formulaScheme == null || StringUtils.isEmpty((String)formulaScheme.toString())) {
            return;
        }
        String formulaSchemeKey = formulaScheme.toString();
        FormulaSchemeDefine schemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (schemeDefine == null) {
            return;
        }
        ArrayList<String> calcFormKeys = new ArrayList<String>(formKeys);
        if (calcFormKeys.size() > 0) {
            calcFormKeys.add(UUIDUtil.emptyID.toString());
        }
        JtableContext calcContext = new JtableContext(jtableContext);
        calcContext.setFormulaSchemeKey(formulaSchemeKey);
        this.calculate(calcContext, calcFormKeys);
    }

    @Override
    public String calculate(JtableContext jtableContext, List<String> formKeys) {
        CalculateParam calculateParam = new CalculateParam();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (Map.Entry<String, DimensionValue> entry : jtableContext.getDimensionSet().entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue().getValue();
            builder.setValue(name, new Object[]{value});
        }
        calculateParam.setDimensionCollection(builder.getCollection());
        calculateParam.setVariableMap(jtableContext.getVariableMap());
        calculateParam.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        calculateParam.setMode(Mode.FORM);
        calculateParam.setRangeKeys(formKeys);
        if (BatchSummaryConst.isBatchSummaryEntry(jtableContext.getVariableMap())) {
            calculateParam.getIgnoreItems().add("ALL");
        }
        return this.calculateService.calculate(calculateParam);
    }

    @Override
    public void calculateByCondition(JtableContext jtableContext, String formKey) {
        CalPar calPar = new CalPar();
        calPar.setFormSchemeKey(jtableContext.getFormSchemeKey());
        AutoCalFormFmlParam autoCalFormFmlParam = new AutoCalFormFmlParam();
        autoCalFormFmlParam.setDimensionCombination(new DimensionCombinationBuilder(DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet())).getCombination());
        String[] split = jtableContext.getFormulaSchemeKey().split(";");
        autoCalFormFmlParam.setFormKey(formKey);
        calPar.setBaseFmlFactoryParam((BaseFmlFactoryParam)autoCalFormFmlParam);
        for (String formulaSchemeKey : split) {
            autoCalFormFmlParam.setFormulaSchemeKey(formulaSchemeKey);
            this.calculateService.calculate(calPar);
        }
    }

    @Override
    public FormulaCheckReturnInfo check(JtableContext jtableContext, List<String> formKeys) {
        CheckParam checkParam = new CheckParam();
        checkParam.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        checkParam.setVariableMap(jtableContext.getVariableMap());
        checkParam.setMode(Mode.FORM);
        checkParam.setRangeKeys(formKeys);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (Map.Entry<String, DimensionValue> entry : jtableContext.getDimensionSet().entrySet()) {
            builder.setValue(entry.getKey(), new Object[]{entry.getValue().getValue()});
        }
        checkParam.setDimensionCollection(builder.getCollection());
        if (BatchSummaryConst.isBatchSummaryEntry(jtableContext.getVariableMap())) {
            checkParam.getIgnoreItems().add("ALL");
        }
        CheckResult checkResult = this.checkService.check(checkParam);
        return this.checkTransformUtil.transformCheckResult(checkResult, jtableContext, formKeys);
    }

    @Override
    public ExecutorContext getExecutorContext(JtableContext jtableContext) {
        return this.getExecutorContext(jtableContext, null);
    }

    @Override
    public ExecutorContext getExecutorContext(JtableContext jtableContext, DimensionValueSet dimensionValueSet) {
        VariableManager variableManager;
        FormDefine form;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (StringUtils.isNotEmpty((String)jtableContext.getFormKey()) && !jtableContext.getFormKey().contains(";") && (form = this.runtimeView.queryFormById(jtableContext.getFormKey())) != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        if (dimensionValueSet == null) {
            dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        }
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        VariableExectuteFormula variableExectuteFormula = new VariableExectuteFormula(jtableContext, varDimensionValueSet);
        ReportFmlExecEnvironment environment = null;
        if (StringUtils.isEmpty((String)jtableContext.getFormSchemeKey())) {
            List<EntityViewData> entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
            environment = new EntityFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey(), entityList);
        } else {
            environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey(), (ExectuteFormula)variableExectuteFormula, jtableContext.getVariableMap());
        }
        executorContext.setEnv(this.executorContextHelper.getExecEnvironment(jtableContext, (IFmlExecEnvironment)environment));
        if (jtableContext.getVariableMap() != null && !jtableContext.getVariableMap().isEmpty() && (variableManager = executorContext.getVariableManager()) != null) {
            Map<String, Object> variableMap = jtableContext.getVariableMap();
            for (String variableName : variableMap.keySet()) {
                Object variableValue = variableMap.get(variableName);
                Variable variable = new Variable(variableName, 6);
                variable.setVarValue(variableValue);
                variableManager.add(variable);
            }
        }
        return executorContext;
    }

    @Override
    public IGroupingQuery getGroupingQuery(JtableContext jtableContext, String regionKey) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(jtableContext.getFormSchemeKey());
        queryEnvironment.setRegionKey(regionKey);
        queryEnvironment.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        DataRegionDefine regionDefine = this.runtimeView.queryDataRegionDefine(regionKey);
        FormDefine formDefine = this.runtimeView.queryFormById(regionDefine.getFormKey());
        queryEnvironment.setFormKey(formDefine.getKey());
        queryEnvironment.setFormCode(formDefine.getFormCode());
        IGroupingQuery groupingQuery = this.groupingAccessProvider.newGroupingQuery(queryEnvironment);
        if (StringUtils.isNotEmpty((String)regionDefine.getInputOrderFieldKey())) {
            FieldDefine inputOrderField = null;
            try {
                inputOrderField = this.dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (inputOrderField != null) {
                List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{inputOrderField.getKey()});
                groupingQuery.setDefaultGroupName(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
            }
        }
        return groupingQuery;
    }

    @Override
    public IDataQuery getDataQuery(JtableContext jtableContext, String regionKey) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(jtableContext.getFormSchemeKey());
        queryEnvironment.setRegionKey(regionKey);
        queryEnvironment.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        DataRegionDefine regionDefine = this.runtimeView.queryDataRegionDefine(regionKey);
        FormDefine formDefine = this.runtimeView.queryFormById(regionDefine.getFormKey());
        queryEnvironment.setFormKey(formDefine.getKey());
        queryEnvironment.setFormCode(formDefine.getFormCode());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        if (StringUtils.isNotEmpty((String)regionDefine.getInputOrderFieldKey())) {
            FieldDefine inputOrderField = null;
            try {
                inputOrderField = this.dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (inputOrderField != null) {
                List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{inputOrderField.getKey()});
                dataQuery.setDefaultGroupName(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
            }
        }
        return dataQuery;
    }

    @Override
    public String getDimensionName(FieldData field) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(field.getFieldKey());
        }
        catch (Exception e) {
            StringBuilder logInfo = new StringBuilder();
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(field.getOwnerTableKey());
            logInfo.append("\u83b7\u53d6\u6307\u6807").append(dataTable.getCode()).append("[").append(field.getFieldCode()).append("]\u7ef4\u5ea6\u540d\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
            logger.error(logInfo.toString(), e);
        }
        return dataAssist.getDimensionName(fieldDefine);
    }

    @Override
    public List<List<FieldData>> getBizKeyOrderFieldList(String regionKey, JtableContext jtableContext) {
        RegionData regionData = this.jtableParamService.getRegion(regionKey);
        ArrayList<List<FieldData>> bizKeyOrderFields = new ArrayList<List<FieldData>>();
        List<LinkData> allLinks = this.jtableParamService.getLinks(regionData.getKey());
        List<TableData> allTables = this.jtableParamService.getAllTableInRegion(regionData.getKey());
        String formSchemeKey = jtableContext.getFormSchemeKey();
        List<EntityViewData> entityList = this.jtableParamService.getEntityList(formSchemeKey);
        ArrayList<String> dimNames = new ArrayList<String>();
        for (EntityViewData entity : entityList) {
            dimNames.add(entity.getDimensionName());
        }
        if (allTables == null || allTables.isEmpty()) {
            return bizKeyOrderFields;
        }
        for (TableData table : allTables) {
            ArrayList<FieldData> bizKeyOrderFieldList = new ArrayList<FieldData>();
            bizKeyOrderFields.add(bizKeyOrderFieldList);
            List<String> bizKeyFieldsID = table.getBizKeyFields();
            FieldData bizKeyOrderField = null;
            for (String fieldKey : bizKeyFieldsID) {
                FieldData fieldDefine = null;
                try {
                    fieldDefine = this.jtableParamService.getField(fieldKey);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                if (fieldDefine == null) {
                    throw new NotFoundFieldException(JtableExceptionCodeCost.NOTFOUND_FIELD, new String[]{"\u672a\u627e\u5230" + table.getTableName() + "\u7684" + fieldKey + "\u6307\u6807"});
                }
                String fieldDimensionName = this.getDimensionName(fieldDefine);
                if (dimNames.contains(fieldDimensionName) || "ADJUST".equals(fieldDimensionName)) continue;
                if (regionData.getAllowDuplicateKey() && fieldDefine.getFieldValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) {
                    bizKeyOrderField = fieldDefine;
                    continue;
                }
                boolean findDimField = false;
                for (LinkData link : allLinks) {
                    if (!fieldKey.equals(link.getZbid())) continue;
                    bizKeyOrderFieldList.add(fieldDefine);
                    fieldDefine.setDataLinkKey(link.getKey());
                    findDimField = true;
                    break;
                }
                if (findDimField || !StringUtils.isEmpty((String)fieldDefine.getDefaultValue()) || RegionSettingUtil.checkRegionSettingContainDefaultVal(regionData, fieldDefine)) continue;
                throw new NotFoundFieldException(JtableExceptionCodeCost.NOTFOUND_FIELD, new String[]{table.getTableName() + "\u7684" + fieldDefine.getFieldCode() + "\u4e3b\u952e\u6307\u6807\u672a\u8bbe\u7f6e\u5728\u533a\u57df\u4e2d"});
            }
            if (bizKeyOrderField == null) continue;
            bizKeyOrderFieldList.add(bizKeyOrderField);
        }
        return bizKeyOrderFields;
    }

    @Override
    public String getDimensionFieldKey(String tableName, String dimensionName) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        FieldDefine dimensionField = dataAssist.getDimensionField(tableName, dimensionName);
        if (dimensionField == null) {
            return null;
        }
        return dimensionField.getKey();
    }

    @Override
    public Map<String, String> getDimensionNameColumnMap(JtableContext jtableContext, String tableName) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        HashMap<String, String> dimensionNameColumnMap = new HashMap<String, String>();
        try {
            TableRunInfo tableInfo = executorContext.getCache().getDataDefinitionsCache().getTableInfo(tableName);
            for (String dimensionName : jtableContext.getDimensionSet().keySet()) {
                String columnCode = tableInfo.getDimensionFieldCode(dimensionName);
                if (StringUtil.isNotEmpty((String)columnCode)) {
                    dimensionNameColumnMap.put(dimensionName, columnCode);
                    continue;
                }
                logger.error("\u7ef4\u5ea6\u540d\u672a\u627e\u5230\u5973\u5a32column:" + dimensionName);
            }
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return dimensionNameColumnMap;
    }

    @Override
    public int addQueryColumn(ICommonQuery query, String fieldKey) {
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
            logger.error(logInfo.toString(), e);
        }
        return query.addColumn(fieldDefine);
    }

    @Override
    public void addOrderByItem(ICommonQuery query, String fieldKey, boolean descending) {
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u83b7\u53d6\u6307\u6807").append("[").append(fieldKey).append("]\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(e.getMessage());
            logger.error(logInfo.toString(), e);
        }
        query.addOrderByItem(fieldDefine, descending);
    }

    @Override
    public void setEntityLevelGather(IGroupingQuery query, String targetKey, int entityColumnIndex, String entityViewKey, List<Integer> entityLevels, SummaryScheme sumScheme) {
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityViewKey);
        ReloadTreeInfo reloadTreeInfo = null;
        if (sumScheme != null) {
            reloadTreeInfo = new SummarySchemeUtils().toReloadTreeInfo(sumScheme);
        }
        query.setEntityLevelGather(targetKey, entityColumnIndex, entityView, entityLevels, reloadTreeInfo);
    }

    @Override
    public void setDataRegTotalInfo(IGroupingQuery groupingQuery, Map<String, GradeCellInfo> gradeEntityCellMap, Map<String, Integer> gradeCellIndex, ArrayList<Integer> levels) {
        ArrayList<GradeTotalItem> gradeTotalItems = new ArrayList<GradeTotalItem>();
        for (String cell : gradeEntityCellMap.keySet()) {
            String[] gatherSettings;
            String[] gatherFields;
            GradeCellInfo gradeCellInfo = gradeEntityCellMap.get(cell);
            GradeLinkItem linkItem = new GradeLinkItem();
            DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(cell);
            linkItem.setKey(dataLinkDefine.getKey());
            linkItem.setLinkExpression(dataLinkDefine.getLinkExpression());
            FieldDefine fieldDefine = null;
            try {
                fieldDefine = this.runtimeView.queryFieldDefine(dataLinkDefine.getLinkExpression());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                EntityViewDefine entityView = this.runtimeView.getViewByLinkDefineKey(dataLinkDefine.getKey());
                linkItem.setEntityView(entityView);
            }
            DataRegionDefine dataRegionDefine = this.runtimeView.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            ArrayList<Object> gradeSettingList = new ArrayList();
            if (gradeCellInfo.getLevels() != null && !gradeCellInfo.getLevels().isEmpty()) {
                gradeSettingList = gradeCellInfo.getLevels();
            } else if (StringUtils.isNotEmpty((String)dataRegionDefine.getGatherFields()) && StringUtils.isNotEmpty((String)dataRegionDefine.getGatherSetting()) && (gatherFields = dataRegionDefine.getGatherFields().split(";")).length == (gatherSettings = dataRegionDefine.getGatherSetting().split(";")).length) {
                for (int gatherIndex = 0; gatherIndex < gatherFields.length; ++gatherIndex) {
                    String[] gatherlevels;
                    String gatherField = gatherFields[gatherIndex];
                    String gatherSetting = gatherSettings[gatherIndex];
                    if (!dataLinkDefine.getLinkExpression().equals(gatherField)) continue;
                    for (String gatherlevel : gatherlevels = gatherSetting.split(",")) {
                        gradeSettingList.add(Integer.parseInt(gatherlevel));
                    }
                    break;
                }
            }
            GradeTotalItem item = new GradeTotalItem(linkItem, gradeCellIndex.get(dataLinkDefine.getKey()).intValue(), gradeSettingList);
            gradeTotalItems.add(item);
        }
        DataRegTotalInfo dataRegTotalInfo = new DataRegTotalInfo();
        if (gradeTotalItems.size() > 0) {
            dataRegTotalInfo.setGradeTotalItems(gradeTotalItems);
        }
        if (!levels.isEmpty()) {
            dataRegTotalInfo.setGradeLevels(levels);
        }
        groupingQuery.setDataRegTotalInfo(dataRegTotalInfo);
    }

    @Override
    public AbstractData expressionEvaluat(String condition, JtableContext jtableContext, DimensionValueSet dimensionValueSet) {
        if (StringUtils.isNotEmpty((String)condition)) {
            IExpressionEvaluator expressionEvaluator = this.dataAccessProvider.newExpressionEvaluator();
            ExecutorContext executorContext = this.getExecutorContext(jtableContext);
            try {
                AbstractData result = expressionEvaluator.eval(condition, executorContext, dimensionValueSet);
                return result;
            }
            catch (ExpressionException e) {
                logger.error("\u516c\u5f0f\u3010" + condition + "\u3011\u6c42\u503c\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    @Override
    public List<IParsedExpression> getExpressionsByLinks(List<String> linkCodes, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, int direction) {
        return this.formulaRunTimeController.getParsedExpressionByDataLink(linkCodes, formulaSchemeKey, formKey, formulaType, Integer.valueOf(direction));
    }

    @Override
    public MultiPeriodRegionDataSet getMultiPeriodRegionDataSet(JtableContext jtableContext, RegionData regionData, String prevPeriod, String prevYear) {
        MultiPeriodRegionDataSet multiPeriodRegionDataSet = new MultiPeriodRegionDataSet();
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        String fromPeriod = formSchemeDefine.getFromPeriod();
        List<String> cellKeys = regionData.getDataLinks().stream().filter(t -> this.filterLink((LinkData)t)).map(t -> t.getKey()).collect(Collectors.toList());
        if (StringUtils.isEmpty((String)fromPeriod) || prevYear.compareTo(fromPeriod) >= 0) {
            List<Object> prevData = this.getPeriodRegionData(jtableContext, regionData, cellKeys, prevPeriod);
            List<Object> prevYearData = prevPeriod.equals(prevYear) ? prevData : this.getPeriodRegionData(jtableContext, regionData, cellKeys, prevYear);
            multiPeriodRegionDataSet.setPrevPeriodData(prevData);
            multiPeriodRegionDataSet.setPrevYearData(prevYearData);
        } else {
            List<Object> prevData;
            if (prevPeriod.compareTo(fromPeriod) >= 0) {
                prevData = this.getPeriodRegionData(jtableContext, regionData, cellKeys, prevPeriod);
                multiPeriodRegionDataSet.setPrevPeriodData(prevData);
            } else {
                prevData = this.getPeriodRegionDataByTask(jtableContext, regionData, cellKeys, prevPeriod);
                multiPeriodRegionDataSet.setPrevPeriodData(prevData);
            }
            List<Object> prevYearData = this.getPeriodRegionDataByTask(jtableContext, regionData, cellKeys, prevYear);
            multiPeriodRegionDataSet.setPrevYearData(prevYearData);
        }
        multiPeriodRegionDataSet.setCells(cellKeys);
        return multiPeriodRegionDataSet;
    }

    public boolean filterLink(LinkData linkData) {
        int type = linkData.getType();
        FieldType fieldType = FieldType.forValue((int)type);
        return fieldType != FieldType.FIELD_TYPE_BINARY && fieldType != FieldType.FIELD_TYPE_PICTURE && fieldType != FieldType.FIELD_TYPE_FILE && fieldType != FieldType.FIELD_TYPE_UUID && fieldType != FieldType.FIELD_TYPE_ERROR && fieldType != FieldType.FIELD_TYPE_OBJECT_ARRAY;
    }

    private List<Object> getPeriodRegionDataByTask(JtableContext jtableContext, RegionData regionData, List<String> cellKeys, String prevPeriod) {
        FormDefine relatedForm;
        List links = this.runtimeView.queryLinksByCurrentFormScheme(jtableContext.getFormSchemeKey());
        if (links.size() <= 0) {
            return new ArrayList<Object>();
        }
        TaskLinkDefine taskLinkDefine = this.getTaskLinkDefine(links, jtableContext, prevPeriod);
        if (taskLinkDefine == null) {
            return new ArrayList<Object>();
        }
        FormDefine formDefine = this.runtimeView.queryFormById(jtableContext.getFormKey());
        try {
            relatedForm = this.runtimeView.queryFormByCodeInScheme(taskLinkDefine.getRelatedFormSchemeKey(), formDefine.getFormCode());
        }
        catch (Exception e) {
            return new ArrayList<Object>();
        }
        if (relatedForm == null) {
            return new ArrayList<Object>();
        }
        List<LinkData> nowLinkDataS = this.jtableParamService.getLinks(regionData.getKey());
        HashSet<String> zbTitle = new HashSet<String>();
        for (LinkData nowLinkData : nowLinkDataS) {
            zbTitle.add(nowLinkData.getZbtitle());
        }
        List<RegionData> relatedRegionDataS = this.jtableParamService.getRegions(relatedForm.getKey());
        RegionData relatedRegion = new RegionData();
        for (RegionData relatedRegionData : relatedRegionDataS) {
            if (relatedRegionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) continue;
            List<LinkData> linkDataS = this.jtableParamService.getLinks(relatedRegionData.getKey());
            for (LinkData linkData : linkDataS) {
                if (!zbTitle.contains(linkData.getZbtitle())) continue;
                relatedRegion = relatedRegionData;
            }
        }
        List<LinkData> relatedDataList = this.jtableParamService.getLinks(relatedRegion.getKey());
        relatedRegion.setDataLinks(relatedDataList);
        List<EntityViewData> relateEntityViewList = this.jtableParamService.getEntityList(taskLinkDefine.getRelatedFormSchemeKey());
        List<EntityViewData> entityViewList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        EntityViewData mastEntityViewData = new EntityViewData();
        EntityViewData mastRelateEntityViewData = new EntityViewData();
        for (EntityViewData entityViewData : entityViewList) {
            if (!entityViewData.isMasterEntity()) continue;
            mastEntityViewData = entityViewData;
        }
        for (EntityViewData entityViewData : relateEntityViewList) {
            if (!entityViewData.isMasterEntity()) continue;
            mastRelateEntityViewData = entityViewData;
        }
        Object o = dimensionValueSet.getValue(mastEntityViewData.getDimensionName());
        List<Object> unitKeys = new ArrayList<Object>();
        if (o instanceof List) {
            if (((List)o).size() != 1) {
                return new ArrayList<Object>();
            }
            unitKeys = (List)o;
        } else {
            unitKeys.add(o);
        }
        JtableContext relatedContext = new JtableContext(jtableContext);
        relatedContext.setFormSchemeKey(taskLinkDefine.getRelatedFormSchemeKey());
        relatedContext.setTaskKey(taskLinkDefine.getKey());
        relatedContext.setFormKey(relatedForm.getKey());
        ReportDataLinkFinder reportDataLinkFinder = new ReportDataLinkFinder(this.controller, this.iDataDefinitionRuntimeController, this.entityViewRunTimeController, taskLinkDefine.getCurrentFormSchemeKey());
        ExecutorContext executorContext = this.getExecutorContext(jtableContext);
        Map relatedUnitKeyMap = reportDataLinkFinder.findRelatedUnitKeyMap(executorContext, taskLinkDefine.getLinkAlias(), null, unitKeys);
        List relatedUnitValue = (List)relatedUnitKeyMap.get(o);
        Object relatedValue = null;
        if (relatedUnitValue == null || relatedUnitValue.size() != 1) {
            return new ArrayList<Object>();
        }
        relatedValue = relatedUnitValue.get(0);
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        Map<String, DimensionValue> dimensionValueMapJtable = jtableContext.getDimensionSet();
        if (relatedValue != null) {
            Set<String> mapKeyList = dimensionValueMapJtable.keySet();
            for (String mapStr : mapKeyList) {
                DimensionValue dimensionValueNew = new DimensionValue();
                DimensionValue dimensionValue = dimensionValueMapJtable.get(mapStr);
                if (dimensionValue.getName().equals(mastEntityViewData.getDimensionName())) {
                    dimensionValueNew.setValue(relatedValue.toString());
                    dimensionValueNew.setName(mastRelateEntityViewData.getDimensionName());
                    dimensionValueMap.put(mastRelateEntityViewData.getDimensionName(), dimensionValueNew);
                    continue;
                }
                dimensionValueMap.put(mapStr, dimensionValue);
            }
        }
        relatedContext.setDimensionSet(dimensionValueMap);
        List<String> relatedCellKeys = relatedRegion.getDataLinks().stream().filter(t -> this.filterLink((LinkData)t)).map(t -> t.getKey()).collect(Collectors.toList());
        List<Object> periodRegionData = this.getPeriodRegionData(relatedContext, relatedRegion, relatedCellKeys, prevPeriod);
        List<LinkData> LinkDataS = regionData.getDataLinks();
        ArrayList<Object> periodRegionDataNew = new ArrayList<Object>();
        if (periodRegionData == null || periodRegionData.size() == 0) {
            return periodRegionDataNew;
        }
        List<LinkData> relatedLinkDataS = relatedRegion.getDataLinks();
        for (LinkData linkData : LinkDataS) {
            String zbTitel = linkData.getZbtitle();
            Object zbData = null;
            for (LinkData relatedLinkData : relatedLinkDataS) {
                if (!relatedLinkData.getZbtitle().equals(zbTitel)) continue;
                int index = relatedLinkDataS.indexOf(relatedLinkData);
                zbData = periodRegionData.get(index);
            }
            periodRegionDataNew.add(zbData);
        }
        return periodRegionDataNew;
    }

    private TaskLinkDefine getTaskLinkDefine(List<TaskLinkDefine> links, JtableContext jtableContext, String prevPeriod) {
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        for (TaskLinkDefine linkDefine : links) {
            if (!StringUtils.isNotEmpty((String)linkDefine.getRelatedFormSchemeKey())) continue;
            FormSchemeDefine linkFormSchemeDefine = this.runtimeView.getFormScheme(linkDefine.getRelatedFormSchemeKey());
            if (formSchemeDefine.getPeriodType() != linkFormSchemeDefine.getPeriodType()) continue;
            String fromPeriod = linkFormSchemeDefine.getFromPeriod();
            String toPeriod = linkFormSchemeDefine.getToPeriod();
            if (!StringUtils.isEmpty((String)fromPeriod) && prevPeriod.compareTo(fromPeriod) < 0 || !StringUtils.isEmpty((String)toPeriod) && prevPeriod.compareTo(toPeriod) > 0) continue;
            return linkDefine;
        }
        return null;
    }

    private List<Object> getPeriodRegionData(JtableContext jtableContext, RegionData regionData, List<String> cellKeys, String prevPeriod) {
        if (cellKeys.size() <= 0) {
            return new ArrayList<Object>();
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        dimensionValueSet.setValue("DATATIME", (Object)prevPeriod);
        JtableContext currentContext = new JtableContext(jtableContext);
        currentContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet(dimensionValueSet));
        RegionDataFactory factory = new RegionDataFactory();
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(currentContext);
        regionQueryInfo.setRegionKey(regionData.getKey());
        RegionDataSet dataSet = factory.getRegionDataSet(regionData, regionQueryInfo);
        if (dataSet.getData().size() > 0) {
            List<Object> currentDatas = dataSet.getData().get(0);
            List<String> currentCells = dataSet.getCells().get(regionData.getKey());
            ArrayList<Object> resultValue = new ArrayList<Object>(cellKeys.size());
            if (currentCells != null) {
                HashMap<String, Integer> cellMap = this.getCellMap(currentCells);
                for (String cellKey : cellKeys) {
                    Integer cellIndex = cellMap.get(cellKey);
                    if (cellIndex == null) {
                        resultValue.add("");
                        continue;
                    }
                    resultValue.add(currentDatas.get(cellIndex));
                }
            }
            return resultValue;
        }
        return new ArrayList<Object>();
    }

    private HashMap<String, Integer> getCellMap(List<String> cellKeys) {
        HashMap<String, Integer> cellMap = new HashMap<String, Integer>();
        for (int index = 0; index < cellKeys.size(); ++index) {
            cellMap.put(cellKeys.get(index), index);
        }
        return cellMap;
    }
}

