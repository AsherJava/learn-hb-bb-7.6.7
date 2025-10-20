/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrBillTypeEnum {
    INITIATOR(0, "\u53d1\u8d77\u65b9"),
    RECEIVER(1, "\u63a5\u6536\u65b9");

    private Integer code;
    private String title;

    private ClbrBillTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrBillTypeEnum getEnumByCode(Integer code) {
        for (ClbrBillTypeEnum operateEnum : ClbrBillTypeEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

