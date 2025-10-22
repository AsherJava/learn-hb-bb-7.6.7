/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DateDiff
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -6286442018742145635L;
    private static final Set<String> YEAR_SET = new HashSet<String>();
    private static final Set<String> MONTH_SET;
    private static final Set<String> DAY_SET;
    private static final Set<String> HOUR_SET;
    private static final Set<String> MINUTE_SET;
    private static final Set<String> SECOND_SET;
    private static final String UNIT_PARAM_EXPLAIN = "\u6bd4\u8f83\u5355\u4f4d\u53c2\u6570\uff0c\u53ef\u4ee5\u662f\u5e74\uff08yy\u6216year\uff09\u3001\u6708\uff08mm\u6216month\uff09\u3001\u65e5\uff08dd\u6216day\u6216\u5929\uff09\u3001\u65f6\uff08hour\uff09\u3001\u5206\uff08minute\uff09\u3001\u79d2\uff08second\uff09\u3002\u9ed8\u8ba4\u4e3a\u65e5";
    private static int DAYS_BEFORE_1970;

    public DateDiff() {
        this.parameters().add(new Parameter("date1", 2, "\u65e5\u671f"));
        this.parameters().add(new Parameter("date2", 2, "\u65e5\u671f"));
        this.parameters().add(new Parameter("datepart", 6, UNIT_PARAM_EXPLAIN, true));
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int diff;
        String datepart;
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Calendar)) {
            throw new SyntaxException("DateDiff()\u51fd\u6570\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u65e5\u671f\u7c7b\u578b");
        }
        Calendar date1 = (Calendar)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Calendar)) {
            throw new SyntaxException("DateDiff()\u51fd\u6570\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u65e5\u671f\u7c7b\u578b");
        }
        Calendar date2 = (Calendar)obj;
        if (parameters.size() == 2) {
            datepart = "dd";
        } else {
            obj = parameters.get(2).evaluate(context);
            if (obj == null) {
                return null;
            }
            if (!(obj instanceof String)) {
                throw new SyntaxException("DateDiff()\u51fd\u6570\u7684\u7b2c\u4e09\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
            }
            datepart = (String)obj;
        }
        String code = datepart.toLowerCase();
        if (YEAR_SET.contains(code)) {
            diff = date1.get(1) - date2.get(1);
        } else if (MONTH_SET.contains(code)) {
            int yDiff = date1.get(1) - date2.get(1);
            int mDiff = date1.get(2) - date2.get(2);
            diff = yDiff * 12 + mDiff;
        } else if (DAY_SET.contains(code)) {
            diff = DateDiff.getDiffValueInDay(date1, date2);
        } else if (HOUR_SET.contains(code)) {
            diff = DateDiff.getDiffValueInHour(date1, date2);
        } else if (MINUTE_SET.contains(code)) {
            diff = DateDiff.getDiffValueInMinute(date1, date2);
        } else if (SECOND_SET.contains(code)) {
            diff = DateDiff.getDiffValueInSecond(date1, date2);
        } else {
            throw new SyntaxException("\u7b2c\u4e09\u4e2a\u53c2\u6570\u503c\u683c\u5f0f\u6709\u8bef\uff0c\u6bd4\u8f83\u5355\u4f4d\u53c2\u6570\uff0c\u53ef\u4ee5\u662f\u5e74\uff08yy\u6216year\uff09\u3001\u6708\uff08mm\u6216month\uff09\u3001\u65e5\uff08dd\u6216day\u6216\u5929\uff09\u3001\u65f6\uff08hour\uff09\u3001\u5206\uff08minute\uff09\u3001\u79d2\uff08second\uff09\u3002\u9ed8\u8ba4\u4e3a\u65e5");
        }
        return diff;
    }

    private static int getDaysBefore1970() {
        int days = 146097;
        days *= 4;
        for (int i = 1600; i < 1970; ++i) {
            boolean isLeap = i % 4 == 0 && i % 100 != 0;
            days += isLeap ? 366 : 365;
        }
        return days;
    }

    private static int getDaysSince1970(long date) {
        return (int)(date / 86400000L);
    }

    private static int getDaysSince0000(long date) {
        return DAYS_BEFORE_1970 + DateDiff.getDaysSince1970(date);
    }

    private static int getDiffValueInDay(Calendar date1, Calendar date2) {
        date1 = DateDiff.ignoreHourMinuteSecond(date1);
        date2 = DateDiff.ignoreHourMinuteSecond(date2);
        return DateDiff.getDaysSince0000(date1.getTimeInMillis()) - DateDiff.getDaysSince0000(date2.getTimeInMillis());
    }

    private static int getDiffValueInHour(Calendar date1, Calendar date2) {
        int dDiff = DateDiff.getDiffValueInDay(date1, date2);
        int hDiff = date1.get(11) - date2.get(11);
        return dDiff * 24 + hDiff;
    }

    private static int getDiffValueInMinute(Calendar date1, Calendar date2) {
        int hDiff = DateDiff.getDiffValueInHour(date1, date2);
        int mDiff = date1.get(12) - date2.get(12);
        return hDiff * 60 + mDiff;
    }

    private static int getDiffValueInSecond(Calendar date1, Calendar date2) {
        int mDiff = DateDiff.getDiffValueInMinute(date1, date2);
        int sDiff = date1.get(13) - date2.get(13);
        return mDiff * 60 + sDiff;
    }

    private static Calendar ignoreHourMinuteSecond(Calendar date) {
        Calendar newCal = Calendar.getInstance();
        newCal.set(1, date.get(1));
        newCal.set(2, date.get(2));
        newCal.set(5, date.get(5));
        newCal.set(11, 0);
        newCal.set(12, 0);
        newCal.set(13, 0);
        return newCal;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String name() {
        return "DateDiff";
    }

    public String title() {
        return "\u8ba1\u7b97\u6307\u5b9a\u8fde\u4e24\u4e2a\u65e5\u671f\u503c\u7684\u5dee";
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    public static final String getCompareDatepart(IContext context, List<IASTNode> children) throws SyntaxException {
        String datepart = "dd";
        if (children.size() == 3) {
            datepart = (String)children.get(2).evaluate(context);
            datepart = datepart.toLowerCase();
        }
        if (YEAR_SET.contains(datepart)) {
            return "yy";
        }
        if (MONTH_SET.contains(datepart)) {
            return "mm";
        }
        if (DAY_SET.contains(datepart)) {
            return "dd";
        }
        if (HOUR_SET.contains(datepart)) {
            return "hh";
        }
        if (MINUTE_SET.contains(datepart)) {
            return "mi";
        }
        if (SECOND_SET.contains(datepart)) {
            return "ss";
        }
        return null;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        String datepart = "dd";
        if (children.size() == 3) {
            try {
                datepart = (String)children.get(2).evaluate(context);
            }
            catch (SyntaxException e) {
                throw new InterpretException((Throwable)e);
            }
            datepart = datepart.toLowerCase();
        }
        this.toOracleSQL(context, children, buffer, info, datepart);
    }

    private void toOracleSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info, String datepart) throws InterpretException {
        if (YEAR_SET.contains(datepart)) {
            buffer.append(" floor(months_between(");
            children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(", ");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(") / 12)");
        } else if (MONTH_SET.contains(datepart)) {
            buffer.append("floor(months_between(");
            children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(", ");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append("))");
        } else if (DAY_SET.contains(datepart)) {
            buffer.append(" floor(");
            this.makeOracleDateDiffValue(context, children, buffer, info);
            buffer.append(")");
        } else if (HOUR_SET.contains(datepart)) {
            buffer.append(" floor(");
            this.makeOracleDateDiffValue(context, children, buffer, info);
            buffer.append("*24)");
        } else if (MINUTE_SET.contains(datepart)) {
            buffer.append(" floor(");
            this.makeOracleDateDiffValue(context, children, buffer, info);
            buffer.append("*24*60)");
        } else if (SECOND_SET.contains(datepart)) {
            buffer.append(" floor(");
            this.makeOracleDateDiffValue(context, children, buffer, info);
            buffer.append("*24*60*60)");
        }
    }

    private void makeOracleDateDiffValue(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append("to_number(");
        children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(" - ");
        children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(")");
    }

    static {
        YEAR_SET.add("\u5e74");
        YEAR_SET.add("yy");
        YEAR_SET.add("year");
        MONTH_SET = new HashSet<String>();
        MONTH_SET.add("\u6708");
        MONTH_SET.add("mm");
        MONTH_SET.add("month");
        DAY_SET = new HashSet<String>();
        DAY_SET.add("\u65e5");
        DAY_SET.add("\u5929");
        DAY_SET.add("dd");
        DAY_SET.add("day");
        HOUR_SET = new HashSet<String>();
        HOUR_SET.add("\u65f6");
        HOUR_SET.add("hour");
        MINUTE_SET = new HashSet<String>();
        MINUTE_SET.add("\u5206");
        MINUTE_SET.add("minute");
        SECOND_SET = new HashSet<String>();
        SECOND_SET.add("\u79d2");
        SECOND_SET.add("second");
        DAYS_BEFORE_1970 = DateDiff.getDaysBefore1970();
    }
}

