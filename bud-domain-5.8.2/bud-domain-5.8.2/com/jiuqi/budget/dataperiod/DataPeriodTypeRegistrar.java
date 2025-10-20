/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.ICustomizePeriodAdapter;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataPeriodTypeRegistrar {
    public static List<IDataPeriodType> getAllDataPeriodTypes() {
        ICustomizePeriodAdapter bean = (ICustomizePeriodAdapter)ApplicationContextRegister.getBean(ICustomizePeriodAdapter.class);
        List<IDataPeriodType> nrPeriodTypes = null;
        if (bean != null) {
            nrPeriodTypes = bean.getNrPeriodTypes();
        }
        DataPeriodType[] values = DataPeriodType.values();
        if (nrPeriodTypes == null || nrPeriodTypes.isEmpty()) {
            return Arrays.asList(values);
        }
        int length = values.length;
        ArrayList<IDataPeriodType> list = new ArrayList<IDataPeriodType>(length + nrPeriodTypes.size());
        for (int i = 0; i < length; ++i) {
            list.add(values[i]);
        }
        list.addAll(nrPeriodTypes);
        return list;
    }

    public static IDataPeriodType typeOf(String type) {
        switch (type) {
            case "W": {
                return DataPeriodType.PERIOD_TYPE_NONE;
            }
            case "N": {
                return DataPeriodType.PERIOD_TYPE_YEAR;
            }
            case "H": {
                return DataPeriodType.PERIOD_TYPE_HALFYEAR;
            }
            case "J": {
                return DataPeriodType.PERIOD_TYPE_SEASON;
            }
            case "Y": {
                return DataPeriodType.PERIOD_TYPE_MONTH;
            }
            case "X": {
                return DataPeriodType.PERIOD_TYPE_TENDAY;
            }
            case "R": {
                return DataPeriodType.PERIOD_TYPE_DAY;
            }
            case "Z": {
                return DataPeriodType.PERIOD_TYPE_WEEK;
            }
        }
        try {
            return DataPeriodType.valueOf(type);
        }
        catch (Exception e) {
            ICustomizePeriodAdapter bean = (ICustomizePeriodAdapter)ApplicationContextRegister.getBean(ICustomizePeriodAdapter.class);
            if (bean == null) {
                return null;
            }
            return bean.getTypeByName(type);
        }
    }
}

