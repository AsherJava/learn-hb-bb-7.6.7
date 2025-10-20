/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.IParameter;
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
import org.springframework.util.ObjectUtils;

@Component
public class InListFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public InListFunction() {
        this.parameters().add(new Parameter("value", 6, "\u5f85\u68c0\u6d4b\u6587\u672c", false));
        this.parameters().add(new Parameter("accurate", 0, "\u662f\u5426\u7cbe\u51c6\u5339\u914d\uff0c\u9ed8\u8ba4\u4e3aFALSE\u8868\u793a\u4ee5\u5f00\u5934\u6a21\u7cca\u5339\u914d\uff0c\u4e3aTRUE\u65f6\u7cbe\u51c6\u5339\u914d", false));
        this.parameters().add(new Parameter("startStr", 0, "\u4f5c\u4e3a\u5f00\u5934\u7684\u5b57\u7b26\u4e32\uff0c\u53ef\u4ee5\u6709\u591a\u4e2a\uff0c\u591a\u4e2a\u7528\u82f1\u6587\u9017\u53f7\u9694\u5f00", true));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    @Override
    public String addDescribe() {
        return "\u5224\u65ad\u6307\u5b9a\u6587\u672c\u662f\u5426\u4ee5\u6307\u5b9a\u5b57\u7b26\u4e32\u5f00\u5934\uff0c\u652f\u6301\u7cbe\u51c6\u5339\u914d\u548c\u6a21\u7cca\u5339\u914d\uff0c\u4f5c\u4e3a\u8fc7\u6ee4\u6761\u4ef6\u65f6\u8fd4\u56de\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u3002";
    }

    public String name() {
        return "InList";
    }

    public String title() {
        return "\u5224\u65ad\u6307\u5b9a\u6587\u672c\u662f\u5426\u5728\u5217\u8868\u4e2d\u5b58\u5728";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
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
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5f85\u68c0\u6d4b\u5b57\u7b26\u4e32").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("accurate").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u7cbe\u51c6\u5339\u914d\uff0c\u53ef\u7701\u7565\uff0c\u4e3aTRUE\u65f6\u7cbe\u51c6\u5339\u914d\uff0c\u4e3aFALSE\u65f6\u4ee5\u5f00\u5934\u6a21\u7cca\u5339\u914d\uff0c\u9ed8\u8ba4\u4e3aFALSE").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("startStr").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u4f5c\u4e3a\u5f00\u5934\u7684\u5b57\u7b26\u4e32\uff0c\u53ef\u4ee5\u6709\u591a\u4e2a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5728\u62a5\u9500\u5355\u4e0a\u6dfb\u52a0\u5ba1\u6838\u516c\u5f0f\uff0c\u5224\u65ad\u4e8b\u7531[MEMO]\u662f\u5426\u4ee5\"\u501f\u6b3e\"\u5f00\u5934\uff0c\u662f\u5219\u901a\u8fc7\u5ba1\u6838\uff0c\u5426\u5219\u63d0\u793a\u9519\u8bef\u4fe1\u606f  ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("InList(FO_APPLOANBILL[MEMO], FALSE, \"\u501f\u6b3e\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("true");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("value", DataType.toString((int)6), "\u5f85\u68c0\u6d4b\u6587\u672c", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("accurate", DataType.toString((int)0), "\u662f\u5426\u7cbe\u51c6\u5339\u914d\uff0c\u9ed8\u8ba4\u4e3aFALSE\u8868\u793a\u4ee5\u5f00\u5934\u6a21\u7cca\u5339\u914d\uff0c\u4e3aTRUE\u65f6\u7cbe\u51c6\u5339\u914d", false);
        ParameterDescription parameterDescription2 = new ParameterDescription("startStr", DataType.toString((int)0), "\u4f5c\u4e3a\u5f00\u5934\u7684\u5b57\u7b26\u4e32\uff0c\u53ef\u4ee5\u6709\u591a\u4e2a\uff0c\u591a\u4e2a\u7528\u82f1\u6587\u9017\u53f7\u9694\u5f00", true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u6536\u6b3e\u4fe1\u606f\u8868\u5ba2\u5546\u7c7b\u578b\u4ee501\u621602\u5f00\u5934\u65f6\uff0c\u6536\u6b3e\u4fe1\u606f\u8868\u6536\u6b3e\u5355\u4f4d\u53ea\u8bfb", "if InList(left(FO_PAYACCOUNT[CUSTSUPPTYPE],2),'01','02')=false then true else false", "true", "\u6536\u6b3e\u4fe1\u606f\u8868\uff08FO_PAYACCOUNT\uff09\u4e2d\u5ba2\u5546\u7c7b\u578b\uff08CUSTSUPPTYPE\uff09\u4ee501\u621602\u5f00\u5934\u65f6\uff0c\u6536\u6b3e\u4fe1\u606f\u8868\u7684\u6536\u6b3e\u5355\u4f4d\u5b57\u6bb5\u53ea\u8bfb\uff0c\u5426\u5219\u6536\u6b3e\u5355\u4f4d\u5b57\u6bb5\u53ef\u7f16\u8f91\u3002");
        FormulaExample formulaExample1 = new FormulaExample("\u90e8\u95e8\u53ef\u9009\u8303\u56f4\u4e3a\u662f\u5426\u9690\u85cf\u4e0d\u4e3a1\u7684\u6570\u636e\u9879", "not InList([SFYC], '1')", "true", "\u90e8\u95e8\u5b57\u6bb5\u7684\u53ef\u9009\u8303\u56f4\u662f\u90e8\u95e8\u57fa\u7840\u6570\u636e\u8868\u4e2d\u662f\u5426\u9690\u85cf\uff08SFYC\uff09\u5b57\u6bb5\u4e0d\u4e3a1\u7684\u6761\u76ee\u3002");
        formulaExamples.add(formulaExample);
        formulaExamples.add(formulaExample1);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    @Override
    protected void printParamDeclaration(StringBuilder buffer) {
        boolean flag = false;
        for (IParameter p : this.parameters()) {
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(DataType.toExpression((int)p.dataType())).append(' ').append(p.name());
        }
        if (this.isInfiniteParameter() && !this.parameters().isEmpty()) {
            buffer.append(", ...");
        }
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object result = parameters.get(0).evaluate(context);
        if (result == null) {
            return false;
        }
        String text = result.toString();
        IASTNode node1 = parameters.get(1);
        Object node1Val = node1.evaluate(context);
        boolean accurate = false;
        if (!ObjectUtils.isEmpty(node1Val) && node1Val instanceof Boolean) {
            if (((Boolean)node1Val).booleanValue()) {
                accurate = true;
            }
            return this.handlerMatch(context, parameters, text, 2, accurate);
        }
        return this.handlerMatch(context, parameters, text, 1, accurate);
    }

    private Object handlerMatch(IContext context, List<IASTNode> parameters, String text, int index, boolean accurate) throws SyntaxException {
        for (int i = index; i < parameters.size(); ++i) {
            String[] values;
            IASTNode node = parameters.get(i);
            Object condition = node.evaluate(context);
            if (condition == null) continue;
            int type = node.getType(context);
            if (11 == type || condition instanceof ArrayData) {
                ArrayData data = (ArrayData)condition;
                for (Object value : data.toList()) {
                    if (value == null || !(accurate ? text.equals(value.toString()) : text.startsWith(value.toString()))) continue;
                    return true;
                }
                continue;
            }
            if (accurate) {
                if (text.equals(condition.toString())) {
                    return true;
                }
                values = condition.toString().split(",");
                if (values.length == 0) continue;
                for (String value : values) {
                    if (!text.equals(value)) continue;
                    return true;
                }
                continue;
            }
            if (text.startsWith(condition.toString())) {
                return true;
            }
            values = condition.toString().split(",");
            if (values.length == 0) continue;
            for (String value : values) {
                if (!text.startsWith(value)) continue;
                return true;
            }
        }
        return false;
    }
}

