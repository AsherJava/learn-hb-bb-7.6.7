/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import java.util.List;

public interface IDesignTimeExtFormulaController {
    public DesignFormulaDefine initExtFormula();

    public void insertExtFormula(DesignFormulaDefine[] var1);

    public void updateExtFormula(DesignFormulaDefine[] var1);

    public void deleteExtFormula(String[] var1);

    public void deleteExtFormulaByFormulaScheme(String var1);

    public List<DesignFormulaDefine> getFormulaByCodeAndSchemeAndForm(String var1, String var2, String var3);

    public List<DesignFormulaDefine> listFormulaByScheme(String var1);

    public List<DesignFormulaDefine> listAllFormulaDefine();

    public List<DesignFormulaDefine> listFormulaBySchemeAndFormAndEntity(String var1, String var2, String var3);

    public List<DesignFormulaDefine> listFormulaBySchemeIgnorePrivate(String var1);

    public List<DesignFormulaDefine> listFormulaBySchemeAndForm(String var1, String var2);

    public boolean getExistPrivateFormula();
}

