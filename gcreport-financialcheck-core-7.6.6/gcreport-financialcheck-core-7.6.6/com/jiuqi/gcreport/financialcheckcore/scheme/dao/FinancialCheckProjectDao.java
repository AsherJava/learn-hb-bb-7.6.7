/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO;
import java.util.List;

public interface FinancialCheckProjectDao
extends IBaseSqlGenericDAO<FinancialCheckProjectEO> {
    public void deleteByIds(List<String> var1);

    public void deleteBySchemeId(String var1);

    public List<FinancialCheckProjectEO> selectBySchemeId(String var1);

    public List<FinancialCheckProjectEO> selectBySchemeIds(List<String> var1);
}

