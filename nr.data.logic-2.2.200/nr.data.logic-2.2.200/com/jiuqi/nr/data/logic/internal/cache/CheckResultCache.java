/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.data.logic.internal.cache.CheckResultCacheObj;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class CheckResultCache
implements Serializable {
    public static final String NAME = "REMOTE_NR";
    private static final long serialVersionUID = 9076077315732343459L;
    private static final Logger logger = LoggerFactory.getLogger(CheckResultCache.class);
    private NedisCacheManager cacheManager;
    private static final String CACHE_NAME = "CHECK_RESULT_CACHE";

    @Autowired
    public void setCacheManager(NedisCacheProvider sessionCacheProvider) {
        this.cacheManager = sessionCacheProvider.getCacheManager(NAME);
    }

    public void saveResult(String checkCacheKey, DataSet<ColumnInfo> dataSet, String actionID) {
        if (StringUtils.isNotEmpty((String)checkCacheKey)) {
            CheckResultCacheObj checkResultCacheObj;
            NedisCache cache = this.cacheManager.getCache(CACHE_NAME);
            Cache.ValueWrapper valueWrapper = cache.get(checkCacheKey);
            if (valueWrapper != null) {
                checkResultCacheObj = (CheckResultCacheObj)valueWrapper.get();
                if (checkResultCacheObj == null) {
                    checkResultCacheObj = new CheckResultCacheObj();
                    checkResultCacheObj.setActionID(actionID);
                    checkResultCacheObj.setDataSet(dataSet);
                } else if (checkResultCacheObj.getActionID().equals(actionID)) {
                    try {
                        checkResultCacheObj.getDataSet().add(dataSet);
                    }
                    catch (DataSetException e) {
                        logger.error("\u540c\u6279\u6b21\u5168\u5ba1\u7ed3\u679c\u5185\u5b58\u5b58\u50a8\u5408\u5e76\u5f02\u5e38\uff1a" + e.getMessage(), e);
                    }
                } else {
                    checkResultCacheObj.setActionID(actionID);
                    checkResultCacheObj.setDataSet(dataSet);
                }
            } else {
                checkResultCacheObj = new CheckResultCacheObj();
                checkResultCacheObj.setActionID(actionID);
                checkResultCacheObj.setDataSet(dataSet);
            }
            cache.put(checkCacheKey, (Object)checkResultCacheObj);
        }
    }

    public DataSet<ColumnInfo> getResult(String checkCacheKey) {
        CheckResultCacheObj checkResultCacheObj;
        if (StringUtils.isEmpty((String)checkCacheKey)) {
            return null;
        }
        NedisCache cache = this.cacheManager.getCache(CACHE_NAME);
        Cache.ValueWrapper valueWrapper = cache.get(checkCacheKey);
        if (valueWrapper != null && (checkResultCacheObj = (CheckResultCacheObj)valueWrapper.get()) != null) {
            return checkResultCacheObj.getDataSet();
        }
        return null;
    }

    public void removeCacheObj(String allCheckCacheKey) {
        NedisCache cache = this.cacheManager.getCache(CACHE_NAME);
        cache.evict(allCheckCacheKey);
    }
}

