/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.ExecEnvDimProviderImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.ExecEnvDimProvider
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.extend.AccessVariableExectuteFormula;
import com.jiuqi.nr.data.access.extend.param.BatchConditionMonitor;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchMergeAccess;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.ExecEnvDimProviderImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.ExecEnvDimProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FormExpressionAccessServiceImpl
implements IDataAccessItemService {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    protected IEntityMetaService entityMetaService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u62a5\u8868\u6570\u636e\u53ea\u8bfb\u516c\u5f0f\u6210\u7acb\uff0c\u4e0d\u53ef\u7f16\u8f91");
    private final AccessCode canAccess = new AccessCode(this.name());
    private final CanAccessBatchMergeAccess canAccessBatchMergeAccess = new CanAccessBatchMergeAccess(this.name());

    @Override
    public String name() {
        return "formExpression";
    }

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    @Override
    public List<String> getCodeList() {
        return Arrays.asList("2");
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        return this.canAccess;
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        AbstractData expressionEvaluat;
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        AccessCode accessCode = new AccessCode(this.name());
        FormDefine formDefine = this.runtimeView.queryFormById(formKey);
        if (StringUtils.isNotEmpty((String)formDefine.getReadOnlyCondition()) && (expressionEvaluat = this.expressionEvaluat(formDefine.getReadOnlyCondition(), formSchemeKey, formKey, masterKey)) != null && expressionEvaluat instanceof BoolData) {
            try {
                boolean formReadable = expressionEvaluat.getAsBool();
                if (formReadable) {
                    accessCode = new AccessCode(this.name(), "2");
                    return accessCode;
                }
            }
            catch (DataTypeException e) {
                this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return accessCode;
    }

    @Override
    public AccessCode sysWriteable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        return this.canAccess;
    }

    @Override
    public IBatchAccess getSysBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKey is must not be null!");
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKey is must not be null!");
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKey is must not be null!");
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKey is must not be null!");
        FormBatchAccessCache formBatchAccessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        Map<DimensionValueSet, Map<String, String>> cacheMap = formBatchAccessCache.getCacheMap();
        Map<String, String> conditions = this.getExpressionForms(formKeys);
        if (conditions.size() <= 0) {
            return formBatchAccessCache;
        }
        ArrayList<String> noAccessformKesys = new ArrayList<String>(conditions.keySet());
        List dimCombins = masterKeys.getDimensionCombinations();
        List dimValueSetList = dimCombins.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        for (DimensionValueSet dimensionValueSet : dimValueSetList) {
            HashMap accessMap = new HashMap();
            noAccessformKesys.forEach(e -> accessMap.put(e, "2"));
            cacheMap.put(dimensionValueSet, accessMap);
        }
        Map<DimensionValueSet, Set<String>> conditionResultInitCache = this.getConditionResultInitCache(formSchemeKey, formKeys, masterKeys, conditions);
        if (!CollectionUtils.isEmpty(conditionResultInitCache)) {
            for (Map.Entry<DimensionValueSet, Set<String>> batchConditionResult : conditionResultInitCache.entrySet()) {
                DimensionValueSet cacheKey = batchConditionResult.getKey();
                Set<String> accessFormKeys = batchConditionResult.getValue();
                Map<String, String> accessMap = cacheMap.get(cacheKey);
                if (accessMap == null) continue;
                accessFormKeys.forEach(e -> accessMap.put((String)e, "1"));
                cacheMap.put(cacheKey, accessMap);
            }
        }
        return formBatchAccessCache;
    }

    private AbstractData expressionEvaluat(String condition, String formSchemeKey, String formKey, DimensionCombination dimensionValueSet) {
        DimensionValueSet masterKey = dimensionValueSet.toDimensionValueSet();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)condition)) {
            IExpressionEvaluator expressionEvaluator = this.dataAccessProvider.newExpressionEvaluator();
            ExecutorContext executorContext = this.getExecutorContext(formSchemeKey, formKey, masterKey);
            try {
                AbstractData result = expressionEvaluator.eval(condition, executorContext, masterKey);
                return result;
            }
            catch (ExpressionException e) {
                this.logger.error("\u516c\u5f0f\u3010" + condition + "\u3011\u6c42\u503c\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    private Map<String, String> getExpressionForms(List<String> formKeys) {
        HashMap<String, String> conditions = new HashMap<String, String>();
        for (String formKey : formKeys) {
            FormDefine formDefine = this.runtimeView.queryFormById(formKey);
            if (formDefine == null || StringUtils.isEmpty((String)formDefine.getReadOnlyCondition())) continue;
            conditions.put(formKey, formDefine.getReadOnlyCondition());
        }
        return conditions;
    }

    private Map<DimensionValueSet, Set<String>> getConditionResultInitCache(String formSchemeKey, List<String> formKeys, DimensionCollection dimensionCollection, Map<String, String> conditions) {
        List dimCombins = dimensionCollection.getDimensionCombinations();
        Set<DimensionValueSet> dimValueSet = dimCombins.stream().map(e -> e.toDimensionValueSet()).collect(Collectors.toSet());
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String dwDimensionName = this.entityMetaService.getDimensionName(entityId);
        Collection<DimensionValueSet> dimensionValueSetList = DimensionValueSetUtil.mergeDimensionValue(dimValueSet, dwDimensionName);
        Object result = null;
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for (String key : conditions.keySet()) {
            Formula formula = new Formula();
            formula.setId(UUID.randomUUID().toString());
            formula.setFormKey(key);
            formula.setFormula(conditions.get(key));
            formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
            formulas.add(formula);
        }
        FormulaCallBack callback = new FormulaCallBack();
        IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
        callback.getFormulas().addAll(formulas);
        BatchConditionMonitor monitor = new BatchConditionMonitor();
        Optional first = dimValueSet.stream().findFirst();
        DimensionSet dimensionSet = ((DimensionValueSet)first.get()).getDimensionSet();
        HashSet<String> names = new HashSet<String>();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String name = dimensionSet.get(i);
            names.add(name);
        }
        monitor.setDimNames(names);
        monitor.setAllDimValues(new ArrayList<DimensionValueSet>(dimValueSet));
        if (dimensionValueSetList.isEmpty()) {
            return monitor.getConditionResultList();
        }
        ExecutorContext executorContext = this.getExecutorContext(formSchemeKey, formKeys.get(0), dimensionValueSetList.stream().findFirst().get());
        executorContext.setOrgEntityId(entityId);
        try {
            runner.prepareCheck(executorContext, dimensionValueSetList.stream().findFirst().get(), (IMonitor)monitor);
            for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
                runner.setMasterKeyValues(dimensionValueSet);
                runner.run((IMonitor)monitor);
            }
        }
        catch (Exception e2) {
            this.logger.error(e2.getMessage(), e2);
            throw new AccessException(e2);
        }
        return monitor.getConditionResultList();
    }

    private ExecutorContext getExecutorContext(String formSchemeKey, String formKey, DimensionValueSet dimensionValueSet) {
        List variables;
        FormDefine form;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (StringUtils.isNotEmpty((String)formKey) && (form = this.runtimeView.queryFormById(formKey)) != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        AccessVariableExectuteFormula variableExectuteFormula = new AccessVariableExectuteFormula(formSchemeKey, formKey, null);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey, (ExectuteFormula)variableExectuteFormula, null);
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionValueSet, formSchemeKey);
        ExecEnvDimProviderImpl dimProvider = new ExecEnvDimProviderImpl(dimensionCollection);
        environment.setDimProvider((ExecEnvDimProvider)dimProvider);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        FormSchemeDefine formSchemeDefine = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        if (formSchemeDefine != null) {
            String dw = formSchemeDefine.getDw();
            executorContext.setOrgEntityId(this.dataAccesslUtil.contextEntityId(dw));
        }
        if (!CollectionUtils.isEmpty(variables = DsContextHolder.getDsContext().getVariables())) {
            VariableManager variableManager = executorContext.getVariableManager();
            for (Variable variable : variables) {
                variableManager.add(variable);
            }
        }
        return executorContext;
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        Set<IAccessForm> accessForms = mergeParam.getAccessForms();
        for (IAccessForm accessForm : accessForms) {
            AbstractData evaluate;
            FormDefine formDefine = this.runtimeView.queryFormById(accessForm.getFormKey());
            if (!StringUtils.isNotEmpty((String)formDefine.getReadOnlyCondition()) || !((evaluate = this.expressionEvaluat(formDefine.getReadOnlyCondition(), accessForm.getFormSchemeKey(), accessForm.getFormKey(), masterKey)) instanceof BoolData)) continue;
            try {
                boolean formReadable = evaluate.getAsBool();
                if (!formReadable) continue;
                return new AccessCode(this.name(), "2");
            }
            catch (DataTypeException e) {
                this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return this.canAccess;
    }

    @Override
    public AccessCode sysWriteable(IAccessFormMerge mergeParam) throws AccessException {
        return this.canAccess;
    }

    @Override
    public IBatchMergeAccess getSysBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.canAccessBatchMergeAccess;
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        Collection<Object> forms;
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        if (CollectionUtils.isEmpty(formSchemeKeys)) {
            return this.canAccessBatchMergeAccess;
        }
        HashMap<String, IBatchAccess> cache = new HashMap<String, IBatchAccess>();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        for (String taskKey : taskKeys) {
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(taskKey);
            Set<String> formSchemeKeysByTasKey = mergeParam.getFormSchemeKeysByTasKey(taskKey);
            for (String formSchemeKey : formSchemeKeysByTasKey) {
                Set<IAccessForm> accessFormsByFormSchemeKey = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey);
                forms = accessFormsByFormSchemeKey.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
                IBatchAccess batchVisible = this.getBatchWriteable(formSchemeKey, masterKeysByTaskKey, (List<String>)forms);
                cache.put(formSchemeKey, batchVisible);
            }
        }
        HashMap<IAccessFormMerge, AccessCode> codeCache = new HashMap<IAccessFormMerge, AccessCode>();
        List<IAccessFormMerge> accessFormMerges = mergeParam.getAccessFormMerges();
        block2: for (IAccessFormMerge accessFormMerge : accessFormMerges) {
            Set<String> formSchemeSet = accessFormMerge.getFormSchemeKeys();
            for (String formSchemeKey : formSchemeSet) {
                forms = accessFormMerge.getAccessFormsByFormSchemeKey(formSchemeKey);
                for (IAccessForm iAccessForm : forms) {
                    IBatchAccess iBatchAccess = (IBatchAccess)cache.get(formSchemeKey);
                    AccessCode accessCode = iBatchAccess.getAccessCode(accessFormMerge.getMasterKey(), iAccessForm.getFormKey());
                    if ("1".equals(accessCode.getCode())) continue;
                    codeCache.put(accessFormMerge, accessCode);
                    continue block2;
                }
            }
            codeCache.put(accessFormMerge, this.canAccess);
        }
        return new BatchMergeAccess(this.name(), codeCache);
    }
}

