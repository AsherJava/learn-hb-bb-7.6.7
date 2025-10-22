/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrBillOperateEnum {
    ADD("add", "\u65b0\u589e"),
    DELETE("delete", "\u5220\u9664");

    private String code;
    private String title;

    private ClbrBillOperateEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrBillOperateEnum getEnumByCode(String code) {
        for (ClbrBillOperateEnum operateEnum : ClbrBillOperateEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

