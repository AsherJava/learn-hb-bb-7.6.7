/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory
 */
package com.jiuqi.gcreport.formulaschemeconfig.gather;

import com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory;
import java.util.List;

public interface IFormulaSchemeConfigCategoryGather {
    public List<IFormulaSchemeConfigCategory> list();

    public IFormulaSchemeConfigCategory get(String var1);
}

