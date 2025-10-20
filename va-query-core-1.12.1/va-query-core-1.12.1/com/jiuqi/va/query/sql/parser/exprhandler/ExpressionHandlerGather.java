/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 */
package com.jiuqi.va.query.sql.parser.exprhandler;

import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.sql.parser.common.ParserUtil;
import com.jiuqi.va.query.sql.parser.common.SqlParserHandlerCollection;
import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import org.springframework.stereotype.Component;

@Component
public class ExpressionHandlerGather {
    public Expression doParser(Expression srcExpression, List<String> params) {
        if (srcExpression == null) {
            return null;
        }
        if (params == null || params.isEmpty()) {
            return srcExpression;
        }
        String exprSql = srcExpression.toString();
        if (!ParserUtil.existParams(exprSql, params)) {
            return srcExpression;
        }
        IExpressionHandler expressionHandler = DCQuerySpringContextUtils.getBean(SqlParserHandlerCollection.class).getExpressionHandlerMap().get(srcExpression.getClass());
        if (expressionHandler == null) {
            IModelHandler iModelHandler = DCQuerySpringContextUtils.getBean(SqlParserHandlerCollection.class).getModelHandlerMap().get(srcExpression.getClass());
            if (iModelHandler != null) {
                return (Expression)iModelHandler.doParser((Model)srcExpression, params);
            }
            throw new DefinedQuerySqlException(String.format("\u4e0d\u652f\u6301\u3010%1$s\u3011\u7c7b\u578b\u8868\u8fbe\u5f0f\u7684\u53c2\u6570\u89e3\u6790\uff01", srcExpression.getClass()));
        }
        return expressionHandler.doParser(srcExpression, params);
    }
}

