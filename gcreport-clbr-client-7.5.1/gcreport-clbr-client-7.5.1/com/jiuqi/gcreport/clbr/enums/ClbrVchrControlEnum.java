/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrVchrControlEnum {
    WEAK("WEAK", "\u67d4\u6027"),
    RIGID("RIGID", "\u521a\u6027");

    private String code;
    private String title;

    private ClbrVchrControlEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrVchrControlEnum getEnumByCode(String code) {
        for (ClbrVchrControlEnum operateEnum : ClbrVchrControlEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }

    public static ClbrVchrControlEnum getEnumByTitle(String title) {
        for (ClbrVchrControlEnum operateEnum : ClbrVchrControlEnum.values()) {
            if (!operateEnum.getTitle().equals(title)) continue;
            return operateEnum;
        }
        return null;
    }
}

