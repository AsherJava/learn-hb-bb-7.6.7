/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.report.query.network;

import com.jiuqi.bi.dataset.report.query.ReportQueryContext;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;

public class EvalExprItem {
    private IExpression expression;
    private int resultIndex;
    private int dataType;

    public EvalExprItem(IExpression expression, int resultIndex, int dataType) {
        this.expression = expression;
        this.resultIndex = resultIndex;
        this.dataType = dataType;
    }

    public EvalExprItem(IExpression expression, int dataType) {
        this.expression = expression;
        this.dataType = dataType;
    }

    public Object eval(ReportQueryContext context) throws SyntaxException {
        return this.expression.evaluate((IContext)context);
    }

    public boolean judge(ReportQueryContext context) throws SyntaxException {
        Object result = this.expression.evaluate((IContext)context);
        return result == null ? false : (Boolean)result;
    }

    public int getResultIndex() {
        return this.resultIndex;
    }

    public int getDataType() {
        return this.dataType;
    }
}

