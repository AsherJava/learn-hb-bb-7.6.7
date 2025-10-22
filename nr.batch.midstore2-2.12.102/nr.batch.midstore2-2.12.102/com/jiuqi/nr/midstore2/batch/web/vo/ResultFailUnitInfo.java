/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.midstore2.batch.common.ErrorType;
import java.io.Serializable;

public class ResultFailUnitInfo
implements Serializable {
    private String index;
    private String schemeTitle;
    private ErrorType errorType;
    private String formCode;
    private String formTitle;
    private String message;

    public ResultFailUnitInfo() {
    }

    public ResultFailUnitInfo(String schemeTitle, String message) {
        this.schemeTitle = schemeTitle;
        this.message = message;
        this.errorType = ErrorType.UNIT;
    }

    public ResultFailUnitInfo(String schemeTitle, String formCode, String formTitle, String message) {
        this.schemeTitle = schemeTitle;
        this.formCode = formCode;
        this.formTitle = formTitle;
        this.errorType = ErrorType.FORM;
        this.message = message;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

