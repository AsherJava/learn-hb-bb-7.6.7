/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrBillConfirmTypeEnum {
    AUTO(1, "\u81ea\u52a8\u534f\u540c"),
    MANUAL(2, "\u624b\u52a8\u534f\u540c");

    private Integer code;
    private String title;

    private ClbrBillConfirmTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrBillConfirmTypeEnum getEnumByCode(Integer code) {
        for (ClbrBillConfirmTypeEnum operateEnum : ClbrBillConfirmTypeEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

