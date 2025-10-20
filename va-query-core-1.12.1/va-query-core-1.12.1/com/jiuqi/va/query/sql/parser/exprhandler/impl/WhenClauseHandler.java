/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.LongValue
 *  net.sf.jsqlparser.expression.WhenClause
 *  net.sf.jsqlparser.expression.operators.relational.EqualsTo
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import org.springframework.stereotype.Component;

@Component
public class WhenClauseHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return WhenClause.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        WhenClause expression = (WhenClause)srcExpression;
        Expression whenExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(expression.getWhenExpression(), params);
        if (whenExpression == null) {
            whenExpression = new EqualsTo((Expression)new LongValue("1"), (Expression)new LongValue("2"));
        }
        expression.setWhenExpression(whenExpression);
        Expression thenExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(expression.getThenExpression(), params);
        expression.setThenExpression(thenExpression);
        return expression;
    }
}

