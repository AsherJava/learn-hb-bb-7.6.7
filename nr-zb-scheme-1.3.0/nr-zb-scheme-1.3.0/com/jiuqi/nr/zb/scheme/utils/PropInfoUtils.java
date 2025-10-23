/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.jiuqi.nr.zb.scheme.core.PropInfo;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.util.Assert;

public class PropInfoUtils {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd H:mm:ss");

    public static <T> T parseValue(PropInfo propInfo) {
        Assert.notNull((Object)propInfo, "propInfo can not be null");
        if (propInfo.getValue() == null) {
            return null;
        }
        Object result = null;
        switch (propInfo.getDataType()) {
            case STRING: {
                if (propInfo.isMultiple()) {
                    result = String.join((CharSequence)";", (List)propInfo.getValue());
                    break;
                }
                result = propInfo.getValue();
                break;
            }
            case BOOLEAN: {
                result = Boolean.TRUE.equals(propInfo.getValue()) ? "\u662f" : "\u5426";
                break;
            }
            case DATETIME: {
                ZonedDateTime zonedDateTime = ((Instant)propInfo.getValue()).atZone(ZoneId.systemDefault());
                result = zonedDateTime.format(FORMATTER);
                break;
            }
            case INTEGER: 
            case DOUBLE: 
            case BIG_DECIMAL: 
            case BLOB: 
            case CLOB: {
                result = propInfo.getValue();
                break;
            }
        }
        return (T)result;
    }
}

