/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 */
package com.jiuqi.va.query.sql.parser.exprhandler;

import java.util.List;
import net.sf.jsqlparser.expression.Expression;

public interface IExpressionHandler {
    public Class<? extends Expression> getClazzType();

    public Expression doParser(Expression var1, List<String> var2);
}

