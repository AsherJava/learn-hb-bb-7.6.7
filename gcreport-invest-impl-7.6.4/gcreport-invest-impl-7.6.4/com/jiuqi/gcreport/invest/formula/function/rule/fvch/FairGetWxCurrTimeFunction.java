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
public class FairGetWxCurrTimeFunction
extends FairGetTimeFunction
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "GETWXCURTIME";

    public String name() {
        return "GETWXCURTIME";
    }

    public String title() {
        return "\u53d6\u516c\u5141\u4ef7\u503c\u8c03\u6574\u65e0\u5f62\u8d44\u4ea7\u5f53\u524d\u5e74\u6298\u65e7\u6708\u6570\u3002";
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
        if (disposeCalendar.get(1) < currCalendar.get(1)) {
            return 0;
        }
        if (periodWrapper.getYear() == inputCalendar.get(1)) {
            if (this.beforeSecondCalendar(currCalendar, disposeCalendar)) {
                return currCalendar.get(2) - inputCalendar.get(2) + 1;
            }
            return disposeCalendar.get(2) - inputCalendar.get(2);
        }
        if (periodWrapper.getYear() > inputCalendar.get(1)) {
            if (this.beforeSecondCalendar(currCalendar, disposeCalendar)) {
                System.out.println("\u4ee5\u524d\u5e74\u7684\u516c\u5141\u4ef7\u503c\u8c03\u6574\uff1a\u672a\u5904\u7f6e\uff1a" + (currCalendar.get(2) + 1));
                return currCalendar.get(2) + 1;
            }
            System.out.println("\u4ee5\u524d\u5e74\u7684\u516c\u5141\u4ef7\u503c\u8c03\u6574\uff1a\u5df2\u7ecf\u5904\u7f6e\uff1a" + (disposeCalendar.get(2) + 1));
            return disposeCalendar.get(2);
        }
        return 0;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u65e0\u5f62\u8d44\u4ea7\u5f53\u524d\u5e74\u6298\u65e7\u6708\u6570").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u516c\u5141\u4ef7\u503c\u8c03\u6574\u65e0\u5f62\u8d44\u4ea7\u5f53\u524d\u5e74\u6298\u65e7\u6708\u6570\u3002 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETWXCURTIME()").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

