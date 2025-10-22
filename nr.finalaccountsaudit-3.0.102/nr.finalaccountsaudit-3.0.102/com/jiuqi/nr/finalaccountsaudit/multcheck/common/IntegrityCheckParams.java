/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class IntegrityCheckParams
extends IntegrityCheckInfo {
    private JtableContext context;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

