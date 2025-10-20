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
import com.jiuqi.va.formula.common.exception.FormulaException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FirstDayOfMonthFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public FirstDayOfMonthFunction() {
        this.parameters().add(new Parameter("date", 2, "\u65e5\u671f", false));
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u5f97\u6307\u5b9a\u65e5\u671f\u6240\u5728\u6708\u7684\u7b2c\u4e00\u5929";
    }

    public String name() {
        return "FirstDayOfMonth";
    }

    public String title() {
        return "\u83b7\u5f97\u6307\u5b9a\u65e5\u671f\u6240\u5728\u6708\u7684\u7b2c\u4e00\u5929";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 2;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object dataSource = parameters.get(0).evaluate(context);
        if (dataSource instanceof Date) {
            Date date = (Date)dataSource;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int specialDay = cal.getActualMinimum(5);
            cal.set(5, specialDay);
            return cal;
        }
        if (dataSource instanceof Calendar) {
            Calendar cal = (Calendar)dataSource;
            int specialDay = cal.getActualMinimum(5);
            cal.set(5, specialDay);
            return cal;
        }
        throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.param.must.data"));
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
        buffer.append("    ").append("date\uff1a").append(DataType.toString((int)2)).append("\uff1b \u4efb\u610f\u65e5\u671f\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)2)).append("\uff1a").append(DataType.toString((int)2)).append("\uff1b\u53c2\u6570\u65e5\u671f\u6240\u5728\u6708\u7b2c\u4e00\u5929\u5bf9\u5e94\u7684\u65e5\u671f").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u8fd4\u56de\u5355\u636e\u5236\u5355\u65e5\u671f\u6240\u5728\u6708\u7684\u7b2c\u4e00\u5929").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("FirstDayOfMonth(T[BILLDATE])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("2020/12/1");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)2) + "\uff1a" + DataType.toString((int)2) + "\uff1b\u53c2\u6570\u65e5\u671f\u6240\u5728\u6708\u7b2c\u4e00\u5929\u5bf9\u5e94\u7684\u65e5\u671f");
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("date");
        parameterDescription.setType(DataType.toString((int)2));
        parameterDescription.setDescription("\u4efb\u610f\u65e5\u671f\u503c");
        parameterDescription.setRequired(true);
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u8fd4\u56de\u5355\u636e\u5236\u5355\u65e5\u671f\u6240\u5728\u6708\u7684\u7b2c\u4e00\u5929");
        formulaExample.setFormula("FirstDayOfMonth(T[BILLDATE])");
        formulaExample.setReturnValue("2020/12/1");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

