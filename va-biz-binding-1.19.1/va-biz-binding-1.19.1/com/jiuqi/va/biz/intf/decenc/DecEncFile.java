/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.decenc;

public interface DecEncFile {
    public String getName();

    public String getTitle();

    default public byte[] decrypt(byte[] data) {
        return data;
    }

    default public byte[] encrypt(byte[] data) {
        return data;
    }
}

