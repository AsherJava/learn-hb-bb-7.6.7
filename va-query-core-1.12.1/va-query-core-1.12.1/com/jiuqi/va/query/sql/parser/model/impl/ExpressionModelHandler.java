/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 */
package com.jiuqi.va.query.sql.parser.model.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import org.springframework.stereotype.Component;

@Component
public class ExpressionModelHandler
implements IModelHandler {
    @Override
    public Class<? extends Model> getClazzType() {
        return Expression.class;
    }

    @Override
    public Model doParser(Model srcModel, List<String> params) {
        Expression expression = (Expression)srcModel;
        return DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(expression, params);
    }
}

