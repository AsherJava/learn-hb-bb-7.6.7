/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 */
package com.jiuqi.gcreport.multicriteria.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterAmtEO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GcMulCriAfterAmtDao
extends IDbSqlGenericDAO<GcMulCriAfterAmtEO, String> {
    public void deleteByIds(Set<String> var1);

    public Map<String, Double> queryMulCriAfterAmt(List<String> var1, GcMultiCriteriaConditionVO var2);

    public List<GcMulCriAfterAmtEO> queryMulCriAfterAmtByDs(List<String> var1, GcMultiCriteriaConditionVO var2);
}

