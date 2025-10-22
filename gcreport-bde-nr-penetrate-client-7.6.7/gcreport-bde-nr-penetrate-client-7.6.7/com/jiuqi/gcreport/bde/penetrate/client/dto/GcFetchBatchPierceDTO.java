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

public class GcFetchBatchPierceDTO
implements INRContext {
    private static final long serialVersionUID = -8962948929307006806L;
    private List<String> linkKeys;
    private String formSchemeKey;
    private List<Map<String, String>> dimensionSets;
    private Boolean includeLinkInfo;
    public String contextEntityId;
    public String contextFilterExpression;

    public Boolean getIncludeLinkInfo() {
        return this.includeLinkInfo;
    }

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

    public List<Map<String, String>> getDimensionSets() {
        return this.dimensionSets;
    }

    public void setDimensionSets(List<Map<String, String>> dimensionSets) {
        this.dimensionSets = dimensionSets;
    }

    public void setIncludeLinkInfo(Boolean includeLinkInfo) {
        this.includeLinkInfo = includeLinkInfo;
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

    public String toString() {
        return "GcFetchBatchPierceDTO [linkKeys=" + this.linkKeys + ", formSchemeKey=" + this.formSchemeKey + ", dimensionSets=" + this.dimensionSets + ", includeLinkInfo=" + this.includeLinkInfo + ", contextEntityId=" + this.contextEntityId + ", contextFilterExpression=" + this.contextFilterExpression + "]";
    }
}

