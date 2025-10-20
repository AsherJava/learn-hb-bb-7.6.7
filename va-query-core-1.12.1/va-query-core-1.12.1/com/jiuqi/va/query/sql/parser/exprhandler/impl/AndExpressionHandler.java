/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.conditional.AndExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import org.springframework.stereotype.Component;

@Component
public class AndExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return AndExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        AndExpression andExpression = (AndExpression)srcExpression;
        Expression leftExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(andExpression.getLeftExpression(), params);
        Expression rightExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(andExpression.getRightExpression(), params);
        if (leftExpression == null && rightExpression == null) {
            return null;
        }
        if (leftExpression == null) {
            return rightExpression;
        }
        if (rightExpression == null) {
            return leftExpression;
        }
        andExpression.setLeftExpression(leftExpression);
        andExpression.setRightExpression(rightExpression);
        return andExpression;
    }
}

