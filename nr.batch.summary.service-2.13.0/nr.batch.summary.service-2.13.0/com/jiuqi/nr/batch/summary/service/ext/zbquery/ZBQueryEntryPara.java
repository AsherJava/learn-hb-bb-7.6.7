/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.zbquery.rest.vo.DataEntryVO
 */
package com.jiuqi.nr.batch.summary.service.ext.zbquery;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.zbquery.rest.vo.DataEntryVO;

public class ZBQueryEntryPara
extends DataEntryVO
implements INRContext {
    private String period;
    private String dimValue;
    private String formSchemeKey;
    private String batchGatherSchemeCode;
    private static final long serialVersionUID = 8981521724668460434L;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getBatchGatherSchemeCode() {
        return this.batchGatherSchemeCode;
    }

    public void setBatchGatherSchemeCode(String batchGatherSchemeCode) {
        this.batchGatherSchemeCode = batchGatherSchemeCode;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

