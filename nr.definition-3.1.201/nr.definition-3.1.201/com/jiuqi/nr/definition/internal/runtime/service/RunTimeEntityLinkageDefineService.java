/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.EntityLinkageDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeEntityLinkageDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeEntityLinkageDefineService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.Objects;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class RunTimeEntityLinkageDefineService
implements IRunTimeEntityLinkageDefineService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private final RunTimeEntityLinkageDefineDao entityLinkageDefineDao;
    private final RuntimeDefinitionCache<EntityLinkageDefine> cache;

    public RunTimeEntityLinkageDefineService(RunTimeEntityLinkageDefineDao entityLinkageDefineDao, NedisCacheProvider cacheProvider) {
        if (entityLinkageDefineDao == null) {
            throw new IllegalArgumentException("'entityLinkageDefineDao' must not be null.");
        }
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.entityLinkageDefineDao = entityLinkageDefineDao;
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), EntityLinkageDefine.class);
    }

    @Override
    public EntityLinkageDefine queryEntityLinkageDefine(String elKey) {
        if (null == elKey) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.cache.getObject(elKey);
        if (Objects.isNull(valueWrapper)) {
            return (EntityLinkageDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
                Cache.ValueWrapper valueWrapp = this.cache.getObject(elKey);
                if (Objects.nonNull(valueWrapp)) {
                    return (EntityLinkageDefine)valueWrapp.get();
                }
                EntityLinkageDefine entityLinkageDefine = this.entityLinkageDefineDao.getDefineByKey(elKey);
                if (null == entityLinkageDefine) {
                    this.cache.putNullObject(elKey);
                } else {
                    this.cache.putObject((IBaseMetaItem)entityLinkageDefine);
                }
                return entityLinkageDefine;
            });
        }
        return (EntityLinkageDefine)valueWrapper.get();
    }

    public void onClearCache() {
        this.cache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        this.cache.clear();
    }
}

