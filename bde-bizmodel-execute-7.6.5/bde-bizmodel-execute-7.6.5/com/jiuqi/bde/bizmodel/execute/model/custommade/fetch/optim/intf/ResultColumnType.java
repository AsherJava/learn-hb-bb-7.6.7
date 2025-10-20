/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

public enum ResultColumnType {
    STRING("STRING", "\u5b57\u7b26"),
    NUMBER("NUM", "\u6570\u503c"),
    INT("INTEGER", "\u6570\u503c\uff08\u6574\u6570\uff09");

    private String code;
    private String name;

    private ResultColumnType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ResultColumnType getEnumByName(String code) {
        for (ResultColumnType columnEnum : ResultColumnType.values()) {
            if (!columnEnum.getCode().equalsIgnoreCase(code)) continue;
            return columnEnum;
        }
        return null;
    }
}

