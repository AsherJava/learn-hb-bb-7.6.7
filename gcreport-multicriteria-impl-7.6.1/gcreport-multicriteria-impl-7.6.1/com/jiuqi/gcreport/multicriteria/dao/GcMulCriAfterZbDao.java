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
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterZbEO;
import java.util.List;
import java.util.Set;

public interface GcMulCriAfterZbDao
extends IDbSqlGenericDAO<GcMulCriAfterZbEO, String> {
    public void deleteMulCriAfterZb(List<String> var1);

    public List<GcMulCriAfterZbEO> queryMulCriAfterDataByFormKey(String var1, Set<String> var2);

    public List<GcMulCriAfterZbEO> queryMulCriAfterDataByIds(List<String> var1);

    public Set<String> queryMcidsOfAfterAmtIsNotNull(GcMultiCriteriaConditionVO var1);

    public Set<String> queryMulCriAfterDataByFormKeys(String var1, List<String> var2);

    public Set<String> queryMcidsByAfterZbKeys(String var1, Set<String> var2);

    public Set<String> queryIdsByMcids(List<String> var1);
}

