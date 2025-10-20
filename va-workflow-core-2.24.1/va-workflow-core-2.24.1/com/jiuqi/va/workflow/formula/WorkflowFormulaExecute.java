/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 */
package com.jiuqi.va.workflow.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaHandle;

public class WorkflowFormulaExecute {
    private static final WorkflowFormulaExecute EXECUTE = new WorkflowFormulaExecute();

    private WorkflowFormulaExecute() {
    }

    public static Object evaluate(WorkflowContext context, FormulaImpl formula) throws SyntaxException {
        IExpression expression = EXECUTE.getExpression(context, formula);
        return expression.evaluate((IContext)context);
    }

    public static boolean judge(WorkflowContext context, FormulaImpl formula) throws SyntaxException {
        IExpression expression = EXECUTE.getExpression(context, formula);
        return expression.judge((IContext)context);
    }

    private IExpression getExpression(WorkflowContext context, FormulaImpl formula) throws ParseException {
        WorkflowFormulaHandle handle = new WorkflowFormulaHandle();
        return handle.parse(context, formula.getExpression(), formula.getFormulaType());
    }
}

