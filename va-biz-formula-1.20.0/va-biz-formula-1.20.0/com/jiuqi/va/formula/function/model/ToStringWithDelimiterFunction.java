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
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ToStringWithDelimiterFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public ToStringWithDelimiterFunction() {
        this.parameters().add(new Parameter("delimiter", 6, "\u5206\u9694\u7b26", false));
        this.parameters().add(new Parameter("value", 0, "\u62fc\u63a5\u5185\u5bb9", false));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    @Override
    public String addDescribe() {
        return "\u5c06\u6240\u6709\u53c2\u6570\u7ec4\u6210\u6307\u5b9a\u5206\u9694\u7b26\u9694\u5f00\u7684\u5b57\u7b26\u4e32\u3002";
    }

    public String name() {
        return "ToStringWithDelimiter";
    }

    public String title() {
        return "\u4ee5\u6307\u5b9a\u5206\u9694\u7b26\u62fc\u63a5\u5b57\u7b26\u4e32";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int baseType = parameters.get(0).getType(context);
        if (baseType != 6) {
            throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.split.param.not.string"));
        }
        String delimiter = (String)parameters.get(0).evaluate(context);
        int size = parameters.size();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < size; ++i) {
            Object value = parameters.get(i).evaluate(context);
            if (value instanceof Calendar) {
                value = this.formatDate((Calendar)value);
            }
            sb.append(value.toString());
            if (i == size - 1) continue;
            sb.append(delimiter);
        }
        return sb.toString();
    }

    private String formatDate(Calendar date) {
        SimpleDateFormat sdf = null;
        String dateStr = null;
        try {
            if (date.getTime().equals(new Date(0L))) {
                return "";
            }
            if (date.get(10) == 0 && date.get(12) == 0 && date.get(13) == 0) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = sdf.format(date.getTime());
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStr = sdf.format(date.getTime());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.date.convert.fail"));
        }
        return dateStr;
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
        buffer.append("    ").append("delimiter").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5206\u9694\u7b26\uff0c\u53ef\u4ee5\u4e3a\u7a7a\u5b57\u7b26\u4e32").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u62fc\u63a5\u5185\u5bb9").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5728\u4e8b\u9879\u7533\u8bf7\u5355\uff0c\u8bbe\u7f6e\u4fdd\u5b58\u65f6\u5907\u6ce8\u5b57\u6bb5\u7684\u503c  ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ToStringWithDelimiter(\"-\", \"\u91d1\u989d\uff1a\" + FO_EXPENSEBILL[BILLMONEY],\"\u65e5\u671f\uff1a\"+FO_EXPENSEBILL[BILLDATE])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u91d1\u989d\uff1a9645.3-\u65e5\u671f\uff1a2020-07-16");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("delimiter", DataType.toString((int)6), "\u5206\u9694\u7b26\uff0c\u53ef\u4ee5\u4e3a\u7a7a\u5b57\u7b26\u4e32", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("value", DataType.toString((int)0), "\u62fc\u63a5\u5185\u5bb9", true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u5728\u4e8b\u9879\u7533\u8bf7\u5355\uff0c\u8bbe\u7f6e\u4fdd\u5b58\u65f6\u5907\u6ce8\u5b57\u6bb5\u7684\u503c ", "ToStringWithDelimiter(\"-\", \"\u91d1\u989d\uff1a\" + FO_EXPENSEBILL[BILLMONEY],\"\u65e5\u671f\uff1a\"+FO_EXPENSEBILL[BILLDATE])", "\u91d1\u989d\uff1a9645.3-\u65e5\u671f\uff1a2020-07-16");
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

