/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.LikeExpression
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.common.ParserUtil;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import org.springframework.stereotype.Component;

@Component
public class LikeExpressionHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return LikeExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        if (params == null || params.isEmpty()) {
            return srcExpression;
        }
        String exprSql = srcExpression.toString();
        if (!ParserUtil.existParams(exprSql, params)) {
            return srcExpression;
        }
        return null;
    }
}

