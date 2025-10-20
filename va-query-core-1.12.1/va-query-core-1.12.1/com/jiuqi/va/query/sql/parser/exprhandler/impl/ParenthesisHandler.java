/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.Parenthesis
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import org.springframework.stereotype.Component;

@Component
public class ParenthesisHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return Parenthesis.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Parenthesis parenthesis = (Parenthesis)srcExpression;
        Expression expression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(parenthesis.getExpression(), params);
        if (expression == null) {
            return null;
        }
        parenthesis.setExpression(expression);
        return parenthesis;
    }
}

