/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.efdc.service;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.internal.utils.NrResult;
import java.util.List;
import java.util.Map;

public interface SoluctionQueryService {
    public FormulaSchemeDefine getSoluctionByDimensions(QueryObjectImpl var1, Map<String, String> var2, String var3);

    public FormulaSchemeDefine getRPTFormulaScheme(QueryObjectImpl var1, Map<String, String> var2, String var3);

    public Map<String, String> batchQueryByDimensions(QueryObjectImpl var1, List<String> var2, Map<String, String> var3, String var4, boolean var5) throws Exception;

    public List<QueryObjectImpl> getSoluctions(QueryObjectImpl var1);

    public NrResult insertSoluction(List<QueryObjectImpl> var1);

    public NrResult clearByFormScheme(QueryObjectImpl var1, List<String> var2);

    public Boolean existSolution(String var1);
}

