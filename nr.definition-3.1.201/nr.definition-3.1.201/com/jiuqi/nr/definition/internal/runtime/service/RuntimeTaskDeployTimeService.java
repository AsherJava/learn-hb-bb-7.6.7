/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.DateUtil
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.util.DateUtil;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDeployTimeService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

@Deprecated
public class RuntimeTaskDeployTimeService
implements RuntimeDefinitionChangeListener,
IRuntimeDeployTimeService,
RuntimeDefinitionRefreshListener {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeTaskDeployTimeService.class);
    private final String cacheKey = "RuntimeTaskDeployTime";
    public static final String NAME = "REMOTE_NR";
    private final RunTimeTaskDefineDao taskDao;
    private final RunTimeFormulaSchemeDefineDao formulaSchemeDao;
    protected final NedisCache cache;
    private final Object cacheLock = new Object();

    public RuntimeTaskDeployTimeService(RunTimeTaskDefineDao taskDao, RunTimeFormulaSchemeDefineDao formulaSchemeDao, NedisCacheProvider cacheManger) {
        this.taskDao = taskDao;
        this.formulaSchemeDao = formulaSchemeDao;
        this.cache = cacheManger.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION").getCache("RuntimeTaskDeployTime");
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        this.cache.clear();
    }

    private void updateTime(Collection<String> deploiedObjectKeys) {
        if (null != deploiedObjectKeys && deploiedObjectKeys.size() > 0) {
            this.synchronizedRun(() -> {
                deploiedObjectKeys.stream().forEach(id -> this.cache.evict(this.createObjectCacheKey((String)id).toString()));
                Date date = new Date();
                String nowDateString = DateUtil.dateTime2String((Date)date);
                Map<Object, Object> entries = deploiedObjectKeys.stream().collect(Collectors.toMap(t -> this.createObjectCacheKey((String)t), t -> nowDateString));
                for (Map.Entry<Object, Object> entey : entries.entrySet()) {
                    this.cache.put(entey.getKey().toString(), entey.getValue());
                }
            });
        }
    }

    @Override
    public String queryTime(String taskKey) {
        Cache.ValueWrapper valueWrapper = this.cache.get(this.createObjectCacheKey(taskKey).toString());
        if (valueWrapper != null) {
            return (String)valueWrapper.get();
        }
        return this.synchronizedRun(() -> this.loadCache(taskKey));
    }

    private String loadCache(String taskKey) {
        Cache.ValueWrapper valueWrapper = this.cache.get(this.createObjectCacheKey(taskKey).toString());
        if (valueWrapper != null) {
            return (String)valueWrapper.get();
        }
        Date nowDate = new Date();
        String nowDateString = DateUtil.dateTime2String((Date)nowDate);
        try {
            Map<String, Date> allTaskDeployTime = this.taskDao.getAllTaskDeployTime();
            for (Map.Entry<String, Date> entry : allTaskDeployTime.entrySet()) {
                this.cache.put(this.createObjectCacheKey(entry.getKey()).toString(), (Object)(null == entry.getValue() ? nowDateString : DateUtil.dateTime2String((Date)entry.getValue())));
            }
            List<FormulaSchemeDefine> list = this.formulaSchemeDao.list();
            if (list != null && !list.isEmpty()) {
                for (FormulaSchemeDefine formulaSchemeDefine : list) {
                    this.cache.put(this.createObjectCacheKey(formulaSchemeDefine.getKey()).toString(), (Object)nowDateString);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        valueWrapper = this.cache.get(this.createObjectCacheKey(taskKey).toString());
        if (valueWrapper != null) {
            return (String)valueWrapper.get();
        }
        this.cache.put(this.createObjectCacheKey(taskKey).toString(), (Object)nowDateString);
        return nowDateString;
    }

    private Object createObjectCacheKey(String t) {
        return "RuntimeTaskDeployTime".concat(t);
    }

    public void onClearCache() {
        this.cache.clear();
    }

    protected void synchronizedRun(Runnable runner) {
        RuntimeTaskDeployTimeService.synchronizedRun(this.cacheLock, runner);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void synchronizedRun(Object lock, Runnable runner) {
        Object object = lock;
        synchronized (object) {
            runner.run();
        }
    }

    protected <R> R synchronizedRun(Supplier<R> runner) {
        return RuntimeTaskDeployTimeService.synchronizedRun(this.cacheLock, runner);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static <R> R synchronizedRun(Object lock, Supplier<R> runner) {
        Object object = lock;
        synchronized (object) {
            return runner.get();
        }
    }
}

