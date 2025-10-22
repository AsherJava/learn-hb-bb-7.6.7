/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class BatchCopyBTWEntityInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String sourceEntityId;
    private String targetEntityId;
    private String sourceEntityTitle;
    private String targetEntityTitle;
    private String contextEntityId;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getSourceEntityId() {
        return this.sourceEntityId;
    }

    public void setSourceEntityId(String sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public String getTargetEntityId() {
        return this.targetEntityId;
    }

    public void setTargetEntityId(String targetEntityId) {
        this.targetEntityId = targetEntityId;
    }

    public String getSourceEntityTitle() {
        return this.sourceEntityTitle;
    }

    public void setSourceEntityTitle(String sourceEntityTitle) {
        this.sourceEntityTitle = sourceEntityTitle;
    }

    public String getTargetEntityTitle() {
        return this.targetEntityTitle;
    }

    public void setTargetEntityTitle(String targetEntityTitle) {
        this.targetEntityTitle = targetEntityTitle;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

