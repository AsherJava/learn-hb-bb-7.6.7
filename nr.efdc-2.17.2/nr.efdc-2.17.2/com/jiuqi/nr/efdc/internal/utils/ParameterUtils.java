/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.internal.utils;

import org.springframework.util.Assert;

public class ParameterUtils {
    public static void AssertNotNull(String parameterName, Object parameterValue) {
        Assert.notNull(parameterValue, "'" + parameterName + "' must not be null.");
    }
}

