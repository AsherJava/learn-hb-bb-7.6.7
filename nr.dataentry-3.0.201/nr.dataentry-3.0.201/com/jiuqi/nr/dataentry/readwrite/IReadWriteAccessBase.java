/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite;

import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IReadWriteAccessBase {
    public String getName();

    default public Boolean IsBreak() {
        return false;
    }

    default public boolean isEnable(JtableContext context) {
        return true;
    }

    default public boolean isCheckFormAccess() {
        return false;
    }

    default public boolean isServerAccess() {
        return false;
    }
}

