/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.dataperiod.BudDataPeriod;
import com.jiuqi.budget.dataperiod.CustomPeriodVO;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.DataPeriodTypeRegistrar;
import com.jiuqi.budget.dataperiod.ICustomizePeriodAdapter;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.PeriodTypeGroup;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataPeriodFactory {
    private static final ConcurrentHashMap<String, DataPeriod> dataPeriodCache = new ConcurrentHashMap(200);

    public static DataPeriod valueOf(String name) {
        if (name == null || name.isEmpty()) {
            throw new BudgetException("\u65f6\u671f\u4e0d\u53ef\u4e3a\u7a7a");
        }
        if (name.length() != 9) {
            throw new BudgetException("DataPeriod\u7684name\u5fc5\u987b\u662fyyyyTpppp\u683c\u5f0f\u76849\u4f4d\u5b57\u7b26\u4e32\uff01" + name);
        }
        IDataPeriodType type = DataPeriodTypeRegistrar.typeOf(name.substring(4, 5));
        if (type == null) {
            return null;
        }
        if (type.getGroup() == PeriodTypeGroup.SYSTEM) {
            return dataPeriodCache.computeIfAbsent(name, k -> {
                int year = Integer.parseInt(name.substring(0, 4));
                int period = 1;
                if (type != DataPeriodType.PERIOD_TYPE_YEAR) {
                    period = Integer.parseInt(name.substring(6, 9));
                }
                return DataPeriodFactory.valueOf(year, type, period);
            });
        }
        int year = Integer.parseInt(name.substring(0, 4));
        int period = 1;
        if (type != DataPeriodType.PERIOD_TYPE_YEAR) {
            period = Integer.parseInt(name.substring(6, 9));
        }
        ICustomizePeriodAdapter ICustomizePeriodAdapter2 = (ICustomizePeriodAdapter)ApplicationContextRegister.getBean(ICustomizePeriodAdapter.class);
        return ICustomizePeriodAdapter2.getNrDataPeriod(name, year, type, period);
    }

    public static DataPeriod valueOf(Integer year, IDataPeriodType type, Integer period) {
        if (type.getGroup() == PeriodTypeGroup.SYSTEM) {
            return new BudDataPeriod(year, (DataPeriodType)type, period);
        }
        ICustomizePeriodAdapter ICustomizePeriodAdapter2 = (ICustomizePeriodAdapter)ApplicationContextRegister.getBean(ICustomizePeriodAdapter.class);
        if (ICustomizePeriodAdapter2 == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(9);
        builder.append(year).append(type.getType());
        String name = period < 10 ? builder.append("000").append(period).toString().intern() : (period < 100 ? builder.append("00").append(period).toString().intern() : (period < 1000 ? builder.append("0").append(period).toString().intern() : builder.append(period).toString().intern()));
        return ICustomizePeriodAdapter2.getNrDataPeriod(name, year, type, period);
    }

    public static DataPeriod valueOf(LocalDate localDate, IDataPeriodType type) {
        return type.valueOf(localDate);
    }

    public static DataPeriod valueOf(LocalDateTime localDateTime, IDataPeriodType type) {
        return DataPeriodFactory.valueOf(localDateTime.toLocalDate(), type);
    }

    public static List<CustomPeriodVO> getAllCustomPeriods(IDataPeriodType type) {
        ICustomizePeriodAdapter ICustomizePeriodAdapter2 = (ICustomizePeriodAdapter)ApplicationContextRegister.getBean(ICustomizePeriodAdapter.class);
        if (ICustomizePeriodAdapter2 == null) {
            return Collections.emptyList();
        }
        return ICustomizePeriodAdapter2.getAllCustomPeriods(type);
    }
}

