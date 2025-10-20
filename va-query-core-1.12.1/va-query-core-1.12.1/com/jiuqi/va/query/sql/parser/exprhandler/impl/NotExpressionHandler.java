/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.NotExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NotExpression;
import org.springframework.stereotype.Component;

@Component
public class NotExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return NotExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        NotExpression notExpression = (NotExpression)srcExpression;
        Expression expression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(notExpression.getExpression(), params);
        if (expression == null) {
            return null;
        }
        notExpression.setExpression(expression);
        return notExpression;
    }
}

