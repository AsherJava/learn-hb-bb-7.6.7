/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formula.exception;

import com.jiuqi.np.common.exception.ErrorEnum;
import java.util.StringJoiner;

public enum FormulaResourceErrorEnum implements ErrorEnum
{
    FORMULA_RESOURCE_14("FORMULA_RESOURCE_14", "\u67e5\u8be2\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    FORMULA_RESOURCE_13("FORMULA_RESOURCE_13", "\u67e5\u8be2\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b\u5931\u8d25"),
    FORMULA_RESOURCE_01("FormulaResource_01", "\u6784\u9020\u6811\u5f62\u5f02\u5e38"),
    FORMULA_RESOURCE_02("FormulaResource_02", "\u67e5\u8be2\u516c\u5f0f\u6570\u636e\u5f02\u5e38"),
    FORMULA_RESOURCE_03("FormulaResource_03", "\u65e0\u516c\u5f0f\u65b9\u6848\u6743\u9650"),
    FORMULA_RESOURCE_04("FormulaResource_04", "\u65e0\u4efb\u52a1\u6743\u9650"),
    FORMULA_RESOURCE_05("FormulaResource_05", "\u4fdd\u5b58\u516c\u5f0f\u5931\u8d25"),
    FORMULA_RESOURCE_06("FormulaResource_06", "\u53d1\u5e03\u5f53\u524d\u8868\u516c\u5f0f\u5931\u8d25"),
    FORMULA_RESOURCE_07("FormulaResource_07", "\u53d1\u5e03\u516c\u5f0f\u65b9\u6848\u5931\u8d25"),
    FORMULA_RESOURCE_08("FormulaResource_08", "\u67e5\u8be2\u5206\u7ea7\u516c\u5f0f\u5931\u8d25,\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a"),
    FORMULA_RESOURCE_09("FormulaResource_09", "\u79c1\u6709\u516c\u5f0f\u5355\u4f4d\u6811\u5f62\u6784\u5efa\u5931\u8d25"),
    FORMULA_RESOURCE_10("FormulaResource_10", "\u5168\u91cf\u5bfc\u5165\u5931\u8d25"),
    FORMULA_RESOURCE_11("FormulaResource_11", "\u516c\u5f0f\u6821\u9a8c\u5f02\u5e38 "),
    FORMULA_RESOURCE_12("FormulaResource_12", "\u516c\u5f0f\u751f\u6210\u8bf4\u660e\u5f02\u5e38 "),
    FORMULA_RESOURCE_15("FORMULA_RESOURCE_15", "\u5931\u8d25"),
    FORMULA_RESOURCE_16("FORMULA_RESOURCE_16", "\u67e5\u8be2\u5173\u8054\u4efb\u52a1\u5b9e\u4f53\u6570\u636e\u5931\u8d25"),
    FORMULA_RESOURCE_17("FORMULA_RESOURCE_17", "\u83b7\u53d6\u4efb\u52a1\u5217\u8868\u5931\u8d25"),
    FORMULA_RESOURCE_18("FORMULA_RESOURCE_18", "\u8868\u8fbe\u5f0f\u6821\u9a8c\u5931\u8d25");

    private final String code;
    private final String message;

    private FormulaResourceErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return new StringJoiner(", ", FormulaResourceErrorEnum.class.getSimpleName() + "[", "]").add("code='" + this.code + "'").add("message='" + this.message + "'").toString();
    }
}

