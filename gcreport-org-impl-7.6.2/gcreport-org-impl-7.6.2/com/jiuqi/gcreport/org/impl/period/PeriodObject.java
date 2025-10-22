/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.period;

import com.jiuqi.gcreport.org.impl.period.PeriodTypeEnum;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.util.StringUtils;

class PeriodObject {
    private int year;
    private int period;
    private PeriodTypeEnum type;
    private Date time;

    public static PeriodObject getInstance() {
        return PeriodObject.getInstance(Calendar.getInstance());
    }

    public static PeriodObject getInstance(Calendar date) {
        return new PeriodObject(date.get(1), PeriodTypeEnum.MONTH, date.get(2) + 1);
    }

    public static PeriodObject getInstance(String periodString) {
        return PeriodObject.parseString(periodString);
    }

    public static PeriodObject getInstance(int year, int period) {
        return new PeriodObject(year, PeriodTypeEnum.MONTH, period);
    }

    public static PeriodObject getInstance(int year, int type, int period) {
        return new PeriodObject(year, PeriodTypeEnum.findById(type), period);
    }

    public static PeriodObject getInstance(int year, PeriodTypeEnum type, int period) {
        return new PeriodObject(year, type, period);
    }

    private PeriodObject(int year, PeriodTypeEnum type, int period) {
        this.setYear(year);
        this.setPeriod(period);
        this.setType(type);
    }

    private static PeriodObject parseString(String periodString) {
        if (StringUtils.isEmpty(periodString)) {
            return PeriodObject.getInstance();
        }
        if (periodString.trim().length() == 8) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = df.parse(periodString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return PeriodObject.getInstance(calendar);
            }
            catch (ParseException e) {
                throw new IllegalArgumentException("\u65e5\u671f\u4ee3\u7801\u683c\u5f0f\u4e0d\u662fyyyyMMdd\uff1a" + periodString);
            }
        }
        try {
            int year = Integer.parseInt(periodString.substring(0, 4));
            PeriodTypeEnum type = PeriodTypeEnum.findByCode(periodString.charAt(4));
            int period = Integer.parseInt(periodString.substring(5));
            if (year < 1970 || year > 9999) {
                throw new IllegalArgumentException("\u5e74\u4efd\u65e0\u6548\uff1a" + periodString);
            }
            if (type == null) {
                throw new IllegalArgumentException("\u65f6\u671f\u7c7b\u578b\u65e0\u6548\uff1a" + periodString);
            }
            if (period < 0 || period > type.getMaxBound()) {
                throw new IllegalArgumentException("\u65f6\u671f\u65e0\u6548\uff1a" + periodString);
            }
            return new PeriodObject(year, type, period);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("\u65f6\u671f\u4ee3\u7801\u683c\u5f0f\u975e\u6cd5\uff1a" + periodString);
        }
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public PeriodTypeEnum getType() {
        return this.type;
    }

    public void setType(PeriodTypeEnum type) {
        this.type = type;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getPeriodDateBegin() {
        return this.getType().objectToBeginDate(this.getYear(), this.getPeriod());
    }

    public Date getPeriodDateEnd() {
        return this.getType().objectToEndDate(this.getYear(), this.getPeriod());
    }

    public PeriodObject clone() throws CloneNotSupportedException {
        return new PeriodObject(this.year, this.type, this.period);
    }

    public int compareTo(Date destDate) {
        Calendar destCalendar = Calendar.getInstance();
        destCalendar.setTime(destDate);
        PeriodObject newPO = this.getType().dateToObject(destCalendar);
        int result = this.getYear() - newPO.getYear();
        if (result != 0) {
            return result;
        }
        return this.getPeriod() - newPO.getPeriod();
    }

    public String toString() {
        return String.format("%04d%c%04d", this.getYear() % 10000, Character.valueOf(this.getType().getCode()), this.getPeriod() % 10000);
    }
}

