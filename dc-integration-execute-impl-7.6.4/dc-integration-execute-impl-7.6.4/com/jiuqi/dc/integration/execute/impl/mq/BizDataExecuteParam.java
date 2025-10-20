/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.mq;

import java.io.Serializable;

public class BizDataExecuteParam
implements Serializable {
    private static final long serialVersionUID = -3151976894553374256L;
    private String logId;
    private String dataSchemeCode;
    private String defineCode;
    private String templateParam;

    public BizDataExecuteParam() {
    }

    public BizDataExecuteParam(String logId, String dataSchemeCode, String defineCode, String templateParam) {
        this.logId = logId;
        this.dataSchemeCode = dataSchemeCode;
        this.defineCode = defineCode;
        this.templateParam = templateParam;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getTemplateParam() {
        return this.templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}

