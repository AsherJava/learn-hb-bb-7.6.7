/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.carryover.enums;

public enum CarryOverTypeEnum {
    OFFSET("gcOffsetCarryOver", "\u62b5\u9500\u5206\u5f55\u5e74\u7ed3\u65b9\u6848", "gcreport-offset-carryover-plugin"),
    INVEST("gcInvestCarryOver", "\u6295\u8d44\u53f0\u8d26\u5e74\u7ed3\u65b9\u6848", "gcreport-invest-carryover-plugin");

    private String code;
    private String title;
    private String pluginName;

    private CarryOverTypeEnum(String code, String title, String pluginName) {
        this.code = code;
        this.title = title;
        this.pluginName = pluginName;
    }

    public static CarryOverTypeEnum getEnumByCode(String code) {
        for (CarryOverTypeEnum carryOverTypeEnum : CarryOverTypeEnum.values()) {
            if (!carryOverTypeEnum.getCode().equals(code)) continue;
            return carryOverTypeEnum;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPluginName() {
        return this.pluginName;
    }
}

