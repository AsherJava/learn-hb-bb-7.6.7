/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.filter.bill.formula.OrgFormulaNodeProvider;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import java.util.Map;

public class OrgFormulaHandle {
    public IExpression parse(OrgFormulaContext context, String expresion) throws ParseException {
        ReportFormulaParser parser = new ReportFormulaParser();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new OrgFormulaNodeProvider());
        for (Map.Entry functionProviderMap : ModelFunctionProvider.functionProviderMap.entrySet()) {
            parser.registerFunctionProvider((IFunctionProvider)functionProviderMap.getValue());
        }
        return parser.parseCond(expresion, (IContext)context);
    }
}

