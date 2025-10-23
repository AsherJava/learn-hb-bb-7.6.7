/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.dto.ImportResult;
import com.jiuqi.nr.formula.web.param.FormulaDataExportPM;
import com.jiuqi.nr.formula.web.param.FormulaExtPM;
import com.jiuqi.nr.formula.web.param.FormulaListPM;
import com.jiuqi.nr.formula.web.param.FormulaMovePM;
import com.jiuqi.nr.formula.web.param.FormulaQueryPM;
import com.jiuqi.nr.formula.web.param.FormulaSavePM;
import com.jiuqi.nr.formula.web.param.ImportPM;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.formula.web.vo.FormulaDataVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface IFormulaDataService {
    public FormulaDataVO searchFormulaData(FormulaListPM var1);

    public FormulaDataVO queryFormulaData(FormulaQueryPM var1);

    public void moveFormulaData(FormulaMovePM var1);

    public void saveFormulaData(FormulaSavePM var1);

    public void updateFormulas(List<FormulaDTO> var1);

    public void insertFormulas(List<FormulaDTO> var1);

    public void deleteFormulas(List<String> var1);

    public FormulaCheckResult formulaCheck(FormulaExtPM var1);

    public void exportFormulaInForm(HttpServletResponse var1, FormulaDataExportPM var2);

    public void exportFormulaInFormulaScheme(HttpServletResponse var1, FormulaDataExportPM var2);

    public ImportResult importFormulaExcel(ImportPM var1);
}

