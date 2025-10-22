/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums;

public enum FloatSettingTypeEnum {
    CUSTOM_TEXT("\u81ea\u5b9a\u4e49\u6587\u672c"),
    RESULT_COLUMN("\u7ed3\u679c\u5217"),
    CUSTOM_RULE("\u81ea\u5b9a\u4e49\u89c4\u5219");

    private final String name;

    private FloatSettingTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static FloatSettingTypeEnum getEnumByName(String name) {
        for (FloatSettingTypeEnum floatSettingTypeEnum : FloatSettingTypeEnum.values()) {
            if (!floatSettingTypeEnum.getName().equals(name)) continue;
            return floatSettingTypeEnum;
        }
        return null;
    }
}

