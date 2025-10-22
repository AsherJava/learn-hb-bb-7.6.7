/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.efdc.bean.PlanTaskParam
 */
package com.jiuqi.gcreport.bde.fetch.impl.runner;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.efdc.bean.PlanTaskParam;

public class BdePlanTaskParam
extends PlanTaskParam
implements INRContext {
    private static final long serialVersionUID = 17551295393077419L;
    public String contextEntityId;
    public String contextFilterExpression;

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

