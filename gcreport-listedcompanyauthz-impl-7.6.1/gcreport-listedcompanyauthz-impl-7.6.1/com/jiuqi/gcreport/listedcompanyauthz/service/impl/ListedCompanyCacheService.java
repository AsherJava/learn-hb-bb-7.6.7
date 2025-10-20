/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gcreport.listedcompanyauthz.service.impl;

import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyAuthzDao;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyDao;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ListedCompanyCacheService {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FListedCompanyAuthzDao companyAuthzDao;
    @Autowired
    private FListedCompanyDao listedCompanyDao;
    private NedisCacheManager manager;

    public void publishOrgCacheEvent(String ... userNames) {
        this.applicationContext.publishEvent(new GcOrgDataCacheClearEvent());
        if (this.manager != null) {
            if (userNames == null || userNames.length < 1) {
                this.manager.getCache("cache_key_listedcompany").clear();
            } else {
                NedisCache cache = this.manager.getCache("cache_key_listedcompany_authz");
                for (String userName : userNames) {
                    cache.evict(userName);
                }
            }
        }
    }

    public List<String> getListedCompany() {
        Cache.ValueWrapper valueWrapper;
        List datas = null;
        NedisCache cache = null;
        if (this.manager != null && (valueWrapper = (cache = this.manager.getCache("cache_key_listedcompany")).get("cache_key_listedcompany")) != null) {
            datas = (List)valueWrapper.get();
        }
        if (datas == null) {
            datas = this.listedCompanyDao.queryAllListedCompany();
            if (cache != null) {
                cache.put("cache_key_listedcompany", datas);
            }
        }
        return datas;
    }

    public List<String> getListedCompanyAuthzByUser(String userName) {
        Cache.ValueWrapper valueWrapper;
        List datas = null;
        NedisCache cache = null;
        if (this.manager != null && (valueWrapper = (cache = this.manager.getCache("cache_key_listedcompany_authz")).get(userName)) != null) {
            datas = (List)valueWrapper.get();
        }
        if (datas == null) {
            datas = this.companyAuthzDao.queryAuthzOrgByUser(userName);
            if (cache != null) {
                cache.put(userName, datas);
            }
        }
        return datas;
    }
}

