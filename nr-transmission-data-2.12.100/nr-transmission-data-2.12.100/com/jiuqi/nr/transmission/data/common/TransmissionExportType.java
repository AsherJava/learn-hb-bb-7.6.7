/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.common;

public enum TransmissionExportType {
    FIRST_VERSION(0, "FIRST", "2.12.200\u4e4b\u524d\u7684\u65e7\u7248\u672c\uff0c\u6ca1\u6709\u6620\u5c04\u65b9\u6848"),
    SECOND_VERSION(1, "SECOND", "2.12.200\u53ca\u4e4b\u540e\u7684\u7248\u672c\uff0c\u6709\u6620\u5c04\u65b9\u6848");

    private int value;
    private String code;
    private String title;

    private TransmissionExportType(int value, String code, String title) {
        this.value = value;
        this.code = code;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

