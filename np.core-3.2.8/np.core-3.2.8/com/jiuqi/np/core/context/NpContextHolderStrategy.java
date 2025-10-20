/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import com.jiuqi.np.core.context.NpContext;

public interface NpContextHolderStrategy {
    public void clearContext();

    public NpContext getContext();

    public void setContext(NpContext var1);

    public NpContext createEmptyContext();
}

