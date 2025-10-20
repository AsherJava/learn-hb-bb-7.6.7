/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bde.base.formula;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bde.base.formula.FetchFormulaPaser;
import com.jiuqi.bi.syntax.SyntaxException;
import java.util.HashMap;

public class FetchExprTest {
    public static void main(String[] args) throws SyntaxException {
        String formula = "if ENV[UNITCODE] = '1067414' then BIZMODEL[0f5fc709ca2238e9] + BIZMODEL[068370323E268564] else 0";
        HashMap<String, Object> envParamMap = new HashMap<String, Object>();
        envParamMap.put("UNITCODE", "1067414");
        FetchFormulaContext context = new FetchFormulaContext(envParamMap);
        HashMap<String, Object> fetchResult = new HashMap<String, Object>();
        fetchResult.put("0f5fc709ca2238e9", 1001.0);
        fetchResult.put("068370323E268564", 1002.0);
        fetchResult.put("A03", 1003.0);
        fetchResult.put("A04", 1004.0);
        context.setFetchResultMap(fetchResult);
        HashMap<String, Object> floatRowMap = new HashMap<String, Object>();
        floatRowMap.put("0f5fc709ca2238e9", "100");
        floatRowMap.put("068370323E268564", "200");
        floatRowMap.put("09e775c3873e3f5e", "300");
        context.setFloatRowMap(floatRowMap);
        Object evaluate = new FetchFormulaPaser().evaluate(formula, context);
        System.out.println(formula);
        System.out.println(evaluate);
    }
}

