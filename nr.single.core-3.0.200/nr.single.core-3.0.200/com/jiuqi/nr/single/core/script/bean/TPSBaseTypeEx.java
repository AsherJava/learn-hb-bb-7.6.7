/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.bean;

public class TPSBaseTypeEx {
    private short value;

    public short getValue() {
        return this.value;
    }

    public void setValue(short value) {
        this.value = value < 0 ? (short)(256 + value) : (value > 255 ? (short)(value % 256) : value);
    }
}

