/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum ApplyScopeEnum {
    FIXED("FIXED", "\u56fa\u5b9a"),
    FLOAT("FLOAT", "\u6d6e\u52a8"),
    ALL("ALL", "\u56fa\u5b9a+\u6d6e\u52a8");

    private String code;
    private String title;

    private ApplyScopeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ApplyScopeEnum getApplyScopeEnumByCode(String code) {
        for (ApplyScopeEnum valEnum : ApplyScopeEnum.values()) {
            if (!valEnum.code.equals(code)) continue;
            return valEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684\u9002\u7528\u8303\u56f4\u679a\u4e3e code=" + code);
    }

    public static ApplyScopeEnum getApplyScopeEnumByTitle(String title) {
        for (ApplyScopeEnum valEnum : ApplyScopeEnum.values()) {
            if (!valEnum.title.equals(title)) continue;
            return valEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684\u9002\u7528\u8303\u56f4\u679a\u4e3e title=" + title);
    }
}

