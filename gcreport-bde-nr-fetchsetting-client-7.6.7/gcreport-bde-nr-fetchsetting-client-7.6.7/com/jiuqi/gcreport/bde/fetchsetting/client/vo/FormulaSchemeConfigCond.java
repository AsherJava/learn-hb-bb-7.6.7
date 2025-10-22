/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.Map;

public class FormulaSchemeConfigCond
implements INRContext {
    private static final long serialVersionUID = -2708494345220805646L;
    private String schemeId;
    private String orgId;
    private Map<String, String> assistDim;
    private String periodStr;
    public String contextEntityId;
    public String contextFilterExpression;

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Map<String, String> getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(Map<String, String> assistDim) {
        this.assistDim = assistDim;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
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
        return "FormulaSchemeConfigCond [schemeId=" + this.schemeId + ", orgId=" + this.orgId + ", assistDim=" + this.assistDim + ", periodStr=" + this.periodStr + ", contextEntityId=" + this.contextEntityId + ", contextFilterExpression=" + this.contextFilterExpression + "]";
    }
}

