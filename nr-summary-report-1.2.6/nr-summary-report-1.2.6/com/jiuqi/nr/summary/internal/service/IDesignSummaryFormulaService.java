/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.DesignSummaryFormula;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryFormulaDTO;
import java.util.List;

public interface IDesignSummaryFormulaService {
    public String insertSummaryFormula(DesignSummaryFormulaDTO var1) throws DBParaException;

    public void batchInsertSummaryFormula(List<DesignSummaryFormulaDTO> var1);

    public void deleteSummaryFormulaByKey(String var1) throws DBParaException;

    public void deleteSummaryFormulaByKeys(List<String> var1) throws DBParaException;

    public void deleteSummaryFormulaByReport(String var1) throws DBParaException;

    public void updateSummaryFormula(DesignSummaryFormulaDTO var1) throws DBParaException;

    public void batchUpdateSummaryFormula(List<DesignSummaryFormulaDTO> var1);

    public DesignSummaryFormula getSummaryFormulaByKey(String var1);

    public List<DesignSummaryFormula> getSummaryFormulasByReport(String var1);

    public List<DesignSummaryFormula> getBJSummaryFormulasBySolution(String var1);

    public List<DesignSummaryFormula> getSummaryFormulasBySolution(String var1);
}

