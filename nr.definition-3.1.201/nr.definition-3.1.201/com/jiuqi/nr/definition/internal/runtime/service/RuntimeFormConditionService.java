/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.env.ItemNode;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class RuntimeFormConditionService
implements RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeFormConditionService.class);
    private final NedisCache conditionCache;
    private final NedisCache expressionCache;
    private static final String IDX_FORM = "form";
    private static final String FORM_CONDITION = "condition";
    private static final String READONLY_CONDITION = "readonly_condition";
    private static final String EXPRESSIONS = "expressions";
    private static final String RELOAD_FORMS = "reloadforms";
    private static final String RELOAD_FIELDS = "reloadfields";
    @Autowired
    private IRuntimeFormGroupService formGroupService;
    @Autowired
    private IRuntimeFormService formService;
    @Autowired
    private DataFieldDeployInfoService dataFieldDeployInfoService;

    private String getCacheName(String groupName) {
        return IDX_FORM.concat("::").concat(groupName);
    }

    public RuntimeFormConditionService(NedisCacheProvider cacheProvider) {
        this.conditionCache = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION").getCache(this.getCacheName(FORM_CONDITION));
        this.expressionCache = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION").getCache(this.getCacheName(READONLY_CONDITION));
    }

    public void onClearCache() {
        this.conditionCache.clear();
        this.expressionCache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        this.conditionCache.clear();
        this.expressionCache.clear();
    }

    public List<IParsedExpression> getFormConditionsByFormScheme(String formSchemeKey, ExecutorContext executorContext) {
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList<IParsedExpression>();
        if (formSchemeKey == null) {
            return parsedExpressions;
        }
        String cacheKey = this.getCacheKey(formSchemeKey, EXPRESSIONS);
        Cache.ValueWrapper valueWrapper = this.conditionCache.get(cacheKey);
        if (valueWrapper != null) {
            return (List)valueWrapper.get();
        }
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.conditionCache.get(cacheKey);
            if (revalueWrapper != null) {
                return (List)revalueWrapper.get();
            }
            ConditionCacheItem cacheItem = this.getFormConditionExpressions(formSchemeKey, executorContext);
            return cacheItem.expressions;
        });
    }

    public List<IParsedExpression> getFormReadOnlyConditionsByFormScheme(String formSchemeKey, ExecutorContext executorContext) {
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList<IParsedExpression>();
        if (formSchemeKey == null) {
            return parsedExpressions;
        }
        Cache.ValueWrapper valueWrapper = this.expressionCache.get(formSchemeKey);
        if (valueWrapper != null) {
            return (List)valueWrapper.get();
        }
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            List<IParsedExpression> expressions;
            Cache.ValueWrapper revalueWrapper = this.expressionCache.get(formSchemeKey);
            if (revalueWrapper != null) {
                return (List)revalueWrapper.get();
            }
            try {
                expressions = this.getFormReadOnlyConditionExpressions(formSchemeKey, executorContext);
            }
            catch (ParseException e) {
                logger.error("\u62a5\u8868\u65b9\u6848\uff1a".concat(formSchemeKey).concat("\u53ea\u8bfb\u6761\u4ef6\u89e3\u6790\u51fa\u9519"), e);
                expressions = new ArrayList<IParsedExpression>();
            }
            this.expressionCache.put(formSchemeKey, expressions);
            return expressions;
        });
    }

    private List<IParsedExpression> getFormReadOnlyConditionExpressions(String formSchemeKey, ExecutorContext executorContext) throws ParseException {
        ArrayList<IParsedExpression> expressions = new ArrayList();
        Map<String, String> conditions = this.getFormReadOnlyConditions(formSchemeKey);
        List<Formula> formulas = this.getFormulas(conditions);
        FmlEngineBaseMonitor fmlEngineBaseMonitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        fmlEngineBaseMonitor.start();
        expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK, (IMonitor)fmlEngineBaseMonitor);
        fmlEngineBaseMonitor.finish();
        return expressions;
    }

    private Map<String, String> getFormReadOnlyConditions(String formSchemeKey) {
        HashMap<String, String> conditions = new HashMap<String, String>();
        List<FormGroupDefine> rootFormGroups = this.formGroupService.queryRootGroupsByFormScheme(formSchemeKey);
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                this.getFormReadOnlyConditionsByGroup(formGroupDefine, conditions);
            }
        }
        return conditions;
    }

    private void getFormReadOnlyConditionsByGroup(FormGroupDefine formGroupDefine, Map<String, String> conditions) {
        List<FormDefine> formDefines;
        String formGroupKey = formGroupDefine.getKey();
        List<FormGroupDefine> childGroupDefines = this.formGroupService.getChildFormGroups(formGroupKey, false);
        if (childGroupDefines != null && childGroupDefines.size() > 0) {
            for (FormGroupDefine childGroupDefine : childGroupDefines) {
                this.getFormConditionsByGroup(childGroupDefine, conditions);
            }
        }
        if ((formDefines = this.formService.getFormsInGroup(formGroupKey, false)) != null && formDefines.size() > 0) {
            for (FormDefine formDefine : formDefines) {
                if (!StringUtils.isNotEmpty((String)formDefine.getReadOnlyCondition())) continue;
                conditions.put(formDefine.getKey(), formDefine.getReadOnlyCondition());
            }
        }
    }

    private ConditionCacheItem getFormConditionExpressions(String formSchemeKey, ExecutorContext executorContext) {
        ConditionCacheItem cacheItem = new ConditionCacheItem();
        try {
            Map<String, String> conditions = this.getFormConditions(formSchemeKey);
            List<Formula> formulas = this.getFormulas(conditions);
            FmlEngineBaseMonitor fmlEngineBaseMonitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            fmlEngineBaseMonitor.start();
            List expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK, (IMonitor)fmlEngineBaseMonitor);
            fmlEngineBaseMonitor.finish();
            HashSet<String> reloadForms = new HashSet<String>();
            HashSet<String> reloadFields = new HashSet<String>();
            boolean noCache = false;
            for (IParsedExpression iParsedExpression : expressions) {
                List<ItemNode> itemNodes;
                IExpression expression = iParsedExpression.getRealExpression();
                if (!noCache && (itemNodes = this.getItemNodes(expression)).size() > 0) {
                    noCache = true;
                }
                List dataNodes = DataEngineFormulaParser.getDynamicDataNodes((IASTNode)expression);
                for (DynamicDataNode dynamicDataNode : dataNodes) {
                    if (dynamicDataNode.getDataLink() != null && dynamicDataNode.getDataLink().getReportInfo() != null) {
                        reloadForms.add(dynamicDataNode.getDataLink().getReportInfo().getReportKey());
                    }
                    if (dynamicDataNode.getQueryField() == null) continue;
                    DataFieldDeployInfo fieldDeployInfo = this.dataFieldDeployInfoService.getByColumnModelKey(dynamicDataNode.getQueryField().getUID());
                    if (fieldDeployInfo != null) {
                        reloadFields.add(fieldDeployInfo.getDataFieldKey());
                        continue;
                    }
                    reloadFields.add(dynamicDataNode.getQueryField().getUID());
                }
            }
            if (!noCache) {
                cacheItem.getExpressions().addAll(expressions);
            }
            cacheItem.getReloadForms().addAll(reloadForms);
            cacheItem.getReloadFields().addAll(reloadFields);
            this.setToCache(cacheItem, formSchemeKey);
            return cacheItem;
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\uff1a".concat(formSchemeKey).concat("\u9002\u5e94\u6027\u6761\u4ef6\u89e3\u6790\u51fa\u9519"), e);
            this.setToCache(cacheItem, formSchemeKey);
            return cacheItem;
        }
    }

    private List<ItemNode> getItemNodes(IExpression astNode) {
        ArrayList<ItemNode> itemNodes = new ArrayList<ItemNode>();
        this.collectDataNodes((IASTNode)astNode, itemNodes);
        return itemNodes;
    }

    private void collectDataNodes(IASTNode astNode, List<ItemNode> itemNodes) {
        for (int i = 0; i < astNode.childrenSize(); ++i) {
            IASTNode childNode = astNode.getChild(i);
            if (childNode instanceof ItemNode) {
                ItemNode fmlNode = (ItemNode)childNode;
                itemNodes.add(fmlNode);
                continue;
            }
            if (childNode.childrenSize() <= 0) continue;
            this.collectDataNodes(childNode, itemNodes);
        }
    }

    private void setToCache(ConditionCacheItem cacheItem, String formSchemeKey) {
        String expressionKey = this.getCacheKey(formSchemeKey, EXPRESSIONS);
        this.conditionCache.put(expressionKey, cacheItem.getExpressions());
        String formKey = this.getCacheKey(formSchemeKey, RELOAD_FORMS);
        this.conditionCache.put(formKey, cacheItem.getReloadForms());
        String fieldKey = this.getCacheKey(formSchemeKey, RELOAD_FIELDS);
        this.conditionCache.put(fieldKey, cacheItem.getReloadFields());
    }

    private List<Formula> getFormulas(Map<String, String> conditions) {
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for (String key : conditions.keySet()) {
            Formula formula = new Formula();
            formula.setId(key);
            formula.setFormKey(key);
            formula.setFormula(conditions.get(key));
            formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
            formulas.add(formula);
        }
        return formulas;
    }

    private Map<String, String> getFormConditions(String formSchemeKey) {
        HashMap<String, String> conditions = new HashMap<String, String>();
        List<FormGroupDefine> rootFormGroups = this.formGroupService.queryRootGroupsByFormScheme(formSchemeKey);
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                this.getFormConditionsByGroup(formGroupDefine, conditions);
            }
        }
        return conditions;
    }

    private void getFormConditionsByGroup(FormGroupDefine formGroupDefine, Map<String, String> conditions) {
        List<FormDefine> formDefines;
        List<FormGroupDefine> childGroupDefines;
        String formGroupKey = formGroupDefine.getKey();
        if (StringUtils.isNotEmpty((String)formGroupDefine.getCondition())) {
            conditions.put(formGroupKey, formGroupDefine.getCondition());
        }
        if ((childGroupDefines = this.formGroupService.getChildFormGroups(formGroupKey, false)) != null && childGroupDefines.size() > 0) {
            for (FormGroupDefine childGroupDefine : childGroupDefines) {
                this.getFormConditionsByGroup(childGroupDefine, conditions);
            }
        }
        if ((formDefines = this.formService.getFormsInGroup(formGroupKey, false)) != null && formDefines.size() > 0) {
            for (FormDefine formDefine : formDefines) {
                if (!StringUtils.isNotEmpty((String)formDefine.getFormCondition())) continue;
                conditions.put(formDefine.getKey(), formDefine.getFormCondition());
            }
        }
    }

    private String getCacheKey(String formSchemeKey, String name) {
        return formSchemeKey.concat("::").concat(name);
    }

    public HashSet<String> getReloadFormsByFormScheme(String formSchemeKey, ExecutorContext executorContext) {
        HashSet<String> reloadForms = new HashSet<String>();
        if (formSchemeKey == null) {
            return reloadForms;
        }
        String cacheKey = this.getCacheKey(formSchemeKey, RELOAD_FORMS);
        Cache.ValueWrapper valueWrapper = this.conditionCache.get(cacheKey);
        if (valueWrapper != null) {
            return (HashSet)valueWrapper.get();
        }
        return (HashSet)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.conditionCache.get(cacheKey);
            if (revalueWrapper != null) {
                return (HashSet)revalueWrapper.get();
            }
            ConditionCacheItem cacheItem = this.getFormConditionExpressions(formSchemeKey, executorContext);
            return cacheItem.getReloadForms();
        });
    }

    public HashSet<String> getConditionFieldsByFormScheme(String formSchemeKey, ExecutorContext executorContext) {
        HashSet<String> reloadFields = new HashSet<String>();
        if (formSchemeKey == null) {
            return reloadFields;
        }
        String cacheKey = this.getCacheKey(formSchemeKey, RELOAD_FIELDS);
        Cache.ValueWrapper valueWrapper = this.conditionCache.get(cacheKey);
        if (valueWrapper != null) {
            return (HashSet)valueWrapper.get();
        }
        return (HashSet)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.conditionCache.get(cacheKey);
            if (revalueWrapper != null) {
                return (HashSet)revalueWrapper.get();
            }
            ConditionCacheItem cacheItem = this.getFormConditionExpressions(formSchemeKey, executorContext);
            return cacheItem.getReloadFields();
        });
    }

    private class ConditionCacheItem {
        private final List<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        private final HashSet<String> reloadForms = new HashSet();
        private final HashSet<String> reloadFields = new HashSet();

        public List<IParsedExpression> getExpressions() {
            return this.expressions;
        }

        public HashSet<String> getReloadForms() {
            return this.reloadForms;
        }

        public HashSet<String> getReloadFields() {
            return this.reloadFields;
        }
    }
}

