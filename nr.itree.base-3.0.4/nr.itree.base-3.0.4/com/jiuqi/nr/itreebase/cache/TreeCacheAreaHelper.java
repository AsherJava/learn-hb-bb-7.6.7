/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.file.FileService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaHelper;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaType;
import com.jiuqi.nr.itreebase.cache.TreeCacheAreaOfDataBase;
import com.jiuqi.nr.itreebase.cache.TreeCacheAreaOfOSS;
import com.jiuqi.nr.itreebase.cache.TreeCacheContextOfRedis;
import com.jiuqi.nr.itreebase.cache.TreeCacheFilterSetOfRedis;
import com.jiuqi.nr.itreebase.lock.ISourceLockManager;
import java.io.Serializable;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TreeCacheAreaHelper
implements ITreeCacheAreaHelper {
    @Resource
    private FileService fileService;
    @Resource
    private NedisCacheProvider nedisCacheProvider;
    @Resource
    private ISourceLockManager sourceLockManager;

    @Override
    public ITreeCacheArea getCacheArea(ITreeCacheAreaType type) {
        switch (type) {
            case REDIS_CACHE_CONTEXT_DATA: {
                return new TreeCacheContextOfRedis(this.nedisCacheProvider);
            }
            case REDIS_CACHE_FILTER_SET: {
                return new TreeCacheFilterSetOfRedis(this.nedisCacheProvider);
            }
            case OSS_FILE: {
                return new TreeCacheAreaOfOSS(this.sourceLockManager);
            }
            case DATABASE: {
                return new TreeCacheAreaOfDataBase();
            }
        }
        return new TreeCacheFilterSetOfRedis(this.nedisCacheProvider);
    }

    @Override
    public boolean contains(ITreeCacheAreaType type, String sourceId) {
        return this.getCacheArea(type).contains(sourceId);
    }

    @Override
    public <T extends Serializable> T getCacheData(ITreeCacheAreaType type, String sourceId, Class<T> clazz) {
        return this.getCacheArea(type).getCacheData(sourceId, clazz);
    }

    @Override
    public <T extends Serializable> void putCacheData(ITreeCacheAreaType type, String sourceId, T contentData) {
        this.getCacheArea(type).putCacheData(sourceId, contentData);
    }
}

