/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrProcessListTypeEnum {
    INITIATE_PART(0, "\u672c\u65b9\u5355\u4f4d\u5217\u8868"),
    RECEIVE_PART(1, "\u5bf9\u65b9\u5355\u4f4d\u5217\u8868");

    private Integer code;
    private String title;

    private ClbrProcessListTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrProcessListTypeEnum getEnumByCode(Integer code) {
        for (ClbrProcessListTypeEnum operateEnum : ClbrProcessListTypeEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

