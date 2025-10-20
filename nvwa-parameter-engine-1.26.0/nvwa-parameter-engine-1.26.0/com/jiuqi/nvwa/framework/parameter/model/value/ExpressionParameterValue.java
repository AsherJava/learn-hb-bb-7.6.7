/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model.value;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ExpressionParameterValue
extends AbstractParameterValue {
    private static final long serialVersionUID = 1L;
    private String formula;

    public ExpressionParameterValue(String formula) {
        this.formula = formula;
    }

    public String getFormula() {
        return this.formula;
    }

    @Override
    public Object getValue() {
        return this.formula;
    }

    @Override
    public boolean isFormulaValue() {
        return true;
    }

    @Override
    public String getValueMode() {
        return "expr";
    }

    @Override
    public void fromJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        this.formula = json.optString("value");
        this.binding = json.optString("binding", null);
    }

    @Override
    public List<Object> toValueList(IParameterValueFormat format) throws ParameterException {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(this.formula);
        return list;
    }

    @Override
    public List<Object> filterValue(List<?> candidateValue) throws ParameterException {
        Object v;
        FormulaParser parser = FormulaParser.getInstance();
        try {
            IExpression expr = parser.parseEval(this.formula, null);
            v = expr.evaluate(null);
        }
        catch (SyntaxException e) {
            throw new ParameterException(e.getMessage(), e);
        }
        if (candidateValue.contains(v)) {
            return Arrays.asList(v);
        }
        return new ArrayList<Object>();
    }

    @Override
    public AbstractParameterValue clone() {
        return new ExpressionParameterValue(this.formula);
    }
}

