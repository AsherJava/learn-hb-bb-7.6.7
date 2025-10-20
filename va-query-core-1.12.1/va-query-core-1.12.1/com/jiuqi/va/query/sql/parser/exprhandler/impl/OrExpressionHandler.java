/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.conditional.OrExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import org.springframework.stereotype.Component;

@Component
public class OrExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return OrExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        OrExpression orExpression = (OrExpression)srcExpression;
        Expression leftExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(orExpression.getLeftExpression(), params);
        Expression rightExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(orExpression.getRightExpression(), params);
        if (leftExpression == null || rightExpression == null) {
            return null;
        }
        return new OrExpression(leftExpression, rightExpression);
    }
}

