/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.VariableAssignment
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.VariableAssignment;
import org.springframework.stereotype.Component;

@Component
public class VariableAssignmentHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return VariableAssignment.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        VariableAssignment variableAssignment = (VariableAssignment)srcExpression;
        Expression variableExpression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser((Expression)variableAssignment.getVariable(), params);
        Expression expression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(variableAssignment.getExpression(), params);
        if (variableExpression == null || expression == null) {
            return null;
        }
        return variableAssignment;
    }
}

