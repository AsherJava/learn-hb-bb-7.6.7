/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import com.jiuqi.nr.formula.dto.ItemOrderMoveDTO;
import java.util.List;

public interface IFormulaSchemeService {
    public void insertFormulaScheme(FormulaSchemeDTO var1);

    public void insertFormulaSchemes(List<FormulaSchemeDTO> var1);

    public void updateFormulaScheme(FormulaSchemeDTO var1);

    public void updateFormulaSchemes(List<FormulaSchemeDTO> var1);

    public void deleteFormulaScheme(String var1);

    public void deleteFormulaSchemes(List<String> var1);

    public FormulaSchemeDTO getFormulaScheme(String var1);

    public List<FormulaSchemeDTO> listFormulaSchemeByFormScheme(String var1);

    public List<FormulaSchemeDTO> listReportFormulaSchemeByFormScheme(String var1);

    public List<FormulaSchemeDTO> listEFDCFormulaSchemeByFormScheme(String var1);

    public List<FormulaSchemeDTO> allFormulaSchemes();

    public FormulaSchemeDTO getEFDCFormulaScheme(String var1);

    public void publishFormulaScheme(String var1);

    public void publishFormulaScheme(String var1, String var2);

    public void copyFormulaScheme(FormulaSchemeDTO var1);

    public void moveFormulaScheme(ItemOrderMoveDTO var1);

    public void defaultFormulaScheme(String var1);

    public void insertDefaultFormulaScheme(String var1);

    public boolean checkFormulaScheme(FormulaSchemeDTO var1);
}

