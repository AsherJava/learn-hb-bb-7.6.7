/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.Function
 *  net.sf.jsqlparser.expression.operators.relational.ExpressionList
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FunctionHandler
implements IExpressionHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Expression> getClazzType() {
        return Function.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Function function = (Function)srcExpression;
        ExpressionList parameters = function.getParameters();
        ArrayList<Expression> newExPression = new ArrayList<Expression>();
        for (Expression expression : parameters.getExpressions()) {
            Model model = this.modelHandlerGather.doParser((Model)expression, params);
            if (model == null) {
                return null;
            }
            newExPression.add(expression);
        }
        parameters.setExpressions(newExPression);
        return function;
    }
}

