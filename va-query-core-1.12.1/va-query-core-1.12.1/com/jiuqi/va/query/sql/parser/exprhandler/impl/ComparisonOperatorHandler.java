/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.ComparisonOperator
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComparisonOperatorHandler
implements IExpressionHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Expression> getClazzType() {
        return ComparisonOperator.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        ComparisonOperator expression = (ComparisonOperator)srcExpression;
        if (params == null || params.isEmpty()) {
            return expression;
        }
        Model leftExpression = this.modelHandlerGather.doParser((Model)expression.getLeftExpression(), params);
        Model rightExpression = this.modelHandlerGather.doParser((Model)expression.getRightExpression(), params);
        if (leftExpression == null || rightExpression == null) {
            return null;
        }
        return expression;
    }
}

