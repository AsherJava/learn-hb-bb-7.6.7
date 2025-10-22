/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;

public interface StatUnit {
    public static final int STAT_KIND_NONE = 0;
    public static final int STAT_KIND_SUM = 1;
    public static final int STAT_KIND_COUNT = 2;
    public static final int STAT_KIND_AVG = 3;
    public static final int STAT_KIND_MAX = 4;
    public static final int STAT_KIND_MIN = 5;
    public static final int STAT_KIND_FIRST = 6;
    public static final int STAT_KIND_LAST = 7;
    public static final int STAT_KIND_LJ = 8;
    public static final int STAT_KIND_DISTINCTCOUNT = 9;
    public static final int STAT_KIND_MEDIAN = 10;
    public static final int STAT_KIND_CONSTANT = 11;

    public int getStatKind();

    public int getResultType();

    public AbstractData getResult();

    public void reset();

    public void statistic(AbstractData var1);
}

