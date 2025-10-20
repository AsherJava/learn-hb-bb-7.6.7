/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.schema.Column
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Component;

@Component
public class ColumnHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return Column.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        return srcExpression;
    }
}

