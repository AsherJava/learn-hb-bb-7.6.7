/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.dataservice.core.dimension.serial;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SerialzeUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SerialzeUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    static {
        objectMapper.setDateFormat((DateFormat)new SimpleDateFormat("yyyy-MM-dd"));
    }
}

