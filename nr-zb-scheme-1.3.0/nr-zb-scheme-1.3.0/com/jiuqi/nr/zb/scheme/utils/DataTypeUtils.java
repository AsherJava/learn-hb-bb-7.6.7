/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class DataTypeUtils {
    private static final Map<PropDataType, Function<PropInfoVO, String>> showTypeMap = new EnumMap<PropDataType, Function<PropInfoVO, String>>(PropDataType.class);

    public static String getShowType(PropInfoVO prop) {
        if (prop == null) {
            return null;
        }
        return showTypeMap.getOrDefault((Object)prop.getDataType(), p -> p.getDataType().getTitle()).apply(prop);
    }

    static {
        showTypeMap.put(PropDataType.DOUBLE, prop -> String.format("%s(%d,%d)", prop.getDataType().getTitle(), prop.getPrecision(), prop.getDecimal()));
        showTypeMap.put(PropDataType.STRING, prop -> String.format("%s(%d)", prop.getDataType().getTitle(), prop.getPrecision()));
        showTypeMap.put(PropDataType.INTEGER, prop -> String.format("%s(%d)", prop.getDataType().getTitle(), prop.getPrecision()));
    }
}

