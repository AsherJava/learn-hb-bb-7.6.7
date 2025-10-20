/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.io.Serializable;

public class ReturnObject
implements Serializable {
    private static final long serialVersionUID = -7363631834480310744L;
    private boolean hasError = false;
    private int errorCode = 0;
    private String errorMessage = "";

    public ReturnObject() {
    }

    public ReturnObject(Exception ex) {
        this.errorMessage = ex.getMessage();
        this.hasError = true;
        ex.printStackTrace();
    }

    public Boolean getHasError() {
        return new Boolean(this.hasError);
    }

    public Integer getErrorCode() {
        return new Integer(this.errorCode);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean errored() {
        return this.hasError;
    }

    public int errorcode() {
        return this.errorCode;
    }

    public String errorMsg() {
        return this.errorMessage;
    }

    public void setHasError(Boolean newHasError) {
        this.hasError = newHasError;
    }

    public void setErrorCode(Integer newErrorCode) {
        this.errorCode = newErrorCode;
    }

    public void setErrorMessage(String newErrorMessage) {
        this.errorMessage = newErrorMessage;
    }

    public void SetErrored(boolean value) {
        this.hasError = value;
    }

    public void SetErrorCode(int value) {
        this.errorCode = value;
    }

    public static void main(String[] args) {
    }
}

