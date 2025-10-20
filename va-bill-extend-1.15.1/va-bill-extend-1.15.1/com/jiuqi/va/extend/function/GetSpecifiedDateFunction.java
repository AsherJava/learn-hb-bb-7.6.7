/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.IFormulaContext
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.extend.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetSpecifiedDateFunction
extends ModelFunction {
    private static final long serialVersionUID = -1L;

    public GetSpecifiedDateFunction() {
        this.parameters().add(new Parameter("date", 0, "\u65e5\u671f", false));
        this.parameters().add(new Parameter("time", 3, "\u65f6", false));
        this.parameters().add(new Parameter("minute", 3, "\u5206", false));
        this.parameters().add(new Parameter("second", 3, "\u79d2", false));
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u6307\u5b9a\u65f6\u95f4";
    }

    public boolean enableDebug() {
        return true;
    }

    public String name() {
        return "GetSpecifiedDate";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u65f6\u95f4";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 2;
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object dateObj = parameters.get(0).evaluate(context);
        if (dateObj == null) {
            return null;
        }
        Date date = null;
        if (dateObj instanceof Date) {
            date = (Date)dateObj;
        }
        if (dateObj instanceof Calendar) {
            date = ((Calendar)dateObj).getTime();
        }
        if (dateObj instanceof GregorianCalendar) {
            date = ((GregorianCalendar)dateObj).getTime();
        }
        int time = this.convertToInteger(parameters.get(1).evaluate(context));
        int minute = this.convertToInteger(parameters.get(2).evaluate(context));
        int second = this.convertToInteger(parameters.get(3).evaluate(context));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(calendar.get(1), calendar.get(2), calendar.get(5), time, minute, second);
        return calendarDate;
    }

    public String getTenantName(IContext context) {
        String tenantName = ShiroUtil.getTenantName();
        if (tenantName != null) {
            return tenantName;
        }
        if (context != null && context instanceof IFormulaContext && ((IFormulaContext)context).getTenantName() != null) {
            return ((IFormulaContext)context).getTenantName();
        }
        return "";
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("date\uff1a").append(DataType.toString((int)0)).append("\uff1b\u65e5\u671f").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("time\uff1a").append(DataType.toString((int)3)).append("\uff1b\u65f6\uff0c\u6574\u6570\uff0c0~23").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("minute\uff1a").append(DataType.toString((int)3)).append("\uff1b\u5206\uff0c\u6574\u6570\uff0c0~59").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("second\uff1a").append(DataType.toString((int)3)).append("\uff1b\u79d2\uff0c\u6574\u6570\uff0c0~59").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)2)).append("\uff1a").append(DataType.toString((int)2)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u6bcf\u5929\u4e0a\u5348\u4e03\u70b9\u7684\u65f6\u95f4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetExpectedDate(Now(),7,0,0)").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)2));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("date", DataType.toString((int)0), "\u65e5\u671f", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("time", DataType.toString((int)3), "\u65f6\uff0c\u6574\u6570\uff0c0~23", Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription("minute", DataType.toString((int)3), "\u5206\uff0c\u6574\u6570\uff0c0~59", Boolean.valueOf(true));
        ParameterDescription parameterDescription3 = new ParameterDescription("second", DataType.toString((int)3), "\u79d2\uff0c\u6574\u6570\uff0c0~59", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        parameterDescriptions.add(parameterDescription3);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u6bcf\u5929\u4e0b\u5348\u516d\u70b9\u7684\u65f6\u95f4", "GetSpecifiedDate(Now(),18,0,0)", "");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Integer convertToInteger(Object obj) {
        if (null == obj) {
            return 0;
        }
        if (obj instanceof Double) {
            return ((Double)obj).intValue();
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal)obj).intValue();
        }
        return (Integer)obj;
    }
}

