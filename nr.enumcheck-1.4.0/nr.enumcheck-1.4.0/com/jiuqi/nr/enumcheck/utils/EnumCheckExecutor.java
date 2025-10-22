/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.datacheckcommon.helper.DataQueryHelper
 *  com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.Guid
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.nr.enumcheck.utils;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.datacheckcommon.helper.DataQueryHelper;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.enumcheck.common.DataFdInfo;
import com.jiuqi.nr.enumcheck.common.DataFdInfoBase;
import com.jiuqi.nr.enumcheck.common.EnumAssTable;
import com.jiuqi.nr.enumcheck.common.EnumCheckErrorKind;
import com.jiuqi.nr.enumcheck.common.EnumCheckException;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckParam;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.enumcheck.common.EnumFieldType;
import com.jiuqi.nr.enumcheck.common.FormFieldWrapper;
import com.jiuqi.nr.enumcheck.helper.EnumCheckHelper;
import com.jiuqi.nr.enumcheck.message.EnumAssAndEntityInfo;
import com.jiuqi.nr.enumcheck.utils.EnumFilterCondition;
import com.jiuqi.nr.enumcheck.utils.TempTableRes;
import com.jiuqi.nr.enumcheck.utils.TmpTableUtils;
import com.jiuqi.nr.enumcheck.utils.VariableExectuteFormula;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.Guid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

@Component
public class EnumCheckExecutor {
    @Autowired
    private JdbcTemplate jdbcTpl;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    private TmpTableUtils tmpTableUtils;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private DataQueryHelper dataQueryHelper;
    @Autowired
    private EnumCheckHelper enumCheckHelper;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private PeriodWrapper periodWrapper;
    private EnumDataCheckParam enumDataCheckParam;
    private HashMap<String, String> enumDic;
    private HashMap<String, HashMap<String, String>> enumDataDic;
    private HashMap<String, String> formDic;
    private List<String> dimNames;
    private HashMap<String, IEntityRow> masterEntityDic;
    private ITempTable tempTable;
    private Map<String, List<String>> mdcodeFmlRes;
    private DimensionValueSet queryDataDims;
    private String masterDimName;
    private AtomicInteger errCount;
    private EnumFilterCondition enumFilterCondition;
    private String configEnum;
    private HashMap<String, String> dicFilterFdCode = new HashMap();
    private boolean enableNrdb;
    private static final String BZ1 = "BZ1";

    private EnumCheckExecutor() {
    }

    public static EnumCheckExecutor createChecker(PeriodWrapper periodWrapper, ExecutorContext executorContext, EnumDataCheckParam enumDataCheckParam, String masterDimName, boolean enableNrdb) {
        EnumCheckExecutor result = (EnumCheckExecutor)BeanUtil.getBean(EnumCheckExecutor.class);
        EnumCheckExecutor checker = new EnumCheckExecutor();
        checker.dataAccessProvider = result.dataAccessProvider;
        checker.runTimeViewController = result.runTimeViewController;
        checker.jdbcTpl = result.jdbcTpl;
        checker.tmpTableUtils = result.tmpTableUtils;
        checker.periodEntityAdapter = result.periodEntityAdapter;
        checker.metaService = result.metaService;
        checker.entityQueryHelper = result.entityQueryHelper;
        checker.enumCheckHelper = result.enumCheckHelper;
        checker.dataQueryHelper = result.dataQueryHelper;
        checker.dataSchemeService = result.dataSchemeService;
        checker.periodWrapper = periodWrapper;
        checker.enumDataCheckParam = enumDataCheckParam;
        checker.masterDimName = masterDimName;
        checker.taskOptionController = result.taskOptionController;
        checker.iDataAccessProvider = result.iDataAccessProvider;
        checker.init(enumDataCheckParam.getFilterFormula());
        checker.enableNrdb = enableNrdb;
        checker.dataDefinitionRuntimeController = result.dataDefinitionRuntimeController;
        checker.entityViewRunTimeController = result.entityViewRunTimeController;
        checker.fmdmDataService = result.fmdmDataService;
        checker.runtimeDataSchemeService = result.runtimeDataSchemeService;
        return checker;
    }

    private void init(String filterFormula) {
        this.enumDataDic = new HashMap();
        this.errCount = new AtomicInteger(0);
        this.configEnum = filterFormula;
        this.enumFilterCondition = this.initEnumCondition();
    }

    private EnumFilterCondition initEnumCondition() {
        EnumFilterCondition result = new EnumFilterCondition();
        result.init(this.configEnum);
        return result;
    }

    public List<ITempTable> prepareData() throws Exception {
        ArrayList<ITempTable> tmpTables = new ArrayList<ITempTable>();
        this.enumDic = new HashMap();
        List<String> enumNames = this.enumDataCheckParam.getEnumNames();
        if (null != enumNames && !enumNames.isEmpty()) {
            for (String enumName : enumNames) {
                if (!StringUtils.isNotEmpty((String)enumName)) continue;
                this.enumDic.put(enumName, enumName);
            }
        }
        this.formDic = new HashMap();
        this.dimNames = new ArrayList<String>();
        HashMap<String, String> dimNameKey = new HashMap<String, String>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(this.enumDataCheckParam.getTaskKey());
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dataDimension : dataSchemeDimension) {
            IEntityDefine entityDefine;
            if (DimensionType.UNIT == dataDimension.getDimensionType()) {
                entityDefine = this.metaService.queryEntity(dataDimension.getDimKey());
                dimNameKey.put(this.masterDimName, entityDefine.getId());
                continue;
            }
            if (DimensionType.PERIOD == dataDimension.getDimensionType()) {
                IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dataDimension.getDimKey());
                dimNameKey.put(periodEntity.getDimensionName(), dataDimension.getDimKey());
                continue;
            }
            if (DimensionType.DIMENSION != dataDimension.getDimensionType()) continue;
            entityDefine = this.metaService.queryEntity(dataDimension.getDimKey());
            if (null != entityDefine) {
                this.dimNames.add(entityDefine.getDimensionName());
                dimNameKey.put(entityDefine.getDimensionName(), entityDefine.getId());
                continue;
            }
            if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
            this.dimNames.add("ADJUST");
            dimNameKey.put("ADJUST", "ADJUST");
        }
        String periodData = null;
        DimensionValueSet dimensionValueSet = this.enumDataCheckParam.getDims().combineWithoutVarDim();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimName = dimensionValueSet.getName(i);
            String viewKey = (String)dimNameKey.get(dimName);
            if (!this.periodEntityAdapter.isPeriodEntity(viewKey)) continue;
            periodData = (String)dimensionValueSet.getValue(dimName);
            if (!StringUtils.isEmpty((String)periodData) || this.periodWrapper != null) break;
            throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
        }
        if (StringUtils.isEmpty(periodData)) {
            throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
        }
        int fmlSize = 0;
        this.masterEntityDic = new HashMap();
        HashMap<String, List<String>> mdcodeFmlRes = new HashMap<String, List<String>>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            Object entityRow2;
            String dimName = dimensionValueSet.getName(i);
            if (!dimName.equals(this.masterDimName)) continue;
            String viewKey = (String)dimNameKey.get(dimName);
            Object enityListObj = dimensionValueSet.getValue(i);
            if (!this.periodEntityAdapter.isPeriodEntity(viewKey) && 0 == this.enumFilterCondition.getAllFilterFml().size()) {
                EntityViewDefine entityViewDefine = this.entityQueryHelper.getEntityView(this.enumDataCheckParam.getFormSchemeKey(), viewKey);
                IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityViewDefine, periodData);
                DimensionValueSet valueSet = entityQuery.getMasterKeys();
                if (null == valueSet) {
                    valueSet = new DimensionValueSet();
                }
                if (enityListObj instanceof String) {
                    valueSet.setValue(dimName, Arrays.asList(((String)enityListObj).split(";")));
                } else {
                    valueSet.setValue(dimName, enityListObj);
                }
                entityQuery.setMasterKeys(valueSet);
                IEntityTable reader = this.entityQueryHelper.buildEntityTable(entityQuery, this.enumDataCheckParam.getFormSchemeKey(), true);
                for (Object entityRow2 : reader.getAllRows()) {
                    this.masterEntityDic.put(entityRow2.getEntityKeyData(), (IEntityRow)entityRow2);
                }
                continue;
            }
            if (this.periodEntityAdapter.isPeriodEntity(viewKey)) continue;
            ArrayList<String> expressions = new ArrayList<String>();
            expressions.add("SYS_UNITCODE");
            expressions.add("SYS_UNITTITLE");
            EntityViewDefine entityViewDefine = this.entityQueryHelper.getEntityView(this.enumDataCheckParam.getFormSchemeKey(), viewKey);
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityViewDefine, periodData);
            DimensionValueSet valueSet = entityQuery.getMasterKeys();
            if (null == valueSet) {
                valueSet = new DimensionValueSet();
            }
            if (enityListObj instanceof String) {
                valueSet.setValue(dimName, Arrays.asList(((String)enityListObj).split(";")));
            } else {
                valueSet.setValue(dimName, enityListObj);
            }
            entityQuery.setMasterKeys(valueSet);
            IEntityTable reader = this.entityQueryHelper.buildEntityTable(entityQuery, this.enumDataCheckParam.getFormSchemeKey(), true);
            entityRow2 = reader.getAllRows().iterator();
            while (entityRow2.hasNext()) {
                IEntityRow entityRow3 = (IEntityRow)entityRow2.next();
                this.masterEntityDic.put(entityRow3.getEntityKeyData(), entityRow3);
            }
            com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.getExecutorContext(dimensionValueSet);
            IExpressionEvaluator evaluator = this.iDataAccessProvider.newExpressionEvaluator();
            for (String fml : this.enumFilterCondition.getAllFilterFml()) {
                expressions.add(fml);
                this.dicFilterFdCode.put(fml, "FD" + fmlSize++);
            }
            Map result = evaluator.evalBatch(expressions, executorContext, dimensionValueSet);
            for (String mdcode : result.keySet()) {
                ArrayList<String> results = new ArrayList<String>();
                for (int j = 0; j < fmlSize; ++j) {
                    String res = (Boolean)((Object[])result.get(mdcode))[j + 2] != false ? "1" : "0";
                    results.add(res);
                }
                mdcodeFmlRes.put(mdcode, results);
            }
        }
        TempTableRes tempTableAndInsertData = this.tmpTableUtils.createTempTableAndInsertData(this.enumDataCheckParam.getDims(), mdcodeFmlRes, this.masterDimName, fmlSize);
        this.tempTable = tempTableAndInsertData.getTempTable();
        tmpTables.add(this.tempTable);
        return tmpTables;
    }

    @NotNull
    private com.jiuqi.np.dataengine.executors.ExecutorContext getExecutorContext(DimensionValueSet dimensionValueSet) {
        VariableManager variableManager;
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int j = 0; j < dimensionValueSet.size(); ++j) {
            String dimensionName = dimensionValueSet.getName(j);
            Object dimensionValue = dimensionValueSet.getValue(j);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        VariableExectuteFormula variableExectuteFormula = new VariableExectuteFormula(this.enumDataCheckParam, varDimensionValueSet);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, this.enumDataCheckParam.getFormSchemeKey(), (ExectuteFormula)variableExectuteFormula, this.enumDataCheckParam.getVariableMap());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        if (this.enumDataCheckParam.getVariableMap() != null && !this.enumDataCheckParam.getVariableMap().isEmpty() && (variableManager = executorContext.getVariableManager()) != null) {
            Map<String, Object> variableMap = this.enumDataCheckParam.getVariableMap();
            for (String variableName : variableMap.keySet()) {
                Object variableValue = variableMap.get(variableName);
                Variable variable = new Variable(variableName, 6);
                variable.setVarValue(variableValue);
                variableManager.add(variable);
            }
        }
        return executorContext;
    }

    public void prepareDataEnableNrdb() throws Exception {
        String dimName;
        this.enumDic = new HashMap();
        List<String> enumNames = this.enumDataCheckParam.getEnumNames();
        if (null != enumNames && !enumNames.isEmpty()) {
            for (String enumName : enumNames) {
                if (!StringUtils.isNotEmpty((String)enumName)) continue;
                this.enumDic.put(enumName, enumName);
            }
        }
        this.formDic = new HashMap();
        this.dimNames = new ArrayList<String>();
        HashMap<String, String> dimNameKey = new HashMap<String, String>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(this.enumDataCheckParam.getTaskKey());
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dataDimension : dataSchemeDimension) {
            IEntityDefine entityDefine;
            if (DimensionType.UNIT == dataDimension.getDimensionType()) {
                entityDefine = this.metaService.queryEntity(dataDimension.getDimKey());
                dimNameKey.put(this.masterDimName, entityDefine.getId());
                continue;
            }
            if (DimensionType.PERIOD == dataDimension.getDimensionType()) {
                IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dataDimension.getDimKey());
                dimNameKey.put(periodEntity.getDimensionName(), dataDimension.getDimKey());
                continue;
            }
            if (DimensionType.DIMENSION != dataDimension.getDimensionType()) continue;
            entityDefine = this.metaService.queryEntity(dataDimension.getDimKey());
            if (null != entityDefine) {
                this.dimNames.add(entityDefine.getDimensionName());
                dimNameKey.put(entityDefine.getDimensionName(), entityDefine.getId());
                continue;
            }
            if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
            this.dimNames.add("ADJUST");
            dimNameKey.put("ADJUST", "ADJUST");
        }
        String periodData = null;
        DimensionValueSet dimensionValueSet = this.enumDataCheckParam.getDims().combineWithoutVarDim();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimName2 = dimensionValueSet.getName(i);
            String viewKey = (String)dimNameKey.get(dimName2);
            if (!this.periodEntityAdapter.isPeriodEntity(viewKey)) continue;
            periodData = (String)dimensionValueSet.getValue(dimName2);
            if (!StringUtils.isEmpty((String)periodData) || this.periodWrapper != null) break;
            throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
        }
        if (StringUtils.isEmpty(periodData)) {
            throw new Exception("\u65f6\u671f\u4f20\u5165\u6709\u8bef");
        }
        HashMap<String, Set> dimNameValues = new HashMap<String, Set>();
        List dimensionCombinations = this.enumDataCheckParam.getDims().getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            for (FixedDimensionValue fixedDimensionValue : dimensionCombination) {
                String dimName3 = fixedDimensionValue.getName();
                String dimValue = (String)fixedDimensionValue.getValue();
                Set dimValues = dimNameValues.computeIfAbsent(dimName3, k -> new HashSet());
                dimValues.add(dimValue);
            }
        }
        this.queryDataDims = new DimensionValueSet();
        for (Map.Entry dimNameValuesEntry : dimNameValues.entrySet()) {
            dimName = (String)dimNameValuesEntry.getKey();
            Set dimValues = (Set)dimNameValuesEntry.getValue();
            if (dimValues.size() == 1) {
                for (String dimValue : dimValues) {
                    this.queryDataDims.setValue(dimName, (Object)dimValue);
                }
                continue;
            }
            this.queryDataDims.setValue(dimName, new ArrayList(dimValues));
        }
        int fmlSize = 0;
        this.masterEntityDic = new HashMap();
        this.mdcodeFmlRes = new HashMap<String, List<String>>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            Object entityRow2;
            dimName = dimensionValueSet.getName(i);
            if (!dimName.equals(this.masterDimName)) continue;
            String viewKey = (String)dimNameKey.get(dimName);
            Object enityListObj = dimensionValueSet.getValue(i);
            if (!this.periodEntityAdapter.isPeriodEntity(viewKey) && 0 == this.enumFilterCondition.getAllFilterFml().size()) {
                EntityViewDefine entityViewDefine = this.entityQueryHelper.getEntityView(this.enumDataCheckParam.getFormSchemeKey(), viewKey);
                IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityViewDefine, periodData);
                DimensionValueSet valueSet = entityQuery.getMasterKeys();
                if (null == valueSet) {
                    valueSet = new DimensionValueSet();
                }
                if (enityListObj instanceof String) {
                    valueSet.setValue(dimName, Arrays.asList(((String)enityListObj).split(";")));
                } else {
                    valueSet.setValue(dimName, enityListObj);
                }
                entityQuery.setMasterKeys(valueSet);
                IEntityTable reader = this.entityQueryHelper.buildEntityTable(entityQuery, this.enumDataCheckParam.getFormSchemeKey(), true);
                for (Object entityRow2 : reader.getAllRows()) {
                    this.masterEntityDic.put(entityRow2.getEntityKeyData(), (IEntityRow)entityRow2);
                }
                continue;
            }
            if (this.periodEntityAdapter.isPeriodEntity(viewKey)) continue;
            ArrayList<String> expressions = new ArrayList<String>();
            expressions.add("SYS_UNITCODE");
            expressions.add("SYS_UNITTITLE");
            EntityViewDefine entityViewDefine = this.entityQueryHelper.getEntityView(this.enumDataCheckParam.getFormSchemeKey(), viewKey);
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(entityViewDefine, periodData);
            DimensionValueSet valueSet = entityQuery.getMasterKeys();
            if (null == valueSet) {
                valueSet = new DimensionValueSet();
            }
            if (enityListObj instanceof String) {
                valueSet.setValue(dimName, Arrays.asList(((String)enityListObj).split(";")));
            } else {
                valueSet.setValue(dimName, enityListObj);
            }
            entityQuery.setMasterKeys(valueSet);
            IEntityTable reader = this.entityQueryHelper.buildEntityTable(entityQuery, this.enumDataCheckParam.getFormSchemeKey(), true);
            entityRow2 = reader.getAllRows().iterator();
            while (entityRow2.hasNext()) {
                IEntityRow entityRow3 = (IEntityRow)entityRow2.next();
                this.masterEntityDic.put(entityRow3.getEntityKeyData(), entityRow3);
            }
            com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.getExecutorContext(dimensionValueSet);
            IExpressionEvaluator evaluator = this.iDataAccessProvider.newExpressionEvaluator();
            for (String fml : this.enumFilterCondition.getAllFilterFml()) {
                expressions.add(fml);
                this.dicFilterFdCode.put(fml, "FD" + fmlSize++);
            }
            Map result = evaluator.evalBatch(expressions, executorContext, dimensionValueSet);
            for (String mdcode : result.keySet()) {
                ArrayList<String> results = new ArrayList<String>();
                for (int j = 0; j < fmlSize; ++j) {
                    String res = (Boolean)((Object[])result.get(mdcode))[j + 2] != false ? "1" : "0";
                    results.add(res);
                }
                this.mdcodeFmlRes.put(mdcode, results);
            }
        }
    }

    public List<EnumDataCheckResultItem> executeCheck(TaskDefine taskDefine, String entityId, String formKey, HashSet<String> enumTableDic) throws Exception {
        List tableList;
        String entityCode;
        ArrayList<EnumDataCheckResultItem> result = new ArrayList<EnumDataCheckResultItem>();
        if (this.isFilterForm(formKey)) {
            return result;
        }
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        List dataLinkDefines = this.runTimeViewController.getAllLinksInForm(formKey);
        HashMap<String, List> dataTableDic = new HashMap<String, List>();
        HashMap<String, List> entityTableDic = new HashMap<String, List>();
        HashMap<String, List> entityInfoTableDic = new HashMap<String, List>();
        for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
            EnumAssTable enumAssTable;
            List tables;
            FormFieldWrapper formFieldWrapper;
            if (dataLinkDefine.getPosX() < 1 || dataLinkDefine.getPosY() < 1) continue;
            DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            if (dataLinkDefine.getPosY() > dataRegionDefine.getRegionBottom() || dataLinkDefine.getPosX() > dataRegionDefine.getRegionRight() || null == (formFieldWrapper = this.enumCheckHelper.getFormField(taskDefine.getDataScheme(), entityId, formDefine, dataLinkDefine)) || null == formFieldWrapper.getRefEntity() || this.isFilterEnum1(formFieldWrapper.getRefEntity())) continue;
            if (EnumFieldType.DATA_ENUM == formFieldWrapper.getEnumFieldType()) {
                tables = dataTableDic.computeIfAbsent(formFieldWrapper.getTableName(), k -> new ArrayList());
                enumAssTable = this.createEnumAssTable(dataLinkDefine, formFieldWrapper);
                tables.add(enumAssTable);
            } else if (EnumFieldType.ENTITY_ENUM == formFieldWrapper.getEnumFieldType()) {
                tables = entityTableDic.computeIfAbsent(formFieldWrapper.getTableName(), k -> new ArrayList());
                enumAssTable = this.createEnumAssTable(dataLinkDefine, formFieldWrapper);
                tables.add(enumAssTable);
            } else {
                tables = entityInfoTableDic.computeIfAbsent(formFieldWrapper.getTableName(), k -> new ArrayList());
                enumAssTable = this.createEnumAssTable(dataLinkDefine, formFieldWrapper);
                tables.add(enumAssTable);
            }
            if (enumTableDic.contains(formFieldWrapper.getRefEntity().getId())) continue;
            enumTableDic.add(formFieldWrapper.getRefEntity().getId());
        }
        for (Map.Entry entry : dataTableDic.entrySet()) {
            String tableCode = (String)entry.getKey();
            List value = (List)entry.getValue();
            if (this.enableNrdb) {
                this.doDataTableCheckEnableNrdb(value, result, formDefine);
                continue;
            }
            this.doDataTableCheck(tableCode, value, result, formDefine.getFormCode());
        }
        for (Map.Entry entry : entityTableDic.entrySet()) {
            entityCode = (String)entry.getKey();
            tableList = (List)entry.getValue();
            if (this.enableNrdb) {
                this.doEntityTableCheckEnableNrdb(tableList, result, formDefine);
                continue;
            }
            this.doEntityTableCheck(entityCode, tableList, result);
        }
        for (Map.Entry entry : entityInfoTableDic.entrySet()) {
            entityCode = (String)entry.getKey();
            tableList = (List)entry.getValue();
            if (this.enableNrdb) {
                this.doEntityTableCheckEnableNrdb(tableList, result, formDefine);
                continue;
            }
            this.doEntityInfoTableCheck(entityCode, tableList, result);
        }
        for (EnumDataCheckResultItem enumDataCheckResultItem : result) {
            enumDataCheckResultItem.setBbfz(formDefine.getTitle());
            enumDataCheckResultItem.setFormKey(formDefine.getKey());
        }
        return result;
    }

    private void doDataTableCheck(String tableCode, List<EnumAssTable> tableList, List<EnumDataCheckResultItem> result, String formCode) {
        if (this.enumFilterCondition.isFilter(formCode) && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml(formCode))) {
            return;
        }
        DataTable dataTable = this.dataSchemeService.getDataTableByCode(tableCode);
        String tableName = this.entityQueryHelper.getTableNameByTableCode(dataTable.getCode());
        String[] bizFdKeys = dataTable.getBizKeys();
        if (0 == bizFdKeys.length) {
            return;
        }
        LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper = new LinkedHashMap<String, String>();
        ArrayList<DataFdInfoBase> bizFds = new ArrayList<DataFdInfoBase>();
        ArrayList<DataFdInfoBase> bizFloats = new ArrayList<DataFdInfoBase>();
        for (String fdKey : bizFdKeys) {
            String dimName;
            DataField keyField = this.dataSchemeService.getDataField(fdKey);
            String string = "DATATIME".equals(keyField.getCode()) ? "DATATIME" : (dimName = StringUtils.isNotEmpty((String)keyField.getRefDataEntityKey()) ? this.metaService.getDimensionName(keyField.getRefDataEntityKey()) : null);
            if (this.isSelectDim(dimName)) {
                String realTempColName = this.tempTable.getRealColName(dimName);
                if (this.masterDimName.equals(dimName) || "DATATIME".equals(dimName)) {
                    tmpFieldAndDbFieldNameWrapper.put(realTempColName, keyField.getCode());
                    bizFds.add(new DataFdInfoBase(keyField.getCode(), tableName));
                } else {
                    tmpFieldAndDbFieldNameWrapper.put(realTempColName, keyField.getCode());
                    bizFds.add(new DataFdInfoBase(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), tableName));
                }
            } else if ("BIZKEYORDER".equals(keyField.getCode())) {
                bizFds.add(new DataFdInfoBase("BIZKEYORDER", tableName));
            } else {
                bizFds.add(new DataFdInfoBase(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), tableName));
            }
            if (this.isSelectDim(dimName) || keyField.getCode().equals(BZ1)) continue;
            if ("BIZKEYORDER".equals(keyField.getCode())) {
                bizFloats.add(new DataFdInfoBase("BIZKEYORDER", tableName));
                continue;
            }
            bizFloats.add(new DataFdInfoBase(this.entityQueryHelper.getFieldNameByFieldKey(keyField.getKey()), tableName));
        }
        for (EnumAssTable enumTable : tableList) {
            boolean hasVersion = false;
            String fdCode = this.getFilterFmlFdCode(enumTable, formCode);
            try {
                if (enumTable.getAllowMultipleSelect()) {
                    this.doMultCheck(tableName, hasVersion, bizFds, bizFloats, tmpFieldAndDbFieldNameWrapper, enumTable, fdCode, result);
                    continue;
                }
                String sqlFormat = this.generateSql1(tableName, hasVersion, bizFds, bizFloats, tmpFieldAndDbFieldNameWrapper, fdCode, enumTable);
                if (StringUtils.isEmpty((String)sqlFormat)) continue;
                this.executeSql(false, hasVersion, sqlFormat, enumTable, bizFds, bizFloats, result);
            }
            catch (Exception e) {
                if (!(e.getCause() instanceof EnumCheckException)) continue;
                return;
            }
        }
    }

    private void doDataTableCheckEnableNrdb(List<EnumAssTable> tableList, List<EnumDataCheckResultItem> results, FormDefine formDefine) throws Exception {
        if (this.enumFilterCondition.isFilter(formDefine.getFormCode()) && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml(formDefine.getFormCode()))) {
            return;
        }
        HashMap<String, EnumAssAndEntityInfo> fieldKeyEnumInfoMap = new HashMap<String, EnumAssAndEntityInfo>();
        HashMap<String, IEntityTable> entityViewKeyTableMap = new HashMap<String, IEntityTable>();
        for (EnumAssTable enumAssTable : tableList) {
            IEntityTable entityTable = (IEntityTable)entityViewKeyTableMap.get(enumAssTable.getEntityViewKey());
            if (null == entityTable) {
                EntityViewDefine enumView = this.entityQueryHelper.getEnumViewByEntityId(enumAssTable.getEntityViewKey());
                IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(enumView, (String)this.enumDataCheckParam.getDims().combineWithoutVarDim().getValue("DATATIME"));
                entityQuery.markLeaf();
                entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, null, false);
                entityViewKeyTableMap.put(enumAssTable.getEntityViewKey(), entityTable);
            }
            if (null != fieldKeyEnumInfoMap.get(enumAssTable.getDataField().getKey())) continue;
            EnumAssAndEntityInfo enumAssAndEntityInfo = new EnumAssAndEntityInfo(enumAssTable, entityTable);
            fieldKeyEnumInfoMap.put(enumAssTable.getDataField().getKey(), enumAssAndEntityInfo);
        }
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formDefine.getFormScheme());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        for (EnumAssTable enumAssTable : tableList) {
            String fieldKey = enumAssTable.getDataField().getKey();
            FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
            fieldDefines.add(fieldDefine);
            dataQuery.addColumn(fieldDefine);
        }
        dataQuery.setMasterKeys(this.queryDataDims);
        IReadonlyTable readonlyTable = dataQuery.executeReader(executorContext);
        for (int i = 0; i < readonlyTable.getCount(); ++i) {
            IDataRow item = readonlyTable.getItem(i);
            String unitKey = (String)item.getRowKeys().getValue(this.masterDimName);
            for (FieldDefine fieldDefine : fieldDefines) {
                EnumAssAndEntityInfo enumAssAndEntityInfo = (EnumAssAndEntityInfo)fieldKeyEnumInfoMap.get(fieldDefine.getKey());
                EnumAssTable enumAssTable = enumAssAndEntityInfo.getEnumAssTable();
                String fdCode = this.getFilterFmlFdCode(enumAssTable, formDefine.getFormCode());
                if (StringUtils.isNotEmpty((String)fdCode)) {
                    int fmlIndex = Integer.parseInt(fdCode.substring(2));
                    String fmlRes = this.mdcodeFmlRes.get(unitKey).get(fmlIndex);
                    if ("1".equals(fmlRes)) continue;
                }
                String data = item.getAsString(fieldDefine);
                IEntityTable entityTable = enumAssAndEntityInfo.getEntityTable();
                this.doCheck(results, item.getRowKeys(), entityTable, enumAssTable, data);
            }
        }
    }

    private void doCheck(List<EnumDataCheckResultItem> results, DimensionValueSet rowKeys, IEntityTable entityTable, EnumAssTable enumAssTable, String data) {
        IEntityRow entityRow;
        if (StringUtils.isEmpty((String)data)) {
            if (!enumAssTable.getCanNull()) {
                EnumDataCheckResultItem result = this.dataRow2Data(rowKeys, data, enumAssTable, EnumCheckErrorKind.ISNULL);
                results.add(result);
            }
        } else if (enumAssTable.getAllowMultipleSelect()) {
            for (String subData : data.split(";")) {
                EnumDataCheckResultItem result;
                if (!StringUtils.isNotEmpty((String)subData)) continue;
                if (enumAssTable.getFixSize() > 0 && subData.length() != enumAssTable.getFixSize()) {
                    result = this.dataRow2Data(rowKeys, subData, enumAssTable, EnumCheckErrorKind.NOTFIXSIZE);
                    results.add(result);
                } else if (!enumAssTable.getAllUndefineCode() && null == entityTable.findByEntityKey(subData)) {
                    result = this.dataRow2Data(rowKeys, subData, enumAssTable, EnumCheckErrorKind.UNDEFINE_CODE);
                    results.add(result);
                } else {
                    IEntityRow entityRow2;
                    if (!enumAssTable.getOnlyLeafNode() || null == (entityRow2 = entityTable.findByEntityKey(subData)) || entityRow2.isLeaf()) continue;
                    EnumDataCheckResultItem result2 = this.dataRow2Data(rowKeys, subData, enumAssTable, EnumCheckErrorKind.NOTLEAF);
                    results.add(result2);
                }
                break;
            }
        } else if (enumAssTable.getFixSize() > 0 && data.length() != enumAssTable.getFixSize()) {
            EnumDataCheckResultItem result = this.dataRow2Data(rowKeys, data, enumAssTable, EnumCheckErrorKind.NOTFIXSIZE);
            results.add(result);
        } else if (!enumAssTable.getAllUndefineCode() && null == entityTable.findByEntityKey(data)) {
            EnumDataCheckResultItem result = this.dataRow2Data(rowKeys, data, enumAssTable, EnumCheckErrorKind.UNDEFINE_CODE);
            results.add(result);
        } else if (enumAssTable.getOnlyLeafNode() && null != (entityRow = entityTable.findByEntityKey(data)) && !entityRow.isLeaf()) {
            EnumDataCheckResultItem result = this.dataRow2Data(rowKeys, data, enumAssTable, EnumCheckErrorKind.NOTLEAF);
            results.add(result);
        }
    }

    private EnumDataCheckResultItem dataRow2Data(DimensionValueSet rowKeys, String data, EnumAssTable enumAssTable, EnumCheckErrorKind errorKind) {
        EnumDataCheckResultItem result = new EnumDataCheckResultItem();
        result.setId(Guid.newGuid());
        result.setDataValue(data);
        result.setEnumCode(enumAssTable.getTable().getCode());
        result.setErrorKind(errorKind);
        String unitKey = (String)rowKeys.getValue(this.masterDimName);
        result.setMasterEntityKey(unitKey);
        IEntityRow entityRow = this.masterEntityDic.get(unitKey);
        if (entityRow != null) {
            result.setEntityTitle(entityRow.getTitle());
            result.setEntityOrder(this.getEntityOrder(entityRow));
        } else {
            result.setEntityTitle(unitKey);
            result.setEntityOrder(999999999);
        }
        HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
        for (int i = 0; i < rowKeys.size(); ++i) {
            for (String dimName : this.dimNames) {
                if (!dimName.equals(rowKeys.getName(i))) continue;
                dimNameValueMap.put(dimName, (String)rowKeys.getValue(i));
            }
        }
        result.setDimNameValueMap(dimNameValueMap);
        result.setEnumTitle(enumAssTable.getTable().getTitle());
        result.setField(enumAssTable.getDataField().getCode() + "/" + enumAssTable.getDataField().getName());
        result.setDataLinkKey(enumAssTable.getDataLinkKey());
        result.setRegionId(enumAssTable.getRegionId());
        return result;
    }

    private void doMultCheck(String tableName, boolean hasVersion, final List<DataFdInfoBase> bizFds, final List<DataFdInfoBase> bizFloats, LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper, final EnumAssTable enumTable, String fdCode, final List<EnumDataCheckResultItem> result) throws Exception {
        String sqlFormat = this.generateSql2(tableName, hasVersion, bizFds, bizFloats, tmpFieldAndDbFieldNameWrapper, fdCode, enumTable);
        if (StringUtils.isEmpty((String)sqlFormat)) {
            return;
        }
        final HashMap<String, String> enumDataDic = this.getEnumDataDic(enumTable.getEntityViewKey());
        int argsCount = EnumCheckExecutor.getStrCount(sqlFormat, "?");
        Object[] args = new Object[argsCount];
        int[] argTyps = new int[argsCount];
        for (int i = 0; i < args.length; ++i) {
            args[i] = PeriodUtils.getStartDateOfPeriod((String)this.periodWrapper.toString(), (boolean)false);
            argTyps[i] = 91;
        }
        if (argsCount > 0) {
            this.jdbcTpl.query(sqlFormat, args, argTyps, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumCheckExecutor.this.doMultCheckRow(enumDataDic, enumTable, bizFds, bizFloats, rs);
                    if (null != item) {
                        result.add(item);
                    }
                }
            });
        } else {
            this.jdbcTpl.query(sqlFormat, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumCheckExecutor.this.doMultCheckRow(enumDataDic, enumTable, bizFds, bizFloats, rs);
                    if (null != item) {
                        result.add(item);
                    }
                }
            });
        }
    }

    private HashMap<String, String> getEnumDataDic(String entityViewKey) throws Exception {
        if (this.enumDataDic.containsKey(entityViewKey)) {
            return this.enumDataDic.get(entityViewKey);
        }
        HashMap<String, String> result = new HashMap<String, String>();
        EntityViewDefine enumView = this.entityQueryHelper.getEnumViewByEntityId(entityViewKey);
        IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(enumView, null);
        IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, null, false);
        for (IEntityRow row : entityTable.getAllRows()) {
            IEntityRow pRow = StringUtils.isEmpty((String)row.getParentEntityKey()) ? null : entityTable.findByEntityKey(row.getParentEntityKey());
            result.put(row.getEntityKeyData(), pRow == null ? "" : pRow.getCode());
        }
        this.enumDataDic.put(entityViewKey, result);
        return result;
    }

    private void doEntityTableCheck(String entityCode, List<EnumAssTable> tableList, List<EnumDataCheckResultItem> result) {
        TableModelDefine entityTable;
        if (this.enumFilterCondition.isFilter("FMDM") && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml("FMDM"))) {
            return;
        }
        IEntityDefine entityDefine = this.metaService.queryEntityByCode(entityCode);
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        try {
            entityTable = this.metaService.getTableModel(this.metaService.queryEntityByCode(entityCode).getId());
        }
        catch (Exception e) {
            return;
        }
        IEntityAttribute dwField = entityModel.getBizKeyField();
        LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper = new LinkedHashMap<String, String>();
        tmpFieldAndDbFieldNameWrapper.put(this.tempTable.getRealColName(this.masterDimName), dwField.getName());
        ArrayList<DataFdInfoBase> bizFds = new ArrayList<DataFdInfoBase>();
        bizFds.add(new DataFdInfoBase(dwField.getName(), entityTable.getName()));
        for (EnumAssTable enumTable : tableList) {
            String fdCode = this.getFilterFmlFdCode(enumTable, "FMDM");
            try {
                String sqlFormat;
                boolean hasVersion = true;
                if (enumTable.getAllowMultipleSelect()) {
                    sqlFormat = this.generateSql3(entityTable.getName(), hasVersion, bizFds, null, tmpFieldAndDbFieldNameWrapper, fdCode, enumTable);
                    if (StringUtils.isEmpty((String)sqlFormat)) continue;
                    this.executeSql(true, hasVersion, sqlFormat, enumTable, bizFds, null, result);
                    continue;
                }
                sqlFormat = this.generateSql1(entityTable.getName(), hasVersion, bizFds, null, tmpFieldAndDbFieldNameWrapper, fdCode, enumTable);
                if (StringUtils.isEmpty((String)sqlFormat)) continue;
                this.executeSql(true, hasVersion, sqlFormat, enumTable, bizFds, null, result);
            }
            catch (Exception e) {
                if (!(e.getCause() instanceof EnumCheckException)) continue;
                return;
            }
        }
    }

    private void doEntityTableCheckEnableNrdb(List<EnumAssTable> tableList, List<EnumDataCheckResultItem> results, FormDefine formDefine) throws Exception {
        if (this.enumFilterCondition.isFilter("FMDM") && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml("FMDM"))) {
            return;
        }
        HashMap<String, IEntityTable> entityViewKeyTableMap = new HashMap<String, IEntityTable>();
        for (EnumAssTable enumAssTable : tableList) {
            IEntityTable entityTable = (IEntityTable)entityViewKeyTableMap.get(enumAssTable.getEntityViewKey());
            if (null != entityTable) continue;
            EntityViewDefine enumView = this.entityQueryHelper.getEnumViewByEntityId(enumAssTable.getEntityViewKey());
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(enumView, (String)this.enumDataCheckParam.getDims().combineWithoutVarDim().getValue("DATATIME"));
            entityQuery.markLeaf();
            entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, null, false);
            entityViewKeyTableMap.put(enumAssTable.getEntityViewKey(), entityTable);
        }
        FMDMDataDTO queryParam = new FMDMDataDTO();
        queryParam.setFormSchemeKey(formDefine.getFormScheme());
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        dimensionCollectionBuilder.setValue(this.masterDimName, new Object[]{this.enumDataCheckParam.getDims().combineWithoutVarDim().getValue(this.masterDimName)});
        dimensionCollectionBuilder.setValue("DATATIME", new Object[]{this.enumDataCheckParam.getDims().combineWithoutVarDim().getValue("DATATIME")});
        List queryRes = this.fmdmDataService.list(queryParam, dimensionCollectionBuilder.getCollection());
        for (IFMDMData queryRe : queryRes) {
            String unitKey = (String)queryRe.getMasterKey().getValue(this.masterDimName);
            for (EnumAssTable enumAssTable : tableList) {
                String fdCode = this.getFilterFmlFdCode(enumAssTable, formDefine.getFormCode());
                if (StringUtils.isNotEmpty((String)fdCode)) {
                    int fmlIndex = Integer.parseInt(fdCode.substring(2));
                    String fmlRes = this.mdcodeFmlRes.get(unitKey).get(fmlIndex);
                    if ("1".equals(fmlRes)) continue;
                }
                String data = queryRe.getEntityValue(enumAssTable.getDataField().getCode()).getAsString();
                if ("PARENTCODE".equals(enumAssTable.getDataField().getCode()) && "-".equals(data)) continue;
                IEntityTable entityTable = (IEntityTable)entityViewKeyTableMap.get(enumAssTable.getEntityViewKey());
                this.doCheck(results, queryRe.getMasterKey(), entityTable, enumAssTable, data);
            }
        }
    }

    private void doEntityInfoTableCheck(String tableCode, List<EnumAssTable> tableList, List<EnumDataCheckResultItem> result) throws Exception {
        if (this.enumFilterCondition.isFilter("FMDM") && StringUtils.isEmpty((String)this.enumFilterCondition.getFromFilterFml("FMDM"))) {
            return;
        }
        DataTable dataTable = this.dataSchemeService.getDataTableByCode(tableCode);
        String tableName = this.entityQueryHelper.getTableNameByTableCode(dataTable.getCode());
        String[] bizFdKeys = dataTable.getBizKeys();
        if (0 == bizFdKeys.length) {
            return;
        }
        LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper = new LinkedHashMap<String, String>();
        ArrayList<DataFdInfoBase> bizFds = new ArrayList<DataFdInfoBase>();
        for (String fdKey : bizFdKeys) {
            String dimName;
            DataField keyField = this.dataSchemeService.getDataField(fdKey);
            String string = "DATATIME".equals(keyField.getCode()) ? "DATATIME" : (dimName = StringUtils.isNotEmpty((String)keyField.getRefDataEntityKey()) ? this.metaService.getDimensionName(keyField.getRefDataEntityKey()) : null);
            if (!this.isSelectDim(dimName)) continue;
            String realTempColName = this.tempTable.getRealColName(dimName);
            if (!this.masterDimName.equals(dimName) && !"DATATIME".equals(dimName)) continue;
            tmpFieldAndDbFieldNameWrapper.put(realTempColName, keyField.getCode());
            bizFds.add(new DataFdInfoBase(keyField.getCode(), tableName));
        }
        for (EnumAssTable enumTable : tableList) {
            boolean hasVersion = false;
            String fdCode = this.getFilterFmlFdCode(enumTable, "FMDM");
            try {
                if (enumTable.getAllowMultipleSelect()) {
                    this.doMultCheck(tableName, hasVersion, bizFds, null, tmpFieldAndDbFieldNameWrapper, enumTable, fdCode, result);
                    continue;
                }
                String sqlFormat = this.generateSql1(tableName, hasVersion, bizFds, null, tmpFieldAndDbFieldNameWrapper, fdCode, enumTable);
                if (StringUtils.isEmpty((String)sqlFormat)) continue;
                this.executeSql(false, hasVersion, sqlFormat, enumTable, bizFds, null, result);
            }
            catch (Exception e) {
                if (!(e.getCause() instanceof EnumCheckException)) continue;
                return;
            }
        }
    }

    private String getFilterFmlFdCode(EnumAssTable enumTable, String formCode) {
        DataLinkDefine dl = this.runTimeViewController.queryDataLinkDefine(enumTable.getDataLinkKey());
        String zbFml = String.format("%s[%s,%s]", formCode, dl.getRowNum(), dl.getColNum());
        String fml = this.enumFilterCondition.getZbFilterFml(zbFml);
        if (StringUtils.isEmpty((String)fml)) {
            return null;
        }
        return this.dicFilterFdCode.get(fml);
    }

    private void executeSql(final boolean isEntityTableCheck, boolean hasVersion, String sqlFormat, final EnumAssTable enumTable, final List<DataFdInfoBase> bizFds, final List<DataFdInfoBase> bizFloats, final List<EnumDataCheckResultItem> result) {
        int argsCount = EnumCheckExecutor.getStrCount(sqlFormat, "?");
        Object[] args = new Object[argsCount];
        int[] argTyps = new int[argsCount];
        for (int i = 0; i < args.length; ++i) {
            args[i] = PeriodUtils.getStartDateOfPeriod((String)this.periodWrapper.toString(), (boolean)false);
            argTyps[i] = 91;
        }
        if (argsCount > 0) {
            this.jdbcTpl.query(sqlFormat, args, argTyps, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumCheckExecutor.this.dataRow2Data(enumTable, bizFds, bizFloats, rs);
                    if (item != null) {
                        if (isEntityTableCheck && "PARENTCODE".equals(enumTable.getDataField().getCode()) && "-".equals(item.getDataValue())) {
                            return;
                        }
                        result.add(item);
                    }
                }
            });
        } else {
            this.jdbcTpl.query(sqlFormat, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    EnumDataCheckResultItem item = EnumCheckExecutor.this.dataRow2Data(enumTable, bizFds, bizFloats, rs);
                    if (item != null) {
                        if (isEntityTableCheck && "PARENTCODE".equals(enumTable.getDataField().getCode()) && "-".equals(item.getDataValue())) {
                            return;
                        }
                        result.add(item);
                    }
                }
            });
        }
    }

    private EnumDataCheckResultItem doMultCheckRow(HashMap<String, String> enumDataDic, EnumAssTable enumAssTable, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, ResultSet rs) throws SQLException {
        String enumDataValue = rs.getString("dc");
        if (StringUtils.isEmpty((String)enumDataValue)) {
            if (!enumAssTable.getCanNull()) {
                EnumDataCheckResultItem result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
                result.setErrorKind(EnumCheckErrorKind.ISNULL);
                return result;
            }
            return null;
        }
        for (String subValue : enumDataValue.split(";")) {
            if (StringUtils.isEmpty((String)subValue)) continue;
            if (enumAssTable.getFixSize() > 0 && subValue.length() != enumAssTable.getFixSize()) {
                EnumDataCheckResultItem result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
                result.setErrorKind(EnumCheckErrorKind.NOTFIXSIZE);
                return result;
            }
            if (!enumAssTable.getAllUndefineCode() && !enumDataDic.containsKey(subValue)) {
                EnumDataCheckResultItem result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
                result.setErrorKind(EnumCheckErrorKind.UNDEFINE_CODE);
                return result;
            }
            if (!enumAssTable.getOnlyLeafNode() || !enumDataDic.containsKey(subValue) || !enumDataDic.containsValue(subValue)) continue;
            EnumDataCheckResultItem result = this.dataRow2Data(enumAssTable, bizFds, bizFloats, rs);
            result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
            return result;
        }
        return null;
    }

    private EnumDataCheckResultItem dataRow2Data(EnumAssTable enumTable, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, ResultSet rs) throws SQLException {
        EnumDataCheckResultItem result = new EnumDataCheckResultItem();
        result.setId(Guid.newGuid());
        int idx = 1;
        String unitKey = null;
        HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
        for (DataFdInfoBase bizFd : bizFds) {
            if ("MDCODE".equals(bizFd.getFieldName())) {
                unitKey = rs.getString(idx);
            }
            for (String dimName : this.dimNames) {
                if (!bizFd.getFieldName().equals(dimName)) continue;
                dimNameValueMap.put(bizFd.getFieldName(), rs.getString(idx));
            }
            ++idx;
        }
        result.setDimNameValueMap(dimNameValueMap);
        if (null != bizFloats) {
            int bizftIdx = 1;
            StringBuilder idStr = new StringBuilder();
            for (DataFdInfoBase bizFt : bizFloats) {
                idStr.append(rs.getString(idx++));
                if (bizftIdx < bizFloats.size()) {
                    idStr.append("#^$");
                }
                ++bizftIdx;
            }
            result.setDataId(idStr.toString());
        }
        result.setMasterEntityKey(unitKey);
        IEntityRow entityRow = this.masterEntityDic.get(unitKey);
        if (null != entityRow) {
            result.setEntityTitle(entityRow.getTitle());
            result.setEntityOrder(this.getEntityOrder(entityRow));
        } else {
            result.setEntityTitle(unitKey);
            result.setEntityOrder(999999999);
        }
        result.setDataValue(rs.getString(idx++));
        result.setEnumCode(rs.getString(idx++));
        ArrayList<String> enumTitle = new ArrayList<String>();
        for (IEntityAttribute fd : enumTable.getEnumTitleField()) {
            enumTitle.add(rs.getString(idx++));
        }
        result.setEnumTitle(String.join((CharSequence)"|", enumTitle));
        result.setDataLinkKey(enumTable.getDataLinkKey());
        result.setRegionId(enumTable.getRegionId());
        result.setField(enumTable.getDataField().getCode() + "/" + enumTable.getDataField().getName());
        if (StringUtils.isEmpty((String)result.getDataValue())) {
            if (!enumTable.getCanNull()) {
                result.setErrorKind(EnumCheckErrorKind.ISNULL);
            }
        } else if (enumTable.getFixSize() > 0 && result.getDataValue().length() != enumTable.getFixSize()) {
            result.setErrorKind(EnumCheckErrorKind.NOTFIXSIZE);
        } else if (!enumTable.getAllUndefineCode()) {
            if (StringUtils.isEmpty((String)result.getEnumCode())) {
                result.setErrorKind(EnumCheckErrorKind.UNDEFINE_CODE);
            } else if (enumTable.getOnlyLeafNode()) {
                result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
            }
        } else if (enumTable.getOnlyLeafNode() && !StringUtils.isEmpty((String)result.getEnumCode())) {
            result.setErrorKind(EnumCheckErrorKind.NOTLEAF);
        }
        result.setEnumCode(enumTable.getTable().getCode());
        result.setEnumTitle(enumTable.getTable().getTitle());
        return result;
    }

    /*
     * WARNING - void declaration
     */
    private String generateSql3(String tableName, boolean hasVersion, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper, String fdCode, EnumAssTable enumAssTable) {
        void var12_21;
        boolean bl;
        boolean bl2;
        String libTableName = this.dataQueryHelper.getLibraryTableName(this.enumDataCheckParam.getTaskKey(), tableName);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT ");
        for (int i = 0; i < bizFds.size(); ++i) {
            sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(",");
        }
        sqlStr.append(" dt1.fieldValue as dC,");
        sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" as enumC,");
        int idxEnumTitle = 0;
        for (IEntityAttribute iEntityAttribute : enumAssTable.getEnumTitleField()) {
            if (idxEnumTitle > 0) {
                sqlStr.append(",");
            }
            sqlStr.append(" et.").append(iEntityAttribute.getCode()).append(" as enumT").append(idxEnumTitle++);
        }
        sqlStr.append(" from ").append(libTableName).append(" dt ");
        sqlStr.append(" left join ").append(libTableName).append("_SUBLIST dt1 ON dt.id = dt1.MASTERID AND '").append(enumAssTable.getDataField().getFieldName()).append("' = dt1.FIELDNAME");
        sqlStr.append(" left join ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" et");
        sqlStr.append(" on dt1.FIELDVALUE = ").append(" et.").append(enumAssTable.getEnumCodeField().getCode());
        sqlStr.append(" where ");
        if (hasVersion) {
            sqlStr.append(String.format(" ((dt.%s <= ? or dt.%s is null) and (dt.%s > ? or dt.%s is null)) ", "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME"));
        }
        int index = 0;
        sqlStr.append(" exists (select 1 from ").append(this.tempTable.getTableName()).append(" ass").append(" where");
        for (Map.Entry<String, String> entry : tmpFieldAndDbFieldNameWrapper.entrySet()) {
            if (index > 0) {
                sqlStr.append(" and ");
            }
            String tmpFieldName = entry.getKey();
            String dbFieldName = entry.getValue();
            sqlStr.append(" ass.").append(tmpFieldName).append(" = ").append(" dt.").append(dbFieldName);
            ++index;
        }
        if (!StringUtils.isEmpty((String)fdCode)) {
            sqlStr.append(" and ass").append(".").append(fdCode).append("='0'");
        }
        sqlStr.append(") ");
        boolean bl3 = false;
        sqlStr.append(" and ");
        if (!enumAssTable.getCanNull()) {
            sqlStr.append("(");
            sqlStr.append(" dt1.FIELDVALUE is null ");
            bl2 = true;
        } else {
            sqlStr.append(" dt1.FIELDVALUE is not null ");
            sqlStr.append(" and (");
        }
        if (enumAssTable.getFixSize() > 0) {
            boolean bl4;
            if (bl2) {
                sqlStr.append(" or ");
            }
            if (bl4 = DatabaseInstance.getDatabase().isDatabase("MYSQL")) {
                sqlStr.append(" char_length(dt1.FIELDVALUE) <> ").append(enumAssTable.getFixSize());
            } else {
                sqlStr.append(" length(dt1.FIELDVALUE) <> ").append(enumAssTable.getFixSize());
            }
            bl = true;
        }
        if (!enumAssTable.getAllUndefineCode()) {
            if (bl) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is null ");
            boolean bl5 = true;
            if (enumAssTable.getOnlyLeafNode() && enumAssTable.getEnumPCodeField() != null) {
                sqlStr.append(" or ");
                sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
                sqlStr.append(" where dt1.FIELDVALUE = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
                sqlStr.append(")");
                boolean bl6 = true;
            }
        } else if (enumAssTable.getOnlyLeafNode()) {
            if (bl) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is not null and");
            sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
            sqlStr.append(" where dt1.FIELDVALUE = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
            sqlStr.append(")");
            boolean bl7 = true;
        }
        sqlStr.append(")");
        if (var12_21 == false) {
            return null;
        }
        return sqlStr.toString();
    }

    private String generateSql2(String tableName, boolean hasVersion, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper, String fdCode, EnumAssTable enumAssTable) {
        int i;
        String libTableName = this.dataQueryHelper.getLibraryTableName(this.enumDataCheckParam.getTaskKey(), tableName);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT ");
        for (i = 0; i < bizFds.size(); ++i) {
            sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(",");
        }
        if (null != bizFloats) {
            for (i = 0; i < bizFloats.size(); ++i) {
                sqlStr.append(" dt.").append(bizFloats.get(i).getFieldName()).append(" as ").append(bizFloats.get(i).getFieldName() + 1).append(",");
            }
        }
        sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" as dC,");
        sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" as enumC,");
        int idxEnumTitle = 0;
        for (IEntityAttribute fd : enumAssTable.getEnumTitleField()) {
            if (idxEnumTitle > 0) {
                sqlStr.append(",");
            }
            sqlStr.append(" et.").append(fd.getCode()).append(" as enumT").append(idxEnumTitle++);
        }
        sqlStr.append(" from ").append(libTableName).append(" dt ");
        sqlStr.append(" left join ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" et");
        sqlStr.append(" on dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" et.").append(enumAssTable.getEnumCodeField().getCode());
        sqlStr.append(" where ");
        if (hasVersion) {
            sqlStr.append(String.format(" ((dt.%s <= ? or dt.%s is null) and (dt.%s > ? or dt.%s is null)) ", "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME"));
        }
        int index = 0;
        sqlStr.append(" exists (select 1 from ").append(this.tempTable.getTableName()).append(" ass").append(" where");
        for (Map.Entry<String, String> tmpFieldAndDbFieldName : tmpFieldAndDbFieldNameWrapper.entrySet()) {
            if (index > 0) {
                sqlStr.append(" and ");
            }
            String tmpFieldName = tmpFieldAndDbFieldName.getKey();
            String dbFieldName = tmpFieldAndDbFieldName.getValue();
            sqlStr.append(" ass.").append(tmpFieldName).append(" = ").append(" dt.").append(dbFieldName);
            ++index;
        }
        if (!StringUtils.isEmpty((String)fdCode)) {
            sqlStr.append(" and ass").append(".").append(fdCode).append("='0'");
        }
        sqlStr.append(") ");
        return sqlStr.toString();
    }

    /*
     * WARNING - void declaration
     */
    private String generateSql1(String tableName, boolean hasVersion, List<DataFdInfoBase> bizFds, List<DataFdInfoBase> bizFloats, LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper, String fdCode, EnumAssTable enumAssTable) {
        void var12_21;
        boolean bl;
        boolean bl2;
        int i;
        String libTableName = this.dataQueryHelper.getLibraryTableName(this.enumDataCheckParam.getTaskKey(), tableName);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT ");
        for (i = 0; i < bizFds.size(); ++i) {
            sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(",");
        }
        if (bizFloats != null) {
            for (i = 0; i < bizFloats.size(); ++i) {
                sqlStr.append(" dt.").append(bizFds.get(i).getFieldName()).append(" as ").append(bizFds.get(i).getFieldName() + 1).append(",");
            }
        }
        sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" as dC,");
        sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" as enumC,");
        int idxEnumTitle = 0;
        for (IEntityAttribute iEntityAttribute : enumAssTable.getEnumTitleField()) {
            if (idxEnumTitle > 0) {
                sqlStr.append(",");
            }
            sqlStr.append(" et.").append(iEntityAttribute.getCode()).append(" as enumT").append(idxEnumTitle++);
        }
        sqlStr.append(" from ").append(libTableName).append(" dt ");
        sqlStr.append(" left join ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" et");
        sqlStr.append(" on dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" et.").append(enumAssTable.getEnumCodeField().getCode());
        sqlStr.append(" where ");
        if (hasVersion) {
            sqlStr.append(String.format(" ((dt.%s <= ? or dt.%s is null) and (dt.%s > ? or dt.%s is null)) ", "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME"));
        }
        int index = 0;
        sqlStr.append(" exists (select 1 from ").append(this.tempTable.getTableName()).append(" ass").append(" where");
        for (Map.Entry<String, String> entry : tmpFieldAndDbFieldNameWrapper.entrySet()) {
            if (index > 0) {
                sqlStr.append(" and ");
            }
            String tmpFieldName = entry.getKey();
            String dbFieldName = entry.getValue();
            sqlStr.append(" ass.").append(tmpFieldName).append(" = ").append(" dt.").append(dbFieldName);
            ++index;
        }
        if (!StringUtils.isEmpty((String)fdCode)) {
            sqlStr.append(" and ass").append(".").append(fdCode).append("='0'");
        }
        sqlStr.append(") ");
        boolean bl3 = false;
        sqlStr.append(" and ");
        if (!enumAssTable.getCanNull()) {
            sqlStr.append("(");
            sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" is null ");
            bl2 = true;
        } else {
            sqlStr.append(" dt.").append(enumAssTable.getDataField().getFieldName()).append(" is not null ");
            sqlStr.append(" and (");
        }
        if (enumAssTable.getFixSize() > 0) {
            boolean bl4;
            if (bl2) {
                sqlStr.append(" or ");
            }
            if (bl4 = DatabaseInstance.getDatabase().isDatabase("MYSQL")) {
                sqlStr.append(" char_length(dt.").append(enumAssTable.getDataField().getFieldName()).append(") <> ").append(enumAssTable.getFixSize());
            } else {
                sqlStr.append(" length(dt.").append(enumAssTable.getDataField().getFieldName()).append(") <> ").append(enumAssTable.getFixSize());
            }
            bl = true;
        }
        if (!enumAssTable.getAllUndefineCode()) {
            if (bl) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is null ");
            boolean bl5 = true;
            if (enumAssTable.getOnlyLeafNode() && enumAssTable.getEnumPCodeField() != null) {
                sqlStr.append(" or ");
                sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
                sqlStr.append(" where dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
                sqlStr.append(")");
                boolean bl6 = true;
            }
        } else if (enumAssTable.getOnlyLeafNode()) {
            if (bl) {
                sqlStr.append(" or ");
            }
            sqlStr.append(" et.").append(enumAssTable.getEnumCodeField().getCode()).append(" is not null and");
            sqlStr.append(" exists(select 1 from ").append(this.getEnumTableStr(enumAssTable.getTable().getCode())).append(" etp ");
            sqlStr.append(" where dt.").append(enumAssTable.getDataField().getFieldName()).append(" = ").append(" etp.").append(enumAssTable.getEnumPCodeField().getCode());
            sqlStr.append(")");
            boolean bl7 = true;
        }
        sqlStr.append(")");
        if (var12_21 == false) {
            return null;
        }
        return sqlStr.toString();
    }

    private String getEnumTableStr(String tableCode) {
        return String.format(" (select * from %s where ((%s <= ? or %s is null) and (%s > ? or %s is null)) and recoveryflag = 0) ", tableCode, "VALIDTIME", "VALIDTIME", "INVALIDTIME", "INVALIDTIME");
    }

    public static int getStrCount(String str, String subStr) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            ++count;
            index += subStr.length();
        }
        return count;
    }

    private EnumAssTable createEnumAssTable(DataLinkDefine dl, FormFieldWrapper fmF) {
        DataFdInfo fd;
        TableModelDefine entityTable = this.metaService.getTableModel(fmF.getRefEntity().getId());
        IEntityModel refEntityModel = this.metaService.getEntityModel(fmF.getRefEntity().getId());
        EnumAssTable result = new EnumAssTable();
        result.setCanNull(true);
        if (fmF.getFieldObj() instanceof ColumnModelDefine) {
            fd = fmF.createFdInfo(this.entityQueryHelper);
            result.setAllowMultipleSelect(((ColumnModelDefine)fmF.getFieldObjAs()).isMultival());
            result.setCanNull(dl.getDataValidation() == null || dl.getDataValidation().size() == 0 || !dl.getDataValidation().contains(String.format("NOT ISNULL(%s[%s])", fd.getTableName(), fd.getCode())));
            if (result.getCanNull()) {
                result.setCanNull(fd.isNullAble());
            }
        } else {
            FieldDefine tmpFd = (FieldDefine)fmF.getFieldObjAs();
            fd = fmF.createFdInfo(this.entityQueryHelper);
            result.setAllowMultipleSelect(tmpFd.getAllowMultipleSelect());
            DataField dataField = this.dataSchemeService.getDataField(tmpFd.getKey());
            if (dataField.getValidationRules() != null && dataField.getValidationRules().size() > 0) {
                for (ValidationRule rule : dataField.getValidationRules()) {
                    if (!StringUtils.isNotEmpty((String)rule.getVerification()) || !rule.getVerification().contains(String.format("NOT ISNULL(%s[%s])", fd.getTableName(), dataField.getCode()))) continue;
                    result.setCanNull(false);
                    break;
                }
            }
            if (result.getCanNull()) {
                result.setCanNull(fd.isNullAble());
            }
        }
        if (result.getCanNull()) {
            result.setCanNull(dl.getAllowNullAble());
        }
        result.setEntityViewKey(refEntityModel.getEntityId());
        result.setTable(entityTable);
        result.setAllUndefineCode(dl.getAllowUndefinedCode());
        result.setOnlyLeafNode(!dl.getAllowNotLeafNodeRefer());
        result.setDataField(fd);
        result.setFixSize(-1);
        result.setFixSize(fd.getFixedSize());
        result.setDataLinkKey(dl.getKey());
        result.setRegionId(dl.getRegionKey());
        result.setEnumCodeField(refEntityModel.getBizKeyField());
        result.setEnumPCodeField(refEntityModel.getParentField());
        result.setEnumTitleField(Arrays.asList(refEntityModel.getNameField()));
        assert (result.getEnumCodeField() != null);
        assert (result.getEnumTitleField() != null);
        assert (dl.getAllowNotLeafNodeRefer() || result.getEnumPCodeField() != null);
        if (this.enumDataCheckParam.isIgnoreBlank()) {
            result.setCanNull(true);
        }
        return result;
    }

    private boolean isSelectDim(String dimName) {
        try {
            DimensionValueSet dimensionValueSet = this.enumDataCheckParam.getDims().combineDim();
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                if (!dimensionValueSet.getName(i).equals(dimName)) continue;
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean isFilterEnum1(IEntityDefine entityDefine) {
        if (entityDefine == null) {
            return true;
        }
        return this.enumDic.size() > 0 && !this.enumDic.containsKey(entityDefine.getCode());
    }

    private boolean isFilterForm(String formKey) {
        boolean result;
        boolean bl = result = this.formDic.size() > 0 && !this.formDic.containsKey(formKey);
        if (!result) {
            FormDefine fm = this.runTimeViewController.queryFormById(formKey);
            if (fm == null) {
                return true;
            }
            switch (fm.getFormType()) {
                case FORM_TYPE_QUERY: 
                case FORM_TYPE_TEXT_INFO: 
                case FORM_TYPE_INTERMEDIATE: 
                case FORM_TYPE_ATTACHED: {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public Set<String> getSelUnitKeys() {
        return this.masterEntityDic.keySet();
    }

    public int getErrorCount() {
        return this.errCount.get();
    }

    private int getEntityOrder(IEntityRow entityRow) {
        String order = entityRow.getEntityOrder().toString();
        int idx = order.indexOf(".");
        if (idx > 0) {
            order = order.substring(0, idx);
        }
        if (order.length() > 9) {
            order = order.substring(order.length() - 9);
        }
        return Integer.parseInt(order);
    }
}

