/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapter;

public interface IDimensionDataAdapterProvider {
    public IDimensionDataAdapter getDimensionDataAdapterByName(String var1);

    public IDimensionDataAdapter getDimensionDataAdapterById(String var1);
}

