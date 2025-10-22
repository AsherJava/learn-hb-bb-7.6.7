/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.impl.WorkflowSettingServiceImpl;
import com.jiuqi.util.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class WorkflowSettingServiceCache
extends WorkflowSettingServiceImpl {
    private static final Logger logger = LogFactory.getLogger(WorkflowSettingServiceCache.class);
    private static final String NULL = "NULL";
    public static final String CACHE_NAME = "nr.bpm.setting";
    private NedisCache _getCache;

    public WorkflowSettingServiceCache(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("default_rediscaffeine");
        this._getCache = cacheManager.getCache(CACHE_NAME);
    }

    @Override
    public WorkflowSettingDefine getWorkflowDefineByFormSchemeKey(String formSchemeKey) {
        Cache.ValueWrapper valueWrapper;
        String cacheKey = formSchemeKey;
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            cacheKey = NULL;
            formSchemeKey = null;
        }
        if ((valueWrapper = this._getCache.get(cacheKey)) != null) {
            WorkflowSettingDefine define = (WorkflowSettingDefine)valueWrapper.get();
            return define.getId() != null ? define : null;
        }
        WorkflowSettingDefine workflowSettingDefine = super.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowSettingDefine == null) {
            this._getCache.put(cacheKey, (Object)new WorkflowSettingDefine());
        } else {
            this._getCache.put(cacheKey, (Object)workflowSettingDefine);
        }
        return workflowSettingDefine != null && workflowSettingDefine.getId() != null ? workflowSettingDefine : null;
    }
}

