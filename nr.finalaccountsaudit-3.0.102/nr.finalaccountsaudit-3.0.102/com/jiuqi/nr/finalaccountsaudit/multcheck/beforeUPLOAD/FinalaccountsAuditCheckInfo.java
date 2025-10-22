/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;

public class FinalaccountsAuditCheckInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean beforeUPLOAD;
    private JtableContext context;

    public boolean isBeforeUPLOAD() {
        return this.beforeUPLOAD;
    }

    public void setBeforeUPLOAD(boolean beforeUPLOAD) {
        this.beforeUPLOAD = beforeUPLOAD;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

