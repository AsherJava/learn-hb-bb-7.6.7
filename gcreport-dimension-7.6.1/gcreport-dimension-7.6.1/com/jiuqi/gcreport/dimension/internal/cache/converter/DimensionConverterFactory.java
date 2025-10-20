/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.dimension.domain.DimensionDO
 *  com.jiuqi.budget.param.measurement.domain.MeasurementDO
 */
package com.jiuqi.gcreport.dimension.internal.cache.converter;

import com.jiuqi.budget.param.dimension.domain.DimensionDO;
import com.jiuqi.budget.param.measurement.domain.MeasurementDO;
import com.jiuqi.gcreport.dimension.internal.cache.converter.DimensionConverter;
import com.jiuqi.gcreport.dimension.internal.cache.converter.DimensionDOConverter;
import com.jiuqi.gcreport.dimension.internal.cache.converter.MeasurementDOConverter;
import java.util.HashMap;
import java.util.Map;

public class DimensionConverterFactory {
    private static final Map<Class<?>, DimensionConverter<?>> converters = new HashMap();

    public static <T> DimensionConverter<T> getConverter(Class<T> clazz) {
        return converters.get(clazz);
    }

    static {
        converters.put(DimensionDO.class, new DimensionDOConverter());
        converters.put(MeasurementDO.class, new MeasurementDOConverter());
    }
}

