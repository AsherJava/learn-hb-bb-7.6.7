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

public class RefreshStatusParam
extends NRContext {
    private JtableContext context;
    private boolean form;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public boolean isForm() {
        return this.form;
    }

    public void setForm(boolean form) {
        this.form = form;
    }
}

