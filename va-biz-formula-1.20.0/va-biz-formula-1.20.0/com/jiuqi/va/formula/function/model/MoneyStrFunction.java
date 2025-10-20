/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.ChineseMoneyUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MoneyStrFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public MoneyStrFunction() {
        this.parameters().add(new Parameter("money", 3, "\u91d1\u989d", false));
        this.parameters().add(new Parameter("prefix", 6, "\u524d\u7f00", false));
        this.parameters().add(new Parameter("suffix", 6, "\u540e\u7f00", false));
    }

    @Override
    public String addDescribe() {
        return "\u5c06\u6570\u503c\u91d1\u989d\u8f6c\u6362\u4e3a\u4e2d\u6587\u5927\u5199\u6570\u5b57";
    }

    public String name() {
        return "MoneyStr";
    }

    public String title() {
        return "\u4e2d\u6587\u91d1\u989d\uff08\u5b57\u7b26\u51fd\u6570\uff09";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object money = parameters.get(0).evaluate(context);
        String prefix = parameters.size() > 1 ? (String)parameters.get(1).evaluate(context) : "";
        String suffix = parameters.size() > 2 ? (String)parameters.get(2).evaluate(context) : "";
        prefix = prefix == null ? "" : prefix;
        suffix = suffix == null ? "" : suffix;
        String res = null;
        try {
            if (money == null) {
                money = 0.0;
            }
            res = prefix + ChineseMoneyUtil.toChineseMoney(new BigDecimal(money.toString())) + suffix;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return res;
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
        buffer.append("    ").append("money\uff1a").append(DataType.toString((int)3)).append("\uff1b \u91d1\u989d").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("prefix\uff1a").append(DataType.toString((int)6)).append("\uff1b \u524d\u7f00").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("suffix\uff1a").append(DataType.toString((int)6)).append("\uff1b \u540e\u7f00").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u524d\u7f00+\u4e2d\u6587\u91d1\u989d+\u540e\u7f00").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u91d1\u989d\u5408\u8ba1").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("MoneyStr(123, \"\u5408\u8ba1\uff1a\", \"\u4eba\u6c11\u5e01\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5408\u8ba1\uff1a\u58f9\u4f70\u8d30\u62fe\u53c1\u5143\u6574\u4eba\u6c11\u5e01");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u524d\u7f00+\u4e2d\u6587\u91d1\u989d+\u540e\u7f00");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("money");
        parameterDescription.setType(DataType.toString((int)3));
        parameterDescription.setDescription("\u91d1\u989d");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("prefix");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u524d\u7f00");
        parameterDescription1.setRequired(false);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("suffix");
        parameterDescription2.setType(DataType.toString((int)6));
        parameterDescription2.setDescription("\u540e\u7f00");
        parameterDescription2.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u91d1\u989d\u5408\u8ba1");
        formulaExample.setFormula("MoneyStr(123, \"\u5408\u8ba1\uff1a\", \"\u4eba\u6c11\u5e01\")");
        formulaExample.setReturnValue("\u5408\u8ba1\uff1a\u58f9\u4f70\u8d30\u62fe\u53c1\u5143\u6574\u4eba\u6c11\u5e01");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

