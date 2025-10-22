/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.util.NumberUtils;

public class OffsetVchrItemNumberUtils {
    public static final int SCALE = 2;

    public static double round(Double value) {
        if (value == null) {
            return 0.0;
        }
        return OffsetVchrItemNumberUtils.round((double)value);
    }

    public static double round(double value) {
        return NumberUtils.round((double)value, (int)2);
    }
}

