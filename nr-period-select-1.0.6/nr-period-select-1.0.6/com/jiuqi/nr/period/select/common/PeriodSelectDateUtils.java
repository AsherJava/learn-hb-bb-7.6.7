/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.common;

import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.vo.PeriodData;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PeriodSelectDateUtils {
    public static Calendar getCurrentDate() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        return date.after(begin) && date.before(end);
    }

    public static List<PeriodData> toPeriodDatas(List<IPeriodRow> periodItems) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return periodItems.stream().map(e -> {
            PeriodData data = new PeriodData();
            data.setText(e.getTitle());
            data.setCode(e.getCode());
            data.setStart(sdf.format(e.getStartDate()));
            return data;
        }).collect(Collectors.toList());
    }
}

