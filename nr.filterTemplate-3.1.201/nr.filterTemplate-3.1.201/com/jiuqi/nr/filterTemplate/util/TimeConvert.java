/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.filterTemplate.util;

import java.sql.Timestamp;
import java.time.Instant;

public class TimeConvert {
    public Instant transTimeStamp(Timestamp time) {
        return time != null ? time.toInstant() : null;
    }

    public Timestamp transTimeStamp(Instant date) {
        return date != null ? Timestamp.from(date) : null;
    }
}

