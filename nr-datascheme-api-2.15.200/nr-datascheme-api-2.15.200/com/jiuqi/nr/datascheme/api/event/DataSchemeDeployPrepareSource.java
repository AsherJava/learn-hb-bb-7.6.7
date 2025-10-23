/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.DataScheme;
import java.io.Serializable;

public class DataSchemeDeployPrepareSource
implements Serializable {
    private static final long serialVersionUID = -2306369304539770752L;
    private final DataScheme dataScheme;

    public DataSchemeDeployPrepareSource(DataScheme dataScheme) {
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

