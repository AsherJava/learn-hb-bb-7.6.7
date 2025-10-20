/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark.inner;

public enum WatermarkEnum {
    TXT(1),
    IMG(2);

    private int mode;

    private WatermarkEnum(int mode) {
        this.mode = mode;
    }

    public int mode() {
        return this.mode;
    }
}

