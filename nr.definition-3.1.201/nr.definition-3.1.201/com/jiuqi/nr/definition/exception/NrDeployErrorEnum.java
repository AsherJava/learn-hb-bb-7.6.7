/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.definition.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrDeployErrorEnum implements ErrorEnum
{
    NRDEPLOYERRORENUM_PARAM("101", "\u53d1\u5e03\u62a5\u8868\u53c2\u6570\u5f02\u5e38"),
    NRDEPLOY_DATASCHEME_ERROR_PARAM("102", "\u53d1\u5e03\u6570\u636e\u65b9\u6848\u5f02\u5e38");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private NrDeployErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

