/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service.Formula;

import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import java.util.List;

public interface ConsolidatedFormulaService {
    public List<ConsolidatedFormulaVO> listConsFormulas(String var1);

    public List<ConsolidatedFormulaVO> listConsFormulas(String var1, boolean var2);

    public void saveConsFormula(List<ConsolidatedFormulaVO> var1);

    public void batchDeleteConsFormula(List<String> var1);

    public void exchangeSort(String var1, int var2);
}

