/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.mapping2.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum MappingErrorEnum implements ErrorEnum
{
    MAPPING_000("000", "\u65e0\u6743\u9650\u64cd\u4f5c\uff01"),
    MAPPING_001("001", "\u62a5\u8868\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848\u53c2\u6570\u5f02\u5e38\uff0c\u8bf7\u5230\u4efb\u52a1\u7ba1\u7406\u68c0\u67e5\uff01"),
    MAPPING_002("002", "\u5f53\u524d\u4efb\u52a1\u672a\u53d1\u5e03\uff01"),
    MAPPING_003("003", "\u672a\u627e\u5230\u4efb\u52a1\uff01"),
    MAPPING_0022("0022", "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u672a\u53d1\u5e03\uff01"),
    MAPPING_0033("0033", "\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff01"),
    MAPPING_004("004", "\u672a\u627e\u5230\u516c\u5f0f\u65b9\u6848\uff01"),
    MAPPING_101("101", "\u5e8f\u5217\u5316\u5931\u8d25\uff01"),
    MAPPING_102("102", "\u53cd\u5e8f\u5217\u5316\u5931\u8d25\uff01"),
    MAPPING_PERIOD_001("P001", "\u65f6\u671f"),
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

