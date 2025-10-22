/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.midstore.dataentry.bean;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;

public class MidstoreParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String midstoreCode;
    private boolean overImport;

    public String getMidstoreCode() {
        return this.midstoreCode;
    }

    public void setMidstoreCode(String midstoreCode) {
        this.midstoreCode = midstoreCode;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormKeys() {
        return this.context.getFormKey();
    }

    public void setFormKeys(String formKeys) {
        this.context.setFormKey(formKeys);
    }

    public boolean isOverImport() {
        return this.overImport;
    }

    public void setOverImport(boolean overImport) {
        this.overImport = overImport;
    }
}

