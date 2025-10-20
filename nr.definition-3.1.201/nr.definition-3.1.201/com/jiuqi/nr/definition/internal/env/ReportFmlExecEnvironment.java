/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IUnitLeafFinder
 *  com.jiuqi.np.dataengine.intf.ZBAuthJudger
 *  com.jiuqi.np.dataengine.setting.AuthorityType
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dto.DataDimDTO
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.ZBAuthJudger;
import com.jiuqi.np.dataengine.setting.AuthorityType;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.EnvDimParam;
import com.jiuqi.nr.definition.facade.EnvDimProvider;
import com.jiuqi.nr.definition.facade.ExecEnvDimProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.formulatracking.CustomVariable;
import com.jiuqi.nr.definition.formulatracking.ExpressionVariable;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.CacheableColumnLinkFinder;
import com.jiuqi.nr.definition.internal.env.CacheableDataLinkFinder;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ExecuteFormulaExpression;
import com.jiuqi.nr.definition.internal.env.ItemNodeProvider;
import com.jiuqi.nr.definition.internal.env.ReportColumnLinkFinder;
import com.jiuqi.nr.definition.internal.env.ReportDataLinkFinder;
import com.jiuqi.nr.definition.internal.env.ReportFunContext;
import com.jiuqi.nr.definition.internal.env.UnitLeafFinder;
import com.jiuqi.nr.definition.internal.env.VarJD;
import com.jiuqi.nr.definition.internal.env.VarYF;
import com.jiuqi.nr.definition.util.SingleDimUtil;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class ReportFmlExecEnvironment
implements IFmlExecEnvironment {
    private static final Logger logger = LoggerFactory.getLogger(ReportFmlExecEnvironment.class);
    private final SingleDimUtil singleDimUtil;
    private IEntityMetaService entityMetaService;
    protected IDataLinkFinder datalinkFinder;
    protected IDataModelLinkFinder dataModelLinkFinder;
    protected IPeriodAdapter periodAdapter;
    protected VariableManager variableManager;
    protected int periodType = -1;
    protected IRunTimeViewController controller;
    protected IDataDefinitionRuntimeController runtimeController;
    protected IEntityViewRunTimeController entityViewRunTimeController;
    protected Map<String, EntityViewDefine> entityViewDefines;
    protected String unitDimension;
    protected String formSchemeKey;
    protected boolean useCache;
    protected IFieldValueProcessor fieldValueProcessor;
    protected IFieldValueUpdateProcessor fieldValueUpdateProcessor;
    private static final int VALUE_TYPE = 0;
    private static final int NUMBER = 0;
    private static final int STRING = 1;
    private static final int FORMUAL_TYPE = 1;
    private String filePrefix;
    private static String BD_PREFIX = "MD_";
    private TaskDefine taskDefine;
    private ReportFunContext reportFunContext;
    protected DataModelService dataModelService;
    private DataSchemeService dataSchemeService;
    private IEntityDataService entityDataService;
    protected ExecEnvDimProvider dimProvider;
    protected EnvDimProvider envDimProvider;
    protected String dataScehmeKey;
    private Map<String, IPeriodAdapter> linkTaskPeriodAdapter = new HashMap<String, IPeriodAdapter>();
    private Map<String, RelationDimPeriodCache> relationDimSchemeCache = new HashMap<String, RelationDimPeriodCache>();
    private Boolean isBatchSummary = null;

    public ReportFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey) {
        this(controller, runtimeController, entityViewRunTimeController, formSchemeKey, null);
    }

    public ReportFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey, Map<String, Object> variableMap) {
        this(controller, runtimeController, entityViewRunTimeController, formSchemeKey, null, variableMap);
    }

    public ReportFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey, ExectuteFormula exectuteFormula, Map<String, Object> variableMap) {
        this.controller = controller;
        this.runtimeController = runtimeController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.formSchemeKey = formSchemeKey;
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.dataSchemeService = BeanUtil.getBean(DataSchemeService.class);
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.envDimProvider = BeanUtil.getBean(EnvDimProvider.class);
        this.singleDimUtil = BeanUtil.getBean(SingleDimUtil.class);
        this.entityDataService = BeanUtil.getBean(IEntityDataService.class);
        this.checkAnal(controller, formSchemeKey);
        if (controller != null) {
            this.setVariableManager(formSchemeKey, variableMap);
        }
    }

    public ReportFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey, boolean useCache) {
        this(controller, runtimeController, entityViewRunTimeController, formSchemeKey);
        this.useCache = useCache;
    }

    public ReportFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController) {
        this(controller, runtimeController, entityViewRunTimeController, null);
    }

    public EntityViewDefine getDefaultEntityViewDefine(String tableKey) {
        return null;
    }

    private void setVariableManager(String formSchemeKey, Map<String, Object> variableMap) {
        List<FormulaVariDefine> formulaVariables = this.controller.queryAllFormulaVariable(formSchemeKey);
        this.variableManager = this.getVariableManager();
        for (FormulaVariDefine formulaVar : formulaVariables) {
            if (formulaVar.getType() == 0) {
                int type = this.getVarType(formulaVar);
                if (formulaVar.getInitType() == 1) {
                    if (variableMap != null) {
                        Object object = this.getValue(type, variableMap.get(formulaVar.getCode()));
                        this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), type, object));
                        continue;
                    }
                    this.variableManager.add((Variable)new CustomVariable(formulaVar.getCode(), type));
                    continue;
                }
                Object value = this.getValue(type, formulaVar);
                this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), type, value));
                continue;
            }
            if (formulaVar.getType() == 1) {
                ExecuteFormulaExpression executeFormulaExpression = new ExecuteFormulaExpression(this.runtimeController);
                String formulaExpression = formulaVar.getInitValue();
                int formulaType = executeFormulaExpression.executeFormula(this, formulaExpression);
                if (formulaType == -1) continue;
                this.variableManager.add((Variable)new ExpressionVariable(formulaVar.getCode(), formulaVar.getTitle(), formulaType, formulaExpression));
                continue;
            }
            int varType = this.getVarType(formulaVar);
            this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), 11, (Object)new ArrayData(varType, this.getListVariObj(varType, formulaVar))));
        }
        List allVars = this.variableManager.getAllVars();
        if (variableMap != null) {
            Set<Object> exixtVars = !CollectionUtils.isEmpty(allVars) ? allVars.stream().map(Variable::getVarName).map(String::toLowerCase).collect(Collectors.toSet()) : new HashSet();
            for (Map.Entry<String, Object> entry : variableMap.entrySet()) {
                if (!exixtVars.add(entry.getKey().toLowerCase())) continue;
                this.variableManager.add(new Variable(entry.getKey(), entry.getKey(), 0, entry.getValue()));
            }
        }
    }

    private int getVarType(FormulaVariDefine formulaVar) {
        int type = 6;
        type = formulaVar.getValueType() == 0 ? 3 : (formulaVar.getValueType() == 1 ? 6 : 2);
        return type;
    }

    private Object getValue(int type, FormulaVariDefine formulaVar) {
        String s;
        Object o = null;
        o = type == 2 ? PeriodUtil.period2Calendar((String)formulaVar.getInitValue()) : (type == 3 ? (null == formulaVar.getInitValue() ? Double.valueOf(Double.parseDouble("0")) : (null != (s = formulaVar.getInitValue()) && !"".equals(s) ? Double.valueOf(Double.parseDouble(formulaVar.getInitValue())) : Double.valueOf(Double.parseDouble("0")))) : formulaVar.getInitValue());
        return o;
    }

    private Object getValue(int type, Object obj) {
        Object o = obj;
        if (obj != null && type == 2) {
            o = PeriodUtil.period2Calendar((String)((String)obj));
        }
        return o;
    }

    private List<?> getListVariObj(int type, FormulaVariDefine formulaVar) {
        ArrayList<String> o = null;
        o = type == 3 ? new ArrayList(Arrays.asList(StringUtils.split((String)formulaVar.getInitValue(), (String)",")).stream().mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList())) : new ArrayList<String>(Arrays.asList(StringUtils.split((String)formulaVar.getInitValue(), (String)",")));
        return o;
    }

    private void checkAnal(IRunTimeViewController controller, String formSchemeKey) {
        FormSchemeDefine formScheme;
        if (controller != null && StringUtils.isNotEmpty((String)formSchemeKey) && (formScheme = controller.getFormScheme(formSchemeKey)) != null) {
            TaskDefine taskDefine = controller.queryTaskDefine(formScheme.getTaskKey());
            DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
            this.filePrefix = dataScheme.getPrefix();
            if (StringUtils.isEmpty((String)this.filePrefix)) {
                FormSchemeDefine sourceScheme;
                List<TaskLinkDefine> links;
                this.filePrefix = formScheme.getFilePrefix();
                if (taskDefine.getTaskType() == TaskType.TASK_TYPE_ANALYSIS && (links = controller.queryLinksByCurrentFormScheme(formSchemeKey)).size() == 1 && (sourceScheme = controller.getFormScheme(links.get(0).getRelatedFormSchemeKey())) != null) {
                    this.filePrefix = sourceScheme.getFilePrefix();
                }
            }
        }
    }

    public void setDataScehmeKey(String dataScehmeKey) {
        this.dataScehmeKey = dataScehmeKey;
    }

    public String getDataScehmeKey() {
        if (StringUtils.isNotEmpty((String)this.dataScehmeKey)) {
            return this.dataScehmeKey;
        }
        TaskDefine taskDefine = this.getTaskDefine();
        if (taskDefine != null) {
            return taskDefine.getDataScheme();
        }
        return null;
    }

    public IDataLinkFinder getDataLinkFinder() {
        if (this.datalinkFinder == null && this.formSchemeKey != null) {
            ReportDataLinkFinder newFinder = new ReportDataLinkFinder(this.controller, this.runtimeController, this.entityViewRunTimeController, this.formSchemeKey);
            this.datalinkFinder = this.useCache ? new CacheableDataLinkFinder(newFinder) : newFinder;
        }
        return this.datalinkFinder;
    }

    public IDataModelLinkFinder getDataModelLinkFinder() {
        if (this.dataModelLinkFinder == null && this.formSchemeKey != null) {
            ReportColumnLinkFinder newFinder = new ReportColumnLinkFinder(this.controller, this.formSchemeKey);
            this.dataModelLinkFinder = this.useCache ? new CacheableColumnLinkFinder(newFinder) : newFinder;
        }
        return this.dataModelLinkFinder;
    }

    public void setDataModelLinkFinder(IDataModelLinkFinder dataModelLinkFinder) {
        this.dataModelLinkFinder = dataModelLinkFinder;
    }

    public IPeriodAdapter getPeriodAdapter(ExecutorContext context) {
        if (this.periodAdapter == null) {
            EntityViewDefine periodEntityViewDefine = this.getEntityViewDefine(context, "DATATIME");
            PeriodEngineService periodService = BeanUtil.getBean(PeriodEngineService.class);
            this.periodAdapter = periodService.getPeriodAdapter().getPeriodProvider(periodEntityViewDefine.getEntityId());
        }
        return this.periodAdapter;
    }

    public IPeriodAdapter getPeriodAdapter(ExecutorContext context, String linkAlias) {
        FormSchemeDefine formScheme;
        TaskLinkDefine taskLinkDefine;
        if (this.linkTaskPeriodAdapter.containsKey(linkAlias)) {
            return this.linkTaskPeriodAdapter.get(linkAlias);
        }
        IPeriodProvider iPeriodAdapter = null;
        if (StringUtils.isNotEmpty((String)this.formSchemeKey) && (taskLinkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias)) != null && (formScheme = this.controller.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey())) != null) {
            IPeriodEntityAdapter periodEntityAdapter = BeanUtil.getBean(IPeriodEntityAdapter.class);
            iPeriodAdapter = periodEntityAdapter.getPeriodProvider(formScheme.getDateTime());
        }
        this.linkTaskPeriodAdapter.put(linkAlias, (IPeriodAdapter)iPeriodAdapter);
        return iPeriodAdapter;
    }

    public VariableManager getVariableManager() {
        if (this.variableManager == null) {
            this.variableManager = new VariableManager();
            switch (this.getPeriodType()) {
                case 4: {
                    this.variableManager.add((Variable)new VarYF());
                    this.variableManager.add((Variable)new VarJD());
                    break;
                }
                case 3: {
                    this.variableManager.add((Variable)new VarJD());
                }
            }
        }
        return this.variableManager;
    }

    public int getPeriodType() {
        if (this.periodType < 0) {
            List dimensions;
            Optional<DataDimDTO> periodOpt;
            FormSchemeDefine formScheme = this.getFormSchemeDefine();
            if (formScheme != null) {
                this.periodType = formScheme.getPeriodType().type();
            } else if (StringUtils.isNotEmpty((String)this.dataScehmeKey) && (periodOpt = (dimensions = this.dataSchemeService.getDataSchemeDimension(this.dataScehmeKey)).stream().filter(d -> d.getDimensionType() == DimensionType.PERIOD).findFirst()).isPresent()) {
                this.periodType = periodOpt.get().getPeriodType().type();
            }
        }
        return this.periodType;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        return formScheme;
    }

    public TaskDefine getTaskDefine() {
        FormSchemeDefine formScheme;
        if (this.taskDefine == null && this.formSchemeKey != null && (formScheme = this.getFormSchemeDefine()) != null) {
            this.taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
        }
        return this.taskDefine;
    }

    public EntityViewDefine getEntityViewDefine(ExecutorContext context, String dimensionName) {
        EntityViewDefine entityViewDefine;
        if (this.entityViewDefines == null) {
            this.initEntityViewDefines(context);
        }
        if ((entityViewDefine = this.entityViewDefines.get(dimensionName)) == null && StringUtils.isNotEmpty((String)context.getCurrentEntityId())) {
            try {
                String dimName = context.getCache().getDataDefinitionsCache().getDimensionName(context.getCurrentEntityId());
                if (dimName.equalsIgnoreCase(dimensionName)) {
                    RunTimeEntityViewDefineImpl entityViewDefine1 = new RunTimeEntityViewDefineImpl();
                    entityViewDefine1.setEntityId(context.getCurrentEntityId());
                    return entityViewDefine1;
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return entityViewDefine;
    }

    private void initEntityViewDefines(ExecutorContext context) {
        this.entityViewDefines = new HashMap<String, EntityViewDefine>();
        if (this.formSchemeKey != null) {
            try {
                DsContext dsContext;
                String entityId;
                FormSchemeDefine formScheme = this.getFormSchemeDefine();
                String masterViews = formScheme.getMasterEntitiesKey();
                String filterExpression = formScheme.getFilterExpression();
                if (StringUtils.isNotEmpty((String)masterViews)) {
                    String[] idArray;
                    for (String entityId2 : idArray = masterViews.split(";")) {
                        String dimName = context.getCache().getDataDefinitionsCache().getDimensionName(entityId2);
                        if (this.unitDimension == null) {
                            if (this.runtimeController != null) {
                                PeriodEngineService periodEngineService = BeanUtil.getBean(PeriodEngineService.class);
                                if (!periodEngineService.getPeriodAdapter().isPeriodEntity(entityId2)) {
                                    this.unitDimension = dimName;
                                }
                            } else {
                                this.unitDimension = dimName;
                            }
                        }
                        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                        entityViewDefine.setEntityId(entityId2);
                        entityViewDefine.setRowFilterExpression(filterExpression);
                        entityViewDefine.setFilterRowByAuthority(true);
                        this.entityViewDefines.put(dimName, (EntityViewDefine)entityViewDefine);
                    }
                }
                if (!StringUtils.isEmpty((String)(entityId = (dsContext = DsContextHolder.getDsContext()).getContextEntityId()))) {
                    String dimName = context.getCache().getDataDefinitionsCache().getDimensionName(entityId);
                    RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                    entityViewDefine.setEntityId(entityId);
                    entityViewDefine.setRowFilterExpression(filterExpression);
                    entityViewDefine.setFilterRowByAuthority(true);
                    this.entityViewDefines.put(dimName, (EntityViewDefine)entityViewDefine);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else if (StringUtils.isNotEmpty((String)this.dataScehmeKey)) {
            List dimensions = this.dataSchemeService.getDataSchemeDimension(this.dataScehmeKey);
            dimensions.stream().forEach(d -> {
                try {
                    String dimName = context.getCache().getDataDefinitionsCache().getDimensionName(d.getDimKey());
                    if (d.getDimensionType() == DimensionType.UNIT) {
                        this.unitDimension = dimName;
                    }
                    RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                    entityViewDefine.setEntityId(d.getDimKey());
                    entityViewDefine.setFilterRowByAuthority(true);
                    this.entityViewDefines.put(dimName, (EntityViewDefine)entityViewDefine);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        if (StringUtils.isNotEmpty((String)context.getCurrentEntityId())) {
            try {
                String dimensionName = context.getCache().getDataDefinitionsCache().getDimensionName(context.getCurrentEntityId());
                if (StringUtils.isEmpty((String)this.unitDimension)) {
                    this.unitDimension = dimensionName;
                }
                RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                entityViewDefine.setEntityId(context.getCurrentEntityId());
                entityViewDefine.setFilterRowByAuthority(true);
                this.entityViewDefines.put(dimensionName, (EntityViewDefine)entityViewDefine);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public IContext getExternalContext() {
        if (this.reportFunContext == null) {
            this.reportFunContext = new ReportFunContext();
        }
        return this.reportFunContext;
    }

    public IRunTimeViewController getController() {
        return this.controller;
    }

    public IDataDefinitionRuntimeController getRuntimeController() {
        return this.runtimeController;
    }

    public IEntityViewRunTimeController getEntityViewRunTimeController() {
        return this.entityViewRunTimeController;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public List<IReportDynamicNodeProvider> getDataNodeFinders() {
        ArrayList<IReportDynamicNodeProvider> dataNodeFinders = new ArrayList<IReportDynamicNodeProvider>();
        dataNodeFinders.add((IReportDynamicNodeProvider)this.getVariableManager());
        dataNodeFinders.add(new ItemNodeProvider());
        return dataNodeFinders;
    }

    public String getUnitDimesion(ExecutorContext context) {
        if (this.unitDimension == null) {
            this.initEntityViewDefines(context);
        }
        return this.unitDimension;
    }

    public IFieldValueProcessor getFieldValueProcessor() {
        return this.fieldValueProcessor;
    }

    public void setFieldValueProcessor(IFieldValueProcessor fieldValueProcessor) {
        this.fieldValueProcessor = fieldValueProcessor;
    }

    public String getDimensionTableName(String dimensionName) {
        FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        return "SYS_VER_" + formScheme.getFormSchemeCode();
    }

    public boolean JudgeZBAuth() {
        return true;
    }

    public ZBAuthJudger getZBAuthJudger(List<String> fieldKeys) {
        HashMap authMap = new HashMap();
        Optional.ofNullable(fieldKeys).orElse(Collections.emptyList()).forEach(fieldKey -> authMap.put(fieldKey, AuthorityType.MODIFY));
        return new ZBAuthJudger(authMap);
    }

    protected void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String findEntityTableCode(String code) {
        if (code.equalsIgnoreCase("BBLX") && this.dataModelService != null) {
            try {
                String bblxCode = BD_PREFIX + "BBLX" + (StringUtils.isEmpty((String)this.filePrefix) ? "" : "_" + this.filePrefix);
                TableModelDefine defineByCode = this.dataModelService.getTableModelDefineByCode(bblxCode);
                if (defineByCode != null) {
                    return bblxCode;
                }
            }
            catch (Exception e) {
                logger.debug("\u67e5\u8be2\u62a5\u8868\u7c7b\u578b\u5973\u5a32\u5b9a\u4e49\u51fa\u9519", e);
            }
        }
        return BD_PREFIX + (StringUtils.isEmpty((String)this.filePrefix) ? "" : this.filePrefix + "_") + code;
    }

    public IFieldValueUpdateProcessor getFieldValueUpdateProcessor() {
        return this.fieldValueUpdateProcessor;
    }

    public void setFieldValueUpdateProcessor(IFieldValueUpdateProcessor fieldValueUpdateProcessor) {
        this.fieldValueUpdateProcessor = fieldValueUpdateProcessor;
    }

    public void setDimProvider(ExecEnvDimProvider dimProvider) {
        this.dimProvider = dimProvider;
    }

    public Map<String, List<String>> getRelationDimValues(ExecutorContext context, String mainDimValue, String period) {
        List dataSchemeDimension;
        List<String> dimNames;
        if (this.dimProvider == null || this.getTaskDefine() == null && StringUtils.isEmpty((String)this.dataScehmeKey)) {
            return null;
        }
        String dataScheme = this.dataScehmeKey;
        if (this.getTaskDefine() != null) {
            dataScheme = this.getTaskDefine().getDataScheme();
        }
        if (CollectionUtils.isEmpty(dimNames = (dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataScheme)).stream().filter(d -> StringUtils.isNotEmpty((String)d.getDimAttribute())).map(d -> this.entityMetaService.getDimensionName(d.getDimKey())).filter(Objects::nonNull).collect(Collectors.toList()))) {
            return null;
        }
        return this.dimProvider.getRelationDimValues(dataScheme, mainDimValue, period, dimNames);
    }

    public List<String> getRelationValuesByDim(ExecutorContext context, String dimension, String mainDimValue, String period) {
        return this.getRelationValuesByDim(context, dimension, mainDimValue, period, null);
    }

    public List<String> getRelationValuesByDim(ExecutorContext context, String dimension, String mainDimValue, String period, String linkAlias) {
        ArrayList<String> mainDimValues = new ArrayList<String>();
        mainDimValues.add(mainDimValue);
        Map<String, List<String>> relationValuesByDim = this.getAllRelationValuesByDim(context, dimension, mainDimValues, period, linkAlias);
        return relationValuesByDim == null ? Collections.emptyList() : relationValuesByDim.get(mainDimValue);
    }

    public Map<String, List<String>> getAllRelationValuesByDim(ExecutorContext context, String dimension, List<String> mainDimValues, String period, String linkAlias) {
        RelationDimCache relationDimCache;
        RelationDimPeriodCache relationDimPeriodCache;
        String orgEntityId = context.getOrgEntityId();
        String dataScheme = this.dataScehmeKey;
        if (StringUtils.isNotEmpty((String)this.formSchemeKey)) {
            if (StringUtils.isNotEmpty((String)linkAlias)) {
                TaskLinkDefine taskLinkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
                orgEntityId = taskLinkDefine.getRelatedEntity(orgEntityId).getSourceEntity();
                FormSchemeDefine formScheme = this.controller.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
                dataScheme = this.controller.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
            } else {
                try {
                    TaskDefine taskDefine = this.getTaskDefine();
                    if (null != taskDefine) {
                        String dimensionName;
                        EntityViewDefine entityViewDefine;
                        dataScheme = taskDefine.getDataScheme();
                        if (StringUtils.isEmpty((String)orgEntityId) && (entityViewDefine = this.getEntityViewDefine(context, dimensionName = context.getCache().getDataDefinitionsCache().getDimensionName(taskDefine.getDw()))) != null) {
                            orgEntityId = entityViewDefine.getEntityId();
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        if ((relationDimPeriodCache = this.relationDimSchemeCache.computeIfAbsent(dataScheme + orgEntityId + dimension, k -> new RelationDimPeriodCache())).hasCache(period)) {
            relationDimCache = relationDimPeriodCache.get(period);
        } else {
            relationDimCache = new RelationDimCache();
            relationDimPeriodCache.put(period, relationDimCache);
        }
        HashMap<String, List<String>> returmMap = new HashMap<String, List<String>>();
        ArrayList<String> newMainDimValue = new ArrayList<String>();
        for (String mainDimValue : mainDimValues) {
            if (relationDimCache.hasCache(mainDimValue)) {
                returmMap.put(mainDimValue, relationDimCache.getDims(mainDimValue));
                continue;
            }
            newMainDimValue.add(mainDimValue);
        }
        if (newMainDimValue.size() == 0) {
            return returmMap;
        }
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataScheme);
        Optional<DataDimDTO> optionalDataDimDTO = dataSchemeDimension.stream().filter(d -> StringUtils.isNotEmpty((String)d.getDimAttribute())).filter(d -> {
            String dimensionName = this.entityMetaService.getDimensionName(d.getDimKey());
            return dimensionName == null ? false : dimensionName.equalsIgnoreCase(dimension);
        }).findFirst();
        if (!optionalDataDimDTO.isPresent()) {
            if (dimension.equalsIgnoreCase("ADJUST")) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("0");
                HashMap<String, List<String>> relationDimValues = new HashMap<String, List<String>>();
                for (String mainDimValue : mainDimValues) {
                    relationDimValues.put(mainDimValue, list);
                }
                relationDimCache.fixCache(relationDimValues);
                returmMap.putAll(relationDimValues);
                return returmMap;
            }
            return null;
        }
        DataDimDTO dwDimDTO = DataDimDTO.valueOf((DataDimension)((DataDimension)dataSchemeDimension.stream().filter(d -> d.getDimensionType() == DimensionType.UNIT).findFirst().get()));
        if (StringUtils.isNotEmpty((String)orgEntityId)) {
            dwDimDTO.setDimKey(orgEntityId);
        }
        DataDimDTO periodDimDTO = dataSchemeDimension.stream().filter(d -> d.getDimensionType() == DimensionType.PERIOD).findFirst().get();
        EnvDimParam dimParam = new EnvDimParam();
        dimParam.setDw(newMainDimValue);
        dimParam.setDwDataDimDTO(dwDimDTO);
        dimParam.setPeriodDimDTO(periodDimDTO);
        dimParam.setPeriod(period);
        dimParam.setDataDimDTO(optionalDataDimDTO.get());
        Map<String, List<String>> relationDimValues = this.envDimProvider.getRelationDimValues(dimParam);
        relationDimCache.fixCache(relationDimValues);
        returmMap.putAll(relationDimValues);
        return returmMap;
    }

    public boolean is1v1RelationDim(ExecutorContext context, String dimension, String linkAlias) {
        List dataSchemeDimension;
        Optional<DataDimDTO> optionalDataDimDTO;
        if (this.isBatchSummary == null) {
            this.isBatchSummary = this.variableManager.getAllVars().stream().anyMatch(v -> v.getVarName().equalsIgnoreCase("BATCHGATHERSCHEMECODE"));
        }
        if (this.isBatchSummary.booleanValue() || !this.singleDimUtil.getCrossTaskSingleDim()) {
            return false;
        }
        String orgEntityId = this.getCurrentOrgEntityID(context);
        String dataScheme = this.getDataScehmeKey();
        if (StringUtils.isNotEmpty((String)linkAlias) && StringUtils.isNotEmpty((String)this.formSchemeKey)) {
            TaskLinkDefine taskLinkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
            orgEntityId = taskLinkDefine.getRelatedEntity(orgEntityId).getSourceEntity();
            FormSchemeDefine formScheme = this.controller.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
            dataScheme = this.controller.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        }
        if (!(optionalDataDimDTO = (dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataScheme)).stream().filter(d -> StringUtils.isNotEmpty((String)d.getDimAttribute())).filter(d -> {
            String dimensionName = this.entityMetaService.getDimensionName(d.getDimKey());
            return dimensionName == null ? false : dimensionName.equalsIgnoreCase(dimension);
        }).findFirst()).isPresent()) {
            return false;
        }
        String dimAttribute = optionalDataDimDTO.get().getDimAttribute();
        if (orgEntityId == null) {
            return false;
        }
        return !this.entityMetaService.getEntityModel(orgEntityId).getAttribute(dimAttribute).isMultival();
    }

    private String getCurrentOrgEntityID(ExecutorContext context) {
        String orgEntityId = context.getOrgEntityId();
        if (StringUtils.isEmpty((String)orgEntityId) && StringUtils.isNotEmpty((String)this.formSchemeKey)) {
            try {
                String dimensionName;
                EntityViewDefine entityViewDefine;
                FormSchemeDefine formScheme = this.getFormSchemeDefine();
                if (null != formScheme && (entityViewDefine = this.getEntityViewDefine(context, dimensionName = context.getCache().getDataDefinitionsCache().getDimensionName(formScheme.getDw()))) != null) {
                    orgEntityId = entityViewDefine.getEntityId();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return orgEntityId;
    }

    public IUnitLeafFinder getUnitLeafFinder() {
        TaskDefine taskDefine = this.getTaskDefine();
        if (taskDefine == null || taskDefine.getTaskGatherType() == TaskGatherType.TASK_GATHER_MANUAL) {
            return null;
        }
        EntityViewDefine entityViewDefine = null;
        if (this.entityViewDefines != null) {
            FormSchemeDefine formScheme = this.getFormSchemeDefine();
            String dimensionName = this.entityMetaService.getDimensionName(formScheme.getDw());
            entityViewDefine = this.entityViewDefines.get(dimensionName);
        }
        if (entityViewDefine == null) {
            entityViewDefine = this.controller.getViewByFormSchemeKey(this.formSchemeKey);
        }
        return new UnitLeafFinder(entityViewDefine, this.entityDataService);
    }

    class RelationDimCache {
        Map<String, List<String>> map = new HashMap<String, List<String>>();

        RelationDimCache() {
        }

        boolean hasCache(String mainDimValue) {
            return this.map.containsKey(mainDimValue);
        }

        List<String> getDims(String mainDimValue) {
            if (this.hasCache(mainDimValue)) {
                return new ArrayList<String>((Collection)this.map.get(mainDimValue));
            }
            return new ArrayList<String>();
        }

        void fixCache(Map<String, List<String>> values) {
            values.entrySet().stream().forEach(e -> {
                List cfr_ignored_0 = this.map.put((String)e.getKey(), e.getValue() == null ? new ArrayList() : new ArrayList((Collection)e.getValue()));
            });
        }
    }

    class RelationDimPeriodCache {
        Map<String, RelationDimCache> map = new HashMap<String, RelationDimCache>();

        RelationDimPeriodCache() {
        }

        boolean hasCache(String period) {
            return this.map.containsKey(period);
        }

        RelationDimCache get(String period) {
            return this.map.get(period);
        }

        void put(String period, RelationDimCache relationDimCache) {
            this.map.put(period, relationDimCache);
        }
    }
}

