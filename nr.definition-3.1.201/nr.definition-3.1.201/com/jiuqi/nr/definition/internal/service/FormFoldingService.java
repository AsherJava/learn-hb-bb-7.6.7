/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.internal.dao.FormFoldingDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FormFoldingService
implements RuntimeDefinitionRefreshListener {
    @Autowired
    private FormFoldingDao formFoldingDao;
    private final RuntimeDefinitionCache<FormFoldingDefine> cache;

    @Autowired
    public FormFoldingService(NedisCacheProvider cacheProvider) {
        this.cache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), FormFoldingDefine.class);
    }

    public FormFoldingDefine getByKey(String key) {
        return this.formFoldingDao.getFormFoldingDefineByKey(key);
    }

    public List<FormFoldingDefine> getByFormKey(String formKey) {
        if (!StringUtils.hasText(formKey)) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(formKey);
        if (Objects.nonNull(valueWrapper)) {
            return (List)valueWrapper.get();
        }
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper value = this.cache.getKVIndexValue(formKey);
            if (Objects.nonNull(value)) {
                return (List)value.get();
            }
            return this.loadCache(formKey);
        });
    }

    private List<FormFoldingDefine> loadCache(String formKey) {
        List<FormFoldingDefine> definesInDb = this.formFoldingDao.getFormFoldingDefineByFormKey(formKey);
        if (CollectionUtils.isEmpty(definesInDb)) {
            this.cache.putKVIndex(formKey, new ArrayList());
            return Collections.emptyList();
        }
        this.cache.putKVIndex(formKey, definesInDb);
        return definesInDb;
    }

    public void onClearCache() {
        this.cache.clear();
    }
}

