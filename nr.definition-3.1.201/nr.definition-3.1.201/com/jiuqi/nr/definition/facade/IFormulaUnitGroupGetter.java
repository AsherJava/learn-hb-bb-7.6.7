/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import java.util.List;

public interface IFormulaUnitGroupGetter {
    public List<FormulaUnitGroup> get(List<String> var1, String var2, DataEngineConsts.FormulaType var3);
}

