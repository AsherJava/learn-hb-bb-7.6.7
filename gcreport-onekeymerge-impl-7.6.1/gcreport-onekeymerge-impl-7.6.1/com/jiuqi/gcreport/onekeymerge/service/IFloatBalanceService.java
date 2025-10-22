/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceEO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.calculate.entity.FloatBalanceEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IFloatBalanceService {
    public void batchClearAmt(String var1, GcActionParamsVO var2);

    public void batchDeleteEmptyRow(String var1, GcActionParamsVO var2);

    public void batchMerge(String var1, Map<String, BigDecimal> var2, GcActionParamsVO var3);

    public List<Map<String, Object>> queryOppNotBelongCurrlevelDatas(GcActionParamsVO var1, String var2, List<String> var3);

    public List<FloatBalanceEO> queryByMergeCode(GcActionParamsVO var1);

    public List<Map<String, Object>> queryByMergeCode(GcActionParamsVO var1, String var2, List<String> var3);

    public void batchDeleteAllBalance(String var1, String var2, GcActionParamsVO var3);

    public Map<String, Object> batchRewriteToBalance(String var1, Map<String, Object> var2, String var3, Map<String, List<Map<String, Object>>> var4, GcActionParamsVO var5, String[] var6, List<String> var7, List<String> var8, Map<String, BigDecimal> var9, Set<String> var10, Map<String, String> var11);

    public List<Map<String, Object>> queryDifferenceIntermediateDatas(List<String> var1, GcActionParamsVO var2, String var3);

    public void batchRewrite(String var1, GcActionParamsVO var2, List<Map<String, Object>> var3, String var4, List<String> var5);

    public void batchDeleteAllBalanceByRelateSubjectCodes(String var1, String var2, GcActionParamsVO var3, List<String> var4);

    public List<Map<String, Object>> querySumFieldByMergeCode(GcActionParamsVO var1, String var2, List<String> var3, List<String> var4);

    public List<Map<String, Object>> queryOneOtherFieldsData(GcActionParamsVO var1, String var2, List<String> var3);
}

