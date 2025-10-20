/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import java.util.List;

public interface FinancialCheckSchemeDao
extends IBaseSqlGenericDAO<FinancialCheckSchemeEO> {
    public List<FinancialCheckSchemeEO> findByParentIdAndAcctYearOrderBySortOrder(String var1, int var2);

    public List<FinancialCheckSchemeEO> findByParentIdAndStartFlagOrderBySortOrder(String var1, boolean var2);

    public void deleteByParentId(String var1);

    public void startByParentId(String var1, int var2);

    public Integer getMaxOrder();

    public void start(String var1, int var2);

    public FinancialCheckSchemeEO getFrontScheme(String var1, int var2, double var3);

    public FinancialCheckSchemeEO getAfterScheme(String var1, int var2, double var3);

    public List<FinancialCheckSchemeEO> getSchemeByIds(List<String> var1);

    public List<FinancialCheckSchemeEO> getSchemeByIdsOrParentIds(List<String> var1);

    public List<FinancialCheckSchemeEO> queryEnable(int var1, String var2);
}

