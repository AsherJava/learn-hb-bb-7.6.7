/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.enums;

public enum PublishStateEnum {
    PUBLISHED(4),
    SAVED(3),
    UNSAVED(2),
    EMPTY(1);

    private int code;

    private PublishStateEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

