/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.period;

import com.jiuqi.gcreport.org.impl.period.PeriodObject;
import java.util.Calendar;
import java.util.Date;

public enum PeriodTypeEnum {
    YEAR(1, 'N', "\u5e74", "\u5e74\u5ea6", 1){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int period = time.get(2) / 12 + 1;
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, 0, 1, 0);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, 12, 1, -24);
        }
    }
    ,
    HALFYEAR(2, 'H', "\u534a\u5e74", null, 2){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int period = time.get(2) / 6 + 1;
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, (period - 1) * 6, 1, 0);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, period * 6, 1, -24);
        }
    }
    ,
    SEASON(3, 'J', "\u5b63", "\u5b63\u5ea6", 4){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int period = time.get(2) / 3 + 1;
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, (period - 1) * 3, 1, 0);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, period * 3, 1, -24);
        }
    }
    ,
    MONTH(4, 'Y', "\u6708", "\u6708\u4efd", 12){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int period = time.get(2) / 1 + 1;
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, period - 1, 1, 0);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, period, 1, -24);
        }
    }
    ,
    TENDAY(5, 'X', "\u65ec", null, 36){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int m = time.get(2);
            int d = time.get(5);
            int period = m * 3 + (d < 11 ? 1 : (d < 21 ? 2 : 3));
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, (period - 1) / 3, (period - 1) % 3 * 10 + 1, 0);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, period / 3, period % 3 * 10 + 1, -24);
        }
    }
    ,
    DAY(6, 'R', "\u65e5", "\u5929", 366){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int period = time.get(6);
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, 0, period, 0);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getDate(year, 0, period + 1, -24);
        }
    }
    ,
    WEEK(7, 'Z', "\u5468", "\u5468\u6b21", 53){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            int year = time.get(1);
            int period = time.get(3);
            return PeriodObject.getInstance(year, this, period);
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return PeriodTypeEnum.getWeekDate(year, period, 2);
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return PeriodTypeEnum.getWeekDate(year, period + 1, 1);
        }
    }
    ,
    NULL(0, ' ', "\u65e0", null, 0){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            return null;
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return null;
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return null;
        }
    }
    ,
    CUSTOM(8, 'B', "\u671f", "\u671f\u95f4", 9999){

        @Override
        public PeriodObject dateToObject(Calendar time) {
            return null;
        }

        @Override
        public Date objectToBeginDate(int year, int period) {
            return null;
        }

        @Override
        public Date objectToEndDate(int year, int period) {
            return null;
        }
    };

    private int id;
    private char code;
    private String name;
    private String allName;
    private int maxBound;

    private PeriodTypeEnum(int id, char code, String name, String allName, int maxBound) {
        this.allName = allName;
        this.id = id;
        this.code = code;
        this.name = name;
        this.maxBound = maxBound;
    }

    public int getId() {
        return this.id;
    }

    public char getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getAllName() {
        return this.allName;
    }

    public int getMaxBound() {
        return this.maxBound;
    }

    public abstract PeriodObject dateToObject(Calendar var1);

    public abstract Date objectToBeginDate(int var1, int var2);

    public abstract Date objectToEndDate(int var1, int var2);

    public static PeriodTypeEnum findByCode(char code) {
        for (PeriodTypeEnum type : PeriodTypeEnum.values()) {
            if (type.getCode() != code) continue;
            return type;
        }
        return null;
    }

    public static PeriodTypeEnum findById(int id) {
        for (PeriodTypeEnum type : PeriodTypeEnum.values()) {
            if (type.getId() != id) continue;
            return type;
        }
        return null;
    }

    public static PeriodTypeEnum findByName(String name) {
        for (PeriodTypeEnum type : PeriodTypeEnum.values()) {
            if (!name.equals(type.getName())) continue;
            return type;
        }
        return null;
    }

    private static Date getDate(int year, int month, int day, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.set(2, month);
        calendar.set(5, day);
        calendar.set(10, hour);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    private static Date getWeekDate(int year, int week, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.set(3, week);
        calendar.set(7, day);
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }
}

