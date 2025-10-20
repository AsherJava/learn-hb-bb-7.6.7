/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.invest.formula.function.rule.fvch;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.function.rule.fvch.FairGetTimeFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FairGetWxAllTimeFunction
extends FairGetTimeFunction
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "GETWXALLTIME";

    public String name() {
        return "GETWXALLTIME";
    }

    public String title() {
        return "\u53d6\u516c\u5141\u4ef7\u503c\u8c03\u6574\u65e0\u5f62\u8d44\u4ea7\u7d2f\u8ba1\u6298\u65e7\u6708\u6570\u3002";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity data = executorContext.getData();
        PeriodWrapper periodWrapper = new PeriodWrapper(queryContext.getMasterKeys().getValue("DATATIME").toString());
        Calendar inputCalendar = this.getCalendar((Date)data.getFieldValue("BIZDATE"));
        Calendar disposeCalendar = this.getCalendar((Date)data.getFieldValue("DISPOSEDATE"), (Date)data.getFieldValue("DPCAAMTIZDEADLINE"));
        Calendar currCalendar = this.getCalendar(periodWrapper);
        if (currCalendar.compareTo(inputCalendar) < 0) {
            return 0;
        }
        if (disposeCalendar.compareTo(currCalendar) <= 0) {
            return (disposeCalendar.get(1) - inputCalendar.get(1)) * 12 + (disposeCalendar.get(2) - inputCalendar.get(2));
        }
        return (currCalendar.get(1) - inputCalendar.get(1)) * 12 + (currCalendar.get(2) - inputCalendar.get(2));
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u65e0\u5f62\u8d44\u4ea7\u7d2f\u8ba1\u6298\u65e7\u6708\u6570").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u516c\u5141\u4ef7\u503c\u8c03\u6574\u65e0\u5f62\u8d44\u4ea7\u7d2f\u8ba1\u6298\u65e7\u6708\u6570\u3002 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETWXALLTIME()").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

