/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class DateUtil {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final TimeZone ZONE = TimeZone.getTimeZone(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(8)));

    public static SimpleDateFormat createDateFormat() {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        format.setTimeZone(ZONE);
        return format;
    }
}

