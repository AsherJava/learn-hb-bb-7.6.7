/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.data.engine.analysis.exe.query.grouping;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.IAnalysisGroupingColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionGroupingColumn
implements IAnalysisGroupingColumn {
    private static final Logger logger = LoggerFactory.getLogger(ExpressionGroupingColumn.class);
    private IExpression exp;

    public ExpressionGroupingColumn(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public Object readValue(AnalysisContext context) {
        try {
            return this.exp.evaluate((IContext)context);
        }
        catch (SyntaxException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void writeValue(AnalysisContext context, Object value) {
    }
}

