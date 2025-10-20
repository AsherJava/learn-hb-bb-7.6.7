/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.NotEqualsTo
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.impl.ComparisonOperatorHandler;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.springframework.stereotype.Component;

@Component
public class NotEqualsToHandler
extends ComparisonOperatorHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return NotEqualsTo.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        return super.doParser(srcExpression, params);
    }
}

