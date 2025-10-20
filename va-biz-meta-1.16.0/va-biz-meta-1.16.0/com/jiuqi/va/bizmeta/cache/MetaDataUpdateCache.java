/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bizmeta.cache;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;

public class MetaDataUpdateCache {
    public static final String META_DATA_UPDATE_CACHE_KEY = "META_DATA_UPDATE_CACHE_KEY";
    public static final String META_DATA_UPDATE_CONTROLLER_CACHE_KEY = "META_DATA_UPDATE_CONTROLLER_CACHE_KEY";
    public static final String META_DATA_DEPLOY_CACHE_KEY = "META_DATA_DEPLOY_CACHE_KEY";
    private static final String META_DATA_UPDATE_PROGRESS_KEY = "META_DATA_UPDATE_PROGRESS_KEY";
    private static StringRedisTemplate redisTemplate;
    private static final MetaDataBatchUpdateProgress metaDataBatchUpdateProgress;

    private static StringRedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
        }
        return redisTemplate;
    }

    private MetaDataUpdateCache() {
    }

    public static MetaDataBatchUpdateProgress getMetaDataBatchUpdateProgress() {
        if (!EnvConfig.getRedisEnable()) {
            return metaDataBatchUpdateProgress;
        }
        return MetaDataUpdateCache.getRedisTemplate().opsForValue().get((Object)META_DATA_UPDATE_PROGRESS_KEY) == null ? metaDataBatchUpdateProgress : (MetaDataBatchUpdateProgress)JSONUtil.parseObject((String)((String)MetaDataUpdateCache.getRedisTemplate().opsForValue().get((Object)META_DATA_UPDATE_PROGRESS_KEY)), MetaDataBatchUpdateProgress.class);
    }

    public static void setMetaDataBatchUpdateProgress(MetaDataBatchUpdateProgress metaDataBatchUpdateProgress) {
        if (!EnvConfig.getRedisEnable()) {
            MetaDataUpdateCache.metaDataBatchUpdateProgress.setTotal(metaDataBatchUpdateProgress.getTotal());
            MetaDataUpdateCache.metaDataBatchUpdateProgress.setCurrent(metaDataBatchUpdateProgress.getCurrent());
            MetaDataUpdateCache.metaDataBatchUpdateProgress.setErrorMetaData(metaDataBatchUpdateProgress.getErrorMetaData());
            MetaDataUpdateCache.metaDataBatchUpdateProgress.setSuccessMetaData(metaDataBatchUpdateProgress.getSuccessMetaData());
            MetaDataUpdateCache.metaDataBatchUpdateProgress.setUpdating(metaDataBatchUpdateProgress.isUpdating());
        } else {
            MetaDataUpdateCache.getRedisTemplate().opsForValue().set((Object)META_DATA_UPDATE_PROGRESS_KEY, (Object)JSONUtil.toJSONString((Object)metaDataBatchUpdateProgress), 60L, TimeUnit.SECONDS);
        }
    }

    public static void clearCache() {
        if (!EnvConfig.getRedisEnable()) {
            metaDataBatchUpdateProgress.setTotal(0);
            metaDataBatchUpdateProgress.setCurrent(0);
            metaDataBatchUpdateProgress.setErrorMetaData(new ArrayList());
            metaDataBatchUpdateProgress.setSuccessMetaData(new ArrayList());
            metaDataBatchUpdateProgress.setUpdating(false);
        } else {
            MetaDataUpdateCache.getRedisTemplate().delete((Object)META_DATA_UPDATE_PROGRESS_KEY);
        }
    }

    static {
        metaDataBatchUpdateProgress = new MetaDataBatchUpdateProgress();
    }
}

