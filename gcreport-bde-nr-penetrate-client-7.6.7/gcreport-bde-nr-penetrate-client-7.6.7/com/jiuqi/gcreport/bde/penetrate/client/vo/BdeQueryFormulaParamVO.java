/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.gcreport.bde.penetrate.client.vo;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.nr.context.infc.INRContext;
import java.util.Map;

public class BdeQueryFormulaParamVO
implements INRContext {
    private static final long serialVersionUID = 3666988252610331692L;
    private FetchSettingCond fetchSettingCond;
    private String bblx;
    private String orgCode;
    private Map<String, String> dimensionSet;
    public String contextEntityId;
    public String contextFilterExpression;

    public FetchSettingCond getFetchSettingCond() {
        return this.fetchSettingCond;
    }

    public void setFetchSettingCond(FetchSettingCond fetchSettingCond) {
        this.fetchSettingCond = fetchSettingCond;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String toString() {
        return "BdeQueryFormulaParamVO [fetchSettingCond=" + this.fetchSettingCond + ", bblx=" + this.bblx + ", orgCode=" + this.orgCode + ", dimensionSet=" + this.dimensionSet + ", contextEntityId=" + this.contextEntityId + ", contextFilterExpression=" + this.contextFilterExpression + "]";
    }
}

