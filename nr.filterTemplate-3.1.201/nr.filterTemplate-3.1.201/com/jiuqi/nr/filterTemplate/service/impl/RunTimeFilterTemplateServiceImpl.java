/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFilterTemplateService
 */
package com.jiuqi.nr.filterTemplate.service.impl;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFilterTemplateService;
import com.jiuqi.nr.filterTemplate.dao.FilterTemplateDao;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RunTimeFilterTemplateServiceImpl
implements IRunTimeFilterTemplateService,
RuntimeDefinitionRefreshListener {
    private final RuntimeDefinitionCache<FilterTemplateDO> cache;
    @Autowired
    private FilterTemplateDao filterTemplateDao;
    private final RuntimeFilterTemplateCacheLoader cacheLoader = new RuntimeFilterTemplateCacheLoader();

    @Autowired
    public RunTimeFilterTemplateServiceImpl(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), FilterTemplateDO.class);
    }

    public void onClearCache() {
        this.cache.clear();
    }

    public RunTimeEntityViewDefineImpl getFilterTemplate(String key) {
        FilterTemplateDO entityViewDefine = this.getEntityView(key);
        if (entityViewDefine != null) {
            RunTimeEntityViewDefineImpl define = new RunTimeEntityViewDefineImpl();
            define.setEntityId(entityViewDefine.getEntityID());
            define.setRowFilterExpression(entityViewDefine.getFilterContent());
            return define;
        }
        return null;
    }

    public void refreshView(String key) {
        this.cache.removeObjects(Arrays.asList(key));
    }

    public FilterTemplateDO getEntityView(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.cache.getObject(key);
        if (Objects.nonNull(valueWrapper)) {
            return (FilterTemplateDO)valueWrapper.get();
        }
        return (FilterTemplateDO)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.cache.getObject(key);
            if (Objects.nonNull(revalueWrapper)) {
                return (FilterTemplateDO)revalueWrapper.get();
            }
            FilterTemplateDO entityViewDefine = this.cacheLoader.loadObjectCacheByEntityView(key);
            if (entityViewDefine == null) {
                this.cache.putNullObject(key);
            }
            return entityViewDefine;
        });
    }

    private class RuntimeFilterTemplateCacheLoader {
        private RuntimeFilterTemplateCacheLoader() {
        }

        private FilterTemplateDO loadObjectCacheByEntityView(String entityViewKey) {
            FilterTemplateDO entityViewDefine = RunTimeFilterTemplateServiceImpl.this.filterTemplateDao.get(entityViewKey);
            if (entityViewDefine != null) {
                RunTimeFilterTemplateServiceImpl.this.cache.putObject((IBaseMetaItem)entityViewDefine);
                return entityViewDefine;
            }
            return null;
        }
    }
}

