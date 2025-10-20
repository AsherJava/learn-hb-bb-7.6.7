/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.IsNullExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import org.springframework.stereotype.Component;

@Component
public class IsNullExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return IsNullExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        IsNullExpression isNullExpression = (IsNullExpression)srcExpression;
        return DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(isNullExpression.getLeftExpression(), params);
    }
}

