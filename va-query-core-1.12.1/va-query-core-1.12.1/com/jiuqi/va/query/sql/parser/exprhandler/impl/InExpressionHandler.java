/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.InExpression
 *  net.sf.jsqlparser.expression.operators.relational.ItemsList
 *  net.sf.jsqlparser.expression.operators.relational.MultiExpressionList
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.itemlisthandler.ItemListHandlerGather;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import org.springframework.stereotype.Component;

@Component
public class InExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return InExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        InExpression inExpression = (InExpression)srcExpression;
        Expression left = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(inExpression.getLeftExpression(), params);
        if (left == null) {
            return null;
        }
        if (inExpression.getRightExpression() != null) {
            Expression right = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(inExpression.getRightExpression(), params);
            if (right == null) {
                return null;
            }
        } else if (inExpression.getRightItemsList() != null) {
            ItemsList rightItemsList = inExpression.getRightItemsList();
            DCQuerySpringContextUtils.getBean(ItemListHandlerGather.class).doParser(rightItemsList, params);
        } else {
            MultiExpressionList multiExpressionList = inExpression.getMultiExpressionList();
        }
        return inExpression;
    }
}

