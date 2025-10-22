/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SumOneYearFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;

    public String name() {
        return "SumOneYear";
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u672a\u6765\u4e00\u5e74\u5185\u5b50\u8868\u6307\u6807\u7684\u6c47\u603b\u6570";
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("SUBTABLEZB").append("\uff1a").append(DataType.toString((int)6)).append("\u6307\u6807code").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u672a\u6765\u4e00\u5e74\u6307\u6807\u503c\u6c47\u603b\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u672a\u6765\u4e00\u5e74\u5185\u5b50\u8868\u6307\u6807\u7684\u6c47\u603b\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SumOneYear(\"SUBTABLEZB\")/SumOneYear(\"GC_LESSORITEMBILL[SUBTABLEZB]\")").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public SumOneYearFunction() {
        this.parameters().add(new Parameter("sumFieldCode", 6, "\u6c47\u603b\u6307\u6807"));
    }

    public String title() {
        return "\u83b7\u53d6\u672a\u6765\u4e00\u5e74\u5185\u5b50\u8868\u6307\u6807\u7684\u6c47\u603b\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return super.validate(context, parameters);
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)iContext;
        GcReportExceutorContext exeContext = (GcReportExceutorContext)queryContext.getExeContext();
        String zbCode = (String)parameters.get(0).evaluate(iContext);
        String datetimeStr = (String)queryContext.getMasterKeys().getValue("DATATIME");
        int index = zbCode.indexOf("[");
        String subTableName = (String)queryContext.getCurrentMasterKey().getValue("subTableName");
        if (index == 0) {
            zbCode = zbCode.substring(1, zbCode.length() - 1);
        } else if (index > 0) {
            subTableName = zbCode.substring(0, index);
            zbCode = zbCode.substring(index + 1, zbCode.length() - 1);
        }
        List datas = InvestBillTool.getBillItemByMasterId((String)exeContext.getData().getId(), (String)subTableName);
        Set<String> yearAndMonthStrSet = this.getYearAndMonth(datetimeStr);
        Double sum = new Double(0.0);
        for (Map data : datas) {
            Double zbValue;
            if (!yearAndMonthStrSet.contains(data.get("CHANGEMONTH")) || (zbValue = (Double)data.get(zbCode)) == null) continue;
            sum = sum + zbValue;
        }
        return sum;
    }

    private Set<String> getYearAndMonth(String datetimeStr) {
        int yearStr = Integer.parseInt(datetimeStr.substring(0, 4));
        int monthStr = Integer.parseInt(datetimeStr.substring(7));
        String dateType = datetimeStr.substring(4, 5);
        if ("N".equals(dateType)) {
            monthStr = 12;
        }
        HashSet<String> futureDateStrSet = new HashSet<String>();
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearStr);
        cal.set(2, monthStr - 1);
        for (int i = 0; i < 12; ++i) {
            String time = "";
            cal.add(2, 1);
            int year = cal.get(1);
            int month = cal.get(2) + 1;
            time = month < 10 ? year + "-0" + month : year + "-" + month;
            futureDateStrSet.add(time);
        }
        return futureDateStrSet;
    }
}

