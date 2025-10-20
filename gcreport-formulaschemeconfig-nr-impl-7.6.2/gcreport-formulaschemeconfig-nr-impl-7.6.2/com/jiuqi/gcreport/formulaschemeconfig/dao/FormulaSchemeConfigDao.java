/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 */
package com.jiuqi.gcreport.formulaschemeconfig.dao;

import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import java.util.List;

public interface FormulaSchemeConfigDao {
    public List<FormulaSchemeConfigEO> getAllFormulaSchemeConfigs(String var1, String var2);

    public void deleteStrategySchemeConfig(String var1, String var2);

    public void addBatch(List<FormulaSchemeConfigEO> var1);

    public void deleteSelectSchemeConfig(List<String> var1);

    public List<FormulaSchemeConfigEO> queryAllByTaskId(String var1);

    public FormulaSchemeConfigEO getShowTableByOrgIdOrBblx(String var1, String var2, String var3, String var4, String var5);

    public FormulaSchemeConfigEO getShowTableById(String var1);

    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByOrgIdOrBblx(String var1, String var2, String var3, String var4, String var5);

    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByParents(List<String> var1, String var2, String var3, String var4);

    public List<FormulaSchemeConfigEO> getStrategyTabSchemeConfig(String var1, String var2);

    public List<FormulaSchemeConfigEO> queryAllBySchemeId(String var1);

    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByOrgIds(List<String> var1, String var2, String var3);

    public void deleteFormulaSchemeConfigByUniqueIndex(String var1, String var2, String var3, String var4);

    public int getFormulaSchemeConfigByFetchSchemeId(String var1);

    public List<FormulaSchemeConfigEO> listFormulaSchemeConfigById(List<String> var1);

    public List<String> selectEntityIdByTask(String var1);

    public int batchUpdateEntityId(String var1, String var2, String var3);
}

