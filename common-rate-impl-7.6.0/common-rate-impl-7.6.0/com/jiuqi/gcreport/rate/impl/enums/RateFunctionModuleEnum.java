/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.enums;

public enum RateFunctionModuleEnum {
    RATESCHEMESETTINGS("\u6c47\u7387\u65b9\u6848\u8bbe\u7f6e");

    private String name;

    private RateFunctionModuleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getFullModuleName() {
        return "\u516c\u5171-" + this.name;
    }
}

