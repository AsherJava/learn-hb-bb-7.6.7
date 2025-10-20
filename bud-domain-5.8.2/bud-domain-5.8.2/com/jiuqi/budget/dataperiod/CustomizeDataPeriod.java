/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.ICustomizePeriodAdapter;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomizeDataPeriod
implements DataPeriod {
    private final IPeriodRow iPeriodRow;
    private final IDataPeriodType periodType;
    private final int year;
    private final int period;
    private final String dataTime;
    private final ICustomizePeriodAdapter customizePeriodAdapter;

    public CustomizeDataPeriod(ICustomizePeriodAdapter customizePeriodAdapter, IPeriodRow iPeriodRow, int year, IDataPeriodType periodType, int period, String dataTime) {
        this.iPeriodRow = iPeriodRow;
        this.periodType = periodType;
        this.year = year;
        this.period = period;
        this.dataTime = dataTime;
        this.customizePeriodAdapter = customizePeriodAdapter;
    }

    @Override
    public long getTime() {
        return this.iPeriodRow == null ? DateTimeCenter.convertLDTToLong((LocalDateTime)DateTimeCenter.MIN_LOCAL_DATE_TIME).longValue() : this.iPeriodRow.getStartDate().getTime();
    }

    @Override
    public LocalDateTime getDate() {
        return this.iPeriodRow == null ? DateTimeCenter.MIN_LOCAL_DATE_TIME : DateTimeCenter.convertDateToLDT((Date)this.iPeriodRow.getStartDate());
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return this.iPeriodRow == null ? DateTimeCenter.MIN_LOCAL_DATE_TIME : DateTimeCenter.convertDateToLDT((Date)this.iPeriodRow.getStartDate());
    }

    @Override
    public IDataPeriodType getType() {
        return this.periodType;
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
        return this.iPeriodRow == null ? DateTimeCenter.MIN_LOCAL_DATE_TIME : DateTimeCenter.convertDateToLDT((Date)this.iPeriodRow.getStartDate());
    }

    @Override
    public LocalDateTime getEndDay() {
        return this.iPeriodRow == null ? DateTimeCenter.MAX_LOCAL_DATE_TIME : DateTimeCenter.convertDateToLDT((Date)this.iPeriodRow.getEndDate());
    }

    @Override
    public String getTitle() {
        return this.iPeriodRow == null ? this.dataTime : (this.iPeriodRow.getSimpleTitle() == null ? this.iPeriodRow.getTitle() : this.iPeriodRow.getSimpleTitle());
    }

    @Override
    public String getName() {
        return this.dataTime;
    }

    @Override
    public DataPeriod getLogicParentPeriod() {
        return null;
    }

    @Override
    public String getRelativePeriod(String tarPeriod) {
        return this.customizePeriodAdapter.getRelativePeriod(this, DataPeriodFactory.valueOf(tarPeriod));
    }

    @Override
    public String getRelativePeriod(DataPeriod tarPeriod) {
        return this.customizePeriodAdapter.getRelativePeriod(this, tarPeriod);
    }

    public String toString() {
        return this.getName();
    }
}

