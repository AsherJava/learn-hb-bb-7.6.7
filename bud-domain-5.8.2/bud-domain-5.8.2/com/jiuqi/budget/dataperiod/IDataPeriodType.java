/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.budget.dataperiod;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.PeriodTypeGroup;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IDataPeriodType {
    public String getType();

    public String getName();

    public String getTitle();

    public int getMax();

    default public int getMax(int year) {
        return this.getMax();
    }

    public IDataPeriodType nextType();

    @JsonIgnore
    public PeriodTypeGroup getGroup();

    default public int compareTo(IDataPeriodType o) {
        return this.getName().compareTo(o.getName());
    }

    public DataPeriod valueOf(LocalDate var1);

    public LocalDateTime calcOffSetResult(LocalDateTime var1, boolean var2, int var3);
}

