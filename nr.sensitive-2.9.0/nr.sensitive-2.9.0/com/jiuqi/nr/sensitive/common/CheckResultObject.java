/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.common;

import java.io.Serializable;

public class CheckResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer errorNum;
    private String errorInfo;
    private Boolean maxLength;

    public CheckResultObject() {
    }

    public CheckResultObject(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Integer getErrorNum() {
        return this.errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Boolean getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(Boolean maxLength) {
        this.maxLength = maxLength;
    }
}

