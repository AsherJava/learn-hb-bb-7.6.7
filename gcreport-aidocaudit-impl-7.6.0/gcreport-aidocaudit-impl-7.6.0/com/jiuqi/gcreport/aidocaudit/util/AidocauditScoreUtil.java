/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.util;

import org.springframework.util.StringUtils;

public class AidocauditScoreUtil {
    private AidocauditScoreUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static Boolean isSuspectMatch(String scoreBasis) {
        if (StringUtils.hasText(scoreBasis)) {
            return scoreBasis.contains("\u53ca\u683c\u5206");
        }
        return false;
    }
}

