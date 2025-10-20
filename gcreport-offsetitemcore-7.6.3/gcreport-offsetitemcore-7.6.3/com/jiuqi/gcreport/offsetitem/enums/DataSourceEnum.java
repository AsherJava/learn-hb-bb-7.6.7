/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum DataSourceEnum {
    INPUT_DATA("INPUT_DATA", 1, "\u5185\u90e8\u8868"),
    CONNECT_DEAL_DATA("CONNECT_DEAL_DATA", 2, "\u5173\u8054\u4ea4\u6613"),
    COMMON_DATA("COMMON_DATA", 3, "\u901a\u7528");

    private String code;
    private int value;
    private String title;

    private DataSourceEnum(String code, int value, String title) {
        this.code = code;
        this.value = value;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataSourceEnum getDataSourceForCode(String code) {
        for (DataSourceEnum dataSourceEnum : DataSourceEnum.values()) {
            if (!code.equals(dataSourceEnum.getCode())) continue;
            return dataSourceEnum;
        }
        return null;
    }
}

