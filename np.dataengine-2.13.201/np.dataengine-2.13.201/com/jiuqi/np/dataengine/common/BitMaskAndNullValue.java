/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.data.AbstractData;

public class BitMaskAndNullValue {
    private final int bitMask;
    private final AbstractData nullValue;

    public BitMaskAndNullValue(int bitMask, AbstractData nullValue) {
        this.bitMask = bitMask;
        this.nullValue = nullValue;
    }

    public int getBitMask() {
        return this.bitMask;
    }

    public AbstractData getNullValue() {
        return this.nullValue;
    }
}

