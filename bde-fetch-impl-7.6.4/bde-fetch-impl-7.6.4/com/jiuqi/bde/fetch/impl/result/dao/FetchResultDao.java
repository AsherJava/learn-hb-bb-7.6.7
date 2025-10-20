/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.base.intf.FloatColResultVO
 *  com.jiuqi.bde.base.intf.FloatDefineEO
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 */
package com.jiuqi.bde.fetch.impl.result.dao;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.base.intf.FloatColResultVO;
import com.jiuqi.bde.base.intf.FloatDefineEO;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FetchResultDao {
    public void insertFixedResult(FetchResultDim var1, List<FetchResult> var2);

    public void insertFloatRowDefine(FetchResultDim var1, List<FloatDefineEO> var2);

    public void insertFloatRowResult(FetchResultDim var1, List<FloatRowResultEO> var2);

    public void insertFloatColResult(FetchResultDim var1, List<FetchResult> var2);

    public Set<String> getFloatRowRegionSet(String var1, String var2, Integer var3);

    public List<Map<String, Object>> getFloatRowResultsWithType(FetchResultDim var1);

    public Map<String, Object> getSyncFixedResultsByFetchTest(String var1, String var2, Integer var3);

    public Map<String, Object> getSyncFixedResults(String var1, String var2, Set<String> var3, Integer var4);

    public Map<String, Map<String, Object>> getFixedSumResults(String var1, String var2, Integer var3);

    public Map<String, Object> getFixedSumResults(String var1, String var2, String var3, Integer var4);

    public Map<String, Map<String, Map<String, Object>>> getFixedDetailResults(String var1, String var2, List<String> var3, Integer var4);

    public Map<String, Map<String, Object>> getFixedDetailResults(String var1, String var2, String var3, List<String> var4, Integer var5);

    public FetchFloatRowResult getFloatRowDatasMap(FetchResultDim var1);

    public FloatColResultVO getFloatColData(FetchResultDim var1);

    public Integer countByTableName(String var1);

    public void truncateByTableName(String var1);

    public List<FloatDefineEO> getFloatDefineList(FetchResultDim var1);

    public void deleteFloatRowByRequestTaskId(List<String> var1, Integer var2);

    public void deleteFloatColByRequestTaskId(List<String> var1, Integer var2);

    public void deleteFixedByRequestTaskId(String var1, String var2, String var3, Integer var4);

    public void deleteFloatDefineByRequestTaskId(String var1, String var2, String var3, Integer var4);

    public List<String> getRequestRegionIdsByFormId(String var1, String var2, String var3, Integer var4);
}

