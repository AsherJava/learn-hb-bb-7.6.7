/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class DataentryRefreshParam
extends NRContext {
    private String refreshType;
    private JtableContext context;

    public DataentryRefreshParam() {
    }

    public DataentryRefreshParam(String refreshType, JtableContext context) {
        this.refreshType = refreshType;
        this.context = context;
    }

    public String getRefreshType() {
        return this.refreshType;
    }

    public void setRefreshType(String refreshType) {
        this.refreshType = refreshType;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

