/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.PeriodTypeGroup;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;

public enum DataPeriodType implements IDataPeriodType
{
    PERIOD_TYPE_YEAR{

        @Override
        public String getType() {
            return "N";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u5e74";
        }

        @Override
        public int getMax() {
            return 1;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return 1;
        }

        @Override
        public DataPeriodType nextType() {
            return PERIOD_TYPE_HALFYEAR;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                return startDay.withYear(offSetVal);
            }
            return startDay.plusYears(offSetVal);
        }
    }
    ,
    PERIOD_TYPE_HALFYEAR{

        @Override
        public String getType() {
            return "H";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u534a\u5e74";
        }

        @Override
        public int getMax() {
            return 2;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return localDate.getMonthValue() <= 6 ? 1 : 2;
        }

        @Override
        public DataPeriodType nextType() {
            return PERIOD_TYPE_SEASON;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                return startDay.withMonth((offSetVal - 1) * 6 + 1);
            }
            return startDay.plusMonths((long)offSetVal * 6L);
        }
    }
    ,
    PERIOD_TYPE_SEASON{

        @Override
        public String getType() {
            return "J";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u5b63";
        }

        @Override
        public int getMax() {
            return 4;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return localDate.get(IsoFields.QUARTER_OF_YEAR);
        }

        @Override
        public DataPeriodType nextType() {
            return PERIOD_TYPE_MONTH;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                return startDay.withMonth((offSetVal - 1) * 3 + 1);
            }
            return startDay.plusMonths((long)offSetVal * 3L);
        }
    }
    ,
    PERIOD_TYPE_MONTH{

        @Override
        public String getType() {
            return "Y";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u6708";
        }

        @Override
        public int getMax() {
            return 12;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return localDate.getMonthValue();
        }

        @Override
        public DataPeriodType nextType() {
            return PERIOD_TYPE_TENDAY;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                return startDay.withMonth(offSetVal);
            }
            return startDay.plusMonths(offSetVal);
        }
    }
    ,
    PERIOD_TYPE_TENDAY{

        @Override
        public String getType() {
            return "X";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u65ec";
        }

        @Override
        public int getMax() {
            return 36;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return (localDate.getMonthValue() - 1) * 3 + (localDate.getDayOfMonth() <= 10 ? 1 : (localDate.getDayOfMonth() <= 20 ? 2 : 3));
        }

        @Override
        public DataPeriodType nextType() {
            return PERIOD_TYPE_WEEK;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                int month = (offSetVal - 1) / 3 + 1;
                int day = (offSetVal - 1) % 3 * 10 + 1;
                return startDay.withMonth(month).withDayOfMonth(day);
            }
            return startDay.plusDays((long)offSetVal * 10L);
        }
    }
    ,
    PERIOD_TYPE_WEEK{

        @Override
        public String getType() {
            return "Z";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u5468";
        }

        @Override
        public int getMax() {
            return 54;
        }

        @Override
        public int getMax(int year) {
            LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
            return lastDayOfYear.get(weekFields.weekOfYear());
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
            int week = localDate.get(weekFields.weekOfYear());
            if (week == 0) {
                return 1;
            }
            return week;
        }

        @Override
        public DataPeriodType nextType() {
            return PERIOD_TYPE_DAY;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
                return startDay.with(weekFields.weekOfYear(), offSetVal);
            }
            return startDay.plusWeeks(offSetVal);
        }
    }
    ,
    PERIOD_TYPE_DAY{

        @Override
        public String getType() {
            return "R";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u65e5";
        }

        @Override
        public int getMax() {
            return 366;
        }

        @Override
        public int getMax(int year) {
            if (Year.isLeap(year)) {
                return 366;
            }
            return 365;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return localDate.getDayOfYear();
        }

        @Override
        public DataPeriodType nextType() {
            return null;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            if (fixed) {
                return startDay.withDayOfYear(offSetVal);
            }
            return startDay.plusDays(offSetVal);
        }
    }
    ,
    PERIOD_TYPE_NONE{

        @Override
        public String getType() {
            return "W";
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public String getTitle() {
            return "\u65e0";
        }

        @Override
        public int getMax() {
            return 0;
        }

        @Override
        public int getPeriod(LocalDate localDate) {
            return 0;
        }

        @Override
        public DataPeriodType nextType() {
            return null;
        }

        @Override
        public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
            return startDay;
        }
    };


    public String toString() {
        return this.getType();
    }

    @Override
    public PeriodTypeGroup getGroup() {
        return PeriodTypeGroup.SYSTEM;
    }

    @Override
    public DataPeriod valueOf(LocalDate localDate) {
        return DataPeriodFactory.valueOf(localDate.getYear(), this, this.getPeriod(localDate));
    }

    public abstract int getPeriod(LocalDate var1);
}

