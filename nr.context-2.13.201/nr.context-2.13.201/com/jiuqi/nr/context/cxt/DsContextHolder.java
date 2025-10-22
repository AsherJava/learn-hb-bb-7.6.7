/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.context.cxt;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import java.io.Serializable;
import org.springframework.util.Assert;

public class DsContextHolder {
    public static final String NR = "NR";
    public static final String DS_CONTEXT = "DS_CONTEXT";

    public static DsContext getDsContext() {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension(NR);
        DsContext dsContext = (DsContext)extension.get(DS_CONTEXT);
        if (dsContext == null) {
            dsContext = DsContextHolder.createEmptyContext();
            extension.put(DS_CONTEXT, (Serializable)dsContext);
        }
        return dsContext;
    }

    public static void setDsContext(DsContext context) {
        Assert.notNull((Object)context, "context is must not be null!");
        NpContext npContext = NpContextHolder.getContext();
        ContextExtension extension = npContext.getExtension(NR);
        extension.put(DS_CONTEXT, (Serializable)context);
    }

    public static void clearContext() {
        NpContext npContext = NpContextHolder.getContext();
        ContextExtension extension = npContext.getExtension(NR);
        extension.remove(DS_CONTEXT);
    }

    public static DsContext createEmptyContext() {
        return new DsContextImpl();
    }
}

