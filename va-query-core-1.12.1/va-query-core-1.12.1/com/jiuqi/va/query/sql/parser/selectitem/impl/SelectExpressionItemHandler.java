/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.va.query.sql.parser.selectitem.impl;

import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import com.jiuqi.va.query.sql.parser.selectitem.ISelectItemHandler;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectExpressionItemHandler
implements ISelectItemHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends SelectItem> getClazzType() {
        return SelectExpressionItem.class;
    }

    @Override
    public SelectItem doParser(SelectItem selectItem, List<String> params) {
        SelectExpressionItem expressionItem = (SelectExpressionItem)selectItem;
        Expression expression = (Expression)this.modelHandlerGather.doParser((Model)expressionItem.getExpression(), params);
        expressionItem.setExpression(expression);
        return selectItem;
    }
}

