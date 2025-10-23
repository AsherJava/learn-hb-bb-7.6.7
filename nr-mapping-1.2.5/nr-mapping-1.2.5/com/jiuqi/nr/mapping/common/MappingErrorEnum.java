/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.mapping.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum MappingErrorEnum implements ErrorEnum
{
    MAPPING_000("000", "\u65e0\u6743\u9650\u64cd\u4f5c\uff01"),
    MAPPING_001("001", "\u5e8f\u5217\u5316\u5931\u8d25\uff01"),
    MAPPING_002("002", "\u53cd\u5e8f\u5217\u5316\u5931\u8d25\uff01"),
    SCHEME_003("003", "\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_004("004", "\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_005("005", "\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_006("006", "\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    SCHEME_007("007", "\u540c\u4e00\u62a5\u8868\u65b9\u6848\u4e0b\u6807\u9898\u4e0d\u80fd\u91cd\u590d\uff01"),
    SCHEME_008("008", "\u6807\u8bc6\u4e0d\u80fd\u91cd\u590d\uff01"),
    MAPPING_PERIOD_001("P001", "\u65f6\u671f"),
    MAPPING_UNIT_001("U001", "\u5355\u4f4d"),
    MAPPING_FORMULA_001("F001", "\u516c\u5f0f");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private MappingErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

