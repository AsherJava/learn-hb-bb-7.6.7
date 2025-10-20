/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.fetch.impl.result.service;

import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import java.util.List;

public interface FetchResultMappingService {
    public List<Integer> getRouteNumber();

    public FetchResultMappingEO getMappingEOByRouteNum(Integer var1);

    public int changeRouteStart(Integer var1);

    public int changeRouteLock(Integer var1);

    public int changeRouteStop(Integer var1);

    public int updateRouteStatus(FetchResultMappingEO var1);

    public void insertFetchResultMapping(List<FetchResultMappingEO> var1);
}

