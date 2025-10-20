/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.ICustomizePeriodAdapter;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.PeriodTypeGroup;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomPeriodType
implements IDataPeriodType {
    private String type;
    private String name;
    private String title;
    private ICustomizePeriodAdapter customizePeriodAdapter;

    public CustomPeriodType(ICustomizePeriodAdapter customizePeriodAdapter) {
        this.customizePeriodAdapter = customizePeriodAdapter;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getMax() {
        return this.customizePeriodAdapter.getMax(this);
    }

    @Override
    public int getMax(int year) {
        return this.customizePeriodAdapter.getMax(this, year);
    }

    @Override
    public DataPeriod valueOf(LocalDate localDate) {
        return this.customizePeriodAdapter.valueOf(this, localDate);
    }

    @Override
    public LocalDateTime calcOffSetResult(LocalDateTime startDay, boolean fixed, int offSetVal) {
        DataPeriod dataPeriod = this.customizePeriodAdapter.valueOf(this, startDay.toLocalDate());
        if (fixed) {
            return DataPeriodFactory.valueOf(dataPeriod.getYear(), this, offSetVal).getStartDay();
        }
        if (offSetVal == 0) {
            return startDay;
        }
        return this.customizePeriodAdapter.getOffSetPeriodTime(this, startDay, offSetVal);
    }

    @Override
    public IDataPeriodType nextType() {
        return null;
    }

    @Override
    public PeriodTypeGroup getGroup() {
        return PeriodTypeGroup.CUSTOMIZE;
    }

    public String toString() {
        return this.getType();
    }
}

