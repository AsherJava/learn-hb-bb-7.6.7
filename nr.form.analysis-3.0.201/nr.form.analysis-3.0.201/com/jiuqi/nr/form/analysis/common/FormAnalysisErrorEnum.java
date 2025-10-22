/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.form.analysis.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FormAnalysisErrorEnum implements ErrorEnum
{
    EXCEPTION_214("214", "\u5206\u6790\u5f02\u5e38\uff1a\u6765\u6e90\u65f6\u671f\u672a\u8bbe\u7f6e"),
    EXCEPTION_218("218", "\u5206\u6790\u5f02\u5e38\uff1a\u65b9\u6848\u7ea7\u5206\u6790\u53c2\u6570\u4e0d\u5b58\u5728"),
    EXCEPTION_301("301", "JSON\u5e8f\u5217\u5316\u5f02\u5e38"),
    EXCEPTION_302("302", "JSON\u53cd\u5e8f\u5217\u5316\u5f02\u5e38"),
    EXCEPTION_701("701", "\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u5f02\u5e38"),
    EXCEPTION_801("801", "\u6784\u5efa\u5206\u6790\u6a21\u578b\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private FormAnalysisErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

