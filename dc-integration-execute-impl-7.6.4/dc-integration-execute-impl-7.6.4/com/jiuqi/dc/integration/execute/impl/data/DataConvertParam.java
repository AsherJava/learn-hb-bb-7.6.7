/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.data;

import java.io.Serializable;

public class DataConvertParam
implements Serializable {
    private static final long serialVersionUID = 4176111197598871195L;
    private String parameterJson;
    private String dimCode;

    public DataConvertParam() {
    }

    public DataConvertParam(String parameterJson, String dimCode) {
        this.parameterJson = parameterJson;
        this.dimCode = dimCode;
    }

    public String getParameterJson() {
        return this.parameterJson;
    }

    public void setParameterJson(String parameterJson) {
        this.parameterJson = parameterJson;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }
}

