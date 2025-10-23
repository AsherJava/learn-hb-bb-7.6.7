/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.DataScheme;
import java.io.Serializable;

public class DataSchemeDeploySource
implements Serializable {
    private static final long serialVersionUID = 1633197641987247685L;
    private final DataScheme dataScheme;

    public DataSchemeDeploySource(DataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getDataSchemeKey() {
        return this.dataScheme.getKey();
    }

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public String toString() {
        return this.dataScheme.toString();
    }
}

