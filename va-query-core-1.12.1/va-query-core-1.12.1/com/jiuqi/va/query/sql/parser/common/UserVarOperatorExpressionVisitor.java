/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  net.sf.jsqlparser.expression.BinaryExpression
 *  net.sf.jsqlparser.expression.UserVariable
 *  net.sf.jsqlparser.expression.operators.relational.EqualsTo
 *  net.sf.jsqlparser.expression.operators.relational.InExpression
 *  net.sf.jsqlparser.expression.operators.relational.JsonOperator
 *  net.sf.jsqlparser.util.deparser.ExpressionDeParser
 */
package com.jiuqi.va.query.sql.parser.common;

import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.springframework.util.CollectionUtils;

public class UserVarOperatorExpressionVisitor
extends ExpressionDeParser {
    private Map<String, List<QueryModeEnum>> userVarOperatorMap = new HashMap<String, List<QueryModeEnum>>();

    public void visit(JsonOperator jsonExpr) {
        this.visitBinaryExpression((BinaryExpression)jsonExpr, " " + jsonExpr.getStringExpression());
    }

    public void visit(InExpression inExpression) {
        if (inExpression.getRightExpression() instanceof UserVariable) {
            String userVar = inExpression.getRightExpression().toString();
            List<QueryModeEnum> queryModeEnums = this.userVarOperatorMap.get(userVar);
            if (CollectionUtils.isEmpty(queryModeEnums)) {
                queryModeEnums = new ArrayList<QueryModeEnum>();
            }
            queryModeEnums.add(QueryModeEnum.mutileData);
            this.userVarOperatorMap.put(userVar, queryModeEnums);
        }
        super.visit(inExpression);
    }

    public void visit(EqualsTo equalsTo) {
        if (equalsTo.getRightExpression() instanceof UserVariable) {
            String userVar = equalsTo.getRightExpression().toString();
            List<QueryModeEnum> queryModeEnums = this.userVarOperatorMap.get(userVar);
            if (CollectionUtils.isEmpty(queryModeEnums)) {
                queryModeEnums = new ArrayList<QueryModeEnum>();
            }
            queryModeEnums.add(QueryModeEnum.singleData);
            this.userVarOperatorMap.put(userVar, queryModeEnums);
        }
        super.visit(equalsTo);
    }

    public Map<String, List<QueryModeEnum>> getUserVarOperatorMap() {
        return this.userVarOperatorMap;
    }

    public void setUserVarOperatorMap(Map<String, List<QueryModeEnum>> userVarOperatorMap) {
        this.userVarOperatorMap = userVarOperatorMap;
    }
}

