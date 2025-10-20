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
public class ToMapArrayFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public ToMapArrayFunction() {
        this.parameters().add(new Parameter("title", 0, "\u6807\u9898", false));
        this.parameters().add(new Parameter("value", 0, "\u5185\u5bb9", false));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    @Override
    public String addDescribe() {
        return "\u5c06\u591a\u4e2a\u6807\u9898\u548c\u5185\u5bb9\u8f6c\u6362\u4e3a\u952e\u503c\u5bf9\u5f62\u5f0f\u5b57\u7b26\u4e32\u6570\u7ec4\u683c\u5f0f\u7684\u5b57\u7b26\u4e32\u8fd4\u56de\uff0c\u6700\u540e\u4e00\u4e2a\u6807\u9898\u5982\u679c\u6ca1\u6709\u4f20\u5185\u5bb9\u5219\u5c06\u5176\u5185\u5bb9\u8bbe\u7f6e\u4e3a\u7a7a\u5b57\u7b26\u4e32\u3002";
    }

    public String name() {
        return "ToMapArray";
    }

    public String title() {
        return "\u5c06\u591a\u4e2a\u6807\u9898\u548c\u5185\u5bb9\u8f6c\u6362\u4e3a\u952e\u503c\u5bf9\u5f62\u5f0f\u5b57\u7b26\u4e32\u6570\u7ec4\u683c\u5f0f\u7684\u5b57\u7b26\u4e32\u8fd4\u56de";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int size = parameters.size();
        boolean isSingularFlag = false;
        if (size % 2 != 0) {
            isSingularFlag = true;
        }
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < size; i += 2) {
            Object key = parameters.get(i).evaluate(context);
            if (key == null) {
                key = "";
            }
            Object value = null;
            if (isSingularFlag && i == size - 1) {
                value = "";
            } else {
                value = parameters.get(i + 1).evaluate(context);
                if (value == null) {
                    value = "";
                } else if (value instanceof Calendar) {
                    value = this.formatDate((Calendar)value);
                }
            }
            sb.append("\"");
            sb.append(key.toString());
            sb.append("\uff1a");
            sb.append(value.toString());
            sb.append("\"");
            if (i == (isSingularFlag ? size - 1 : size - 2)) continue;
            sb.append(",");
        }
        sb.append("]");
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
        buffer.append("    ").append("title").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u6807\u9898").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5185\u5bb9").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5728\u4e8b\u9879\u7533\u8bf7\u5355\uff0c\u8bbe\u7f6e\u4fdd\u5b58\u65f6\u5907\u6ce8\u5b57\u6bb5\u7684\u503c  ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ToMapArray( '\u5355\u636e\u7f16\u53f7',FO_APPLOANBILL[BILLCODE],'\u662f\u5426\u663e\u793a','true')").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("[\"\u5355\u636e\u7f16\u53f7\uff1aDZSQDA00011912000370\",\"\u662f\u5426\u663e\u793a\uff1atrue\"]");
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
        ParameterDescription parameterDescription = new ParameterDescription("title", DataType.toString((int)0), "\u6807\u9898", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("value", DataType.toString((int)0), "\u5185\u5bb9", true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u5728\u4e8b\u9879\u7533\u8bf7\u5355\uff0c\u8bbe\u7f6e\u4fdd\u5b58\u65f6\u5907\u6ce8\u5b57\u6bb5\u7684\u503c", "ToMapArray( '\u5355\u636e\u7f16\u53f7',FO_APPLOANBILL[BILLCODE],'\u662f\u5426\u663e\u793a','true')", "[\"\u5355\u636e\u7f16\u53f7\uff1aDZSQDA00011912000370\",\"\u662f\u5426\u663e\u793a\uff1atrue\"]");
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

