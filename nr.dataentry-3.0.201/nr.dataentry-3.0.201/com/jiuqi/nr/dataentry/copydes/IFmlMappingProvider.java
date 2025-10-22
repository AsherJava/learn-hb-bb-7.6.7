/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 */
package com.jiuqi.nr.dataentry.copydes;

import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import java.util.List;

public interface IFmlMappingProvider {
    public String getSrcFmlScheme(String var1);

    public List<FormulaMappingDefine> getFormulaMapping(String var1);
}

