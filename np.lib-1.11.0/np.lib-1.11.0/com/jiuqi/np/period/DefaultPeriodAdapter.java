/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.DateAider;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.period.multilang.WrapperData;
import com.jiuqi.np.period.multilang.handler.LanguagePeriodHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DefaultPeriodAdapter
implements IPeriodAdapter {
    @Override
    public boolean priorYear(PeriodWrapper periodWrapper) {
        return this.modifyYear(periodWrapper, -1);
    }

    @Override
    public boolean nextYear(PeriodWrapper periodWrapper) {
        return this.modifyYear(periodWrapper, 1);
    }

    @Override
    public boolean modifyYear(PeriodWrapper periodWrapper, int modifyCount) {
        if (modifyCount == 0) {
            return true;
        }
        int year = periodWrapper.getYear() + modifyCount;
        if (year < 0 || year > 9999) {
            return false;
        }
        int type = periodWrapper.getType();
        int period = periodWrapper.getPeriod();
        if (type == 6 && period > 59) {
            int day = period;
            if (DateAider.isLeapYear(year - modifyCount)) {
                --day;
            }
            if (DateAider.isLeapYear(year)) {
                ++day;
            }
            period = day;
        }
        PeriodWrapper newPeriodWrapper = new PeriodWrapper(year, type, period);
        periodWrapper.assign(newPeriodWrapper);
        return true;
    }

    @Override
    public boolean priorPeriod(PeriodWrapper periodWrapper) {
        return this.modifyPeriod(periodWrapper, -1);
    }

    @Override
    public boolean nextPeriod(PeriodWrapper periodWrapper) {
        return this.modifyPeriod(periodWrapper, 1);
    }

    @Override
    public boolean modifyPeriod(PeriodWrapper periodWrapper, int modifyCount) {
        int newPeriod;
        int year = periodWrapper.getYear();
        int type = periodWrapper.getType();
        int period = periodWrapper.getPeriod();
        while (modifyCount < 0) {
            newPeriod = period + modifyCount;
            if (newPeriod > 0) {
                period = newPeriod;
                modifyCount = 0;
                break;
            }
            if (--year < 0) {
                return false;
            }
            period = this.getHighBound(year, type);
            modifyCount = newPeriod;
        }
        while (modifyCount > 0) {
            newPeriod = period + modifyCount;
            int high = this.getHighBound(year, type);
            if (newPeriod <= high) {
                period = newPeriod;
                break;
            }
            if (++year > 9999) {
                return false;
            }
            period = 1;
            modifyCount = newPeriod - high - 1;
        }
        PeriodWrapper newPeriodWrapper = new PeriodWrapper(year, type, period);
        periodWrapper.assign(newPeriodWrapper);
        return true;
    }

    @Override
    public String getPeriodTitle(PeriodWrapper periodWrapper) {
        WrapperData wraper = new WrapperData(periodWrapper.getYear(), periodWrapper.getType(), periodWrapper.getPeriod());
        return LanguagePeriodHandler.localizePeriod(wraper, periodWrapper.getLanguage());
    }

    @Override
    public Date[] getPeriodDateRegion(PeriodWrapper periodWrapper) throws ParseException {
        String[] periodList = this.getPeriodDateStrRegion(periodWrapper);
        if (periodList == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date[] dateRegion = new Date[]{simpleDateFormat.parse(periodList[0]), simpleDateFormat.parse(periodList[1])};
        return dateRegion;
    }

    @Override
    public String[] getPeriodDateStrRegion(PeriodWrapper periodWrapper) throws ParseException {
        return PeriodUtil.getTimesArr(periodWrapper);
    }

    @Override
    public Date[] getPeriodDateRegion(String period) throws ParseException {
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        return this.getPeriodDateRegion(periodWrapper);
    }

    @Override
    public String[] getPeriodDateStrRegion(String period) throws ParseException {
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        return this.getPeriodDateStrRegion(periodWrapper);
    }

    @Override
    public String getPeriodTitle(String period) {
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        return this.getPeriodTitle(periodWrapper);
    }

    @Override
    public String modify(String period, PeriodModifier modifier) {
        return modifier.modify(period);
    }

    @Override
    public boolean modify(PeriodWrapper periodWrapper, PeriodModifier modifier) {
        return modifier.modify(periodWrapper);
    }

    private int getHighBound(int year, int type) {
        int result = PeriodConsts.PERIOD_TYPE_HIGH_BOUNDs[type];
        if (type == 6 && !DateAider.isLeapYear(year)) {
            --result;
        }
        return result;
    }

    public String getDateStr(String periodStr) throws Exception {
        int y = Integer.parseInt(periodStr.substring(0, 4));
        int m = 0;
        int d = 1;
        GregorianCalendar calendar = new GregorianCalendar(y, m, d);
        int month = calendar.get(2);
        while (month > m) {
            ((Calendar)calendar).add(5, -1);
            month = calendar.get(2);
        }
        int periodNum = Integer.parseInt(periodStr.substring(5));
        ((Calendar)calendar).add(5, periodNum - 1);
        int ryear = calendar.get(1);
        int rmonth = calendar.get(2);
        int rday = calendar.get(5);
        return ryear + "-" + (rmonth + 1) + "-" + rday;
    }

    @Override
    public int getPeriodType(PeriodWrapper periodWrapper) {
        return periodWrapper.getType();
    }

    @Override
    public int getPeriodType(String period) {
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        return periodWrapper.getType();
    }

    @Override
    public String getStandardPeriodStr(String periodCode) {
        return periodCode;
    }

    @Override
    public String getCustomPeriodStr(String periodCode) {
        return periodCode;
    }

    @Override
    public String modify(String period, PeriodModifier modifier, IPeriodAdapter targetPeriodAdapter) {
        return this.modify(period, modifier);
    }

    @Override
    public boolean modify(PeriodWrapper periodWrapper, PeriodModifier modifier, IPeriodAdapter targetPeriodAdapter) {
        return this.modify(periodWrapper, modifier);
    }
}

