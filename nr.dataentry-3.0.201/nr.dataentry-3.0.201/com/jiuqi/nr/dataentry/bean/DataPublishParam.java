/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public class DataPublishParam
extends JtableLog {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private boolean publish;
    private List<String> formKeys;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isPublish() {
        return this.publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}

