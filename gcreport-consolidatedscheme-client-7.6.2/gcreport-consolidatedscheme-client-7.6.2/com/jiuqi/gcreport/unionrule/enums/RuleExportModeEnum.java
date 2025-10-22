/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

public enum RuleExportModeEnum {
    ALL("all", "\u6240\u6709\u89c4\u5219"),
    SELECT("select", "\u9009\u62e9\u89c4\u5219"),
    CURRENT("current", "\u5f53\u524d\u89c4\u5219");

    private String code;
    private String title;

    private RuleExportModeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static RuleExportModeEnum fromCode(String code) {
        for (RuleExportModeEnum c : RuleExportModeEnum.values()) {
            if (!c.getCode().equalsIgnoreCase(code)) continue;
            return c;
        }
        return null;
    }
}

