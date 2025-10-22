/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.facade.FDimensionState;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IPermission {
    default public int getOrder() {
        return 1;
    }

    default public boolean isReadable(JtableContext context) {
        return true;
    }

    public FDimensionState isWriteable(JtableContext var1);
}

