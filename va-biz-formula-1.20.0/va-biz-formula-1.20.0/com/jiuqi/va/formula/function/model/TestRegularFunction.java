/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TestRegularFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public TestRegularFunction() {
        this.parameters().add(new Parameter("value", 0, "\u5339\u914d\u5185\u5bb9", false));
        this.parameters().add(new Parameter("regular", 6, "\u6b63\u5219\u8868\u8fbe\u5f0f", false));
    }

    @Override
    public String addDescribe() {
        return "\u6b63\u5219\u8868\u8fbe\u5f0f\u5339\u914d\u51fd\u6570\u3002\u6821\u9a8c\u5b57\u7b26\u4e32\u662f\u5426\u5339\u914d\u6b63\u5219\u8868\u8fbe\u5f0f\u7684\u53e5\u6cd5\u89c4\u5219";
    }

    public String name() {
        return "TestRegular";
    }

    public String title() {
        return "\u6b63\u5219\u8868\u8fbe\u5f0f\u5339\u914d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String str = (String)parameters.get(0).evaluate(context);
        if (str == null || str.length() == 0) {
            return true;
        }
        String regex = (String)parameters.get(1).evaluate(context);
        return str.matches(regex);
    }

    @Override
    public void toJavaScript(StringBuilder builder, List<IASTNode> parameters) throws ToJavaScriptException {
        StringBuilder strBuilder = new StringBuilder();
        JavaScriptNodeProvider.get(parameters.get(0).getNodeType()).toJavaScript(parameters.get(0), strBuilder, false);
        StringBuilder regexBuilder = new StringBuilder();
        JavaScriptNodeProvider.get(parameters.get(1).getNodeType()).toJavaScript(parameters.get(1), regexBuilder, false);
        regexBuilder.setLength(regexBuilder.length() - 1);
        builder.append(String.format("new RegExp('%s').test(%s)", regexBuilder.substring(1), strBuilder));
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
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5339\u914d\u5185\u5bb9").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("regular").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u6b63\u5219\u8868\u8fbe\u5f0f").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u67e5\u770bMD_TABLE\u8868\u7684NUMBER\u5b57\u6bb5\u662f\u5426\u662f\u6b63\u6574\u6570\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("TestRegular(MD_TABLE[NUMBER],\"/[1-9][0-9]*/\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("NUMBER\u5b57\u6bb5\u662f\u6b63\u6574\u6570\u5219\u8fd4\u56detrue\uff0c\u5426\u5219\u8fd4\u56defalse");
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
        ParameterDescription parameterDescription = new ParameterDescription("value", DataType.toString((int)6), "\u5339\u914d\u5185\u5bb9", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("regular", DataType.toString((int)0), "\u6b63\u5219\u8868\u8fbe\u5f0f", false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u67e5\u770bMD_TABLE\u8868\u7684NUMBER\u5b57\u6bb5\u662f\u5426\u662f\u6b63\u6574\u6570\u3002", "TestRegular(MD_TABLE[NUMBER],\"/[1-9][0-9]*/\")", "NUMBER\u5b57\u6bb5\u662f\u6b63\u6574\u6570\u5219\u8fd4\u56detrue\uff0c\u5426\u5219\u8fd4\u56defalse");
        formulaExamples.add(formulaExample);
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
}

