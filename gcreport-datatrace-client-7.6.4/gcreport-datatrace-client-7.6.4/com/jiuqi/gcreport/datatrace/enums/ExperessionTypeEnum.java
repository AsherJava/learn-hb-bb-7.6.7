/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.datatrace.enums;

public enum ExperessionTypeEnum {
    AUTOCALC("autocalc", "\u81ea\u52a8\u8ba1\u7b97"),
    VABILL("VABILL", "\u53f0\u8d26"),
    NRFIELD("NRFIELD", "\u6307\u6807");

    private String code;
    private String title;

    private ExperessionTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
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

