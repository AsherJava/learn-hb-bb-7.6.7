/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.utils;

import org.springframework.util.ObjectUtils;

public class FcUtils {
    public static boolean fieldValueIsEmpty(String str) {
        return ObjectUtils.isEmpty(str) || "#".equals(str);
    }
}

