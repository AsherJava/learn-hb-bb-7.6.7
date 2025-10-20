/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 */
package com.jiuqi.bde.fetch.impl.request.service;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import java.util.List;
import java.util.Map;

public interface FetchDataRequestService {
    public Boolean doInit(FetchInitTaskDTO var1);

    public FetchResultDTO doFetch(FetchRequestDTO var1);

    public List<Map<String, Object>> penetrateTable(FetchRequestDTO var1);
}

