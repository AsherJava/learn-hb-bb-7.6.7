/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.event.DimensionFilterChangeEvent;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.DesignDimensionFilterDao;
import com.jiuqi.nr.definition.internal.impl.DesignDimensionFilterImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IDimensionFilterService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class RunTimeDimensionFilterService
implements IDimensionFilterService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener,
ApplicationListener<DimensionFilterChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(RunTimeDimensionFilterService.class);
    private final RuntimeDefinitionCache<IDimensionFilter> cache;
    private final DesignDimensionFilterDao designDimensionFilterDao;
    @Autowired
    private IRuntimeTaskService runtimeTaskService;
    @Autowired
    private IRuntimeFormSchemeService runtimeFormSchemeService;

    public RunTimeDimensionFilterService(NedisCacheProvider cacheProvider, DesignDimensionFilterDao designDimensionFilterDao) {
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), IDimensionFilter.class);
        this.designDimensionFilterDao = designDimensionFilterDao;
    }

    @Override
    public List<IDimensionFilter> getByFormSchemeKey(String formSchemeKey) {
        if (!StringUtils.hasLength(formSchemeKey)) {
            return Collections.emptyList();
        }
        FormSchemeDefine formScheme = this.runtimeFormSchemeService.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return Collections.emptyList();
        }
        return this.getByTaskKey(formScheme.getTaskKey());
    }

    public void onClearCache() {
        log.debug("\u6e05\u7a7a\u60c5\u666f\u8fc7\u6ee4\u6761\u4ef6\u7f13\u5b58");
        this.cache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        DeployItem formScheme = deployParams.getFormScheme();
        if (formScheme != null) {
            Set keys = formScheme.getRuntimeUninDesignTimeKeys();
            this.cache.synchronizedRun("dimensionFilter", () -> this.cache.removeIndexs((Collection)keys));
        }
    }

    @Override
    public List<IDimensionFilter> getByTaskKey(String taskKey) {
        if (taskKey == null) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(taskKey);
        if (Objects.nonNull(valueWrapper)) {
            return (List)valueWrapper.get();
        }
        return (List)RuntimeDefinitionCacheObject.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper kvIndexValue = this.cache.getKVIndexValue(taskKey);
            if (Objects.nonNull(kvIndexValue)) {
                return (List)kvIndexValue.get();
            }
            return this.loadCacheByTask(taskKey);
        });
    }

    private List<IDimensionFilter> loadCacheByTask(String taskKey) {
        TaskDefine taskDefine;
        String dims;
        List<DesignDimensionFilter> list = this.designDimensionFilterDao.getByTaskKey(taskKey);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<DesignDimensionFilter>();
        }
        if (StringUtils.hasLength(dims = (taskDefine = this.runtimeTaskService.queryTaskDefine(taskKey)).getDims())) {
            Set entitySet = list.stream().map(IDimensionFilter::getEntityId).collect(Collectors.toSet());
            String[] splitKey = dims.split(";");
            for (int i = 0; i < splitKey.length; ++i) {
                if (entitySet.contains(splitKey[i])) continue;
                DesignDimensionFilterImpl dimensionFilter = new DesignDimensionFilterImpl(taskKey, splitKey[i]);
                list.add(i, dimensionFilter);
            }
        }
        this.cache.putKVIndex(taskKey, list);
        return new ArrayList<IDimensionFilter>(list);
    }

    @Override
    public IDimensionFilter getByTaskAndEntityId(String taskKey, String entityId) {
        if (!StringUtils.hasLength(taskKey) || !StringUtils.hasLength(entityId)) {
            return null;
        }
        List<IDimensionFilter> list = this.getByTaskKey(taskKey);
        return list.stream().filter(l -> entityId.equals(l.getEntityId())).findFirst().orElse(new DesignDimensionFilterImpl(taskKey, entityId));
    }

    @Override
    public void onApplicationEvent(DimensionFilterChangeEvent event) {
        List<String> keys = event.getKeys();
        log.debug("\u6536\u5230\u4e8b\u4ef6\uff0c\u7c7b\u578b\uff1a{}\uff0c\u53c2\u6570\uff1a{}", (Object)event.getType(), (Object)keys);
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        this.cache.removeIndexs(event.getKeys());
        log.debug("\u6e05\u7a7a\u60c5\u666f\u8fc7\u6ee4\u6761\u4ef6\u7f13\u5b58\uff0c\u4efb\u52a1key\uff1a{}", (Object)event.getKeys());
    }
}

