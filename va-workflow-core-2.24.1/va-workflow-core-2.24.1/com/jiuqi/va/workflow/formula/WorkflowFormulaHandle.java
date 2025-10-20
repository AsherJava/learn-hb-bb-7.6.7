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
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 */
package com.jiuqi.va.workflow.formula;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowNodeProvider;
import java.util.Map;

public class WorkflowFormulaHandle {
    public IExpression parse(WorkflowContext context, String expresion, FormulaType formulaType) throws ParseException {
        ReportFormulaParser parser = new ReportFormulaParser();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new WorkflowNodeProvider());
        for (Map.Entry functionProviderMap : ModelFunctionProvider.functionProviderMap.entrySet()) {
            parser.registerFunctionProvider((IFunctionProvider)functionProviderMap.getValue());
        }
        if (FormulaType.CHECK.equals((Object)formulaType)) {
            return parser.parseCond(expresion, (IContext)context);
        }
        return parser.parseAssign(expresion, (IContext)context);
    }
}

