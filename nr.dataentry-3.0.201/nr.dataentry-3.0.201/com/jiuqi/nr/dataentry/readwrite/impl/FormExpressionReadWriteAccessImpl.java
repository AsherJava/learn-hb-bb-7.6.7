/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableContextService
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.internal.service.BatchConditionMonitor;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchReadWriteCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class FormExpressionReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(FormExpressionReadWriteAccessImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IJtableContextService jtableContextService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;

    @Override
    public String getName() {
        return "formExpression";
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == readWriteAccessCacheParams.getFormAccessLevel()) {
            FormSchemeDefine formScheme = null;
            try {
                formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey().toString()});
            }
            FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
            List<String> formKeys = readWriteAccessCacheParams.getFormKeys();
            Map<String, String> conditions = this.getExpressionForms(formKeys);
            if (conditions.size() <= 0) {
                return batchCache;
            }
            Map batchDimensionSet = jtableContext.getDimensionSet();
            List entityList = this.jtableParamService.getEntityList(formScheme.getKey());
            ArrayList<String> errorDimensionList = new ArrayList<String>();
            JtableContext jtableContextInfo = new JtableContext();
            jtableContextInfo.setDimensionSet(batchDimensionSet);
            jtableContextInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
            jtableContextInfo.setTaskKey(jtableContext.getTaskKey());
            List<Map<String, DimensionValue>> splitDimensionValueList = this.dimensionValueProvider.splitDimensionValueList(jtableContextInfo, errorDimensionList, true);
            HashMap<DimensionCacheKey, HashSet<String>> cacheItems = new HashMap<DimensionCacheKey, HashSet<String>>();
            batchCache.setCanAccess(false);
            batchCache.setCacheMap(cacheItems);
            ArrayList<String> dimKeys = new ArrayList<String>();
            boolean adjustDimension = this.checkAdjustDimension(splitDimensionValueList, entityList, dimKeys);
            for (Map<String, DimensionValue> dimensionValue : splitDimensionValueList) {
                Map<String, DimensionValue> resultMap = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, dimensionValue, entityList);
                DimensionCacheKey cacheKey = new DimensionCacheKey(resultMap);
                HashSet<String> formCache = (HashSet<String>)cacheItems.get(cacheKey);
                if (formCache == null) {
                    formCache = new HashSet<String>();
                    cacheItems.put(cacheKey, formCache);
                }
                for (String formKey : conditions.keySet()) {
                    formCache.add(formKey);
                }
            }
            Map<DimensionCacheKey, Set<String>> conditionResultInitCache = this.getConditionResultInitCache(jtableContext, conditions, entityList);
            boolean hasInit = false;
            for (Map.Entry<DimensionCacheKey, Set<String>> batchConditionResult : conditionResultInitCache.entrySet()) {
                Set formItems;
                DimensionCacheKey cacheKey = ReadWriteAccessCacheManager.getCacheKey(adjustDimension, batchConditionResult.getKey(), entityList);
                if (hasInit) {
                    dimKeys = new ArrayList<String>(batchConditionResult.getKey().getDimensionSet().keySet());
                    hasInit = true;
                }
                if ((formItems = (Set)cacheItems.get(cacheKey)) == null) continue;
                formItems.removeAll((Collection)batchConditionResult.getValue());
            }
            batchCache.setDimKeys(dimKeys);
            return batchCache;
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

    private boolean checkAdjustDimension(List<Map<String, DimensionValue>> splitDimensionValueList, List<EntityViewData> entityList, List<String> dimKeys) {
        if (splitDimensionValueList.size() <= 0) {
            return false;
        }
        boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(splitDimensionValueList.get(0), entityList);
        dimKeys.addAll(splitDimensionValueList.get(0).keySet());
        return adjustDimension;
    }

    private Map<DimensionCacheKey, Set<String>> getConditionResultInitCache(JtableContext jtableContext, Map<String, String> conditions, List<EntityViewData> entityList) {
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        excutorJtableContext.setFormKey("");
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)excutorJtableContext);
        Map<DimensionCacheKey, Set<String>> result = null;
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for (String key : conditions.keySet()) {
            Formula formula = new Formula();
            formula.setId(UUID.randomUUID().toString());
            formula.setFormKey(key);
            formula.setFormula(conditions.get(key));
            formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
            formulas.add(formula);
        }
        try {
            ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(jtableContext);
            FormulaCallBack callback = new FormulaCallBack();
            callback.getFormulas().addAll(formulas);
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
            BatchConditionMonitor monitor = new BatchConditionMonitor(jtableContext.getFormSchemeKey(), jtableContext.getTaskKey());
            monitor.setDimensionValueMap(jtableContext.getDimensionSet());
            runner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
            runner.run((IMonitor)monitor);
            result = monitor.getConditionResultList();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel) {
            FormBatchReadWriteCache batchReadWriteCache = (FormBatchReadWriteCache)cacheObj;
            Map<DimensionCacheKey, HashSet<String>> cacheItems = batchReadWriteCache.getCacheMap();
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, batchReadWriteCache.getDimKeys());
            HashSet<String> formKeys = cacheItems == null ? null : cacheItems.get(simpleKey);
            boolean formReadable = false;
            formReadable = batchReadWriteCache.isCanAccess() ? (formKeys == null || formKeys.size() <= 0 ? true : formKeys.contains(formKey)) : (formKeys == null || formKeys.size() <= 0 ? true : !formKeys.contains(formKey));
            return new ReadWriteAccessDesc(formReadable, formReadable ? "" : "\u62a5\u8868\u6570\u636e\u53ea\u8bfb\u516c\u5f0f\u6210\u7acb\uff0c\u4e0d\u53ef\u7f16\u8f91");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel) {
            return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        boolean formReadable = true;
        try {
            FormDefine formDefine = this.runtimeView.queryFormById(context.getFormKey());
            if (StringUtils.isNotEmpty((String)formDefine.getReadOnlyCondition())) {
                DimensionValueSet dimensionValueSet = this.jtableContextService.getDimensionValueSet(context);
                AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(formDefine.getReadOnlyCondition(), context, dimensionValueSet);
                if (expressionEvaluat != null && expressionEvaluat instanceof BoolData) {
                    try {
                        formReadable = !expressionEvaluat.getAsBool();
                    }
                    catch (DataTypeException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u62a5\u8868\u53ea\u8bfb\u516c\u5f0f\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        String unwriteableDesc = "";
        if (!formReadable) {
            unwriteableDesc = DataEntryUtil.isChinese() ? "\u62a5\u8868\u6570\u636e\u53ea\u8bfb\u516c\u5f0f\u6210\u7acb\uff0c\u4e0d\u53ef\u7f16\u8f91" : "Report data read-only formula is valid,the form is not editable";
        }
        return new ReadWriteAccessDesc(formReadable, unwriteableDesc);
    }
}

