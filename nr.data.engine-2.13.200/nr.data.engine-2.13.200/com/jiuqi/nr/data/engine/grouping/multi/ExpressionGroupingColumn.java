/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.grouping.multi.IMultiGroupingColumn;

public class ExpressionGroupingColumn
implements IMultiGroupingColumn {
    private IExpression exp;

    public ExpressionGroupingColumn(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public Object readValue(QueryContext context) {
        try {
            return this.exp.evaluate((IContext)context);
        }
        catch (SyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeValue(QueryContext context, Object value) {
    }

    @Override
    public int getDataType(QueryContext context) {
        try {
            return this.exp.getType((IContext)context);
        }
        catch (SyntaxException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void writeValue(Object value) {
    }
}

