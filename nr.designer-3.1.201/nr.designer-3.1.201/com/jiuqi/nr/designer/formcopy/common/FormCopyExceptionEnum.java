/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.designer.formcopy.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FormCopyExceptionEnum implements ErrorEnum
{
    FORMCOPY_ERROR_001("001", "\u5220\u9664\u62a5\u8868\u590d\u5236\u6620\u5c04\u5173\u7cfb\u5f02\u5e38"),
    FORMCOPY_ERROR_002("002", "\u5220\u9664\u62a5\u8868\u590d\u5236\u9644\u5c5e\u65b9\u6848\u6620\u5c04\u5173\u7cfb\u5f02\u5e38"),
    FORMCOPY_ERROR_003("003", "\u4fdd\u5b58\u62a5\u8868\u590d\u5236\u540c\u6b65\u8bb0\u5f55\u5f02\u5e38"),
    FORMCOPY_ERROR_004("004", "\u62a5\u8868\u590d\u5236\u6267\u884c\u5f02\u5e38"),
    FORMCOPY_ERROR_005("005", "\u62a5\u8868\u590d\u5236\u7f16\u8bd1\u516c\u5f0f\u6267\u884c\u5f02\u5e38"),
    FORMCOPY_ERROR_006("006", "\u62a5\u8868\u590d\u5236\u83b7\u53d6\u516c\u5f0f\u7f16\u8bd1\u4e0a\u4e0b\u6587\u5f02\u5e38"),
    FORMCOPY_ERROR_007("007", "\u62a5\u8868\u590d\u5236\u6267\u884c\u540c\u6b65\u5f02\u5e38"),
    FORMCOPY_ERROR_008("008", "\u590d\u5236\u591a\u8bed\u8a00\u4fe1\u606f\u5f02\u5e38"),
    FORM_COPY_ERROR_009("009", "\u62a5\u8868\u590d\u5236\u590d\u5236\u516c\u5f0f\u9002\u5e94\u4f53\u6761\u4ef6\u94fe\u63a5\u5f02\u5e38"),
    FORM_COPY_ERROR_010("010", "\u62a5\u8868\u590d\u5236\u590d\u5236\u516c\u5f0f\u9002\u5e94\u4f53\u6761\u4ef6\u5f02\u5e38");

    private String code;
    private String message;

    private FormCopyExceptionEnum(String code, String message) {
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

