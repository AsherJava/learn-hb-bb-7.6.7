/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.va.query.sql.formula;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import java.util.List;

public interface QueryFormulaHandler {
    public boolean check(String var1);

    public Object evaluateData(String var1, String var2);

    public Object evaluateData(String var1, String var2, QueryFormulaContext var3);

    public List<String> getFieldInExpression(String var1, QueryFormulaContext var2);

    public boolean judgeFormula(String var1, String var2, QueryFormulaContext var3);

    public IExpression parseIExpression(String var1, QueryFormulaContext var2) throws ParseException;

    public Object executeFormula(IExpression var1, String var2, QueryFormulaContext var3);
}

