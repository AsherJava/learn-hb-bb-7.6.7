/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.dataperiod.CustomPeriodType;
import com.jiuqi.budget.dataperiod.CustomPeriodVO;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICustomizePeriodAdapter {
    public DataPeriod getNrDataPeriod(String var1, int var2, IDataPeriodType var3, int var4);

    public List<IDataPeriodType> getNrPeriodTypes();

    public IDataPeriodType getTypeByName(String var1);

    public int getMax(IDataPeriodType var1);

    public int getMax(IDataPeriodType var1, int var2);

    public List<CustomPeriodVO> getAllCustomPeriods(IDataPeriodType var1);

    public DataPeriod valueOf(CustomPeriodType var1, LocalDate var2);

    public LocalDateTime getOffSetPeriodTime(CustomPeriodType var1, LocalDateTime var2, int var3);

    public String getRelativePeriod(DataPeriod var1, DataPeriod var2);
}

