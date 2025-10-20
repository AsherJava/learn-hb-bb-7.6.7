/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.Between
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import org.springframework.stereotype.Component;

@Component
public class BetweenHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return Between.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Between between = (Between)srcExpression;
        Expression leftExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(between.getLeftExpression(), params);
        if (leftExpression == null) {
            return null;
        }
        Expression betweenExpressionStart = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(between.getBetweenExpressionStart(), params);
        if (betweenExpressionStart == null) {
            return null;
        }
        Expression betweenExpressionEnd = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(between.getBetweenExpressionEnd(), params);
        if (betweenExpressionEnd == null) {
            return null;
        }
        return between;
    }
}

