/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.fml_cache_refresh.EFDCCacheService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.efdc.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.service.fml_cache_refresh.EFDCCacheService;
import com.jiuqi.nr.efdc.common.CloneUtils;
import com.jiuqi.nr.efdc.dao.SoluctionsDao;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.internal.service.EntityDataSerivce;
import com.jiuqi.nr.efdc.internal.utils.EFDCConstants;
import com.jiuqi.nr.efdc.internal.utils.NrResult;
import com.jiuqi.nr.efdc.internal.utils.QueryEntityFormatter;
import com.jiuqi.nr.efdc.pojo.QueryEntity;
import com.jiuqi.nr.efdc.pojo.QueryEntityImpl;
import com.jiuqi.nr.efdc.service.DataCenterService;
import com.jiuqi.nr.efdc.service.SoluctionQueryService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
@Transactional(rollbackFor={NpRollbackException.class})
public class SoluctionQueryServiceImpl
implements SoluctionQueryService,
RuntimeDefinitionRefreshListener,
Observer,
EFDCCacheService,
IParamDeployFinishListener {
    private static final Logger log = LoggerFactory.getLogger(SoluctionQueryServiceImpl.class);
    private static final String DEFAULT_VOID = "VOID";
    private static final String TOP_PARENT = "00000000000000000000000000000000";
    private static final String NULL_SOLUCTION = "00000000-0000-0000-0000-000000000000";
    private static final String SPLIT_STR = ";";
    private static final String SPLIT_STR_AT = "@";
    private static final String FINANCI_ALACCESS = "FA";
    private static final String RPT = "RPT";
    public static final String CACHE_NAME = "EFDC_QUERY_RESULT";
    public static final String FAKE_UUID = "b403ae95-3f55-4862-b128-b7b83c1056e0";
    private NedisCache cache;
    private ThreadLocal<Set<String>> entityKeySet = new ThreadLocal();
    @Autowired
    private IFormulaRunTimeController formCtrl;
    @Autowired
    private SoluctionsDao dao;
    @Autowired
    private DataCenterService dataService;
    @Autowired
    private IRunTimeViewController runTimeView;
    @Autowired
    IDataDefinitionDesignTimeController desTimeCtrl;
    @Autowired
    private EntityDataSerivce entityDataSerivce;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager("EFDC_CACHE").getCache(CACHE_NAME);
    }

    public void onClearCache() {
        this.cache.clear();
    }

    public boolean isAsyn() {
        return false;
    }

    public void excute(Object[] objs) {
        if (objs != null) {
            for (Object obj : objs) {
                if (!(obj instanceof String)) continue;
                this.cache.hDel((String)obj, new Object[]{FINANCI_ALACCESS, RPT});
            }
        }
    }

    public void clearCache(String formulaSchemeKey) {
        String formSchemeKey;
        FormSchemeDefine formScheme;
        FormulaSchemeDefine formulaSchemeDefine = this.formCtrl.queryFormulaSchemeDefine(formulaSchemeKey);
        if (formulaSchemeDefine != null && (formScheme = this.runTimeView.getFormScheme(formSchemeKey = formulaSchemeDefine.getFormSchemeKey())) != null) {
            String taskKey = formScheme.getTaskKey();
            this.cache.hDel(taskKey, new Object[]{FINANCI_ALACCESS, RPT});
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FormulaSchemeDefine getSoluctionByDimensions(QueryObjectImpl object, Map<String, String> dimMap, String entityId) {
        Cache.ValueWrapper valueWrapper = this.cache.hGet(object.getTaskKey(), (Object)FINANCI_ALACCESS);
        this.solutionAssistDim(object);
        MultiKey<String> key = new MultiKey<String>(object.getTaskKey(), object.getFormSchemeKey(), object.getMainDim(), object.getAssistDim());
        if (valueWrapper == null) {
            String string = FINANCI_ALACCESS;
            synchronized (FINANCI_ALACCESS) {
                Cache.ValueWrapper wrapper = this.cache.hGet(object.getTaskKey(), (Object)FINANCI_ALACCESS);
                if (wrapper == null) {
                    FormulaSchemeDefine define = this.loadSolution(object, dimMap, entityId);
                    HashMap<MultiKey<String>, String> map = new HashMap<MultiKey<String>, String>();
                    map.put(key, define == null ? FAKE_UUID : define.getKey());
                    this.cache.hSet(object.getTaskKey(), (Object)FINANCI_ALACCESS, map);
                    // ** MonitorExit[var6_6] (shouldn't be in output)
                    return define;
                }
                // ** MonitorExit[var6_6] (shouldn't be in output)
                return this.getFormulaSchemeFromWrapper(object, dimMap, entityId, key, wrapper);
            }
        }
        return this.getFormulaSchemeFromWrapper(object, dimMap, entityId, key, valueWrapper);
    }

    private FormulaSchemeDefine getFormulaSchemeFromWrapper(QueryObjectImpl object, Map<String, String> dimMap, String entityId, MultiKey<String> key, Cache.ValueWrapper wrapper) {
        HashMap<MultiKey<String>, String> map = (HashMap<MultiKey<String>, String>)wrapper.get();
        if (map == null) {
            map = new HashMap<MultiKey<String>, String>();
            this.cache.hSet(object.getTaskKey(), (Object)FINANCI_ALACCESS, map);
        }
        if (!map.containsKey(key)) {
            FormulaSchemeDefine define = this.loadSolution(object, dimMap, entityId);
            map.put(key, define == null ? FAKE_UUID : define.getKey());
            return define;
        }
        String formulaSchemeKey = (String)map.get(key);
        if (FAKE_UUID.equals(formulaSchemeKey)) {
            return null;
        }
        return this.formCtrl.queryFormulaSchemeDefine(formulaSchemeKey);
    }

    private FormulaSchemeDefine loadSolution(QueryObjectImpl object, Map<String, String> dimMap, String entityId) {
        this.entityKeySet.set(new HashSet());
        QueryObjectImpl cloneObj = CloneUtils.clone(object);
        Object result = null;
        List<FormulaSchemeDefine> allFormulaSchemeByFromScheme = this.dataService.getAllFormulaSchemeByFromScheme(cloneObj.getFormSchemeKey());
        if (!CollectionUtils.isEmpty(allFormulaSchemeByFromScheme)) {
            QueryObjectImpl query = this.getFormuluaSchemeDataInDB(cloneObj, dimMap, entityId, true);
            result = query == null || query.getSolutionKey() == null ? this.getDefaultScheme(cloneObj.getFormSchemeKey()) : (NULL_SOLUCTION.equals(query.getSolutionKey()) ? null : this.formCtrl.queryFormulaSchemeDefine(query.getSolutionKey()));
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FormulaSchemeDefine getRPTFormulaScheme(QueryObjectImpl object, Map<String, String> dimMap, String entityId) {
        Cache.ValueWrapper valueWrapper = this.cache.hGet(object.getTaskKey(), (Object)RPT);
        this.solutionAssistDim(object);
        MultiKey<String> key = new MultiKey<String>(object.getTaskKey(), object.getFormSchemeKey(), object.getMainDim(), object.getAssistDim());
        if (valueWrapper == null) {
            String string = RPT;
            synchronized (RPT) {
                Cache.ValueWrapper wrapper = this.cache.hGet(object.getTaskKey(), (Object)RPT);
                if (wrapper == null) {
                    FormulaSchemeDefine define = this.loadRPTFormulaScheme(object, dimMap, entityId);
                    HashMap<MultiKey<String>, String> map = new HashMap<MultiKey<String>, String>();
                    map.put(key, define == null ? FAKE_UUID : define.getKey());
                    this.cache.hSet(object.getTaskKey(), (Object)RPT, map);
                    // ** MonitorExit[var6_6] (shouldn't be in output)
                    return define;
                }
                // ** MonitorExit[var6_6] (shouldn't be in output)
                return this.getRPTFormulaSchemeFromWrapper(object, dimMap, entityId, key, wrapper);
            }
        }
        return this.getRPTFormulaSchemeFromWrapper(object, dimMap, entityId, key, valueWrapper);
    }

    private FormulaSchemeDefine getRPTFormulaSchemeFromWrapper(QueryObjectImpl object, Map<String, String> dimMap, String entityId, MultiKey<String> key, Cache.ValueWrapper wrapper) {
        HashMap<MultiKey<String>, String> map = (HashMap<MultiKey<String>, String>)wrapper.get();
        if (map == null) {
            map = new HashMap<MultiKey<String>, String>();
            this.cache.hSet(object.getTaskKey(), (Object)RPT, map);
        }
        if (!map.containsKey(key)) {
            FormulaSchemeDefine define = this.loadRPTFormulaScheme(object, dimMap, entityId);
            map.put(key, define == null ? FAKE_UUID : define.getKey());
            return define;
        }
        String formulaSchemeKey = (String)map.get(key);
        if (FAKE_UUID.equals(formulaSchemeKey)) {
            return null;
        }
        return this.formCtrl.queryFormulaSchemeDefine(formulaSchemeKey);
    }

    private FormulaSchemeDefine loadRPTFormulaScheme(QueryObjectImpl object, Map<String, String> dimMap, String entityId) {
        this.entityKeySet.set(new HashSet());
        QueryObjectImpl cloneObj = CloneUtils.clone(object);
        FormulaSchemeDefine result = null;
        QueryObjectImpl query = this.getFormuluaSchemeDataInDB(cloneObj, dimMap, entityId, false);
        if (query != null && query.getRptScheme() != null) {
            result = NULL_SOLUCTION.equals(query.getRptScheme()) ? null : this.formCtrl.queryFormulaSchemeDefine(query.getRptScheme());
        }
        return result;
    }

    @Override
    public Map<String, String> batchQueryByDimensions(QueryObjectImpl object, List<String> mainDims, Map<String, String> dimMap, String entityId, boolean getScheme) throws Exception {
        this.entityKeySet.set(new HashSet());
        HashMap<String, String> result = new HashMap<String, String>();
        if (mainDims == null || mainDims.isEmpty()) {
            throw new RuntimeException("\u67e5\u8be2\u7684\u5355\u4f4d\u96c6\u5408\u4e3a\u7a7a");
        }
        List<IEntityRow> allEntityRows = this.entityDataSerivce.queryAllRows(entityId);
        if (allEntityRows == null || allEntityRows.isEmpty()) {
            throw new RuntimeException("\u5f53\u524d\u89c6\u56fe\u4e0b\u6ca1\u6709\u67e5\u8be2\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\u6570\u636e");
        }
        String assistDimension = this.filterAssistDimension(object.getFormSchemeKey(), dimMap);
        object.setMainDim(null);
        object.setAssistDim(assistDimension);
        List<QueryObjectImpl> allSolutions = this.dao.getSolutions(object, getScheme);
        HashMap<String, QueryObjectImpl> solutionMap = new HashMap<String, QueryObjectImpl>(allSolutions.size());
        if (allSolutions != null && !allSolutions.isEmpty()) {
            allSolutions.forEach(e -> solutionMap.put(e.getMainDim(), (QueryObjectImpl)e));
        }
        for (String mainDim : mainDims) {
            object.setMainDim(mainDim);
            QueryObjectImpl query = this.circulationQuery(object, allEntityRows, solutionMap);
            if (query == null || query.getSolutionKey() == null) {
                if (!getScheme) continue;
                result.put(mainDim, this.getDefaultScheme(object.getFormSchemeKey()).getKey());
                continue;
            }
            result.put(mainDim, query.getSolutionKey());
        }
        return result;
    }

    private String filterAssistDimension(String formSchemeKey, Map<String, String> dimMap) {
        Map<Object, Object> filterDim = new HashMap();
        if (!dimMap.isEmpty()) {
            filterDim = this.filterAssistDim(formSchemeKey, dimMap);
        }
        StringBuffer sbs = new StringBuffer();
        for (String string : filterDim.keySet()) {
            String value = (String)filterDim.get(string);
            sbs.append(string).append(":").append(value).append(SPLIT_STR);
        }
        return this.getAssistDimension(filterDim);
    }

    private QueryObjectImpl circulationQuery(QueryObjectImpl object, List<IEntityRow> allEntityRows, Map<String, QueryObjectImpl> solutionMap) {
        QueryObjectImpl result = solutionMap.get(object.getMainDim());
        if (result == null) {
            this.getEntityKeySet().add(object.getMainDim());
            result = this.queryByParent(object, allEntityRows, solutionMap);
        }
        return result;
    }

    private QueryObjectImpl queryByParent(QueryObjectImpl object, List<IEntityRow> allEntityRows, Map<String, QueryObjectImpl> solutionMap) {
        String parentId;
        QueryObjectImpl result = null;
        Optional<IEntityRow> first = allEntityRows.stream().filter(e -> e.getEntityKeyData().equals(object.getMainDim())).findFirst();
        if (first.isPresent() && this.isLegalParentNode(parentId = first.get().getParentEntityKey(), object.getMainDim())) {
            object.setMainDim(parentId);
            result = this.circulationQuery(object, allEntityRows, solutionMap);
        }
        return result;
    }

    @Override
    public List<QueryObjectImpl> getSoluctions(QueryObjectImpl object) {
        String realAssist = null;
        if (object.getAssistDim() != null && !"".equals(object.getAssistDim())) {
            QueryEntity parse = QueryEntityFormatter.parsingFromString(object.getAssistDim());
            realAssist = QueryEntityFormatter.formatToString(parse);
            object.setAssistDim(realAssist);
        }
        List<QueryObjectImpl> soluction = this.dao.getSolutions(object);
        return soluction;
    }

    @Override
    public NrResult insertSoluction(List<QueryObjectImpl> list) {
        StringBuffer log = new StringBuffer("\u4fdd\u5b58EFDC\u914d\u7f6e");
        NrResult result = new NrResult();
        int success = 0;
        int update = 0;
        int fail = 0;
        int delete = 0;
        if (list.size() > 0) {
            QueryObjectImpl queryObject = list.get(0);
            log.append("\u4efb\u52a1\u4e3a\uff1a").append(queryObject.getTaskKey()).append(",").append("\u62a5\u8868\u65b9\u6848\u4e3a:").append(queryObject.getFormSchemeKey()).append(",").append("\u7ef4\u5ea6\u4e3a\uff1a").append(queryObject.getAssistDim()).append("\r\n");
        }
        for (QueryObjectImpl impl : list) {
            if ("".equals(impl.getAssistDim())) {
                impl.setAssistDim(null);
            }
            impl.setUpdatetime(new Timestamp(System.currentTimeMillis()));
            try {
                List<QueryObjectImpl> soluctions = this.getSoluctions(impl);
                if (soluctions.size() > 0) {
                    for (QueryObjectImpl soluction : soluctions) {
                        if (impl.getSolutionKey() == null && impl.getRptScheme() == null) {
                            this.dao.delete(soluction);
                            ++delete;
                            log.append("\u5220\u9664\u5355\u4f4d\uff1a").append(soluction.getMainDim()).append("\u3002").append("\r\n");
                            continue;
                        }
                        log.append("\u66f4\u65b0\u4e86\u4e3b\u952e\u4e3a\uff1a").append(soluction.getId()).append("\u7684\u914d\u7f6e\uff0c");
                        String beforeRpt = soluction.getRptScheme();
                        String afterRpt = impl.getRptScheme();
                        String beforeSoluction = soluction.getSolutionKey();
                        String afterSoluction = impl.getSolutionKey();
                        if (beforeRpt != afterRpt) {
                            log.append("\u4fee\u6539\u524d\u7684\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u4e3a\uff1a").append(beforeRpt).append(",").append("\u4fee\u6539\u540e\u7684\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\uff1a").append(afterRpt).append("\u3002");
                        }
                        if (beforeSoluction != afterSoluction) {
                            log.append("\u4fee\u6539\u524d\u7684\u53d6\u6570\u65b9\u6848\u914d\u7f6e\u4e3a\uff1a").append(beforeSoluction).append(",").append("\u4fee\u6539\u540e\u7684\u53d6\u6570\u65b9\u6848\u914d\u7f6e\uff1a").append(afterSoluction).append("\u3002");
                        }
                        log.append("\r\n");
                        soluction.setSolutionKey(impl.getSolutionKey());
                        soluction.setRptScheme(impl.getRptScheme());
                        soluction.setUpdatetime(impl.getUpdatetime());
                        this.dao.update(soluction);
                        ++update;
                    }
                    continue;
                }
                impl.setId(UUID.randomUUID().toString());
                this.dao.insert(impl);
                ++success;
                log.append("\u65b0\u589e\u4e86\u914d\u7f6e\uff1a").append(impl.getId()).append(",").append("\u53d6\u6570\u65b9\u6848\u4e3a\uff1a").append(impl.getSolutionKey()).append(",").append("\u516c\u5f0f\u65b9\u6848\u4e3a\uff1a").append(impl.getRptScheme()).append("\u3002").append("\r\n");
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.clearCache(list);
        result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        result.setMsg("\u63d2\u5165" + success + "\u6761\u8bb0\u5f55,\u66f4\u65b0" + update + "\u6761\u8bb0\u5f55,\u5220\u9664" + delete + "\u6761\u8bb0\u5f55,\u5931\u8d25" + fail + "\u6761\u8bb0\u5f55!");
        LogHelper.info((String)"EFDC\u914d\u7f6e", (String)"\u4fdd\u5b58EFDC\u914d\u7f6e", (String)log.toString());
        return result;
    }

    @Override
    public NrResult clearByFormScheme(QueryObjectImpl object, List<String> choose) {
        NrResult result = new NrResult();
        StringBuffer log = new StringBuffer("\u5220\u9664EFDC\u914d\u7f6e\uff0c");
        if (object.getMainDim() != null || "".equals(object.getMainDim())) {
            object.setMainDim(null);
        }
        if (object.getTaskKey() != null) {
            log.append("\u4efb\u52a1\u4e3a\uff1a").append(object.getTaskKey()).append(",");
        }
        if (object.getFormSchemeKey() != null) {
            log.append("\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(object.getFormSchemeKey()).append(",");
        }
        if (object.getAssistDim() != null && !"".equals(object.getAssistDim())) {
            String[] split;
            String assistDim = object.getAssistDim();
            HashMap<String, String> dimMap = new HashMap<String, String>();
            for (String dim : split = assistDim.split(SPLIT_STR)) {
                String key = dim.substring(0, dim.indexOf(SPLIT_STR_AT));
                String table = dim.substring(dim.indexOf(SPLIT_STR_AT) + 1, dim.length());
                dimMap.put(table, key);
            }
            String assistDimension = this.getAssistDimension(dimMap);
            object.setAssistDim(assistDimension);
            log.append("\u7ef4\u5ea6\u4e3a\uff1a").append(object.getAssistDim());
        }
        log.append("\u3002");
        try {
            List<QueryObjectImpl> soluctions = this.dao.getSolutions2(object);
            if (soluctions.size() > 0) {
                if (!choose.isEmpty()) {
                    soluctions = soluctions.stream().filter(s -> choose.stream().filter(c -> c.equals(s.getMainDim())).findFirst().isPresent()).collect(Collectors.toList());
                }
                this.dao.deleteAll(soluctions);
                result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
                if (soluctions.isEmpty()) {
                    result.setMsg("\u6ca1\u6709\u53ef\u5220\u9664\u7684\u8bb0\u5f55.");
                    log.append("\u6ca1\u6709\u67e5\u5230\u53ef\u5220\u9664\u7684\u6570\u636e\u3002");
                } else {
                    result.setData(soluctions);
                    result.setMsg("\u6210\u529f\u5220\u9664\u4e86" + soluctions.size() + "\u6761\u8bb0\u5f55.");
                    log.append("\u5171\u5220\u9664\u4e86").append(soluctions.size()).append("\u6761\u6570\u636e:");
                }
                soluctions.forEach(e -> log.append(e.getId()).append("\u3001"));
                log.replace(log.length() - 2, log.length() - 1, "\u3002");
            } else {
                result.setMsg("\u6ca1\u6709\u53ef\u5220\u9664\u7684\u8bb0\u5f55.");
            }
            LogHelper.info((String)"EFDC\u914d\u7f6e", (String)"\u5220\u9664EFDC\u914d\u7f6e", (String)log.toString());
        }
        catch (Exception e2) {
            result.setStatus(EFDCConstants.RETURN_FAIL);
            result.setMsg("\u5220\u9664\u5931\u8d25.");
            SoluctionQueryServiceImpl.log.error(e2.getMessage(), e2);
            log.append(e2.getMessage());
            LogHelper.error((String)"EFDC\u914d\u7f6e", (String)"\u5220\u9664EFDC\u914d\u7f6e", (String)log.toString());
        }
        this.clearCache(object);
        return result;
    }

    @Override
    public Boolean existSolution(String taskKey) {
        return this.dao.countSolutionByTask(taskKey) > 0;
    }

    private QueryObjectImpl getFormuluaSchemeDataInDB(QueryObjectImpl object, Map<String, String> dimMap, String entityId, Boolean getScheme) {
        QueryObjectImpl result = null;
        if (object.getMainDim().equals(NULL_SOLUCTION)) {
            return result;
        }
        HashMap<String, String> filterDim = new HashMap();
        HashMap<String, String> newDim = new HashMap<String, String>(dimMap);
        if (!newDim.isEmpty()) {
            filterDim = this.filterAssistDim(object.getFormSchemeKey(), newDim);
        }
        StringBuffer sbs = new StringBuffer();
        for (String key : filterDim.keySet()) {
            String value = (String)filterDim.get(key);
            sbs.append(key).append(":").append(value).append(SPLIT_STR);
        }
        String assistDim = this.getAssistDimension(filterDim);
        object.setAssistDim(assistDim);
        IEntityTable entityTable = this.getEntityTable(entityId, dimMap);
        IEntityRow entityRow = entityTable.quickFindByEntityKey(object.getMainDim());
        String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
        Map<String, QueryObjectImpl> soluctionsMap = this.getSoluctionsMap(object, parentsEntityKeyDataPath);
        result = this.getInheritSolution(object, entityId, getScheme, entityTable, soluctionsMap);
        return result;
    }

    private Map<String, QueryObjectImpl> getSoluctionsMap(QueryObjectImpl object, String[] parentsEntityKeyDataPath) {
        QueryObjectImpl copy = new QueryObjectImpl();
        BeanUtils.copyProperties(object, copy);
        if (parentsEntityKeyDataPath.length > 0) {
            List<String> unitKeys = Arrays.stream(parentsEntityKeyDataPath).collect(Collectors.toList());
            unitKeys.add(object.getMainDim());
            copy.setMainDim(null);
            copy.setMainDims(unitKeys);
        } else {
            copy.setMainDim(object.getMainDim());
        }
        List<QueryObjectImpl> solutions = this.dao.getSolutions(copy);
        Map<String, QueryObjectImpl> queryObjectMap = new HashMap<String, QueryObjectImpl>();
        if (!CollectionUtils.isEmpty(solutions)) {
            queryObjectMap = solutions.stream().collect(Collectors.toMap(QueryObjectImpl::getMainDim, q -> q, (ke1, key2) -> key2));
        }
        return queryObjectMap;
    }

    private QueryObjectImpl getInheritSolution(QueryObjectImpl object, String entityId, Boolean getScheme, IEntityTable entityTable, Map<String, QueryObjectImpl> soluctionsMap) {
        QueryObjectImpl result = null;
        QueryObjectImpl queryObject = soluctionsMap.get(object.getMainDim());
        if (null == queryObject) {
            this.getEntityKeySet().add(object.getMainDim());
            result = this.getParentData(object, entityId, getScheme, entityTable, soluctionsMap);
        } else {
            result = queryObject;
        }
        return result;
    }

    private QueryObjectImpl getParentData(QueryObjectImpl object, String entityId, Boolean getScheme, IEntityTable entityTable, Map<String, QueryObjectImpl> soluctionsMap) {
        QueryObjectImpl result = null;
        String parentId = null;
        try {
            parentId = this.getEntityParent(object.getMainDim(), entityTable);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (this.isLegalParentNode(parentId, object.getMainDim())) {
            object.setMainDim(parentId);
            result = this.getInheritSolution(object, entityId, getScheme, entityTable, soluctionsMap);
        }
        return result;
    }

    private boolean isLegalParentNode(String parentId, String mainDim) {
        return parentId != null && !TOP_PARENT.equals(parentId) && !mainDim.equals(parentId) && !this.getEntityKeySet().contains(parentId);
    }

    private IEntityTable getEntityTable(String entityId, Map<String, String> dimMap) {
        IEntityTable entityTable = this.entityDataSerivce.getEntityTable(entityId, dimMap);
        return entityTable;
    }

    private String getEntityParent(String mainDim, IEntityTable entityTable) throws Exception {
        String parentKey;
        IEntityRow row;
        String parent = null;
        if (entityTable != null && (row = entityTable.findByEntityKey(mainDim)) != null && !StringUtils.isEmpty((String)(parentKey = row.getParentEntityKey())) && !DEFAULT_VOID.equals(parentKey)) {
            parent = parentKey;
        }
        return parent;
    }

    private FormulaSchemeDefine getDefaultScheme(String fromScheme) {
        Optional<FormulaSchemeDefine> findDefault;
        FormulaSchemeDefine define = null;
        List<FormulaSchemeDefine> formulaSchemes = this.dataService.getAllFormulaSchemeByFromScheme(fromScheme);
        if (formulaSchemes.size() > 0 && (findDefault = formulaSchemes.stream().filter(FormulaSchemeDefine::isDefault).findFirst()).isPresent()) {
            define = findDefault.get();
        }
        return define;
    }

    private String getAssistDimension(Map<String, String> dimMap) {
        QueryEntityImpl queryEntity = new QueryEntityImpl();
        String assistDim = null;
        if (dimMap != null && !dimMap.isEmpty()) {
            for (String key : dimMap.keySet()) {
                String value = dimMap.get(key);
                if (null == value || "".equals(value)) continue;
                queryEntity.setQueryEntityDimessionValue(key, value);
            }
        }
        if (!queryEntity.getDimessionNames().isEmpty()) {
            assistDim = QueryEntityFormatter.formatToString(queryEntity);
        }
        return assistDim;
    }

    private Map<String, String> filterAssistDim(String formSchemeKey, Map<String, String> dimMap) {
        dimMap.remove("DATATIME");
        return dimMap;
    }

    private Set<String> getEntityKeySet() {
        return this.entityKeySet.get();
    }

    private void clearCache(List<QueryObjectImpl> queryObjects) {
        for (QueryObjectImpl queryObject : queryObjects) {
            this.clearCache(queryObject);
        }
    }

    private void clearCache(QueryObjectImpl queryObject) {
        this.cache.hDel(queryObject.getTaskKey(), new Object[]{RPT, FINANCI_ALACCESS});
    }

    private void solutionAssistDim(QueryObjectImpl object) {
        if (object.getAssistDim() != null && !"".equals(object.getAssistDim())) {
            String[] split;
            String assistDim = object.getAssistDim();
            HashMap<String, String> dimMap = new HashMap<String, String>();
            for (String dim : split = assistDim.split(SPLIT_STR)) {
                String key = dim.substring(0, dim.indexOf(SPLIT_STR_AT));
                String table = dim.substring(dim.indexOf(SPLIT_STR_AT) + 1, dim.length());
                dimMap.put(table, key);
            }
            String assistDimension = this.getAssistDimension(dimMap);
            object.setAssistDim(assistDimension);
        }
    }
}

