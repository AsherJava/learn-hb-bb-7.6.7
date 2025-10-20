/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.UserVariable
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.UserVariable;
import org.springframework.stereotype.Component;

@Component
public class UserVariableHandler
implements IExpressionHandler {
    @Override
    public Class<? extends Expression> getClazzType() {
        return UserVariable.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        UserVariable userVariable = (UserVariable)srcExpression;
        int paramSize = params.size();
        for (int paramIndex = 0; paramIndex < paramSize; ++paramIndex) {
            String paramName = params.get(paramIndex);
            if (!("@" + paramName).equalsIgnoreCase(userVariable.toString())) continue;
            return null;
        }
        return userVariable;
    }
}

