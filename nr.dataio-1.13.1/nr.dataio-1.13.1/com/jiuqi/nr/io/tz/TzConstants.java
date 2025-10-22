/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.io.tz;

import com.jiuqi.bi.util.OrderGenerator;
import java.security.SecureRandom;

public class TzConstants {
    public static final String P_IMPORT_TYPE = "P_IMPORT_TYPE";
    public static final String P_DATATIME = "P_DATATIME";
    public static final String P_SOURCE_TYPE = "P_SOURCE_TYPE";
    public static final String P_SOURCE_DATA = "P_SOURCE_DATA";
    public static final String P_DEST_FORM = "P_DEST_TABLE";
    public static final String P_FULL_OR_ADD = "P_FULL_OR_ADD";
    public static final String FULL = "F";
    public static final String ADD = "A";
    public static final String TMP_TABLE_OPT = "OPT";
    public static final String TMP_TABLE_RPT_OPT = "RPT_OPT";
    public static final String TMP_TABLE_ORDINAL = "ORDINAL";
    public static final String TMP_TABLE_MD_ORDINAL = "MD_ORDINAL";
    public static final String TMP_TABLE_ID = "ID";
    public static final String P_DEST_MD_AREA_ROOT = "P_DEST_MD_AREA_ROOT";
    public static final byte DEFAULT_FLAG = 0;
    public static final byte DEL_FLAG = -1;
    public static final byte UPDATE_FLAG = 2;
    public static final byte NO_RECORD_UPDATE_FLAG = 3;
    public static final byte ADD_FLAG = 4;
    public static final String SBID_DV = "NULL";
    public static final String PERIOD_LOG_1 = "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868\u4e2d\u751f\u6548\u65f6\u95f4\u65f6\u671f\u503c\u4e0d\u6b63\u786e";
    public static final String PERIOD_LOG_2 = "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u65f6\u671f\u53c2\u6570\u4e0d\u6b63\u786e";
    public static final String PERIOD_LOG_3 = "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e0d\u80fd\u5bfc\u5165\u5386\u53f2\u671f\u6570\u636e";
    public static final String FIELD_PRE = "_";

    public static String createName() {
        SecureRandom rand = new SecureRandom();
        int id = rand.nextInt(10000);
        return "TMP_" + OrderGenerator.newOrder() + FIELD_PRE + id;
    }
}

