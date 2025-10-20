/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.statement.select.FromItem
 *  net.sf.jsqlparser.statement.select.GroupByElement
 *  net.sf.jsqlparser.statement.select.Join
 *  net.sf.jsqlparser.statement.select.OrderByElement
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.va.query.sql.parser.model.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.fromitemhandler.FromItemHandlerGather;
import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import com.jiuqi.va.query.sql.parser.selectitem.SelectItemHandlerGather;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PlainSelectHandler
implements IModelHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Model> getClazzType() {
        return PlainSelect.class;
    }

    @Override
    public Model doParser(Model srcModel, List<String> params) {
        PlainSelect plainSelect = (PlainSelect)srcModel;
        this.parserSelectItem(plainSelect, params);
        this.parserFromItem(plainSelect, params);
        this.parserJoins(plainSelect, params);
        plainSelect.setWhere(DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(plainSelect.getWhere(), params));
        this.parserGroupBy(plainSelect, params);
        plainSelect.setHaving(DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(plainSelect.getHaving(), params));
        this.parserOrderBy(plainSelect, params);
        return plainSelect;
    }

    private void parserOrderBy(PlainSelect plainSelect, List<String> params) {
        List orderByElements = plainSelect.getOrderByElements();
        if (CollectionUtils.isEmpty(orderByElements)) {
            return;
        }
        for (OrderByElement orderByElement : orderByElements) {
            Expression expression = orderByElement.getExpression();
            orderByElement.setExpression((Expression)this.modelHandlerGather.doParser((Model)expression, params));
        }
    }

    private void parserSelectItem(PlainSelect plainSelect, List<String> params) {
        List selectItems = plainSelect.getSelectItems();
        ArrayList<SelectItem> newSelectItems = new ArrayList<SelectItem>();
        for (SelectItem selectItem : selectItems) {
            SelectItem item = DCQuerySpringContextUtils.getBean(SelectItemHandlerGather.class).doParser(selectItem, params);
            if (item == null) continue;
            newSelectItems.add(item);
        }
        plainSelect.setSelectItems(newSelectItems);
    }

    private void parserFromItem(PlainSelect plainSelect, List<String> params) {
        FromItem fromItem = plainSelect.getFromItem();
        FromItem item = (FromItem)DCQuerySpringContextUtils.getBean(FromItemHandlerGather.class).doParser(fromItem, params);
        plainSelect.setFromItem(item);
    }

    private void parserJoins(PlainSelect plainSelect, List<String> params) {
        List joins = plainSelect.getJoins();
        if (CollectionUtils.isEmpty(joins)) {
            return;
        }
        for (Join join : joins) {
            FromItem rightItem = join.getRightItem();
            FromItem item = (FromItem)DCQuerySpringContextUtils.getBean(FromItemHandlerGather.class).doParser(rightItem, params);
            join.setRightItem(item);
            ArrayList<Expression> newOnExpression = new ArrayList<Expression>();
            for (Expression onExpression : join.getOnExpressions()) {
                Model model = this.modelHandlerGather.doParser((Model)onExpression, params);
                if (model == null) continue;
                newOnExpression.add((Expression)model);
            }
            join.setOnExpressions(newOnExpression);
        }
    }

    private void parserGroupBy(PlainSelect plainSelect, List<String> params) {
        GroupByElement groupBy = plainSelect.getGroupBy();
        if (groupBy == null) {
            return;
        }
        List expressions = groupBy.getGroupByExpressionList().getExpressions();
        ArrayList<Expression> newExpression = new ArrayList<Expression>();
        for (Expression expression : expressions) {
            Model model = this.modelHandlerGather.doParser((Model)expression, params);
            if (model == null) continue;
            newExpression.add((Expression)model);
        }
        groupBy.addGroupByExpressions(newExpression);
    }
}

