/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.multicriteria.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriBeforeZbEO;
import java.util.List;
import java.util.Set;

public interface GcMulCriBeforeZbDao
extends IDbSqlGenericDAO<GcMulCriBeforeZbEO, String> {
    public void deleteMulCriBeforeZb(List<String> var1);

    public List<GcMulCriBeforeZbEO> queryMulCriBeforeDataByFormKey(String var1, String var2);

    public List<GcMulCriBeforeZbEO> queryMulCriBeforeDataByMcids(List<String> var1);

    public Set<String> queryMulCriBeforeMcidsByFormKeys(String var1, List<String> var2);

    public Set<String> queryMcidsByBeforeZbKeys(String var1, Set<String> var2);
}

