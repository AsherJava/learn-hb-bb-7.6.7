/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrBillStateEnum {
    INIT(0, "\u539f\u59cb\u72b6\u6001"),
    PARTCONFIRM(1, "\u90e8\u5206\u534f\u540c"),
    CONFIRM(2, "\u5df2\u534f\u540c"),
    REJECT(3, "\u5df2\u9a73\u56de"),
    DELETE(4, "\u5df2\u5220\u9664"),
    CANCEL(5, "\u5df2\u53d6\u6d88"),
    ARBITRATION(6, "\u4ef2\u88c1\u4e2d");

    private Integer code;
    private String title;

    private ClbrBillStateEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrBillStateEnum getEnumByCode(Integer code) {
        for (ClbrBillStateEnum operateEnum : ClbrBillStateEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

