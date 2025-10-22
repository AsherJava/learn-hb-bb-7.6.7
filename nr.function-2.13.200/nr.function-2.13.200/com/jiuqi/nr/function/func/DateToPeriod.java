/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateToPeriod
extends Function
implements IReportFunction {
    private static final Logger logger = LoggerFactory.getLogger(DateToPeriod.class);
    private static final long serialVersionUID = -6769967285358864943L;

    public DateToPeriod() {
        this.parameters().add(new Parameter("type", 6, "\u65f6\u671f\u7c7b\u578b\uff1aN\u5e74/H\u534a\u5e74/J\u5b63/Y\u6708/X\u65ec/R\u65e5"));
        this.parameters().add(new Parameter("date", 2, "\u65e5\u671f"));
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("DateToPeriod()\u51fd\u6570\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u7c7b\u578b");
        }
        String periodType = (String)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Calendar)) {
            throw new SyntaxException("DateToPeriod()\u51fd\u6570\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u65e5\u671f\u7c7b\u578b");
        }
        Calendar c = (Calendar)obj;
        return DateToPeriod.callFunction(c, periodType);
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 6;
    }

    public String name() {
        return "DateToPeriod";
    }

    public String title() {
        return "\u65e5\u671f\u671f\u8f6c\u6362\u62109\u4f4d\u65f6\u671f\u5b57\u7b26\u4e32";
    }

    public boolean support(Language lang) {
        return lang == Language.FORMULA || lang == Language.EXPLAIN;
    }

    public static final String callFunction(Calendar calendar, String periodType) {
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int weekOfYear = calendar.get(3);
        int dayOfMonth = calendar.get(5);
        int dayOfYear = calendar.get(6);
        int period = 1;
        if (!"N".equals(periodType)) {
            if ("H".equals(periodType)) {
                period = month < 7 ? 1 : 2;
            } else if ("J".equals(periodType)) {
                period = month < 4 ? 1 : (month < 7 ? 2 : (month < 10 ? 3 : 4));
            } else if ("Y".equals(periodType)) {
                period = month;
            } else if ("X".equals(periodType)) {
                int tailTenDay = dayOfMonth < 11 ? 1 : (dayOfMonth < 21 ? 2 : 3);
                period = (month - 1) * 3 + tailTenDay;
            } else if ("Z".equals(periodType)) {
                period = weekOfYear;
            } else if ("R".equals(periodType)) {
                period = dayOfYear;
            }
        }
        return year + periodType + String.format("%04d", period);
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        logger.info(DateToPeriod.callFunction(c, "N"));
        logger.info(DateToPeriod.callFunction(c, "H"));
        logger.info(DateToPeriod.callFunction(c, "J"));
        logger.info(DateToPeriod.callFunction(c, "Y"));
        logger.info(DateToPeriod.callFunction(c, "X"));
        logger.info(DateToPeriod.callFunction(c, "Z"));
        logger.info(DateToPeriod.callFunction(c, "R"));
        c.set(2, 3);
        logger.info("4\u6708");
        logger.info(DateToPeriod.callFunction(c, "N"));
        logger.info(DateToPeriod.callFunction(c, "H"));
        logger.info(DateToPeriod.callFunction(c, "J"));
        logger.info(DateToPeriod.callFunction(c, "Y"));
        logger.info(DateToPeriod.callFunction(c, "X"));
        logger.info(DateToPeriod.callFunction(c, "Z"));
        logger.info(DateToPeriod.callFunction(c, "R"));
    }
}

