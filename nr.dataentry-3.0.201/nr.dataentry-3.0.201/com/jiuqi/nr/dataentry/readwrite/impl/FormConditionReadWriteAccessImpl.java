/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.filter.FormFilter;
import com.jiuqi.nr.dataentry.filter.FormGroupFilter;
import com.jiuqi.nr.dataentry.internal.service.BatchConditionMonitor;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchReadWriteCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=20)
@Component
@Deprecated
public class FormConditionReadWriteAccessImpl
implements IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(FormConditionReadWriteAccessImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;

    @Override
    public String getName() {
        return "formCondition";
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        boolean canSeeForm;
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        List<String> formKeysByUser = readWriteAccessCacheParams.getFormKeys();
        ArrayList<String> formKeysByUserAndGroupKey = new ArrayList<String>();
        formKeysByUserAndGroupKey.addAll(formKeysByUser);
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey().toString()});
        }
        List entityList = this.jtableParamService.getEntityList(formScheme.getKey());
        ArrayList<String> groupKeys = new ArrayList<String>();
        ArrayList<FormGroupData> formGroupList = new ArrayList<FormGroupData>();
        Map<String, String> conditions = this.getFormConditions(formScheme, groupKeys, formGroupList);
        formKeysByUserAndGroupKey.addAll(groupKeys);
        Map<DimensionCacheKey, Set<String>> conditionResultInitCache = null;
        FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
        if (conditions.isEmpty()) {
            return batchCache;
        }
        conditionResultInitCache = this.getConditionResultInitCache(jtableContext, conditions, entityList);
        boolean bl = canSeeForm = formKeysByUserAndGroupKey.size() / 2 < conditions.size();
        if (null != conditionResultInitCache && conditionResultInitCache.size() > 0) {
            HashMap<DimensionCacheKey, HashSet<String>> mapItem = new HashMap<DimensionCacheKey, HashSet<String>>();
            ArrayList<String> dimKeys = new ArrayList<String>();
            boolean adjustDimension = this.checkAdjustDimension(conditionResultInitCache, entityList, dimKeys);
            for (Map.Entry<DimensionCacheKey, Set<String>> oneList : conditionResultInitCache.entrySet()) {
                DimensionCacheKey cacheKey = ReadWriteAccessCacheManager.getCacheKey(adjustDimension, oneList.getKey(), entityList);
                Set<String> collect = oneList.getValue();
                if (null == collect || collect.size() <= 0) continue;
                FormFilter formFilter = new FormFilter();
                FormGroupFilter formGroupFilter = new FormGroupFilter();
                formFilter.setCondition(collect);
                formGroupFilter.setCondition(collect);
                Set<String> canSeeformKeys = this.filterForm(formGroupList, formGroupFilter, formFilter);
                HashSet<String> cacheItem = (HashSet<String>)mapItem.get(cacheKey);
                if (cacheItem == null) {
                    cacheItem = new HashSet<String>();
                    mapItem.put(cacheKey, cacheItem);
                }
                for (String formKeyByUser : formKeysByUserAndGroupKey) {
                    if (canSeeForm) {
                        if (!canSeeformKeys.contains(formKeyByUser)) continue;
                        cacheItem.add(formKeyByUser);
                        continue;
                    }
                    if (canSeeformKeys.contains(formKeyByUser)) continue;
                    cacheItem.add(formKeyByUser);
                }
            }
            batchCache.setDimKeys(dimKeys);
            batchCache.setCacheMap(mapItem);
            batchCache.setCanAccess(canSeeForm);
        }
        return batchCache;
    }

    private boolean checkAdjustDimension(Map<DimensionCacheKey, Set<String>> conditionResultInitCache, List<EntityViewData> entityList, List<String> dimKeys) {
        boolean adjustDimension;
        block0: {
            adjustDimension = false;
            Iterator<Map.Entry<DimensionCacheKey, Set<String>>> iterator = conditionResultInitCache.entrySet().iterator();
            if (!iterator.hasNext()) break block0;
            Map.Entry<DimensionCacheKey, Set<String>> oneList = iterator.next();
            Map<String, DimensionValue> mapItem = oneList.getKey().getDimensionSet();
            adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(mapItem, entityList);
            dimKeys.addAll(mapItem.keySet());
        }
        return adjustDimension;
    }

    private Map<DimensionCacheKey, Set<String>> getConditionResultInitCache(JtableContext jtableContext, Map<String, String> conditions, List<EntityViewData> entityList) {
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        excutorJtableContext.setFormKey("");
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)excutorJtableContext);
        Map<DimensionCacheKey, Set<String>> result = null;
        List expressions = this.formulaRunTimeController.getFormConditionsByFormScheme(jtableContext.getFormSchemeKey());
        if ((expressions == null || expressions.size() <= 0) && conditions.size() <= 0) {
            return result;
        }
        try {
            ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(jtableContext);
            FormulaCallBack callback = new FormulaCallBack();
            if (expressions == null || expressions.size() != conditions.size()) {
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
            } else {
                callback.getParsedExpressions().addAll(expressions);
            }
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
            BatchConditionMonitor monitor = new BatchConditionMonitor(jtableContext.getFormSchemeKey(), jtableContext.getTaskKey());
            monitor.setDimensionValueMap(jtableContext.getDimensionSet());
            runner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
            runner.run((IMonitor)monitor);
            result = monitor.getConditionResultList();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        HashSet<String> formKeys;
        FormBatchReadWriteCache batchReadWriteCache = (FormBatchReadWriteCache)cacheObj;
        Map<DimensionCacheKey, HashSet<String>> cacheItems = batchReadWriteCache.getCacheMap();
        DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, batchReadWriteCache.getDimKeys());
        HashSet<String> hashSet = formKeys = cacheItems == null ? null : cacheItems.get(simpleKey);
        if (formKeys == null) {
            return null;
        }
        boolean hasForm = formKeys.contains(formKey);
        if (batchReadWriteCache.isCanAccess()) {
            return hasForm ? null : new ReadWriteAccessDesc(false, "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6");
        }
        return hasForm ? new ReadWriteAccessDesc(false, "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6") : null;
    }

    private FormGroupData getFormGroupData(FormGroupDefine formGroupDefine, FormSchemeDefine formScheme, Map<String, String> conditions) {
        FormGroupData formGroup = new FormGroupData();
        formGroup.setKey(formGroupDefine.getKey());
        formGroup.setTitle(formGroupDefine.getTitle());
        formGroup.setCode(formGroupDefine.getCode());
        if (StringUtils.isNotEmpty((String)formGroupDefine.getCondition())) {
            conditions.put(formGroupDefine.getKey(), formGroupDefine.getCondition());
        }
        List formGroupDefines = this.runtimeView.getChildFormGroups(formGroupDefine.getKey());
        List<FormGroupData> groups = formGroup.getGroups();
        if (formGroupDefines != null) {
            for (FormGroupDefine childFormGroupDefine : formGroupDefines) {
                FormGroupData formGroupData = this.getFormGroupData(childFormGroupDefine, formScheme, conditions);
                if (formGroupData == null || formGroupData.getGroups().isEmpty() && formGroupData.getReports().isEmpty()) continue;
                groups.add(formGroupData);
            }
        }
        List formDefines = null;
        try {
            formDefines = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupDefine.getKey(), false);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formDefines != null) {
            for (FormDefine formDefine : formDefines) {
                FormData formInfo = new FormData();
                formInfo.init(formDefine);
                formGroup.addForm(formInfo);
                if (!StringUtils.isNotEmpty((String)formDefine.getFormCondition())) continue;
                conditions.put(formDefine.getKey(), formDefine.getFormCondition());
            }
        }
        return formGroup;
    }

    private Set<String> filterForm(List<FormGroupData> formGroupList, FormGroupFilter formGroupFilter, FormFilter formFilter) {
        HashSet<String> formKeys = new HashSet<String>();
        for (FormGroupData group : formGroupList) {
            if (!formGroupFilter.accept(group)) continue;
            formKeys.add(group.getKey());
            Set<String> filterForm = this.filterForm(group, formGroupFilter, formFilter);
            for (String key : filterForm) {
                if (formKeys.contains(key)) continue;
                formKeys.add(key);
            }
        }
        return formKeys;
    }

    private Set<String> filterForm(FormGroupData group, FormGroupFilter formGroupFilter, FormFilter formFilter) {
        HashSet<String> filterFormList = new HashSet<String>();
        for (FormGroupData childGroup : group.getGroups()) {
            if (!formGroupFilter.accept(childGroup)) continue;
            Set<String> filterForm = this.filterForm(childGroup, formGroupFilter, formFilter);
            for (String key : filterForm) {
                if (filterFormList.contains(key)) continue;
                filterFormList.add(key);
            }
        }
        for (FormData form : group.getReports()) {
            if (!formFilter.accept(form) || filterFormList.contains(form.getKey())) continue;
            filterFormList.add(form.getKey());
        }
        return filterFormList;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
    }

    @Override
    public boolean canUseReadTempTable(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            return false;
        }
        ArrayList<String> groupKeys = new ArrayList<String>();
        ArrayList<FormGroupData> formGroupList = new ArrayList<FormGroupData>();
        Map<String, String> conditions = this.getFormConditions(formScheme, groupKeys, formGroupList);
        return conditions.size() <= 0;
    }

    private Map<String, String> getFormConditions(FormSchemeDefine formSchemeDefine, List<String> groupKeys, List<FormGroupData> formGroupList) {
        HashMap<String, String> conditions = new HashMap<String, String>();
        List rootFormGroups = this.runtimeView.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                FormGroupData formGroup = this.getFormGroupData(formGroupDefine, formSchemeDefine, conditions);
                groupKeys.add(formGroup.getKey());
                if (formGroup == null || formGroup.getGroups().isEmpty() && formGroup.getReports().isEmpty()) continue;
                formGroupList.add(formGroup);
            }
        }
        return conditions;
    }
}

