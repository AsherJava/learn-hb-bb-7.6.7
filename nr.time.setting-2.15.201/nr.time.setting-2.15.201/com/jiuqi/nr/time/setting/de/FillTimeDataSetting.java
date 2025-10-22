/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.holiday.manager.bean.HolidayDefine
 *  com.jiuqi.nr.holiday.manager.service.IHolidayManagerService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.time.setting.de;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import com.jiuqi.nr.holiday.manager.service.IHolidayManagerService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.time.setting.de.FillDataType;
import com.jiuqi.nr.time.setting.de.HolidayRange;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FillTimeDataSetting {
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IHolidayManagerService holidayManagerService;

    public FillDataType fillTimeData(String taskKey, String period, Date time) {
        Date starTime;
        String realPeriod;
        Assert.notNull((Object)taskKey, "\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)period, "\u586b\u62a5\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u4efb\u52a1");
        }
        if (null == time) {
            time = new Date();
        }
        String periodStart = taskDefine.getFromPeriod();
        String periodEnd = taskDefine.getToPeriod();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
        if (periodProvider.comparePeriod(realPeriod = this.getPeriod(taskDefine, periodProvider, period), periodStart) < 0) {
            return FillDataType.PERIOD_BEFORE_TASK;
        }
        if (periodProvider.comparePeriod(realPeriod, periodEnd) > 0) {
            return FillDataType.PERIOD_AFTER_TASK;
        }
        Date startTime = this.getStartTime(taskDefine, periodProvider, realPeriod);
        FillDateType fillingDateType = taskDefine.getFillingDateType();
        if (!FillDateType.NONE.equals((Object)fillingDateType) && time.before(starTime = this.calcStarTime(startTime, taskDefine, realPeriod))) {
            return FillDataType.TIME_BEFORE_PERIOD;
        }
        Date endTime = this.getEndTime(taskDefine, periodProvider, realPeriod);
        boolean fillInAutoDueOpen = this.fillInAutoDueOpen(taskDefine);
        if (fillInAutoDueOpen) {
            Date entTime = this.calcEndTime(endTime, taskDefine, realPeriod);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(entTime);
            String calculatedEndTime = format + " 23:59:59";
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date endDate = formatter.parse(calculatedEndTime);
                if (time.after(endDate)) {
                    return FillDataType.TIME_AFTER_PERIOD;
                }
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return FillDataType.SUCCESS;
    }

    private String getPeriod(TaskDefine taskDefine, IPeriodProvider periodProvider, String period) {
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(taskDefine.getDateTime());
        PeriodType periodType = periodEntity.getPeriodType();
        PeriodModifier modifier = new PeriodModifier();
        boolean period13 = PeriodUtils.isPeriod13((String)taskDefine.getDateTime(), (PeriodType)periodType);
        boolean period13Data = PeriodUtils.isPeriod13Data((PeriodType)periodType, (String)period);
        if (period13) {
            if (!period13Data) {
                modifier.setPeriodModifier(-taskDefine.getTaskPeriodOffset());
            }
        } else {
            modifier.setPeriodModifier(-taskDefine.getTaskPeriodOffset());
        }
        return periodProvider.modify(period, modifier);
    }

    private Date getStartTime(TaskDefine taskDefine, IPeriodProvider periodProvider, String realPeriod) {
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(taskDefine.getDateTime());
        PeriodType periodType = periodEntity.getPeriodType();
        boolean period13 = PeriodUtils.isPeriod13((String)taskDefine.getDateTime(), (PeriodType)periodType);
        if (period13) {
            boolean period13Data = PeriodUtils.isPeriod13Data((PeriodType)periodType, (String)realPeriod);
            if (period13Data) {
                Date[] dates = this.getTime(periodProvider, realPeriod);
                PeriodModifier modifier = new PeriodModifier();
                modifier.setPeriodModifier(-taskDefine.getTaskPeriodOffset());
                String periodFromDate = PeriodUtils.getPeriodFromDate((int)periodType.type(), (Date)dates[0]);
                IPeriodProvider iPeriodProviderOfY = this.periodEntityAdapter.getPeriodProvider("Y");
                String modifyPeriod = iPeriodProviderOfY.modify(periodFromDate, modifier);
                Date[] datesY = this.getTime(iPeriodProviderOfY, modifyPeriod);
                return datesY[0];
            }
            Date[] dates = this.getTime(periodProvider, realPeriod);
            return dates[0];
        }
        Date[] dates = this.getTime(periodProvider, realPeriod);
        return dates[0];
    }

    private Date[] getTime(IPeriodProvider periodProvider, String realPeriod) {
        try {
            return periodProvider.getPeriodDateRegion(realPeriod);
        }
        catch (ParseException e) {
            throw new RuntimeException("\u83b7\u53d6\u65f6\u671f\uff1a" + realPeriod + "\u7684\u5f00\u59cb\u65f6\u95f4\u548c\u7ed3\u675f\u65f6\u95f4\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
    }

    private Date getEndTime(TaskDefine taskDefine, IPeriodProvider periodProvider, String realPeriod) {
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(taskDefine.getDateTime());
        PeriodType periodType = periodEntity.getPeriodType();
        boolean period13 = PeriodUtils.isPeriod13((String)taskDefine.getDateTime(), (PeriodType)periodType);
        if (period13) {
            boolean period13Data = PeriodUtils.isPeriod13Data((PeriodType)periodType, (String)realPeriod);
            if (period13Data) {
                Date[] dates = this.getTime(periodProvider, realPeriod);
                PeriodModifier modifier = new PeriodModifier();
                modifier.setPeriodModifier(-taskDefine.getTaskPeriodOffset());
                String periodFromDate = PeriodUtils.getPeriodFromDate((int)periodType.type(), (Date)dates[1]);
                IPeriodProvider iPeriodProviderOfY = this.periodEntityAdapter.getPeriodProvider("Y");
                String modifyPeriod = iPeriodProviderOfY.modify(periodFromDate, modifier);
                Date[] datesY = this.getTime(iPeriodProviderOfY, modifyPeriod);
                return datesY[1];
            }
            Date[] dates = this.getTime(periodProvider, realPeriod);
            return dates[1];
        }
        Date[] dates = this.getTime(periodProvider, realPeriod);
        return dates[1];
    }

    public Calendar getEndFillTimeData(String taskKey, String period, Date time) {
        Assert.notNull((Object)taskKey, "\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)period, "\u586b\u62a5\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u4efb\u52a1");
        }
        if (null == time) {
            time = new Date();
        }
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
        String realPeriod = this.getPeriod(taskDefine, periodProvider, period);
        Date endTime = this.getEndTime(taskDefine, periodProvider, realPeriod);
        boolean fillInAutoDueOpen = this.fillInAutoDueOpen(taskDefine);
        if (fillInAutoDueOpen) {
            Date entTime = this.calcEndTime(endTime, taskDefine, realPeriod);
            Calendar instance = Calendar.getInstance();
            instance.setTime(entTime);
            return instance;
        }
        return null;
    }

    public Calendar getStartFillTimeData(String taskKey, String period, Date time) {
        Assert.notNull((Object)taskKey, "\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)period, "\u586b\u62a5\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u4efb\u52a1");
        }
        if (null == time) {
            time = new Date();
        }
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
        String realPeriod = this.getPeriod(taskDefine, periodProvider, period);
        Date startTime = this.getStartTime(taskDefine, periodProvider, realPeriod);
        FillDateType fillingDateType = taskDefine.getFillingDateType();
        if (!FillDateType.NONE.equals((Object)fillingDateType)) {
            Date starTime = this.calcStarTime(startTime, taskDefine, realPeriod);
            Calendar instance = Calendar.getInstance();
            instance.setTime(starTime);
            return instance;
        }
        return null;
    }

    private Date calcStarTime(Date start, TaskDefine taskDefine, String realPeriod) {
        FillDateType fillingDateType = taskDefine.getFillingDateType();
        if (FillDateType.NONE.equals((Object)fillingDateType)) {
            return start;
        }
        int offset = taskDefine.getFillingDateDays();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        if (FillDateType.NATURAL_DAY.equals((Object)fillingDateType)) {
            this.offsetNaturalDays(calendar, offset);
        } else if (FillDateType.WORK_DAY.equals((Object)fillingDateType)) {
            this.offsetWorkdays(calendar, offset, realPeriod);
        }
        return calendar.getTime();
    }

    private Date calcEndTime(Date end, TaskDefine taskDefine, String realPeriod) {
        FillInAutomaticallyDue fillInAutomaticallyDue = taskDefine.getFillInAutomaticallyDue();
        if (!fillInAutomaticallyDue.getAutomaticTermination()) {
            return end;
        }
        int type = fillInAutomaticallyDue.getType();
        if (FillInAutomaticallyDue.Type.CLOSE.getValue() == type || FillInAutomaticallyDue.Type.DEFAULT.getValue() == type) {
            return end;
        }
        int offset = fillInAutomaticallyDue.getDays();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        if (FillInAutomaticallyDue.Type.NATURAL.getValue() == type) {
            this.offsetNaturalDays(calendar, offset);
        } else if (FillInAutomaticallyDue.Type.WORKING.getValue() == type) {
            this.offsetWorkdays(calendar, offset, realPeriod);
        }
        return calendar.getTime();
    }

    private void offsetWorkdays(Calendar calendar, int offset, String realPeriod) {
        int sign = offset >= 0 ? 1 : -1;
        List<HolidayRange> list = this.getHolidayData(realPeriod);
        while (offset != 0) {
            if (FillTimeDataSetting.checkHoliday(calendar, list)) {
                calendar.add(5, sign);
                continue;
            }
            calendar.add(5, sign);
            offset -= sign;
        }
        while (FillTimeDataSetting.checkHoliday(calendar, list)) {
            calendar.add(5, sign);
        }
    }

    private void offsetNaturalDays(Calendar calendar, int offset) {
        calendar.add(5, offset);
    }

    private List<HolidayRange> getHolidayData(String period) {
        ArrayList<HolidayRange> list = new ArrayList<HolidayRange>();
        String subPeriod = period.substring(0, 4);
        List holidayDefines = this.holidayManagerService.doQueryHolidayDefine(subPeriod);
        if (holidayDefines != null && holidayDefines.size() > 0) {
            for (HolidayDefine holidayDefine : holidayDefines) {
                list.add(new HolidayRange(holidayDefine));
            }
        }
        return list;
    }

    public static boolean checkHoliday(Calendar calendar, List<HolidayRange> list) {
        for (HolidayRange holidayRange : list) {
            boolean a = holidayRange.isHolidayRange(calendar.getTime());
            if (a) {
                return true;
            }
            boolean b = holidayRange.isWorkRange(calendar.getTime());
            if (!b) continue;
            return false;
        }
        return calendar.get(7) == 1 || calendar.get(7) == 7;
    }

    private boolean fillInAutoDueOpen(TaskDefine taskefine) {
        boolean open = false;
        FillInAutomaticallyDue fillInAutomaticallyDue = taskefine.getFillInAutomaticallyDue();
        int type = fillInAutomaticallyDue.getType();
        if (FillInAutomaticallyDue.Type.CLOSE.getValue() != type) {
            open = true;
        }
        return open;
    }
}

