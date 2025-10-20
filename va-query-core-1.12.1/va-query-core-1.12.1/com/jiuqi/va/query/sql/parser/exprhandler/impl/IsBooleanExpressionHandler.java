/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class IsBooleanExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return IsBooleanExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        IsBooleanExpression isBooleanExpression = (IsBooleanExpression)srcExpression;
        return DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(isBooleanExpression.getLeftExpression(), params);
    }
}

