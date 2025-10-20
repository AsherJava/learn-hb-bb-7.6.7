/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.common;

public enum FinancialCubesRebuildScopeEnum {
    FINCUBES_DIM("FINCUBES_DIM", "\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f"),
    FINCUBES_CF("FINCUBES_CF", "\u73b0\u6d41\u5408\u5e76\u5e95\u7a3f"),
    FINCUBES_AGING("FINCUBES_AGING", "\u8d26\u9f84\u5408\u5e76\u5e95\u7a3f"),
    FINCUBES_RELATED_ITEM("FINCUBES_RELATED_ITEM", "\u5bf9\u8d26\u6570\u636e");

    private String code;
    private String title;

    private FinancialCubesRebuildScopeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static FinancialCubesRebuildScopeEnum getEnumByCode(String code) {
        for (FinancialCubesRebuildScopeEnum scopeEnum : FinancialCubesRebuildScopeEnum.values()) {
            if (!scopeEnum.getCode().equals(code)) continue;
            return scopeEnum;
        }
        return null;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }
}

