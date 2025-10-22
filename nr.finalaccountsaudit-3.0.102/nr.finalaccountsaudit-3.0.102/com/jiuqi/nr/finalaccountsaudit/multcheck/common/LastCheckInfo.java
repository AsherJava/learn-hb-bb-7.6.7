/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;

public class LastCheckInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String asyncTaskId;
    private JtableContext context;

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

