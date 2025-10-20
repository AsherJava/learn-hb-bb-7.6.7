/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.Map;

public class AdjustPeriodFetchDTO
implements INRContext {
    private static final long serialVersionUID = 7347329354816497321L;
    private String formSchemeKey;
    private String mainDim;
    private Map<String, String> dimensionSet;
    public String contextEntityId;
    public String contextFilterExpression;
    private String adjustPeriod;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMainDim() {
        return this.mainDim;
    }

    public void setMainDim(String mainDim) {
        this.mainDim = mainDim;
    }

    public Map<String, String> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, String> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(String adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
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
}

