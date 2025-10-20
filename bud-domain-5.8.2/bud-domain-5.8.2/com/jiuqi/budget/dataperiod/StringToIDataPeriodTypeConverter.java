/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod;

import com.jiuqi.budget.dataperiod.DataPeriodTypeRegistrar;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component(value="StringToIDataPeriodTypeConverter")
public class StringToIDataPeriodTypeConverter
implements Converter<String, IDataPeriodType> {
    @Override
    public IDataPeriodType convert(String source) {
        IDataPeriodType typeByName = DataPeriodTypeRegistrar.typeOf(source);
        if (typeByName != null) {
            return typeByName;
        }
        return DataPeriodTypeRegistrar.typeOf(source);
    }
}

