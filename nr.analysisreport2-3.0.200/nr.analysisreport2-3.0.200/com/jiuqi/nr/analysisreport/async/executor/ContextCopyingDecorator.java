/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.analysisreport.async.executor;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.core.task.TaskDecorator;

public class ContextCopyingDecorator
implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        NpContext context = NpContextHolder.getContext();
        return () -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                runnable.run();
            }
            finally {
                NpContextHolder.clearContext();
            }
        };
    }
}

