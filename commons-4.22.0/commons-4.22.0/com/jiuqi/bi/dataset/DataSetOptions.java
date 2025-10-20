/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.util.StringUtils;

public final class DataSetOptions {
    static int MAX_DATA_SIZE = DataSetOptions.getMaxDataSize();

    private static int getMaxDataSize() {
        String maxSize = System.getProperty("bi.maxDataSize");
        if (!StringUtils.isEmpty((String)maxSize)) {
            try {
                return Integer.parseInt(maxSize);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 1000000;
    }

    public static void setMaxDataSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("\u6570\u636e\u96c6\u6700\u5927\u8bb0\u5f55\u6570\u5fc5\u987b\u5927\u4e8e0");
        }
        MAX_DATA_SIZE = size;
    }
}

