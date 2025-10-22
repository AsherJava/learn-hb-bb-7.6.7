/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceDiffEO
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.calculate.entity.FloatBalanceDiffEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import java.util.List;

public interface FloatBalanceDiffDao
extends IDbSqlGenericDAO<FloatBalanceDiffEO, String> {
    public void batchDeleteAllBalance(String var1, List<String> var2, String var3, GcDiffProcessCondition var4);

    public void batchDeleteAllBalanceByOppunitTitle(String var1, String var2, String var3, String var4, GcDiffProcessCondition var5);
}

