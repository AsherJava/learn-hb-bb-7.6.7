/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.common;

@Deprecated
public enum FinancialCubesSrcTypeEnum {
    SINGLE("1", "\u5355\u6237"),
    ACCOUNTING("11", "\u8d22\u52a1"),
    ACCOUNTING_DATA("111", "\u6838\u7b97\u6570\u636e"),
    UNCLEARED("112", "\u672a\u6e05\u8d26"),
    DECREASING_GROUP("113", "\u62b5\u51cf\u8d26\u9f84"),
    TARGETED_YEAR("114", "\u6307\u5b9a\u4f59\u989d"),
    FIFO("115", "\u5148\u8fdb\u5148\u51fa"),
    ADJUST_VCHR("12", "\u8c03\u6574\u51ed\u8bc1"),
    ADJUST_VCHR_MANUAL("121", "\u624b\u5de5\u8c03\u6574"),
    BALANCE_RECLASSIFY("122", "\u4f59\u989d\u91cd\u5206\u7c7b"),
    EXPIRE_DATE_RECLASSIFY("123", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b"),
    DEDUCTION_RECLASSIFY("124", "\u62b5\u51cf\u91cd\u5206\u7c7b"),
    OFFSET("2", "\u62b5\u6d88\u5206\u5f55\u7c7b"),
    OFF_DEALINGS("201", "\u5f80\u6765\u7c7b"),
    OFF_TRADE("202", "\u4ea4\u6613\u7c7b"),
    OFF_CF("203", "\u73b0\u91d1\u6d41\u91cf"),
    OFF_INDIRECT_INVESTMENTS("204", "\u95f4\u63a5\u6295\u8d44"),
    OFF_DIRECT_INVESTMENTS("205", "\u76f4\u63a5\u6295\u8d44"),
    OFF_FAIR_VALUE("206", "\u516c\u5141\u4ef7\u503c\u8c03\u6574"),
    OFF_FIXED_ASSET("207", "\u56fa\u5b9a\u8d44\u4ea7"),
    OFF_INTANGIBLE_ASSET("208", "\u65e0\u5f62\u8d44\u4ea7"),
    OFF_BALANCE_RECLASSIFY("29801", "\u4f59\u989d\u91cd\u5206\u7c7b"),
    OFF_DEDUCTION_RECLASSIFY("29802", "\u62b5\u51cf\u91cd\u5206\u7c7b"),
    OFF_RESTORATION("29803", "\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f"),
    OFF_DEFERRED_INCOME_TAX("29804", "\u9012\u5ef6\u6240\u5f97\u7a0e"),
    OFF_LOSS("29805", "\u7ed3\u8f6c\u635f\u76ca");

    private String code;
    private String name;

    private FinancialCubesSrcTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FinancialCubesSrcTypeEnum getEnumByCode(String code) {
        for (FinancialCubesSrcTypeEnum auditTrailEnum : FinancialCubesSrcTypeEnum.values()) {
            if (!auditTrailEnum.getCode().equals(code)) continue;
            return auditTrailEnum;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

