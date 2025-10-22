/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.efdc.param;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.Map;

public class EfdcNewRequestInfo
implements INRContext {
    private String linkKey;
    private String formSchemeKey;
    private Map<String, String> dimensionSet;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public Map<String, String> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setDimensionSet(Map<String, String> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}

