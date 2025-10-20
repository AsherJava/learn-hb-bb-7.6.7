/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LenBFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public LenBFunction() {
        this.parameters().add(new Parameter("text", 6, "\u5b57\u7b26\u4e32\u503c", false));
        this.parameters().add(new Parameter("charsetName", 6, "\u5b57\u7b26\u96c6\u540d\u79f0", true));
    }

    @Override
    public String addDescribe() {
        return "\u6c42\u5b57\u7b26\u4e32\u7684\u5b57\u8282\u957f\u5ea6";
    }

    public String name() {
        return "LenB";
    }

    public String title() {
        return "\u6c42\u5b57\u7b26\u4e32\u7684\u5b57\u8282\u957f\u5ea6";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
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

    public void toDeclaration(StringBuilder buffer) {
        try {
            int retType = this.getResultType(null, null);
            buffer.append(DataType.toExpression((int)retType)).append(' ').append(this.name()).append('(');
        }
        catch (SyntaxException e) {
            throw new SyntaxRuntimeException((Throwable)e);
        }
        this.printParamDeclaration(buffer);
        buffer.append(");");
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
        buffer.append("    ").append("text").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5b57\u7b26\u4e32\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("charsetName").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5b57\u7b26\u96c6\u540d\u79f0\uff0c\u53ef\u7701\u7565\uff1b\u9ed8\u8ba4\u4e3a\u5f53\u524d\u7cfb\u7edf\u7684\u5b57\u7b26\u96c6\uff1b\u5e38\u7528\u5b57\u7b26\u96c6\u6709GBK\u3001GB2312\u3001UTF-8\u7b49").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u8fd4\u56de\u5b57\u7b26\u4e32\u7684\u5b57\u8282\u957f\u5ea6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u5b57\u7b26\u4e32\"hello\u5f20\u4e09\"\u7684\u5b57\u8282\u957f\u5ea6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("LenB(\"hello\u5f20\u4e09\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("9");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)3) + "\uff1a" + DataType.toString((int)3) + "\uff1b\u8fd4\u56de\u5b57\u7b26\u4e32\u7684\u5b57\u8282\u957f\u5ea6");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("text", DataType.toString((int)6), "\u5b57\u7b26\u4e32\u503c", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("charsetName", DataType.toString((int)6), "\u5b57\u7b26\u96c6\u540d\u79f0\uff0c\u53ef\u7701\u7565\uff1b\u9ed8\u8ba4\u4e3a\u5f53\u524d\u7cfb\u7edf\u7684\u5b57\u7b26\u96c6\uff1b\u5e38\u7528\u5b57\u7b26\u96c6\u6709GBK\u3001GB2312\u3001UTF-8\u7b49", false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u5b57\u7b26\u4e32\"hello\u5f20\u4e09\"\u7684\u5b57\u8282\u957f\u5ea6", "LenB(\"hello\u5f20\u4e09\")", "9");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        Object obj = node0.evaluate(context);
        if (obj == null) {
            return 0;
        }
        String str = (String)obj;
        if (!StringUtils.hasText(str)) {
            return 0;
        }
        if (parameters.size() == 1) {
            return str.getBytes().length;
        }
        Object charset = parameters.get(1).evaluate(context);
        if (charset == null || !StringUtils.hasText(charset.toString())) {
            return str.getBytes().length;
        }
        try {
            return str.getBytes(charset.toString()).length;
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

