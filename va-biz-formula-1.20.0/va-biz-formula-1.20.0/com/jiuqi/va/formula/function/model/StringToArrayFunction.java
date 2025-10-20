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
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StringToArrayFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public StringToArrayFunction() {
        this.parameters().add(new Parameter("text", 6, "\u5b57\u7b26\u4e32", false));
        this.parameters().add(new Parameter("separate", 6, "\u5206\u9694\u7b26", true));
    }

    @Override
    public String addDescribe() {
        return "\u5b57\u7b26\u4e32\u6309\u7167\u6307\u5b9a\u5206\u9694\u7b26\u8f6c\u6362\u6210\u6570\u7ec4\uff0c\u5206\u9694\u7b26\u4e0d\u4f20\u9ed8\u8ba4\u4e3a\u82f1\u6587\u9017\u53f7";
    }

    public String name() {
        return "StringToArray";
    }

    public String title() {
        return "\u5b57\u7b26\u4e32\u8f6c\u6362\u6210\u6570\u7ec4";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String text = (String)parameters.get(0).evaluate(context);
        if (!StringUtils.hasText(text)) {
            return new ArrayData(6, new ArrayList());
        }
        String separate = parameters.size() == 1 ? "," : (String)parameters.get(1).evaluate(context);
        return new ArrayData(6, Arrays.asList(text.split(separate)));
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
        buffer.append("    ").append("text\uff1a").append(DataType.toString((int)6)).append("\uff1b \u5b57\u7b26\u4e32").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("separate\uff1a").append(DataType.toString((int)6)).append("\uff1b \u5206\u9694\u7b26\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4f7f\u7528\",\"").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)11)).append("\uff1a").append(DataType.toString((int)11)).append("\uff1b\u8fd4\u56de\u8f6c\u6362\u7684\u6570\u7ec4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5c06\"\u5b57\u7b261,\u5b57\u7b262\"\u6309\",\"\u5206\u9694\u7b26\u8f6c\u6362\u4e3a\u6570\u7ec4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("StringToArray(\"\u5b57\u7b261,\u5b57\u7b262\",\",\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("[\"\u5b57\u7b261\",\"\u5b57\u7b262\"]");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)11) + "\uff1a" + DataType.toString((int)11) + "\uff1b\u8fd4\u56de\u8f6c\u6362\u7684\u6570\u7ec4");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("text", DataType.toString((int)6), "\u5b57\u7b26\u4e32", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("separate", DataType.toString((int)6), "\u5206\u9694\u7b26\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4f7f\u7528\",\"", false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u5c06\"\u5b57\u7b261,\u5b57\u7b262\"\u6309\",\"\u5206\u9694\u7b26\u8f6c\u6362\u4e3a\u6570\u7ec4", "StringToArray(\"\u5b57\u7b261,\u5b57\u7b262\",\",\")", "[\"\u5b57\u7b261\",\"\u5b57\u7b262\"]");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

