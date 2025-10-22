/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import java.util.List;
import java.util.Map;

public interface IExtFormulaDesignTimeController {
    public boolean existPrivateFormula();

    public DesignFormulaDefine createFormulaDefine();

    public String insertFormulaDefine(DesignFormulaDefine var1) throws JQException;

    public void updateFormulaDefine(DesignFormulaDefine var1) throws JQException;

    public void deleteFormulaDefine(String var1);

    public void exchangeFormulaOrder(String var1, String var2);

    public DesignFormulaDefine queryFormulaDefine(String var1) throws JQException;

    public List<DesignFormulaDefine> findRepeatFormulaDefineFormOutSchemes(String var1, String var2, String var3) throws JQException;

    public List<DesignFormulaDefine> getAllFormulasInScheme(String var1) throws JQException;

    public List<DesignFormulaDefine> getAllFormulas() throws JQException;

    public List<DesignFormulaDefine> getFormulaByUnit(String var1, String var2, String var3) throws JQException;

    public void updateFormulaDefines(DesignFormulaDefine[] var1) throws JQException;

    public void deleteFormulaDefines(String[] var1) throws JQException;

    public Map<String, Integer> getFormulaCodeCountByScheme(String var1) throws JQException;

    public List<String> insertFormulaDefines(DesignFormulaDefine[] var1) throws JQException;

    public List<DesignFormulaDefine> searchFormulaIgnorePrivate(String var1) throws JQException;

    public List<DesignFormulaDefine> getFormulaBySchemeAndForm(String var1, String var2) throws Exception;
}

