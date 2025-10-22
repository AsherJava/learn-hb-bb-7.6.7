/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import java.io.Serializable;

public class ResultColumn
implements Serializable {
    private String code;
    private String title;
    private String schemeTitle;
    private String errorMsg;

    public ResultColumn() {
    }

    public ResultColumn(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public ResultColumn(String code, String title, String schemeTitle, String errorMsg) {
        this.code = code;
        this.title = title;
        this.schemeTitle = schemeTitle;
        this.errorMsg = errorMsg;
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

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }
}

