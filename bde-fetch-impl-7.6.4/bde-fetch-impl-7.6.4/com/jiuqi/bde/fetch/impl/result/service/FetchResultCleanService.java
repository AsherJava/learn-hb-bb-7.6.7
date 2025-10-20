/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 */
package com.jiuqi.bde.fetch.impl.result.service;

import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import com.jiuqi.bi.logging.ILogger;

public interface FetchResultCleanService {
    public void doClean(ILogger var1);

    public String doCleanEachRoute(FetchResultMappingEO var1);
}

