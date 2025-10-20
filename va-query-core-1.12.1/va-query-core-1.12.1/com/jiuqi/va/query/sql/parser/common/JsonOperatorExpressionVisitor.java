/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.BinaryExpression
 *  net.sf.jsqlparser.expression.operators.relational.JsonOperator
 *  net.sf.jsqlparser.util.deparser.ExpressionDeParser
 */
package com.jiuqi.va.query.sql.parser.common;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;

public class JsonOperatorExpressionVisitor
extends ExpressionDeParser {
    public void visit(JsonOperator jsonExpr) {
        this.visitBinaryExpression((BinaryExpression)jsonExpr, " " + jsonExpr.getStringExpression());
    }

    protected void visitBinaryExpression(BinaryExpression binaryExpression, String operator) {
        super.visitBinaryExpression(binaryExpression, operator);
    }
}

