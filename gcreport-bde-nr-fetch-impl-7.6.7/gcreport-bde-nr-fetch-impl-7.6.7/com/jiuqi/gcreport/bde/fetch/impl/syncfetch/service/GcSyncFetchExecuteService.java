/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetch.client.dto.FetchExecuteMessage
 */
package com.jiuqi.gcreport.bde.fetch.impl.syncfetch.service;

import com.jiuqi.gcreport.bde.fetch.client.dto.FetchExecuteMessage;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;

public interface GcSyncFetchExecuteService {
    public void doFetchExecute(FetchExecuteMessage var1, GcFetchItemTaskLogEO var2);
}

