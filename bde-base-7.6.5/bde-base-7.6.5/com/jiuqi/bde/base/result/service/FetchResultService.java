/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 */
package com.jiuqi.bde.base.result.service;

import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import java.util.Map;

public interface FetchResultService {
    public FetchResultDTO getFetchResult(FetchRequestDTO var1);

    public Map<String, Object> getFixedResults(FetchRequestDTO var1);

    public FloatRegionResultDTO getFloatResults(FetchRequestDTO var1);

    public void cleanResultTable(String var1, String var2, String var3, Integer var4, boolean var5);
}

