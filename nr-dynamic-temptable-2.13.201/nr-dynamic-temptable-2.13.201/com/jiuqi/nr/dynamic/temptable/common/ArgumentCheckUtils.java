/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.common;

import com.jiuqi.nr.dynamic.temptable.common.PropertiesUtils;

public class ArgumentCheckUtils {
    private static final int maxColumnCount = PropertiesUtils.getIntPropertyValue("max-column-number");

    public static void checkDynamicTempTableColumnCount(int columnCount, String argumentName) throws IllegalArgumentException {
        if (columnCount < 1) {
            throw new IllegalArgumentException(argumentName + " must be greater than 0");
        }
        if (columnCount > maxColumnCount) {
            throw new IllegalArgumentException(argumentName + " must be less than or equal to " + maxColumnCount);
        }
    }
}

