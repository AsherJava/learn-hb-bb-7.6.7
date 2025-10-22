/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean;

import com.jiuqi.nr.jtable.params.base.JtableContext;

public class QueryExplainCheckResultInfo {
    private JtableContext context;
    private String asyncTaskId;
    private String itemKey = "";
    private int itemOffset;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public int getItemOffset() {
        return this.itemOffset;
    }

    public void setItemOffset(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}

