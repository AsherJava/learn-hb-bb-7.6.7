/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formulamapping.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrFormulaMappingErrorEnum implements ErrorEnum
{
    NRFORMULAMAPPING_EXCEPTION_000("000", "\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_001("001", "\u65b0\u589e\u62a5\u8868\u516c\u5f0f\u6620\u5c04\u65b9\u6848\u6570\u636e\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_002("002", "\u4fee\u6539\u62a5\u8868\u516c\u5f0f\u6620\u5c04\u65b9\u6848\u6570\u636e\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_003("003", "\u5220\u9664\u62a5\u8868\u516c\u5f0f\u6620\u5c04\u65b9\u6848\u6570\u636e\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_004("004", "\u62a5\u8868\u516c\u5f0f\u6620\u5c04\u65b9\u6848key\u4e3anull"),
    NRFORMULAMAPPING_EXCEPTION_100("100", "\u5206\u9875\u67e5\u8be2\u6620\u5c04\u516c\u5f0f\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_101("101", "\u67e5\u8be2\u516c\u5f0f\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_102("102", "\u89e3\u6790\u516c\u5f0f\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_103("103", "\u65b0\u589e\u516c\u5f0f\u6620\u5c04\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_104("104", "\u66f4\u65b0\u516c\u5f0f\u6620\u5c04\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_105("105", "\u5220\u9664\u516c\u5f0f\u6620\u5c04\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_106("106", "\u672a\u627e\u5230\u6765\u6e90\u62a5\u8868"),
    NRFORMULAMAPPING_EXCEPTION_107("107", "\u672a\u627e\u5230\u6765\u6e90\u516c\u5f0f"),
    NRFORMULAMAPPING_EXCEPTION_108("108", "\u67e5\u8be2\u6307\u5b9a\u5206\u7ec4\u4e0b\u7684\u62a5\u8868\u5931\u8d25"),
    NRFORMULAMAPPING_EXCEPTION_109("109", "\u5bfc\u5165\u5931\u8d25");

    private String code;
    private String message;

    private NrFormulaMappingErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

