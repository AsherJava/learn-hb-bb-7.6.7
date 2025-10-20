/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.arithmetic.Concat
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.stereotype.Component;

@Component
public class ConcatHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return Concat.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Concat concat = (Concat)srcExpression;
        Expression leftExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(concat.getLeftExpression(), params);
        Expression rightExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(concat.getRightExpression(), params);
        if (leftExpression == null || rightExpression == null) {
            return null;
        }
        Concat concat1 = new Concat();
        concat.setLeftExpression(leftExpression);
        concat.setRightExpression(rightExpression);
        return concat1;
    }
}

