/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.np.common.exception.ErrorEnum;

public class BusinessError
implements ErrorEnum {
    private String code = "BUSINESS ERROR";
    private String message;

    public BusinessError(RuntimeException e) {
        this.message = e.getMessage();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessError() {
    }

    public BusinessError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

