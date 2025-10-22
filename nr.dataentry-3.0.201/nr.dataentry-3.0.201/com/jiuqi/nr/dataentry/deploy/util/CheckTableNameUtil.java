/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.deploy.util;

@Deprecated
public class CheckTableNameUtil {
    public static String getCKRTableName(String formSchemeCode) {
        return "SYS_CKR_" + formSchemeCode;
    }

    public static String getAllCKRTableName(String formSchemeCode) {
        return "SYS_ALLCKR_" + formSchemeCode;
    }

    public static String getCKDTableName(String formSchemeCode) {
        return "SYS_CKD_" + formSchemeCode;
    }

    public static String getCKITableName(String formSchemeCode) {
        return "SYS_CKI_" + formSchemeCode;
    }
}

