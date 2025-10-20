/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.multicriteria.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterFormEO;
import java.util.List;

public interface GcMulCriAfterFormDao
extends IDbSqlGenericDAO<GcMulCriAfterFormEO, String> {
    public void deleteMulCriAfterForms(String var1, String var2);

    public List<GcMulCriAfterFormEO> queryMulCriAfterForms(String var1, String var2);
}

