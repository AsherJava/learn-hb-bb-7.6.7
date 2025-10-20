/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.BudI18nUtil
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.budget.common.utils.Num2Ch
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.common.utils.BudI18nUtil;
import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.common.utils.Num2Ch;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.ICustomizePeriodAdapter;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.PeriodTypeGroup;
import com.jiuqi.budget.dataperiod.format.DataPeriodFormatCollector;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import org.springframework.util.Assert;

public final class BudDataPeriod
implements DataPeriod {
    private final Integer year;
    private final DataPeriodType type;
    private final Integer period;

    BudDataPeriod(Integer year, DataPeriodType type, Integer period) {
        this.year = year;
        this.type = type;
        this.period = type == DataPeriodType.PERIOD_TYPE_YEAR ? Integer.valueOf(1) : period;
        this.check();
    }

    private void check() {
        if (this.type != DataPeriodType.PERIOD_TYPE_NONE) {
            Assert.isTrue(this.period > 0, "\u65f6\u671f\u5e94\u5927\u4e8e\u96f6");
            Assert.isTrue(this.period <= this.type.getMax(), "\u65f6\u671f\u5e94\u5c0f\u4e8e\u6700\u5927\u503c");
        } else {
            Assert.isTrue(this.period == 0, "\u65f6\u671f\u5e94\u7b49\u4e8e\u96f6");
        }
    }

    @Override
    public long getTime() {
        if (DataPeriodType.PERIOD_TYPE_NONE == this.type) {
            return System.currentTimeMillis();
        }
        return DateTimeCenter.convertLDTToLong((LocalDateTime)this.getStartDay());
    }

    @Override
    public LocalDateTime getDate() {
        return this.getStartDay();
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return this.getStartDay();
    }

    @Override
    public IDataPeriodType getType() {
        return this.type;
    }

    @Override
    public int getPeriod() {
        return this.period;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public LocalDateTime getStartDay() {
        switch (this.type) {
            case PERIOD_TYPE_YEAR: {
                return LocalDateTime.of(LocalDate.of((int)this.year, 1, 1), LocalTime.MIN);
            }
            case PERIOD_TYPE_HALFYEAR: {
                return LocalDateTime.of(LocalDate.of((int)this.year, (this.period - 1) * 6 + 1, 1), LocalTime.MIN);
            }
            case PERIOD_TYPE_SEASON: {
                return LocalDateTime.of(LocalDate.of((int)this.year, (this.period - 1) * 3 + 1, 1), LocalTime.MIN);
            }
            case PERIOD_TYPE_MONTH: {
                return LocalDateTime.of(LocalDate.of((int)this.year, this.period, 1), LocalTime.MIN);
            }
            case PERIOD_TYPE_TENDAY: {
                int count = this.period % 3;
                int month = count == 0 ? this.period / 3 : this.period / 3 + 1;
                return LocalDateTime.of(LocalDate.of((int)this.year, month, (this.period - ((month - 1) * 3 + 1)) * 10 + 1), LocalTime.MIN);
            }
            case PERIOD_TYPE_WEEK: {
                WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
                return LocalDateTime.of(LocalDate.now(), LocalTime.MIN).withYear(this.year).with(weekFields.weekOfYear(), this.period.intValue()).with(weekFields.dayOfWeek(), 1L);
            }
            case PERIOD_TYPE_DAY: {
                return LocalDateTime.of(LocalDate.now(), LocalTime.MIN).withYear(this.year).withDayOfYear(this.period);
            }
        }
        return LocalDateTime.now();
    }

    @Override
    public LocalDateTime getEndDay() {
        switch (this.type) {
            case PERIOD_TYPE_YEAR: {
                return LocalDateTime.of(LocalDate.of((int)this.year, 12, 31), DateTimeCenter.MAX_LOCAL_TIME);
            }
            case PERIOD_TYPE_HALFYEAR: {
                return LocalDateTime.of(LocalDate.of((int)this.year, this.period * 6, 1), DateTimeCenter.MAX_LOCAL_TIME).with(TemporalAdjusters.lastDayOfMonth());
            }
            case PERIOD_TYPE_SEASON: {
                return LocalDateTime.of(LocalDate.of((int)this.year, this.period * 3, 1), DateTimeCenter.MAX_LOCAL_TIME).with(TemporalAdjusters.lastDayOfMonth());
            }
            case PERIOD_TYPE_MONTH: {
                return LocalDateTime.of(LocalDate.MAX.withYear(this.year).withMonth(this.period), DateTimeCenter.MAX_LOCAL_TIME);
            }
            case PERIOD_TYPE_TENDAY: {
                int month = this.period / 3 + 1;
                int count = this.period % 3;
                return LocalDateTime.of(LocalDate.now().withYear(this.year).withMonth(month).withDayOfMonth(count < 2 ? (count + 1) * 10 : 31), DateTimeCenter.MAX_LOCAL_TIME);
            }
            case PERIOD_TYPE_WEEK: {
                WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
                return LocalDateTime.of(LocalDate.now(), DateTimeCenter.MAX_LOCAL_TIME).withYear(this.year).with(weekFields.weekOfYear(), this.period.intValue()).with(weekFields.dayOfWeek(), 7L);
            }
            case PERIOD_TYPE_DAY: {
                return LocalDateTime.of(LocalDate.now(), DateTimeCenter.MAX_LOCAL_TIME).withYear(this.year).withDayOfYear(this.period);
            }
        }
        return LocalDateTime.now();
    }

    public String toString() {
        return this.getName();
    }

    @Override
    public String getTitle() {
        if (BudI18nUtil.checkIsNotInChinese()) {
            return ((DataPeriodFormatCollector)ApplicationContextRegister.getBean(DataPeriodFormatCollector.class)).useDefaultFormat(BudI18nUtil.getLocale(), this);
        }
        StringBuilder builder = new StringBuilder(9);
        builder.append(this.year);
        switch (this.type) {
            case PERIOD_TYPE_YEAR: {
                return builder.append(DataPeriodType.PERIOD_TYPE_YEAR.getTitle()).toString().intern();
            }
            case PERIOD_TYPE_HALFYEAR: {
                return builder.append(this.period == 1 ? "\u4e0a" : "\u4e0b").append(DataPeriodType.PERIOD_TYPE_HALFYEAR.getTitle()).toString().intern();
            }
            case PERIOD_TYPE_SEASON: 
            case PERIOD_TYPE_MONTH: {
                return builder.append(DataPeriodType.PERIOD_TYPE_YEAR.getTitle()).append(this.period).append(this.type.getTitle()).toString().intern();
            }
            case PERIOD_TYPE_WEEK: {
                return builder.append(DataPeriodType.PERIOD_TYPE_YEAR.getTitle()).append(" \u7b2c").append(Num2Ch.cvt((long)this.period.intValue())).append(this.type.getTitle()).toString().intern();
            }
            case PERIOD_TYPE_TENDAY: {
                int month = (int)Math.ceil((double)this.period.intValue() / 3.0);
                int count = this.period % 3;
                return builder.append(DataPeriodType.PERIOD_TYPE_YEAR.getTitle()).append(month).append(DataPeriodType.PERIOD_TYPE_MONTH.getTitle()).append(count == 0 ? "\u4e0b" : (count == 1 ? "\u4e0a" : "\u4e2d")).append(this.type.getTitle()).toString().intern();
            }
            case PERIOD_TYPE_DAY: {
                LocalDate localDate = LocalDate.now().withYear(this.year).withDayOfYear(this.period);
                return builder.append(DataPeriodType.PERIOD_TYPE_YEAR.getTitle()).append(localDate.getMonthValue()).append(DataPeriodType.PERIOD_TYPE_MONTH.getTitle()).append(localDate.getDayOfMonth()).append(this.type.getTitle()).toString().intern();
            }
        }
        return DataPeriodType.PERIOD_TYPE_NONE.getTitle();
    }

    @Override
    public String getName() {
        if (DataPeriodType.PERIOD_TYPE_NONE == this.type) {
            return "0000W0000";
        }
        StringBuilder builder = new StringBuilder(9);
        builder.append(this.year).append(this.type.getType());
        if (this.period < 10) {
            return builder.append("000").append(this.period).toString().intern();
        }
        if (this.period < 100) {
            return builder.append("00").append(this.period).toString().intern();
        }
        return builder.append("0").append(this.period).toString().intern();
    }

    @Override
    public DataPeriod getLogicParentPeriod() {
        if (DataPeriodType.PERIOD_TYPE_YEAR.equals(this.type) || DataPeriodType.PERIOD_TYPE_NONE.equals(this.type)) {
            return null;
        }
        DataPeriod logicParentPeriod = null;
        LocalDateTime startDay = this.getStartDay();
        int month = startDay.getMonthValue();
        switch (this.type) {
            case PERIOD_TYPE_HALFYEAR: {
                logicParentPeriod = DataPeriodFactory.valueOf(this.year, DataPeriodType.PERIOD_TYPE_YEAR, 1);
                break;
            }
            case PERIOD_TYPE_SEASON: {
                logicParentPeriod = DataPeriodFactory.valueOf(this.year, DataPeriodType.PERIOD_TYPE_HALFYEAR, (month - 1) / 6 + 1);
                break;
            }
            case PERIOD_TYPE_TENDAY: {
                logicParentPeriod = DataPeriodFactory.valueOf(this.year, DataPeriodType.PERIOD_TYPE_MONTH, month);
                break;
            }
            case PERIOD_TYPE_MONTH: {
                logicParentPeriod = DataPeriodFactory.valueOf(this.year, DataPeriodType.PERIOD_TYPE_SEASON, (month - 1) / 3 + 1);
                break;
            }
            case PERIOD_TYPE_WEEK: {
                logicParentPeriod = DataPeriodFactory.valueOf(this.year, DataPeriodType.PERIOD_TYPE_MONTH, month);
                break;
            }
            case PERIOD_TYPE_DAY: {
                WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
                logicParentPeriod = DataPeriodFactory.valueOf(this.year, DataPeriodType.PERIOD_TYPE_WEEK, startDay.get(weekFields.weekOfYear()) + 1);
                break;
            }
        }
        return logicParentPeriod;
    }

    @Override
    public String getRelativePeriod(String tarPeriod) {
        return this.getRelativePeriod(DataPeriodFactory.valueOf(tarPeriod));
    }

    @Override
    public String getRelativePeriod(DataPeriod tarPeriod) {
        if (tarPeriod.getType().getGroup() == PeriodTypeGroup.CUSTOMIZE) {
            return ((ICustomizePeriodAdapter)ApplicationContextRegister.getBean(ICustomizePeriodAdapter.class)).getRelativePeriod(this, tarPeriod);
        }
        if (this.type == DataPeriodType.PERIOD_TYPE_NONE) {
            return tarPeriod.getName();
        }
        StringBuilder builder = new StringBuilder(9);
        int tarPeriodYear = tarPeriod.getYear();
        IDataPeriodType tarPeriodType = tarPeriod.getType();
        int yearDiff = tarPeriodYear - this.year;
        if (yearDiff == 0) {
            builder.append('-');
        } else if (yearDiff > 0) {
            builder.append('+');
        }
        builder.append(yearDiff).append(DataPeriodType.PERIOD_TYPE_YEAR.getType());
        if (tarPeriodType == DataPeriodType.PERIOD_TYPE_YEAR) {
            return builder.toString();
        }
        builder.append(',').append(tarPeriod.getPeriod()).append(tarPeriodType.getType());
        return builder.toString();
    }
}

