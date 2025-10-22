/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.enums;

public enum YongYouReadyStatus {
    PREPARING(0),
    COMPLETED(1);

    private final int value;

    private YongYouReadyStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

