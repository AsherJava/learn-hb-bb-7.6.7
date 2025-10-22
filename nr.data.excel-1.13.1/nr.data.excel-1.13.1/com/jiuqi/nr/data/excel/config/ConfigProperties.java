/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.data.excel")
public class ConfigProperties {
    public static final int DEF_SHEET_SPLIT_MAX_NUM = 11;
    public static final int DEF_SHEET_MAX_FLOAT_ROW_COUNT = 100000;
    public static final int DEF_QUERY_PAGE_LIMIT = 20000;
    public static final int DEF_ROW_ACCESS_WINDOW_SIZE = 2000;
    public static final int DEF_MEM_SIZE_EACH_FIELD = 40;
    public static final int DEF_BATCH_QUERY_MAX_MEM_SIZE = 0x4B00000;
    public static final int DEF_MEM_PERFORMANCE_LEVEL = 1;
    private int expSheetSplitMax = 11;
    private int expSheetFloatMax = 100000;
    private int expQueryPageLimit = 20000;

    public int getExpSheetSplitMax() {
        return this.expSheetSplitMax;
    }

    public void setExpSheetSplitMax(int expSheetSplitMax) {
        this.expSheetSplitMax = expSheetSplitMax;
    }

    public int getExpQueryPageLimit() {
        return this.expQueryPageLimit;
    }

    public void setExpQueryPageLimit(int expQueryPageLimit) {
        this.expQueryPageLimit = expQueryPageLimit;
    }

    public int getExpSheetFloatMax() {
        return this.expSheetFloatMax;
    }

    public void setExpSheetFloatMax(int expSheetFloatMax) {
        this.expSheetFloatMax = expSheetFloatMax;
    }
}

