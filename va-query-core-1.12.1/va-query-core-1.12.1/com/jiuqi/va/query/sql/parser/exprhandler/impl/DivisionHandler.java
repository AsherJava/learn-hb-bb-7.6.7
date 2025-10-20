/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.arithmetic.Division
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DivisionHandler
implements IExpressionHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Expression> getClazzType() {
        return Division.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Division division = (Division)srcExpression;
        Model leftExpression = this.modelHandlerGather.doParser((Model)division.getLeftExpression(), params);
        Model rightExpression = this.modelHandlerGather.doParser((Model)division.getRightExpression(), params);
        if (leftExpression == null) {
            return null;
        }
        if (rightExpression == null) {
            return null;
        }
        return division;
    }
}

