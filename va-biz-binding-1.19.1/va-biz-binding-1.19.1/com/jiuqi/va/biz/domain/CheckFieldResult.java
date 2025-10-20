/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.biz.intf.CheckFieldValueEnum;

public class CheckFieldResult {
    private String code;
    private CheckFieldValueEnum errorData;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CheckFieldValueEnum getErrorData() {
        return this.errorData;
    }

    public void setErrorData(CheckFieldValueEnum errorData) {
        this.errorData = errorData;
    }

    public CheckFieldResult(String code, CheckFieldValueEnum errorData) {
        this.code = code;
        this.errorData = errorData;
    }

    public CheckFieldResult() {
    }
}

