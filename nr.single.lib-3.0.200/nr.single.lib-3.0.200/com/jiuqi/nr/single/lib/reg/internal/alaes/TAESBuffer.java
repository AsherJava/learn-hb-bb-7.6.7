/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.internal.alaes;

public class TAESBuffer {
    private short[] value = new short[16];

    public short[] getValue() {
        return this.value;
    }

    public void setValue(short[] value) {
        this.value = value;
    }
}

