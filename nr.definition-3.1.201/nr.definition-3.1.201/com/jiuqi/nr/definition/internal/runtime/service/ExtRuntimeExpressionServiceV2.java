/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.ExpressionKeyUtil
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.ExpressionKeyUtil;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.common.Consts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.formulatracking.facade.FormulaTrackDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExtExpressionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExtFormulaService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaTrackService;
import com.jiuqi.nr.definition.internal.runtime.parse.BatchExtFormulaParserV2;
import com.jiuqi.nr.definition.internal.runtime.parse.FormulaParserV2;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ExtRuntimeExpressionServiceV2
implements IRuntimeExtExpressionService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    @Autowired
    private BatchExtFormulaParserV2 batchFormulaParserV2;
    @Autowired
    private IRuntimeFormService formService;
    private final IRuntimeFormulaSchemeService formulaSchemeService;
    private final IRuntimeExtFormulaService formulaService;
    private final IDataDefinitionRuntimeController dataDefinitionController;
    private final IRunTimeViewController viewController;
    private final IEntityViewRunTimeController entityViewController;
    private final NedisCache expCache;
    private final NedisCache expFileCache;
    private final NedisCache balanceZBExpCache;
    private final IRuntimeFormulaTrackService formulaTrackService;
    private static final Logger LOGGER = LogFactory.getLogger(ExtRuntimeExpressionServiceV2.class);

    private static String makeExpressionFileName(String formKey, DataEngineConsts.FormulaType type) {
        return formKey.concat("_exp_").concat(type.name()).concat("_ext");
    }

    private static String makeJSFileName(String formKey, DataEngineConsts.FormulaType type) {
        return formKey.concat("_js_").concat(type.name()).concat("_ext");
    }

    private static String createExpressionCacheKey(String formulaSchemeKey) {
        return "exp_".concat(formulaSchemeKey).concat("_ext");
    }

    private static String createCalcDataLinkCacheKey(String formulaSchemeKey) {
        return "calc_datalink_".concat(formulaSchemeKey).concat("_ext");
    }

    private static String createExpFileCacheKey(String formulaSchemeKey) {
        return "file_exp_".concat(formulaSchemeKey).concat("_ext");
    }

    private static String createJsFileCacheKey(String formulaSchemeKey) {
        return "file_js_".concat(formulaSchemeKey).concat("_ext");
    }

    private static String createUnitCacheKey(String formulaSchemeKey) {
        return "unit_".concat(formulaSchemeKey).concat("_all");
    }

    public ExtRuntimeExpressionServiceV2(IRuntimeFormulaSchemeService formulaSchemeService, IRuntimeExtFormulaService formulaService, IDataDefinitionRuntimeController dataDefinitionController, IRunTimeViewController viewController, IEntityViewRunTimeController entityViewController, NedisCacheProvider cacheProvider, IRuntimeFormulaTrackService formulaTrackService) {
        this.formulaSchemeService = formulaSchemeService;
        this.formulaService = formulaService;
        this.dataDefinitionController = dataDefinitionController;
        this.viewController = viewController;
        this.entityViewController = entityViewController;
        NedisCacheManager cacheManger = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION");
        this.expCache = cacheManger.getCache(RuntimeDefinitionCache.createCacheName(IParsedExpression.class));
        this.expFileCache = cacheManger.getCache(RuntimeDefinitionCache.createCacheName(IParsedExpression.class).concat("_f"));
        this.balanceZBExpCache = cacheManger.getCache(RuntimeDefinitionCache.createCacheName(IParsedExpression.class).concat("_balanceZB"));
        this.formulaTrackService = formulaTrackService;
    }

    @Override
    public void refreshExpressionCache(String formulaSchemeKey, String formKey, boolean pubByScheme) throws Exception {
        this.reloadCalDatalinkCache(formulaSchemeKey, formKey);
        if (pubByScheme) {
            FormulaSchemeDefine formulaSchemeDefine = this.formulaSchemeService.queryFormulaScheme(formulaSchemeKey);
            List allFormKeys = this.formService.queryFormDefinesByFormScheme(formulaSchemeDefine.getFormSchemeKey()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            allFormKeys.add("00000000-0000-0000-0000-000000000000");
            for (String formKey1 : allFormKeys) {
                this.reloadCache(formulaSchemeKey, formKey1);
            }
        } else {
            if (formKey == null) {
                formKey = "00000000-0000-0000-0000-000000000000";
            }
            this.reloadCache(formulaSchemeKey, formKey);
        }
    }

    private void reloadCalDatalinkCache(String formulaSchemeKey, String formKey) {
        String calcDataLinkCacheKey = ExtRuntimeExpressionServiceV2.createCalcDataLinkCacheKey(formulaSchemeKey);
        ArrayList<Object> calNeedFormKey = new ArrayList<Object>();
        if (formKey == null) {
            FormulaSchemeDefine formulaSchemeDefine = this.formulaSchemeService.queryFormulaScheme(formulaSchemeKey);
            List list = this.formService.queryFormDefinesByFormScheme(formulaSchemeDefine.getFormSchemeKey()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            calNeedFormKey.addAll(list);
        } else {
            calNeedFormKey.add(formKey);
        }
        for (String string : calNeedFormKey) {
            List<String> dataLinkKeys = this.formulaTrackService.queryDataLinkKeyByFormulaTracks(formulaSchemeKey, string, DataEngineConsts.FormulaType.CALCULATE, Consts.WRITE_ENUM);
            this.expCache.hSet(calcDataLinkCacheKey, (Object)string, dataLinkKeys);
        }
    }

    private void reloadCache(String formulaSchemeKey, String formKey) {
        this.reloadExpExpFileJsFileCache(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE);
        this.reloadExpExpFileJsFileCache(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CHECK);
        if (!formKey.equals("00000000-0000-0000-0000-000000000000")) {
            this.reloadBlanceZBExpression(formulaSchemeKey, formKey);
        }
    }

    private void reloadExpExpFileJsFileCache(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            BatchExtFormulaParserV2.BatchParsedExpressionCollection parsedExpression = this.parseFormula(formulaSchemeKey, formKey, formulaType);
            String expCacheKey = ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey);
            this.expCache.hMSet(expCacheKey, parsedExpression.getExpressionMap(formKey, formulaType));
            List<IParsedExpression> expressions = parsedExpression.getExpressions(formKey, formulaType);
            ExpressionFile expressionFile = new ExpressionFile(formulaSchemeKey, formKey, formulaType, expressions);
            String expFileCacheKey = ExtRuntimeExpressionServiceV2.createExpFileCacheKey(formulaSchemeKey);
            String expFileName = ExtRuntimeExpressionServiceV2.makeExpressionFileName(formKey, formulaType);
            this.expFileCache.hSet(expFileCacheKey, (Object)expFileName, (Object)expressionFile);
            if (formulaType == DataEngineConsts.FormulaType.CALCULATE) {
                String js = parsedExpression.getJsFormula(formKey, DataEngineConsts.FormulaType.CALCULATE);
                JSFile jsFile = new JSFile(formulaSchemeKey, formKey, formulaType, js);
                String jsFileCacheKey = ExtRuntimeExpressionServiceV2.createJsFileCacheKey(formulaSchemeKey);
                String jsFileName = ExtRuntimeExpressionServiceV2.makeJSFileName(formKey, formulaType);
                this.expFileCache.hSet(jsFileCacheKey, (Object)jsFileName, (Object)jsFile);
            }
            return null;
        });
    }

    public void onClearCache() {
        RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            this.expCache.clear();
            this.expFileCache.clear();
            this.balanceZBExpCache.clear();
            return null;
        });
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            for (String formulaSchemeKey : deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys()) {
                this.expCache.evict(ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey));
                this.expCache.evict(ExtRuntimeExpressionServiceV2.createCalcDataLinkCacheKey(formulaSchemeKey));
                this.expFileCache.evict(ExtRuntimeExpressionServiceV2.createExpFileCacheKey(formulaSchemeKey));
                this.expFileCache.evict(ExtRuntimeExpressionServiceV2.createJsFileCacheKey(formulaSchemeKey));
                this.balanceZBExpCache.evict(formulaSchemeKey);
            }
            return null;
        });
    }

    public List<IParsedExpression> getParsedExpressionByUnit(String formulaSchemeKey, String unit) {
        ArrayList<IParsedExpression> data = new ArrayList<IParsedExpression>();
        String unitCacheKey = ExtRuntimeExpressionServiceV2.createUnitCacheKey(formulaSchemeKey);
        Cache.ValueWrapper valueWrapper = this.expCache.hGet(unitCacheKey, (Object)unitCacheKey);
        if (null != valueWrapper) {
            List iParsedExpressionList;
            Map cacheData = (Map)valueWrapper.get();
            if (null != cacheData && null != (iParsedExpressionList = (List)cacheData.get(unit)) && iParsedExpressionList.size() != 0) {
                data.addAll(iParsedExpressionList);
            }
        } else {
            List<IParsedExpression> parsedExpressionByForm = this.getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
            HashMap parseMap = new HashMap();
            for (IParsedExpression iParsedExpression : parsedExpressionByForm) {
                String formulaKey = iParsedExpression.getSource().getId();
                if (!StringUtils.isNotEmpty((String)formulaKey)) continue;
                if (null == parseMap.get(formulaKey)) {
                    parseMap.put(formulaKey, new ArrayList());
                    ((List)parseMap.get(formulaKey)).add(iParsedExpression);
                    continue;
                }
                ((List)parseMap.get(formulaKey)).add(iParsedExpression);
            }
            List<FormulaDefine> formulasInScheme = this.formulaService.getFormulasInScheme(formulaSchemeKey);
            HashMap mapByFormulas = new HashMap();
            for (FormulaDefine formulaDefine : formulasInScheme) {
                if (!StringUtils.isNotEmpty((String)formulaDefine.getUnit())) continue;
                if (null == mapByFormulas.get(formulaDefine.getUnit())) {
                    mapByFormulas.put(formulaDefine.getUnit(), new ArrayList());
                    ((List)mapByFormulas.get(formulaDefine.getUnit())).add(formulaDefine);
                    continue;
                }
                ((List)mapByFormulas.get(formulaDefine.getUnit())).add(formulaDefine);
            }
            HashMap cacheData = new HashMap();
            for (String unitCode : mapByFormulas.keySet()) {
                List formulaDefines = (List)mapByFormulas.get(unitCode);
                ArrayList ipes = new ArrayList();
                for (FormulaDefine formulaDefine : formulaDefines) {
                    List iParsedExpressionList = (List)parseMap.get(formulaDefine.getKey());
                    if (null == iParsedExpressionList || iParsedExpressionList.size() == 0) continue;
                    ipes.addAll(iParsedExpressionList);
                }
                if (null == cacheData.get(unitCode)) {
                    cacheData.put(unitCode, new ArrayList());
                    ((List)cacheData.get(unitCode)).addAll(ipes);
                    continue;
                }
                ((List)cacheData.get(unitCode)).addAll(ipes);
            }
            if (null != cacheData.get(unit) && ((List)cacheData.get(unit)).size() != 0) {
                data.addAll((Collection)cacheData.get(unit));
            }
            this.expCache.hSet(unitCacheKey, (Object)unitCacheKey, cacheData);
        }
        return data;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForm(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        String fileName;
        if (formKey == null) {
            return this.getParsedExpressionByFormulaSchema(formulaSchemeKey, formulaType);
        }
        String cacheKey = ExtRuntimeExpressionServiceV2.createExpFileCacheKey(formulaSchemeKey);
        Cache.ValueWrapper valueWrapper = this.expFileCache.hGet(cacheKey, (Object)(fileName = ExtRuntimeExpressionServiceV2.makeExpressionFileName(formKey, formulaType)));
        ExpressionFile expressionFile = valueWrapper != null ? (ExpressionFile)valueWrapper.get() : this.getExpressionFileByForm(formulaSchemeKey, formKey, formulaType);
        return Collections.unmodifiableList(expressionFile.getExpressions());
    }

    private ExpressionFile getExpressionFileByForm(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        return (ExpressionFile)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            String expFileName;
            String expFileCacheKey = ExtRuntimeExpressionServiceV2.createExpFileCacheKey(formulaSchemeKey);
            Cache.ValueWrapper valueWrapper = this.expFileCache.hGet(expFileCacheKey, (Object)(expFileName = ExtRuntimeExpressionServiceV2.makeExpressionFileName(formKey, formulaType)));
            if (valueWrapper != null) {
                ExpressionFile expressionFileInner = (ExpressionFile)valueWrapper.get();
                return expressionFileInner;
            }
            BatchExtFormulaParserV2.BatchParsedExpressionCollection parsedExpression = this.parseFormula(formulaSchemeKey, formKey, formulaType);
            List<IParsedExpression> expressions = parsedExpression.getExpressions(formKey, formulaType);
            ExpressionFile expressionFileInner = new ExpressionFile(formulaSchemeKey, formKey, formulaType, expressions);
            this.expFileCache.hSet(expFileCacheKey, (Object)expFileName, (Object)expressionFileInner);
            String expCacheKey = ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey);
            this.expCache.hMSet(expCacheKey, parsedExpression.getExpressionMap(formKey, formulaType));
            return expressionFileInner;
        });
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForms(String formulaSchemeKey, List<String> formKeys, DataEngineConsts.FormulaType formulaType) {
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        for (String formKey : formKeys) {
            expressions.addAll(this.getParsedExpressionByForm(formulaSchemeKey, formKey, formulaType));
        }
        return expressions;
    }

    public List<IParsedExpression> getParsedExpressionByFormulaSchema(String formulaSchemeKey, DataEngineConsts.FormulaType type) {
        if (formulaSchemeKey == null) {
            throw new IllegalArgumentException("'formulaSchemeKey' must not be null.");
        }
        FormulaSchemeDefine formulaScheme = this.formulaSchemeService.queryFormulaScheme(formulaSchemeKey);
        if (formulaScheme == null || formulaScheme.getFormSchemeKey() == null) {
            throw new IllegalArgumentException("formula scheme not found or invalid. ".concat(formulaSchemeKey));
        }
        List<FormDefine> forms = this.viewController.queryAllFormDefinesByFormScheme(formulaScheme.getFormSchemeKey());
        List<String> formKeys = forms.stream().map(t -> t.getKey()).collect(Collectors.toList());
        formKeys.add("00000000-0000-0000-0000-000000000000");
        return this.getParsedExpressionByForms(formulaSchemeKey, formKeys, type);
    }

    @Override
    public List<IParsedExpression> getParsedExpressionBetweenTable(String formulaSchemeKey, DataEngineConsts.FormulaType type) {
        return this.getParsedExpressionByForm(formulaSchemeKey, "00000000-0000-0000-0000-000000000000", type);
    }

    @Override
    public String getCalculateJsFormulasInForm(String formulaSchemeKey, String formKey) {
        String fileName;
        if (formKey == null) {
            throw new IllegalArgumentException("'formKey' must not be null.");
        }
        DataEngineConsts.FormulaType formulaType = DataEngineConsts.FormulaType.CALCULATE;
        String cacheKey = ExtRuntimeExpressionServiceV2.createJsFileCacheKey(formulaSchemeKey);
        Cache.ValueWrapper valueWrapper = this.expFileCache.hGet(cacheKey, (Object)(fileName = ExtRuntimeExpressionServiceV2.makeJSFileName(formKey, formulaType)));
        JSFile jsFile = valueWrapper != null ? (JSFile)valueWrapper.get() : this.getJsFileByForm(formulaSchemeKey, formKey, formulaType);
        return jsFile.getScript();
    }

    private JSFile getJsFileByForm(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        return (JSFile)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            String jsFileName;
            String jsFileCacheKey = ExtRuntimeExpressionServiceV2.createJsFileCacheKey(formulaSchemeKey);
            Cache.ValueWrapper valueWrapper = this.expFileCache.hGet(jsFileCacheKey, (Object)(jsFileName = ExtRuntimeExpressionServiceV2.makeJSFileName(formKey, formulaType)));
            if (valueWrapper != null) {
                JSFile jsFileInner = (JSFile)valueWrapper.get();
                return jsFileInner;
            }
            BatchExtFormulaParserV2.BatchParsedExpressionCollection parsedExpression = this.parseFormula(formulaSchemeKey, formKey, formulaType);
            List<IParsedExpression> expressions = parsedExpression.getExpressions(formKey, formulaType);
            ExpressionFile expressionFile = new ExpressionFile(formulaSchemeKey, formKey, formulaType, expressions);
            String expFileCacheKey = ExtRuntimeExpressionServiceV2.createExpFileCacheKey(formulaSchemeKey);
            String expFileName = ExtRuntimeExpressionServiceV2.makeExpressionFileName(formKey, formulaType);
            this.expFileCache.hSet(expFileCacheKey, (Object)expFileName, (Object)expressionFile);
            String expCacheKey = ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey);
            this.expCache.hMSet(expCacheKey, parsedExpression.getExpressionMap(formKey, formulaType));
            String js = parsedExpression.getJsFormula(formKey, DataEngineConsts.FormulaType.CALCULATE);
            JSFile jsFileInner = new JSFile(formulaSchemeKey, formKey, formulaType, js);
            this.expFileCache.hSet(jsFileCacheKey, (Object)jsFileName, (Object)jsFileInner);
            return jsFileInner;
        });
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(String dataLinkCode, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType type) {
        if (formKey == null) {
            return this.getParsedExpressionByDataLink(dataLinkCode, formulaSchemeKey, type);
        }
        List<FormulaTrackDefine> formulaTracks = this.formulaTrackService.queryFormulaTracks(dataLinkCode, formulaSchemeKey, formKey, type);
        List<String> expressionKeys = formulaTracks.stream().map(FormulaTrackDefine::getExpressionKey).collect(Collectors.toList());
        List<IParsedExpression> expressions = this.getParsedExpressions(formulaSchemeKey, expressionKeys);
        return expressions.stream().filter(t -> t != null).collect(Collectors.toList());
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(List<String> linkCodes, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, Integer direction) {
        if (formKey == null) {
            return null;
        }
        List<FormulaTrackDefine> formulaTracks = this.formulaTrackService.queryFormulaTracks(linkCodes, formulaSchemeKey, formKey, formulaType, direction);
        List<String> expressionKeys = formulaTracks.stream().map(FormulaTrackDefine::getExpressionKey).collect(Collectors.toList());
        List<IParsedExpression> expressions = this.getParsedExpressions(formulaSchemeKey, expressionKeys);
        return expressions.stream().filter(t -> t != null).collect(Collectors.toList());
    }

    public List<IParsedExpression> getParsedExpressionByDataLink(String dataLinkCode, String formulaSchemeKey, DataEngineConsts.FormulaType type) {
        List<FormulaTrackDefine> formulaTracks = this.formulaTrackService.queryFormulaTracks(dataLinkCode, formulaSchemeKey, type);
        List<String> expressionKeys = formulaTracks.stream().map(FormulaTrackDefine::getExpressionKey).collect(Collectors.toList());
        List<IParsedExpression> expressions = this.getParsedExpressions(formulaSchemeKey, expressionKeys);
        return expressions.stream().filter(t -> t != null).collect(Collectors.toList());
    }

    public List<IParsedExpression> getParsedExpressions(String formulaSchemeKey, List<String> expressionKeys) {
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>(expressionKeys.size());
        String cacheKey = ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey);
        List valueWrappers = this.expCache.hMGet(cacheKey, expressionKeys);
        HashMap<Integer, String> uncachedExpressionKeys = new HashMap<Integer, String>();
        for (int i = 0; i < expressionKeys.size(); ++i) {
            Cache.ValueWrapper valueWrapper = (Cache.ValueWrapper)valueWrappers.get(i);
            if (valueWrapper != null) {
                expressions.add((IParsedExpression)valueWrapper.get());
                continue;
            }
            expressions.add(null);
            uncachedExpressionKeys.put(i, expressionKeys.get(i));
        }
        if (uncachedExpressionKeys.isEmpty()) {
            return expressions;
        }
        Map<String, IParsedExpression> uncachedExpressions = this.parsedExpressions(formulaSchemeKey, uncachedExpressionKeys.values());
        for (Map.Entry entry : uncachedExpressionKeys.entrySet()) {
            IParsedExpression expression = uncachedExpressions.get(entry.getValue());
            expressions.set((Integer)entry.getKey(), expression);
        }
        return expressions;
    }

    @Override
    public IParsedExpression getParsedExpression(String formulaSchemeKey, String expressionKey) {
        String cacheKey = ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey);
        Cache.ValueWrapper valueWrapper = this.expCache.hGet(cacheKey, (Object)expressionKey);
        if (valueWrapper != null) {
            return (IParsedExpression)valueWrapper.get();
        }
        ArrayList<String> expressionKeys = new ArrayList<String>();
        expressionKeys.add(expressionKey);
        Map<String, IParsedExpression> expressions = this.parsedExpressions(formulaSchemeKey, expressionKeys);
        return expressions.get(expressionKey);
    }

    private Map<String, IParsedExpression> parsedExpressions(String formulaSchemeKey, Collection<String> expressionKeys) {
        HashSet<String> formulaKeySet = new HashSet<String>();
        for (String expressionKey : expressionKeys) {
            formulaKeySet.add(ExpressionKeyUtil.getFormulaKeyFromExpressionKey((String)expressionKey));
        }
        return (Map)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            FormulaParserV2.ParsedExpressionCollectionV2 parsedExpression = this.parseFormula(formulaSchemeKey, new ArrayList<String>(formulaKeySet));
            Map<String, IParsedExpression> expMap = parsedExpression.getExpressionMap();
            for (String expKey : expressionKeys) {
                expMap.putIfAbsent(expKey, null);
            }
            String cacheKey = ExtRuntimeExpressionServiceV2.createExpressionCacheKey(formulaSchemeKey);
            this.expCache.hMSet(cacheKey, expMap);
            return expMap;
        });
    }

    public IParsedExpression getParsedExpression(String formulaSchemeKey, String formKey, String expressionKey) {
        return this.getParsedExpression(formulaSchemeKey, expressionKey);
    }

    @Override
    public Collection<String> getCalcCellDataLinks(String formulaSchemeKey, String formKey) {
        String cacheKey = ExtRuntimeExpressionServiceV2.createCalcDataLinkCacheKey(formulaSchemeKey);
        Cache.ValueWrapper valueWrapper = this.expCache.hGet(cacheKey, (Object)formKey);
        if (valueWrapper != null) {
            return (List)valueWrapper.get();
        }
        return (Collection)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper valueWrapperInner = this.expCache.hGet(cacheKey, (Object)formKey);
            if (valueWrapperInner != null) {
                return (List)valueWrapperInner.get();
            }
            List<String> dataLinkKeys = this.formulaTrackService.queryDataLinkKeyByFormulaTracks(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE, Consts.WRITE_ENUM);
            this.expCache.hSet(cacheKey, (Object)formKey, dataLinkKeys);
            return dataLinkKeys;
        });
    }

    @Override
    public List<CalcItem> getDimensionCalcCells(String formulaSchemeKey, String formKey) {
        ArrayList<CalcItem> calcItems = new ArrayList<CalcItem>();
        List<IParsedExpression> expressions = this.getParsedExpressionByForm(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE);
        if (expressions.isEmpty()) {
            return calcItems;
        }
        HashMap<Integer, String> linkMap = new HashMap<Integer, String>();
        int index = 0;
        for (IParsedExpression iParsedExpression : expressions) {
            DynamicDataNode assignNode = iParsedExpression.getAssignNode();
            QueryField queryField = assignNode == null ? null : assignNode.getQueryField();
            if (queryField == null || queryField.getDimensionRestriction() == null) continue;
            CalcItem calcItem = new CalcItem();
            calcItem.setDimValues(new DimensionValueSet(queryField.getDimensionRestriction()));
            if (assignNode.getDataLink() != null) {
                linkMap.put(index, assignNode.getDataLink().getDataLinkCode());
            } else {
                calcItem.setLinkId(queryField.getUID());
            }
            ++index;
            calcItems.add(calcItem);
        }
        if (!calcItems.isEmpty()) {
            List<DataLinkDefine> dataLinkDefines = this.viewController.getAllLinksInForm(formKey);
            Map<String, String> fieldLinkMap = dataLinkDefines.stream().collect(Collectors.toMap(t -> t.getLinkExpression(), t -> t.getKey(), (oldValue, newValue) -> oldValue));
            Map<String, String> linkCodeMap = dataLinkDefines.stream().collect(Collectors.toMap(t -> t.getUniqueCode(), t -> t.getKey(), (oldValue, newValue) -> oldValue));
            for (index = calcItems.size() - 1; index >= 0; --index) {
                CalcItem calcItem = (CalcItem)calcItems.get(index);
                if (linkMap.containsKey(index)) {
                    String linkCode = (String)linkMap.get(index);
                    if (linkCodeMap.containsKey(linkCode)) {
                        calcItem.setLinkId(linkCodeMap.get(linkCode));
                        continue;
                    }
                    calcItems.remove(index);
                    continue;
                }
                if (fieldLinkMap.containsKey(calcItem.getLinkId())) {
                    String linkId = calcItem.getLinkId();
                    calcItem.setLinkId(fieldLinkMap.get(linkId));
                    continue;
                }
                calcItems.remove(index);
            }
        }
        return calcItems;
    }

    private BatchExtFormulaParserV2.BatchParsedExpressionCollection parseFormula(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
        BatchExtFormulaParserV2.BatchParsedExpressionCollection expressionCollection;
        try {
            expressionCollection = this.batchFormulaParserV2.parseFormulas(formulaSchemeKey, formKey, formulaType);
        }
        catch (ParseException e) {
            LOGGER.error("\u89e3\u6790\u516c\u5f0f\u65b9\u6848\u51fa\u9519\uff0c\u516c\u5f0f\u65b9\u6848Key\uff1a".concat(formulaSchemeKey).concat("\uff0c\u62a5\u8868Key\uff1a").concat(formKey), (Throwable)e);
            throw new RuntimeException("\u89e3\u6790\u516c\u5f0f\u65b9\u6848\u5931\u8d25\u3002", e);
        }
        return expressionCollection;
    }

    private FormulaParserV2.ParsedExpressionCollectionV2 parseFormula(String formulaSchemeKey, List<String> formulaKeys) {
        FormulaParserV2.ParsedExpressionCollectionV2 expressionCollection;
        FormulaParserV2 formulaParser = new FormulaParserV2(this.formulaSchemeService, this.dataDefinitionController, this.viewController, this.entityViewController);
        List<FormulaDefine> formulaDefines = this.formulaService.queryFormulas(formulaKeys);
        try {
            expressionCollection = formulaParser.parseFormulas(formulaSchemeKey, formulaDefines);
        }
        catch (ParseException e) {
            LOGGER.error("\u89e3\u6790\u516c\u5f0f\u65b9\u6848\u51fa\u9519\uff0c\u516c\u5f0f\u65b9\u6848Key\uff1a".concat(formulaSchemeKey).concat("\uff0c\u516c\u5f0fKey\uff1a").concat(formulaKeys.toString()), (Throwable)e);
            throw new RuntimeException("\u89e3\u6790\u516c\u5f0f\u65b9\u6848\u5931\u8d25\u3002", e);
        }
        return expressionCollection;
    }

    @Override
    public Map<String, String> getBalanceZBExpressionByForm(String formulaSchemeKey, String formKey) {
        if (StringUtils.isEmpty((String)formulaSchemeKey) || StringUtils.isEmpty((String)formKey)) {
            return Collections.emptyMap();
        }
        Cache.ValueWrapper valueWrapper = this.balanceZBExpCache.hGet(formulaSchemeKey, (Object)formKey);
        if (valueWrapper == null) {
            return (Map)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                Cache.ValueWrapper valueWrapper2 = this.balanceZBExpCache.hGet(formulaSchemeKey, (Object)formKey);
                if (null == valueWrapper2) {
                    Map<String, String> fieldsAndBalanceFormulas = this.getBalanceZBExpression(formulaSchemeKey, formKey);
                    this.balanceZBExpCache.hSet(formulaSchemeKey, (Object)formKey, fieldsAndBalanceFormulas);
                    return fieldsAndBalanceFormulas;
                }
                return (Map)valueWrapper2.get();
            });
        }
        Map result = (Map)valueWrapper.get();
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyMap();
        }
        return result;
    }

    private void reloadBlanceZBExpression(String formulaSchemeKey, String formKey) {
    }

    private Map<String, String> getBalanceZBExpression(String formulaSchemeKey, String formKey) {
        HashMap<String, String> result = new HashMap<String, String>();
        return result;
    }

    public static class AsyncParseFormulaException
    extends RuntimeException {
        private static final long serialVersionUID = -1305551034961397476L;

        public AsyncParseFormulaException(Throwable e) {
            super("\u5f02\u6b65\u89e3\u6790\u516c\u5f0f\u9519\u8bef\u3002", e);
        }
    }

    public static class FormulaFileIOException
    extends RuntimeException {
        private static final long serialVersionUID = -4399168522296632709L;

        public FormulaFileIOException(Throwable e) {
            super("\u516c\u5f0f\u6587\u4ef6\u8bfb\u5199\u9519\u8bef\u3002", e);
        }
    }

    public static class JSFile
    extends FormulaFile {
        private static final long serialVersionUID = 1296947684903557931L;
        private String script;

        public JSFile(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, String script) {
            super(formulaSchemeKey, formKey, formulaType);
            this.setScript(script);
        }

        public String getScript() {
            return this.script;
        }

        public void setScript(String script) {
            this.script = script;
        }
    }

    public static class ExpressionFile
    extends FormulaFile {
        private static final long serialVersionUID = -5943773907888745939L;
        private List<IParsedExpression> expressions;

        public List<IParsedExpression> getExpressions() {
            return this.expressions;
        }

        public void setExpressions(List<IParsedExpression> expressions) {
            this.expressions = expressions;
        }

        public ExpressionFile(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, List<IParsedExpression> expressions) {
            super(formulaSchemeKey, formKey, formulaType);
            this.expressions = expressions;
        }
    }

    public static abstract class FormulaFile
    implements Serializable {
        private static final long serialVersionUID = 4230000676033094370L;
        private String formulaSchemeKey;
        private String formKey;
        private DataEngineConsts.FormulaType formulaType;

        public String getFormulaSchemeKey() {
            return this.formulaSchemeKey;
        }

        public void setFormulaSchemeKey(String formulaSchemeKey) {
            this.formulaSchemeKey = formulaSchemeKey;
        }

        public String getFormKey() {
            return this.formKey;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }

        public DataEngineConsts.FormulaType getFormulaType() {
            return this.formulaType;
        }

        public void setFormulaType(DataEngineConsts.FormulaType formulaType) {
            this.formulaType = formulaType;
        }

        public FormulaFile(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType) {
            this.formulaSchemeKey = formulaSchemeKey;
            this.formKey = formKey;
            this.formulaType = formulaType;
        }
    }
}

