/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.constant;

public class StandardizationLayerPenetrationConstants {
    public static final String SUBJECT_CODE = "SUBJECTCODE";
    public static final String UNIT_CODE = "UNITCODE";
    public static final String OPP_UNITCODE = "OPPUNITCODE";
    public static final String PRIMARY_DIMENSION_FIELD = "MD_ORG.CODE";
    public static final String PRIMARY_DIMENSION_UNDERSCORE = "MD_ORG_";
    public static final String TIMEKEY = "P_TIMEKEY";
    public static final String[] CURRENCY_START_KEYS = new String[]{"ORGNCURRENCY_MD_CURRENCY", "MD_CURRENCY"};
    public static final String CURRENCY_END_KEY = "ORGNCURRENCY";
    public static final String MD_PREFIX = "MD_";
    public static final String OBJECTCODE = "OBJECTCODE";
    public static final String MD_ACCTSUBJECT = "MD_ACCTSUBJECT";
    public static final String ASSOCIATE_MDORG = "_MD_ORG.CODE";

    private StandardizationLayerPenetrationConstants() {
        throw new AssertionError((Object)"\u6b64\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316\u3002");
    }
}

