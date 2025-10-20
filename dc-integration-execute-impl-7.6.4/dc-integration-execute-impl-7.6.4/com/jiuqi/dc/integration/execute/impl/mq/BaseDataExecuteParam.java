/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.mq;

import java.io.Serializable;

public class BaseDataExecuteParam
implements Serializable {
    private static final long serialVersionUID = -4658547107184879127L;
    private String dataSchemeCode;
    private String defineCode;
    private String defineName;

    public BaseDataExecuteParam() {
    }

    public BaseDataExecuteParam(String dataSchemeCode, String defineCode, String defineName) {
        this.dataSchemeCode = dataSchemeCode;
        this.defineCode = defineCode;
        this.defineName = defineName;
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

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }
}

