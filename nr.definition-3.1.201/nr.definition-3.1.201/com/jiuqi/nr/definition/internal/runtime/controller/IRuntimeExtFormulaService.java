/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import java.util.List;
import java.util.Set;

public interface IRuntimeExtFormulaService {
    public FormulaDefine queryFormula(String var1);

    public List<FormulaDefine> queryFormulas(List<String> var1);

    public FormulaDefine findFormula(String var1, String var2);

    public List<FormulaDefine> getFormulasInScheme(String var1);

    public List<FormulaDefine> getFormulasInScheme(String var1, DataEngineConsts.FormulaType var2);

    public List<FormulaDefine> getFormulasInForm(String var1, String var2);

    public List<FormulaDefine> getFormulasInFormByType(String var1, String var2);

    public void ModifyFormulaCheckType(int var1, int var2);

    public List<FormulaDefine> searchFormulaInScheme(String var1, String var2);

    public void refreshFormulaCache(String var1) throws Exception;

    public List<FormulaDefine> getFormulaByUnits(String var1, Set<String> var2);
}

