/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query;

public enum QueryOpenMode {
    NORMAL_OPEN,
    OPEN_UPDATE_ONLY,
    OPEN_UPDATE_AND_TRUNC;


    public int getValue() {
        return this.ordinal();
    }

    public static QueryOpenMode forValue(int value) {
        return QueryOpenMode.values()[value];
    }
}

