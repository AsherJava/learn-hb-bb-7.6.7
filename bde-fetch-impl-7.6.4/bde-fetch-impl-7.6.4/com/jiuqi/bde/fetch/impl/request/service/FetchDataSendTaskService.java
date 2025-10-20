/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO
 *  com.jiuqi.bde.common.dto.FetchDataExecuteContext
 */
package com.jiuqi.bde.fetch.impl.request.service;

import com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO;
import com.jiuqi.bde.common.dto.FetchDataExecuteContext;
import java.util.Map;

public interface FetchDataSendTaskService {
    public void sendFetchTask(FetchDataRequestDTO var1);

    public Map<String, FetchDataExecuteContext> buildFetchCtxMap(FetchDataRequestDTO var1);
}

