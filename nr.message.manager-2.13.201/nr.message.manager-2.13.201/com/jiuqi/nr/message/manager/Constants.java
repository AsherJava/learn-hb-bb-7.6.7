/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Deprecated
public class Constants {
    public static final String ALL_PARTICIPANT_ID = "FFFFFFFF-FFFF-FFFF-MMMM-FFFFFFFFFFFF";
    public static final Timestamp DEFAULT_INVALID_TIME = Timestamp.valueOf("2037-12-30 23:59:59");
    public static final String TABLE_STATUS = "msg_status";
    public static final String COL_MSG_ID = "msgid";
    public static final String COL_USER_ID = "userid";
    public static final String COL_MSG_STATUS = "status";
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.systemDefault());
}

