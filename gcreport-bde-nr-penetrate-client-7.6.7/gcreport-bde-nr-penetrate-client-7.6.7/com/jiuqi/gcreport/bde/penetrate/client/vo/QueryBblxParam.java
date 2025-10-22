/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.gcreport.bde.penetrate.client.vo;

import com.jiuqi.nr.context.infc.INRContext;

public class QueryBblxParam
implements INRContext {
    private static final long serialVersionUID = -9121425997859831415L;
    private String formSchemeKey;
    private String orgCode;
    private String formatPeriodStr;
    private String contextEntityId;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getFormatPeriodStr() {
        return this.formatPeriodStr;
    }

    public void setFormatPeriodStr(String formatPeriodStr) {
        this.formatPeriodStr = formatPeriodStr;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return null;
    }
}

