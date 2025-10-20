/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrFlowControlEnum {
    WEAK("WEAK", "\u67d4\u6027"),
    RIGID("RIGID", "\u521a\u6027");

    private String code;
    private String title;

    private ClbrFlowControlEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrFlowControlEnum getEnumByCode(String code) {
        for (ClbrFlowControlEnum operateEnum : ClbrFlowControlEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }

    public static ClbrFlowControlEnum getEnumByTitle(String title) {
        for (ClbrFlowControlEnum operateEnum : ClbrFlowControlEnum.values()) {
            if (!operateEnum.getTitle().equals(title)) continue;
            return operateEnum;
        }
        return null;
    }
}

