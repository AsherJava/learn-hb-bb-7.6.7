/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.CaseExpression
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.NullValue
 */
package com.jiuqi.va.query.sql.parser.exprhandler.impl;

import com.jiuqi.va.query.sql.parser.exprhandler.ExpressionHandlerGather;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaseExpressionHandler
implements IExpressionHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Expression> getClazzType() {
        return CaseExpression.class;
    }

    @Override
    public Expression doParser(Expression srcExpression, List<String> params) {
        Expression expression;
        CaseExpression caseExpression = (CaseExpression)srcExpression;
        Expression switchExp = caseExpression.getSwitchExpression();
        if (switchExp != null && (expression = DCQuerySpringContextUtils.getBean(ExpressionHandlerGather.class).doParser(switchExp, params)) == null) {
            caseExpression.setSwitchExpression((Expression)new NullValue());
        }
        for (Expression exp : caseExpression.getWhenClauses()) {
            this.modelHandlerGather.doParser((Model)exp, params);
        }
        Expression elseExp = caseExpression.getElseExpression();
        Model model = this.modelHandlerGather.doParser((Model)elseExp, params);
        if (model == null) {
            caseExpression.setElseExpression((Expression)new NullValue());
        }
        return caseExpression;
    }
}

