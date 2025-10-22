/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDeployTimeService
 *  com.jiuqi.util.DateUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.deploy.service.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.deploy.extend.RuntimeParamChangeEvent;
import com.jiuqi.nr.definition.deploy.service.IParamDeployTimeService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDeployTimeService;
import com.jiuqi.util.DateUtil;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParamDeployTimeServiceImpl
implements IParamDeployTimeService,
IRuntimeDeployTimeService {
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDefineDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final NedisCache cache;
    private final NedisCache taskCache;
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();

    public ParamDeployTimeServiceImpl(CacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION");
        this.cache = cacheManager.getCache("ParamDeployTime");
        this.taskCache = cacheManager.getCache("TaskDeployTime");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Date getDeployTime(ParamResourceType type, String schemeKey) {
        if (this.cache.exists(type.getId())) {
            String key = this.getI18nKey(type, schemeKey);
            Cache.ValueWrapper wrapper = this.cache.hGet(type.getId(), (Object)key);
            if (null == wrapper) {
                IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(key);
                synchronized (mutex) {
                    Date now = Date.from(Instant.now());
                    this.cache.hSet(type.getId(), (Object)key, (Object)now);
                    return now;
                }
            }
            return (Date)wrapper.get();
        }
        this.loadCache(type);
        return this.getDeployTime(type, schemeKey);
    }

    public Map<String, Date> getFormulaUpdateTime() {
        String sql = "SELECT FL_SCHEME_KEY AS FL_SCHEME_KEY, MAX(FL_UPDATETIME) AS FL_UPDATETIME FROM NR_PARAM_FORMULA GROUP BY FL_SCHEME_KEY";
        HashMap<String, Date> result = new HashMap<String, Date>();
        this.jdbcTemplate.query("SELECT FL_SCHEME_KEY AS FL_SCHEME_KEY, MAX(FL_UPDATETIME) AS FL_UPDATETIME FROM NR_PARAM_FORMULA GROUP BY FL_SCHEME_KEY", rs -> result.put(rs.getString(1), rs.getTimestamp(2)));
        return result;
    }

    public Map<String, Date> getFormUpdateTime() {
        String sql = "SELECT F.FM_FORMSCHEME AS FORMSCHEME, D.BD_LANG AS LANG, MAX(D.BD_UPDATETIME) AS UPDATETIME FROM NR_PARAM_BIGDATATABLE D INNER JOIN NR_PARAM_FORM F ON D.BD_KEY = F.FM_KEY WHERE D.BD_CODE = 'FORM_DATA' GROUP BY F.FM_FORMSCHEME, D.BD_LANG";
        HashMap<String, Date> result = new HashMap<String, Date>();
        this.jdbcTemplate.query("SELECT F.FM_FORMSCHEME AS FORMSCHEME, D.BD_LANG AS LANG, MAX(D.BD_UPDATETIME) AS UPDATETIME FROM NR_PARAM_BIGDATATABLE D INNER JOIN NR_PARAM_FORM F ON D.BD_KEY = F.FM_KEY WHERE D.BD_CODE = 'FORM_DATA' GROUP BY F.FM_FORMSCHEME, D.BD_LANG", rs -> {
            String scheme = rs.getString(1);
            int lang = rs.getInt(2);
            Timestamp timestamp = rs.getTimestamp(3);
            Date date = (Date)result.get(scheme);
            if (null == date || date.before(timestamp)) {
                result.put(scheme, timestamp);
            }
            result.put(this.getI18nKey(scheme, String.valueOf(lang)), timestamp);
        });
        return result;
    }

    private String getI18nKey(String schemeKey, String lang) {
        return schemeKey + "@" + lang;
    }

    private String getI18nKey(ParamResourceType type, String schemeKey) {
        if (ParamResourceType.FORM == type) {
            return this.getI18nKey(schemeKey, LanguageType.getCurrentLanguageType().getKey());
        }
        return schemeKey;
    }

    private synchronized void loadCache(ParamResourceType type) {
        if (this.cache.exists(type.getId())) {
            return;
        }
        Date now = Date.from(Instant.now());
        block4: for (ParamResourceType resourceType : ParamResourceType.values()) {
            switch (resourceType) {
                case TASK: 
                case FORM: {
                    HashMap<String, Date> taskDeployTimes = new HashMap<String, Date>();
                    Map<String, Date> formSchemeDeployTimes = this.getFormUpdateTime();
                    List formSchemeDefines = this.formSchemeDefineDao.list();
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                        if (!formSchemeDeployTimes.containsKey(formSchemeDefine.getKey())) {
                            formSchemeDeployTimes.put(formSchemeDefine.getKey(), now);
                        }
                        Date formSchemeDeployTime = formSchemeDeployTimes.get(formSchemeDefine.getKey());
                        Date taskDeployTime = (Date)taskDeployTimes.get(formSchemeDefine.getTaskKey());
                        if (null != taskDeployTime && !formSchemeDeployTime.after(taskDeployTime)) continue;
                        taskDeployTimes.put(formSchemeDefine.getTaskKey(), formSchemeDeployTime);
                    }
                    this.cache.hMSet(ParamResourceType.TASK.getId(), taskDeployTimes);
                    this.cache.hMSet(ParamResourceType.FORM.getId(), formSchemeDeployTimes);
                    for (Map.Entry entry : taskDeployTimes.entrySet()) {
                        this.taskCache.put((String)entry.getKey(), (Object)DateUtil.dateTime2String((Date)((Date)entry.getValue())));
                    }
                    continue block4;
                }
                case FORMULA: {
                    Map<String, Date> deployTimes = this.getFormulaUpdateTime();
                    this.cache.hMSet(type.getId(), deployTimes);
                    for (Map.Entry<String, Date> entry : deployTimes.entrySet()) {
                        this.taskCache.put(entry.getKey(), (Object)DateUtil.dateTime2String((Date)entry.getValue()));
                    }
                    continue block4;
                }
                default: {
                    this.cache.hMSet(type.getId(), new HashMap());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String queryTime(String taskKey) {
        if (this.cache.exists(ParamResourceType.TASK.getId())) {
            Cache.ValueWrapper wrapper = this.taskCache.get(taskKey);
            if (null == wrapper) {
                IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(taskKey);
                synchronized (mutex) {
                    Date now = Date.from(Instant.now());
                    String timeString = DateUtil.dateTime2String((Date)now);
                    this.cache.put(taskKey, (Object)timeString);
                    return timeString;
                }
            }
            return (String)wrapper.get();
        }
        this.loadCache(ParamResourceType.TASK);
        return this.queryTime(taskKey);
    }

    @Override
    public void refreshDeployTime() {
        this.cache.clear();
        this.taskCache.clear();
    }

    @Service
    public static class ParamRefreshEventListener
    implements ApplicationListener<RefreshDefinitionCacheEvent> {
        @Autowired
        private ParamDeployTimeServiceImpl paramDeployTimeService;

        @Override
        public void onApplicationEvent(RefreshDefinitionCacheEvent event) {
            this.paramDeployTimeService.refreshDeployTime();
        }
    }

    @Service
    public static class ParamDeployFinishedEventListener
    implements ApplicationListener<DeployFinishedEvent> {
        @Autowired
        private ParamDeployTimeServiceImpl paramDeployTimeService;

        @Override
        public void onApplicationEvent(DeployFinishedEvent event) {
            this.paramDeployTimeService.refreshDeployTime();
        }
    }

    @Service
    public static class ParamChangeEventListener
    implements ApplicationListener<RuntimeParamChangeEvent> {
        @Autowired
        private ParamDeployTimeServiceImpl paramDeployTimeService;

        @Override
        public void onApplicationEvent(RuntimeParamChangeEvent event) {
            this.paramDeployTimeService.refreshDeployTime();
        }
    }
}

