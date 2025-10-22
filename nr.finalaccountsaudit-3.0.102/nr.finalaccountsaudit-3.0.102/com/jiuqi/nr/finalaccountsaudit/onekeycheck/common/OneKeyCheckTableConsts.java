/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.onekeycheck.common;

public class OneKeyCheckTableConsts {
    public static final String OLD_FIELDNAME_ZHSH_KEY = "ZHSH_RECID";
    public static final String FIELDNAME_ZHSH_KEY = "RECID";
    public static final String FIELDNAME_ZHSH_OPERATOR = "OPERATOR";
    public static final String FIELDNAME_ZHSH_UPDATETIME = "UPDATETIME";
    public static final String FIELDNAME_ZHSH_CHECKRESULT = "RESULTSTATUS";
    public static final String FIELDNAME_ZHSH_INFO = "INFO";
    public static final String FIELDNAME_ZHSH_CHECKTYPE = "SHLX";
    public static final String FIELDNAME_ZHSH_CHECKITEMID = "SHLXID";
    public static final String FIELDNAME_ZHSH_CHECKITEMNAME = "SHLXNAME";
    public static final String FIELDNAME_ZHSH_PARM1 = "PARM1";
    public static final String FIELDNAME_ZHSH_PARM2 = "PARM2";
    public static final String FIELDNAME_ZHSH_PARM3 = "PARM3";
    public static final String FIELDNAME_ZHSH_PARM4 = "PARM4";
    public static final String FIELDNAME_ZHSH_PARM5 = "PARM5";
    public static final String FIELDNAME_ZHSH_PARM6 = "PARM6";
    public static final String FIELDNAME_ZHSH_EC1 = "EC1";
    public static final String FIELDNAME_ZHSH_EC2 = "EC2";
    public static final String FIELDNAME_ZHSH_EC3 = "EC3";
    public static final String FIELDNAME_ZHSH_EC4 = "EC4";
    public static final String FIELDNAME_ZHSH_EC5 = "EC5";
    public static final String FIELDNAME_ZHSH_EC6 = "EC6";
    public static final String FIELDNAME_ZHSH_ECD1 = "ECD1";
    public static final String FIELDNAME_ZHSH_ECD2 = "ECD2";
    public static final String FIELDNAME_ZHSH_ECD3 = "ECD3";
    public static final String FIELDNAME_ZHSH_ECD4 = "ECD4";
    public static final String FIELDNAME_ZHSH_ECD5 = "ECD5";
    public static final String FIELDNAME_ZHSH_ECD6 = "ECD6";
    public static final String TABLENAMEPRE = "SYS_SHJL_";

    public static String getTableName(String formSchemeKey) {
        return TABLENAMEPRE + formSchemeKey;
    }
}

