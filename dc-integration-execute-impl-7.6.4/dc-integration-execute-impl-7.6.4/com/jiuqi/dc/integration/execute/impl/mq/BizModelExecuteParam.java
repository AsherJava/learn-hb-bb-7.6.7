/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.mq;

import java.io.Serializable;

public class BizModelExecuteParam
implements Serializable {
    private static final long serialVersionUID = -8692928400899474488L;
    private String executeParam;
    private String convertParam;

    public BizModelExecuteParam() {
    }

    public BizModelExecuteParam(String executeParam, String convertParam) {
        this.executeParam = executeParam;
        this.convertParam = convertParam;
    }

    public String getExecuteParam() {
        return this.executeParam;
    }

    public void setExecuteParam(String executeParam) {
        this.executeParam = executeParam;
    }

    public String getConvertParam() {
        return this.convertParam;
    }

    public void setConvertParam(String convertParam) {
        this.convertParam = convertParam;
    }
}

