/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckMappingEO;
import java.util.List;

public interface FinancialCheckMappingDao
extends IBaseSqlGenericDAO<FinancialCheckMappingEO> {
    public void deleteBySchemeId(String var1);

    public List<FinancialCheckMappingEO> selectBySchemeIds(List<String> var1);
}

