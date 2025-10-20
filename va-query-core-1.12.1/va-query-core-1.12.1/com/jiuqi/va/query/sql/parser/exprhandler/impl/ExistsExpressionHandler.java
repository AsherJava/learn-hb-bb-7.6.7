/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.ExistsExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import org.springframework.stereotype.Component;

@Component
public class ExistsExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return ExistsExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        ExistsExpression existsExpression = (ExistsExpression)srcExpression;
        return DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(existsExpression.getRightExpression(), params);
    }
}

