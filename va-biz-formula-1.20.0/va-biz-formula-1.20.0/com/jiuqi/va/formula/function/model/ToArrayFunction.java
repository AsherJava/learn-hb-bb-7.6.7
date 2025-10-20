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
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ToArrayFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public ToArrayFunction() {
        this.parameters().add(new Parameter("value", 0, "\u503c\u5185\u5bb9", false));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    @Override
    public String addDescribe() {
        StringBuffer sb = new StringBuffer().append("\u591a\u503c\u8f6c\u6362\u6570,\u53c2\u6570\u53ef\u4ee5\u8f93\u5165\u4efb\u610f\u7c7b\u578b\uff0c\u4f46\u662f\u8981\u4fdd\u8bc1\u6240\u6709\u53c2\u6570\u7c7b\u578b\u4e00\u81f4\u3002").append("\u8fd4\u56de\u751f\u6210\u7684\u6570\u7ec4\u3002");
        return sb.toString();
    }

    public String name() {
        return "ToArray";
    }

    public String title() {
        return "\u591a\u503c\u8f6c\u6362\u6570\u7ec4";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int baseType = parameters.get(0).getType(context);
        IASTNode node = parameters.stream().filter(parameter -> {
            try {
                return parameter.getType(context) != baseType;
            }
            catch (SyntaxException e) {
                throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.toarray.execute.exception"), e);
            }
        }).findFirst().orElse(null);
        if (node != null) {
            throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.param.type.not.same"));
        }
        ArrayList list = new ArrayList();
        parameters.forEach(parameter -> {
            try {
                list.add(parameter.evaluate(context));
            }
            catch (SyntaxException e) {
                throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.toarray.execute.exception"), e);
            }
        });
        return new ArrayData(baseType, list);
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
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u503c\u5185\u5bb9,\u53ef\u4ee5\u6709\u591a\u4e2a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)11)).append("\uff1a").append(DataType.toString((int)11)).append(";\u8fd4\u56de\u751f\u6210\u7684\u6570\u7ec4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5c06\u51b2\u501f\u6b3e\u603b\u91d1\u989d\u3001\u62a5\u9500\u603b\u91d1\u989d\u3001\u5b9e\u9645\u652f\u4ed8\u91d1\u989d\u4e09\u4e2a\u5b57\u6bb5\u7684\u503c\u8f6c\u6362\u4e3a\u6570\u7ec4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ToArray(FO_EXPENSEBILL[LOANMONEY],FO_EXPENSEBILL[BILLMONEY],FO_EXPENSEBILL[PAYMONEY])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("[343.5,343.5,0.0]");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)11) + "\uff1a" + DataType.toString((int)11) + ";\u8fd4\u56de\u751f\u6210\u7684\u6570\u7ec4");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("value", DataType.toString((int)0), "\u503c\u5185\u5bb9,\u53ef\u4ee5\u6709\u591a\u4e2a", true);
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u5c06\u51b2\u501f\u6b3e\u603b\u91d1\u989d\u3001\u62a5\u9500\u603b\u91d1\u989d\u3001\u5b9e\u9645\u652f\u4ed8\u91d1\u989d\u4e09\u4e2a\u5b57\u6bb5\u7684\u503c\u8f6c\u6362\u4e3a\u6570\u7ec4", "ToArray(FO_EXPENSEBILL[LOANMONEY],FO_EXPENSEBILL[BILLMONEY],FO_EXPENSEBILL[PAYMONEY])", "[343.5,343.5,0.0]");
        FormulaExample formulaExample1 = new FormulaExample("\u62a5\u9500\u5355\u6c47\u603b\u5b50\u8868\u7ecf\u6d4e\u4e8b\u9879\u8fc7\u6ee4\u53ef\u9009\u8303\u56f4", "[CODE] in toArray('0140','0145','014501','014510')", "['0140','0145','014501','014510']");
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

    @Override
    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append('(');
        boolean flag = false;
        for (IASTNode p : parameters) {
            if (flag) {
                buffer.append(',');
            } else {
                flag = true;
            }
            p.interpret(context, buffer, Language.SQL, (Object)info);
        }
        buffer.append(')');
    }
}

