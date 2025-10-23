/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.common;

public enum ImportDropDownType {
    DROP_DOWN(0, "\u4e0b\u62c9\u5217\u8868"),
    DATA_VALIDATION(1, "\u6570\u636e\u6709\u6548\u6027");

    private int value;
    private String title;

    private ImportDropDownType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

