/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.query.sql.formula.QueryFormulaContext
 *  com.jiuqi.va.query.sql.formula.QueryFormulaHandler
 */
package com.jiuqi.va.query.common.service;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import com.jiuqi.va.query.sql.formula.QueryFormulaHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class FormulaExecuteHandlerUtil {
    private static QueryFormulaHandler queryFormulaHandler = null;

    private FormulaExecuteHandlerUtil() {
    }

    public static Object getFormulaEvaluateData(String formulaString, String paramType) {
        if (DCQueryStringHandle.isEmpty(formulaString)) {
            return "";
        }
        try {
            return queryFormulaHandler.evaluateData(formulaString, paramType);
        }
        catch (NoSuchBeanDefinitionException e) {
            return formulaString;
        }
    }

    public static Object executeFormula(String formulaString, String paramType) {
        if (DCQueryStringHandle.isEmpty(formulaString)) {
            return "";
        }
        return queryFormulaHandler.evaluateData(formulaString, paramType);
    }

    public static Object executeFormula(String formulaString, String paramType, QueryFormulaContext context) {
        if (DCQueryStringHandle.isEmpty(formulaString)) {
            return "";
        }
        return queryFormulaHandler.evaluateData(formulaString, paramType, context);
    }

    public static boolean judgeFormula(String formulaString, String paramType, QueryFormulaContext context) {
        return queryFormulaHandler.judgeFormula(formulaString, paramType, context);
    }

    public static IExpression parseIExpression(String expression, QueryFormulaContext queryFormulaContext) throws ParseException {
        return queryFormulaHandler.parseIExpression(expression, queryFormulaContext);
    }

    public static Object executeFormula(IExpression iExpression, String typeName, QueryFormulaContext queryFormulaContext) {
        return queryFormulaHandler.executeFormula(iExpression, typeName, queryFormulaContext);
    }

    static {
        queryFormulaHandler = DCQuerySpringContextUtils.getBean(QueryFormulaHandler.class);
    }
}

