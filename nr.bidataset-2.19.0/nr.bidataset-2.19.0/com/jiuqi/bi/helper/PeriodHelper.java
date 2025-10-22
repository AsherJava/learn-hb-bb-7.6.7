/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.exception.PeriodFormatException
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.select.common.RunType
 *  com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService
 */
package com.jiuqi.bi.helper;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.exception.PeriodFormatException;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeriodHelper {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimePeriodModuleService periodModuleService;
    private static final Logger logger = LoggerFactory.getLogger(PeriodHelper.class);

    public String getCurrentPeriod(TaskDefine taskDefine, IPeriodEntity periodEntity) throws Exception {
        String currentPeriod = null;
        try {
            currentPeriod = this.periodModuleService.queryOffsetPeriod(taskDefine.getKey(), RunType.RUNTIME);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
        if (currentPeriod == null) {
            currentPeriod = periodProvider.getCurPeriod().getCode();
        }
        if (periodEntity.getPeriodType() == PeriodType.MONTH && !periodEntity.getKey().equals("Y")) {
            PeriodWrapper pw = new PeriodWrapper(currentPeriod);
            while (pw.getPeriod() < 1 || pw.getPeriod() > 12) {
                if (periodProvider.priorPeriod(pw)) continue;
                return currentPeriod;
            }
            return pw.toString();
        }
        return currentPeriod;
    }

    public PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        PeriodWrapper fromPeriodWrapper = null;
        PeriodWrapper toPeriodWrapper = null;
        try {
            fromPeriodWrapper = new PeriodWrapper(fromPeriod);
            toPeriodWrapper = new PeriodWrapper(toPeriod);
        }
        catch (Exception e) {
            throw new PeriodFormatException(new String[]{e.getMessage()});
        }
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)periodType, (int)periodOffset);
        currentPeriod = this.adjustWeekPeriod(currentPeriod, periodType, periodOffset);
        GregorianCalendar currentCalendar = PeriodUtil.period2Calendar((PeriodWrapper)currentPeriod);
        GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)fromPeriod);
        GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)toPeriod);
        if (currentCalendar.compareTo(fromCalendar) >= 0 && currentCalendar.compareTo(toCalendar) <= 0) {
            return currentPeriod;
        }
        GregorianCalendar nowGregorianCalendar = new GregorianCalendar();
        if (nowGregorianCalendar.after(toCalendar)) {
            return toPeriodWrapper;
        }
        return fromPeriodWrapper;
    }

    private PeriodWrapper adjustWeekPeriod(PeriodWrapper currentPeriod, int periodType, int periodOffset) {
        if (periodType != 7) {
            return currentPeriod;
        }
        PeriodWrapper currPeriod = new PeriodWrapper();
        GregorianCalendar calendar = new GregorianCalendar();
        int weekOfYear = this.getWeekOfYear(calendar);
        int year = calendar.get(1);
        int period = 1;
        period = weekOfYear;
        year += periodOffset / 53;
        if ((period += periodOffset % 53) <= 0) {
            --year;
            period = 53 + period;
        } else if (period > 53) {
            ++year;
            period -= 53;
        }
        currPeriod.setAll(year, periodType, period);
        return currPeriod;
    }

    private int getWeekOfYear(GregorianCalendar calendar) {
        int year = calendar.get(1);
        GregorianCalendar januaryCalendar = new GregorianCalendar(year, 0, 1);
        int week = januaryCalendar.get(7);
        calendar.setFirstDayOfWeek(2);
        if (week == 3 || week == 4 || week == 5) {
            calendar.setMinimalDaysInFirstWeek(7 - week + 2);
        } else {
            calendar.setMinimalDaysInFirstWeek(7);
        }
        int weekOfYear = calendar.get(3);
        return weekOfYear;
    }
}

