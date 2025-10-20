/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ArrayToStringFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public ArrayToStringFunction() {
        this.parameters().add(new Parameter("array", 0, "\u6570\u7ec4", false));
        this.parameters().add(new Parameter("sep", 6, "\u5206\u9694\u7b26", true));
        this.parameters().add(new Parameter("deduplication", 1, "\u53bb\u91cd", true));
    }

    @Override
    public String addDescribe() {
        return "\u5c06\u6570\u7ec4\u8f6c\u6362\u4e3a\u5b57\u7b26\u4e32";
    }

    public String name() {
        return "ArrayToString";
    }

    public String title() {
        return "\u6570\u7ec4\u8f6c\u5b57\u7b26\u4e32\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String evaluate;
        ArrayList<String> dataSources = new ArrayList<String>();
        Object array = parameters.get(0).evaluate(context);
        boolean deduplication = false;
        if (parameters.size() > 2) {
            deduplication = (Boolean)parameters.get(2).evaluate(context);
        }
        if (array instanceof ArrayData) {
            ArrayData arrayData = (ArrayData)array;
            int j = arrayData.size();
            for (int i = 0; i < j; ++i) {
                String s = String.valueOf(arrayData.get(i));
                if (deduplication && dataSources.contains(s)) continue;
                dataSources.add(s);
            }
        } else {
            dataSources.add(String.valueOf(array));
        }
        if (CollectionUtils.isEmpty(dataSources)) {
            return "";
        }
        String sep = ",";
        if (parameters.size() > 1 && (evaluate = (String)parameters.get(1).evaluate(context)) != null) {
            sep = evaluate;
        }
        StringBuilder result = new StringBuilder();
        int j = dataSources.size();
        for (int i = 0; i < j; ++i) {
            result.append((String)dataSources.get(i)).append(sep);
        }
        return result.substring(0, result.length() - sep.length());
    }

    @Override
    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("array\uff1a").append(DataType.toString((int)0)).append("\uff1b \u6570\u7ec4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("sep\uff1a").append(DataType.toString((int)6)).append("\uff1b \u5206\u9694\u7b26\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4f7f\u7528\",\"").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("deduplication\uff1a").append(DataType.toString((int)1)).append("\uff1b \u53bb\u91cd\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4false").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u8fd4\u56de\u8f6c\u6362\u7684\u5b57\u7b26\u4e32").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5c06\u51b2\u501f\u6b3e\u603b\u91d1\u989d\u3001\u62a5\u9500\u603b\u91d1\u989d\u3001\u5b9e\u9645\u652f\u4ed8\u91d1\u989d\u4e09\u4e2a\u5b57\u6bb5\u7684\u503c\u8f6c\u6362\u4e3a\u6570\u7ec4\u540e\u6309\u9ed8\u8ba4\u5206\u9694\u7b26\u8f6c\u6362\u4e3a\u5b57\u7b26\u4e32").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ArrayToString(ToArray(FO_EXPENSEBILL[LOANMONEY],FO_EXPENSEBILL[BILLMONEY],FO_EXPENSEBILL[PAYMONEY]),\"---\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("45456.0---45456.0---0.0");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8fd4\u56de\u8f6c\u6362\u7684\u5b57\u7b26\u4e32");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("array");
        parameterDescription.setType(DataType.toString((int)0));
        parameterDescription.setDescription("\u6570\u7ec4");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("sep");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u5206\u9694\u7b26\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4f7f\u7528\",\"");
        parameterDescription1.setRequired(false);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("deduplication");
        parameterDescription2.setType(DataType.toString((int)1));
        parameterDescription2.setDescription("\u53bb\u91cd\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4false");
        parameterDescription2.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u5c06\u51b2\u501f\u6b3e\u603b\u91d1\u989d\u3001\u62a5\u9500\u603b\u91d1\u989d\u3001\u5b9e\u9645\u652f\u4ed8\u91d1\u989d\u4e09\u4e2a\u5b57\u6bb5\u7684\u503c\u8f6c\u6362\u4e3a\u6570\u7ec4\u540e\u6309\u9ed8\u8ba4\u5206\u9694\u7b26\u8f6c\u6362\u4e3a\u5b57\u7b26\u4e32");
        formulaExample.setFormula("ArrayToString(ToArray(FO_EXPENSEBILL[LOANMONEY],FO_EXPENSEBILL[BILLMONEY],FO_EXPENSEBILL[PAYMONEY]),\"---\")");
        formulaExample.setReturnValue("45456.0---45456.0---0.0");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

