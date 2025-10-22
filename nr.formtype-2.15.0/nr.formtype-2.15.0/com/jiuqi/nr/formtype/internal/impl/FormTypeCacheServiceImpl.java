/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.internal.dao.FormTypeDao;
import com.jiuqi.nr.formtype.service.IFormTypeCacheService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class FormTypeCacheServiceImpl
implements IFormTypeCacheService {
    private static final String CACHE_KEY_ALL_FORMTYPE_CODE = "ALL_FORMTYPE_CODE";
    @Autowired
    private FormTypeDao formTypeDao;
    private NedisCache cache;

    @Autowired
    public void setNedisCache(NedisCacheProvider cacheProvider) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = cacheProvider.getCacheManager("nr:param:formtype").getCache(FormTypeCacheServiceImpl.class.getSimpleName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> getAllFormTypeCodes() {
        Set allCodeSet;
        Cache.ValueWrapper valueWrapper = this.cache.get(CACHE_KEY_ALL_FORMTYPE_CODE);
        if (null != valueWrapper) {
            return (Set)valueWrapper.get();
        }
        FormTypeCacheServiceImpl formTypeCacheServiceImpl = this;
        synchronized (formTypeCacheServiceImpl) {
            valueWrapper = this.cache.get(CACHE_KEY_ALL_FORMTYPE_CODE);
            if (null == valueWrapper) {
                List<FormTypeDefine> allFormType = this.formTypeDao.getAll();
                allCodeSet = allFormType.stream().map(FormTypeDefine::getCode).collect(Collectors.toSet());
                this.cache.put(CACHE_KEY_ALL_FORMTYPE_CODE, allCodeSet);
            } else {
                allCodeSet = (Set)valueWrapper.get();
            }
        }
        return allCodeSet;
    }

    @Override
    public boolean isFormType(String formTypeCode) {
        Set<String> allCodeSet = this.getAllFormTypeCodes();
        return allCodeSet.contains(formTypeCode);
    }

    @Override
    public void cleanCache() {
        this.cache.clear();
    }
}

