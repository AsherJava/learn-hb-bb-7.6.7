/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import org.springframework.util.Assert;

public class ParameterUtils {
    public static void AssertNotNull(String parameterName, Object parameterValue) {
        Assert.notNull(parameterValue, "'" + parameterName + "' must not be null.");
    }
}

