/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financialcubes.query.factory;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gc.financialcubes.query.enums.PenetrationType;
import com.jiuqi.gc.financialcubes.query.plugin.ParseLayerPenetrationPlugin;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseLayerPenetrationHandlerFactory {
    private final Map<PenetrationType, ParseLayerPenetrationPlugin> handlerMap = new EnumMap<PenetrationType, ParseLayerPenetrationPlugin>(PenetrationType.class);

    @Autowired
    public ParseLayerPenetrationHandlerFactory(List<ParseLayerPenetrationPlugin> handlers) {
        for (ParseLayerPenetrationPlugin handler : handlers) {
            this.handlerMap.put(handler.getType(), handler);
        }
    }

    public ParseLayerPenetrationPlugin getParsePlugin(PenetrationType type) {
        ParseLayerPenetrationPlugin handler = this.handlerMap.get((Object)type);
        if (handler == null) {
            throw new BusinessRuntimeException("\u8be5\u7a7f\u900f\u7c7b\u578b\u6682\u4e0d\u652f\u6301\uff1a " + (Object)((Object)type));
        }
        return handler;
    }
}

