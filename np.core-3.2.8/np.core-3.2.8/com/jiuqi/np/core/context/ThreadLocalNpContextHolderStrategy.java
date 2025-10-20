/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolderStrategy;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import org.springframework.util.Assert;

public class ThreadLocalNpContextHolderStrategy
implements NpContextHolderStrategy {
    private static final ThreadLocal<NpContext> contextHolder = new ThreadLocal();

    @Override
    public void clearContext() {
        contextHolder.remove();
    }

    @Override
    public NpContext getContext() {
        NpContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = this.createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(NpContext context) {
        Assert.notNull((Object)context, "Only non-null AminoContext instances are permitted");
        contextHolder.set(context);
    }

    @Override
    public NpContext createEmptyContext() {
        return new NpContextImpl();
    }
}

