/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.holiday.manager.bean.HolidayDefine
 *  com.jiuqi.nr.holiday.manager.common.HolidayEvent
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.time.setting.de;

import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import com.jiuqi.nr.holiday.manager.common.HolidayEvent;
import com.jiuqi.util.StringUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HolidayRange {
    private Date startTime;
    private Date endTime;
    private int type;

    public HolidayRange(HolidayDefine holidayDefine) {
        int event;
        this.type = event = holidayDefine.getEvent();
        String date = holidayDefine.getDate();
        String[] split = date.split("-");
        String startTime = split[0];
        String endTime = split[1];
        if (StringUtils.isNotEmpty((String)startTime) && StringUtils.isNotEmpty((String)endTime)) {
            PeriodWrapper periodWrapper = PeriodUtil.getPeriodWrapper((String)startTime);
            GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)periodWrapper);
            this.startTime = HolidayRange.getstartTime(startCalendar.getTime());
            PeriodWrapper endPeriodWrapper = PeriodUtil.getPeriodWrapper((String)endTime);
            GregorianCalendar endCalendar = PeriodUtil.period2Calendar((PeriodWrapper)endPeriodWrapper);
            this.endTime = HolidayRange.getendTime(endCalendar.getTime());
        }
    }

    public boolean isHolidayRange(Date time) {
        Date startTime1 = this.startTime;
        Date endTime1 = this.endTime;
        return startTime1.getTime() <= time.getTime() && endTime1.getTime() >= time.getTime() && HolidayEvent.HOLIDAY.getValue() == this.type;
    }

    public boolean isWorkRange(Date time) {
        Date startTime1 = this.startTime;
        Date endTime1 = this.endTime;
        return startTime1.getTime() <= time.getTime() && endTime1.getTime() >= time.getTime() && HolidayEvent.WORK.getValue() == this.type;
    }

    private static Date getstartTime(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(11, 0);
        day.set(12, 0);
        day.set(13, 0);
        day.set(14, 0);
        return day.getTime();
    }

    private static Date getendTime(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(11, 23);
        day.set(12, 59);
        day.set(13, 59);
        day.set(14, 999);
        return day.getTime();
    }
}

