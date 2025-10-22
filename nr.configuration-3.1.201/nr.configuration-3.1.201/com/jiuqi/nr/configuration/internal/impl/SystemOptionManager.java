/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.configuration.internal.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.configuration.common.SystemOptionType;
import com.jiuqi.nr.configuration.config.SystemOptionCacheManagerConfiguration;
import com.jiuqi.nr.configuration.controller.ISystemOptionManager;
import com.jiuqi.nr.configuration.db.ISystemOptionDao;
import com.jiuqi.nr.configuration.facade.SystemOptionBase;
import com.jiuqi.nr.configuration.facade.SystemOptionDefine;
import com.jiuqi.nr.configuration.internal.impl.SystemOptionDefineImpl;
import com.jiuqi.nr.configuration.service.ISystemOptionListenerService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationContext;

@Deprecated
public class SystemOptionManager
implements ISystemOptionManager,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SystemOptionManager.class);
    private NedisCacheManager cacheManager;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ISystemOptionDao systemOptionDao;
    @Autowired
    private ISystemOptionListenerService iSystemOptionListenerService;
    private final String CACHENAME = "SYSTEM_OPTION";
    private ConcurrentHashMap<String, HashMap<SystemOptionType, SystemOptionBase>> defaultOptions = new ConcurrentHashMap();

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("SYSTEM_OPTION");
    }

    @Override
    public void registerSystemOption(SystemOptionBase optionDefine) {
        if (StringUtils.isEmpty((String)optionDefine.getKey())) {
            return;
        }
        this.setDefaultOption(optionDefine);
    }

    @Override
    public Object getObject(String key) {
        return this.getObject(key, null, null);
    }

    @Override
    public Object getObject(String key, String taskKey) {
        return this.getObject(key, taskKey, null);
    }

    @Override
    public Object getObject(String key, String taskKey, String formSchemeKey) {
        try {
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            String cacheKey = this.getCacheKey(key, taskKey, formSchemeKey);
            Cache.ValueWrapper wrapper = caffeineCache.get(cacheKey);
            if (wrapper != null) {
                return wrapper.get();
            }
            SystemOptionDefine optionDefine = this.systemOptionDao.getOptionByCode(key, taskKey, formSchemeKey);
            if (optionDefine == null) {
                SystemOptionType optionType = this.getOptionType(taskKey, formSchemeKey);
                SystemOptionBase baseDefine = this.getDefaultOption(key, optionType);
                if (baseDefine != null) {
                    Object value = baseDefine.getDefaultValue();
                    caffeineCache.put(cacheKey, value);
                    return value;
                }
                caffeineCache.put(cacheKey, null);
                return null;
            }
            Object value = optionDefine.getValue();
            caffeineCache.put(cacheKey, value);
            return value;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Object getObjectFromDB(String key, String taskKey, String formSchemeKey) {
        try {
            SystemOptionDefine optionDefine = this.systemOptionDao.getOptionByCode(key, taskKey, formSchemeKey);
            if (optionDefine == null) {
                return null;
            }
            Object value = optionDefine.getValue();
            return value;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void setObject(String key, Object value) {
        this.setObject(key, null, null, value);
    }

    @Override
    public void setObject(String key, String taskKey, Object value) {
        this.setObject(key, taskKey, null, value);
    }

    @Override
    public void setObject(String key, String taskKey, String formSchemeKey, Object value) {
        try {
            Object oldValue = this.getObject(key, taskKey, formSchemeKey);
            this.systemOptionDao.setSystemOption(key, value, taskKey, formSchemeKey);
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            String cacheKey = this.getCacheKey(key, taskKey, formSchemeKey);
            caffeineCache.evict(cacheKey);
            if (!(value == oldValue || value != null && value.equals(oldValue))) {
                this.iSystemOptionListenerService.publish(key, taskKey, formSchemeKey, value, oldValue);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }
    }

    @Override
    public List<SystemOptionDefine> getGlobalOptions() {
        try {
            List<SystemOptionDefine> optionDefines = this.systemOptionDao.getOptions();
            optionDefines = this.mergeDefaultOptionsByType(optionDefines, SystemOptionType.SYSTEM_OPTION);
            return optionDefines;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<SystemOptionDefine> getOptionsByTask(String taskKey) {
        try {
            List<SystemOptionDefine> optionDefines = this.systemOptionDao.getOptionsByTask(taskKey);
            optionDefines = this.mergeDefaultOptionsByType(optionDefines, SystemOptionType.TASK_OPTION);
            return optionDefines;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<SystemOptionDefine> getOptionsByFormScheme(String taskKey, String formSchemeKey) {
        try {
            List<SystemOptionDefine> optionDefines = this.systemOptionDao.getOptionsByFormScheme(taskKey, formSchemeKey);
            optionDefines = this.mergeDefaultOptionsByType(optionDefines, SystemOptionType.FORMSCHEME_OPTION);
            return optionDefines;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<SystemOptionDefine> getAllOptions() {
        try {
            List<SystemOptionDefine> optionDefines = this.systemOptionDao.getAllOptions();
            optionDefines = this.mergeDefaultOptions(optionDefines);
            return optionDefines;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<SystemOptionDefine> getOptionsByGroup(String groupKey) {
        try {
            if (groupKey == null) {
                ArrayList<SystemOptionDefine> res = new ArrayList<SystemOptionDefine>();
                List<SystemOptionDefine> allOptions = this.getGlobalOptions();
                for (SystemOptionDefine define : allOptions) {
                    if (define.getGroup() != null) continue;
                    res.add(define);
                }
                return res;
            }
            List<SystemOptionDefine> optionDefines = this.systemOptionDao.getOptionsByByGroup(groupKey);
            optionDefines = this.mergeDefaultOptionsByGroup(optionDefines, groupKey);
            return optionDefines;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void deleteSystemOption(String key) {
        this.deleteSystemOption(key, null, null);
    }

    @Override
    public void deleteSystemOption(String key, String taskKey) {
        this.deleteSystemOption(key, taskKey, null);
    }

    @Override
    public void deleteSystemOption(String key, String taskKey, String formSchemeKey) {
        try {
            Object oldValue = this.getObject(key, taskKey, formSchemeKey);
            this.systemOptionDao.deleteSystemOption(key, taskKey, formSchemeKey);
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            String cacheKey = this.getCacheKey(key, taskKey, formSchemeKey);
            caffeineCache.evict(cacheKey);
            Object value = this.getObject(key, taskKey, formSchemeKey);
            if (!(value == oldValue || value != null && value.equals(oldValue))) {
                this.iSystemOptionListenerService.publish(key, taskKey, formSchemeKey, value, oldValue);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void batchDeleteOptions(List<String> keys) {
        this.batchDeleteOptions(keys, null, null);
    }

    @Override
    public void batchDeleteOptions(List<String> keys, String taskKey) {
        this.batchDeleteOptions(keys, taskKey, null);
    }

    @Override
    public void batchDeleteOptions(List<String> keys, String taskKey, String formSchemeKey) {
        try {
            HashMap<String, Object> keyMap = null;
            if (keys != null) {
                keyMap = new HashMap<String, Object>(keys.size());
                for (String key : keys) {
                    Object oldValue = this.getObject(key, taskKey, formSchemeKey);
                    keyMap.put(key, oldValue);
                }
            }
            this.systemOptionDao.batchDeleteOptions(keys, taskKey, formSchemeKey);
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            caffeineCache.clear();
            if (keyMap != null) {
                for (Map.Entry entry : keyMap.entrySet()) {
                    String key = (String)entry.getKey();
                    Object oldValue = entry.getValue();
                    Object value = this.getObject(key);
                    if (value == oldValue || value != null && value.equals(oldValue)) continue;
                    this.iSystemOptionListenerService.publish(key, taskKey, formSchemeKey, value, oldValue);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void batchResetOptions(List<String> keys) {
        HashMap<String, Object> op = new HashMap<String, Object>();
        for (String key : keys) {
            HashMap<SystemOptionType, SystemOptionBase> systemOptionTypeSystemOptionBaseHashMap = this.defaultOptions.get(key);
            SystemOptionBase systemOptionBase = systemOptionTypeSystemOptionBaseHashMap.get((Object)SystemOptionType.SYSTEM_OPTION);
            op.put(key, systemOptionBase.getDefaultValue());
        }
        this.batchSetObjects(op);
    }

    @Override
    public boolean adjustOptionsInheritProperty(String key, String taskKey) {
        SystemOptionDefine option = null;
        try {
            option = this.systemOptionDao.getOptionByCode(key, taskKey);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return option == null;
    }

    @Override
    public boolean adjustOptionsInheritProperty(String key, String taskKey, String formSchemeKey) {
        SystemOptionDefine option = null;
        try {
            option = this.systemOptionDao.getOptionByCode(key, taskKey, formSchemeKey);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return option == null;
    }

    private String getCacheKey(String key, String taskKey, String formSchemeKey) {
        return SystemOptionCacheManagerConfiguration.getCacheKey(key, taskKey, formSchemeKey);
    }

    private SystemOptionBase getDefaultOption(String key, SystemOptionType optionType) {
        HashMap<SystemOptionType, SystemOptionBase> optionValue;
        if (this.defaultOptions.containsKey(key) && (optionValue = this.defaultOptions.get(key)).containsKey((Object)optionType)) {
            return optionValue.get((Object)optionType);
        }
        return null;
    }

    private void setDefaultOption(SystemOptionBase optionDefine) {
        HashMap<Object, Object> optionValue;
        if (!this.defaultOptions.containsKey(optionDefine.getKey())) {
            optionValue = new HashMap();
            this.defaultOptions.put(optionDefine.getKey(), optionValue);
        } else {
            optionValue = this.defaultOptions.get(optionDefine.getKey());
        }
        if (!optionValue.containsKey((Object)optionDefine.getOptionType())) {
            optionValue.put((Object)optionDefine.getOptionType(), optionDefine);
        }
    }

    @Override
    public HashMap<String, Object> batchGetObjects(List<String> keys) {
        try {
            return this.systemOptionDao.batchGetOptions(keys);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public HashMap<String, Object> batchGetObjects(List<String> keys, String taskKey) {
        try {
            return this.systemOptionDao.batchGetOptions(keys, taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public HashMap<String, Object> batchGetObjects(List<String> keys, String taskKey, String formSchemeKey) {
        try {
            HashMap<String, Object> resultValues = this.systemOptionDao.batchGetOptions(keys, taskKey, formSchemeKey);
            SystemOptionType optionType = this.getOptionType(taskKey, formSchemeKey);
            for (String optionKey : keys) {
                SystemOptionBase defaultOption;
                if (resultValues.containsKey(optionKey) || (defaultOption = this.getDefaultOption(optionKey, optionType)) == null) continue;
                resultValues.put(optionKey, defaultOption.getDefaultValue());
            }
            return resultValues;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void batchSetObjects(HashMap<String, Object> optionValues) {
        try {
            if (optionValues != null) {
                for (Map.Entry<String, Object> entry : optionValues.entrySet()) {
                    Object oldValue;
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == (oldValue = this.getObject(key)) || value != null && value.equals(oldValue)) continue;
                    this.iSystemOptionListenerService.publish(key, value, oldValue);
                }
            }
            this.systemOptionDao.batchSetOptions(optionValues, false);
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            caffeineCache.clear();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void setObject(HashMap<String, Object> optionValues, String taskKey) {
        try {
            if (optionValues != null) {
                for (Map.Entry<String, Object> entry : optionValues.entrySet()) {
                    Object oldValue;
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == (oldValue = this.getObject(key, taskKey)) || value != null && value.equals(oldValue)) continue;
                    this.iSystemOptionListenerService.publish(key, taskKey, null, value, oldValue);
                }
            }
            this.systemOptionDao.batchSetOptions(optionValues, taskKey, false);
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            caffeineCache.clear();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void setObject(HashMap<String, Object> optionValues, String taskKey, String formSchemeKey) {
        try {
            if (optionValues != null) {
                for (Map.Entry<String, Object> entry : optionValues.entrySet()) {
                    Object oldValue;
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == (oldValue = this.getObject(key, taskKey, formSchemeKey)) || value != null && value.equals(oldValue)) continue;
                    this.iSystemOptionListenerService.publish(key, taskKey, formSchemeKey, value, oldValue);
                }
            }
            this.systemOptionDao.batchSetOptions(optionValues, taskKey, formSchemeKey, false);
            NedisCache caffeineCache = this.cacheManager.getCache("SYSTEM_OPTION");
            caffeineCache.clear();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public SystemOptionDefine getGlobalOptionByKey(String key) {
        try {
            Optional<SystemOptionDefine> findOption;
            List<SystemOptionDefine> systemOptionDefines;
            SystemOptionDefine optionByCode = this.systemOptionDao.getOptionByCode(key);
            ArrayList<SystemOptionDefine> op = new ArrayList<SystemOptionDefine>();
            if (optionByCode != null) {
                op.add(optionByCode);
            }
            if ((systemOptionDefines = this.mergeDefaultOptionsByType(op, SystemOptionType.SYSTEM_OPTION)).size() > 0 && (findOption = systemOptionDefines.stream().filter(e -> e.getKey().equalsIgnoreCase(key)).findAny()).isPresent()) {
                return findOption.get();
            }
            return null;
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
            return null;
        }
    }

    private List<SystemOptionDefine> mergeDefaultOptions(List<SystemOptionDefine> optionDefines) {
        HashMap optionItems;
        ArrayList<SystemOptionDefine> resultValues = new ArrayList<SystemOptionDefine>();
        HashMap<String, HashMap> optionCache = new HashMap<String, HashMap>();
        for (SystemOptionDefine systemOptionDefine : optionDefines) {
            if (!optionCache.containsKey(systemOptionDefine.getKey())) {
                optionItems = new HashMap();
                optionCache.put(systemOptionDefine.getKey(), optionItems);
            } else {
                optionItems = (HashMap)optionCache.get(systemOptionDefine.getKey());
            }
            if (optionItems.containsKey((Object)systemOptionDefine.getOptionType())) continue;
            optionItems.put(systemOptionDefine.getOptionType(), systemOptionDefine);
        }
        for (Map.Entry entry : this.defaultOptions.entrySet()) {
            optionItems = (HashMap)entry.getValue();
            for (Map.Entry optionDefine : optionItems.entrySet()) {
                SystemOptionDefineImpl copyOption = new SystemOptionDefineImpl((SystemOptionBase)optionDefine.getValue());
                if (optionCache.containsKey(entry.getKey())) {
                    HashMap exsitItems = (HashMap)optionCache.get(entry.getKey());
                    if (exsitItems.containsKey(optionDefine.getKey())) {
                        SystemOptionDefine exsitItem = (SystemOptionDefine)exsitItems.get(optionDefine.getKey());
                        copyOption.setValue(exsitItem.getValue());
                        copyOption.setTaskKey(exsitItem.getTaskKey());
                        copyOption.setFormSchemeKey(exsitItem.getFormSchemeKey());
                        resultValues.add(copyOption);
                        continue;
                    }
                    resultValues.add(copyOption);
                    continue;
                }
                resultValues.add(copyOption);
            }
        }
        return resultValues;
    }

    private List<SystemOptionDefine> mergeDefaultOptionsByGroup(List<SystemOptionDefine> optionDefines, String group) {
        ArrayList<SystemOptionDefine> resultValues = new ArrayList<SystemOptionDefine>();
        HashMap<String, SystemOptionDefine> optionCache = new HashMap<String, SystemOptionDefine>();
        for (SystemOptionDefine systemOptionDefine : optionDefines) {
            if (optionCache.containsKey(systemOptionDefine.getKey())) continue;
            optionCache.put(systemOptionDefine.getKey(), systemOptionDefine);
        }
        for (Map.Entry entry : this.defaultOptions.entrySet()) {
            HashMap value = (HashMap)entry.getValue();
            SystemOptionBase defaultOption = (SystemOptionBase)value.get((Object)SystemOptionType.SYSTEM_OPTION);
            if (defaultOption == null) continue;
            if (optionCache.containsKey(defaultOption.getKey())) {
                SystemOptionDefine systemOptionDefine = (SystemOptionDefine)optionCache.get(defaultOption.getKey());
                SystemOptionDefineImpl copyOption = new SystemOptionDefineImpl(defaultOption);
                copyOption.setGroup(group);
                copyOption.setValue(systemOptionDefine.getValue());
                resultValues.add(copyOption);
                continue;
            }
            String sysGroup = defaultOption.getGroup();
            if (group == null || !group.equals(sysGroup)) continue;
            resultValues.add(new SystemOptionDefineImpl(defaultOption));
        }
        return resultValues;
    }

    private List<SystemOptionDefine> mergeDefaultOptionsByType(List<SystemOptionDefine> optionDefines, SystemOptionType optionType) {
        ArrayList<SystemOptionDefine> resultValues = new ArrayList<SystemOptionDefine>();
        HashMap<String, SystemOptionDefine> optionCache = new HashMap<String, SystemOptionDefine>();
        for (SystemOptionDefine systemOptionDefine : optionDefines) {
            if (optionCache.containsKey(systemOptionDefine.getKey())) continue;
            optionCache.put(systemOptionDefine.getKey(), systemOptionDefine);
        }
        for (Map.Entry entry : this.defaultOptions.entrySet()) {
            HashMap defaultValue = (HashMap)entry.getValue();
            if (!defaultValue.containsKey((Object)optionType)) continue;
            SystemOptionBase defaultOption = (SystemOptionBase)defaultValue.get((Object)optionType);
            SystemOptionDefineImpl copyOption = new SystemOptionDefineImpl(defaultOption);
            if (optionCache.containsKey(defaultOption.getKey())) {
                SystemOptionDefine optionDefine = (SystemOptionDefine)optionCache.get(defaultOption.getKey());
                copyOption.setValue(optionDefine.getValue());
                copyOption.setTaskKey(optionDefine.getTaskKey());
                copyOption.setFormSchemeKey(optionDefine.getFormSchemeKey());
                copyOption.setGroup(optionDefine.getGroup());
            }
            resultValues.add(copyOption);
        }
        return resultValues;
    }

    private SystemOptionType getOptionType(String taskKey, String formSchemeKey) {
        if (taskKey == null && formSchemeKey == null) {
            return SystemOptionType.SYSTEM_OPTION;
        }
        if (taskKey != null && formSchemeKey == null) {
            return SystemOptionType.TASK_OPTION;
        }
        return SystemOptionType.FORMSCHEME_OPTION;
    }

    @Override
    public List<SystemOptionDefine> getSystemOption(List<String> optionKeys) {
        if (optionKeys == null) {
            return null;
        }
        ArrayList<SystemOptionDefine> optionDefineImplList = new ArrayList<SystemOptionDefine>();
        for (String optionKey : optionKeys) {
            for (Map.Entry<String, HashMap<SystemOptionType, SystemOptionBase>> optionEntry : this.defaultOptions.entrySet()) {
                String key = optionEntry.getKey();
                if (!optionKey.equals(key)) continue;
                HashMap<SystemOptionType, SystemOptionBase> optionItems = optionEntry.getValue();
                for (Map.Entry<SystemOptionType, SystemOptionBase> optionDefine : optionItems.entrySet()) {
                    SystemOptionType optionKeyType = optionDefine.getKey();
                    if (!optionKeyType.equals((Object)SystemOptionType.SYSTEM_OPTION)) continue;
                    SystemOptionDefineImpl copyOption = new SystemOptionDefineImpl(optionDefine.getValue());
                    copyOption.setValue(this.getObject(key));
                    optionDefineImplList.add(copyOption);
                }
            }
        }
        return optionDefineImplList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, SystemOptionBase> systemOptions = this.applicationContext.getBeansOfType(SystemOptionBase.class);
        for (Map.Entry<String, SystemOptionBase> optionDefine : systemOptions.entrySet()) {
            this.registerSystemOption(optionDefine.getValue());
        }
    }
}

