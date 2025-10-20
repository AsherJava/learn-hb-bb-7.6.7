/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.invest.formula.function.rule.fvch;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public abstract class FairGetTimeFunction
extends Function {
    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    protected Calendar getCalendar(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    protected Calendar getCalendar(Date date, Date date1) {
        if (null == date && null == date1) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(1, 100);
            return calendar;
        }
        if (date == null) {
            return this.getCalendar(date1);
        }
        if (date1 == null) {
            return this.getCalendar(date);
        }
        Calendar calendar = GregorianCalendar.getInstance();
        if (date.before(date1)) {
            calendar.setTime(date);
        } else {
            calendar.setTime(date1);
        }
        return calendar;
    }

    protected Calendar getCalendar(PeriodWrapper periodWrapper) {
        Calendar calendar = GregorianCalendar.getInstance();
        switch (periodWrapper.getType()) {
            case 1: {
                calendar.set(periodWrapper.getYear(), 11, 1);
                break;
            }
            case 2: {
                calendar.set(periodWrapper.getYear(), periodWrapper.getPeriod() * 6 - 1, 1);
                break;
            }
            case 3: {
                calendar.set(periodWrapper.getYear(), periodWrapper.getPeriod() * 3 - 1, 1);
                break;
            }
            case 4: {
                calendar.set(periodWrapper.getYear(), periodWrapper.getPeriod() - 1, 1);
            }
        }
        return calendar;
    }

    protected boolean beforeSecondCalendar(Calendar firstCalendar, Calendar secondCalendar) {
        int result = firstCalendar.get(1) - secondCalendar.get(1);
        if (result != 0) {
            return result < 0;
        }
        result = firstCalendar.get(2) - secondCalendar.get(2);
        return result < 0;
    }
}

