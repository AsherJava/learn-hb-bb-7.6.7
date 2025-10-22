/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.datacheck.nodecheck.bean;

import com.jiuqi.nr.context.infc.INRContext;
import java.util.Map;

public class RequestDimTitileParam
implements INRContext {
    private String formSchemeKey;
    private String period;
    private Map<String, String> dimSet;
    private String orgEntity;
    private String[] orgCode;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Map<String, String> getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(Map<String, String> dimSet) {
        this.dimSet = dimSet;
    }

    public String[] getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String[] orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }

    public String getContextEntityId() {
        return this.orgEntity;
    }

    public String getContextFilterExpression() {
        return "";
    }
}

