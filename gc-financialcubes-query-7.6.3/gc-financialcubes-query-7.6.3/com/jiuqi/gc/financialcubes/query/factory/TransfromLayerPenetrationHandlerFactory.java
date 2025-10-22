/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financialcubes.query.factory;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;
import com.jiuqi.gc.financialcubes.query.plugin.TransformLayerPenetrationPlugin;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransfromLayerPenetrationHandlerFactory {
    private final Map<UnitType, TransformLayerPenetrationPlugin> converterMap = new EnumMap<UnitType, TransformLayerPenetrationPlugin>(UnitType.class);

    @Autowired
    public TransfromLayerPenetrationHandlerFactory(List<TransformLayerPenetrationPlugin> converters) {
        for (TransformLayerPenetrationPlugin converter : converters) {
            this.converterMap.put(converter.getType(), converter);
        }
    }

    public TransformLayerPenetrationPlugin getConvertPlugin(UnitType type) {
        TransformLayerPenetrationPlugin converter = this.converterMap.get((Object)type);
        if (converter == null) {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u6b64\u5355\u4f4d\u7c7b\u578b\u7684\u7a7f\u900f: " + (Object)((Object)type));
        }
        return converter;
    }
}

