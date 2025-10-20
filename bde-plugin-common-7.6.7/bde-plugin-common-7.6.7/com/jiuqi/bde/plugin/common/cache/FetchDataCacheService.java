/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 */
package com.jiuqi.bde.plugin.common.cache;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheQueryCondi;

public interface FetchDataCacheService {
    public String getCacheTableName();

    public boolean cacheValid(FetchDataCacheKey var1);

    public void putCache(FetchDataCacheKey var1, FetchData var2);

    public FetchData getCache(FetchDataCacheKey var1, FetchDataCacheQueryCondi var2);

    public FetchFloatRowResult getCacheByAss(FetchDataCacheKey var1, FetchFieldAndWhereSql var2);

    public void removeCache(FetchDataCacheKey var1);

    default public String getBizDataModelEnumCode() {
        return null;
    }
}

