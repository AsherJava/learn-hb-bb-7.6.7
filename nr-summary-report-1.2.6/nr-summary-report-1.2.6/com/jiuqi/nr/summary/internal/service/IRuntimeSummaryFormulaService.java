/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.SummaryFormula;
import com.jiuqi.nr.summary.internal.dto.SummaryFormulaDTO;
import java.util.List;

public interface IRuntimeSummaryFormulaService {
    public String insertSummaryFormula(SummaryFormulaDTO var1) throws DBParaException;

    public void batchInsertSummaryFormula(List<SummaryFormulaDTO> var1);

    public void deleteSummaryFormulaByKey(String var1) throws DBParaException;

    public void deleteSummaryFormulaByReport(String var1) throws DBParaException;

    public void deleteSummaryFormulaBySolution(String var1) throws DBParaException;

    public void updateSummaryFormula(SummaryFormulaDTO var1) throws DBParaException;

    public SummaryFormula getSummaryFormulaByKey(String var1);

    public List<SummaryFormula> getSummaryFormulasByReport(String var1);

    public List<SummaryFormula> getBJSummaryFormulasBySolution(String var1);

    public List<SummaryFormula> getSummaryFormulasBySolution(String var1);
}

