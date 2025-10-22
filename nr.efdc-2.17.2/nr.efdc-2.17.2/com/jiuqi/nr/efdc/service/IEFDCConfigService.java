/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.efdc.service;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import java.util.List;
import java.util.Map;

public interface IEFDCConfigService {
    public List<FormulaSchemeDefine> getRPTFormulaScheme(QueryObjectImpl var1, Map<String, String> var2, String var3);

    public FormulaSchemeDefine getSoluctionByDimensions(QueryObjectImpl var1, Map<String, String> var2, String var3);

    public Boolean existSolution(String var1);
}

