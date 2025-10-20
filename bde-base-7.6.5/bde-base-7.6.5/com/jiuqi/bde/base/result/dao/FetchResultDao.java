/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 */
package com.jiuqi.bde.base.result.dao;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.base.intf.FloatColResultVO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FetchResultDao {
    public Map<String, Object> getSyncFixedResultsByFetchTest(String var1, String var2, Integer var3);

    public Map<String, Object> getSyncFixedResults(String var1, String var2, Set<String> var3, Integer var4);

    public Map<String, Object> getFixedSumResults(String var1, String var2, String var3, Integer var4);

    public Map<String, Map<String, Object>> getFixedDetailResults(String var1, String var2, String var3, List<String> var4, Integer var5);

    public FetchFloatRowResult getFloatRowDatasMap(FetchResultDim var1);

    public FloatColResultVO getFloatColData(FetchResultDim var1);

    public void deleteFloatRowByRequestTaskId(List<String> var1, Integer var2);

    public void deleteFloatColByRequestTaskId(List<String> var1, Integer var2);

    public void deleteFixedByRequestTaskId(String var1, String var2, String var3, Integer var4);

    public void deleteFloatDefineByRequestTaskId(String var1, String var2, String var3, Integer var4);

    public List<String> getRequestRegionIdsByFormId(String var1, String var2, String var3, Integer var4);
}

