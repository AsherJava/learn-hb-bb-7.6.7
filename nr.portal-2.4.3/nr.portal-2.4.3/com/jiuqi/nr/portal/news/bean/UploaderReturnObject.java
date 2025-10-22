/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news.bean.UploaderInfoObject;
import java.io.Serializable;

public class UploaderReturnObject
implements Serializable {
    private static final long serialVersionUID = -4203109892187672406L;
    @JsonProperty(value="ErrorMessage")
    private String errorMsg;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="Result")
    private UploaderInfoObject object;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResponse() {
        return this.code;
    }

    public void setResponse(String response) {
        this.code = response;
    }

    public UploaderInfoObject getObject() {
        if (this.object == null) {
            this.object = new UploaderInfoObject();
        }
        return this.object;
    }

    public void setObject(UploaderInfoObject object) {
        this.object = object;
    }
}

