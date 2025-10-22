/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.List;

public class PeriodToDate
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -217531253901392593L;

    public PeriodToDate() {
        this.parameters().add(new Parameter("period", 6, "\u65f6\u671f\u4ee3\u7801"));
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public String[] aliases() {
        return new String[]{"DayToDate"};
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Calendar date = null;
        try {
            String period = (String)parameters.get(0).evaluate(context);
            PeriodWrapper pw = new PeriodWrapper(period);
            PeriodType periodType = PeriodType.fromType((int)pw.getType());
            date = periodType.toCalendar(pw);
        }
        catch (Exception e) {
            QueryContext qContext = (QueryContext)context;
            qContext.getMonitor().exception(e);
        }
        return date;
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 2;
    }

    public String name() {
        return "PeriodToDate";
    }

    public String title() {
        return "\u6570\u636e\u65f6\u671f\u8f6c\u6362\u6210\u65e5\u671f(\u53d6\u65f6\u671f\u533a\u95f4\u5185\u7684\u7b2c\u4e00\u5929)";
    }
}

