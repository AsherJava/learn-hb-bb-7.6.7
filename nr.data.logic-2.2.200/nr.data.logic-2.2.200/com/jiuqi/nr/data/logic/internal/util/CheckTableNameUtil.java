/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.util;

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

    public static String getCKSTableName(String formSchemeCode) {
        return "SYS_CKS_" + formSchemeCode;
    }

    public static String getCKSSubTableName(String formSchemeCode) {
        return "SYS_CKS_S_" + formSchemeCode;
    }

    @Deprecated
    public static String getRWIFTableName(String formSchemeCode) {
        return "DE_RWIF_" + formSchemeCode;
    }

    public static String getRWIFTableName() {
        return "NR_PARAM_REVIEW_INFO";
    }
}

