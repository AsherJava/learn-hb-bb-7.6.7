/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.midstore2.batch.common.ErrorType;
import java.io.Serializable;

public class ResultFailUnitVO
implements Serializable {
    private String index;
    private String code;
    private String title;
    private ErrorType errorType;
    private String schemeTitle;
    private String formCode;
    private String formTitle;
    private String message;
    private int unitErrorCount = 0;

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
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

    public int getUnitErrorCount() {
        return this.unitErrorCount;
    }

    public void setUnitErrorCount(int unitErrorCount) {
        this.unitErrorCount = unitErrorCount;
    }
}

