/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import java.util.List;

public class CopyAttributes {
    private DesignDataScheme scheme;
    private String newKey;
    private DesignDataScheme newScheme;
    private List<DesignDataDimension> dimensions;

    public String getNewKey() {
        return this.newKey;
    }

    public void setNewKey(String newKey) {
        this.newKey = newKey;
    }

    public DesignDataScheme getNewScheme() {
        return this.newScheme;
    }

    public void setNewScheme(DesignDataScheme newScheme) {
        this.newScheme = newScheme;
    }

    public List<DesignDataDimension> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<DesignDataDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public CopyAttributes() {
    }

    public CopyAttributes(String newKey) {
        this.newKey = newKey;
    }

    public CopyAttributes(String newKey, CopyAttributes copyAttributes) {
        this.newKey = newKey;
        this.scheme = copyAttributes.scheme;
        this.dimensions = copyAttributes.dimensions;
        this.newScheme = copyAttributes.getNewScheme();
    }

    public DesignDataScheme getScheme() {
        return this.scheme;
    }

    public void setScheme(DesignDataScheme scheme) {
        this.scheme = scheme;
    }

    public CopyAttributes(String newKey, DesignDataScheme scheme, DesignDataScheme newScheme, List<DesignDataDimension> dimensions) {
        this.scheme = scheme;
        this.newKey = newKey;
        this.newScheme = newScheme;
        this.dimensions = dimensions;
    }
}

