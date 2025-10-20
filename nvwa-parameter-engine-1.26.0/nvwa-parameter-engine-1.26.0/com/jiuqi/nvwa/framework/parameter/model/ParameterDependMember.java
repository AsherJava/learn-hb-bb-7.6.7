/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

import java.io.Serializable;

public class ParameterDependMember
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private String parameterName;
    private String datasourceFieldName;

    public ParameterDependMember(String parameterName, String datasourceFieldName) {
        this.parameterName = parameterName;
        this.datasourceFieldName = datasourceFieldName;
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public String getDatasourceFieldName() {
        return this.datasourceFieldName;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[").append(this.parameterName);
        if (this.datasourceFieldName != null) {
            buf.append(".").append(this.datasourceFieldName);
        }
        buf.append("]");
        return buf.toString();
    }
}

