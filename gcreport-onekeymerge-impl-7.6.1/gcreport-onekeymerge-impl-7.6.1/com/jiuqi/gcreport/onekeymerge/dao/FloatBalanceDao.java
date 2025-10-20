/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceEO
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.calculate.entity.FloatBalanceEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import java.util.List;
import java.util.Map;

public interface FloatBalanceDao
extends IDbSqlGenericDAO<FloatBalanceEO, String> {
    public void batchClearAmt(String var1, GcActionParamsVO var2);

    public void batchDeleteEmptyRow(String var1, GcActionParamsVO var2);

    public List<FloatBalanceEO> list(String var1, GcActionParamsVO var2);

    public List<Map<String, Object>> queryByMergeCode(boolean var1, GcActionParamsVO var2, String var3, List<String> var4);

    public List<FloatBalanceEO> queryByMergeCode(boolean var1, GcActionParamsVO var2);

    public void batchDeleteAllBalance(String var1, String var2, GcActionParamsVO var3);

    public List<Map<String, Object>> queryDifferenceIntermediateDatas(List<String> var1, GcActionParamsVO var2, String var3);

    public void batchDeleteAllBalanceByRelateSubjectCodes(String var1, String var2, GcActionParamsVO var3, List<String> var4);

    public void intertFloatBalanceDetail(String var1, List<String> var2, Map<String, Object> var3);

    public List<Map<String, Object>> querySumFieldByMergeCode(GcActionParamsVO var1, String var2, List<String> var3, List<String> var4);

    public List<Map<String, Object>> queryOneOtherFieldsData(GcActionParamsVO var1, String var2, List<String> var3);
}

