/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
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
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.ExecEnvDimProvider
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
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
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.ExecEnvDimProviderImpl;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.ExecEnvDimProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class FormConditionAccessServiceImpl
implements IDataAccessItemService,
IFormConditionAccessService {
    @Autowired
    protected IRunTimeViewController runtimeView;
    @Autowired
    private IRuntimeFormSchemePeriodService iRuntimeFormSchemePeriodService;
    @Autowired
    protected IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    protected IDataAccessProvider dataAccessProvider;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected DataAccesslUtil dataAccesslUtil;
    @Autowired
    protected DimCollectionBuildUtil collectionBuildUtil;
    @Autowired
    protected IEntityMetaService entityMetaService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccessCode canAccess = new AccessCode(this.name());
    protected final Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6");
    private final AccessCode noAccess = new AccessCode(this.name(), "2");

    @Override
    public String name() {
        return "formCondition";
    }

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    @Override
    public List<String> getCodeList() {
        return Collections.singletonList("2");
    }

    @Override
    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        DimensionCollection collection = this.collectionBuildUtil.buildCollectionByCombination(masterKey);
        IBatchAccess batchWriteable = this.getBatchWriteable(formSchemeKey, collection, Collections.singletonList(formKey));
        if (batchWriteable != null) {
            return batchWriteable.getAccessCode(masterKey, formKey);
        }
        return this.canAccess;
    }

    @Override
    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        DimensionCollection collection = this.collectionBuildUtil.buildCollectionByCombination(masterKey);
        IBatchAccess batchWriteable = this.getBatchWriteable(formSchemeKey, collection, Collections.singletonList(formKey));
        if (batchWriteable != null) {
            return batchWriteable.getAccessCode(masterKey, formKey);
        }
        return this.canAccess;
    }

    @Override
    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        DimensionCollection collection = this.collectionBuildUtil.buildCollectionByCombination(masterKey);
        IBatchAccess batchWriteable = this.getBatchWriteable(formSchemeKey, collection, Collections.singletonList(formKey));
        if (batchWriteable != null) {
            return batchWriteable.getAccessCode(masterKey, formKey);
        }
        return this.canAccess;
    }

    @Override
    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    @Override
    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        return this.getBatchWriteable(formSchemeKey, masterKeys, formKeys);
    }

    @Override
    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        return this.getBatchWriteable(formSchemeKey, masterKeys, formKeys);
    }

    @Override
    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, (String)"masterKeys is must not be null!");
        Assert.isTrue((!CollectionUtils.isEmpty(formKeys) ? 1 : 0) != 0, (String)"formKeys is must not be empty!");
        FormBatchAccessCache formBatchAccessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        HashMap<String, Set<String>> groupMapForms = new HashMap<String, Set<String>>();
        HashMap<String, Integer> formGroupCount = new HashMap<String, Integer>();
        Map<String, String> conditions = this.getFormConditionByForms(groupMapForms, formGroupCount, formKeys);
        if (conditions.isEmpty()) {
            return formBatchAccessCache;
        }
        String formKey = formKeys.stream().findFirst().orElse(null);
        Map<DimensionValueSet, Set<String>> conditionResultInitCache = this.getConditionResultInitCache(formScheme, formKey, masterKeys, conditions);
        if (null != conditionResultInitCache && !conditionResultInitCache.isEmpty()) {
            HashMap<DimensionValueSet, Map<String, String>> mapItem = new HashMap<DimensionValueSet, Map<String, String>>();
            for (Map.Entry<DimensionValueSet, Set<String>> oneList : conditionResultInitCache.entrySet()) {
                HashMap<String, Integer> temp = new HashMap<String, Integer>(formGroupCount);
                DimensionValueSet cacheKey = oneList.getKey();
                Set<String> collect = oneList.getValue();
                if (null == collect || collect.isEmpty()) continue;
                Map cacheItem = mapItem.computeIfAbsent(cacheKey, k -> new HashMap());
                for (String carKey : collect) {
                    Set forms = (Set)groupMapForms.get(carKey);
                    if (forms != null) {
                        cacheItem.put(carKey, "2");
                        for (String f : forms) {
                            Integer count = (Integer)temp.get(f);
                            if (count != null && count > 1) {
                                count = count - 1;
                                temp.put(f, count);
                                continue;
                            }
                            cacheItem.put(f, "2");
                        }
                        continue;
                    }
                    cacheItem.put(carKey, "2");
                }
            }
            formBatchAccessCache.setCacheMap(mapItem);
        }
        return formBatchAccessCache;
    }

    protected Map<DimensionValueSet, Set<String>> getConditionResultInitCache(FormSchemeDefine formSchemeDefine, String formKey, DimensionCollection dimensionCollection, Map<String, String> conditions) {
        List dimCombins = dimensionCollection.getDimensionCombinations();
        Set<DimensionValueSet> dimValueSet = dimCombins.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toSet());
        String entityId = this.dataAccesslUtil.contextEntityId(formSchemeDefine.getDw());
        String dwDimensionName = this.entityMetaService.getDimensionName(entityId);
        Collection<DimensionValueSet> dimensionValueSetList = DimensionValueSetUtil.mergeDimensionValue(dimValueSet, dwDimensionName);
        List expressions = this.formulaRunTimeController.getFormConditionsByFormScheme(formSchemeDefine.getKey());
        if ((expressions == null || expressions.isEmpty()) && conditions.isEmpty()) {
            return null;
        }
        Set<String> formKeyOrGroupKey = conditions.keySet();
        List<Object> collect = new ArrayList();
        if (null != expressions && !expressions.isEmpty()) {
            collect = expressions.stream().filter(e -> formKeyOrGroupKey.contains(e.getFormKey())).collect(Collectors.toList());
        }
        FormulaCallBack callback = new FormulaCallBack();
        if (collect.size() == conditions.size()) {
            callback.getParsedExpressions().addAll(expressions);
        } else {
            ArrayList<Formula> formulas = new ArrayList<Formula>();
            for (String key : conditions.keySet()) {
                Formula formula = new Formula();
                formula.setId(key);
                formula.setFormKey(key);
                formula.setFormula(conditions.get(key));
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formulas.add(formula);
            }
            callback.getFormulas().addAll(formulas);
        }
        IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
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
        ExecutorContext executorContext = this.getExecutorContext(formSchemeDefine.getKey(), formKey, dimensionValueSetList.stream().findFirst().get());
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

    protected ExecutorContext getExecutorContext(String formSchemeKey, String formKey, DimensionValueSet dimensionValueSet) {
        FormDefine form;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (StringUtils.isNotEmpty((String)formKey) && (form = this.runtimeView.queryFormById(formKey)) != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        AccessVariableExectuteFormula variableExectuteFormula = new AccessVariableExectuteFormula(formSchemeKey, formKey, null);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey, (ExectuteFormula)variableExectuteFormula, null);
        ExecEnvDimProviderImpl dimProvider = new ExecEnvDimProviderImpl(DimensionValueSetUtil.buildDimensionCollection(dimensionValueSet, formSchemeKey));
        environment.setDimProvider((ExecEnvDimProvider)dimProvider);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        List variables = DsContextHolder.getDsContext().getVariables();
        if (!CollectionUtils.isEmpty(variables)) {
            VariableManager variableManager = executorContext.getVariableManager();
            for (Variable variable : variables) {
                variableManager.add(variable);
            }
        }
        return executorContext;
    }

    protected Map<String, String> getFormConditionByForms(Map<String, Set<String>> map, Map<String, Integer> formCount, List<String> formKeys) {
        HashMap<String, String> conditions = new HashMap<String, String>();
        List formDefines = this.runtimeView.queryFormsById(formKeys);
        for (FormDefine formDefine : formDefines) {
            List groups;
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formDefine.getFormCondition())) {
                conditions.put(formDefine.getKey(), formDefine.getFormCondition());
            }
            if ((groups = this.runtimeView.getFormGroupsByFormKey(formDefine.getKey())) != null) {
                for (FormGroupDefine group : groups) {
                    if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)group.getCondition())) {
                        conditions.put(group.getKey(), group.getCondition());
                    }
                    Set forms = map.computeIfAbsent(group.getKey(), k -> new HashSet());
                    forms.add(formDefine.getKey());
                }
                formCount.put(formDefine.getKey(), groups.size());
                continue;
            }
            formCount.put(formDefine.getKey(), 0);
        }
        return conditions;
    }

    @Override
    public AccessCode visible(IAccessFormMerge mergeParam) throws AccessException {
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        String period = (String)masterKey.getValue("DATATIME");
        ArrayList<String> period2FormScheme = new ArrayList<String>();
        for (String formSchemeKey : formSchemeKeys) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formSchemeKey, period);
            if (schemePeriodLinkDefine == null) continue;
            period2FormScheme.add(formSchemeKey);
        }
        for (String formSchemeKey : period2FormScheme) {
            Set<IAccessForm> formSet = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey);
            DimensionCollection collection = this.collectionBuildUtil.buildCollectionByCombination(masterKey);
            IBatchAccess batchWriteable = this.getBatchWriteable(formSchemeKey, collection, formSet.stream().map(IAccessForm::getFormKey).collect(Collectors.toList()));
            if (batchWriteable == null) continue;
            for (IAccessForm iAccessForm : formSet) {
                AccessCode accessCode = batchWriteable.getAccessCode(masterKey, iAccessForm.getFormKey());
                if (accessCode == null || !"1".equals(accessCode.getCode())) continue;
                return accessCode;
            }
        }
        return new AccessCode(this.name(), "2");
    }

    @Override
    public AccessCode readable(IAccessFormMerge mergeParam) throws AccessException {
        return this.visible(mergeParam);
    }

    @Override
    public AccessCode writeable(IAccessFormMerge mergeParam) throws AccessException {
        DimensionCombination masterKey = mergeParam.getMasterKey();
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        String period = (String)masterKey.getValue("DATATIME");
        ArrayList<String> period2FormScheme = new ArrayList<String>();
        for (String formSchemeKey : formSchemeKeys) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formSchemeKey, period);
            if (schemePeriodLinkDefine == null) continue;
            period2FormScheme.add(formSchemeKey);
        }
        for (String formSchemeKey : period2FormScheme) {
            Set<IAccessForm> formSet = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey);
            DimensionCollection collection = this.collectionBuildUtil.buildCollectionByCombination(masterKey);
            IBatchAccess batchWriteable = this.getBatchWriteable(formSchemeKey, collection, formSet.stream().map(IAccessForm::getFormKey).collect(Collectors.toList()));
            if (batchWriteable == null) continue;
            for (IAccessForm iAccessForm : formSet) {
                AccessCode accessCode = batchWriteable.getAccessCode(masterKey, iAccessForm.getFormKey());
                if (accessCode == null || !"1".equals(accessCode.getCode())) continue;
                return accessCode;
            }
        }
        return this.canAccess;
    }

    @Override
    public IBatchMergeAccess getBatchVisible(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.getBatchWriteable(mergeParam);
    }

    @Override
    public IBatchMergeAccess getBatchReadable(IBatchAccessFormMerge mergeParam) throws AccessException {
        return this.getBatchWriteable(mergeParam);
    }

    @Override
    public IBatchMergeAccess getBatchWriteable(IBatchAccessFormMerge mergeParam) throws AccessException {
        Set<String> formSchemeKeys = mergeParam.getFormSchemeKeys();
        if (CollectionUtils.isEmpty(formSchemeKeys)) {
            return new CanAccessBatchMergeAccess(this.name());
        }
        DimensionCollection masterKeys = mergeParam.getMasterKeys();
        List dimensionCombinations = masterKeys.getDimensionCombinations();
        HashSet<Object> periods = new HashSet<Object>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            String period = (String)dimensionCombination.getValue("DATATIME");
            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)period)) continue;
            periods.add(period);
        }
        HashMap<String, Set> period2FormScheme = new HashMap<String, Set>();
        HashMap<String, Set> formScheme2Period = new HashMap<String, Set>();
        for (String formSchemeKey : formSchemeKeys) {
            for (String string : periods) {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formSchemeKey, string);
                if (schemePeriodLinkDefine == null) continue;
                period2FormScheme.computeIfAbsent(string, k -> new HashSet()).add(formSchemeKey);
                formScheme2Period.computeIfAbsent(formSchemeKey, k -> new HashSet()).add(string);
            }
        }
        HashMap<String, IBatchAccess> cache = new HashMap<String, IBatchAccess>();
        Set<String> taskKeys = mergeParam.getTaskKeys();
        for (String string : taskKeys) {
            Set<String> formSchemeKeysByTasKey = mergeParam.getFormSchemeKeysByTasKey(string);
            DimensionCollection masterKeysByTaskKey = mergeParam.getMasterKeysByTaskKey(string);
            for (String formSchemeKey : formSchemeKeysByTasKey) {
                Set<IAccessForm> accessFormsByFormSchemeKey;
                Set scheme2period = (Set)formScheme2Period.get(formSchemeKey);
                if (CollectionUtils.isEmpty(scheme2period) || (accessFormsByFormSchemeKey = mergeParam.getAccessFormsByFormSchemeKey(formSchemeKey)).isEmpty()) continue;
                List<String> forms = accessFormsByFormSchemeKey.stream().map(IAccessForm::getFormKey).collect(Collectors.toList());
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(masterKeysByTaskKey);
                Object value = dimensionValueSet.getValue("DATATIME");
                ArrayList<String> period = new ArrayList<String>();
                if (value instanceof String) {
                    period.add((String)value);
                } else if (value instanceof Collection) {
                    period.addAll((List)value);
                }
                ArrayList validatePeriod = new ArrayList(period);
                validatePeriod.retainAll(scheme2period);
                if (validatePeriod.isEmpty()) continue;
                dimensionValueSet.setValue("DATATIME", validatePeriod);
                DimensionCollection dimensionValueSets = this.collectionBuildUtil.buildDimensionCollection(dimensionValueSet, formSchemeKey);
                IBatchAccess batchVisible = this.getBatchWriteable(formSchemeKey, dimensionValueSets, forms);
                cache.put(formSchemeKey, batchVisible);
            }
        }
        HashMap<IAccessFormMerge, AccessCode> codeCache = new HashMap<IAccessFormMerge, AccessCode>();
        List<IAccessFormMerge> list = mergeParam.getAccessFormMerges();
        block5: for (IAccessFormMerge accessFormMerge : list) {
            String period = (String)accessFormMerge.getMasterKey().getValue("DATATIME");
            Set formSchemeSet = (Set)period2FormScheme.get(period);
            Set<String> allFormSchemeKey = accessFormMerge.getFormSchemeKeys();
            HashSet<String> result = new HashSet<String>(allFormSchemeKey);
            if (!CollectionUtils.isEmpty(formSchemeSet)) {
                result.retainAll(formSchemeSet);
            }
            for (String formSchemeKey : result) {
                Set<IAccessForm> forms = accessFormMerge.getAccessFormsByFormSchemeKey(formSchemeKey);
                if (!cache.containsKey(formSchemeKey)) continue;
                IBatchAccess iBatchAccess = (IBatchAccess)cache.get(formSchemeKey);
                for (IAccessForm form : forms) {
                    AccessCode accessCode = iBatchAccess.getAccessCode(accessFormMerge.getMasterKey(), form.getFormKey());
                    if (!"1".equals(accessCode.getCode())) continue;
                    codeCache.put(accessFormMerge, accessCode);
                    continue block5;
                }
            }
            codeCache.put(accessFormMerge, this.noAccess);
        }
        return new BatchMergeAccess(this.name(), codeCache);
    }
}

