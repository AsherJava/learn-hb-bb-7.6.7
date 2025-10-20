/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.gcreport.oauth2.pojo;

import com.jiuqi.gcreport.oauth2.pojo.CustomField;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomParam {
    private String method;
    private List<CustomField> headers;
    private List<CustomField> urlParameters;
    private List<CustomField> formBody;
    private List<CustomField> jsonBody;
    private boolean reqAcessTokenUseBasicAuth = false;
    private boolean reqUserInfoUseBearerAuth = false;

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<CustomField> getHeaders() {
        return this.headers;
    }

    public void setHeaders(List<CustomField> headers) {
        this.headers = headers;
    }

    public List<CustomField> getUrlParameters() {
        return this.urlParameters;
    }

    public void setUrlParameters(List<CustomField> urlParameters) {
        this.urlParameters = urlParameters;
    }

    public boolean getReqAcessTokenUseBasicAuth() {
        return this.reqAcessTokenUseBasicAuth;
    }

    public void setReqAcessTokenUseBasicAuth(boolean reqAcessTokenUseBasicAuth) {
        this.reqAcessTokenUseBasicAuth = reqAcessTokenUseBasicAuth;
    }

    public List<CustomField> getFormBody() {
        return this.formBody;
    }

    public void setFormBody(List<CustomField> formBody) {
        this.formBody = formBody;
    }

    public List<CustomField> getJsonBody() {
        return this.jsonBody;
    }

    public void setJsonBody(List<CustomField> jsonBody) {
        this.jsonBody = jsonBody;
    }

    public boolean getReqUserInfoUseBearerAuth() {
        return this.reqUserInfoUseBearerAuth;
    }

    public void setReqUserInfoUseBearerAuth(boolean reqUserInfoUseBearerAuth) {
        this.reqUserInfoUseBearerAuth = reqUserInfoUseBearerAuth;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

