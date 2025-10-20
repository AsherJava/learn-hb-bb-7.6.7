/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf;

import java.io.Serializable;

public class ServiceUrl
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String frontendURL;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrontendURL() {
        return this.frontendURL;
    }

    public void setFrontendURL(String frontendURL) {
        this.frontendURL = frontendURL;
    }
}

