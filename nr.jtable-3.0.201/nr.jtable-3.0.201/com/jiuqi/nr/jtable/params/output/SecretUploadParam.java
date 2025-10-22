/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class SecretUploadParam
implements INRContext {
    private String fileKey;
    private JtableContext jtableContext;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public SecretUploadParam(String fileKey, JtableContext jtableContext) {
        this.fileKey = fileKey;
        this.jtableContext = jtableContext;
    }

    public SecretUploadParam() {
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

