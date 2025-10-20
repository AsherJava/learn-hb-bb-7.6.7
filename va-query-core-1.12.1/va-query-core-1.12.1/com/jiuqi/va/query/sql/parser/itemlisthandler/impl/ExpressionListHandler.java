/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.ExpressionList
 *  net.sf.jsqlparser.expression.operators.relational.ItemsList
 */
package com.jiuqi.va.query.sql.parser.itemlisthandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.itemlisthandler.IItemListHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import org.springframework.stereotype.Component;

@Component
public class ExpressionListHandler
implements IItemListHandler {
    @Override
    public ItemsList doParser(ItemsList itemsList, List<String> params) {
        ExpressionList expressionList = (ExpressionList)itemsList;
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        for (Expression expr : expressionList.getExpressions()) {
            Expression expression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(expr, params);
            if (expression == null) continue;
            expressions.add(expression);
        }
        expressionList.setExpressions(expressions);
        return expressionList;
    }

    @Override
    public Class<? extends ItemsList> getClazzType() {
        return ExpressionList.class;
    }
}

