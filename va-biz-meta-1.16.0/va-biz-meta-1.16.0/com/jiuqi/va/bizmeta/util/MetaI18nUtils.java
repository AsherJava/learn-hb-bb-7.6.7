/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.util;

public final class MetaI18nUtils {
    public static String generateBillDefineI18nKey(String moduleName, String uniqueCode) {
        return "VA#bill#" + moduleName + "&module#" + uniqueCode + "&define";
    }
}

