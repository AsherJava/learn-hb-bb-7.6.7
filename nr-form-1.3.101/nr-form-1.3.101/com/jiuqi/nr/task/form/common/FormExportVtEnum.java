/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.common;

public enum FormExportVtEnum {
    DEFAULT(1, "\u9ed8\u8ba4\u89c6\u56fe"),
    TITLE(2, "\u6307\u6807\u6807\u9898"),
    FIELD_TYPE(3, "\u6307\u6807\u7c7b\u578b"),
    GATHER_TYPE(4, "\u6c47\u603b\u7c7b\u578b"),
    TABLE(8, "\u6307\u6807\u6807\u8bc6"),
    DATA_LINK_POS(10, "\u5355\u5143\u683c\u7f16\u53f7");

    private int value;
    private String title;

    private FormExportVtEnum(int value, String title) {
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

