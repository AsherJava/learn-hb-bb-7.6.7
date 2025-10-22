/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DataSchemeDeployErrorEnum implements ErrorEnum
{
    DATATABLE_DEPLOY_FAIL("000", "\u6570\u636e\u8868\u53d1\u5e03\u5931\u8d25"),
    DATATABLE_DEPLOY_UNSPT_001("001", "\u5f53\u524d\u6570\u636e\u8868\u5b58\u5728\u79fb\u52a8\u6307\u6807\uff0c\u4e0d\u652f\u6301\u5355\u72ec\u53d1\u5e03"),
    DATATABLE_DEPLOY_NONRSCHEME("002", "\u975e\u62a5\u8868\u6570\u636e\u65b9\u6848\uff0c\u4e0d\u652f\u6301\u5355\u8868\u53d1\u5e03"),
    DATATABLE_DEPLOY_PARAERROR("003", "\u53c2\u6570\u5f02\u5e38");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DataSchemeDeployErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

