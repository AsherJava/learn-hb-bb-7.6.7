/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.service;

import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParam;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public interface SumHbService {
    public void batchCalc(QueryContext var1, String var2, List<FunctionNode> var3);

    public void batchFilterCalc(QueryContext var1, String var2, List<FunctionNode> var3);

    public static String getCacheKey(String orgId, String currencyCode, SumhbParam sumhbParam) {
        return orgId + currencyCode + sumhbParam.getReginId() + sumhbParam.getFieldDefine().getCode() + sumhbParam.getFilter();
    }

    public void sumHbZbFunctionCalc(QueryContext var1, String var2, List<FunctionNode> var3);
}

