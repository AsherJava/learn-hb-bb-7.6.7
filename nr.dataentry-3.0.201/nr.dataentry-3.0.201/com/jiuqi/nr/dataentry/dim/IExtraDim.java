/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.dim;

import com.jiuqi.nr.dataentry.bean.DataEntryContext;

public interface IExtraDim {
    default public boolean isReadable(DataEntryContext context) {
        return true;
    }

    default public boolean isWriteable(DataEntryContext context) {
        return true;
    }
}

