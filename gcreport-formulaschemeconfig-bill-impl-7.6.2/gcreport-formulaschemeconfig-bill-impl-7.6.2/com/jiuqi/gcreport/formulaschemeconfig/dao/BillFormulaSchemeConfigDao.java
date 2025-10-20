/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 */
package com.jiuqi.gcreport.formulaschemeconfig.dao;

import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import java.util.List;

public interface BillFormulaSchemeConfigDao {
    public List<FormulaSchemeConfigEO> getAllFormulaSchemeConfigs(String var1);

    public void deleteStrategySchemeConfig(String var1);

    public void addBatch(List<FormulaSchemeConfigEO> var1);

    public void deleteSelectSchemeConfig(List<String> var1);

    public List<FormulaSchemeConfigEO> listFormulaSchemeConfigById(List<String> var1);

    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByOrgIds(List<String> var1, String var2);

    public List<String> listByBillSettingType(String var1);
}

