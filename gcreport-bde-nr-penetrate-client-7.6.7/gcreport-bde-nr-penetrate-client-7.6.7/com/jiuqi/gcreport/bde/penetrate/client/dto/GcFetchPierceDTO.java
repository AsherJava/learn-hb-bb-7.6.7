/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.gcreport.bde.penetrate.client.dto;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.List;
import java.util.Map;

public class GcFetchPierceDTO
implements INRContext {
    private static final long serialVersionUID = 8541845335278993211L;
    private List<String> linkKeys;
    private String formSchemeKey;
    private Map<String, String> dimensionSet;
    private String contextEntityId;
    private String contextFilterExpression;

    public List<String> getLinkKeys() {
        return this.linkKeys;
    }

    public void setLinkKeys(List<String> linkKeys) {
        this.linkKeys = linkKeys;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Map<String, String> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, String> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    public GcFetchPierceDTO() {
    }

    public GcFetchPierceDTO(List<String> linkKeys, String formSchemeKey, Map<String, String> dimensionSet, String contextEntityId, String contextFilterExpression) {
        this.linkKeys = linkKeys;
        this.formSchemeKey = formSchemeKey;
        this.dimensionSet = dimensionSet;
        this.contextEntityId = contextEntityId;
        this.contextFilterExpression = contextFilterExpression;
    }
}

