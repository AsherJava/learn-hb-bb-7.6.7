/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.form.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrFormCopyErrorEnum implements ErrorEnum
{
    NRDESINGER_EXCEPTION_025("025", "\u76f4\u63a5\u53d1\u5e03\u5e76\u4e14\u8fdb\u5165\u7ef4\u62a4\u72b6\u6001\u5931\u8d25"),
    NRDESINGER_EXCEPTION_030("030", "\u4efb\u52a1\u6b63\u5728\u53d1\u5e03"),
    NRDESINGER_EXCEPTION_190("190", "\u62a5\u8868\u540c\u6b65\u4fe1\u606f\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_191("191", "\u62a5\u8868\u540c\u6b65\u5386\u53f2\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_192("192", "\u62a5\u8868\u6267\u884c\u540c\u6b65\u5f02\u5e38"),
    NRDESINGER_EXCEPTION_193("193", "\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_194("194", "\u6253\u5370\u65b9\u6848\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_195("195", "\u540c\u6b65\u65f6\u672a\u9009\u4e2d\u4efb\u4f55\u6570\u636e"),
    NRDESINGER_EXCEPTION_196("196", "\u8bf7\u9009\u4e2d\u62a5\u8868\u516c\u5f0f\u65b9\u6848\u548c\u6253\u5370\u65b9\u6848\u540e\u9009\u62e9\u5bf9\u5e94\u7684\u8981\u540c\u6b65\u7684\u8868"),
    NRDESINGER_EXCEPTION_197("197", "\u8868\u5355\u5206\u7ec4\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_198("198", "\u62a5\u8868\u590d\u5236\u53c2\u6570\u4e3a\u7a7a\u6216\u4e0d\u6b63\u786e"),
    NRDESINGER_EXCEPTION_199("199", "\u62a5\u8868\u6267\u884c\u590d\u5236\u5f02\u5e38"),
    NRDESINGER_EXCEPTION_200("200", "\u67e5\u8be2\u53c2\u6570\u5b58\u5728\u7a7a"),
    NRDESINGER_EXCEPTION_201("201", "\u62a5\u8868\u4fe1\u606f\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_202("202", "\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u548c\u6253\u5370\u65b9\u6848\u67e5\u8be2\u5931\u8d25"),
    NRDESINGER_EXCEPTION_203("203", "\u62a5\u8868\u590d\u5236\u4fe1\u606f\u6821\u9a8c\u5f02\u5e38"),
    NRDESINGER_EXCEPTION_204("204", "\u6821\u9a8c\u62a5\u8868code\u65f6\u53d1\u751f\u5f02\u5e38");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private NrFormCopyErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

