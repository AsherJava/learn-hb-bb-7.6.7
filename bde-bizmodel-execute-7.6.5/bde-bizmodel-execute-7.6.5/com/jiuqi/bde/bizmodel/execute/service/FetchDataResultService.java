/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 */
package com.jiuqi.bde.bizmodel.execute.service;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.dto.FetchFloatRowDTO;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import java.util.List;
import java.util.Map;

public interface FetchDataResultService {
    public void insertFixedResult(FetchResultDim var1, List<FetchResult> var2);

    public void insertFloatRowResult(FetchResultDim var1, FetchFloatRowDTO var2);

    public void insertFloatColResult(FetchResultDim var1, List<FetchResult> var2);

    public List<Map<String, Object>> getFloatRowResultsWithType(FetchResultDim var1);

    public FetchResultDTO getFetchResult(FetchDataRequestDTO var1);

    public Map<String, Object> getFixedResults(FetchDataRequestDTO var1);

    public FloatRegionResultDTO getFloatResults(FetchDataRequestDTO var1);

    public Integer countByTableName(String var1);

    public void truncateByTableName(String var1);

    public void cleanResultTable(String var1, String var2, String var3, Integer var4, boolean var5);
}

