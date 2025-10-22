/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.internal.alaes;

public class TAESKey128 {
    private short[] value = new short[16];

    public short[] getValue() {
        return this.value;
    }

    public void setValue(short[] value) {
        this.value = value;
    }

    public void setAllValue(short num) {
        for (int i = 0; i < this.value.length; ++i) {
            this.value[i] = num;
        }
    }
}

