/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.enums;

public enum GcBillStatusEnum {
    UNHANDLED("01", "\u5f85\u5904\u7406"),
    HANDLED("02", "\u5df2\u5904\u7406"),
    DEPRECATED("03", "\u5df2\u5e9f\u5f03");

    private String code;
    private String title;

    private GcBillStatusEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getEnumTitleByValue(String code) {
        for (GcBillStatusEnum lockStatusEnum : GcBillStatusEnum.values()) {
            if (!lockStatusEnum.getCode().equals(code)) continue;
            return lockStatusEnum.getTitle();
        }
        return null;
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

