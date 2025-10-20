/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.NullValue
 *  net.sf.jsqlparser.expression.operators.arithmetic.Addition
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdditionHandler
implements IExpressionHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Expression> getClazzType() {
        return Addition.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Addition addition = (Addition)srcExpression;
        Model leftExpression = this.modelHandlerGather.doParser((Model)addition.getLeftExpression(), params);
        Model rightExpression = this.modelHandlerGather.doParser((Model)addition.getRightExpression(), params);
        if (leftExpression == null) {
            addition.setLeftExpression((Expression)new NullValue());
        }
        if (rightExpression == null) {
            addition.setRightExpression((Expression)new NullValue());
        }
        return addition;
    }
}

